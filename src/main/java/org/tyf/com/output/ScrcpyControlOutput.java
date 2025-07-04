package org.tyf.com.output;


import org.tyf.com.framework.ControlOutput;
import org.tyf.com.tinylog.TinyLog;
import org.tyf.com.util.Point2f;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 *   @desc : scrcpy 远超链接 android 手机，鼠标操作 UI
 *   @auth : tyf
 *   @date : 2025-06-30 17:04:00
 */
public class ScrcpyControlOutput implements ControlOutput {


    // android 是通过屏幕进行操作，需要记录 scrcpy 窗口相对于宿主机的偏移，再进行鼠标移动操作时需要计算这个偏移量，这里记录左上角、右下角两个点的坐标
    private static int offsetX;
    private static int offsetY;
    private static int width;
    private static int height;
    public static void setWinOffset(int offsetX,int offsetY,int width,int height){
        ScrcpyControlOutput.offsetX = offsetX;
        ScrcpyControlOutput.offsetY = offsetY;
        ScrcpyControlOutput.width = width;
        ScrcpyControlOutput.height = height;
    }

    // 用于鼠标控制的工具，为清晰看到效果，鼠标可以设置为红色
    private static Robot robot;

    // 初始化
    public ScrcpyControlOutput(){
        try {
            // 初始化鼠标控制工具
            robot = new Robot();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void qxbz(Point2f point2f) {
        // 组件相对窗口的坐标
        int x = point2f.getCenterX();
        int y = point2f.getCenterY();
        // 窗口相对屏幕的偏移
        x = x + offsetX;
        y = y + offsetY;
        // 鼠标移动到这个位置
        smoothMouseMove(x,y);
        // 然后点击
        mouseLeftClick();
    }

    @Override
    public void djkbtg(Point2f point2f) {
        // 组件相对窗口的坐标
        int x = point2f.getCenterX();
        int y = point2f.getCenterY();
        // 窗口相对屏幕的偏移
        x = x + offsetX;
        y = y + offsetY;
        // 鼠标移动到这个位置
        smoothMouseMove(x,y);
        // 然后点击
        mouseLeftClick();
    }

    @Override
    public void cf(Point2f point2f) {
        // 组件相对窗口的坐标
        int x = point2f.getCenterX();
        int y = point2f.getCenterY();
        // 窗口相对屏幕的偏移
        x = x + offsetX;
        y = y + offsetY;
        // 鼠标移动到这个位置
        smoothMouseMove(x,y);
        // 然后点击
        mouseLeftClick();
    }

    @Override
    public void ksxd(Point2f point2f) {
        // 组件相对窗口的坐标
        int x = point2f.getCenterX();
        int y = point2f.getCenterY();
        // 窗口相对屏幕的偏移
        x = x + offsetX;
        y = y + offsetY;
        // 鼠标移动到这个位置
        smoothMouseMove(x,y);
        // 然后点击
        mouseLeftClick();
    }

    @Override
    public void lhdb(Point2f point2f) {
        // 组件相对窗口的坐标
        int x = point2f.getCenterX();
        int y = point2f.getCenterY();
        // 窗口相对屏幕的偏移
        x = x + offsetX;
        y = y + offsetY;
        // 鼠标移动到这个位置
        smoothMouseMove(x,y);
        // 然后点击
        mouseLeftClick();
    }

    @Override
    public void qrzp(Point2f point2f) {
        // 组件相对窗口的坐标
        int x = point2f.getCenterX();
        int y = point2f.getCenterY();
        // 窗口相对屏幕的偏移
        x = x + offsetX;
        y = y + offsetY;
        // 鼠标移动到这个位置
        smoothMouseMove(x,y);
        // 然后点击
        mouseLeftClick();
    }

    @Override
    public void qr(Point2f point2f) {
        // 组件相对窗口的坐标
        int x = point2f.getCenterX();
        int y = point2f.getCenterY();
        // 窗口相对屏幕的偏移
        x = x + offsetX;
        y = y + offsetY;
        // 鼠标移动到这个位置
        smoothMouseMove(x,y);
        // 然后点击
        mouseLeftClick();
    }

    @Override
    public void moveForward_start() {
        TinyLog.log(TinyLog.Level.TRACE, "moveForward_start");
    }

    @Override
    public void moveForward_stop() {
        TinyLog.log(TinyLog.Level.TRACE, "moveForward_stop");
    }

    @Override
    public void moveBackward_start() {
        TinyLog.log(TinyLog.Level.TRACE, "moveBackward_start");
    }

    @Override
    public void moveBackward_stop() {
        TinyLog.log(TinyLog.Level.TRACE, "moveBackward_stop");
    }

    @Override
    public void moveLeft_start() {
        TinyLog.log(TinyLog.Level.TRACE, "moveLeft_start");
    }

    @Override
    public void moveLeft_stop() {
        TinyLog.log(TinyLog.Level.TRACE, "moveLeft_stop");
    }

    @Override
    public void moveRight_start() {
        TinyLog.log(TinyLog.Level.TRACE, "moveRight_start");
    }

    @Override
    public void moveRight_stop() {
        TinyLog.log(TinyLog.Level.TRACE, "moveRight_stop");
    }

    @Override
    public void openMap() {
        TinyLog.log(TinyLog.Level.TRACE, "openMap");
    }

    @Override
    public void closeMap() {
        TinyLog.log(TinyLog.Level.TRACE, "closeMap");
    }


    @Override
    public void selfCheck() {
        TinyLog.log(TinyLog.Level.INFO,"窗口区域四点校准，请勿移动鼠标");
        for (int i = 0; i < 4; i++) {
            int idx = i % 4;
            switch (idx) {
                case 0 : moveTopLeft(); break;  // 移动到左上
                case 1 : moveTopRight(); break; // 移动到右上
                case 2 : moveBottomLeft(); break; // 移动到左下
                case 3 : moveBottomRight(); break;  // 移动到右下
            }
            try {
                Thread.sleep(100); // 1 秒间隔
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void release() {
        TinyLog.log(TinyLog.Level.INFO,"ScrcpyControlOutput releae");
    }




    // 平滑移动鼠标到指定坐标
    private void smoothMouseMove(int endX, int endY) {
        Point curr = MouseInfo.getPointerInfo().getLocation();
        int startX = curr.x;
        int startY = curr.y;
        int steps = 20;
        double dx = (endX - startX) / (double) steps;
        double dy = (endY - startY) / (double) steps;
        for (int i = 1; i <= steps; i++) {
            int moveX = startX + (int)(dx * i);
            int moveY = startY + (int)(dy * i);
            robot.mouseMove(moveX, moveY);
            robot.delay(10); // 每步延迟，影响平滑程度
        }
    }

    // 执行一次鼠标左键单击
    private void mouseLeftClick() {
        if (robot == null) {
            return;
        }
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(50); // 按下持续时间
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(100); // 点击后稍微等待一下
    }


    // 鼠标移动到左上（用于校准测试）
    public void moveTopLeft() {
        int targetX = offsetX;
        int targetY = offsetY;
        TinyLog.log(TinyLog.Level.TRACE, "moveTopLeft to (" + targetX + "," + targetY + ")");
        smoothMouseMove(targetX, targetY);
    }

    // 鼠标移动到右上（用于校准测试）
    public void moveTopRight() {
        int targetX = offsetX + width - 1;
        int targetY = offsetY;
        TinyLog.log(TinyLog.Level.TRACE, "moveTopRight to (" + targetX + "," + targetY + ")");
        smoothMouseMove(targetX, targetY);
    }


    // 鼠标移动到左下（用于校准测试）
    public void moveBottomLeft() {
        int targetX = offsetX;
        int targetY = offsetY + height - 1;
        TinyLog.log(TinyLog.Level.TRACE, "moveBottomLeft to (" + targetX + "," + targetY + ")");
        smoothMouseMove(targetX, targetY);
    }

    // 鼠标移动到右下（用于校准测试）
    public void moveBottomRight() {
        int targetX = offsetX + width - 1;
        int targetY = offsetY + height - 1;
        TinyLog.log(TinyLog.Level.TRACE, "moveBottomRight to (" + targetX + "," + targetY + ")");
        smoothMouseMove(targetX, targetY);
    }



}
