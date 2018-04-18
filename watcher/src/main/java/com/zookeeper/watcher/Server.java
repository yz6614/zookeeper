package com.zookeeper.watcher;

import org.apache.zookeeper.server.NIOServerCnxnFactory;
import org.apache.zookeeper.server.ZooKeeperServer;
import org.apache.zookeeper.server.persistence.FileTxnSnapLog;


import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    NIOServerCnxnFactory serverCnxnFactory;

    public void zkStart(Conf conf) throws IOException, InterruptedException {
        ZooKeeperServer zkServer = new ZooKeeperServer();
        File dataDirectory = new File(conf.DIR);
        FileTxnSnapLog ftxn = new FileTxnSnapLog(dataDirectory, dataDirectory);
        zkServer.setTxnLogFactory(ftxn);
        zkServer.setTickTime(conf.TICK_TIME);
        serverCnxnFactory = new NIOServerCnxnFactory();
        serverCnxnFactory.configure(new InetSocketAddress(conf.PORT), conf.MAX_CLIENT_CONNECTIONS);
        serverCnxnFactory.startup(zkServer);
    }
}
