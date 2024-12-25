package com.example.calc;

import java.io.BufferedReader;
import java.io.*;

public class Calculator {


    public Integer calcSum(String filePath) throws IOException {
        return fileReadTemplate(filePath, (BufferedReader br) -> {
            int sum = 0;
            String line = null;
            while ((line = br.readLine()) != null) {
                sum += Integer.parseInt(line);
            }
            return sum;
        });
    }
    public Integer calcMul(String filePath) throws IOException {
        return fileReadTemplate(filePath, (BufferedReader br) -> {
            int sum = 1;
            String line = null;
            while ((line = br.readLine()) != null) {
                sum *= Integer.parseInt(line);
            }
            return sum;
        });
    }
    public String concat(String filePath) throws  IOException{
        return fileReadTemplate(filePath, (BufferedReader br)->{
            String line = "";
            String newline = "";
            while ((newline=br.readLine()) != null) {
                line += newline;
            }
            return line;
        });
    }

    public <T> T fileReadTemplate(String filePath, OperationCallback<T> callback) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            return callback.doSomething(br);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
