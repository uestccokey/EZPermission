# ![Logo](https://raw.githubusercontent.com/uestccokey/EZPermission/master/logo.png)
# EZPermission

A small and easy to use (<20KB) Android runtime permission library, compatible with Android O

[中文](README-CN.md)

[ ![Download](https://api.bintray.com/packages/uestccokey/maven/EZPermission/images/download.svg) ](https://bintray.com/uestccokey/maven/EZPermission/_latestVersion)

### Features

1.Support check the availability of permissions

2.Support the callback mode to apply for permissions

3.Support batch apply for permissions

4.Support Android O

### Usage

#### Gradle

``` gradle
dependencies {
    compile 'cn.ezandroid:EZPermission:1.0.4' //  Gradle version < 3.0
    // Or
    implementation 'cn.ezandroid:EZPermission:1.0.4' // Gradle version >= 3.0
}
```

#### Sample

Check whether permissions are available

``` java
boolean available = EZPermission.permissions(Permission.CAMERA, Permission.STORAGE...)
                        .available(context);
```

Apply for permission

``` java
EZPermission.permissions(Permission.CAMERA, Permission.STORAGE...)
    .apply(context, new PermissionCallback() {
        @Override
        public void onPermissionGranted(Permission grantedPermission) {
            // agree
        }

        @Override
        public void onPermissionDenied(Permission deniedPermission) {
            // refuse
        }

        @Override
        public void onAllPermissionsGranted() {
            // agree all
        }
});
```


