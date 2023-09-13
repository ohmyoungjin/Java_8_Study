package maeng.jdkstudy.stream;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamExamples5Parallel {
    public static void main(String[] args) {
        final int[] sum = { 0 };
        IntStream.range(0, 100)
                .forEach(i -> sum[0] += i);

        System.out.println("stream sum (side-effect) : " + sum[0]);

        final int[] sum2 = { 0 };
        IntStream.range(0, 100)
                .parallel() // 병렬 프로그래밍 현재 상황에서는 race condition (경쟁 상태) 여러 개의 프로세스가 접근 하려고 하는 상황 core 수가 여러개이기 때문에
                .forEach(i -> sum2[0] += i);

        System.out.println("parallel sum2 (with side-effect): " + sum2[0]);

        System.out.println("stream sum (no side-effect) : " +
            IntStream.range(0, 100)
                    .sum()
        );

        System.out.println("parallel stream sum (no side-effect) : " +
                IntStream.range(0, 100)
                        .parallel()
                        .sum()
        );

//        final Long start = System.currentTimeMillis();
//        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)
//                .stream()
//                .map(i -> { // 1초 지연이 발생
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    return i;
//                })
//                .forEach(i -> System.out.print(i + " "));
//        System.out.println(System.currentTimeMillis() - start);
        System.out.println("\n=====================");
        System.out.println("Parallel Stream");
        System.out.println("8 elements");
        final Long start2 = System.currentTimeMillis();
        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)
                .parallelStream()
                .map(i -> { // 1초 지연이 발생
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return i;
                })
                .forEach(i -> System.out.print(i + " "));
        System.out.println(System.currentTimeMillis() - start2);

        System.out.println("15 element");
        final Long start3 = System.currentTimeMillis();
        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ,13 ,14 ,15)
                .parallelStream()
                .map(i -> { // 1초 지연이 발생
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return i;
                })
                .forEach(i -> System.out.print(i + " "));
        System.out.println(System.currentTimeMillis() - start3);

        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "4");
        System.out.println("core count 4 & 15 element");
        final Long start4 = System.currentTimeMillis();
        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ,13 ,14 ,15)
                .parallelStream()
                .map(i -> { // 1초 지연이 발생
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return i;
                })
                .forEach(i -> System.out.print(i + " "));
        System.out.println(System.currentTimeMillis() - start4);
    }
}
