package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Edges;
import com.example.demo.entity.Node;
import com.example.demo.entity.Path;
import com.example.demo.mapper.EdgeMapper;
import com.example.demo.mapper.NodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/path")
public class DijkstraController {

    @Autowired
    private EdgeMapper edgeRepository;

    @Autowired
    private NodeMapper nodeRepository;

    @GetMapping("/shortestPath/{src}")
    public List<Path> shortestPath(@PathVariable String src) { // Make sure to annotate src with @PathVariable
        Wrapper<Node> wrapper = new QueryWrapper<Node>().eq("status", "Available");
        List<Node> nodes = nodeRepository.selectList(wrapper);
        //Wrapper<Edges> wrapper1 = new QueryWrapper<Edges>().eq("src", "Available");
        List<Edges> allEdges = edgeRepository.selectList(null);
        Set<String> availableNodeNames = nodes.stream().map(Node::getName).collect(Collectors.toSet());
        List<Edges> edges = allEdges.stream()
                .filter(edge -> availableNodeNames.contains(edge.getSrc()) && availableNodeNames.contains(edge.getDest()))
                .toList();

        Map<String, List<Edges>> adjacencyList = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>();
        Map<String, Double> distanceMap = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingDouble(distanceMap::get));

        // Initialize distances and priority queue
        for (Node node : nodes) {
            distanceMap.put(node.getName(), Double.POSITIVE_INFINITY);
            pq.add(node.getName());
        }

        if (distanceMap.containsKey(src)) {  // Ensure src is in distanceMap
            distanceMap.put(src, 0.0);
            pq.remove(src);
            pq.add(src);
        } else {
            return new ArrayList<>();  // Return an empty list if src is not in the graph
        }

        for (Edges edge : edges) {
            adjacencyList
                    .computeIfAbsent(edge.getSrc(), k -> new ArrayList<>())
                    .add(edge);
        }

        while (!pq.isEmpty()) {
            String currentNode = pq.poll();
            if (currentNode == null) continue; // Skip null entries
            List<Edges> neighbors = adjacencyList.getOrDefault(currentNode, new ArrayList<>());

            for (Edges edge : neighbors) {
                String neighborNode = edge.getDest();
                double currentDistance = Optional.ofNullable(distanceMap.get(currentNode)).orElse(Double.POSITIVE_INFINITY);
                double neighborDistance = Optional.ofNullable(distanceMap.get(neighborNode)).orElse(Double.POSITIVE_INFINITY);
                double newDist = currentDistance + edge.getCost();

                if (newDist < neighborDistance) {
                    pq.remove(neighborNode);
                    distanceMap.put(neighborNode, newDist);
                    pq.add(neighborNode);
                    predecessors.put(neighborNode, currentNode);
                }
            }
        }

        List<Path> paths = new ArrayList<>();
        for (Node node : nodes) {
            String targetNode = node.getName();
            if (!targetNode.equals(src)) {
                List<String> pathNodes = reconstructPath(src, targetNode, predecessors);
                paths.add(new Path(pathNodes, Optional.ofNullable(distanceMap.get(targetNode)).orElse(Double.POSITIVE_INFINITY)));
            }
        }

        Collections.sort(paths);
        return paths.size() <= 5 ? paths : paths.subList(0, 5);
    }

    private List<String> reconstructPath(String start, String end, Map<String, String> predecessors) {
        LinkedList<String> path = new LinkedList<>();
        for (String at = end; at != null; at = predecessors.get(at)) {
            path.addFirst(at);
        }
        return path;
    }
}

