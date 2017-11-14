package cn.ezandroid.ezpermission;

import android.app.Activity;
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

                @Override
                public void onPermissionGranted(Permission grantedPermission) {
                    if (callback != null) {
                        callback.onPermissionGranted(grantedPermission);
                    }

                    mGrantedCount++;

                    mRemainCount--;
                    if (mRemainCount <= 0) {
                        onAllComplete();
                    }
                }

                @Override
                public void onPermissionDenied(Permission deniedPermission) {
                    if (callback != null) {
                        callback.onPermissionDenied(deniedPermission);
                    }

                    mRemainCount--;
                    if (mRemainCount <= 0) {
                        onAllComplete();
                    }
                }

                @Override
                public void onAllPermissionsGranted() {
                    if (callback != null) {
                        callback.onAllPermissionsGranted();
                    }
                }

                private void onAllComplete() {
                    if (mGrantedCount == mPermissionGroups.length) {
                        onAllPermissionsGranted();
                    } else if (isAlwaysRefusePermission(context)) {
                        // 跳转到应用设置页
                        try {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.parse("package:" + context.getPackageName());
                            intent.setData(uri);
                            context.startActivity(intent);
                        } catch (Exception e) {
                            // try住异常，以防找不到应用设置页的情况
                        }
                    }
                }

                /**
                 * 是否用户选择了不再提醒
                 *
                 * @param activity
                 * @return
                 */
                private boolean isAlwaysRefusePermission(Context activity) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity instanceof Activity) {
                        for (Permission permission : mPermissionGroups) {
                            if (!permission.available(activity)) {
                                for (String p : permission.getPermissions()) {
                                    boolean rationale = ((Activity) activity).shouldShowRequestPermissionRationale(p);
                                    if (!rationale) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    return false;
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
