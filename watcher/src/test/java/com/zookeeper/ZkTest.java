package com.zookeeper;

import com.zookeeper.watcher.Client;
import com.zookeeper.watcher.Conf;
import com.zookeeper.watcher.Server;
import com.zookeeper.watcher.WatherRunnable;
import junit.framework.TestCase;
import org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class ZkTest extends TestCase{
//    @Test
//    public void testSrv(){
//        Conf conf = new Conf();
//        conf.DIR="/home/xkorey/zkc";
//        conf.PORT=2181;
//        conf.TICK_TIME=2000;
//        conf.MAX_CLIENT_CONNECTIONS=60;
//        Server srv = new Server();
//        try {
//            srv.zkStart(conf);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testClient(){
        Thread thread1 = new Thread(new WatherRunnable(Client.getInstance(), 1,"Client-1"), "Client-1");
        Thread thread2 = new Thread(new WatherRunnable(Client.getInstance(), 20,"Client-20"), "Client-20");
        thread1.start();
        thread2.start();
    }


//    @Test
//    public void testClient1(){
//        Client client = Client.getInstance();
//        client.initZkClient("168.2.9.156:2181",3000,"/zkc");
//        client.dataMaker(1, "client1");
//    }
//
//    @Test
//    public void testClient20(){
//        Client client = Client.getInstance();
//        client.initZkClient("168.2.9.157:2181",3000,"/zk1");
//        client.dataMaker(20, "client20");
//    }
}
