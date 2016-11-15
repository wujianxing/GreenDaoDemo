package com.mundane.mygreendaodemo.db;

import com.mundane.mygreendaodemo.entity.User;
import org.greenrobot.greendao.AbstractDao;

/**
 * Created by mundane on 2016/10/16.
 */

public class UserHelper extends BaseDbHelper<User, Long> {

    public UserHelper(AbstractDao dao) {
        super(dao);
    }
}
