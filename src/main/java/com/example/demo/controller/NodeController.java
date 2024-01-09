package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Node;
import com.example.demo.mapper.NodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class NodeController {

    @Autowired
    private NodeMapper nodeMapper;
    @GetMapping("/node")
    public List<Node> node() {
        //Wrapper<Node> wrapper = new QueryWrapper<Node>().eq("status", "Available");
        List<Node> list = nodeMapper.selectList(null);
        System.out.println(list);
        return list;
    }

    @PostMapping("/insertNode")
    public String insertNode(Node node) {
        if (node.getType().equalsIgnoreCase("source") || node.getType().equalsIgnoreCase("destination")) {
            node.setCost(0.0);
        }
        int res = nodeMapper.insert(node);
        if (res > 0) {
            return "Success";
        } else {
            return "Fail";
        }
    }

    @GetMapping("/deleteNode")
    public String deleteNode(String name) {
        nodeMapper.deleteByName(name);
        return "Success";
    }

    @PostMapping ("/updateNode")
    public String updateNode(Node node) {
        System.out.println(node.getName());
        System.out.println(node.getStatus());
        nodeMapper.updateStatus(node);

        return "Success";
    }
}
