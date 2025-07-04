package org.tyf.com.model;


import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtSession;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.tyf.com.util.ImageUtils;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *   @desc : restnet50 特征提取网络
 *   @auth : tyf
 *   @date : 2025-07-01 10:25:24
*/
public class OrtRestnet50 {

    // 环境
    public static OrtSession session;

    // 输入
    public static int width = 224;
    public static int height = 224;
    public static int channel = 3;


    // 模型初始化
    public static void checkSession(){
        try {
            if(session==null){
                String currentPath = System.getProperty("user.dir");
                String model = currentPath + "\\model\\resnet50_features.onnx";
                session = Ort.getEnv().createSession(model, new OrtSession.SessionOptions());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    // 推理输出特征向量
    public static float[] infer(Mat image){

        checkSession();

        Mat mat = ImageUtils.resizeWithPadding(image,width,height);
        image.release();

        // BGR -> RGB
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2RGB);

        //  归一化 0-255 转 0-1
        mat.convertTo(mat, CvType.CV_32FC1, 1. / 255);


        // 提取三个通道
        List<Mat> mats = new ArrayList<>();
        Core.split(mat, mats);
        Mat r = mats.get(0);
        Mat g = mats.get(1);
        Mat b = mats.get(2);

        // 提取数组
        float[] r_data = new float[r.cols()*r.rows()];
        float[] g_data = new float[g.cols()*g.rows()];
        float[] b_data = new float[b.cols()*b.rows()];
        r.get(0, 0, r_data);
        g.get(0, 0, g_data);
        b.get(0, 0, b_data);

        // 标准化
        float[] mean = {0.485f, 0.456f, 0.406f};
        float[] std  = {0.229f, 0.224f, 0.225f};
        for (int i = 0; i < r_data.length; i++) {
            r_data[i] = (r_data[i] - mean[0]) / std[0];
            g_data[i] = (g_data[i] - mean[1]) / std[1];
            b_data[i] = (b_data[i] - mean[2]) / std[2];
        }

        // 因为输入是chw 也就是说  rrrrr ggggg bbbbb 将三个数组拼接即可
        float[] chw = new float[ r_data.length + g_data.length + b_data.length ];

        // 合并为一个数组
        int currentIndex = 0;
        System.arraycopy(r_data, 0, chw, currentIndex, r_data.length);currentIndex += r_data.length;
        System.arraycopy(g_data, 0, chw, currentIndex, g_data.length);currentIndex += g_data.length;
        System.arraycopy(b_data, 0, chw, currentIndex, b_data.length);

        // 释放 mat
        r.release();
        g.release();
        b.release();
        for (Mat m : mats) m.release();
        mat.release();

        // 推理
        try {
            OnnxTensor tensor = OnnxTensor.createTensor(Ort.getEnv(), FloatBuffer.wrap(chw), new long[]{1,channel,width,height});
            OrtSession.Result result = session.run(Collections.singletonMap("input", tensor));
            OnnxTensor output = (OnnxTensor)result.get(0);
            float[] data = ((float[][])output.getValue())[0];
            // 返回 2048 dim 的特征向量
            return data;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


}
