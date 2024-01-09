package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Edges;
import com.example.demo.entity.Node;
import com.example.demo.entity.Path;
import com.example.demo.mapper.EdgeMapper;
import com.example.demo.mapper.NodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

//Yen's K-shortest paths algorithm
@RestController
public class KShortestPathController {
    @Autowired
    private EdgeMapper edgeRepository;
    @Autowired
    private NodeMapper nodeRepository;


    // public List<PathResult> topFiveShortestPaths(@PathVariable String src, @PathVariable String dest) {





    @RequestMapping(value = "/topFiveShortestPaths", method = RequestMethod.GET)
    public List<Path> kShortestPaths(String src, String dest, int k) {
        Wrapper<Node> wrapper = new QueryWrapper<Node>().eq("status", "Available");
        List<Node> nodes = nodeRepository.selectList(wrapper);
        //Wrapper<Edges> wrapper1 = new QueryWrapper<Edges>().eq("src", "Available");
        List<Edges> allEdges = edgeRepository.selectList(null);
        Set<String> availableNodeNames = nodes.stream().map(Node::getName).collect(Collectors.toSet());
        List<Edges> edges = allEdges.stream()
                .filter(edge -> availableNodeNames.contains(edge.getSrc()) && availableNodeNames.contains(edge.getDest()))
                .toList();
        Map<String, Map<String, Double>> graph = buildGraph(edges);

        List<Path> result = new ArrayList<>();
        Set<Path> seenPaths = new HashSet<>(); // Introduced to keep track of seen paths
        PriorityQueue<Path> potentialPaths = new PriorityQueue<>();
        Path shortestPath = dijkstra(graph, src, dest);

        if (shortestPath != null) {
            result.add(shortestPath);
            seenPaths.add(shortestPath);
            potentialPaths.add(shortestPath);
        }

        while (result.size() < k && !potentialPaths.isEmpty()) {
            Path previousPath = potentialPaths.poll();
            for (int j = 0; j < previousPath.getNodes().size() - 1; j++) {
                String spurNode = previousPath.getNodes().get(j);
                List<String> rootPath = previousPath.getNodes().subList(0, j + 1);

                Map<String, Map<String, Double>> subgraph = new HashMap<>(graph);
                removeEdges(subgraph, result, rootPath);

                for (String node : rootPath.subList(0, j)) {
                    subgraph.remove(node);
                }

                Path spurPath = dijkstra(subgraph, spurNode, dest);
                if (spurPath != null) {
                    List<String> totalPath = new ArrayList<>(rootPath);
                    totalPath.addAll(spurPath.getNodes().subList(1, spurPath.getNodes().size()));

                    Path newPath = new Path(totalPath, computeCost(graph, totalPath));
                    if (!seenPaths.contains(newPath) && !potentialPaths.contains(newPath)) {
                        potentialPaths.add(newPath);
                    }
                }
            }

            if (!potentialPaths.isEmpty()) {
                Path nextShortest = potentialPaths.poll();
                result.add(nextShortest);
                seenPaths.add(nextShortest);  // Add to the seenPaths set
            }
        }

        return result;

    }
    private Map<String, Map<String, Double>> buildGraph(List<Edges> edgesList) {
        Map<String, Map<String, Double>> graph = new HashMap<>();

        for (Edges edge : edgesList) {
            graph
                    .computeIfAbsent(edge.getSrc(), k -> new HashMap<>())
                    .put(edge.getDest(), edge.getCost());
        }

        return graph;
    }
    private void removeEdges(Map<String, Map<String, Double>> graph, List<Path> result, List<String> rootPath) {
        String lastNodeInRootPath = rootPath.get(rootPath.size() - 1);

        for (Path path : result) {
            boolean matchesRoot = true;

            for (int i = 0; i < rootPath.size(); i++) {
                if (path.getNodes().size() <= i || !path.getNodes().get(i).equals(rootPath.get(i))) {
                    matchesRoot = false;
                    break;
                }
            }

            if (matchesRoot && path.getNodes().size() > rootPath.size()) {
                String nextNode = path.getNodes().get(rootPath.size());
                Map<String, Double> edges = graph.get(lastNodeInRootPath);
                if (edges != null) {
                    edges.remove(nextNode);
                }
            }
        }
    }


    private Path dijkstra(Map<String, Map<String, Double>> graph, String src, String dest) {
        Map<String, Double> distances = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>();
        PriorityQueue<String[]> pq = new PriorityQueue<>(Comparator.comparingDouble(o -> Double.parseDouble(o[1])));

        distances.put(src, 0.0);
        pq.add(new String[]{src, "0"});

        while (!pq.isEmpty()) {
            String[] current = pq.poll();
            String node = current[0];
            double dist = Double.parseDouble(current[1]);

            if (!graph.containsKey(node)) continue;

            for (String next : graph.get(node).keySet()) {
                double newDist = dist + graph.get(node).get(next) + getNodeCost(next);
                //double newDist = dist + graph.get(node).get(next);
                if (!distances.containsKey(next) || newDist < distances.get(next)) {
                    distances.put(next, newDist);
                    predecessors.put(next, node);
                    pq.add(new String[]{next, String.valueOf(newDist)});
                }
            }
        }

        if (!predecessors.containsKey(dest)) return null;

        List<String> path = new ArrayList<>();
        String current = dest;
        while (current != null && !current.equals(src)) {
            path.add(current);
            current = predecessors.get(current);
        }
        path.add(src);
        Collections.reverse(path);

        return new Path(path, distances.get(dest));
    }

    private double getNodeCost(String nodeName) {
        Wrapper<Node> wrapper = new QueryWrapper<Node>().eq("name", nodeName);
        Node node = nodeRepository.selectOne(wrapper);
        if (node == null) {
            throw new IllegalArgumentException("Node not found: " + nodeName);
        }
        return node.getCost();
    }

    private double computeCost(Map<String, Map<String, Double>> graph, List<String> path) {
        double cost = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            Map<String, Double> edges = graph.get(path.get(i));
            if (edges == null) {
                throw new IllegalArgumentException("Node not found in graph: " + path.get(i));
            }
            Double edgeCost = edges.get(path.get(i + 1));
            if (edgeCost == null) {
                throw new IllegalArgumentException("Edge not found in graph: " + path.get(i) + " -> " + path.get(i + 1));
            }
            cost += edgeCost + getNodeCost(path.get(i));
        }
        // Adding the cost of the last node in the path
        cost += getNodeCost(path.get(path.size() - 1));
        return cost;
    }


//    private double computeCost(Map<String, Map<String, Double>> graph, List<String> path) {
//        double cost = 0.0;
//        for (int i = 0; i < path.size() - 1; i++) {
//            Map<String, Double> edges = graph.get(path.get(i));
//            if (edges == null) {
//                throw new IllegalArgumentException("Node not found in graph: " + path.get(i));
//            }
//            Double edgeCost = edges.get(path.get(i + 1));
//            if (edgeCost == null) {
//                throw new IllegalArgumentException("Edge not found in graph: " + path.get(i) + " -> " + path.get(i + 1));
//            }
//            cost += edgeCost;
//        }
//        return cost;
//    }

//    private double computeCost(Map<String, Map<String, Double>> graph, List<String> path) {
//        double cost = 0.0;
//        for (int i = 0; i < path.size() - 1; i++) {
//            cost += graph.get(path.get(i)).get(path.get(i + 1));
//        }
//        return cost;
//    }
}

