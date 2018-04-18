package com.zookeeper.watcher;


import org.apache.log4j.Logger;
import org.apache.zookeeper.Watcher;

public class WatherRunnable implements Runnable {
    private static final Logger LOG = Logger.getLogger(Watcher.class);
    private Client client;
    private int num = 1;
    private String name = "default";
    public WatherRunnable(Client client, int num, String name){
        this.client = client;
        this.num = num;
        this.name = name;
    }
    public void run() {
        client.initZkClient("168.2.9.156:2181,168.2.9.157:2181,168.2.9.158:2181",120000,"/zka");
        client.dataMaker(this.num,this.name);
        LOG.info(" task finished , the current thread name as :" + this.name);
    }
}
