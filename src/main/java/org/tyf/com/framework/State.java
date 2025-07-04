package org.tyf.com.framework;

/**
 * 通用状态接口
 * @param <K> 状态唯一标识类型（如 String、Enum、Class 等）
 * @param <T> 状态数据类型
 */
public interface State<T> {

    /**
     * 获取状态值
     */
    T getValue();

}