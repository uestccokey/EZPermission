package cn.ezandroid.ezpermission;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

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
        public void apply(final Context context, final PermissionCallback callback) {
            PermissionCallback globalCallback = new PermissionCallback() {
                int mGrantedCount = 0;
                int mRemainCount = mPermissionGroups.length;
                boolean mHasNoLongerPrompted;// 是否有勾选了不再提示并且拒绝的权限

                @Override
                public void onPermissionGranted(Permission grantedPermission) {
                    if (callback != null) {
                        callback.onPermissionGranted(grantedPermission);
                    }

                    mGrantedCount++;

                    mRemainCount--;
                    if (mRemainCount <= 0) {
                        onAllComplete(mHasNoLongerPrompted);
                    }
                }

                @Override
                public void onPermissionDenied(Permission deniedPermission, boolean isNoLongerPrompted) {
                    if (callback != null) {
                        callback.onPermissionDenied(deniedPermission, isNoLongerPrompted);
                    }

                    mHasNoLongerPrompted = mHasNoLongerPrompted || isNoLongerPrompted;

                    mRemainCount--;
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
                    if (mGrantedCount == mPermissionGroups.length) {
                        onAllPermissionsGranted();
                    } else if (startSetting) {
                        startSetting();
                    }
                }

                /**
                 * 跳转到应用设置页
                 */
                private void startSetting() {
                    try {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + context.getPackageName()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 非Activity的Context必须加此参数
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // try住异常，以防找不到应用设置页的情况
                    }
                }
            };

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                globalCallback.onAllPermissionsGranted();
            } else {
                for (Permission permission : mPermissionGroups) {
                    permission.apply(context, globalCallback);
                }
            }
        }
    }
}
