package com.example.domain;

public class DuplicateUsernameException extends RuntimeException {
     public DuplicateUsernameException(Throwable cause) {
         super(cause);
     }
}
