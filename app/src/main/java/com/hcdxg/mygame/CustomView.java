package com.hcdxg.mygame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.KeyEvent;
import android.widget.Toast;

import com.uxyq7e.test.Button;
import com.uxyq7e.test.GameView;
import com.uxyq7e.test.Image;
import com.uxyq7e.test.Lable;
import com.uxyq7e.test.TextField;
import com.uxyq7e.test.ViewList;
import com.uxyq7e.test.tools.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CustomView extends GameView {

    //public static int select_idx=0;
    //public int touch_index;
    public static GameView select_view;

    ViewList<GameView> blocks_list;

    //Vector<BlockItem> item_list=new Vector<BlockItem>();
    SoundPool sp=new SoundPool(10, AudioManager.STREAM_MUSIC,100);

    public CustomView(){

        blocks_list = new ViewList<GameView>(40,200,1000,800);
        blocks_list.setlength(1);
        blocks_list.setrow_spacing(10);
        blocks_list.setorientation(ViewList.vertical);
        blocks_list.setmove(true);
        blocks_list.setonItemClickListener(new ViewList.ItemClickListener(){
            @Override
            public void startclick(int idx) {

            }

            @Override
            public void onclick(final int idx) {
                new AlertDialog.Builder(MainActivity.ma).setTitle("选择操作")
                        .setIcon(R.drawable.ic_launcher)
                        .setItems(new String[]{"前面添加一项", "删除该项", "后面添加一项"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int index) {
                                switch (index){
                                    case 0:{
                                        addItem(Screen.blocks.get(0), Block.ball_size[0], 0,new File(MainActivity.data_dir, "0.wav"),idx);
                                    }break;
                                    case 1:{
                                        removeItem(idx);
                                    }break;
                                    case 2:{
                                        addItem(Screen.blocks.get(0), Block.ball_size[0], 0,new File(MainActivity.data_dir, "0.wav"),idx+1);
                                    }break;
                                }
                            }
                        }).setNegativeButton("取消",null).show();
            }
        });

        for(int i=0;i<Screen.blocks.size();i++){
            addItem(Screen.blocks.get(i), Block.ball_size[i], Block.ball_rate[i],new File(MainActivity.data_dir, i+".wav"),i);
            //item_list.add(new BlockItem(Screen.blocks.get(i), Block.ball_size[i], readFile(new File(MainActivity.data_dir, i+".wav")),Screen.mix.get(i)));
        }
        addview(blocks_list);

        Button exp=new Button();
        exp.settextsize(70);
        exp.settext("导出资源包");
        exp.setsize(400,150);
        exp.setposition_center(270,1200);
        exp.setbitmap(tool.loadbitmap("an.png"));
        exp.setclick(new Button.ClickListener(){
            @Override
            public boolean down()
            {
                return false;
            }

            @Override
            public boolean up()
            {
                exportBlocks("test");
                Toast.makeText(MainActivity.ma,"导出完成",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        addview(exp);

        Button imp=new Button();
        imp.settextsize(70);
        imp.settext("导入资源包");
        imp.setsize(400,150);
        imp.setposition_center(810,1200);
        imp.setbitmap(tool.loadbitmap("an.png"));
        imp.setclick(new Button.ClickListener(){
            @Override
            public boolean down()
            {
                return false;
            }

            @Override
            public boolean up()
            {
                MainActivity.ma.chooseFile(3);
                return true;
            }
        });
        addview(imp);

        Button bg=new Button();
        bg.settextsize(70);
        bg.settext("设置背景");
        bg.setsize(400,150);
        bg.setposition_center(540,1400);
        bg.setbitmap(tool.loadbitmap("an.png"));
        bg.setclick(new Button.ClickListener(){
            @Override
            public boolean down()
            {
                return false;
            }

            @Override
            public boolean up()
            {
                MainActivity.ma.chooseFile(4);
                return true;
            }
        });
        addview(bg);
    }

    public void addItem(Bitmap bmp, int r, float rate, File sound_file, int idx){
        int sound=sp.load(sound_file.toString(), 1);

        final GameView gv=new GameView();
        gv.setsize(1080,120);
        Image im=new Image(0,0,120,120);
        im.setbitmap(bmp);
        im.setclick(new Image.ClickListener(){
            @Override
            public void onclick() {
                select_view=gv;
                MainActivity.ma.chooseFile(1);
            }
        });
        gv.addview(im);

        Lable la_r=new Lable("半径：");
        la_r.SetwhSurround();
        la_r.setposition(im.getright()+30, (120-la_r.h)/2);
        gv.addview(la_r);

        TextField tf=new TextField(Screen.scr);
        tf.settext(r+"");
        tf.setsize(120, la_r.h);
        tf.setposition(la_r.getright()+0,(120-la_r.h)/2);
        gv.addview(tf);


        Lable la_rate=new Lable("概率：");
        la_rate.SetwhSurround();
        la_rate.setposition(tf.getright()+20, (120-la_rate.h)/2);
        gv.addview(la_rate);

        TextField tf_rate=new TextField(Screen.scr);
        tf_rate.settext(rate+"");
        tf_rate.setsize(150, la_rate.h);
        tf_rate.setposition(la_rate.getright()+0,(120-la_rate.h)/2);
        gv.addview(tf_rate);

        Button bu_play = new Button();
        bu_play.setsize(100, 100);
        bu_play.setposition(tf_rate.getright() + 20, (120 - bu_play.h) / 2);
        bu_play.setbitmap(Screen.play);

        bu_play.setclick(new Button.ClickListener() {
            @Override
            public boolean down() {
                return false;
            }

            @Override
            public boolean up() {
                Screen.sp.play(((BlockItem)gv.user_data).sound, 1, 1, 1, 0, 1);
                return true;
            }
        });
        gv.addview(bu_play);

        Button bu_change = new Button();
        bu_change.setsize(100, 100);
        bu_change.setposition(bu_play.getright() + 10, (120 - bu_change.h) / 2);
        bu_change.setbitmap(Screen.change);
        bu_change.setclick(new Button.ClickListener() {
            @Override
            public boolean down() {
                return false;
            }

            @Override
            public boolean up() {
                select_view=gv;
                MainActivity.ma.chooseFile(2);
                return true;
            }
        });
        gv.addview(bu_change);
        gv.user_data=new BlockItem(bmp,r,readFile(sound_file),sound);

        if(idx==blocks_list.getsize())
            blocks_list.add(gv);
        else
            blocks_list.insert_sort(idx, gv);
    }

    public void removeItem(int idx){
        sp.unload(((BlockItem)blocks_list.getItem(idx).user_data).sound);
        blocks_list.remove_sort(idx);
    }

    @Override
    public boolean onKeyDown(int keycode)
    {
        if(keycode== KeyEvent.KEYCODE_BACK){
            saveChages();
            Screen.gv=Screen.mv;
            return true;
        }
        return super.onKeyDown(keycode);
    }

    public void changeImage(GameView gv, Bitmap bmp){
        ((Image)gv.getchildview(0)).setbitmap(bmp);
        ((BlockItem)gv.user_data).bmp=bmp;
    }

    public boolean changeSound(GameView gv,String path){
        if(!path.toLowerCase().endsWith(".wav")) return false;

        BlockItem bl=(BlockItem)gv.user_data;
        sp.unload(bl.sound);
        bl.sound=Screen.sp.load(path,1);
        bl.sound_data=readFile(new File(MainActivity.data_dir, path));
        return true;
    }

    public void saveChages(){
        StringBuilder sizes=new StringBuilder();
        StringBuilder rates=new StringBuilder();
        for(int i=0;i<blocks_list.getsize();i++){
            BlockItem bl=(BlockItem)blocks_list.getItem(i).user_data;
            saveBitmap(bl.bmp, i+".png");
            writeFile(new File(MainActivity.data_dir, i+".wav"), bl.sound_data);
            bl.r=Integer.parseInt(((TextField)blocks_list.getItem(i).getchildview(2)).text.toString());
            bl.rate=Float.parseFloat(((TextField)blocks_list.getItem(i).getchildview(4)).text.toString());
            sizes.append(bl.r).append(",");
            rates.append(bl.rate).append(",");
        }
        sizes.deleteCharAt(sizes.length()-1);
        rates.deleteCharAt(rates.length()-1);
        sizes.append("\n").append(rates);
        writeFile(new File(MainActivity.data_dir, "size.txt"),sizes.toString().getBytes());
        Screen.scr.loadBlockData();
        Screen.mv.title_final.setbitmap(Screen.blocks.lastElement());
    }

    public void reload(){
        blocks_list.removeall();
        Block.load_size_rate();
        sp.release();
        sp=new SoundPool(10, AudioManager.STREAM_MUSIC,100);
        for(int i=0;i<Block.ball_size.length;i++){
            addItem(BitmapFactory.decodeFile(new File(MainActivity.data_dir,i+".png").toString()),
                    Block.ball_size[i],Block.ball_rate[i],new File(MainActivity.data_dir, i+".wav"),i);
            //item_list.add(new BlockItem(Screen.blocks.get(i), Block.ball_size[i], readFile(new File(MainActivity.data_dir, i+".wav")),Screen.mix.get(i)));
        }

        File bg_file=new File(MainActivity.data_dir, "bg.png");
        if(bg_file.exists()){
            Screen.bg=BitmapFactory.decodeFile(bg_file.toString());
        }
    }

    public void exportBlocks(String name){
        saveChages();
        try {
            ZipUtil.zip(MainActivity.data_dir, new File(Screen.FILE_SD, name+".zip").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importBlocks(String path){
        try {
            File[] fdata=new File(MainActivity.data_dir).listFiles();
            for(File f:fdata)
                f.delete();
            ZipUtil.unzip(new File(path), MainActivity.data_dir);
            reload();
            Toast.makeText(MainActivity.ma,"导入完成",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(MainActivity.ma,"导入失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static void saveBitmap(Bitmap mBitmap, String bitName) {
        File f = new File(MainActivity.data_dir, bitName);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(fOut==null){
            return;
        }
        //设置输出类型为PNG和文件名后缀要对应
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] readFile(File file){
        try {
            FileInputStream fin=new FileInputStream(file);
            byte[] bys=new byte[fin.available()];
            fin.read(bys);
            fin.close();
            return bys;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void writeFile(File file, byte[] data){
        try {
            FileOutputStream fout=new FileOutputStream(file);
            fout.write(data);
            fout.flush();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class BlockItem{
        Bitmap bmp;
        int r;
        float rate;
        byte[] sound_data;
        int sound;
        public BlockItem(Bitmap bmp, int r, byte[] sound_data, int sound){
            this.bmp=bmp;this.r=r;this.sound_data=sound_data;this.sound=sound;
        }
        public BlockItem(BlockItem copy){
            this.bmp=copy.bmp;this.r=copy.r;
            this.sound_data=copy.sound_data;this.sound=copy.sound;
        }
    }
}
