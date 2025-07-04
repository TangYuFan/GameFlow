package org.tyf.com.framework;


/**
 *   @desc : 上下文容器”或者“状态总线”，让所有模块通过它进行交互和信息共享。
 *   @auth : tyf
 *   @date : 2025-06-30 10:25:32
*/
public class GameContext {


    // 状态管理器
    private final StateManager stateManager = new StateManager();


    // 保存一个状态
    public void setState(String key, State<?> state) {
        stateManager.setState(key,state);
    }

    // 获取一个状态，带类型
    public <T extends State<?>> T getState(String key, Class<T> clazz) {
        return stateManager.getState(key, clazz);
    }

    // 上下文每执行一此 step 就重置一下
    public void reset() {
        stateManager.clear();
    }


    // 用于调试，下面打印所有感知阶段的感知结果
    public void debug(){
        stateManager.showAllState();
    }

}
