package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Node;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NodeMapper extends BaseMapper<Node> {
    @Delete("DELETE FROM nodes WHERE name = #{name}")
    public int deleteByName(String name);

    @Update("UPDATE nodes SET status = #{status}, cost = #{cost} WHERE name = #{name}")
    public int updateStatus(Node node);
    //get all current nodes
//    @Select("SELECT * FROM nodes")
//    public List<Node> getAllNodes();
//
//    @Insert("INSERT INTO nodes (node_id, node_name) VALUES (#{node_id}, #{node_name})")
//    public int insertNode(Node node);
}
