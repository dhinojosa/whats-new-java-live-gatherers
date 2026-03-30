package com.evolutionnext.lazyconstant;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class LazyListTest {
    private static final String[] COLORS = {"red", "orange", "yellow", "green",
        "blue", "indigo", "violet", "black"};

    public static final int SIZE = 8;


    record Ball(String color) {
    }

    @SuppressWarnings("preview")
    @Test
    public void testStableList() {
        List<Ball> list = List.ofLazy(SIZE, new IntFunction<Ball>() {
            @Override
            public Ball apply(int i) {
                String color = COLORS[i];
                System.out.printf("Loading %s ball%n", color);
                return new Ball(color);
            }
        });


        IntStream
            .range(1, 5)
            .forEach(_ ->
                Thread.ofVirtual().start(() -> {
                    Ball ball = list.get((int) Thread.currentThread().threadId() % SIZE);
                    System.out.printf("Thread %s got %s ball%n", Thread.currentThread().threadId(), ball.color());
                }));


    }

    @SuppressWarnings("preview")
    @Test
    public void testStableListToViewTheListWhenFewElementsAreLoaded() {
        List<Ball> list = List.ofLazy(SIZE, new IntFunction<Ball>() {
            @Override
            public Ball apply(int i) {
                String color = COLORS[i];
                System.out.printf("Loading %s ball%n", color);
                return new Ball(color);
            }
        });

        System.out.println(list);

        Ball ball = list.get(3);
        assertThat(ball).isEqualTo(new Ball("green"));

        System.out.println(list);
    }
}
