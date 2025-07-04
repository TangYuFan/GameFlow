package org.tyf.com.framework;

import org.tyf.com.tinylog.TinyLog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @desc : 泛型缓存状态管理器
 * @auth : tyf
 * @date : 2025-07-01 18:33:06
 */
public class StateManager {

    private final Map<String, State<?>> states = new ConcurrentHashMap<>();

    // 设置一个缓存
    public void setState(String key, State<?> state) {
        states.put(key, state);
    }

    // 获取状态对象（原始状态）
    @SuppressWarnings("unchecked")
    public <S extends State<?>> S getState(String key, Class<S> stateClass) {
        State<?> state = states.get(key);
        if (state != null && stateClass.isInstance(state)) {
            return stateClass.cast(state);
        }
        return null;
    }


    // 移除状态
    public void remove(String key) {
        states.remove(key);
    }


    // 打印一下所有状态
    public void showAllState(){
        states.entrySet().forEach(entry->{
            TinyLog.log(TinyLog.Level.INFO,"[状态标识]："+entry.getKey()+"，状态值："+entry.getValue().getClass().getSimpleName());
        });
    }

    // 清空所有状态
    public void clear() {
        states.clear();
    }



}
