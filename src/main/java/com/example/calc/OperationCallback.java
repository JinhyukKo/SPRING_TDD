package com.example.calc;

import java.io.BufferedReader;
import java.io.IOException;

public interface OperationCallback<T> {
    T doSomething(BufferedReader br) throws IOException;
}
