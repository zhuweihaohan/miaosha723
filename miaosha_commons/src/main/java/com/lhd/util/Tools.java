package com.lhd.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Random;
import java.util.UUID;

public class Tools {
   private static char[] operator=new char[]{'+','-','*'};
   private static char[] number="0123456789".toCharArray();
  private static Random random=new Random();
    /**
     * 生成uuid码
     * @return
     */
    public static String uuid(){
        String str= UUID.randomUUID().toString();
        return str.replace("-","");
    }

    /**
     * 随机生成表达式
     * @return
     */
    public static String makeExp(){
return ""+number[random.nextInt(10)]+operator[random.nextInt(3)]+number[random.nextInt(10)];
    }

    public static Integer eval(String exp){
        ScriptEngineManager scriptEngineManager=new ScriptEngineManager();
        ScriptEngine scriptEngine=scriptEngineManager.getEngineByName("javascript");
        try {
            return (Integer) scriptEngine.eval(exp);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }


}
