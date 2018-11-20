package com.github.lihang941.common.utils;

import org.apache.commons.io.FileUtils;
import com.github.lihang941.common.utils.FileTool;
import com.github.lihang941.common.utils.RandomCode;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/9/4
 */
public class FileService {

    private String staticPath;

    private File staticDir;

    public static String[] picture() {
        return new String[]{"jpg", "jpeg", "png", "gif"};
    }

    public FileService(String staticPath) {
        this.staticPath = staticPath;
        staticDir = new File(staticPath);
        if (!staticDir.exists()) {
            staticDir.mkdirs();
        }
    }

    /**
     * file copy -> static/path/{uuid}.{suffix}
     *
     * @param fileName       实际文件名
     * @param uploadFileName 服务器暂存文件名
     * @param path           static下的路径
     * @param maxFileSize
     * @param whiteList      白名单
     * @return {path}/{uuid}.{suffix}
     */
    public String addFile(String fileName, String uploadFileName, String path, long maxFileSize, String[]... whiteList) throws IOException {
        File uploadFile = new File(uploadFileName);
        if (maxFileSize != -1 && uploadFile.length() > maxFileSize) {
            throw new IOException("File should be less than " + getFormatSize(maxFileSize));
        }
        String suffix = FileTool.parseSuffix(fileName);
        if (whiteList != null && whiteList.length > 0) {
            boolean flag = true;
            for (String[] white : whiteList) {
                if (FileTool.isWhiteList(suffix, white)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                throw new IOException("This file does not allow uploading");
            }
        }
        String name = RandomCode.getUUIDString() + "." + suffix;
        File temp = path == null
                ? new File(staticPath)
                : new File(staticDir.getAbsolutePath() + File.separator + path);
        if (!temp.exists()) {
            temp.mkdirs();
        }
        FileUtils.copyFile(uploadFile, new File(temp.getAbsolutePath() + File.separator + name));
        return path == null ? name : path + "/" + name;
    }


    public String getStaticPath() {
        return staticPath;
    }

    public File getStaticDir() {
        return staticDir;
    }

    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte(s)";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}
