package com.github.xhrg.java.agent.jar;

import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

public class PreMainAgent {

    public static void premain(String agentParam, Instrumentation inst) {
        System.out.println("premain1 运行" + agentParam);
        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            @Override
            public Builder<?> transform(Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader,
                    JavaModule module, ProtectionDomain protectionDomain) {
                return builder.method(ElementMatchers.<MethodDescription>any())
                        .intercept(MethodDelegation.to(TimerIntercept.class));
            }
        };
        new AgentBuilder.Default()
                .type(ElementMatchers.<TypeDescription>nameStartsWith("com.github.xhrg.java.agent.jar.demo"))
                .transform(transformer).installOn(inst);
    }

    /**
     * 当存在上面这个方法的时候，该方法不会被执行到
     * 
     * @param agentParam
     */
    public static void premain(String agentParam) {
        System.out.println("premain2 运行" + agentParam);
    }
}
