package com.zookeeper;
import com.zookeeper.watcher.Client;

import java.util.HashMap;


public class ZkClient1 {
    public static void main(String[]args){
        HashMap<String, String> testMap = new HashMap<String, String>();
        testMap.put("a", "123");
        System.out.println(testMap.toString());
        testMap.put("a", "asdafsdfasdfasdf");
        System.out.println(testMap.toString());
        Client client = Client.getInstance();
        client.initZkClient("168.2.9.156:2181,168.2.9.157:2181,168.2.9.158:2181",120000,"/zka");
        client.dataMaker(0, "ZkClient1");
    }

}
