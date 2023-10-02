package maeng.jdkstudy.closure;

import java.util.function.Function;

public class ClosureExamples {
    private int number = 999;

    public static void main(String[] args) {
        //new ClosureExamples().test2();
        //Function<Integer, String> 이 부분 먼저 실행하여 String return
        //이후 f 의 대한 Function 값으로 어떤식으로 apply 할 지 override
        //Function<Function<Integer, String> 첫 번째 파라미터가 Function override 한 부분이 됨
        Function<Function<Integer, String>, String> f = x -> x.apply(10);
        Function<Integer, Function<Integer, Integer>> sum = s -> s2 -> s+s2;
        Function<Integer, String> b = i -> String.valueOf(i);
        System.out.println("Function : " +b.apply(30));
        Function<Integer, String> andThen = b.andThen(i -> "#" + i);
        System.out.println("andThen : " + andThen.apply(10));
        System.out.println("고계함수 : " + f.apply(i -> "#"+ i));
        System.out.println("고계함수2 : " + sum.apply(1).apply(9));
    }

    @Override
    public String toString() {
        return "ClosureExamples{" +
                "number=" + number +
                '}';
    }

    public static <T> String toString(T value) {
        return "The value is " + String.valueOf(value) +".";
    }

    private void test1() {
        //final int number = 100; //jdk 7 에서는 final 로 선언을 해야 하지만 8 부터는 final 선언을 안해도 compile 오류가 나지 않는다
        //int number = 100; // Effectively final 이다. 변수 값을 변경 하면 compile 오류가 나온다
        //number = 1 ; => 이러면 compile error
        //Anonymous Class 의 this 의 경우 해당 선언하는 class 를 가르킨다 ! 밑에 예제에서는 Runnable class
        //*로컬 및 익명 클래스는 별도의 객체를 생성해서 별도의 Scope 로 인정받고, 람다는 같은 Scope 로 취급받는다.*
        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println(number);
            } // 이렇게 접근이 원래 불가능 하다 scope 자체가 Runnable class 에 속해있기 때문에
            // 컴파일러가 getter 를 생성 해서 ClosureExamples 의 filed 에 접근하도록 만들어준다 !! **
        });
        testClosure("lambda", ()-> System.out.println(this.number));
    }

    private void test2() {

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println("this.toString():" + this.toString());
            }
        });

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println("ClosureExamples.this : "+ClosureExamples.this.toString());
            }
        });

        testClosure("lambda", ()-> System.out.println(this.toString()));
    }

    private void test3() {

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                // Anonymous Class 에서는 이렇게 접근해야 한다.
                System.out.println("ClosureExamples.this : "+ ClosureExamples.this.toString("Test"));
            }
        });

        testClosure("lambda", ()-> System.out.println(toString("Test")));
    }

    private void test4() {
        int number = 100;
        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                int number = 50;
                // Anonymous class 같은 경우 scope 이 새로 생기기 때문에 변수 선언이 가능하다
                System.out.println("ClosureExamples.this : "+ number);
            }
        });

        testClosure("lambda", ()-> {
            // int number = 50 => lambda 같은 경우 오류
            System.out.println(number); // => 함수 안의 number
            System.out.println(this.number); // => class 안의 number
        });
    }


    private static void testClosure(final String name, Runnable runnable) {
        System.out.println("=====================================");
        System.out.println(name + " : ");
        runnable.run();
        System.out.println("=====================================");
    }


}
