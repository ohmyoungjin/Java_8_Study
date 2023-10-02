package maeng.jdkstudy.functionalinterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionalInterfaceProduct {
    public static void main(String[] args) {
        Product productA = new Product(1L, "A", new BigDecimal("10.00"));
        Product productB = new Product(2L, "B", new BigDecimal("55.50"));
        Product productC = new Product(3L, "C", new BigDecimal("17.45"));
        Product productD = new Product(4L, "D", new BigDecimal("20.00"));
        Product productE = new Product(5L, "E", new BigDecimal("110.99"));
        final List<Product> products = Arrays.asList(
                productA,
                productB,
                productC,
                productD,
                productE
        );
        //** 람다로 사용하기 전 방식 **
        List<Product> result = new ArrayList<>(); // 결과 값 닮을 리스트 선언
        BigDecimal twenty = new BigDecimal("20"); // 분기 처리 값
        // for 문 사용 할 때 처럼 변수의 형을 선언해줘야 한다 int i = 0 같이 여기서는 Product product
        for (final Product product : products) {
            // products 의 리스트를 가지고 for each 로 사용하면서 n 번째 인수를 뜻하는 객체는 product 이다
            //if(product.getPrice().compareTo(new BigDecimal("20")) >=0 ) { // 내부에서 계속 BigDecimal 클레스가 생성 되므로 변수로 빼기 !
            if (product.getPrice().compareTo(twenty) >= 0) {
                result.add(product);
            }
        }
        //System.out.println(result);;
        // ** 람다 사용식 및 소스 리펙토링**
        //Predicate true false 를 반환 해주는 Functional Interface
        //람다 이해하기 => 처음 부분은 매개 변수를 사용해주면 된다. -> 이거 이후에 오는건 내가 어떤 로직을 실행하는지 override 하는 부분이라고 생각하면 된다.
        //리턴은 그 자체로 선언된 isPositive 이부분이 되는거다 !
        //이렇게 변수로 빼서 써도 되지만 조금더 간결? 하게 쓰는 방법
        Predicate<Product> isPositive = (product -> product.getPrice().compareTo(twenty) >= 0);
        //System.out.println("lambda : " +filter(products, isPositive));
        //System.out.println("lambda : " +filter(products, (product -> product.getPrice().compareTo(twenty) >= 0)));
        //익명 클레스로 선언할 경우 = >
        Predicate<Product> isAnonymous = new Predicate<Product>() {
            @Override
            public boolean test(Product product) {
                if (product.getPrice().compareTo(twenty) >= 0) {
                    return true;
                }
                return false;
            }
        };
        //System.out.println("isAnonymous : " +filter(products, isAnonymous));
        List<Product> filterList = filter(products, isAnonymous);
        //System.out.println("isAnonymous : " + filterList);
        List<Product> discountResult = new ArrayList<>(); // 결과 값 닮을 리스트 선언

        //50 달러 보다 비싼 제품 가져오기
        List<Product> expensiveProducts = filter(products, product -> product.getPrice().compareTo(new BigDecimal("50")) > 0);

        List<DiscountedProduct> discountedProducts = map(expensiveProducts,
                (product) -> new DiscountedProduct(product.getId(), product.getName(), product.getPrice().multiply(new BigDecimal("0.5"))));
        System.out.println("expensive products :  " + expensiveProducts);
        System.out.println("discountedProducts :  " + discountedProducts);
        Predicate<Product> lessThanOrEqualTo30 = discountedProduct -> discountedProduct.getPrice().compareTo(new BigDecimal("30")) <= 0;
        System.out.println("discounted products (<= $30)" +
                filter(discountedProducts, lessThanOrEqualTo30)
        );

        System.out.println("discounted products (<= $30)" +
                filter(products, lessThanOrEqualTo30)
        );

        final List<BigDecimal> prices = map(products, product -> product.getPrice());
        BigDecimal total = BigDecimal.ZERO;

        for (final BigDecimal price : prices) {
            total = total.add(price);
        }
        System.out.println("total : " + total);

        BigDecimal newTotal = total(products, product -> product.getPrice());
        System.out.println("newTotal : " + newTotal);

        BigDecimal discountProductTotal = total(discountedProducts, discountedProduct -> discountedProduct.getPrice());
        System.out.println("discountProductTotal : " + discountProductTotal);

        Order order = new Order(1L, "on-1234", Arrays.asList(
                new OrderedItem(1L, productA, 2),
                new OrderedItem(2L, productB, 1),
                new OrderedItem(3L, productC, 10)
        ));
        System.out.println("order total : " + order.totalPrice());
    }


    //처음에 이렇게 선언했음 .. 여기서는 어떤 형을 받을지 모르기 때문에
    //제네릭 메서드로 선언을 해줘야 함
    //이렇게 쓰려면 class 자체에 제네릭 클레스로 선언이 되어야 한다.
    //private List<T> filter(List<T> list, Predicate<T> isPositive) {
    //변경 후 => 제네릭 메소드의 <T> 이 부분은 지역함수 개념으로 이해 하면 된다. 함수가 호출 되면서 어떤 값을 반환 할 것인지 정해줄 수 있다.
    //짧막 팁 => static 의 제네릭 타입과 class 에 선언된 제네릭 타입은 같지 않다 !
    //스테틱이 먼저 메모리에 적재되기 때문에 인스턴스 변수와 스테틱 변수는 공유하지 않는다.
    //와일드카드 사용 => 와일드 카드 사용으로 변경하지 않으면 제네릭 타입에서 선언된 변수로만 사용이 가능하므로
    //자바의 다형성에 위배가 된다 이를 수정 하기 위해 super 및 extends 로 변경해서 다형성을 사용하게 끔 변경해줌 ! 중요하다 **
    private static <T> List<T> filter(List<T> list, Predicate<? super T> isPositive) {
        //함수 내부에서 반환 해줄 List 생성
        List<T> result = new ArrayList<>();
        //내가 받아서 사용할 list 자체가 제네릭 타입으로 넘어왔기 때문에 밑에 for each 에 사용할 list 도 선언을 제네릭 타입으로 해준다 ***
        for (T listValue : list) {
            //분기 처리 할 부분 람다를 사용해야 하므로 어떤 람다 분기처리식을 받을지 Predicate<T> isPositive 이 부분으로 파라미터로 받아준다
            if (isPositive.test(listValue)) {
                result.add(listValue);
            }
        }
        return result;
    }

    private static <T, R> List<R> map(List<T> discountList, Function<T, R> discount) {
        List<R> result = new ArrayList<>();
        for (final T t : discountList) {
            result.add(discount.apply(t));
        }
        return result;
    }

    private static <T> BigDecimal total(List<T> list, Function<T, BigDecimal> mapper) {
        BigDecimal total = BigDecimal.ZERO;
        for (final T t : list) {
            total = total.add(mapper.apply(t));
        }
        return total;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class Product {
        private Long id;
        private String name;
        private BigDecimal price;

    }

    @ToString(callSuper = true)
    static class DiscountedProduct extends Product {
        public DiscountedProduct(Long id, String name, BigDecimal price) {
            super(id, name, price);
        }
    }

    @AllArgsConstructor
    @Data
    static class OrderedItem {
        private Long id;
        private Product product;
        private int quantity;

        public BigDecimal getItemTotal() {
            return product.getPrice().multiply(new BigDecimal(quantity));
        }
    }

    @Data
    @AllArgsConstructor
    static class Order {
        private Long id;
        private String orderNumber;
        private List<OrderedItem> items;

        public BigDecimal totalPrice() {
            return total(items, item -> item.getItemTotal());
        }
    }

}


