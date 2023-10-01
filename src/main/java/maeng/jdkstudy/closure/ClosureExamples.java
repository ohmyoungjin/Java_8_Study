package maeng.jdkstudy.closure;

public class ClosureExamples {
    private int number = 999;

    public static void main(String[] args) {
        new ClosureExamples().test2();
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
        int number = 100; // Effectively final 이다. 변수 값을 변경 하면 compile 오류가 나온다
        //number = 1 ; => 이러면 compile error
        //Anonymous Class 의 this 의 경우 해당 선언하는 class 를 가르킨다 ! 밑에 예제에서는 Runnable class
        //*로컬 및 익명 클래스는 별도의 객체를 생성해서 별도의 Scope 로 인정받고, 람다는 같은 Scope 로 취급받는다.*
        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println(ClosureExamples.this.number);
            }
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
