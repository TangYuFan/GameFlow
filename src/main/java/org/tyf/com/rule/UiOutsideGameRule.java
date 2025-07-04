package org.tyf.com.rule;

import org.tyf.com.componet.UiComponentType;
import org.tyf.com.framework.*;
import org.tyf.com.state.UiOutsideGameState;

import java.util.Arrays;
import java.util.Objects;


/**
 *   @desc : 游戏外可交互UI规则（前行备战）
 *   @auth : tyf
 *   @date : 2025-07-02 16:57:30
*/
public class UiOutsideGameRule extends BaseControlRule {


    // 传入某个控制
    public UiOutsideGameRule(Control control) {
        super(control);
    }

    @Override
    public boolean match(GameContext context) {


        UiOutsideGameState qxbz = context.getState(UiComponentType.qxbz.name(), UiOutsideGameState.class);
        UiOutsideGameState djkbtg = context.getState(UiComponentType.djkbtg.name(), UiOutsideGameState.class);
        UiOutsideGameState cf = context.getState(UiComponentType.cf.name(), UiOutsideGameState.class);
        UiOutsideGameState ksxd = context.getState(UiComponentType.ksxd.name(), UiOutsideGameState.class);
        UiOutsideGameState lhdb = context.getState(UiComponentType.lhdb.name(), UiOutsideGameState.class);
        UiOutsideGameState qrzp1 = context.getState(UiComponentType.qrzp1.name(), UiOutsideGameState.class);
        UiOutsideGameState qrzp2 = context.getState(UiComponentType.qrzp2.name(), UiOutsideGameState.class);
        UiOutsideGameState qr = context.getState(UiComponentType.qr.name(), UiOutsideGameState.class);

        // 任意一个不为空，则 match
        return Arrays.asList(qxbz, djkbtg, cf, ksxd, lhdb, qrzp1, qrzp2, qr)
                .stream()
                .anyMatch(Objects::nonNull);

    }

    @Override
    public void execute(GameContext context, ControlOutput output) {

        control.execute(context,output);

    }


    // 当前规则触发后跳过后续规则
    @Override
    public boolean skipOtherRule() {
        return true;
    }
}
