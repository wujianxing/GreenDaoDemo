package com.mundane.mygreendaodemo.db;

import android.content.Context;
import com.mundane.mygreendaodemo.dao.DaoMaster;
import com.mundane.mygreendaodemo.dao.DaoSession;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by mundane on 2016/10/16.
 */

public class DbCore {
    private static final String DEFAULT_DB_NAME = "green-dao3.db";
    private static DaoMaster daoMaster;
    private static DaoSession daoSessicon;

    private static Context mContext;
    private static String DB_NAME;


    public static void init(Context context) {
        init(context, DEFAULT_DB_NAME);
    }


    public static void init(Context context, String dbName) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        mContext = context;
        DB_NAME = dbName;
    }


    public static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new MyOpenHelper(mContext, DB_NAME);
            daoMaster = new DaoMaster(helper.getWritableDb());
        }
        return daoMaster;
    }


    public static DaoSession getDaoSessicon() {
        if (daoSessicon == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSessicon = daoMaster.newSession();
        }
        return daoSessicon;
    }


    public static void enableQueryBuilderLog() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

}
