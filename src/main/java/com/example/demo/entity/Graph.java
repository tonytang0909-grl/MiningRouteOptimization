package com.example.demo.entity;

import java.util.*;

public class Graph {
    private Set<Node> nodes = new HashSet<>();
    private Set<Edges> edges = new HashSet<>();
    private Map<String, List<Edges>> adjacencyList = new HashMap<>();

    public Graph(List<Node> nodes, List<Edges> edges) {
        for (Node node : nodes) {
            this.nodes.add(node);
            adjacencyList.put(node.getName(), new ArrayList<>());
        }
        for (Edges edge : edges) {
            this.edges.add(edge);
            adjacencyList.get(edge.getSrc()).add(edge);
        }
    }

    public Graph() {
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public Set<Edges> getEdges() {
        return edges;
    }

    public void setEdges(Set<Edges> edges) {
        this.edges = edges;
    }

    public Map<String, List<Edges>> getAdjacencyList() {
        return adjacencyList;
    }



    public void addNode(Node node) {
        nodes.add(node);
        adjacencyList.put(node.getName(), new ArrayList<>());
    }

    public void addEdge(Edges edge) {
        edges.add(edge);
        adjacencyList.get(edge.getSrc()).add(edge);
    }



    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                ", edges=" + edges +
                ", adjacencyList=" + adjacencyList +
                '}';
    }
    // Other methods as required
}

