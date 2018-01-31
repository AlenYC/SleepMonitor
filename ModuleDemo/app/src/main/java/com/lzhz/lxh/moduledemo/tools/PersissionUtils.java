package com.lzhz.lxh.moduledemo.tools;

import android.content.Context;

import com.lzhz.lxh.moduledemo.R;
import com.lzhz.lxh.moduledemo.tools.interfacetool.PermissionInter;

import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

/**
 * 作者：lxh on 2018-01-09:10:13
 * 邮箱：15911638454@163.com
 * 权限管理类
 */

public class PersissionUtils {
    private static PermissionInter permissionInter;

    public static void setOnPermissionInter(PermissionInter permission){
        permissionInter = permission;
    }

    public static void setPermission(final Context context, PermissionItem permission){
        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        permissonItems.add(permission);
        HiPermission.create(context)
                .permissions(permissonItems)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                        ToastUtil.showShort(context, R.string.permission_on_close);
                        permissionInter.onClose();
                    }

                    @Override
                    public void onFinish() {
                        permissionInter.onFinish();
                    }

                    @Override
                    public void onDeny(String permission, int position) {
                        permissionInter.onDeny();
                    }

                    @Override
                    public void onGuarantee(String permission, int position) {
                        permissionInter.onGuarantee();
                    }
                });

    }
    public static void setPermissionList(final Context context, List<PermissionItem> permission){
        HiPermission.create(context)
                .permissions(permission)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                        ToastUtil.showShort(context,R.string.permission_on_close);
                        permissionInter.onClose();
                    }

                    @Override
                    public void onFinish() {
                        permissionInter.onFinish();
                    }

                    @Override
                    public void onDeny(String permission, int position) {
                        permissionInter.onDeny();
                    }

                    @Override
                    public void onGuarantee(String permission, int position) {
                        permissionInter.onGuarantee();
                    }
                });

    }
}
