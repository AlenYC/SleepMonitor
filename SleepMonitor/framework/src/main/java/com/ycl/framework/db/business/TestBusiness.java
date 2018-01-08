package com.ycl.framework.db.business;

import android.content.Context;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.ycl.framework.db.entity.TestEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestBusiness extends BaseDbBusiness<TestEntity> {
    private static TestBusiness instance = null;

    private TestBusiness(Context context) {
        super(context);
        try {
            dao = helper.getDao(TestEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static TestBusiness getInstance(Context context) {
        if (instance == null) {
            instance = new TestBusiness(context);
        }
        return instance;
    }


    public TestBusiness findImageIndex(String url) {
        QueryBuilder<TestBusiness, Integer> qb = dao.queryBuilder();
        List<TestBusiness> list = new ArrayList<TestBusiness>();
        try {
            Where<TestBusiness, Integer> where1 = qb.where().eq("remote", url);
            where1.or();
            where1.eq("storage", url);
            list = dao.query(qb.prepare());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dao.clearObjectCache();
            try {
                dao.closeLastIterator();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    public TestBusiness findImagStorageUrl(String url) {
        QueryBuilder<TestBusiness, Integer> qb = dao.queryBuilder();
        List<TestBusiness> list;
        try {
            Where<TestBusiness, Integer> where1 = qb.where().eq("storage", url);
            where1.and();
            where1.eq("imageType", 1);
            list = dao.query(qb.prepare());
            if (list.size() > 0) {
                return list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dao.clearObjectCache();
            try {
                dao.closeLastIterator();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public void deleteImageIndexUrl(String url) {
        DeleteBuilder<TestBusiness, Integer> deleteBuilder = dao.deleteBuilder();
        try {
            deleteBuilder.where().eq("remote", url);
            deleteBuilder.delete();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dao.clearObjectCache();
            try {
                dao.closeLastIterator();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //删除   根据Storage
    public void deleteImageStorageUrl(String url) {
        DeleteBuilder<TestBusiness, Integer> deleteBuilder = dao.deleteBuilder();
        try {
            deleteBuilder.where().eq("storage", url);
            deleteBuilder.delete();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dao.clearObjectCache();
            try {
                dao.closeLastIterator();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
