package ca.serum390.godzilla.api.handlers;

import ca.serum390.godzilla.data.repositories.OrdersRepository;
import ca.serum390.godzilla.domain.orders.Order;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;

@Component
public class ShippingManagerHandler {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final OrdersRepository ordersRepository;

    private Order salesOrder = null;
    private TaskScheduler scheduler;
    private Logger logger;

    public ShippingManagerHandler(ApplicationEventPublisher applicationEventPublisher,
                                  OrdersRepository ordersRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.ordersRepository = ordersRepository;
        setupLogger();
    }


    public Mono<ServerResponse> validateShipping(ServerRequest req) {
        setup(req);
        if (salesOrder != null && salesOrder.getStatus().equals(Order.READY)) {


        }
        else{
            logger.info("The order ID is invalid or it is already planned for shipping");
        }


        return noContent().build();
    }


    public Mono<ServerResponse> cancelShipping(ServerRequest req) {

        return noContent().build();
    }


    private void setup(ServerRequest req) {
        Optional<String> orderID = req.queryParam("orderId");
        Optional<String> shippingMethod = req.queryParam("method");
        Optional<String> shippingDate = req.queryParam("shippingDate");


        // get the sales order object
        if (orderID.isPresent()) {
            salesOrder = ordersRepository.findById(Integer.parseInt(orderID.get())).block();
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
