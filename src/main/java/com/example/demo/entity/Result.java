package com.example.demo.entity;

import java.util.List;

public class Result {
    private static int lastId = 0;  // static field to track the last assigned ID

    private int id;
    private List<Path> paths;
    private double cost;

    public Result() {
        this.id = ++lastId;  // increment the lastId and assign it to the current instance's id
    }

    public Result(List<Path> paths) {
        this();  // call the default constructor to assign the ID
        this.paths = paths;
        this.cost = 0.0;
    }

    public Result(List<Path> paths, double cost) {
        this();  // call the default constructor to assign the ID
        this.paths = paths;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", paths=" + paths +
                ", cost=" + cost +
                '}';
    }
}

