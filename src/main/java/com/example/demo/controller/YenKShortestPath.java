package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.*;
import com.example.demo.mapper.EdgeMapper;
import com.example.demo.mapper.NodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class YenKShortestPath {
    @Autowired
    private EdgeMapper edgeRepository;
    @Autowired
    private NodeMapper nodeRepository;

    @RequestMapping(value = "/oneOnOneShortPath", method = RequestMethod.GET)
    public List<Path> oneOnOneShortestPath(String src, String dest, int k) {
        //Wrapper<Node> wrapper = new QueryWrapper<Node>().eq("status",  "Available");
        Wrapper<Node> wrapper = new QueryWrapper<Node>().like("LOWER(status)", "available");
        List<Node> nodes = nodeRepository.selectList(wrapper);
        HashSet<String> availableNodes = new HashSet<>();
        for (Node node : nodes) {
            availableNodes.add(node.getName());
        }
        //Wrapper<Edges> wrapper1 = new QueryWrapper<Edges>().eq("src", "Available").eq("dest", "Available");
        Wrapper<Edges> wrapper1 = new QueryWrapper<Edges>()
                .in("src", availableNodes)
                .in("dest", availableNodes);
        List<Edges> allFullEdges = edgeRepository.selectList(null);
        List<Edges> allEdges = allFullEdges.stream()
                .filter(edge -> availableNodes.contains(edge.getSrc()) && availableNodes.contains(edge.getDest()))
                .collect(Collectors.toList());
        Graph graph = constructGraph(nodes, allEdges);
        List<Path> paths = new ArrayList<>();
        List<String> path = new ArrayList<>();
        dfs(graph, src, dest, path, paths, 0);
        PriorityQueue<Path> pq = new PriorityQueue<>(Comparator.comparingDouble(Path::getCost));
        pq.addAll(paths);
        List<Path> res = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (pq.isEmpty()) {
                break;
            }
            res.add(pq.poll());
        }
        return res;
    }

    @RequestMapping(value = "/shortPaths", method = RequestMethod.POST)
    public List<Result> getTopKShortestSubResults(@RequestParam String source, @RequestParam List<String> destinations,@RequestParam int k) {
        Wrapper<Node> wrapper = new QueryWrapper<Node>().eq("status", "Available");
        List<Node> nodes = nodeRepository.selectList(wrapper);
        HashSet<String> availableNodes = new HashSet<>();
        for (Node node : nodes) {
            availableNodes.add(node.getName());
        }
        //Wrapper<Edges> wrapper1 = new QueryWrapper<Edges>().eq("src", "Available");
        List<Edges> allFullEdges = edgeRepository.selectList(null);
        List<Edges> allEdges = allFullEdges.stream()
                .filter(edge -> availableNodes.contains(edge.getSrc()) && availableNodes.contains(edge.getDest()))
                .collect(Collectors.toList());
        Graph graph = constructGraph(nodes, allEdges);
        List<List<Path>> allPaths = new ArrayList<>();
        //destinations.remove(destinations.size() - 1);
        for (String dest : destinations) {
            if (dest == null || dest.isEmpty()) {
                continue;
            }
            List<Path> paths = new ArrayList<>();
            List<String> path = new ArrayList<>();
            dfs(graph, source, dest, path, paths, 0);
            allPaths.add(paths);
        }
        //print all the paths in allPaths
//        for (List<Path> paths : allPaths) {
//            for (Path p : paths) {
//                System.out.println(p);
//            }
//        }
        PriorityQueue<Result> pq = new PriorityQueue<>(Comparator.comparingDouble(Result::getCost));
        permutation(allPaths, 0, new ArrayList<>(), pq);
        List<Result> res = new ArrayList<>();
        for (int i = 0; i <  k; i++) {
            if (pq.isEmpty()) {
                break;
            }
            res.add(pq.poll());
        }
        return res;
    }

    private double calculateCost(List<Path> paths) {
        Set<String> seenNodes = new HashSet<>();
        Set<Edges> seenEdges = new HashSet<>();
        double cost = 0.0;
        for (Path path : paths) {
            List<String> nodes = path.getNodes();
            //calculate node cost
            for (String node : nodes) {
                if (!seenNodes.contains(node)) {
                    cost += nodeRepository.selectList(new QueryWrapper<Node>().eq("name", node)).get(0).getCost();
                    seenNodes.add(node);
                }
            }
            //calculate edge cost
            for (int i = 0; i < nodes.size() - 1; i++) {
                String src = nodes.get(i);
                String dest = nodes.get(i + 1);
                Edges edge = edgeRepository.selectList(new QueryWrapper<Edges>().eq("src", src).eq("dest", dest)).get(0);
                if (!seenEdges.contains(edge)) {
                    seenEdges.add(edge);
                    cost += edge.getCost();
                }
            }
        }
        return cost;
    }

    private void permutation(List<List<Path>> allPaths, int level, List<Path> path, PriorityQueue<Result> pq) {
        if (level == allPaths.size()) {
            double cost = calculateCost(path);
            pq.add(new Result(new ArrayList<>(path), cost));
            return;
        }
        for (int i = 0; i < allPaths.get(level).size(); i++) {
            path.add(allPaths.get(level).get(i));
            permutation(allPaths, level + 1, path, pq);
            path.remove(path.size() - 1);
        }
    }

    private Graph constructGraph(List<Node> nodes, List<Edges> edges) {
        Graph graph = new Graph();
        for (Node node : nodes) {
            graph.addNode(node);
        }
        for (Edges edge : edges) {
            graph.addEdge(edge);
        }
        return graph;
    }
    public void dfs(Graph graph, String start, String end, List<String> path,List<Path> paths, double cost) {
        if (start.equals(end)) {
            path.add(end);
            Path temp = new Path();
            temp.setNodes(new ArrayList<>(path));
            temp.setCost(cost);
            paths.add(temp);
            path.remove(path.size() - 1);
            return;
        }
        if (path.contains(start)) {
            return;
        }
        path.add(start);
        //cost += start.getCost();
        double curNodeCost = nodeRepository.selectList(new QueryWrapper<Node>().eq("name", start)).get(0).getCost();
        List<Edges> neighbors = graph.getAdjacencyList().get(start);
        for (Edges e : neighbors) {
            dfs(graph, e.getDest(), end, path, paths, cost + e.getCost() + curNodeCost);
        }
        path.remove(path.size() - 1);
    }


    public static void main(String[] args) {
        YenKShortestPath test = new YenKShortestPath();
        Graph graph = new Graph();
        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");
        Node E = new Node("E");
        Node X = new Node("X");
        Node Y = new Node("Y");
        Node Z = new Node("Z");
        for (int i = 0; i < 5; i++) {
            graph.addNode(new Node((char)(i + 65) + ""));
        }
        graph.addNode(new Node("X"));
        graph.addNode(new Node("Y"));
        graph.addNode(new Node("Z"));
        graph.addEdge(new Edges("A", "B", 1.0));
        graph.addEdge(new Edges("A", "C", 1.0));
        graph.addEdge(new Edges("C", "B", 1.0));
        graph.addEdge(new Edges("B", "E", 1.0));
        graph.addEdge(new Edges("C", "D", 1.0));
        graph.addEdge(new Edges("C", "Z", 1.0));
        graph.addEdge(new Edges("D", "Y", 1.0));
        graph.addEdge(new Edges("E", "D", 1.0));
        graph.addEdge(new Edges("E", "X", 1.0));

        //System.out.println(graph.getAdjacencyList());
        YenKShortestPath test1 = new YenKShortestPath();
        List<Path> paths = new ArrayList<>();
        List<String> path = new ArrayList<>();
        test1.dfs(graph, "A", "Z", path, paths, 0);
        System.out.println(paths);


        //Path dijkstra = test.dijkstra(graph, "A", "G");
        //System.out.println(dijkstra);
//        List<Path> paths = test.yenKShortestPaths(graph, "A", "G", 3);
//        for (Path p : paths) {
//            System.out.println(p);
//        }
//        List<String> path = new ArrayList<>();
//        PriorityQueue<Path> paths = new PriorityQueue<>();
//        test.dfs(graph, "A", "G", path, paths, 0);
//        while (!paths.isEmpty()) {
//            System.out.println(paths.poll());
//        }
    }
}
