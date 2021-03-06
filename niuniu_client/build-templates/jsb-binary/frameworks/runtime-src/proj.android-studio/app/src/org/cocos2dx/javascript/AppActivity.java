/****************************************************************************
Copyright (c) 2008-2010 Ricardo Quesada
Copyright (c) 2010-2012 cocos2d-x.org
Copyright (c) 2011      Zynga Inc.
Copyright (c) 2013-2014 Chukong Technologies Inc.
 
http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
****************************************************************************/
package org.cocos2dx.javascript;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxGLSurfaceView;
import org.cocos2dx.lib.Cocos2dxHelper;
import org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge;

import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.app.AlertDialog;
import android.content.DialogInterface;
// -------------------------------------
import org.cocos2dx.javascript.SDKWrapper;
import android.util.Log;
import com.game.sdk.Constants;
import com.game.sdk.WXAPI;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import android.content.ClipboardManager;
import android.location.LocationManager;
import android.app.Activity;

public class AppActivity extends Cocos2dxActivity {
	private static String roomId = "";
    
    private static AppActivity MainActivity = null;
    public static Intent mainIntent = null;
    private static LocationManager locationManager = null;
    private static ClipboardManager clipboardManager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainIntent = getIntent();
        
        MainActivity = this;
        SDKWrapper.getInstance().init(this);

        WXAPI.Init(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    }
   
    public static void openUrl(String url) { 
          Uri uri = Uri.parse(url); 
          Intent it = new Intent(Intent.ACTION_VIEW,uri); 
          MainActivity.startActivity(it); 
    } 
    
    @Override
    public Cocos2dxGLSurfaceView onCreateView() {
        Cocos2dxGLSurfaceView glSurfaceView = new Cocos2dxGLSurfaceView(this);
        // TestCpp should create stencil buffer
        glSurfaceView.setEGLConfigChooser(5, 6, 5, 0, 16, 8);

        SDKWrapper.getInstance().setGLSurfaceView(glSurfaceView);

        return glSurfaceView;
    }

    // For JS and JAVA reflection test, you can delete it if it's your own project
    public static void showAlertDialog(final String title,final String message) {
        // Here be sure to use runOnUiThread
        MainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity).create();
                alertDialog.setTitle(title);
                alertDialog.setMessage(message);
                alertDialog.show();
            }
        });
   }
    public static LocationManager getLocationManager() {
        return locationManager;
    }
    public static ClipboardManager getClipboardManager() {
        return clipboardManager;
    }
	public static AppActivity getActivity(){
        return MainActivity;
    }
    
	public static String getRoomId() {
        Log.e("11111","6666666666666666666666666666");
		String aa = roomId;
		roomId = "";
		return aa;
	}

    @Override
    protected void onResume() {
        super.onResume();
        SDKWrapper.getInstance().onResume();

		Uri uriData = this.getIntent().getData();
		if(uriData != null){
			String id = uriData.getQueryParameter("roomId");
			roomId = id;
			Log.d("getRoomId", roomId );
		}
    }

    @Override
    protected void onPause() {
        super.onPause();
        SDKWrapper.getInstance().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SDKWrapper.getInstance().onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SDKWrapper.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SDKWrapper.getInstance().onNewIntent(intent);

		Uri uriData = intent.getData();
		if(uriData != null){
			final String id = uriData.getQueryParameter("roomId");
			Log.d("getRoomId", id + roomId );
            Cocos2dxHelper.runOnGLThread(new Runnable() {
				@Override
				public void run() {
					Cocos2dxJavascriptJavaBridge.evalString("cc.vv.anysdkMgr.setRoomId("+ id +")");
				}
			});
		}
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SDKWrapper.getInstance().onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SDKWrapper.getInstance().onStop();
    }
        
    @Override
    public void onBackPressed() {
        SDKWrapper.getInstance().onBackPressed();
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        SDKWrapper.getInstance().onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        SDKWrapper.getInstance().onRestoreInstanceState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        SDKWrapper.getInstance().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        SDKWrapper.getInstance().onStart();
        super.onStart();
    }
}
