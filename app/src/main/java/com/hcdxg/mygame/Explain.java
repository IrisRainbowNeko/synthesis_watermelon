package com.hcdxg.mygame;
import android.view.*;
import com.uxyq7e.test.*;
import com.uxyq7e.test.tools.tool;

public class Explain extends GameView
{
	ViewList vl;
	final String[] info={"使物体数值减半","使物体数值平方","使方块数值加倍","使撞到的物体消失"};
	public Explain(){
		vl=new ViewList(60,200,900,1700);
		vl.setlength(1);
		vl.setrow_spacing(10);
		vl.setorientation(ViewList.vertical);
		for(int i=0;i<info.length;i++){
			GameView ga=new GameView();
			Image im=new Image(0,0,150,150);
			im.setbitmap(Screen.fun[i]);
			Lable la=new Lable(info[i],180,0,700,150);
			ga.setsize(900,im.h);
			ga.addview(im);
			ga.addview(la);
			vl.add(ga);
		}
		Button back=new Button(0,0,120,120);
		back.setbitmap(tool.loadbitmap("back.png"));
		back.setclick(new Button.ClickListener(){
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
		addview(back);
		addview(vl);
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
