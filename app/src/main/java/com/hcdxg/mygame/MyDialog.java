package com.hcdxg.mygame;

import android.graphics.*;
import com.uxyq7e.test.*;
import com.uxyq7e.test.tools.*;
import android.util.*;

public class MyDialog extends Dialog
{
	Bitmap bmp;
	public MyDialog(){
		super();
		setposition_center(540,960);
		bmp=tool.loadbitmap("bu.png");
		setbg(tool.loadbitmap("dialog.png"));
	}

	@Override
	public void setLtext(String text)
	{
		super.setLtext(text);
		setLbitmap(bmp);
	}

	@Override
	public void setRtext(String text)
	{
		super.setRtext(text);
		setRbitmap(bmp);
	}
	
}
