package cn.ezandroid.ezpermission;

import android.content.Context;

/**
 * 权限管理
 *
 * @author like
 * @date 2017-09-28
 */
public class EZPermission {

    private EZPermission() {
    }

    public static Builder permissions(Permission... groups) {
        return new Builder(groups);
    }

    public static class Builder {

        private Permission[] mPermissionGroups;

        private Builder(Permission... groups) {
            this.mPermissionGroups = groups;
        }

        /**
         * 权限是否都可用
         *
         * @param context
         * @return
         */
        public boolean available(Context context) {
            for (Permission permission : mPermissionGroups) {
                if (!permission.available(context)) {
                    return false;
                }
            }
            return true;
        }

        /**
         * 申请权限
         *
         * @param context
         * @param callback
         */
        public void apply(Context context, PermissionCallback callback) {
            for (Permission permission : mPermissionGroups) {
                permission.apply(context, callback);
            }
        }
    }
}
