package com.ismartlib.framework.permissions;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃永无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * Created by  Administrator  on 2016/11/15.
 * Email:924686754@qq.com
 * 权限申请单例
 */
public class Permission {
    private static Permission mPermission;
    private Context mContext;
    private ResultCallBack mResultCallBack;


    public Permission(Context mContext) {
        this.mContext = mContext;
    }

    public static Permission getInstance(Context context) {
        if (mPermission == null) {
            mPermission = new Permission(context.getApplicationContext());
        }
        return mPermission;
    }

    private String[] mPermissions = null;

    public Permission requestPermission(String... permissions) {
        mPermissions = permissions;
        return this;
    }

    public Permission results(ResultCallBack resultCallBack) {
        mResultCallBack = resultCallBack;
        if (Build.VERSION.SDK_INT >= 23) {
            String[] newPermissions = filterPermision();
            if (newPermissions != null && newPermissions.length > 0) {
                Intent intent = new Intent(mContext, RequestActivity.class);
                intent.putExtra("permissions", newPermissions);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        } else if (mResultCallBack != null) {
            for (int i = 0; i < mPermissions.length; i++) {
                mResultCallBack.result(new PermissionsResult(mPermissions[i], true));
            }
        }
        return this;
    }

    /**
     * 是否获取到该权限
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    public boolean getPermisionState(String permission) {
        if ((mContext.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)) {
            //没有权限的
            return true;
        }
        return false;
    }

    /**
     * 过滤掉已有的权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    private String[] filterPermision() {
        String[] newPermissions = null;
        List<String> permissionList = new ArrayList<>();
        if (mPermissions != null) {
            for (int i = 0; i < mPermissions.length; i++) {
                if (!(mContext.checkSelfPermission(mPermissions[i]) == PackageManager.PERMISSION_GRANTED)) {
                    //没有权限的
                    permissionList.add(mPermissions[i]);
                } else {
                    //有权限的先回调出去
                    if (mResultCallBack != null) {
                        mResultCallBack.result(new PermissionsResult(mPermissions[i], true));
                    }
                }
            }
            //重新装入数组对象
            if (permissionList.size() > 0) {
                newPermissions = new String[permissionList.size()];
                for (int i = 0; i < permissionList.size(); i++) {
                    newPermissions[i] = permissionList.get(i);
                }
            }
        }
        return newPermissions;
    }


    /**
     * 这是申请权限的activity页面调用的,别看见public就三七二十一调了再说
     *
     * @param permission
     * @param granted
     */
    public void setPermission(String permission, boolean granted) {
        if (mResultCallBack != null) {
            mResultCallBack.result(new PermissionsResult(permission, granted));
        }
    }

}
