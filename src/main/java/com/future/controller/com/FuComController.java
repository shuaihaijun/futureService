package com.future.controller.com;

import com.future.service.com.FuComService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/com")
public class FuComController {
    @Autowired
    FuComService fuComService;

    Logger log=LoggerFactory.getLogger(FuComController.class);

    @RequestMapping(value = "/uploadPicture")
    public @ResponseBody Map uploadPicture(@RequestPart(value = "picture") MultipartFile file) {
        String filePath=fuComService.uploadPicture(file);
        Map resultMap = new HashMap();
        resultMap.put("name",file.getOriginalFilename());
        resultMap.put("url",filePath);
        log.info(filePath);
        log.info(file.getOriginalFilename());
        return resultMap;
    }
}