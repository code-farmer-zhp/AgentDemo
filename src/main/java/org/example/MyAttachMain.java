package org.example;

import com.sun.tools.attach.VirtualMachine;

public class MyAttachMain {
    public static void main(String[] args) {
        VirtualMachine vm = null;
        try {
            vm = VirtualMachine.attach("76293");//MyBizMain进程ID
            vm.loadAgent("/Users/zhp/Documents/AgentDemo/target/agent-demo-jar-with-dependencies.jar");//java agent jar包路径
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}