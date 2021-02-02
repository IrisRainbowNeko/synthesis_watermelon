package com.hcdxg.mygame;
import android.view.*;
import com.uxyq7e.test.*;
import com.uxyq7e.test.tools.*;

public class GravityView extends GameView
{
	public GravityView(){
		final Lable la=new Lable("设置重力",200,300,1,1);
		la.SetwhSurround();
		Seeker sk=new Seeker(200,450,700,50);
		sk.setseekable(true);
		sk.setIndexDrawer(new Seeker.IndexDrawer(){

				@Override
				public String drawindex(int index)
				{
					return (index+5)+"";
				}
			});
		sk.setbgbitmap(tool.loadbitmap("bar_bg.png"));
		sk.setbitmap(tool.loadbitmap("bar.png"));
		sk.setmax(35);
		sk.setindex((int)Game.G.y-5);
		sk.setseeklistener(new Seeker.Seeklistener(){
				@Override
				public void startseek()
				{

				}
				@Override
				public void seeking(int index)
				{
					
				}
				@Override
				public void stopseek(int index)
				{
					Game.G.y=index+5;
					MainActivity.fs.edit().putInt("gravity",index+5).commit();
				}
			});
		addview(la);
		addview(sk);
	}
	@Override
	public boolean onKeyDown(int keycode)
	{
		if(keycode==KeyEvent.KEYCODE_BACK){
			Screen.gv=Screen.mv;
			return true;
		}
		return super.onKeyDown(keycode);
	}
}
