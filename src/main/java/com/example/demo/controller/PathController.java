package com.example.demo.controller;

import com.example.demo.entity.Path;
import com.example.demo.mapper.PathMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PathController {

    @Autowired
    private PathMapper pathMapper;

    @GetMapping("/shortestPath")
    public List<Path> getPath() {
        List<Path> list = pathMapper.selectList(null);
        System.out.println(list);
        return list;
    }

    @PostMapping("/insertPath")
    public String insertPath(Path path) {
        int res = pathMapper.insert(path);
        if (res > 0) {
            return "Success";
        } else {
            return "Fail";
        }
    }
}
