package org.tyf.com.input;


import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import org.tyf.com.config.Config;
import org.tyf.com.framework.VideoInput;
import org.tyf.com.output.ScrcpyControlOutput;
import org.tyf.com.tinylog.TinyLog;
import org.tyf.com.util.ImageUtils;
import org.tyf.com.util.ScreenUtils;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 *   @desc : scrcpy 远程链接 android 手机，抓取 scrcpy 窗口屏幕
 *   @auth : tyf
 *   @date : 2025-06-30 17:04:00
*/
public class ScrcpyVideoInput implements VideoInput {

    // 设置 scrcpy 窗口属性
    private static Process scrcpyProcess;
    private static int maxSize = 1200;
    private static int bitRate = 100;
    private static int fps = 15;
    private static String winName = "SCRCPY";

    // 这里记录 scrcpy 窗口的左上角坐标以及宽高，用于每帧截取
    private static int x;
    private static int y;
    private static int width;
    private static int height;

    // 用于截图
    private static Robot robot;

    // 运行 scrcpy 打开窗口
    public ScrcpyVideoInput(){

        String currentPath = System.getProperty("user.dir");
        TinyLog.log(TinyLog.Level.INFO,"Project Dir："+currentPath);
        TinyLog.log(TinyLog.Level.INFO,"显示缩放比例推算："+ ScreenUtils.getScala()+"，当前缩放比例配置："+Config.winScala);
        // 强制设置手机以指定的分辨率进行显示，这样屏蔽缩放对UI识别的影响
        ProcessBuilder wd = new ProcessBuilder(currentPath+"\\android-scrcpy-win64-v3.3.1\\adb.exe","shell","wm","size","1080x2400");

        // 启动 scrcpy
        ProcessBuilder pb = new ProcessBuilder(
                currentPath + "\\android-scrcpy-win64-v3.3.1\\scrcpy.exe",
                "--max-size=" + maxSize,
                "--video-bit-rate="+bitRate+"M",     // 视频码率
                "--max-fps="+fps,                     // 帧率
                "--video-codec=h264",                 // 使用 h264 编码
                "--window-borderless",                // 设置不显示标题栏
                "--window-title=" + winName);
        try {
            TinyLog.log(TinyLog.Level.INFO,"scrcpy 启动开始，需设置显示缩放比例为 100%（或者配置缩放比例），启动成功后保持窗口在顶部，勿拖动窗口位置");
            // 强制设置手机分辨率
            wd.start();
            Thread.sleep(1000);
            // 启动 scrcpy 并等待成功
            scrcpyProcess = pb.start();
            Thread.sleep(2000);
            // 初始化鼠标控制工具
            robot = new Robot();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // 等待启动成功，然后记录窗口位置
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, winName); // 根据标题找窗口
        if (hwnd == null) {
            TinyLog.log(TinyLog.Level.INFO, "找不到 scrcpy 窗口！");
            System.exit(0);
        }
        // 获取窗口位置和大小
        WinDef.RECT rect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(hwnd, rect);
        x = rect.left;
        y = rect.top;
        width = rect.right - rect.left;
        height = rect.bottom - rect.top;

        // 因为是截取屏幕区域所以需要保持 scrcpy 窗口在顶部，并且 win 的显示缩放比例需要设置为 100%
        // 记录窗口左上角的坐标，屏幕操作时需要考虑这个偏移量，这里记录窗口左上角、右下角两个坐标
        ScrcpyControlOutput.setWinOffset(x,y,width,height);
//        TinyLog.log(TinyLog.Level.INFO,"Scrcpy 启动完成 x："+x+"，y："+y+"，width："+width+"，height："+height);

    }


    // 获取 scrcpy 窗口的帧图（截图需要显示缩放比例为 100% 否则截图会错位）
    @Override
    public BufferedImage getCurrentFrame(){
        if(robot==null){
            return null;
        }
        // 屏幕截取需要考虑缩放比例
        int _x = Double.valueOf(x * Config.winScala).intValue();
        int _y = Double.valueOf(y * Config.winScala).intValue();
        int _width = Double.valueOf(width * Config.winScala).intValue();
        int _height = Double.valueOf(height * Config.winScala).intValue();
        BufferedImage image = robot.createScreenCapture(new Rectangle( _x, _y, _width, _height));
        return image;
    }


    @Override
    public void selfCheck() {
        // 弹窗显示当前帧，2s后关闭
        TinyLog.log(TinyLog.Level.INFO,"窗口抓取..");
        ImageUtils.showImage(getCurrentFrame(),2000);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void release() {
        TinyLog.log(TinyLog.Level.INFO,"ScrcpyVideoInput release");
        if (scrcpyProcess != null && scrcpyProcess.isAlive()) {
            scrcpyProcess.destroy(); // 尝试正常关闭
            try {
                boolean exited = scrcpyProcess.waitFor(5, java.util.concurrent.TimeUnit.SECONDS);
                if (!exited) {
                    scrcpyProcess.destroyForcibly(); // 强制关闭
                }
                // 将手机分辨率恢复
                String currentPath = System.getProperty("user.dir");
                ProcessBuilder wd = new ProcessBuilder(currentPath+"\\android-scrcpy-win64-v3.3.1\\adb.exe","shell","wm","size","reset");
                wd.start();
            } catch (InterruptedException e) {
                scrcpyProcess.destroyForcibly();
                Thread.currentThread().interrupt();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
