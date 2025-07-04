package org.tyf.com.util;

import org.tyf.com.tinylog.TinyLog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class FileUtils {


    // 移动指定文件到指定目录下
    public static void fileMove(String filePath, String folderPath) {
        File sourceFile = new File(filePath);
        File targetFolder = new File(folderPath);
        // 校验源文件是否存在
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            System.err.println("源文件不存在或不是一个文件: " + filePath);
            return;
        }
        // 如果目标目录不存在，创建目录
        if (!targetFolder.exists()) {
            boolean created = targetFolder.mkdirs();
            if (!created) {
                System.err.println("无法创建目标文件夹: " + folderPath);
                return;
            }
        }
        // 构造目标文件路径
        Path targetPath = new File(targetFolder, sourceFile.getName()).toPath();

        try {
            // 移动文件，如果已存在则覆盖
            Files.move(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            TinyLog.log(TinyLog.Level.INFO,"文件移动成功: " + targetPath.toString());
        } catch (IOException e) {
            System.err.println("移动文件失败: " + e.getMessage());
        }
    }


    // 复制指定文件到指定目录下（保留原文件）
    public static void fileCopy(String filePath, String folderPath) {
        File sourceFile = new File(filePath);
        File targetFolder = new File(folderPath);

        // 校验源文件是否存在
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            System.err.println("源文件不存在或不是一个文件: " + filePath);
            return;
        }

        // 如果目标目录不存在，创建目录
        if (!targetFolder.exists()) {
            boolean created = targetFolder.mkdirs();
            if (!created) {
                System.err.println("无法创建目标文件夹: " + folderPath);
                return;
            }
        }

        // 构造目标文件路径
        Path targetPath = new File(targetFolder, sourceFile.getName()).toPath();

        try {
            // 复制文件，如果已存在则覆盖
            Files.copy(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            TinyLog.log(TinyLog.Level.INFO, "file copy: " + targetPath.toString());
        } catch (IOException e) {
            System.err.println("复制文件失败: " + e.getMessage());
        }
    }


}
