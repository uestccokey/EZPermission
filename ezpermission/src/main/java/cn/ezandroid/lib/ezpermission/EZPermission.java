package cn.ezandroid.lib.ezpermission;

import android.content.Context;
import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 权限管理
 *
 * @author like
 * @date 2017-09-28
 */
public class EZPermission {

    private EZPermission() {
    }

    public static Builder permissions(String... permission) {
        return new Builder(new Permission(permission));
    }

    public static Builder permissions(String[]... groups) {
        List<String> permissions = new ArrayList<>();
        for (String[] group : groups) {
            permissions.addAll(Arrays.asList(group));
        }
        return new Builder(new Permission(permissions.toArray(new String[0])));
    }

    public static Builder permissions(Permission... groups) {
        List<String> permissions = new ArrayList<>();
        for (Permission permission : groups) {
            permissions.addAll(Arrays.asList(permission.getPermissions()));
        }
        return new Builder(new Permission(permissions.toArray(new String[0])));
    }

    public static class Builder {

        private Permission mPermission;

        private Builder(Permission permission) {
            this.mPermission = permission;
        }

        /**
         * 权限是否都可用
         *
         * @param context
         * @return
         */
        public boolean available(Context context) {
            return mPermission.available(context);
        }

        /**
         * 申请权限
         *
         * @param context
         */
        public void apply(final Context context) {
            apply(context, null);
        }

        /**
         * 申请权限
         *
         * @param context
         * @param callback
         */
        public void apply(final Context context, final PermissionCallback callback) {
            PermissionCallback globalCallback = new PermissionCallback() {
                int mGrantedCount = 0;
                int mRemainCount = mPermission.getPermissions().length;
                boolean mHasNoLongerPrompted; // 是否有勾选了不再提示并且拒绝的权限

                @Override
                public void onPermissionGranted(String[] grantedPermissions) {
                    if (callback != null) {
                        callback.onPermissionGranted(grantedPermissions);
                    }

                    mGrantedCount += grantedPermissions.length;

                    mRemainCount -= grantedPermissions.length;
                    if (mRemainCount <= 0) {
                        onAllComplete(mHasNoLongerPrompted);
                    }
                }

                @Override
                public void onPermissionDenied(String[] deniedPermissions, boolean isNoLongerPrompted) {
                    if (callback != null) {
                        callback.onPermissionDenied(deniedPermissions, isNoLongerPrompted);
                    }

                    mHasNoLongerPrompted = mHasNoLongerPrompted || isNoLongerPrompted;

                    mRemainCount -= deniedPermissions.length;
                    if (mRemainCount <= 0) {
                        onAllComplete(mHasNoLongerPrompted);
                    }
                }

                @Override
                public void onAllPermissionsGranted() {
                    if (callback != null) {
                        callback.onAllPermissionsGranted();
                    }
                }

                private void onAllComplete(boolean startSetting) {
                    if (mGrantedCount == mPermission.getPermissions().length) {
                        onAllPermissionsGranted();
                    } else if (startSetting) {
                        if (callback != null) {
                            callback.onStartSetting(context);
                        }
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        PermissionProxyActivity.sPermissionCallback = null; // 防止内存泄漏
                    }
                }
            };

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                globalCallback.onAllPermissionsGranted();
            } else {
                mPermission.apply(context, globalCallback);
            }
        }
    }
}
