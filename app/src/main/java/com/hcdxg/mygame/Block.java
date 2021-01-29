package com.hcdxg.mygame;
import com.uxyq7e.test.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.common.*;
import android.graphics.*;
import com.uxyq7e.test.tools.tool;

public class Block extends Image
{
	public Body body;
	public int num,id,time,bitw,bith;
	float r,dr,alpha;
	boolean outof;
	Paint pp=new Paint(),pai=new Paint();
	
	public static int[] ball_size={45,55,70,90,100,120,150,175,200,230,280,350};
	
	public Block(Body body,int num){
		this.body=body;this.num=num;
		pp.setColor(Color.WHITE);
		setbitmap();
		
	}
	public void change(int n){
		switch(n){
			case -1:
				num=Math.max(num-1,0);
				break;
			case -2:
				num=(num+1)*2-1;
				break;
			case -3:
				num++;
				break;
			case -4:
				scaleto(106,150);
				break;
		}
		Game.update_point(num+1);
		setbitmap();
	}
	
	public void plus(){
		num++;
		Game.update_point(num);
		setbitmap();
	}
	public void setbitmap()
	{
		setsize(ball_size[num]*2,ball_size[num]*2);
		if(num<0)
			setbitmap(Screen.fun[-num-1]);
		else if(num<Screen.blocks.length)
		{
			setbitmap(Screen.blocks[num]);
			outof=false;
		}else
		{
			setbitmap(Screen.out);
			bitw=Screen.numbers[0].getWidth();
			bith=Screen.numbers[0].getHeight();
			outof=true;
		}
	}
	
	public void setangle(){
		setangle((float)Math.toDegrees(body.getAngle()));
	}
	@Override
	public void draw(Canvas canvas){
		canvas.save();
		Path pathc1=new Path();
		pathc1.addCircle(w/2,h/2,w/2,Path.Direction.CW);
		canvas.clipPath(pathc1);
		
		Path pathc=new Path();
		pathc.addCircle(w/2,h/2,r,Path.Direction.CW);
		canvas.clipPath(pathc,Region.Op.DIFFERENCE);
		super.draw(canvas);
		if(outof){
			pp.setAlpha((int)Math.abs(Math.sin(alpha)*255));
			alpha+=0.017;
			canvas.drawBitmap(Screen.teji,0,0,pp);
			String number="2^"+(num+1);
			float startx=w/2-(number.length()*(bitw/2));
			float starty=h/2-(bith/2);
			for(int i=0;i<number.length();i++){
				char ch=number.charAt(i);
				canvas.drawBitmap(Screen.numbers[ch=='^'?10:ch-'0'],startx+bitw*i,starty,pai);
			}
		}
		canvas.restore();
	}
	public boolean poi_b(){
		if(time>0){
			r+=dr;
			time-=20;
			if(time<=0){
				Game.body_remove.add(body);
				return true;
			}
		}
		setangle();
		Vec2 vec=body.getPosition();
		setposition_center(vec.x*Game.bl,vec.y*Game.bl);
		return false;
	}
	public void scaleto(float ar,int time){
		dr=(ar-r)/(time/20);
		this.time=time;
		body.setUserData(-1);
		Screen.sp.play(Screen.mix[num],1,1,1,0,1);
	}
}
