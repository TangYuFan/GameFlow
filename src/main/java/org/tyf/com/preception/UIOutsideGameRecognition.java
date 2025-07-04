package org.tyf.com.preception;


import org.opencv.core.Mat;
import org.tyf.com.componet.UiComponentEntity;
import org.tyf.com.componet.UiComponentManager;
import org.tyf.com.config.Config;
import org.tyf.com.framework.*;
import org.tyf.com.state.UiOutsideGameState;
import org.tyf.com.tinylog.TinyLog;
import org.tyf.com.util.ImageUtils;
import org.tyf.com.util.Point2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


/**
 *   @desc : UI 元素识别
 *   @auth : tyf
 *   @date : 2025-06-27 17:03:28
*/
public class UIOutsideGameRecognition implements Perception {


    @Override
    public void process(VideoInput input,ControlOutput output, GameContext context) {

//        TinyLog.log(TinyLog.Level.INFO,"UI元素识别：");

        // 当前帧
        BufferedImage image = input.getCurrentFrame();
        Mat mat = ImageUtils.bufferedImageToMat(image);

        // 保存待查找UI控件
        List<UiComponentEntity> detection = new ArrayList<>();

        // 控件识别
        UiComponentManager.getComponets().entrySet().forEach(
                n->{
                    // 取控件 mat 矩形匹配，注意这里 clone 之后传进去否则会被修改
                    UiComponentEntity component = n.getValue();
                    Point2f point2f = ImageUtils.findSubImageBySIFT(mat.clone(),component.getMat().clone());
                    // 如果找到则设置坐标
                    if(point2f!=null){
                        component.setPoint2f(point2f);
                        // 添加到检出列表中
                        detection.add(component);
                    }
                }
        );

        // 管理当前可交互UI状态（UI类型为key，UI实例为value）
        detection.forEach( uiEntity -> {
            TinyLog.log(TinyLog.Level.INFO,"识别到交互对象："+uiEntity);
            String key = uiEntity.getType().name();
            UiOutsideGameState value = new UiOutsideGameState(uiEntity);
            // 状态移交给上下文
            context.setState(key,value);
            // 如果开启了可视化则绘制
            if(Config.visualization){
                ImageUtils.drawPolygonOnImage(image,uiEntity.getPoint2f());
            }
        });

        // 如果开启了可视化
        if(Config.visualization){
            ImageViewer.setFrame(image);
        }

        mat.release();
    }

}
