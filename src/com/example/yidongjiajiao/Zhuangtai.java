package com.example.yidongjiajiao;

import com.example.yidongjiajiao.R;

import servers.Updatestatue;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class Zhuangtai extends Activity {
	int image[] = new int[] { R.drawable.no, R.drawable.yes,

	};
	private String url="http://www.huanglinqing.com/yidongjiajiao/xiugaizhuangtai/";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zhuangtai);
		
	}
    
	// 两种状态的监听事件
	public void zhuangtai(View v) {
		Intent intent = getIntent();
		final ProgressDialog dialog = new ProgressDialog(Zhuangtai.this);
		String name = intent.getStringExtra("name");
		Toast.makeText(Zhuangtai.this, name, 0).show();
		new Updatestatue(name, 0, url, handler).start();
		dialog.setTitle("正在更新...");
		dialog.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dialog.dismiss();
			}
		}).start();
	}
	// 两种状态的监听事件
		public void zhuangtai1(View v) {
			Intent intent = getIntent();
			final ProgressDialog dialog = new ProgressDialog(Zhuangtai.this);
			String name = intent.getStringExtra("name");
			new Updatestatue(name, 1, url, handler).start();
			dialog.setTitle("正在更新...");
			dialog.show();
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dialog.dismiss();
				}
			}).start();
		}
	//更改教师状态线程机制
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			String json=(String) msg.obj;
			int n=json.indexOf("更新成功");
			if(n>0){
				Toast.makeText(Zhuangtai.this, "状态更新成功", 0).show();
			}else{
				Toast.makeText(Zhuangtai.this, "状态更新失败，请稍后重试", 0).show();
			}
			
		};
	};

}
