package com.example.yidongjiajiao;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.example.yidongjiajiao.R;

import servers.HTTPutils;
import servers.deletejiaoshi;
import servers.shenhebiangeng;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Jiaoshixiangxi extends Activity {

	private TextView textView1, textView2, textView3, textView4, textView5;
	private ImageView imageView;
	private String phone;
	private Button buttonok,buttonno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_jiaoshixiangxi);
		buttonok=(Button) findViewById(R.id.ok);
		buttonno=(Button) findViewById(R.id.no);
		Intent intent = getIntent(); 
		 String flag=intent.getStringExtra("flag");
		 if (flag.equals("0")) {
			buttonno.setVisibility(View.GONE);
			buttonok.setVisibility(View.GONE);
		}
		String name = intent.getStringExtra("name");
		String sex = intent.getStringExtra("sex");
		String age = intent.getStringExtra("age");
		String shenfen = intent.getStringExtra("shenfen");
		phone = intent.getStringExtra("phone");
       
		String touxiang = intent.getStringExtra("touxiang");
		init();
		imageView = (ImageView) findViewById(R.id.iamgeview);
		HTTPutils.setPicBitmap(imageView, touxiang);
		textView1.setText(name);
		textView2.setText(age);
		textView3.setText(phone);
		textView4.setText(sex);
		textView5.setText(shenfen);

	}

	// 初始化
	private void init() {

		textView1 = (TextView) findViewById(R.id.name);
		textView2 = (TextView) findViewById(R.id.age);
		textView3 = (TextView) findViewById(R.id.phone);
		textView4 = (TextView) findViewById(R.id.sex);
		textView5 = (TextView) findViewById(R.id.shenfen);

	}

	// 点击拒绝审核按钮
	public void jujue(View v) {
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		phone = intent.getStringExtra("phone");
		final ProgressDialog dialog = new ProgressDialog(Jiaoshixiangxi.this);
		dialog.setTitle("正在处理...");
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
		String url = "http://www.huanglinqing.com/yidongjiajiao/deletejiaoshi/";
		new deletejiaoshi(name, url, handler2).start();
		sendSMS(phone,
				"尊敬的用户您好!移动家教提醒您:您的教师信息未通过审核,请核对您的账户信息,须确保用户信息真实准确,感谢您的使用!");
		Toast.makeText(Jiaoshixiangxi.this, "已将回执发送给用户", 0).show();
	}

	// 发送回执短信
	public void sendSMS(String phoneNumber, String message) {
		// 获取短信管理器
		android.telephony.SmsManager smsManager = android.telephony.SmsManager
				.getDefault();
		// 拆分短信内容（手机短信长度限制）
		List<String> divideContents = smsManager.divideMessage(message);
		for (String text : divideContents) {
			smsManager.sendTextMessage(phoneNumber, null, text, null, null);
		}
	}

	// 点击通过审核按钮
	public void tongguo(View v) {
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		final ProgressDialog dialog = new ProgressDialog(Jiaoshixiangxi.this);
		dialog.setTitle("正在处理...");
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
		String url = "http://www.huanglinqing.com/yidongjiajiao/quanxianbiangeng/";
		new shenhebiangeng(name, url, handler).start();
	}

	// 变更用户权限
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			String json = (String) msg.obj;
			if (json.indexOf("成功") > 0) {
				sendSMS(phone, "尊敬的用户您好!移动家教提醒您:您的教师信息已经通过审核,赶快登录吧!");
				Toast.makeText(Jiaoshixiangxi.this, "回执信息已发送给用户", 0).show();
			} else {
				Toast.makeText(Jiaoshixiangxi.this, "服务器繁忙请稍后重试", 0).show();
			}
		};
	};
	// 变更用户权限
	private Handler handler2 = new Handler() {

		public void handleMessage(android.os.Message msg) {
			String json = (String) msg.obj;
			if (json.indexOf("成功") > 0) {

				System.out.println("s");
			} else {
				System.out.println("s");
				// Toast.makeText(Jiaoshixiangxi.this, "服务器繁忙请稍后重试", 0).show();
			}
		};
	};

}
