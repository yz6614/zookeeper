package com.zookeeper;

import com.zookeeper.watcher.Client;
public class ZkClient2 {
    public static void main(String[]args){
        Client client = Client.getInstance();
        client.initZkClient("168.2.9.156:2181,168.2.9.157:2181,168.2.9.158:2181",120000,"/zka");
        client.dataMaker(20,"ZkClient20");
    }
}

