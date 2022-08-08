package org.example;

import com.sun.tools.attach.VirtualMachine;

public class MyAttachMain {
    public static void main(String[] args) {
        VirtualMachine vm = null;
        try {
            //进程ID
            vm = VirtualMachine.attach("76293");
            //java agent jar包路径
            vm.loadAgent("/Users/zhp/Documents/AgentDemo/target/agent-demo-jar-with-dependencies.jar");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}