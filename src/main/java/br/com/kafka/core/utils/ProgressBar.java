package br.com.kafka.core.utils;

import lombok.Builder;
import lombok.Data;

import static java.lang.Math.round;

@Data
public class ProgressBar {

    private Integer size;
    private Integer maxSquares;

    public void print(Integer itemCount) {

        Integer checkpoint = size / maxSquares;

        if (itemCount == 1) {
            System.out.print("[");
        }
        if (itemCount == size) {
            System.out.println("]");
        }

        if (itemCount % checkpoint == 0) {
            System.out.print("\u25A0");
        }
    }

}
