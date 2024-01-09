package com.example.demo.entity;

import com.example.demo.controller.YenKShortestPath;

import java.util.ArrayList;
import java.util.List;

public class Path implements Comparable<Path> {
    private List<String> nodes;
    private double cost;

    public Path(List<String> nodes, double cost) {
        this.nodes = nodes;
        this.cost = cost;
    }

    public Path() {
        this.nodes = new ArrayList<>();
        this.cost = 0.0;
    }

    public Path(Path prevPath) {
        this.nodes = new ArrayList<>(prevPath.nodes);
        this.cost = prevPath.cost;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    void addNode(String node, double weight) {
        this.nodes.add(node);
        this.cost += weight;
    }

    @Override
    public int compareTo(Path o) {
        return Double.compare(this.cost, o.cost);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Path p) {
            //check if the size of the two paths are the same
            if (this.nodes.size() != p.nodes.size()) {
                return false;
            }
            //check if the nodes in the two paths are the same
            for (int i = 0; i < this.nodes.size(); i++) {
                if (!this.nodes.get(i).equals(p.nodes.get(i))) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Path{" +
                "nodes=" + nodes +
                ", cost=" + cost +
                '}';
    }
}
