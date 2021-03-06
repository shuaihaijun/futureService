package com.future.service.com;

import com.future.common.util.DateUtil;
import com.future.common.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@Service
public class FuComService{

    Logger log= LoggerFactory.getLogger(FuComService.class);

    @Value("${imageFilePath}")
    String imageFilePath;

    /**
     * 上传文件
     * @param file
     * @return
     */
    public  String uploadPicture(MultipartFile file) {
        String filePath =imageFilePath;
        filePath += DateUtil.getYear()+"\\"+DateUtil.getMonth();
        log.info("filePath:"+filePath);
        String fileName= FileUtil.uploadImage(file,filePath);
        return filePath+"\\"+fileName;
    }


    /**
     * 获取 jar包所在目录路径
     * @return
     */
    public String getLoactionPath(){
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()) path = new File("");
            return path.getAbsolutePath();
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return null;
        }
    }

    /*if(file.isEmpty()){
            log.error("查询用户列表,获取参数为空！");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        //获取文件在服务器的储存位置
        File filePath = new File(path);
        System.out.println("文件的保存路径：" + path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            System.out.println("目录不存在，创建目录:" + filePath);
            filePath.mkdir();
        }

        //获取原始文件名称(包含格式)
        String originalFileName = picture.getOriginalFilename();
        System.out.println("原始文件名称：" + originalFileName);

        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        System.out.println("文件类型：" + type);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));

        //设置文件新名称: 当前时间+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = date + name + "." + type;
        System.out.println("新文件名称：" + fileName);

        //在指定路径下创建一个文件
        File targetFile = new File(path, fileName);

        //将文件保存到服务器指定位置
        try {
            picture.transferTo(targetFile);
            System.out.println("上传成功");
            //将文件在服务器的存储路径返回
            return new Result(true,"/upload/" + fileName);
        } catch (IOException e) {
            System.out.println("上传失败");
            e.printStackTrace();
            return new Result(false, "上传失败");
        }
        return file.getName();*/
}
