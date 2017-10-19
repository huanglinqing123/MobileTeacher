package com.example.yidongjiajiao;

import com.example.yidongjiajiao.R;

import servers.Updateke;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class Genggaikecheng extends Activity {

	private EditText editText1, editText2, editText3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_genggaikecheng);
		editText1 = (EditText) findViewById(R.id.tname);
		editText2 = (EditText) findViewById(R.id.address);
		editText3 = (EditText) findViewById(R.id.price);
		Intent intent = getIntent();
		String kname = intent.getStringExtra("kname");
		String address = intent.getStringExtra("address");
		String price = intent.getStringExtra("price");
		editText1.setText(kname);
		editText2.setText(address);
		editText3.setText(price);
		init();
	}

	// 设置初始状态的edteittext不可编辑
	private void init() {
		editText1.setFocusable(false);
		editText2.setFocusable(false);
		editText3.setFocusable(false);
	}

	// 点击更改按钮
	public void update(View v) {
		editText1.setFocusableInTouchMode(true);
		editText2.setFocusableInTouchMode(true);
		editText3.setFocusableInTouchMode(true);
	}

	// 保存更改按钮
	public void save(View v) {
		Intent intent = getIntent();

		// 更新课程信息
		String url = "http://www.huanglinqing.com/yidongjiajiao/Updateke/";
		String kname1 = editText1.getText().toString();
		String ttdress = editText2.getText().toString();
		String tprice = editText3.getText().toString();
		String name = intent.getStringExtra("tname");
		// new UpdateServer(name, password, url, handler).start();
		new Updateke(kname1, ttdress, tprice, name, url, handler).start();

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result = (String) msg.obj;
			int result1 = result.indexOf("修改成功");
			if (result1 > 0) {
				Toast.makeText(Genggaikecheng.this, "课程信息更新成功", 0).show();

			}
		};
	};

}
