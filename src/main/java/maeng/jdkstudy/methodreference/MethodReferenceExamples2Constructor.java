package maeng.jdkstudy.methodreference;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.function.Function;

public class MethodReferenceExamples2Constructor {

    public static void main(String[] args) {
        final Section section1 = new Section(1);

        final Function<Integer, Section> sectionFactoryWithLambdaExpression = number -> new Section(number);
        final Section section1WithLambdaExpression = sectionFactoryWithLambdaExpression.apply(1);

        //생성자를 MethodReference 방법으로 만들기
        final Function<Integer, Section> sectionFactoryWithMethodReference = Section::new; // 여기에서  instance 화 되지 않음 ** 밑의 부분에서 instance 화 시켜줘야 함
        Section section1WithMethodReference = sectionFactoryWithMethodReference.apply(1);

        System.out.println(section1);
        System.out.println(section1WithLambdaExpression);
        System.out.println(section1WithMethodReference);

        System.out.println("===========================================\n");

        final OldProduct product = new OldProduct(1L, "A", new BigDecimal("100"));
        System.out.println(product);

        final OldProductCreator productCreator = OldProduct::new;
        System.out.println(productCreator.create(2L, "B", new BigDecimal("200")));

        ProductA a = createProduct(1L, "A", new BigDecimal("123"), ProductA::new);
        ProductB b = createProduct(1L, "A", new BigDecimal("123"), ProductB::new);

    }

    private static <T extends Product> T createProduct(final Long id,
                                                final String name,
                                                final BigDecimal price,
                                                final ProductCreator<T> productCreator) {
        if (id == null || id < 1L) {
            throw new IllegalArgumentException("The id must be a positive Long.");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("The name is not given.");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The price must be greater then 0.");
        }

        return productCreator.create(id, name, price);
    }
}

@FunctionalInterface
interface ProductCreator<T extends Product> {
    T create(Long id, String name, BigDecimal price);
}

@FunctionalInterface
interface OldProductCreator {
    OldProduct create(Long id, String name, BigDecimal price);
}

@AllArgsConstructor
@Data
class Section {
    private int number;
}

@AllArgsConstructor
@Data
abstract class Product {
    private Long id;
    private String name;
    private BigDecimal price;
}

@AllArgsConstructor
@Data
class OldProduct {
    private Long id;
    private String name;
    private BigDecimal price;
}

class ProductA extends Product{
    public ProductA(Long id, String name, BigDecimal price) {
        super(id, name, price);
    }

    @Override
    public String toString() {
        return "A=" + super.toString();
    }
}

class ProductB extends Product{
    public ProductB(Long id, String name, BigDecimal price) {
        super(id, name, price);
    }

    @Override
    public String toString() {
        return "B=" + super.toString();
    }
}
