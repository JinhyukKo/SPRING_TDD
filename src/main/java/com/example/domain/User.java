package com.example.domain;

public class User{
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }
    @Override
    public boolean equals(Object o) {
        return this.username.equals(((User)o).username) && this.password.equals(((User)o).password);
    }


    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }
}