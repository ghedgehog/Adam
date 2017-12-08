package com.sunway.utils;

public class Utils {

    // 表更新标记位 INT -- 1-insert 2-update 3-delete 4-done(采集服务处理完毕)
    public enum Mark
    {
        insert, update, delete , done;
    }
}
