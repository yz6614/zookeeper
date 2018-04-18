package com.zookeeper.watcher;

import org.apache.zookeeper.*;
import static org.apache.zookeeper.KeeperException.Code.*;
import org.apache.zookeeper.data.Stat;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import org.apache.log4j.Logger;



public class Client implements Watcher, AsyncCallback.StatCallback{
    private ZooKeeper zk;
    private boolean dead;
    private String znode;
    private Stat stat;
    private int index;
    private volatile static Client instance;
    static volatile int a = 0;
    private static final Logger LOG = Logger.getLogger(Client.class);

    private Client(){

        System.out.println(new StringBuilder("the zk object hs been established !!! HASHCODE: ").append(this.hashCode()).toString());
    }

//    public static Client getInstance(){
//        getProcessID();
//        if(null == instance){
//            synchronized( Client.class ){
//                if(null == instance){
//                    instance = new Client();
//                }
//            }
//        }
//        return instance;
//    }

    public static Client getInstance(){
        getProcessID();
        return new Client();
    }
    public void initZkClient(String ipport,int timetout,String znode) {
        try {
            this.znode = znode;
            zk = new ZooKeeper(ipport, timetout, this);
            stat = zk.exists(znode, false);
            if(null == stat){
                zk.create(znode,new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
            }
            a += 1;
            LOG.info("the initZkClient function has been called times as :" + a);

            zk.getData(znode,this,stat); //同步调用版本 byte[] getData(String path, boolean watch,Stat stat)
        } catch (IOException e) {
            e.printStackTrace();
        }catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static final int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println(runtimeMXBean.getName());
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0])
                .intValue();
    }
    // AsyncCallback 回调处理返回结果
    /*
    * （1）0（ok）：接口调用成功。
    *（2）-4（ConnectionLoss）：客户端和服务端连接已断开。
    *（3）-110（NodeExists）：指定节点已存在。
    *（4）-112（SessionExpired）：会话已过期。
    */
    public void processResult(int rc, String s, Object o, Stat stat) {
        boolean exists;
        switch (get(rc)) {
            case OK:
                exists = true;
                break;
            case NONODE:
                exists = false;
                break;
            case SESSIONMOVED:
            case NOAUTH:
                dead = true;
                return;
            default:
                zk.exists(znode, false, this, null); //重新注册watcher异步调用版本 byte[] void getData(String path,Watcher watcher,AsyncCallback.StatCallback cb,Object ctx)
                return;
        }

        byte b[] = null;
        if (exists) {
//            try {
//               /* b = zk.getData(znode, true, null);
//                System.out.println(new String(b));*/
//            } catch (KeeperException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                return;
//            }
        }
    }
    //Watcher 处理回调 一般可用来再次加载监听（监听每次通知完必须重新注册）
    /*
    * WatchedEvent 有三个调用 getState -> KeeperState  getType -> EventType  getPath -> String
    */
    public void process(WatchedEvent event) {
        String path = event.getPath();
        if (event.getType() == Event.EventType.None) {
            switch (event.getState()) {
                case SyncConnected:
                    break;
                case Expired:
                    dead = true;
                    break;
            }
        } else {
            byte b[] = null;
            if (path != null && path.equals(znode)) {
                System.out.println(" Watcher callback execute !!!");
                zk.exists(znode, true, this, null);
                //异步调用
                try {
                    b = zk.getData(znode, true, null);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(new String(b));
            }
        }
    }

    public void dataMaker(int i, String clientName){
        index=i;
        int max=index+10;
        boolean run=true;
        while(run){
            try {
                zk.setData(znode,( new StringBuilder("Data-").append(index++).append("; ").append("clientName-").append(clientName).toString()).getBytes(), -1);
                Thread.sleep(1000*3);
                LOG.info(new StringBuilder("Data upload finished, client name:").append(clientName).toString());
            } catch (KeeperException e) {
                e.printStackTrace();
                LOG.error(e);
                run=false;
            } catch (InterruptedException e) {
                e.printStackTrace();
                LOG.error(e);
                run=false;
            } catch (Exception e){
                e.printStackTrace();
                LOG.error(e);
                run=false;
            } finally{
                if(index>max){
                    run=false;
                    try {
                        if(null != zk){
                            zk.close();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
