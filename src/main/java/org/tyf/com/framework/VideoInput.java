package org.tyf.com.framework;


import java.awt.image.BufferedImage;

/**
 *   @desc : 视频输入
 *   @auth : tyf
 *   @date : 2025-06-27 17:36:27
*/
public interface VideoInput {

    BufferedImage getCurrentFrame();  // 获取当前帧

    void selfCheck();   // 启动成功的自检函数，例如截取一帧查看是否准确

    void release();     // 资源释放

    int getX();         // 获取视频输入的位置和宽高
    int getY();         // 获取视频输入的位置和宽高
    int getWidth();     // 获取视频输入的位置和宽高
    int getHeight();    // 获取视频输入的位置和宽高

}
