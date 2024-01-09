package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Edges;
import com.example.demo.entity.Node;
import com.example.demo.mapper.EdgeMapper;
import com.example.demo.mapper.NodeMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Wrapper;

@RestController
@CrossOrigin
public class FileUploadController {
    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private EdgeMapper edgeMapper;

    @PostMapping("/uploadFile")
    public String uploadFile(MultipartFile file, HttpServletRequest request){
        System.out.println(file.getContentType());
        System.out.println(file.getOriginalFilename());
        String path = request.getServletContext().getRealPath("/upload/");
        System.out.println(path);
        try {
            saveFile(file, path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(path + file.getOriginalFilename());
        return writeToDB(path + file.getOriginalFilename());
        //return "Success";
    }

    private void saveFile(MultipartFile file, String path) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File saveFile = new File(path + file.getOriginalFilename());
        file.transferTo(saveFile);
    }

    private String writeToDB(String Path) {
        try {
            FileInputStream fis = new FileInputStream(new File(Path));
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            //adding nodes
            int i = 1;
            while (sheet.getRow(i)!= null) {
            //for (int i = 1; i < sheet.getLastRowNum(); i++) {
            //for (int i = 8; i <= 9; i++) {
                String name = sheet.getRow(i).getCell(0).getStringCellValue();
                boolean isSource = sheet.getRow(i).getCell(1).getBooleanCellValue();
                boolean isDest = sheet.getRow(i).getCell(2).getBooleanCellValue();
                String status = sheet.getRow(i).getCell(4).getStringCellValue();
                Node node = new Node();
                node.setName(name);
                if (isSource) {
                    node.setType("Source");
                    node.setCost(0.0);
                } else if (isDest) {
                    node.setType("Destination");
                    node.setCost(0.0);
                } else {
                    node.setType("Normal");
                }
                node.setStatus(status);
                QueryWrapper<Node> wrapper = new QueryWrapper<>();
                wrapper.eq("name", node.getName());
                if (nodeMapper.selectList(wrapper).isEmpty()) {
                    nodeMapper.insert(node);
                }
                i++;
            }
            //adding edges
            i = 1;
            while (sheet.getRow(i) != null) {
                //for (int i = 1; i < sheet.getLastRowNum(); i++) {
                String name = sheet.getRow(i).getCell(0).getStringCellValue();
                Edges edges = new Edges();

                if (sheet.getRow(i).getCell(3) != null) {
                    String dest = sheet.getRow(i).getCell(3).getStringCellValue();
//                if (sheet.getRow(i).getCell(6) == null) {
//                    continue;
//                }
                    double cost = sheet.getRow(i).getCell(5).getNumericCellValue();
                    //source to name
                    edges.setSrc(name);
                    edges.setDest(dest);
                    edges.setCost(cost);
                    edgeMapper.insert(edges);
                    i++;
//                    QueryWrapper<Edges> wrapper = new QueryWrapper<>();
//                    wrapper.eq("src", source);
//                    if (edgeMapper.selectList(wrapper).isEmpty()) {
//
//                    }
                }
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "Success";
    }
}
