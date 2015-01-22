package com.example.badya.androidcloud.DBWork;

import java.util.List;

/**
 * Created by Ruslan on 22.01.2015.
 */

public interface DAO<T> {
    public long save(DBHelper db);
    public long delete(DBHelper db);
}
