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
     * @param grantPermissions 申请成功的权限列表
     */
    void onPermissionsGranted(String[] grantPermissions);

    /**
     * 权限申请失败
     *
     * @param deniedPermissions 申请失败的权限列表
     */
    void onPermissionsDenied(String[] deniedPermissions);
}
