package maeng.jdkstudy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalInterfaceExamples {

    private void run4FunctionalInterfaces() {

        Function<String, Integer> toIntToNoLambda = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        };
        Function<String, Integer> toInt = (value) -> Integer.parseInt(value);
        Integer number = toInt.apply("100");
        System.out.println(number);

        final Function<Integer, Integer> identity = value -> value;
        final Function<Integer, Integer> identityFunction = Function.identity();
        System.out.println(identity.apply(999));

        final Consumer<String> print = new Consumer<String>() {
            @Override
            public void accept(String value) {
                System.out.println(value);
            }
        };

        final Consumer<String> print1 = value ->System.out.println(value);
        final Consumer<String> greetings = value ->System.out.println("hello " + value);
        //final Function<String, Void> print2 = value ->System.out.println(value); //Function 같은 경우는 입력 값 출력 값이 존재해야 한다.
        //출력 값을 Void 로 해놔도 오류가 나온다.


        print.accept("hello");
        print1.accept("hello2");
        greetings.accept("hello2");

        Predicate<Integer> isPositive = value ->  value > 0;
        //System.out.println(isPositive(1)); => 이렇게 쓰면 오류다 Class 자체가 아닌 안에 있는 함수형 인터페이스 함수를 사용해줘야 함.
        System.out.println(isPositive.test(1));
        System.out.println(isPositive.test(0));
        System.out.println(isPositive.test(-1));

        List<Integer> numbers = Arrays.asList(-5,-4,-3,-2,-1,0,1,2,3,4,5);
        List<Integer> positiveNumbers = new ArrayList<>();
        for (Integer num : numbers) {
            if(isPositive.test(num)) {
                positiveNumbers.add(num);
            }
        }
        System.out.println("positiveNumbers : " + positiveNumbers);

        Predicate<Integer> lessThan3 = value ->  value < 3;
        List<Integer> numberLessThan3 = new ArrayList<>();
        for (Integer num : numbers) {
            if(lessThan3.test(num)) {
                numberLessThan3.add(num);
            }
        }
        System.out.println("positiveNumbers : " + numberLessThan3);

        System.out.println("filter : " + filter(numbers, isPositive));
        System.out.println("filter : " + filter(numbers, lessThan3));

        final Supplier<String> helloSupplier = () -> "Hello";

        System.out.println(helloSupplier.get() + " world");
        long start = System.currentTimeMillis();
        printIfValidIndex(0, ()->getVeryExpensiveValue());
        printIfValidIndex(-2, ()->getVeryExpensiveValue());
        printIfValidIndex(-1, ()->getVeryExpensiveValue());
        System.out.println("It took "+ ((System.currentTimeMillis()- start) / 1000) + " seconds.");
    }

    //많은 컴퓨터 자원을 사용하는 로직을 가정
    private static String getVeryExpensiveValue() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Maeng";
    }

    //private static void printIfValidIndex(int number, String value) {
    //Lazy Evaluation
    private static void printIfValidIndex(int number, Supplier<String> valueSupplier) {
        if (number >= 0) {
            System.out.println("The value is " + valueSupplier.get() + ".");
        } else {
            System.out.println("Invalid");
        }
    }

    private static <T> List<T> filter(List<T> list, Predicate<T> filter) {
        List<T> result = new ArrayList<>();
        for (T input : list) {
            if (filter.test(input)) {
                result.add(input);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        //한 개의 Functional interface 를 통해서 여러가지로 응용해서 사용할 수 있다.
        println(1, 12, 20, (i1, i2, i3) -> String.valueOf(i1 + i2 + i3));
        println("Area is ", 12, 20, (message, length, width) -> message + (length * width));
        println(1L, "Maeng2", "test@email.com", (id, name, email) -> "User info : Id= " + id + ", name=" + name + ", email=" + email);

        Function3<Integer, Integer, Integer, String> f3 = (i1, i2, i3) -> String.valueOf(i1 + i2 + i3);

        BigDecimalToCurrency bigDecimalToCurrency = bd -> "$" + bd.toString();
        System.out.println(bigDecimalToCurrency.toCurrency(new BigDecimal("120.00")));

        //익명의 클레스
        final InvalidFunctionalInterface anonymous = new InvalidFunctionalInterface()
        {
            @Override
            public <T> String mkString(T value) {
                return value.toString();
            }
        };
        System.out.println(anonymous.mkString("anonymous class : " + 123));

        //람다 표현식
        //이건 불가능 하다 => 익명 클레스 같은 경우 어떤 value 값으로 return 하겠다는 제네릭 타입을 추론할 수 있지만
        //아래의 경우 value 를 변경할 때 이 변수 값이 어떤 값으로 변환되어 return 하겠다는 타입을 추론할 수 없다.
        //final InvalidFunctionalInterface invalidFunctionalInterface = value ->  value.toString();
        //System.out.println(invalidFunctionalInterface.mkString("lambda : " + 123));


    }
    private static <T1, T2, T3> void println(T1 t1, T2 t2, T3 t3, Function3<T1, T2, T3, String> function) {
        System.out.println(function.apply(t1, t2, t3));
    }
}


@FunctionalInterface
interface Function3<T1, T2, T3, R> {
    R apply(T1 t1, T2 t2, T3 t3);
}

@FunctionalInterface
interface BigDecimalToCurrency {
    String toCurrency(BigDecimal value);
}

@FunctionalInterface
interface InvalidFunctionalInterface {
    //익명의 클레스로는 가능 하지만 람다로는 사용이 불가능하다. 위와 같이 사용해야 함 .
    <T> String mkString(T value);
}