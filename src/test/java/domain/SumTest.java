package domain;

import com.example.calc.Calculator;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SumTest {
    @Test
    public void testSum() throws IOException {
        Calculator calculator = new Calculator();
        int sum = calculator.calcSum(getClass().getResource("numbers.txt").getPath());
        assert sum  == 15;
    }
}
