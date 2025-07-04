package org.tyf.com.framework;


/**
 *   @desc : 规则抽象
 *   @auth : tyf
 *   @date : 2025-06-30 10:34:57
*/
public interface Rule {
    /**
     * 是否满足规则前提条件
     */
    boolean match(GameContext context);

    /**
     * 执行规则绑定的行为
     */
    void execute(GameContext context,ControlOutput output);

    /**
     * 如果match规则时是否跳过后续规则
     */
    default boolean skipOtherRule() {
        return false;
    }



}