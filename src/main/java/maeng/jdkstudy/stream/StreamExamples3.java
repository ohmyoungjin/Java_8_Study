package maeng.jdkstudy.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExamples3 {
    public static void main(String[] args) {

        System.out.println(
        Stream.of(1, 3, 3, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .collect(Collectors.toList())
        );

        System.out.println(
                Stream.of(1, 3, 3, 5, 5)
                        .filter(i -> i > 2)
                        .map(i -> i * 2)
                        .map(i -> "#" + i)
                        .collect(Collectors.toSet())
        );

        System.out.println(
                Stream.of(1, 3, 3, 5, 5)
                        .filter(i -> i > 2)
                        .map(i -> i * 2)
                        .map(i -> "#" + i)
                        .collect(Collectors.joining(", "))
        );

        System.out.println(
                Stream.of(1, 3, 3, 5, 5)
                        .filter(i -> i > 2)
                        .map(i -> i * 2)
                        .map(i -> "#" + i)
                        .distinct() //sql 중복 제거 함수랑 똑같은 중복 값 제거 해주는 함수 !
                        .collect(Collectors.joining(", ", "[",  "]"))
        );

        final Integer integer127 = 127;
        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 127)
                        .filter(i -> i == integer127)
                        .findFirst()
        );

        //Integer 의 cache 메모리로 인해서 -128 ~ 127 까지의 숫자만 가지고 있는데
        //== 메모리 주소 값 비교가 되기 때문에 이렇게 쓰면 Optional.empty 이런 값이 나온다 equals 사용
        final Integer integer128 = 128;
        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 128)
                        .filter(i -> i == integer128)
                        .findFirst()
        );

        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 128)
                        .filter(i -> i.equals(integer128))
                        .findFirst()
        );

        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        for (Integer i : numbers) {
            System.out.print("i=" + i + " ");
        }

        Integer integer3 = 3;

        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 128)
                        .filter(i -> i > integer3)
                        .findFirst()
        );

        Stream.of(1, 2, 3, 4, 5)
                .forEach(i -> System.out.println(i));

    }
}
