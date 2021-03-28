package ca.serum390.godzilla.api.handlers;

import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.data.repositories.ShippingRepository;
import ca.serum390.godzilla.domain.orders.Order;
import ca.serum390.godzilla.domain.shipping.Shipping;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
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
    private LocalDate packagingDate;
    private String shippingMethod;
    private Shipping shipping;
    private boolean isShippingValid = true;
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
        validateOrderID(req);
        validateShippingDate(req);
        validateShippingMethod(req);
        if (isShippingValid && salesOrder.getStatus().equals(Order.READY)) {
            shipping = new Shipping(
                    shippingMethod,
                    Shipping.NEW,
                    salesOrder.getDueDate(),
                    shippingDate,
                    packagingDate,
                    salesOrder.getId(),
                    0);

            shippingRepository.save(shipping).block();

            // schedule the packaging
            // schedule the delivery


        }
        return noContent().build();
    }


    public Mono<ServerResponse> cancelShipping(ServerRequest req) {

        return noContent().build();
    }


    private void validateOrderID(ServerRequest req) {
        Optional<String> orderID = req.queryParam("orderId");
        // get the sales order object
        if (orderID.isPresent()) {
            salesOrder = ordersRepository.findById(Integer.parseInt(orderID.get())).block();
        } else {
            logger.info("invalid orderID");
            isShippingValid = false;
        }
    }

    private void validateShippingDate(ServerRequest req) {
        Optional<String> shippingDateReq = req.queryParam("shippingDate");
        // shipping Date setup
        if (salesOrder != null && shippingDateReq.isPresent()) {
            LocalDate input = LocalDate.parse(shippingDateReq.get());
            if (input.compareTo(LocalDate.now()) < 0) {
                logger.info("the entered shipping date is past.");
                isShippingValid = false;
            } else if (input.compareTo(salesOrder.getDueDate()) >= 0) {
                logger.info("the entered shipping date does not meet the order due date");
                isShippingValid = false;
            } else {
                shippingDate = input;
                packagingDate = shippingDate.minus(1, ChronoUnit.DAYS);
            }
        } else {
            logger.info("no shipping date is entred");
            isShippingValid = false;
        }
    }


    private void validateShippingMethod(ServerRequest req) {
        Optional<String> shippingMethodReq = req.queryParam("method");
        // shipping method setup
        if (shippingMethodReq.isPresent()) {
            switch (shippingMethodReq.get()) {
                case Shipping.AIR:
                case Shipping.CAR:
                case Shipping.FERRY:
                    shippingMethod = shippingMethodReq.get();
                    break;
                default:
                    logger.info("invalid shipping method");
                    isShippingValid = false;
            }
        } else {
            logger.info("invalid shipping method");
            isShippingValid = false;
        }
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
