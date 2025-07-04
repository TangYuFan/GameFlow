package org.tyf.com.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *   @desc : 执行系统 cmd
 *   @auth : tyf
 *   @date : 2025-06-30 17:06:54
*/
public class CmdUtils {


    public static String exec(String command) {
        StringBuilder output = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            // 获取标准输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }

            // 等待命令执行完成
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return output.toString().trim();
    }






}
