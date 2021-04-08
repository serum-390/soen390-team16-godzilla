package ca.serum390.godzilla.api.handlers;

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

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.data.repositories.PackagedProductRepository;
import ca.serum390.godzilla.domain.orders.Order;
import ca.serum390.godzilla.domain.packaging.PackagedProduct;
import ca.serum390.godzilla.util.Events.PackagingEvent;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class PackageManagerHandler{
    
    private final ApplicationEventPublisher applicationEventPublisher;
    private final OrdersRepository ordersRepository;
    private final PackagedProductRepository packagedProductRepository;

    private Order salesOrder = null;
    private float length;
    private float width;
    private float height;
    private float weight;
    private String packageType;
    private LocalDate packagingDate;
    private TaskScheduler scheduler;
    private Logger logger;
    private String message;

    public PackageManagerHandler(ApplicationEventPublisher applicationEventPublisher,
                                  OrdersRepository ordersRepository, PackagedProductRepository packagedProductRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.ordersRepository = ordersRepository;
        this.packagedProductRepository = packagedProductRepository;
        setupLogger();
    }

    /**
     * validates the packaging request. If the packaging request is valid, it creates a packaging item in db and schedules
     * the packaging
     *
     * @param req
     * @return
     */
    public Mono<ServerResponse> validatePackaging(ServerRequest req) {
        message = "";
        if (validateOrderID(req) && salesOrder.getStatus().equals(Order.READY) && validatePackagingDate(req) ) {
            packagedProductRepository.save(
                    new PackagedProduct(
                            salesOrder.getId(),
                            length,
                            width,
                            height,
                            weight,
                            packageType,
                            packagingDate)).subscribe(packagedOrder ->
            {

                //set the order status to packaging process
                ordersRepository.updateStatus(salesOrder.getId(), Order.PACKAGING_PROCESS).subscribe();
                // schedule the shipping
                PackagingEvent packagingEvent = new PackagingEvent(packagedOrder.getId());
                Runnable productionTask = () -> applicationEventPublisher.publishEvent(packagingEvent);
                ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
                scheduler = new ConcurrentTaskScheduler(localExecutor);
                scheduler.schedule(productionTask, new Date(System.currentTimeMillis() + 120000));
                        logger.info("packaging " + packagedOrder.getId() + " is scheduled");
            });
        }
        else {
            message += "\n Invalid request: please check the order due date and status";
        }

        return ok().contentType(MediaType.TEXT_PLAIN).bodyValue(message);
    }

    private boolean validateOrderID(ServerRequest req) {
        Optional<String> orderID = req.queryParam("orderID");
        // get the sales order object
        if (orderID.isPresent()) {
            salesOrder = ordersRepository.findById(Integer.parseInt(orderID.get())).block();
            logger.info("order ID is valid");
            return salesOrder != null;
        } else {
            logger.info("order ID is not entered");
            message += "\n orderID is not entered";

        }
        return false;
    }

// Checks if the date for packaging is valid 
    private boolean validatePackagingDate(ServerRequest req) {
        Optional<String> packagingDateReq = req.queryParam("packagingDate");
        // shipping Date setup
        if (packagingDateReq.isPresent()) {
            LocalDate input = LocalDate.parse(packagingDateReq.get());
            if (input.compareTo(LocalDate.now()) < 0) {
                logger.info("the entered packaging date is past.");
                message += "\n entered packaging date is past";

            } else if (input.compareTo(salesOrder.getDueDate()) >= 0) {
                logger.info("the entered packaging date does not meet the order due date");
                message += "\n entered packaging date does not meet the order due date";

            } else {
                packagingDate = input;
                logger.info("packaging date is valid");
                return true;
            }
        } else {
            logger.info("no packaging date is entered");
            message += "\n no packaging date is entered";

        }
        return false;
    }
        // Setups logger to log packaging info
        private void setupLogger() {
            logger = Logger.getLogger("PackagingLog");
            FileHandler fh;
            try {
                fh = new FileHandler(new File("PackagingLog.log").getAbsolutePath());
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);
            } catch (SecurityException | IOException e) {
                e.printStackTrace();
            }
        }
}