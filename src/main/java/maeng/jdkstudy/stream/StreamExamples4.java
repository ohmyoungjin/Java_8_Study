package maeng.jdkstudy.stream;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamExamples4 {

    public static void main(String[] args) {

        Product2 productA = new Product2(1L, "A", new BigDecimal("10.00"));
        Product2 productB = new Product2(2L, "B", new BigDecimal("55.50"));
        Product2 productC = new Product2(3L, "C", new BigDecimal("17.45"));
        Product2 productD = new Product2(4L, "D", new BigDecimal("20.00"));
        Product2 productE = new Product2(5L, "E", new BigDecimal("110.99"));
        final List<Product2> products = Arrays.asList(
                productA,
                productB,
                productC,
                productD,
                productE
        );

        System.out.println("Products.price >= 30 : " +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                        .collect(Collectors.toList())
        );

        System.out.println("Products.price >= 30 : (with joining) \n" +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                        .map(product -> product.toString())
                        .collect(Collectors.joining("\n"))
        );
        System.out.println("Total Price : " +
        products.stream()
                .map(product -> product.getPrice())
                .reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2))
        );

        System.out.println("Total Price : " +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                        .map(product -> product.getPrice())
                        .reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2))
        );

        System.out.println("Total Price :: add : " +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                        .map(product -> product.getPrice())
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        System.out.println("Total Count : " +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                        .count()
        );

        OrderedItem orderedItem1 = new OrderedItem(1L, productA, 2);
        OrderedItem orderedItem2 = new OrderedItem(2L, productB, 1);
        OrderedItem orderedItem3 = new OrderedItem(3L, productC, 10);

        final Order order = new Order(1L, Arrays.asList(
                orderedItem1,
                orderedItem2,
                orderedItem3
        ));
        System.out.println("order total Price : " + order.totalPrice());
    }
}

@Data
@AllArgsConstructor
class Product {
    private Long id;
    private String name;
    private BigDecimal price;
}

@AllArgsConstructor
@Data
class OrderedItem {
    private Long id;
    private Product2 product;
    private int quantity;

    //수량에 따른 총합
    public BigDecimal getTotalPrice() {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }
}

@Data
@AllArgsConstructor
class Order {
    private Long id;
    private List<OrderedItem> items;

    //주문에 대한 계산 값
    public BigDecimal totalPrice() {
        return items.stream()
                    .map(items -> items.getTotalPrice())
                    .reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2));
    }
}
