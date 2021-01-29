package com.hcdxg.mygame;
import com.uxyq7e.test.*;
import org.jbox2d.callbacks.*;
import org.jbox2d.collision.*;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.*;
import java.util.*;
import android.graphics.*;
import android.view.*;
import android.util.*;
import com.uxyq7e.test.tools.tool;

public class Game extends GameView
{
	World world;
	public static Vec2 G=new Vec2(0,10);
	AABB aabb=new AABB();
	public final static float bl=30;
	public static Vector<Block> blocklist=new Vector<Block>();
	public static Vector<Body> body_remove=new Vector<Body>();
	int count=0;
	public static int point=0;
	boolean add;
	public static boolean caidan,firstopen;
	float X,Y;
	public static Lable point_la;
	Path firstpath;
	Paint pa;
	
	Vector<Block> blk_plus_list=new Vector<Block>();
	Block blk_hold;
	
	public Game(){
		aabb.lowerBound.set(-100,-100);
		aabb.upperBound.set(100,100);
		world=new World(G);
		world.setContactListener(cl);
		blocklist=new Vector<Block>();
		body_remove=new Vector<Body>();
		caidan=false;
		//addblock();
		create(-5,810,10,2220,0,0.5f,0.3f,0,-2);
		create(1085,810,10,2220,0,0.5f,0.3f,0,-2);
		create(540,1925,1080,10,0,0.5f,0.3f,0,-1);
		//Body bbo=create(540,-5,1080,10,0,0.5f,0.3f,0,-2);
		//bbo.getFixtureList().m_filter.groupIndex=-1;
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
		point=0;
		point_la=new Lable();
		point_la.setbgcolor(0x9066ccff);
		update_point(0);
		addview(back);
		addview(point_la);
		
		firstpath=new Path();
		firstpath.moveTo(540,400);
		firstpath.lineTo(510,1000);
		firstpath.lineTo(450,1000);
		firstpath.lineTo(540,1100);
		firstpath.lineTo(630,1000);
		firstpath.lineTo(570,1000);
		firstpath.close();
		pa=new Paint();
		pa.setTextSize(50);
	}
	public static void update_point(int p){
		point+=p;
		point_la.settext("得分:"+point);
		point_la.SetwhSurround();
		point_la.setposition_center(540,200);
	}
	@Override
	public void Draw(Canvas canvas)
	{
		for(int i=0;i<blocklist.size();i++){
			blocklist.get(i).Draw(canvas);
		}
		if(blk_hold!=null){
			blk_hold.Draw(canvas);
		}
		
		super.Draw(canvas);
		/*if(firstopen){
			canvas.save();
			canvas.rotate(10,540,400);
			canvas.drawPath(firstpath,pa);
			canvas.restore();
			canvas.drawText("滑动施加力",600,540,pa);
		}*/
	}

	@Override
	public void poi()
	{
		//try{
		world.step(1/50f,10,8);
		if(blocklist.size()>77){
			Screen.lost=new Lost();
			Screen.gv=Screen.lost;
			return;
		}
		for(int i=0;i<blocklist.size();i++){
			if(blocklist.get(i).poi_b())
				blocklist.removeElementAt(i--);
		}
		for(int i=0;i<blocklist.size();i++){
			if(blocklist.get(i).num>=63){
				Screen.win=new Win();
				Screen.gv=Screen.win;
				return;
			}
			/*if(!caidan&&blocklist.get(i).num>=12){
				caidan=true;
				Screen.sp.play(Screen.caidan,1,1,1,0,1);
				for(int u=0;u<4;u++){
					Body bo;
					bo=createc(100+880*(u/3f),300,75,5,0.1f,0.3f,0,count);
					Block blo=new Block(bo,-u-1);
					blo.setposition_center(100+880*(u/3f),300);
					blo.id=count;
					blo.body.getFixtureList().m_filter.groupIndex=1;
					count++;
					blocklist.add(blo);
				}
				break;
			}*/
		}
		if(add){
			addblock();
			add=false;
		}
		int bsize=body_remove.size();
		for(int i=0;i<bsize;i++){
			world.destroyBody(body_remove.firstElement());
			body_remove.removeElementAt(0);
		}
		if(!blk_plus_list.isEmpty()){
			for(Block blk_plus:blk_plus_list){
				int idA=(int)blk_plus.body.getFixtureList().getUserData();
				Fixture ft=blk_plus.body.getFixtureList();
				blk_plus.body.destroyFixture(ft);

				ft=blk_plus.body.createFixture(createc_fix(Block.ball_size[blk_plus.num],5,0.1f,0.3f,idA));
				ft=blk_plus.body.getFixtureList();
			}
			blk_plus_list.clear();
		}
		
		super.poi();
		//}catch(Exception e){Log.e("错误",e.toString());}
	}
	
	public int geneType(){
		double pid=Math.random()*100;
		int id=0;
		if(pid<0)id=-1;
		else if(pid<0)id=-2;
		else if(pid<0)id=-3;
		else if(pid<0)id=-4;
		else if(pid<80)id=0;
		else if(pid<98)id=1;
		else id=2;
		return id;
	}
	
