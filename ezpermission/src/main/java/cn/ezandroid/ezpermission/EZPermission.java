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
        public void apply(Context context, final PermissionCallback callback) {
            PermissionCallback globalCallback = new PermissionCallback() {
                int mGrantedCount = 0;

                @Override
                public void onPermissionGranted(Permission grantedPermission) {
                    if (callback != null) {
                        callback.onPermissionGranted(grantedPermission);
                    }

                    mGrantedCount++;
                    if (mGrantedCount == mPermissionGroups.length) {
                        onAllPermissionsGranted();
                    }
                }

                @Override
                public void onPermissionDenied(Permission deniedPermission) {
                    if (callback != null) {
                        callback.onPermissionDenied(deniedPermission);
                    }

                    mGrantedCount--;
                }

                @Override
                public void onAllPermissionsGranted() {
                    if (callback != null) {
                        callback.onAllPermissionsGranted();
                    }
                }
            };

            for (Permission permission : mPermissionGroups) {
                permission.apply(context, globalCallback);
            }
        }
    }
}
