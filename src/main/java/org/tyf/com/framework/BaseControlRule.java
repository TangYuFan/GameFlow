package org.tyf.com.framework;




/**
 *   @desc : 规则行为基类，由外部自定义 “当符合某种情况下执行某种操作”
 *   @auth : tyf
 *   @date : 2025-06-30 10:31:47
*/
public abstract class BaseControlRule implements Rule {

    protected Control control;

    /**
     * 可传入多个控制器初始化
     */
    public BaseControlRule(Control control) {
        if (control != null) {
            this.control = control;
        }
    }


    /**
     * 子类必须实现的规则匹配逻辑
     */
    public abstract boolean match(GameContext context);


    /**
     * 默认行为：按顺序执行所有控制器
     */
    @Override
    public void execute(GameContext context,ControlOutput output) {
        control.execute(context,output);
    }


}
