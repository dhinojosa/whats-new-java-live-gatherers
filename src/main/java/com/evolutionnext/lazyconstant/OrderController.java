package com.evolutionnext.lazyconstant;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import module java.base;

@SuppressWarnings("preview")
class OrderController {
    private final LazyConstant<Logger> logger = LazyConstant.of(new Supplier<Logger>() {
        @Override
        public Logger get() {
            return LoggerFactory.getLogger(OrderController.class);
        }
    });
    void submitOrder() {
        logger.get().info("Order Submitted");
    }
}
