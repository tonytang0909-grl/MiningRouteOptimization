package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Edges;
import com.example.demo.mapper.EdgeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class EdgesController {
    @Autowired
    private EdgeMapper edgeMapper;

    @GetMapping("/edges")
    public List<Edges> getEdges() {
        List<Edges> list = edgeMapper.selectList(null);
        System.out.println(list);
        return list;
    }

    @PostMapping("/insertEdge")
    public String insertEdge(Edges edge) {
        //if edge already exist, return fail
        List<Edges> curEdge = edgeMapper.selectList(new QueryWrapper<Edges>().eq("src", edge.getSrc()).eq("dest", edge.getDest()));
        if (!curEdge.isEmpty()) {
            System.out.println("Edge already exist");
            return "Fail";
        }
//        List<Edges> list = edgeMapper.selectList(null);
//        for (Edges e : list) {
//            if (e.equals(edge)) {
//                System.out.println("Edge already exist");
//                return "Fail";
//            }
//        }
        int res = edgeMapper.insert(edge);
        if (res > 0) {
            return "Success";
        } else {
            return "Fail";
        }
    }

    @RequestMapping(value = "/updateEdge", method = RequestMethod.POST)
    public String updateEdge(String source, String destination, double cost) {
        //update the edge cost
        Edges edge = new Edges(source, destination, cost);
        int res = edgeMapper.update(edge, new QueryWrapper<Edges>().eq("src", source).eq("dest", destination));
        if (res > 0) {
            return "Success";
        } else {
            return "Fail";
        }
    }

    @RequestMapping(value = "/deleteEdge", method = RequestMethod.POST)
    public String deleteEdge(String source, String destination) {
        //delete the edge
        int res = edgeMapper.delete(new QueryWrapper<Edges>().eq("src", source).eq("dest", destination));
        if (res > 0) {
            return "Success";
        } else {
            return "Fail";
        }
    }
}
