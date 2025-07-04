package org.tyf.com.state;

import org.tyf.com.componet.UiComponentEntity;
import org.tyf.com.framework.State;

/**
 *   @desc : 可交互 UI 组件状态
 *   @auth : tyf
 *   @date : 2025-07-01 17:25:18
*/
public class UiOutsideGameState implements State<UiComponentEntity> {

    UiComponentEntity entity;

    public UiOutsideGameState(UiComponentEntity entity) {
        this.entity = entity;
    }

    @Override
    public UiComponentEntity getValue() {
        return entity;
    }


}