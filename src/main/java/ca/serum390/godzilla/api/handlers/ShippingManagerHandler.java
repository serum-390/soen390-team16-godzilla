package ca.serum390.godzilla.api.handlers;

import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.data.repositories.ShippingRepository;
import ca.serum390.godzilla.domain.orders.Order;
import ca.serum390.godzilla.domain.shipping.Shipping;
import ca.serum390.godzilla.util.Events.ShippingEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ShippingManagerHandler {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final OrdersRepository ordersRepository;
    private final ShippingRepository shippingRepository;

    private LocalDate shippingDate;
    private String shippingMethod;
    private TaskScheduler scheduler;
    private Logger logger;
    private String message;

    public ShippingManagerHandler(ApplicationEventPublisher applicationEventPublisher,
                                  OrdersRepository ordersRepository, ShippingRepository shippingRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.ordersRepository = ordersRepository;
        this.shippingRepository = shippingRepository;
        setupLogger();
    }


    /**
     * validates the shipping request. If the shipping request is valid, it creates a shipping item in db and schedules
     * the shipping
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> validateShipping(ServerRequest req) {
        message = "";
        Optional<String> orderID = req.queryParam("orderID");
        // get the sales order object
        if (orderID.isPresent()) {
            ordersRepository.findById(Integer.parseInt(orderID.get())).subscribe(salesOrder -> {

                if (salesOrder != null && salesOrder.getStatus().equals(Order.PACKAGED) && validateShippingDate(req, salesOrder) &&
                        validateShippingMethod(req)) {
                    logger.info("orderID is valid");
                    shippingRepository.save(
                            new Shipping(
                                    shippingMethod,
                                    Shipping.NEW,
                                    salesOrder.getDueDate(),
                                    shippingDate,
                                    salesOrder.getId(),
                                    Shipping.getPrice(salesOrder.getItems().size(), shippingMethod))).subscribe(shippingItem ->
                    {

                        //set the order status to shipping process
                        ordersRepository.updateStatus(salesOrder.getId(), Order.SHIPPING_PROCESS).subscribe();
                        // schedule the shipping
                        ShippingEvent shippingEvent = new ShippingEvent(shippingItem.getId());
                        Runnable productionTask = () -> applicationEventPublisher.publishEvent(shippingEvent);
                        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
                        scheduler = new ConcurrentTaskScheduler(localExecutor);
                        scheduler.schedule(productionTask, new Date(System.currentTimeMillis() + 120000));
                        shippingRepository.updateStatus(shippingItem.getId(), Shipping.SCHEDULED).subscribe(item ->
                                logger.info("shipping " + shippingItem.getId() + " is scheduled"));
                    });
                } else {
                    logger.info("Invalid request: please check the order id, due date and status");
                    message += "\n Invalid request: please check the order id, due date and status";
                }
            });
        }
        return ok().contentType(MediaType.TEXT_PLAIN).bodyValue(message);
    }

    /**
     * cancels the shipping by setting the shipping item status to canceled and the order status to packaged
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> cancelShipping(ServerRequest req) {
        int shippingID = Integer.parseInt(req.pathVariable("id"));
        shippingRepository
                .findById(shippingID)
                .subscribe(shipping -> {
                    shippingRepository.updateStatus(shippingID, Shipping.CANCELED).subscribe(item ->
                            logger.info("shipping item " + shippingID + " is cancelled"));
                    ordersRepository.updateStatus(shipping.getOrderID(), Order.PACKAGED).subscribe();
                });
        return noContent().build();
    }

    private boolean validateShippingDate(ServerRequest req, Order salesOrder) {
        Optional<String> shippingDateReq = req.queryParam("shippingDate");
        // shipping Date setup
        if (shippingDateReq.isPresent()) {
            LocalDate input = LocalDate.parse(shippingDateReq.get());
            if (input.compareTo(LocalDate.now()) < 0) {
                logger.info("the entered shipping date is past.");
                message += "\n the entered shipping date is past";
            } else if (input.compareTo(salesOrder.getDueDate()) >= 0) {
                logger.info("the entered shipping date does not meet the order due date");
                message += "\n the entered shipping date does not meet the order due date";
            } else {
                shippingDate = input;
                logger.info("shippingDate is valid");
                return true;
            }
        } else {
            logger.info("no shipping date is entered");
            message += "\n no shipping date is entered";
        }
        return false;
    }


    private boolean validateShippingMethod(ServerRequest req) {
        Optional<String> shippingMethodReq = req.queryParam("method");
        // shipping method setup
        if (shippingMethodReq.isPresent()) {
            switch (shippingMethodReq.get()) {
                case Shipping.AIR:
                case Shipping.CAR:
                case Shipping.FERRY:
                    shippingMethod = shippingMethodReq.get();
                    logger.info("shipping method is valid");
                    return true;
                default:
                    logger.info("shipping method " + shippingMethodReq.get() + " is invalid");
                    message += "\n shipping method is invalid";
            }
        } else {
            logger.info("no shipping method is entered");
            message += "\n no shipping method is entered";
        }
        return false;
    }

    // Setups logger to log production info
    private void setupLogger() {
        logger = Logger.getLogger("ShippingLog");
        FileHandler fh;
        try {
            fh = new FileHandler(new File("shippingLog.log").getAbsolutePath());
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}
