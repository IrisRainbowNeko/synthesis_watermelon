package com.hcdxg.mygame;

import android.view.*;
import com.uxyq7e.test.*;
import com.uxyq7e.test.tools.*;

public class Win extends GameView
{
	public Win(){
		Lable la=new Lable("这游戏你居然赢了！\n得分"+(-Game.point));
		la.settextsize(80);
		la.SetwhSurround();
		la.setposition_center(540,400);
		Button over=new Button();
		over.settextsize(70);
		over.settext("返回主界面");
		over.setsize(400,150);
		over.setposition_center(540,700);
		over.setbitmap(tool.loadbitmap("an.png"));
		over.setclick(new Button.ClickListener(){
				@Override
				public boolean down()
				{
					return false;
				}

				@Override
				public boolean up()
				{
					Screen.gv=Screen.mv;
					return false;
				}
			});
		Button again=new Button();
		again.settextsize(70);
		again.settext("再来一次");
		again.setsize(400,150);
		again.setposition_center(540,900);
		again.setbitmap(tool.loadbitmap("an.png"));
		again.setclick(new Button.ClickListener(){
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
		addview(la);
		addview(over);
		addview(again);
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
