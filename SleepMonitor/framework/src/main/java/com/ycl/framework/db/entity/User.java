package com.ycl.framework.db.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * ycl on 2015/10/14.   关联表Customer
 */
@DatabaseTable
public class User extends DBEntity {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String password;
    @ForeignCollectionField(eager = true)    //必须是ForeignCollection<>
    private ForeignCollection<Customer> customers;//

    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ForeignCollection<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ForeignCollection<Customer> customers) {
        this.customers = customers;
    }
}
