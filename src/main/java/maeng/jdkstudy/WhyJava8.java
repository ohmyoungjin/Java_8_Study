package maeng.jdkstudy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class WhyJava8 {

    public static void main(String[] args) {
        final List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        final StringBuilder stringBuilder = new StringBuilder();
        for (Integer number : numbers) {
            stringBuilder.append(number).append(" : ");
        }
        System.out.println(stringBuilder.toString());

        final String result = numbers.stream()
                .map(String::valueOf)
                .collect(joining(" : ")); //mapping 하는 함수 자료구조 Map 과는 다름
        System.out.println(result);

    }
}
