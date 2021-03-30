package ca.serum390.godzilla.api.handlers;

import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.data.repositories.ShippingRepository;
import ca.serum390.godzilla.domain.orders.Order;
import ca.serum390.godzilla.domain.shipping.Shipping;
import ca.serum390.godzilla.util.Events.ShippingEvent;
import org.springframework.context.ApplicationEventPublisher;
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

@Component
public class ShippingManagerHandler {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final OrdersRepository ordersRepository;
    private final ShippingRepository shippingRepository;

    private Order salesOrder = null;
    private LocalDate shippingDate;
    private String shippingMethod;
    private Shipping shipping;
    private TaskScheduler scheduler;
    private Logger logger;

    public ShippingManagerHandler(ApplicationEventPublisher applicationEventPublisher,
                                  OrdersRepository ordersRepository, ShippingRepository shippingRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.ordersRepository = ordersRepository;
        this.shippingRepository = shippingRepository;
        setupLogger();
    }


    public Mono<ServerResponse> validateShipping(ServerRequest req) {

        if (validateOrderID(req) && salesOrder.getStatus().equals(Order.PACKAGED) && validateShippingDate(req) &&
                validateShippingMethod(req)) {
            shipping = new Shipping(
                    shippingMethod,
                    Shipping.NEW,
                    salesOrder.getDueDate(),
                    shippingDate,
                    salesOrder.getId(),
                    Shipping.getPrice(salesOrder.getItems().size(), shippingMethod));

            shipping = shippingRepository.save(shipping).block();

            // schedule the delivery
            ShippingEvent shippingEvent = new ShippingEvent(shipping.getId(), salesOrder.getId());
            Runnable productionTask = () -> applicationEventPublisher.publishEvent(shippingEvent);
            ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
            scheduler = new ConcurrentTaskScheduler(localExecutor);
            scheduler.schedule(productionTask, new Date(System.currentTimeMillis() + 120000));
            shippingRepository.updateStatus(shipping.getId(), Shipping.SCHEDULED);
            logger.info("shipping " + shipping.getId() + " is scheduled");
        }
        return noContent().build();
    }


    //TODO removed the shipping item, reset the order status to packaged
    public Mono<ServerResponse> cancelShipping(ServerRequest req) {
        Optional<String> shippingIDReq = req.queryParam("shippingID");
        int shippingID;
        int orderID;
        if (shippingIDReq.isPresent()) {
            shippingID = Integer.parseInt(shippingIDReq.get());
            shippingRepository.updateStatus(shippingID, Shipping.CANCELED);
            shippingRepository
                    .findById(shippingID)
                    .subscribe(shipping -> ordersRepository
                            .updateStatus(shipping.getOrderID(), Order.PACKAGED)
                            .subscribe());
        }


        return noContent().build();
    }


    private boolean validateOrderID(ServerRequest req) {
        Optional<String> orderID = req.queryParam("orderID");
        // get the sales order object
        if (orderID.isPresent()) {
            salesOrder = ordersRepository.findById(Integer.parseInt(orderID.get())).block();
            if (salesOrder != null) {
                return true;
            }
        } else {
            logger.info("orderID is not entered");
        }
        return false;
    }

    private boolean validateShippingDate(ServerRequest req) {
        Optional<String> shippingDateReq = req.queryParam("shippingDate");
        // shipping Date setup
        if (salesOrder != null && shippingDateReq.isPresent()) {
            LocalDate input = LocalDate.parse(shippingDateReq.get());
            if (input.compareTo(LocalDate.now()) < 0) {
                logger.info("the entered shipping date is past.");
            } else if (input.compareTo(salesOrder.getDueDate()) >= 0) {
                logger.info("the entered shipping date does not meet the order due date");
            } else {
                shippingDate = input;
                return true;
            }
        } else {
            logger.info("no shipping date is entered");
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
                    return true;
                default:
                    logger.info("invalid shipping method");
            }
        } else {
            logger.info("no shipping method is entered");
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
