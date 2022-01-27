# ![Logo](https://raw.githubusercontent.com/uestccokey/EZPermission/master/logo.png)
# EZPermission

A small and easy to use (<20KB) Android runtime permission library, compatible with Android O

[中文](README-CN.md)

### Features

1.Support check the availability of permissions

2.Support the callback mode to apply for permissions

3.Support batch apply for permissions

4.Support Android O

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
        public void onPermissionGranted(String[] grantedPermissions) {
            // agree
        }

        @Override
        public void onPermissionDenied(String[] deniedPermissions, boolean isNoLongerPrompted) {
            // refuse
        }

        @Override
        public void onAllPermissionsGranted() {
            // agree all
        }
});
```