	public void addblock(){
		addblock(blk_hold.num, blk_hold);
		blk_hold=null;
	}
	public void addblock(int id, Block bpos){
		Body bo;
		com.uxyq7e.test.tools.Vec2 center=bpos.getposition_center();
		float mass=(float)Math.pow(Block.ball_size[id]/10f,2);
		if(id>=0)
			bo=createc(center.x,center.y,mass,bpos.angle,createc_fix(Block.ball_size[id],mass,0.2f,0.3f,count));
		else
			bo=createc(center.x,center.y,mass,bpos.angle,createc_fix(75,mass,0.2f,0.3f,count));
		Block blo=new Block(bo,id);
		blo.setposition_center(bpos.x,bpos.y);
		blo.id=count;
		//blo.body.getFixtureList().m_filter.groupIndex=-1;
		count++;
		blocklist.add(blo);
	}
	public Body create(float x,float y,float w,float h,float mass,float f,float restitution,float angle,int id)
	{
		BodyDef bd=new BodyDef();
		bd.position.set(x/bl,y/bl);
		bd.angle=(float)Math.toRadians(angle);
		if(mass==0)bd.type=BodyType.STATIC;
		else bd.type=BodyType.DYNAMIC;
		Body bo = world.createBody(bd);
		//创建多边形皮肤
		PolygonShape ps=new PolygonShape();
		ps.setAsBox(w/2/bl,h/2/bl);
		FixtureDef fd=new FixtureDef();
		fd.density=mass;
		fd.friction=f;
		fd.restitution=restitution;// 设置多边形的恢复力
		fd.shape=ps;
		fd.userData=id;
		bo.createFixture(fd);
		return bo;
	}
	public Body createc(float x,float y,float mass,float angle,FixtureDef fd)
	{
		BodyDef bd=new BodyDef();
		bd.position.set(x/bl,y/bl);
		bd.angle=(float)Math.toRadians(angle);
		if(mass==0)bd.type=BodyType.STATIC;
		else bd.type=BodyType.DYNAMIC;
		Body bo = world.createBody(bd); 
		System.out.println(bo.createFixture(fd));
		return bo;
	}
	
	public FixtureDef createc_fix(float r,float mass,float f,float restitution,int id)
	{
		//创建圆形皮肤
		CircleShape cs=new CircleShape();
		cs.m_radius=r/bl;
		FixtureDef fd=new FixtureDef();
		fd.density=mass;
		fd.friction=f;
		fd.restitution=restitution;// 设置恢复力
		fd.shape=cs;
		fd.userData=id;
		return fd;
	}
	
	@Override
	public boolean touch(MotionEvent event)
	{
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			X=getX(event);
			Y=getY(event);
			blk_hold=new Block(null, geneType());
			blk_hold.setposition_center(X,100);
			blk_hold.setangle((float)Math.random()*360);
		}
		if(event.getAction()==MotionEvent.ACTION_MOVE){
			float xx=getX(event);
			float yy=getY(event);
			blk_hold.setposition_center(xx,100);
		}
		if(event.getAction()==MotionEvent.ACTION_UP){
			float xx=getX(event);
			float yy=getY(event);
			blk_hold.setposition_center(xx,100);
			add=true;
			
			/*if(blocklist.lastElement().y<=30)dy=Math.max(0,dy);
			Body bb=blocklist.lastElement().body;
			bb.applyForce(new Vec2(dx*bb.getMass()*2,dy*bb.getMass()*2),bb.getWorldCenter());
			if(firstopen){
				firstopen=false;
				MainActivity.fs.edit().putBoolean("first",false).commit();
			}*/
		}
		return super.touch(event);
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
	
	ContactListener cl=new ContactListener(){

		@Override
		public void beginContact(Contact p1)
		{
			Block fkk=blocklist.lastElement();
			int A=(int)p1.getFixtureA().getBody().getFixtureList().getUserData();
			int B=(int)p1.getFixtureB().getBody().getFixtureList().getUserData();
			/*if((p1.getFixtureA().getBody()==fkk.body||p1.getFixtureB().getBody()==fkk.body)&&(A!=-2&&B!=-2))
			{
				add=true;
			}*/
			if(A>=0&&B>=0)
			{
				if(body_remove.indexOf(p1.getFixtureA().getBody())==-1&&
					body_remove.indexOf(p1.getFixtureB().getBody())==-1){
						
					Block blA=findblock(blocklist,A),blB=findblock(blocklist,B);
					if(blA.time!=0||blB.time!=0)return;
					if(blA.num<0&&blB.num<0)return;
					if(blA.num<0)
					{
						 blA.scaleto(106,150);
						 blB.change(blA.num);
					}else if(blB.num<0){
						blB.scaleto(106,150);
						blA.change(blB.num);
					}else if(blA.num==blB.num){
						 blB.scaleto(106,150);
						 /*int idA=blA.body.getFixtureList().getUserData();
						 Fixture ft=blA.body.getFixtureList();
						 System.out.println(ft);
						 blA.body.destroyFixture(ft);
						 
						 System.out.println(Block.ball_size[blA.num+1]);
						 ft=blA.body.createFixture(createc_fix(Block.ball_size[blA.num+1],5,0.1f,0.3f,idA));
						 System.out.println(ft);
						 System.out.println("before "+ft.getShape().getRadius());*/
						 blA.plus();
						 blk_plus_list.add(blA);
						 //ft=blA.body.getFixtureList();
						 //System.out.println("aftre "+ft.getShape().getRadius());
					}
				}
			}
		}
		
		@Override
		public void endContact(Contact p1)
		{
			// TODO: Implement this method
		}

		@Override
		public void preSolve(Contact p1, Manifold p2)
		{
			// TODO: Implement this method
		}

		@Override
		public void postSolve(Contact p1, ContactImpulse p2)
		{
			// TODO: Implement this method
		}
	};
	public Block findblock(Vector<Block> ve,int key){
		for(int i=0;i<ve.size();i++)
		if(ve.get(i).id==key)return ve.get(i);
		return null;
	}
}
