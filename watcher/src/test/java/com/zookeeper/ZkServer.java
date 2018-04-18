package com.zookeeper;
import com.zookeeper.watcher.Conf;
import com.zookeeper.watcher.Server;

import java.io.IOException;

public class ZkServer {
    public static void main(String[]args){
        Conf conf = new Conf();
        conf.DIR="/home/xkorey/zkc";
        conf.PORT=22801;
        conf.TICK_TIME=2000;
        conf.MAX_CLIENT_CONNECTIONS=60;
        Server srv = new Server();
        try {
            srv.zkStart(conf);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

