package com.future.common.util;

import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.ParameterInvalidException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 文件上传工具类
 * @author Admin
 * @version: 1.0
 */
public class FileUtil {

    private static final List<String> ALLOW_TYPES = Arrays.asList("image/png", "image/jpeg", "image/bmg");

    /**
     * 上传图片
     * @param file
     * @param filePath
     * @return
     */
    public static String uploadImage(MultipartFile file,String filePath) {
        try {
            //校验文件类型
            String contentType = file.getContentType();
            if(!ALLOW_TYPES.contains(contentType)){
                throw new ParameterInvalidException(GlobalResultCode.IMPORT_FILE_ERROR);
            }
            //校验文件的内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null){
                throw new ParameterInvalidException(GlobalResultCode.IMPORT_FILE_ERROR);
            }

            //获取文件在服务器的储存位置
            File thePath = new File(filePath);
            if (!thePath.exists() && !thePath.isDirectory()) {
                System.out.println("目录不存在，创建目录:" + filePath);
                thePath.mkdir();
            }
            //准备目标路径
            File dest = new File(filePath,file.getOriginalFilename());
            file.transferTo(dest);
            //返回路径
            return file.getOriginalFilename();
        } catch (IOException e) {
            throw new BusinessException(e);
        }

    }

    /**
     * 获取文件相对地址
     * @param absolutePath
     * @return
     */
    public static String getFileRelativePath(String absolutePath){
        if (StringUtils.isEmpty(absolutePath)){
            return null;
        }
        if(absolutePath.indexOf(":")<0){
            return absolutePath;
        }
        return absolutePath.substring(absolutePath.indexOf(":")+1,absolutePath.length());
    }
}