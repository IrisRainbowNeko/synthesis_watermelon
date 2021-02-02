package com.hcdxg.mygame;
import android.content.*;
import android.view.*;
import com.uxyq7e.test.*;
import android.graphics.*;
import com.uxyq7e.test.tools.BitmapRegion;
import com.uxyq7e.test.tools.tool;
import android.media.*;
import java.io.*;
import java.util.Vector;

import android.util.*;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Screen extends Screen7e
{
	public static Bitmap[] numbers,fun;
	public static Vector<Bitmap> blocks=new Vector<Bitmap>();
	public static GameView gv;
	public static Game ge;
	public static MainView mv;
	public static CustomView cv;
	public static GravityView grav;
	public static Lost lost;
	public static Bitmap out,teji,bg,play,change;
	public static SoundPool sp;
	public static int caidan;
	public static Vector<Integer> mix=new Vector<Integer>();

	public static Screen scr;

	public Screen(Context c){
		super(c);
		scr=this;
		fun=BitmapRegion.splitwh(tool.loadbitmap("fh.png"),150,150);
		numbers=BitmapRegion.split(tool.Scale(tool.loadbitmap("num.png"),0.5f,0.5f),11,1);
		out=tool.loadbitmap("else.png");
		teji=tool.loadbitmap("teji.png");
		play=tool.loadbitmap("play.png");
		change=tool.loadbitmap("change.png");

		File bg_file=new File(MainActivity.data_dir, "bg.png");
		if(bg_file.exists()){
			bg=BitmapFactory.decodeFile(bg_file.toString());
		} else {
			bg=tool.loadbitmap("indi.png");
		}

		loadBlockData();

		mv=new MainView();
		ge=new Game();
		cv=new CustomView();
		lost=new Lost();
		grav=new GravityView();
		gv=mv;

	}

	public void loadBlockData(){
        blocks=Block.loadBlockImages();
        sp=new SoundPool(6,AudioManager.STREAM_MUSIC,100);
        try
        {
            mix.addAll(Block.loadMixSounds(this));
            caidan=sp.load(am.openFd("caidan.wav"),1);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

	@Override
	public void surfaceCreated(SurfaceHolder p1)
	{
		MainActivity.creat=true;
		startrander();
	}

	@Override
	public void Draw(Canvas Canvas)
	{
		canvas.drawBitmap(bg,new Rect(0,0,bg.getWidth(),bg.getHeight()),new Rect(0,0,(int)fblx_raw,(int)fbly_raw),null);
		gv.Draw(Canvas);
		super.Draw(Canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		gv.touch(event);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return gv.onKeyDown(keyCode);
		//return true;
	}

	@Override
	public void poi()
	{
		gv.poi();
		super.poi();
	}
}
