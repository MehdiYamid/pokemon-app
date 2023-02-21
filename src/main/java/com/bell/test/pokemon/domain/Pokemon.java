package com.bell.test.pokemon.domain;

public class Pokemon {

    private String name;
    private double height;
    private double weight;
    private int hp = 20;
    private String url;

    public Pokemon() {}

    public Pokemon(String name, double height, double weight) {
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void decreaseHp(int hp) {
        this.hp = this.hp - hp;
    }
}
