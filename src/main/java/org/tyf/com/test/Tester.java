package org.tyf.com.test;



import org.tyf.com.input.ScrcpyVideoInput;
import org.tyf.com.output.ScrcpyControlOutput;
import org.tyf.com.util.ImageUtils;

/**
 *   @desc : 各种单元测试
 *   @auth : tyf
 *   @date : 2025-06-30 21:42:17
*/
public class Tester {


    // 测试 scrcpy 的视频输入和鼠标输出
    public static void test_scrcpy_in_out(){

        // 开启视频输入
        ScrcpyVideoInput input = new ScrcpyVideoInput();
        // 开启鼠标输出
        ScrcpyControlOutput output = new ScrcpyControlOutput();

        // 校准测试：循环移动到 4 个角，每次间隔 1 秒
        for (int i = 0; i < 8; i++) {
            int idx = i % 4;
            switch (idx) {
                case 0 : output.moveTopLeft(); break;  // 移动到左上
                case 1 : output.moveTopRight(); break; // 移动到右上
                case 2 : output.moveBottomLeft(); break; // 移动到左下
                case 3 : output.moveBottomRight(); break;  // 移动到右下
            }
            try {
                Thread.sleep(1000); // 1 秒间隔
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 抓取一帧显示
        ImageUtils.showImage(input.getCurrentFrame());
        ImageUtils.saveImage(input.getCurrentFrame(),"./debug");

        // 释放资源
        output.release();
        input.release();

        System.exit(0);

    }


    // 测试 scrcpy 的鼠标输入（案件点击、游戏控制）
    public static void test_scrcpy_control() throws Exception{

        // 开启视频输入
        ScrcpyVideoInput input = new ScrcpyVideoInput();
        // 开启鼠标输出
        ScrcpyControlOutput output = new ScrcpyControlOutput();

        // 点击开始对局


        // 等待进入对局
        Thread.sleep(20*1000);

        // 测试


        // 释放资源
        output.release();
        input.release();

        System.exit(0);



    }


    // 测试 win 鼠标输入，按键坐标定位以及功能点击测试
    public static void test_win_control() throws Exception{

        // 开启视频输入
        ScrcpyVideoInput input = new ScrcpyVideoInput();
        // 开启鼠标输出
        ScrcpyControlOutput output = new ScrcpyControlOutput();

        // 点击开始对局


        // 等待进入对局
        Thread.sleep(20*1000);

        // 测试


        // 释放资源
        output.release();
        input.release();

        System.exit(0);



    }



    public static void main(String[] args) throws Exception{

        // 测试 scrcpy 的视频输入和鼠标输出
//        test_scrcpy_in_out();

        // 测试 win 的视频输入和鼠标输出
//        test_win_in_out();

        // 测试 scrcpy 鼠标输入，按键坐标定位以及功能点击测试
//        test_scrcpy_control();

        // 测试 win 鼠标输入，按键坐标定位以及功能点击测试
//        test_win_control();



    }

}
