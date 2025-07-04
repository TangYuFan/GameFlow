package org.tyf.com.control;

import org.tyf.com.componet.UiComponentType;
import org.tyf.com.framework.Control;
import org.tyf.com.framework.ControlOutput;
import org.tyf.com.framework.GameContext;
import org.tyf.com.state.UiOutsideGameState;
import org.tyf.com.util.Point2f;

import java.util.Arrays;
import java.util.Optional;


/**
 *   @desc : 前行备战
 *   @auth : tyf
 *   @date : 2025-07-02 18:05:27
*/
public class UiOutsideGameControl implements Control {


    @Override
    public void execute(GameContext context,ControlOutput output) {

        // 任意大厅或游戏外可交互 UI
        UiOutsideGameState qxbz = context.getState(UiComponentType.qxbz.name(), UiOutsideGameState.class);
        UiOutsideGameState djkbtg = context.getState(UiComponentType.djkbtg.name(), UiOutsideGameState.class);
        UiOutsideGameState cf = context.getState(UiComponentType.cf.name(), UiOutsideGameState.class);
        UiOutsideGameState ksxd = context.getState(UiComponentType.ksxd.name(), UiOutsideGameState.class);
        UiOutsideGameState lhdb = context.getState(UiComponentType.lhdb.name(), UiOutsideGameState.class);
        UiOutsideGameState qrzp1 = context.getState(UiComponentType.qrzp1.name(), UiOutsideGameState.class);
        UiOutsideGameState qrzp2 = context.getState(UiComponentType.qrzp2.name(), UiOutsideGameState.class);
        UiOutsideGameState qr = context.getState(UiComponentType.qr.name(), UiOutsideGameState.class);


        Optional.ofNullable(qxbz).ifPresent(s -> {
            Point2f point2f = s.getValue().getPoint2f();
            output.qxbz(point2f);
        });

        Optional.ofNullable(djkbtg).ifPresent(s -> {
            Point2f point2f = s.getValue().getPoint2f();
            output.djkbtg(point2f);
        });

        Optional.ofNullable(cf).ifPresent(s -> {
            Point2f point2f = s.getValue().getPoint2f();
            output.cf(point2f);
        });

        Optional.ofNullable(ksxd).ifPresent(s -> {
            Point2f point2f = s.getValue().getPoint2f();
            output.ksxd(point2f);
        });

        Optional.ofNullable(lhdb).ifPresent(s -> {
            Point2f point2f = s.getValue().getPoint2f();
            output.lhdb(point2f);
        });

        Optional.ofNullable(qrzp1).ifPresent(s -> {
            Point2f point2f = s.getValue().getPoint2f();
            output.qrzp(point2f);
        });

        Optional.ofNullable(qrzp2).ifPresent(s -> {
            Point2f point2f = s.getValue().getPoint2f();
            output.qrzp(point2f);
        });

        Optional.ofNullable(qr).ifPresent(s -> {
            Point2f point2f = s.getValue().getPoint2f();
            output.qr(point2f);
        });

    }


}
