package maeng.jdkstudy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CalculatorServiceTest {

    @Test
    void calculatorAddition() {
        Calculation calculation = new Addition();

        int actual = calculation.calculate(1, 1);

        assertThat(actual).isEqualTo(2);
    }

    @Test
    void calculatorSubtraction() {
        Calculation calculation = new Subtraction();

        int actual = calculation.calculate(1, 1);

        assertThat(actual).isEqualTo(0);
    }

    @Test
    void calculatorMultiplication() {
        Calculation calculation = new Multiplication();

        int actual = calculation.calculate(1, 1);

        assertThat(actual).isEqualTo(1);
    }

    @Test
    void calculatorDivision() {
        Calculation calculation = new Division();

        int actual = calculation.calculate(8, 4);

        assertThat(actual).isEqualTo(2);
    }
}