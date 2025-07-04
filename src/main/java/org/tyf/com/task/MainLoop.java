package org.tyf.com.task;


import org.tyf.com.config.Config;
import org.tyf.com.framework.*;
import org.tyf.com.framework.StateManager;
import org.tyf.com.tinylog.TinyLog;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *   @desc : 游戏主循环
 *   @auth : tyf
 *   @date : 2025-06-27 17:37:49
*/
public class MainLoop implements Runnable {

    // 控制运行
    private final AtomicBoolean working = new AtomicBoolean(true);

    // 所有模块
    private final List<Perception> perceptionModules;
    private final List<Rule> ruleModules;
    private final VideoInput videoInput; // 视频帧输入源
    private final ControlOutput controlOutput; // UI控制输出源
    private final GameContext context;
    private final long frameIntervalMillis;

    public MainLoop(List<Perception> perceptionModules,
                    List<Rule> ruleModules,
                    VideoInput videoInput,
                    ControlOutput controlOutput,
                    GameContext context,
                    long frameIntervalMillis) {
        this.perceptionModules = perceptionModules;
        this.ruleModules = ruleModules;
        this.videoInput = videoInput;
        this.controlOutput = controlOutput;
        this.context = context;
        this.frameIntervalMillis = frameIntervalMillis;

        // 启动自检
        this.selfCheck();

        // 启用可视化
        this.visualization();

    }

    /**
     * 启动循环
     */
    @Override
    public void run() {

        while (working.get()) {

//            TinyLog.log(TinyLog.Level.INFO,"----------Loop frame----------");

            long start = System.currentTimeMillis();

            // 1. 视觉感知阶段（更新 GameContext）
            for (Perception v : perceptionModules) {
                v.process(videoInput,controlOutput, context);
            }

            // 用于调试，打印感知阶段的所有感知结果
//            context.debug();

            // 3. 规则匹配阶段，前面已经通过感知器搜集了状态
            for (Rule rule : ruleModules) {
                if (rule.match(context)) {
                    TinyLog.log(TinyLog.Level.INFO,"[规则命中] "+rule.getClass().getSimpleName());
                    // 执行操作需要传入控制输出实现
                    rule.execute(context,controlOutput);
                    if (rule.skipOtherRule()) {
                        break; // 当前规则要求阻止后续规则执行
                    }
                }
            }

            // 4.控制帧率
            long elapsed = System.currentTimeMillis() - start;
            long sleepTime = frameIntervalMillis - elapsed;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ignored) {
                }
            }

            // 5.重置上下文
            context.reset();

        }
    }


    // 功能自检
    public void selfCheck(){

        TinyLog.log(TinyLog.Level.INFO,"启动自检..");
//        this.videoInput.selfCheck();
//        this.controlOutput.selfCheck();

    }


    // 启用帧处理可视化
    public void visualization(){
        if(Config.visualization){
            ImageViewer.init("Vision",videoInput.getWidth()/2,videoInput.getHeight()/2);
        }
    }


    // 退出 loop
    public void release(){
        working.set(false);
        TinyLog.log(TinyLog.Level.INFO,"MainLoop releae");
    }

}
