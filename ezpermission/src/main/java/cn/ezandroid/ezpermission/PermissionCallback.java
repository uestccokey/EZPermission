package cn.ezandroid.ezpermission;

/**
 * 权限申请回调
 *
 * @author like
 * @date 2017-09-28
 */
public interface PermissionCallback {

    /**
     * 权限申请成功
     *
     * @param grantedPermission 申请成功的权限
     */
    default void onPermissionGranted(Permission grantedPermission) {}

    /**
     * 权限申请失败
     *
     * @param deniedPermission 申请失败的权限
     */
    default void onPermissionDenied(Permission deniedPermission) {}

    /**
     * 权限列表全部申请成功
     */
    default void onAllPermissionsGranted() {}
}
