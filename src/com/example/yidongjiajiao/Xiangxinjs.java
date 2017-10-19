package com.example.yidongjiajiao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import servers.HTTPutils;
import servers.yuyue;

import com.example.yidongjiajiao.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Xiangxinjs extends Activity {

	private TextView editText1, editText2, editText3, editText4, editText5,
			editText6, editText7;
	private ListView listView;
	private EditText editText;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_xiangxinjs);
		listView = (ListView) findViewById(R.id.neirong);
		editText1 = (TextView) findViewById(R.id.name);
		editText2 = (TextView) findViewById(R.id.age);
		editText3 = (TextView) findViewById(R.id.shenfen);
		editText4 = (TextView) findViewById(R.id.zhuangtai);
		editText5 = (TextView) findViewById(R.id.jiage);
		editText6 = (TextView) findViewById(R.id.didian);
		editText7 = (TextView) findViewById(R.id.dianhua);
		button = (Button) findViewById(R.id.ok);
		Intent intent = getIntent();
		String name = intent.getStringExtra("teachername");
		String age = intent.getStringExtra("age");
		String shenfen = intent.getStringExtra("shenfen");
		int zhuangtai = intent.getIntExtra("statue", 0);
		String kecheng = intent.getStringExtra("kecheng");
		String price = intent.getStringExtra("price");
		String address = intent.getStringExtra("address");
		String dianhua = intent.getStringExtra("dianhua");
		editText1.setText(name);
		editText2.setText(age);
		editText3.setText(shenfen);
		if (zhuangtai == 0) {
			editText4.setText("忙碌");
		} else {
			editText4.setText("在线");
		}

		editText5.setText(price);
		editText6.setText(address);
		editText7.setText(dianhua);
		final String url = "http://www.huanglinqing.com/yidongjiajiao/pinglun/getnewsJSON.php?tname="
				+ name;
		new HTTPutils().getNewsJSON(url, handler);

	}

	private String[] sname = new String[1024];
	private String[] content = new String[1024];
	private SimpleAdapter adapter;
	private List<Map<String, Object>> dates = new ArrayList<Map<String, Object>>();
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String jsondate = (String) msg.obj;
			try {
				JSONArray array = new JSONArray(jsondate);
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject = array.getJSONObject(i);
					sname[i] = jsonObject.getString("sname");
					content[i] = jsonObject.getString("content");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("sname", sname[i]);
					map.put("content", content[i]);
					dates.add(map);
				}
				//
				adapter = new SimpleAdapter(Xiangxinjs.this, dates,
						R.layout.pinglunneirong1, new String[] { "sname",
								"content" }, new int[] { R.id.name,
								R.id.content });
				listView.setAdapter(adapter);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

	// 拨打电话按钮监听事件
	public void clall(View v) {
		TextView textView = (TextView) findViewById(R.id.dianhua);
		TextView textView1 = (TextView) findViewById(R.id.name);
		String num = textView.getText().toString();
		String name = textView1.getText().toString();
		Intent intent = new Intent(Intent.ACTION_CALL);
		Uri data = Uri.parse("tel:" + num);
		Intent i = getIntent();
		intent.setData(data);

		startActivity(intent);
		button.setVisibility(1);
		//
	}

	// 点击确定按钮再保存
	public void ok(View v) {
		Intent i = getIntent();
		TextView textView1 = (TextView) findViewById(R.id.name);
		String name = textView1.getText().toString();
		String url = "http://www.huanglinqing.com/yidongjiajiao/yuyue/";
		String sname = i.getStringExtra("name");
		new yuyue(sname, name, url, handler3).start();
	}

	private Handler handler3 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String js = (String) msg.obj;
			Toast.makeText(Xiangxinjs.this, "选择教师成功", 0).show();
		};
	};

}
