# # ![Logo](https://raw.githubusercontent.com/uestccokey/EZPermission/master/logo.png)
# EZPermission

一款小巧易用的（<20KB）Android运行时权限框架，兼容Android O

[English](README.md)

### 功能

1.支持检查权限是否可用

2.支持回调方式申请权限

3.支持批量申请权限

4.兼容Android O

#### 示例

检查权限是否可用

``` java
boolean available = EZPermission.permissions(Permission.CAMERA, Permission.STORAGE...)
                        .available(context);
```

申请权限

``` java
EZPermission.permissions(Permission.CAMERA, Permission.STORAGE...)
    .apply(context, new PermissionCallback() {
        @Override
        public void onPermissionGranted(String[] grantedPermissions) {
            // 同意申请
        }

        @Override
        public void onPermissionDenied(String[] deniedPermissions, boolean isNoLongerPrompted) {
            // 拒绝申请
        }

        @Override
        public void onAllPermissionsGranted() {
            // 权限全部申请成功
        }
});
```


