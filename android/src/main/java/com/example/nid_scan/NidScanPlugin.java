package com.example.nid_scan;

import android.content.Intent;
import android.util.Log;
import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.ActivityResultListener;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * NidScanPlugin
 */
public class NidScanPlugin implements FlutterPlugin, MethodCallHandler, ActivityResultListener, ActivityAware {
    private MethodChannel channel;
    static FlutterActivity activity;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        Log.e("sakkar", "Start 1");
        channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "nid_scan");
        channel.setMethodCallHandler(new NidScanPlugin());
    }


    public static void registerWith(Registrar registrar) {
        Log.e("sakkar", "Start 2");
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "nid_scan");
        channel.setMethodCallHandler(new NidScanPlugin());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equalsIgnoreCase("scan")) {
            Log.e("sakkar", "Start 3");
            startScan();
        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }


    private void startScan() {
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.PDF_417);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }


    /**
     * Get the barcode scanning results in onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */
    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String rawData = result.getContents();
                Log.e("sakkar", rawData);
            }
        } else {

        }
        return false;
    }

    @Override
    public void onAttachedToActivity(ActivityPluginBinding binding) {
        createPluginSetup(binding.getActivity());
    }

    @Override
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
        createPluginSetup(binding.getActivity());
    }

    @Override
    public void onDetachedFromActivity() {
        clearPluginSetup();
    }


    @Override
    public void onDetachedFromActivityForConfigChanges() {
        clearPluginSetup();
    }


    /**
     * Clear plugin setup
     */
    private void clearPluginSetup() {
        activity = null;
        channel.setMethodCallHandler(null);
        channel = null;
    }

    private void createPluginSetup(
            final Activity activity) {
        NidScanPlugin.activity = (FlutterActivity) activity;
    }
}
