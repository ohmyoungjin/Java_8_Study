package maeng.jdkstudy.stream;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamExamples1 {

    public static void main(String[] args) {
        IntStream.range(1, 10).forEach(i -> System.out.print(i + " ")); // 0 ~ 9
        System.out.println();
        IntStream.rangeClosed(1, 10).forEach(i -> System.out.print(i + " ")); // 1 ~ 10

    }
}
