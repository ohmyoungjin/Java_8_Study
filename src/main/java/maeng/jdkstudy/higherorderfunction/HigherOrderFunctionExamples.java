package maeng.jdkstudy.higherorderfunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HigherOrderFunctionExamples {
    public static void main(String[] args) {

        //g 자체가 i -> "#" + i 이걸 파라미터로 받아서 실행된다.
        final Function<Function<Integer, String>, String> f = g -> g.apply(10);

        System.out.println(f.apply(i -> "#" + i)); // "#10"

        final Function<Integer, Function<Integer, Integer>> f2 = i -> (i2 -> i+i2);
        System.out.println(f2.apply(10).apply(30));

        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<String> mappedList = map(list, i -> "#" + i);
        System.out.println(
                mappedList
        );
        System.out.println(
        list.stream()
                .filter(i -> i > 2)
                .map(i -> "#" + i)
                .collect(Collectors.toList())
        );

        //f1     //P1     //f2     //P2     //f3     //P3     //R
        Function<Integer, Function<Integer, Function<Integer, Integer>>> f3 =
                i1 -> i2 -> i3 -> i1 + i2 + i3;
        System.out.println(
            f3.apply(1).apply(2).apply(3)
        );
        Function<Integer, Function<Integer, Integer>> plus10 = f3.apply(10);
        System.out.println(
            plus10.apply(1).apply(3)
        );
        System.out.println(
            filterMap(list, i -> i + "#", i -> i>2)
        );

        System.out.println(testSum(test()));
    }


    private static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        final List<R> result = new ArrayList<>();
        for (final T t: list) {
            result.add(mapper.apply(t));
        }
        return result;
    }

    private static <T, R> List<R> filterMap(List<T> list, Function<T, R> mapper, Predicate<T> filter) {
        final List<R> result = new ArrayList<>();
        for (final T t: list) {
            if(filter.test(t)) {
                result.add(mapper.apply(t));
            }
        }
        return result;
    }

    private static int testSum(int a) {
        return a + 1;
    }

    private static int test() {
        return 1;
    }
}
