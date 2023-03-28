package br.com.kafka.core.utils;

import lombok.Builder;
import lombok.Data;
import lombok.Synchronized;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.round;

@Data
public class ProgressBar {

    private Integer size;
    private Integer maxSquares;

    private AtomicInteger count;

    @Synchronized
    public void print() {

        count.addAndGet(1);

        Integer checkpoint = size / maxSquares;

        if (count.get() == 1) {
            System.out.print("Total de itens:" + String.format("%04d", size)  + "[" + "\u25A0" + String.format("%04d", count.get()) + "\u25A0");
        }

        if (count.get() == size) {
            System.out.println("\b\b\b\b\b" + "\u25A0" + String.format("%04d", count.get()) + "\u25A0" + "] 100%");
        } else {
            if (count.get() != 1) {
                System.out.print("\b\b\b\b\b\b" + "\u25A0" + String.format("%04d", count.get()) + "\u25A0");
            }
        }

        if (count.get() % checkpoint == 0) {
            System.out.print("\b\b\b\b\b" + "\u25A0" + String.format("%04d", count.get()) + "\u25A0");
        }

    }

}
