package com.mundane.mygreendaodemo.db;

import android.content.Context;
import com.mundane.mygreendaodemo.dao.DaoMaster;
import com.mundane.mygreendaodemo.dao.UserDao;
import org.greenrobot.greendao.database.Database;

/**
 * Created by mundane on 2016/10/16.
 */

public class MyOpenHelper extends DaoMaster.OpenHelper {

    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }


    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        switch (oldVersion) {
            case 1:
                UserDao.createTable(db, true);
                //  加入新字段 address
                db.execSQL("ALTER TABLE 'STUDENT' ADD 'ADDRESS' TEXT;");
                break;
        }
    }
}
