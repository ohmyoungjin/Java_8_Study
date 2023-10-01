package maeng.jdkstudy.stream;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamExamples5ParallelPerformancePractical {
    private static final String[] priceStrings ={"1.0", "100.99", "35.75", "21.30", "88.00"};
    private static final BigDecimal[] targetPrices = {new BigDecimal("30"), new BigDecimal("20"), new BigDecimal("31")};
    private static final Random random = new Random(123);
    private static Random targetPriceRandom = new Random(111);
    private static final List<Product2> products;

    static {
        final int length = 8_000_000;
        //final int length = 80;
        //final List<Product2> list = new ArrayList<>(); ArrayList 의 default length = 100,000 모자라면 계속 1.5배씩 늘려간다 => 필요 없는 메모리 사용량이 늘어남 계속 연산을 해야 하므로
        //final List<Product2> list = new ArrayList<>(length); 이렇게 변경해주면 처음부터 사이즈를 지정해준다 !
        final Product2[] list = new Product2[length];
        for (int i=1; i<=length; i++) {
            list[i -1] =new Product2((long) i, "Product" + i, new BigDecimal(priceStrings[random.nextInt(5)]));
        }
        //products = Collections.unmodifiableList(list);
        products = Arrays.asList(list);
    }

    private static BigDecimal imperativeSum(List<Product2> products, Predicate<Product2> predicate) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final Product2 product: products) {
            if (predicate.test(product)) {
                sum = sum.add(product.getPrice());
            }
        }
        return sum;
    }

    private static BigDecimal streamSum(final Stream<Product2> stream, final Predicate<Product2> predicate) {
        return stream.filter(predicate).map(Product2::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        //return stream.filter(predicate).map(Product2 -> Product2.getPrice()).reduce(BigDecimal.ZERO, (i1, i2) -> i1.add(i2)); 동일한 문법 위와 같이 씀
    }

    private static void imperativeTest(BigDecimal targetPrice) {
        System.out.println("=====================================");
        System.out.println("\nImperative Sum\n----------------------------------------------" + products.toArray().length);
        final long start = System.currentTimeMillis();
        System.out.println("Sum " +
                imperativeSum(products, product2 -> product2.getPrice().compareTo(targetPrice) >= 0)
        );
        System.out.println("It took " + (System.currentTimeMillis() - start) + " ms." );
        System.out.println("================================================");
    }

    private static void streamTest(BigDecimal targetPrice) {
        System.out.println("=====================================");
        System.out.println("\nstreamTest Sum\n----------------------------------------------");
        final long start = System.currentTimeMillis();
        System.out.println("Sum " +
                streamSum(products.stream(), product2 -> product2.getPrice().compareTo(targetPrice) >= 0)
        );
        System.out.println("It took " + (System.currentTimeMillis() - start) + " ms." );
        System.out.println("================================================");
    }

    private static void parallelStreamTest(BigDecimal targetPrice) {
        System.out.println("=====================================");
        System.out.println("\nparallelStreamTest Sum\n----------------------------------------------");
        final long start = System.currentTimeMillis();
        System.out.println("Sum " +
                streamSum(products.parallelStream(), product2 -> product2.getPrice().compareTo(targetPrice) >= 0)
        );
        System.out.println("It took " + (System.currentTimeMillis() - start) + " ms." );
        System.out.println("================================================");
    }

    public static void main(String[] args) {

        final BigDecimal targetPrice = new BigDecimal("0");

        imperativeTest(targetPrice);
        streamTest(targetPrice);
        parallelStreamTest(targetPrice);

        System.out.println("\nIgnore Tests Above\n===========================\n");
        System.out.println("Start!");
        for (int i = 0; i < 5; i++) {
            BigDecimal price = targetPrices[targetPriceRandom.nextInt(3)];

            imperativeTest(price);
            streamTest(price);
            parallelStreamTest(price);
        }

    }
}

@AllArgsConstructor
@Data
class Product2 {
    private Long id;
    private String name;
    private BigDecimal price;
}