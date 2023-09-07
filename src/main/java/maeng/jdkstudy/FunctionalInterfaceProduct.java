package maeng.jdkstudy;

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
        final List<Product> products = Arrays.asList(
                new Product(1L, "A", new BigDecimal("10.00")),
                new Product(2L, "B", new BigDecimal("55.50")),
                new Product(3L, "C", new BigDecimal("17.45")),
                new Product(4L, "D", new BigDecimal("20.00")),
                new Product(5L, "E", new BigDecimal("110.99"))
        );
        //** 람다로 사용하기 전 방식 **
        List<Product> result = new ArrayList<>(); // 결과 값 닮을 리스트 선언
        BigDecimal twenty = new BigDecimal("20"); // 분기 처리 값
        // for 문 사용 할 때 처럼 변수의 형을 선언해줘야 한다 int i = 0 같이 여기서는 Product product
        for (final Product product : products) {
            // products 의 리스트를 가지고 for each 로 사용하면서 n 번째 인수를 뜻하는 객체는 product 이다
            //if(product.getPrice().compareTo(new BigDecimal("20")) >=0 ) { // 내부에서 계속 BigDecimal 클레스가 생성 되므로 변수로 빼기 !
            if(product.getPrice().compareTo(twenty) >=0 ) {
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
                if (product.getPrice().compareTo(twenty) >=0) {
                    return true;
                }
                return false;
            }
        };
        //System.out.println("isAnonymous : " +filter(products, isAnonymous));
        List<Product> filterList  = filter(products, isAnonymous);
        //System.out.println("isAnonymous : " + filterList);
        List<Product> discountResult = new ArrayList<>(); // 결과 값 닮을 리스트 선언

        //50 달러 보다 비싼 제품 가져오기
        List<Product> expensiveProducts = filter(products, product -> product.getPrice().compareTo(new BigDecimal("50")) > 0);

        List<DiscountedProduct> discountedProducts = map(expensiveProducts,
                (Product product) -> new DiscountedProduct(product.getId(), product.getName(), product.getPrice().multiply(new BigDecimal("0.5"))));
        System.out.println("expensive products :  " + expensiveProducts);
        System.out.println("discountedProducts :  " + discountedProducts);
        Predicate<DiscountedProduct> lessThanOrEqualTo30 = discountedProduct -> discountedProduct.getPrice().compareTo(new BigDecimal("30")) <= 0;
        System.out.println("discounted products (<= $30)" +
            filter(discountedProducts, lessThanOrEqualTo30)
        );

        System.out.println("discounted products (<= $30)" +
            filter(products, (Product)lessThanOrEqualTo30)
        );
    }
    //처음에 이렇게 선언했음 .. 여기서는 어떤 형을 받을지 모르기 때문에
    //제네릭 메서드로 선언을 해줘야 함
    //이렇게 쓰려면 class 자체에 제네릭 클레스로 선언이 되어야 한다.
    //private List<T> filter(List<T> list, Predicate<T> isPositive) {
    //변경 후 => 제네릭 메소드의 <T> 이 부분은 지역함수 개념으로 이해 하면 된다. 함수가 호출 되면서 어떤 값을 반환 할 것인지 정해줄 수 있다.
    //짧막 팁 => static 의 제네릭 타입과 class 에 선언된 제네릭 타입은 같지 않다 !
    //스테틱이 먼저 메모리에 적재되기 때문에 인스턴스 변수와 스테틱 변수는 공유하지 않는다.
    private static <T> List<T> filter(List<T> list, Predicate<T> isPositive) {
        //함수 내부에서 반환 해줄 List 생성
        List<T> result = new ArrayList<>();
        //내가 받아서 사용할 list 자체가 제네릭 타입으로 넘어왔기 때문에 밑에 for each 에 사용할 list 도 선언을 제네릭 타입으로 해준다 ***
        for (T listValue : list) {
            //분기 처리 할 부분 람다를 사용해야 하므로 어떤 람다 분기처리식을 받을지 Predicate<T> isPositive 이 부분으로 파라미터로 받아준다
            if(isPositive.test(listValue)) {
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
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class Product {
    private Long id;
    private String name;
    private BigDecimal price;

}

@ToString(callSuper = true)
class DiscountedProduct extends Product {
    public DiscountedProduct(Long id, String name, BigDecimal price) {
        super(id, name, price);
    }
}

