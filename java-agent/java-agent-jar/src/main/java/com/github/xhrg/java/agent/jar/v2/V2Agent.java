package com.github.xhrg.java.agent.jar.v2;

import java.lang.instrument.Instrumentation;

public class V2Agent {
    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("=========premain方法执行========");
        // -javaagent:xxxagent.jar=后携带的参数
        System.out.println(agentOps);
        inst.addTransformer(new FirstAgent());
    }
}
