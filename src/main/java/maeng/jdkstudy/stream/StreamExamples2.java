package maeng.jdkstudy.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamExamples2 {
    public static void main(String[] args) {
//        Stream.of(1, 2, 3, 4, 5)
//                .forEach(i -> System.out.print(i + " "));

        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer result = null;
        for (final Integer number : numbers) {
            if (number > 3 && number < 9) {
                final Integer newNumber = number * 2;
                if (newNumber > 10) {
                    result = newNumber;
                }
            }
        }
        System.out.println("Imperative Result : " + result);
        System.out.println("Functional Result : " +
        numbers.stream()
                .filter(number -> number > 3)
                .filter(number -> number < 9)
                .map(number -> number * 2)
                .filter(number -> number > 10)
                .findFirst()
        );

        final List<Integer> greaterThan3 = filter(numbers, i -> i > 3);
        List<Integer> lessThan9 = filter(greaterThan3, i -> i < 9);
        List<Integer> doubled = map(lessThan9, i -> i * 2);
        List<Integer> graterThan10 = filter(doubled, i -> i > 10);
        System.out.println(graterThan10.get(0));
    }

    private static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        final List<T> result = new ArrayList<>();
        for (final T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        final List<R> result =new ArrayList<>();
        for (final T t : list) {
            result.add(mapper.apply(t));
        }
        return result;
    }
}
