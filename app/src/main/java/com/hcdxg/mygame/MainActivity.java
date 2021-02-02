package com.hcdxg.mygame;

import android.Manifest;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.*;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uxyq7e.test.tools.tool;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity
{
    Screen scr=null;
	public static SharedPreferences fs;
	public static boolean creat;
	public static String data_dir;

	public static MainActivity ma;

    private static final int RESULT_CODE_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 100;

    @Override
    public void onCreate(Bundle savedInstanceState)
	{
		ma=this;
        super.onCreate(savedInstanceState);
        data_dir=getDiskFileDir(this);
        System.out.println(data_dir);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		fs=getSharedPreferences("diyi",Context.MODE_PRIVATE);
        Game.firstopen=fs.getBoolean("first",true);

        Screen.am=getAssets();

        if(Game.firstopen){
			try {
				for(String s:getAssets().list("data"))
					tool.copyBigDataToSD(data_dir+"/"+s,"data/"+s);
			} catch (IOException e) {
				e.printStackTrace();
			}
			fs.edit().putBoolean("first",false).apply();
		}

		scr=new Screen(this);
        setContentView(scr);
		Game.G.y=fs.getInt("gravity",10);


		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			RxPermissions rxPermissions = new RxPermissions(this);
			rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe();
			//rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe();
		}
	}
	@Override
	protected void onResume()
	{
		if(creat)
			scr.startrander();
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		if(creat)
			scr.stoprander();
		super.onPause();
	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return scr.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    public String getDiskFileDir(Context context) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalFilesDir(null).getPath();
		} else {
			cachePath = context.getFilesDir().getPath();
		}
		File fpath=new File(cachePath);
		if(!fpath.exists())
			fpath.mkdirs();
		return cachePath;
	}

	public void chooseFile(int code){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		try {
			startActivityForResult(Intent.createChooser(intent, "请选择文件!"),code);
		} catch (android.content.ActivityNotFoundException ex) {
			// Potentially direct the user to the Market with a Dialog
			Toast.makeText(this, "请安装文件管理器", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode==RESULT_OK)
		{
			try{
				String path=new UriUtil().getPath(data.getData(),this);
				switch (requestCode){
					case 1:
						Screen.cv.changeImage(CustomView.select_view,BitmapFactory.decodeFile(path));
						break;
					case 2:
					    if(path.endsWith(".wav"))
						    Screen.cv.changeSound(CustomView.select_view, path);
					    else
					        Toast.makeText(this,"只支持wav格式音频",Toast.LENGTH_LONG).show();
						break;
					case 3:
						Screen.cv.importBlocks(path);
						break;
					case 4:
						Screen.bg=BitmapFactory.decodeFile(path);
						tool.fileChannelCopy(new FileInputStream(path),new File(MainActivity.data_dir, "bg.png"));
						break;
				}
			}catch(Exception e){
				Toast.makeText(this,"文件选择错误，请更换文件选择器"+e.toString(),Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
