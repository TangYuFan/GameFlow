# FlowGamer（自动化游戏交互研究与学习）

本项目是一个关于自动化游戏行为的探索性脚本系统，目标是实现从一场游戏开局到结算的全流程自动化操作，例如自动匹配入局、判断对局开始、局内战斗、物资搜索、执行挂机行为、识别结算界面并返回大厅等。

## 项目说明

> **⚠️ 声明：**
>
> 本项目并非基于对市面上已有商业自动化脚本的分析或逆向所得，当前所采用的设计方式和实现方案，均为本人基于已有计算机视觉、自动化控制等知识自行推导、设计与实现的。
本项目具有探索性和学习性质，目的是建立一套属于个人理解的自动化脚本结构体系，验证其可行性与灵活性。


## 技术方案（持续更新）

本项目将围绕游戏自动化流程，从视觉感知、输入控制、行为决策等多个方向进行探索。可能涉及的技术领域包括但不限于：

- 图像识别与视觉感知
- 用户界面识别与状态判断
- 输入事件模拟与控制
- 状态机与行为树建模
- 路径规划与地图建模
- 场景理解与上下文切换
- 自动化流程设计与任务调度
- 深度学习与目标检测
- 强化学习与策略优化
- 人类行为仿真与轨迹生成
- 数据采集与行为回放
- 多模块系统设计与可插拔架构
- 基于规则驱动的行为回跳控制

> 注：本人并非从事该方向的专业开发人员，本项目主要出于个人兴趣和知识积累所发起。如有更专业或更高效的实现方式，欢迎提出宝贵建议或批评指正。


---

## 模块预建

| 模块名称           | 说明                                                         |
|--------------------|--------------------------------------------------------------|
| 视觉感知模块（vision）       | 负责游戏画面截图、界面元素识别、UI状态判定等视觉信息提取                   |
| 输入控制模块（input_control） | 模拟鼠标、键盘、触摸屏等输入操作，支持轨迹模拟与操作随机化                     |
| 状态管理模块（state_manager） | 管理游戏整体流程状态（匹配中、战斗中、结算中），负责状态切换逻辑               |
| 决策模块（decision_engine）   | 根据状态与视觉信息做行为决策，支持规则决策与智能体集成                         |
| 任务调度模块（task_runner）   | 组织任务执行，调度子模块协同工作，管理流程顺序和异常处理                       |
| 路径规划模块（path_planning） | 基于地图数据进行导航路径规划，包含路径搜索与跟踪                              |
| 场景理解模块（scene_understanding） | 理解当前游戏场景上下文，如地图识别、安全区判定、环境变化检测                |
| 数据采集与回放模块（data_recorder） | 记录游戏画面与操作事件，用于调试和行为复现                                   |
| 日志与监控模块（logging_monitor） | 运行日志记录与异常捕获，支持实时状态监控和调试信息输出                         |
| 配置管理模块（config）         | 统一管理项目参数配置、路径及多环境适配信息                                   |
| 资源管理模块（assets）         | 管理图像模板、模型文件及其他静态资源                                         |
| AI模型集成模块（ai_inference） | 集成深度学习模型推理，用于目标检测、行为预测和策略生成                         |
| 异常处理与恢复模块（error_handler） | 处理异常弹窗、断线重连，保证脚本稳定运行                                     |
| 多平台适配模块（platform_adapter） | 抽象不同操作系统和设备输入输出接口，支持多平台运行                             |

  

## 开发逻辑：

[视觉感知模块] -> [状态评估模块] -> [规则引擎模块] -> [状态行为规则匹配] -> [行为控制模块] -> [游戏界面] 

## 调用示例

``` 
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
``` 

## 开发与扩展

（1）继承 VideoInput.getCurrentFrame() 接口回调每一帧画面  
（2）封装 ControlOutput 接口提供游戏内每个可交互的独立操作（例如备战、开局、开火、打药等）  
（3）在可交互 UI 管理器 UiComponentManager 中注册需要识别的组件对象（按钮、道具等）  
（4）封装组件对象的状态以及规则触发处理器（识别到 A 状态时触发规则 B，或每隔一会儿执行一下 C 等）  
（5）TODO  


## 免责声明

- 本项目不包含任何破解、篡改、绕过游戏安全机制的行为；
- 不建议将本项目用于线上正式账号、竞技环境等敏感用途；
- 禁止将本项目用于破坏游戏公平、恶意竞争、牟取不正当利益或侵犯他人权益的场景；
- 本项目主要用于学习与研究自动化与人机交互相关技术；
- 本项目不针对或关联任何特定游戏或厂商，纯属技术研究示例；
- 用户应自行承担因使用本项目可能带来的法律风险和账号安全风险；
- 开发者不对因使用本项目产生的任何直接或间接损失承担责任；
- 请确保遵守相关法律法规及游戏厂商的使用协议和服务条款。