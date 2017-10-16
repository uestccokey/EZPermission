package cn.ezandroid.ezpermission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

/**
 * 代理Activity
 * <p>
 * 在onRequestPermissionsResult中进行处理权限申请回调是个很奇怪的事情，会破坏代码阅读时的连续性。
 * 这里使用一个代理Activity，封装后外边调用时支持使用监听器RequestCallback接收权限申请结果。
 *
 * @author like
 * @date 2017-09-28
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public final class ProxyActivity extends Activity {

    private static final String KEY_PERMISSIONS = "KEY_PERMISSIONS";

    interface PermissionListener {

        void onRequestPermissionsResult(String[] permissions, int[] grantResults);
    }

    interface RationaleListener {

        void onRationaleResult(boolean showRationale);
    }

    private static PermissionListener mPermissionListener;

    public static void launch(Context context, String[] permissions, PermissionListener permissionListener) {
        Intent intent = new Intent(context, ProxyActivity.class);
        intent.putExtra(ProxyActivity.KEY_PERMISSIONS, permissions);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mPermissionListener = permissionListener;
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String[] permissions = intent.getStringArrayExtra(KEY_PERMISSIONS);

        if (permissions == null) {
            finish();
            return;
        }

        if (mPermissionListener != null) {
            requestPermissions(permissions, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionListener != null) {
            mPermissionListener.onRequestPermissionsResult(permissions, grantResults);
        }
        mPermissionListener = null;
        finish();
    }
}
