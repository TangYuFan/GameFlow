package org.tyf.com.framework;


import org.tyf.com.util.Point2f;

/**
 *   @desc : 控制输出（封装的游戏可操作对象）
 *           控制最底层就两种形式：1.鼠标操作某个地方 2.键盘操作某个地方
 *           也就是说最多需要一个坐标参数，由子类来实现
 *   @auth : tyf
 *   @date : 2025-06-30 16:21:01
*/
public interface ControlOutput {

    void qxbz(Point2f point2f);    // 前行备战
    void djkbtg(Point2f point2f);    // 点击空白跳过
    void cf(Point2f point2f);    // 出发
    void ksxd(Point2f point2f);    // 开始行动
    void lhdb(Point2f point2f);    // 零号大巴
    void qrzp(Point2f point2f);    // 确认装配
    void qr(Point2f point2f);    // 确认

    // 前进开始/停止
    void moveForward_start();
    void moveForward_stop();

    // 后退开始/停止
    void moveBackward_start();
    void moveBackward_stop();

    // 左移开始/停止
    void moveLeft_start();
    void moveLeft_stop();

    // 右移开始/停止
    void moveRight_start();
    void moveRight_stop();

    // 打开地图
    void openMap();

    // 关闭地图
    void closeMap();

    // 资源退出
    void release();

    void selfCheck();
}
