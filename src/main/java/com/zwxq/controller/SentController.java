package com.zwxq.controller;


import com.zwxq.utils.ExcelUtil;
import com.zwxq.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sent")
public class SentController {

    //文档存储位置
    @Value("${excel.uploadLabelDown}")
    private String excelUploadDown;



    @RequestMapping("/sentMessage")
    public String sentMessage(){

        return "sentMessage";
    }

    @RequestMapping("/pastMessage")
    public String pastMessage(){

        return "pastMessage";
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public String pastMessage(MultipartFile file){
        //下载文件 将文件保存到服务器上
        String path = excelUploadDown; // 以后配置到配置文件中
        if(!FileUtil.isExist(path)) {
            try {
                FileUtil.makeDir(path);
                String realPath = FileUtil.uploadFile(file, path);
                File fileUpload = new File(path + "/" + realPath);
                List<Map<String, Object>> exportListFromExcel = ExcelUtil.exportListFromExcel(fileUpload, 0);
                System.out.println(exportListFromExcel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "true";
    }


}
