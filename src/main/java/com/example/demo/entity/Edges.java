package com.example.demo.entity;



import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Objects;

@TableName("edges")
public class Edges {
    private int id;
    private String src;
    private String dest;
    private Double cost;

    public Edges() {
    }

    public Edges(String src, String dest, Double cost) {
        this.src = src;
        this.dest = dest;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Edges{" +
                "src='" + src + '\'' +
                ", dest='" + dest + '\'' +
                ", cost=" + cost +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edges e) {
            return this.src.equals(e.src) && this.dest.equals(e.dest) && this.cost.equals(e.cost);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getSrc().hashCode() + getDest().hashCode() + getCost().hashCode();
    }

}
