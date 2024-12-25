package domain;

import com.example.calc.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;

public class SumTest {
    Calculator calculator;
    String filepath1;
    String filepath2;
    @BeforeEach
    public void setup(){
        calculator = new Calculator();
        filepath1 = getClass().getResource("numbers.txt").getPath();
        filepath2 = getClass().getResource("numbers2.txt").getPath();


    }
    @Test
    public void testSum() throws IOException {

        int sum = calculator.calcSum(filepath1);
        int sum2 = calculator.calcSum(filepath2);

        assert sum  == 15;
        assert sum2 == 21;
    }
    @Test
    public void testTemplate() throws IOException {

        int value = calculator.fileReadTemplate(filepath1,(BufferedReader br)->3);
        assert value == 3;
    }
    @Test
    public void testConcat() throws IOException {
        String value = calculator.concat(filepath2);
        assert value.equals("123456");
    }
    @Test
    public void testMultiply() throws IOException {
        int value = calculator.calcMul(filepath1);
        assert value == 2*3*4*5;
    }
}
