package com.evolutionnext.lazyconstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import module java.base;

@SuppressWarnings("preview")
class Application {
    final Supplier<Logger> logger = LazyConstant.of(() ->
        LoggerFactory.getLogger(Application.class));

    private final int POOL_SIZE = 10;

    private final List<OrderController> ORDERS
        = List.ofLazy(POOL_SIZE, x -> {
        logger.get().info("Retrieving Item");
        logger.get().info("x: {}", x);
        return new OrderController();
    });

    public OrderController orders() {
        var threadId = Thread.currentThread().threadId();
        logger.get().info("Thread Id: {}", threadId);
        long index = Thread.currentThread().threadId() % POOL_SIZE;
        return ORDERS.get((int) index);
    }
}
