package org.tyf.com.util;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.ORB;
import org.opencv.features2d.SIFT;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.tyf.com.tinylog.TinyLog;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *   @desc : 图片工具
 *   @auth : tyf
 *   @date : 2025-06-30 18:21:58
*/
public class ImageUtils {

    static {
        // 复制 dll 到jdk路径
        String project = System.getProperty("user.dir") + "\\lib\\opencv_java455.dll";
        String javaHome = System.getProperty("java.home").replace("jre","bin");
        TinyLog.log(TinyLog.Level.INFO,"javaHome："+javaHome);
        FileUtils.fileCopy(project,javaHome);
        // opencv 库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }


    public static Mat imread(String path){
        return Imgcodecs.imread(path);
    }

    /**
     * 显示图像在一个弹窗中
     */
    public static void showImage(BufferedImage image) {
        if (image == null) {
            TinyLog.log(TinyLog.Level.INFO,"图像为空，无法显示。");
            return;
        }
        JFrame frame = new JFrame("截图预览");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(image.getWidth(), image.getHeight());
        frame.setLocation(0, 0);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * 显示图像在一个弹窗中，超时后自动退出
     */
    public static void showImage(BufferedImage image,int delay) {
        if (image == null) {
            TinyLog.log(TinyLog.Level.INFO, "图像为空，无法显示。");
            return;
        }

        JFrame frame = new JFrame("截图预览");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(image.getWidth(), image.getHeight());
        frame.setLocation(0, 0); // 显示在屏幕左上角

        JLabel label = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);

        // 2秒后自动关闭窗口
        Timer timer = new Timer(delay, e -> frame.dispose());
        timer.setRepeats(false); // 只执行一次
        timer.start();
    }


    /**
     * 保存图像到指定目录，文件名为当前时间戳
     */
    public static void saveImage(BufferedImage image, String dirPath) {
        if (image == null) {
            TinyLog.log(TinyLog.Level.INFO,"图像为空，无法保存。");
            return;
        }
        try {
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File outputFile = new File(dir, "screenshot_" + timestamp + ".png");
            ImageIO.write(image, "png", outputFile);
            TinyLog.log(TinyLog.Level.INFO,"图像已保存：" + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 将 BufferedImage 转换为 Mat
     */
    public static Mat bufferedImageToMat(BufferedImage bi) {
        // 创建标准的 3 通道 BGR 图像
        BufferedImage bgrImage = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        // 将原图绘制到新图上，确保是 byte 格式
        Graphics2D g = bgrImage.createGraphics();
        g.drawImage(bi, 0, 0, null);
        g.dispose();
        // 提取像素数据
        byte[] pixels = ((DataBufferByte) bgrImage.getRaster().getDataBuffer()).getData();
        // 构造 OpenCV Mat
        Mat mat = new Mat(bgrImage.getHeight(), bgrImage.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, pixels);
        return mat;
    }

    public static Mat resizeWithPadding(Mat src,int netWidth,int netHeight) {
        Mat dst = new Mat();
        int oldW = src.width();
        int oldH = src.height();
        double r = Math.min((double) netWidth / oldW, (double) netHeight / oldH);
        int newUnpadW = (int) Math.round(oldW * r);
        int newUnpadH = (int) Math.round(oldH * r);
        int dw = (Long.valueOf(netWidth).intValue() - newUnpadW) / 2;
        int dh = (Long.valueOf(netHeight).intValue() - newUnpadH) / 2;
        int top = (int) Math.round(dh - 0.1);
        int bottom = (int) Math.round(dh + 0.1);
        int left = (int) Math.round(dw - 0.1);
        int right = (int) Math.round(dw + 0.1);
        Imgproc.resize(src, dst, new Size(newUnpadW, newUnpadH));
        Core.copyMakeBorder(dst, dst, top, bottom, left, right, Core.BORDER_CONSTANT);
        return dst;
    }


    /**
     * 在大图中查找小图位置（带预处理和参数优化）
     * @param fullImage 大图 Mat (彩色)
     * @param subImage 小图 Mat (彩色)
     * @return 返回匹配到的小图在大图中的四个顶点位置，找不到返回 null
     */
    public static Point2f findSubImageBySIFT(Mat fullImage, Mat subImage) {
        Mat grayFull = new Mat();
        Mat graySub = new Mat();
        Mat descFull = new Mat();
        Mat descSub = new Mat();
        Mat objCorners = new Mat(4, 1, CvType.CV_32FC2);
        Mat sceneCorners = new Mat();
        Mat homography = null;

        try {
            Imgproc.cvtColor(fullImage, grayFull, Imgproc.COLOR_BGR2GRAY);
            Imgproc.cvtColor(subImage, graySub, Imgproc.COLOR_BGR2GRAY);

            SIFT sift = SIFT.create();
            MatOfKeyPoint kpFull = new MatOfKeyPoint();
            MatOfKeyPoint kpSub = new MatOfKeyPoint();
            sift.detectAndCompute(grayFull, new Mat(), kpFull, descFull);
            sift.detectAndCompute(graySub, new Mat(), kpSub, descSub);

            if (descFull.empty() || descSub.empty()) return null;

            DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
            List<MatOfDMatch> knnMatches = new ArrayList<>();
            matcher.knnMatch(descSub, descFull, knnMatches, 2);

            List<DMatch> goodMatches = new ArrayList<>();
            for (MatOfDMatch pair : knnMatches) {
                DMatch[] matches = pair.toArray();
                if (matches.length >= 2 && matches[0].distance < 0.75f * matches[1].distance) {
                    goodMatches.add(matches[0]);
                }
            }

            if (goodMatches.size() < 30) {
                return null;
            }

            List<Point> ptsSub = new ArrayList<>();
            List<Point> ptsFull = new ArrayList<>();
            KeyPoint[] subKeypoints = kpSub.toArray();
            KeyPoint[] fullKeypoints = kpFull.toArray();

            for (DMatch match : goodMatches) {
                ptsSub.add(subKeypoints[match.queryIdx].pt);
                ptsFull.add(fullKeypoints[match.trainIdx].pt);
            }

            MatOfPoint2f srcPoints = new MatOfPoint2f();
            MatOfPoint2f dstPoints = new MatOfPoint2f();
            srcPoints.fromList(ptsSub);
            dstPoints.fromList(ptsFull);

            homography = Calib3d.findHomography(srcPoints, dstPoints, Calib3d.RANSAC, 5);
            if (homography == null || homography.empty()) return null;

            objCorners.put(0, 0, new float[]{
                    0f, 0f,
                    subImage.cols(), 0f,
                    subImage.cols(), subImage.rows(),
                    0f, subImage.rows()
            });

            Core.perspectiveTransform(objCorners, sceneCorners, homography);

            Point2f result = new Point2f();
            for (int i = 0; i < 4; i++) {
                double[] pt = sceneCorners.get(i, 0);
                result.add((float) pt[0], (float) pt[1]);
            }

            return result;
        } finally {
            // 全部手动释放
            grayFull.release();
            graySub.release();
            descFull.release();
            descSub.release();
            objCorners.release();
            sceneCorners.release();
            if (homography != null) homography.release();
        }
    }

    /**
     * 在原始图像上绘制一个多边形框（通常用于标注 UI 元素边界）
     * @param image 要标注的图像（BufferedImage，直接修改）
     * @param points UI 区域四个点的坐标（自定义 Point2f，通常为4点）
     */
    public static void drawPolygonOnImage(BufferedImage image, Point2f points) {
        if (image == null || points == null || points.getPoints().size() < 4) {
            return;
        }
        Graphics2D g = image.createGraphics();
        // 设置红色边框线条
        g.setColor(java.awt.Color.RED);
        g.setStroke(new java.awt.BasicStroke(4));

        // 取出点坐标列表
        List<Point2f.Point> pts = points.getPoints();
        int n = pts.size();

        // 连线构建闭环
        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            g.drawLine((int) pts.get(i).x, (int) pts.get(i).y, (int) pts.get(j).x, (int) pts.get(j).y);
        }
        g.dispose();
    }



}
