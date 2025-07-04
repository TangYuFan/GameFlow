package org.tyf.com.componet;

import org.tyf.com.util.ImageUtils;

import java.util.HashMap;
import java.util.Map;


/**
 *   @desc : ui 组件管理器
 *   @auth : tyf
 *   @date : 2025-07-01 13:57:48
*/
public class UiComponentManager {

    // 所有可交互的组件都在这里面
    private static Map<UiComponentType, UiComponentEntity> componets = new HashMap<>();

    // 提前截取的可交互UI图片，注意有的UI时动态的可能需要截取多张并在这里注册
    public static void init (){

        // 游戏外可交互UI，例如备战、开始、退出等非对局内的交互
        componets.put(UiComponentType.qxbz,new UiComponentEntity(UiComponentType.qxbz,"前行备战",ImageUtils.imread(System.getProperty("user.dir")+"\\componet\\qxbz.png")));
        componets.put(UiComponentType.djkbtg,new UiComponentEntity(UiComponentType.djkbtg,"点击空白跳过",ImageUtils.imread(System.getProperty("user.dir")+"\\componet\\djkbtg.png")));
        componets.put(UiComponentType.cf,new UiComponentEntity(UiComponentType.cf,"出发",ImageUtils.imread(System.getProperty("user.dir")+"\\componet\\cf.png")));
        componets.put(UiComponentType.ksxd,new UiComponentEntity(UiComponentType.ksxd,"开始行动",ImageUtils.imread(System.getProperty("user.dir")+"\\componet\\ksxd.png")));
        componets.put(UiComponentType.lhdb,new UiComponentEntity(UiComponentType.lhdb,"零号大坝",ImageUtils.imread(System.getProperty("user.dir")+"\\componet\\lhdb.png")));
        componets.put(UiComponentType.qrzp1,new UiComponentEntity(UiComponentType.qrzp1,"确认装配",ImageUtils.imread(System.getProperty("user.dir")+"\\componet\\qrzp1.png")));
        componets.put(UiComponentType.qrzp2,new UiComponentEntity(UiComponentType.qrzp2,"确认装配2",ImageUtils.imread(System.getProperty("user.dir")+"\\componet\\qrzp2.png")));
        componets.put(UiComponentType.qr,new UiComponentEntity(UiComponentType.qr,"确认",ImageUtils.imread(System.getProperty("user.dir")+"\\componet\\qr.png")));

        // 游戏内可交互UI（地图、退出、开火等等）

    }


    public static Map<UiComponentType, UiComponentEntity> getComponets() {
        return componets;
    }




}
