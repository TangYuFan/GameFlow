package org.tyf.com.componet;


import org.opencv.core.Mat;
import org.tyf.com.util.Point2f;

/**
 *   @desc : 初始化所有可以交互的 UI 控件
 *   @auth : tyf
 *   @date : 2025-07-01 12:03:25
*/
public class UiComponentEntity {

    // 组件名称
    String name;

    // 组件类型
    UiComponentType type;

    // 组件对于 mat 图片
    Mat mat;

    // 组件相对窗口的区域
    Point2f point2f;


    public UiComponentEntity(UiComponentType type,String name, Mat mat) {
        this.name = name;
        this.mat = mat;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mat getMat() {
        return mat;
    }

    public void setMat(Mat mat) {
        this.mat = mat;
    }

    public Point2f getPoint2f() {
        return point2f;
    }

    // 设置当前组件在整个UI中的区域四个顶点
    public void setPoint2f(Point2f point2f) {
        this.point2f = point2f;
    }

    public UiComponentType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Ui{" +
                "[" + name + ']' +
                '}';
    }
}
