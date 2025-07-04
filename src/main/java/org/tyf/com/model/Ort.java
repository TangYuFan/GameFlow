package org.tyf.com.model;


import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;

/**
 *   @desc : ort 环境初始化
 *   @auth : tyf
 *   @date : 2025-07-01 10:30:19
*/
public class Ort {


    // onnxruntime 环境
    public static OrtEnvironment env;


    public static OrtEnvironment getEnv(){
        if(env==null){
            env = OrtEnvironment.getEnvironment();
        }
        return env;
    }



}
