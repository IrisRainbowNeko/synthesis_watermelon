package com.hcdxg.mygame;
import com.uxyq7e.test.*;
import com.uxyq7e.test.tools.*;

import android.content.Context;
import android.view.*;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class MainView extends GameView
{
	MyDialog md;
	boolean diatc;
    Image title_final;

	public MainView(){
	    Image title=new Image(286,300,361,130);
		title.setbitmap(tool.loadbitmap("title.png"));
        title_final=new Image(title.getright()+20,305,120,120);
        title_final.setbitmap(Screen.blocks.lastElement());

		Button ks=new Button();
		ks.settextsize(70);
		ks.settext("开始游戏");
		ks.setsize(350,150);
		ks.setposition_center(540,700);
		ks.setbitmap(tool.loadbitmap("an.png"));
		ks.setclick(new Button.ClickListener(){
				@Override
				public boolean down()
				{
					return false;
				}

				@Override
				public boolean up()
				{
					Screen.ge=new Game();
					Screen.gv=Screen.ge;
					return false;
				}
			});
		Button sm=new Button();
		sm.settextsize(70);
		sm.settext("自定义");
		sm.setsize(350,150);
		sm.setposition_center(540,ks.getposition_center().y+200);
		sm.setbitmap(tool.loadbitmap("an.png"));
		sm.setclick(new Button.ClickListener(){
				@Override
				public boolean down()
				{
					return false;
				}

				@Override
				public boolean up()
				{
					Screen.gv=Screen.cv;
					//((InputMethodManager)MainActivity.ma.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
					return false;
				}
			});
		Button tc=new Button();
		tc.settextsize(70);
		tc.settext("退出");
		tc.setsize(350,150);
		tc.setposition_center(540,sm.getposition_center().y+200);
		tc.setbitmap(tool.loadbitmap("an.png"));
		tc.setclick(new Button.ClickListener(){
				@Override
				public boolean down()
				{
					return false;
				}

				@Override
				public boolean up()
				{
					System.exit(0);
					return false;
				}
			});
		Button gv=new Button();
		gv.settextsize(70);
		gv.settext("设置");
		gv.setsize(350,150);
		gv.setposition_center(540,tc.getposition_center().y+200);
		gv.setbitmap(tool.loadbitmap("an.png"));
		gv.setclick(new Button.ClickListener(){
				@Override
				public boolean down()
				{
					return false;
				}

				@Override
				public boolean up()
				{
					Screen.grav=new GravityView();
					Screen.gv=Screen.grav;
					return false;
				}
			});
		md=new MyDialog();
		md.setTitle("退出游戏");
		md.setLtext("取消");
		md.left.setclick(new Button.ClickListener(){

				@Override
				public boolean down()
				{
					return false;
				}

				@Override
				public boolean up()
				{
					diatc=false;
					removedialog(0);
					return false;
				}
			});
		md.setRtext("确定");
		md.right.setclick(new Button.ClickListener(){

				@Override
				public boolean down()
				{
					return false;
				}

				@Override
				public boolean up()
				{
					System.exit(0);
					removedialog(0);
					return false;
				}
			});
		addview(title);
		addview(title_final);
		addview(ks);
		addview(sm);
		addview(tc);
		addview(gv);
	}
	@Override
	public boolean onKeyDown(int keycode)
	{
		if(keycode==KeyEvent.KEYCODE_BACK){
			if(diatc)removedialog(0);
			else adddialog(md);
			return true;
		}
		return super.onKeyDown(keycode);
	}
}
