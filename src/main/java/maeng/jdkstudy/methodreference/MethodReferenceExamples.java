package maeng.jdkstudy.methodreference;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MethodReferenceExamples {

    public static void main(String[] args) {
        Arrays.asList(1, 2, 3, 4, 5)
                .forEach(System.out::println);
                //.forEach(i -> System.out.println(i));

        System.out.println(
        Arrays.asList(new BigDecimal("10.00"), new BigDecimal("23.00"), new BigDecimal("5"))
                .stream()
//                .sorted((bd1, bd2) -> bd1.compareTo(bd2))
//                .sorted(BigDecimalUtil::compare)
                .sorted(BigDecimal::compareTo)
                .collect(Collectors.toList())
        );

        final String targetString = "c";
        System.out.println(
            Arrays.asList("a", "b", "c", "d")
                    .stream()
                    //.anyMatch(x -> x.equals("c"))
                    .anyMatch(targetString::equals)
        );

        methodReference03();
    }

    private static void methodReference03() {
        /* First Class Function */

        /*
         * A function cam be passed as a parameter to another function.
         * Using Lambda Expression
         */
        System.out.println(testFirstClassFunction(3, i -> String.valueOf(i * 2)));
        /*
         * Using Method Reference
         */
        System.out.println(testFirstClassFunction(3, MethodReferenceExamples::doubleThenToString));

        /**
         * A function can be returned as the result of another function.
         */
        /*
         * Using Lambda Expression
         */
        final Function<Integer, String> f1 = getDoubleThenToStringUsingLambdaExpression();
        String resultFromF1 = f1.apply(3);
        System.out.println(resultFromF1);
        /*
         * Using Method Reference
         */
        final Function<Integer, String> fmr = getDoubleThenToStringUsingMethodReference();
        String resultFromFmr = fmr.apply(3);
        System.out.println(resultFromFmr);

        System.out.println("\n=======================================================");
        /**
         * A function can be stored in the data structure.
         */
        /*
         * Using Lambda Expression
         */
        final List<Function<Integer, String>> fsL = Arrays.asList(i -> String.valueOf(i * 2));
        for (final Function<Integer, String> f : fsL) {
            final String result = f.apply(3);
            System.out.println(result);
        }
        /*
         * Using Method Reference
         */
        final List<Function<Integer, String>> fsMr = Arrays.asList(MethodReferenceExamples::doubleThenToString);
        for (final Function<Integer, String> f : fsMr) {
            final String result = f.apply(3);
            System.out.println(result);
        }
        System.out.println("\n=======================================================");
        /*
         * Using Lambda Expression
         */
        final Function<Integer, String> fl2 = i -> String.valueOf(i * 2);
        final String resultFl2 = fl2.apply(5);
        System.out.println(resultFl2);

        /*
         * Using Method Reference
         */
        final Function<Integer, String> fmr2 = MethodReferenceExamples::doubleThenToString;
        String resultFmr2 = fmr2.apply(5);
        System.out.println(resultFmr2);
        System.out.println("\n=======================================================");
        /*
         * Both Lambda Expression and Method Reference
         */
        List<Function<Integer, String >> fsBoth = Arrays.asList(i -> String.valueOf(i * 2), MethodReferenceExamples::doubleThenToString, MethodReferenceExamples::addHashPrefix);
        for (final Function<Integer, String> f : fsBoth) {
            System.out.println(f.apply(7));
        }
    }

    private static String doubleThenToString(int i) {
        return String.valueOf(i * 2);
    }

    private static String addHashPrefix(int number) {
        return "#" + number;
    }
    private static String testFirstClassFunction(int n, Function<Integer, String> f) {
        return "The result is " + f.apply(n);
    }

    private static Function<Integer, String> getDoubleThenToStringUsingLambdaExpression() {
        return i -> String.valueOf(i * 2);
    }
    private static Function<Integer, String> getDoubleThenToStringUsingMethodReference() {
        //Function 의 두 개의 타입을 받게 되는데,
        // 첫 번째 타입 같은 경우 내가 Method Reference 로 사용하는 함수의 파라미터 값을 받는다 ex)int i.
        // 두 번째 타입 같은 경우 Method Reference 로 사용하는 함수의 return 값으로 받는다. ex)return String.valueOf(i * 2);
        return MethodReferenceExamples::doubleThenToString;
    }
}

class BigDecimalUtil {
    public static int compare(BigDecimal bd1, BigDecimal bd2) {
        return bd1.compareTo(bd2);
    }
}