package com.example.domain;

public class User{
    private int id;
    private String username;
    private String password;
    Level level;
    int login;
    int recommend;
    private String email;

    public User(int id,String username, String password, Level level, int login, int recommend, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
        this.email= email;
    }

    public User() {
    }
    @Override
    public boolean equals(Object o) {
        return this.username.equals(((User)o).username)
                && this.password.equals(((User)o).password)
                &&  this.level.getValue() == ((User)o).level.getValue()
                && this.login == ((User)o).login
                && this.recommend == ((User)o).recommend
                && this.id == ((User)o).id
                && this.email.equals(((User)o).email);

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }
    public void upgradeLevel(){
        Level nextLevel = this.level.nextLevel();
        if(nextLevel != null){
            this.level=nextLevel;
        } else{
            throw new IllegalArgumentException(this.level + "is not upgradable");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}