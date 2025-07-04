package org.tyf.com.framework;

/**
 * @desc : 感知接口（支持前置处理、视频帧识别、后置处理）
 * @auth : tyf
 * @date : 2025-06-27 17:32:16
 */
public interface Perception {

    /**
     * 前置处理：可选实现
     */
    default void preProcess(GameContext context) {
        // 默认无操作
    }

    /**
     * 感知操作
     */
    void process(VideoInput input, ControlOutput output,GameContext context);

    /**
     * 后置处理：
     */
    default void postProcess(GameContext context) {
        // 默认无操作
    }


}
