package com.example.calc;

import java.io.BufferedReader;
import java.io.*;

public class Calculator {
    public Integer calcSum(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        int sum = 0;
        String line= null;
        while( (line = br.readLine()) != null ) {
            sum += Integer.parseInt(line);
        }
        br.close();
        return sum;
    }
}
