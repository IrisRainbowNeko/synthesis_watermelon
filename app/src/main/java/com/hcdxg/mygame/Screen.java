package com.hcdxg.mygame;
import android.content.*;
import android.view.*;
import com.uxyq7e.test.*;
import android.graphics.*;
import com.uxyq7e.test.tools.BitmapRegion;
import com.uxyq7e.test.tools.tool;
import android.media.*;
import java.io.*;
import android.util.*;

public class Screen extends Screen7e
{
	public static Bitmap[] blocks,numbers,fun;
	public static GameView gv;
	public static Game ge;
	public static MainView mv;
	public static GravityView grav;
	public static Explain exp;
	public static Lost lost;
	public static Win win;
	public static Bitmap out,teji,bg;
	public static SoundPool sp;
	public static int caidan;
	public static int[] mix=new int[4];

	public Screen(Context c){
		super(c);
		blocks=BitmapRegion.splitwh(tool.loadbitmap("blocks.png"),150,150);
		fun=BitmapRegion.splitwh(tool.loadbitmap("fh.png"),150,150);
		numbers=BitmapRegion.split(tool.big(tool.loadbitmap("num.png"),0.5f,0.5f),11,1);
		out=tool.loadbitmap("else.png");
		teji=tool.loadbitmap("teji.png");
		bg=tool.loadbitmap("indi.png");
		sp=new SoundPool(6,AudioManager.STREAM_MUSIC,100);
		try
		{
			mix[0]=sp.load(am.openFd("sb.wav"),1);
			mix[1]=sp.load(am.openFd("hyjs.wav"),1);
			mix[2]=sp.load(am.openFd("rock.wav"),1);
			mix[3]=sp.load(am.openFd("ding.wav"),1);
			caidan=sp.load(am.openFd("caidan.wav"),1);
		}catch (IOException e){}
		mv=new MainView();
		ge=new Game();
		exp=new Explain();
		lost=new Lost();
		win=new Win();
		grav=new GravityView();
		gv=mv;
		
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
	}

	@Override
	public void poi()
	{
		gv.poi();
		super.poi();
	}
	
}
