package com.mundane.mygreendaodemo.db;

import com.mundane.mygreendaodemo.dao.UserDao;

/**
 * Created by mundane on 2016/10/16.
 */

public class DbUtil {
    private static UserHelper sUserHelper;


    private static UserDao getDriveDao() {
        return DbCore.getDaoSessicon().getUserDao();
    }


    public static UserHelper getDriveHelper() {
        if (sUserHelper == null) {
            sUserHelper = new UserHelper(getDriveDao());
        }
        return sUserHelper;
    }
}
