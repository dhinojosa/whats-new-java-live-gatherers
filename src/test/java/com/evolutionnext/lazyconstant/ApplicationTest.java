package com.evolutionnext.lazyconstant;

import org.junit.jupiter.api.Test;

public class ApplicationTest {
    @Test
    void testVirtualThreadOneTime() throws InterruptedException {
        Application application = new Application();
        Thread virtualThread = Thread.ofVirtual().name("new-virtual-thread-1").start(() -> {
            application.orders().submitOrder();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        virtualThread.join();
    }

    @Test
    void runMainThreadThreeTimes() {
        Application application = new Application();
        try {
            application.orders().submitOrder();
            Thread.sleep(100);
            application.orders().submitOrder();
            Thread.sleep(100);
            application.orders().submitOrder();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
