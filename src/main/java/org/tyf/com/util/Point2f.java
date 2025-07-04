package org.tyf.com.util;

import java.util.ArrayList;
import java.util.List;

/**
 *   @desc : 存储位置区域，保存坐标点集合
 *   @auth : tyf
 *   @date : 2025-07-02 15:39:12
*/
public class Point2f {


    // 坐标点定义
    public static class Point {
        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("(%.2f, %.2f)", x, y);
        }
    }

    // 坐标点集合
    private final List<Point> points = new ArrayList<>();

    // 中心点
    private int centerX;
    private int centerY;

    public void add(float x, float y) {
        points.add(new Point(x, y));

        // 计算中心点坐标：
        double sumX = 0, sumY = 0;
        for (Point p : points) {
            sumX += p.x;
            sumY += p.y;
        }
        this.centerX = (int) Math.round(sumX / points.size());
        this.centerY = (int) Math.round(sumY / points.size());
    }


    // UI区域对应多个点
    public List<Point> getPoints() {
        return points;
    }


    // 获取中心点坐标

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    @Override
    public String toString() {
        return points.toString();
    }
}