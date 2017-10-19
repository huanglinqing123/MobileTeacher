package com.example.yidongjiajiao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.yidongjiajiao.R;

import servers.HTTPutils;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Kechengpinglun extends Activity {
	private String content[] = new String[1020];
	private ListView listView;
    private String sname[]=new String[1021];
    private List<Map<String, Object>> dates = new ArrayList<Map<String, Object>>();
	private SimpleAdapter adapter1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_kechengpinglun);
		Intent intent = getIntent();
		listView=(ListView) findViewById(R.id.listView1);
		String kname = intent.getStringExtra("kname");
		String tname = intent.getStringExtra("tname");
		Toast.makeText(Kechengpinglun.this, kname + tname, 0).show();
		String url = "http://www.huanglinqing.com/yidongjiajiao/pinglun/getnewsJSON.php?tname="
				+ tname + "&&kname=" + kname;
		new HTTPutils().getNewsJSON(url, handler);
	}

	// 获取解析评论内容
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String jsondate = (String) msg.obj;
			try {
				JSONArray array = new JSONArray(jsondate);
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject = array.getJSONObject(i);
					content[i] = jsonObject.getString("content");
					sname[i] = jsonObject.getString("sname");
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("content", content[i]);
					item.put("sname", sname[i]);
					dates.add(item);
				}
				adapter1 = new SimpleAdapter(Kechengpinglun.this, dates,
						R.layout.pinglunneirong,
						new String[] { "content", "sname"},
						new int[] { R.id.content, R.id.name
								 });
				listView.setAdapter(adapter1);
				//
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

}
