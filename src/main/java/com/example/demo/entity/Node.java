package com.example.demo.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.ArrayList;
import java.util.List;


@TableName("nodes")
public class Node {

//    @TableField("node_id")

    private int id;
    private String name;

    private String status;
    private String type;

    private double cost;

    //private List<Node> neighbors;
    public Node() {
    }

    public Node(String name) {
        this.name = name;
        this.status = "available";
        this.type = "normal";
        this.cost = 0.0;
        //this.neighbors = new ArrayList<>();
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

//    public List<Node> getNeighbors() {
//        return neighbors;
//    }

//    public void setNeighbors(List<Node> neighbors) {
//        this.neighbors = neighbors;
//    }


    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", cost=" + cost +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
