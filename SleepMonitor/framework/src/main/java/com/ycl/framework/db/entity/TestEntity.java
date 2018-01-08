package com.ycl.framework.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *  by Administrator on 2015/10/14.
 */

@DatabaseTable(tableName = "local_cache")
public class TestEntity extends DBEntity {

    @DatabaseField(generatedId = true )
    public Integer id;
    @DatabaseField
    public String key;
    @DatabaseField
    public String result;

    public TestEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

