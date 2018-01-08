package com.ycl.framework.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *  by Administrator on 2015/10/14.
 */
@DatabaseTable
public class Customer extends DBEntity {
    @DatabaseField(generatedId = true) //主键自增加
    private int id;
    @DatabaseField     //映射属性
    private String name;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "user_id")
    private User user; // 略去get set


    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
