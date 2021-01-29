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
	public void setltext(String text)
	{
		super.setltext(text);
		setlbitmap(bmp);
	}

	@Override
	public void setrtext(String text)
	{
		super.setrtext(text);
		setrbitmap(bmp);
	}
	
}
