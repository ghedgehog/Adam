package com.sunway.model;

public class IoUaServer {

    private String name;
    private String description;
    private String ip;
    private String port;
    private boolean status;
    private int mark;  //1-insert 2-update 3-delete 4-done(采集服务处理完毕)
    private String reserved;
}
