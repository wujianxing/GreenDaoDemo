package com.mundane.mygreendaodemo.app;

import android.app.Application;
import com.mundane.mygreendaodemo.db.DbCore;

/**
 * Created by mundane on 2016/10/16.
 */

public class DaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //  初始化数据库
        DbCore.init(this);
        //  开启调试log
        DbCore.enableQueryBuilderLog();
    }
}
