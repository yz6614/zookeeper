package com.zookeeper;

import com.zookeeper.watcher.Client;
import com.zookeeper.watcher.WatherRunnable;

public class zkClientThread {
    public static void main(String[]args){
        Thread thread1 = new Thread(new WatherRunnable(Client.getInstance(), 1,"Client-1"), "Client-1");
        Thread thread2 = new Thread(new WatherRunnable(Client.getInstance(), 20,"Client-20"), "Client-20");
        thread1.start();
        thread2.start();
    }
}
