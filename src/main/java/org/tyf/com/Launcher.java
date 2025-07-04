package org.tyf.com;

import org.tyf.com.componet.UiComponentManager;
import org.tyf.com.control.UiOutsideGameControl;
import org.tyf.com.framework.*;
import org.tyf.com.input.ScrcpyVideoInput;
import org.tyf.com.output.ScrcpyControlOutput;
import org.tyf.com.rule.UiOutsideGameRule;
import org.tyf.com.task.MainLoop;
import org.tyf.com.preception.*;
import org.tyf.com.tinylog.TinyLog;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;


/**
 *   @desc : 脚本启动器
 *   @auth : tyf
 *   @date : 2025-06-30 11:12:25
*/
public class Launcher {

    public static void main(String[] args) throws Exception{

        // 可交互UI初始化
        UiComponentManager.init();

        // 视频输入和控制输出（ scrcpy + android 方案）
        VideoInput videoInput = new ScrcpyVideoInput();
        ControlOutput controlOutput = new ScrcpyControlOutput();

        // 感知模块
        List<Perception> perceptionModules = Arrays.asList(
                new UIOutsideGameRecognition(), // 对局外 UI 元素识别（大厅、备战等）
                new DynamicEventRecognition(),  // 动态事件识别（爆炸、枪火、倒地、载具移动等）TODO
                new EnemyAndFriendDetection(),  // 敌我人物识别 TODO
                new LargeMapRecognition(),      // 大地图识别 TODO
                new MovableAreaRecognition(),   // 可行进区域识别 TODO
                new PopUpRecognition(),         // 弹窗确认识别 TODO
                new PropRecognition(),          // 道具识别 TOD
                new SceneKeypointRecognition(), // 场景关键点识别（建筑入口、梯子、封锁门、跳点、窗口等） TODO
                new SmallMapRecognition(),      // 小地图识别 TODO
                new StatusDetermination(),      // 状态栏/图标判定 TODO
                new GameTimePerception(),       // 游戏时长感知器 TODO
                new GameStartRecognition(),     // 游戏开始感知器 TODO
                new GameOverRecognition()       // 游戏结束感知器 TODO
        );

        // 路径规划器 TODO

        // 规则引擎
        List<Rule> ruleModules = Arrays.asList(
                new UiOutsideGameRule(new UiOutsideGameControl())   // 对局外 UI 元素可交互处理规则（自动备战入局）
        );

        // 启动主循环
        MainLoop loop = new MainLoop(
                perceptionModules,  // 感知模块
                ruleModules,        // 规则引擎
                videoInput,         // 视频输入
                controlOutput,      // 控制输出
                new GameContext(),  // 游戏上下文
                1                // 每帧间隔时长 ms，控制处理帧率以及操作丝滑程度
        );
        Executors.newSingleThreadExecutor().execute(loop);

        // 资源清理
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            TinyLog.log(TinyLog.Level.INFO,"exit..");
            videoInput.release();
            controlOutput.release();
            loop.release();
        }));
    }

}
