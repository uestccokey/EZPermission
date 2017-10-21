# EZPermission
一款体积超小的（20KB）Android运行时权限框架，兼容Android O

 [ ![Download](https://api.bintray.com/packages/uestccokey/maven/EZPermission/images/download.svg) ](https://bintray.com/uestccokey/maven/EZPermission/_latestVersion)

### 功能介绍

1.支持检查是否拥有某个权限

2.支持回调方式申请权限

3.兼容Android O

### 依赖配置

``` gradle
dependencies {
    compile 'cn.ezandroid:EZPermission:1.0.1' // Gradle 3.0以下
    // 或者
    implementation 'cn.ezandroid:EZPermission:1.0.1' // Gradle3.0及以上
}
```

### 使用方式

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
                            public void onPermissionsGranted(String[] grantPermissions) {
                                // 同意申请
                            }

                            @Override
                            public void onPermissionsDenied(String[] deniedPermissions) {
                                // 拒绝申请
                            }
                        });
```


