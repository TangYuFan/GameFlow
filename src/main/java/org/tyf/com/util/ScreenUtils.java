package org.tyf.com.util;

import java.awt.*;

public class ScreenUtils {



    // Windows 的显示缩放是通过将 逻辑像素（DIP）映射到物理像素 来实现的，比如：
    // 缩放比例	DPI 值
    // 100%	96
    // 125%	120
    // 150%	144
    // 175%	168
    // 200%	192
    public static double getScala(){
        // 获取默认工具包
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // 获取屏幕分辨率，即DPI
        int dpi = toolkit.getScreenResolution();
        // dpi 推算缩放缩放比例
        return Double.valueOf(dpi)/96;
    }

}
