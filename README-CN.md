# # ![Logo](https://raw.githubusercontent.com/uestccokey/EZPermission/master/logo.png)
# EZPermission

一款小巧易用的（<20KB）Android运行时权限框架，兼容Android O

[English](README.md)

[ ![Download](https://api.bintray.com/packages/uestccokey/maven/EZPermission/images/download.svg) ](https://bintray.com/uestccokey/maven/EZPermission/_latestVersion)

### 功能

1.支持检查权限是否可用

2.支持回调方式申请权限

3.支持批量申请权限

4.兼容Android O

### 使用

#### Gradle

``` gradle
dependencies {
    compile 'cn.ezandroid:EZPermission:1.0.3' // Gradle 3.0以下
    // 或者
    implementation 'cn.ezandroid:EZPermission:1.0.3' // Gradle3.0及以上
}
```

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
        public void onPermissionGranted(Permission grantedPermission) {
            // 同意申请
        }

        @Override
        public void onPermissionDenied(Permission deniedPermission) {
            // 拒绝申请
        }

        @Override
        public void onAllPermissionsGranted() {
            // 权限全部申请成功
        }
});
```


