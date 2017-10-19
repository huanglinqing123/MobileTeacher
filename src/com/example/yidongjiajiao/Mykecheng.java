package com.example.yidongjiajiao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.yidongjiajiao.R;

import servers.HTTPutils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ToggleButton;

public class Mykecheng extends Activity {

	private EditText editText1, editText2, editText3;
	private String kecheng[] = new String[1024];
	private String didian[] = new String[1024];
	private String jiage[] = new String[1024];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mykecheng);
		editText1 = (EditText) findViewById(R.id.kecheng);
		editText2 = (EditText) findViewById(R.id.didian);
		editText3 = (EditText) findViewById(R.id.jiage);
		editText1.setFocusable(false);
		editText2.setFocusable(false);
		editText3.setFocusable(false);
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		String geturl = "http://www.huanglinqing.com/yidongjiajiao/jiaoshikecheng/getnewsJSON.php?name="
				+ name;
		new HTTPutils().getNewsJSON(geturl, handler2);

	}

	// 读取教师课程信息的线程机制
	private Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			String jsondate = (String) msg.obj;
			try {
				JSONArray array = new JSONArray(jsondate);
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject = array.getJSONObject(i);
					kecheng[i] = jsonObject.getString("kechengname");
					didian[i] = jsonObject.getString("address");
					jiage[i] = jsonObject.getString("price");

				}
				//
				editText1.setText(kecheng[0]);
				editText2.setText(didian[0]);
				editText3.setText(jiage[0]);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

	// 更改课程
	public void update(View v) {
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		editText2.setFocusableInTouchMode(true);
		editText3.setFocusableInTouchMode(true);
		editText1.setFocusableInTouchMode(true);
	}

	// 保存课程
	public void save(View v) {
		String kecheng = editText1.getText().toString();
		String address = editText2.getText().toString();
		String jiage = editText3.getText().toString();
		// 获取教师的用户名
		final ProgressDialog dialog = new ProgressDialog(Mykecheng.this);
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		String url = "http://www.huanglinqing.com/yidongjiajiao/wanshanxinxi/";
		new wanshanxinxi(name, kecheng, address, jiage, url, handler).start();
		dialog.setTitle("正在保存...");
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

	// 将用户的数据保存至服务器端数据库
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String json = (String) msg.obj;
			int idenx = json.indexOf("修改成功");
			if (idenx >= 0) {
				Toast.makeText(Mykecheng.this, "信息完善成功", 0).show();
			} else {
				Toast.makeText(Mykecheng.this, "服务器繁忙,请稍后重试", 0).show();
			}
		};
	};

}
