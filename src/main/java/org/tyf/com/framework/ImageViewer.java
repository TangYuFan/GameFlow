package org.tyf.com.framework;

import org.tyf.com.componet.UiComponentEntity;
import org.tyf.com.util.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *   @desc : 帧处理可视化
 *   @auth : tyf
 *   @date : 2025-07-02 09:55:54
*/
public class ImageViewer extends JPanel {

    private static JFrame frame;
    private static ImageViewer instance;

    private BufferedImage currentImage;

    public ImageViewer() {
        this.setPreferredSize(new Dimension(640, 480)); // 默认大小
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentImage != null) {
            int panelWidth = this.getWidth();
            int panelHeight = this.getHeight();
            int imgWidth = currentImage.getWidth();
            int imgHeight = currentImage.getHeight();
            // 计算缩放比例，保持图像宽高比
            double scale = Math.min((double) panelWidth / imgWidth, (double) panelHeight / imgHeight);
            int drawWidth = (int) (imgWidth * scale);
            int drawHeight = (int) (imgHeight * scale);
            // 居中显示
            int x = (panelWidth - drawWidth) / 2;
            int y = (panelHeight - drawHeight) / 2;
            g.drawImage(currentImage, x, y, drawWidth, drawHeight, null);
        }
    }

    /**
     * 初始化窗口，只需调用一次
     */
    public static void init(String title, int width, int height) {
        if (frame != null) return;
        // 将宽高比帧图设置大一些，保证比例显示
        width = width + 30;
        height = height + 30;
        instance = new ImageViewer();
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(instance);
        frame.setSize(width, height);
        frame.setLocation(0,0);
        frame.setVisible(true);
    }

    /**
     * 设置当前帧图像，调用后自动重绘
     */
    public static void setFrame(BufferedImage image) {
        if (instance == null) {
            return;
        }
        // 渲染新图像
        instance.currentImage = image;
        instance.repaint();
    }

}
