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
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class Mystudent extends Activity {
    ArrayAdapter<String>adapter;
    private String no="没有数据";
	private String[] name = new String[200];
	private String[] phone = new String[200];
	private SimpleAdapter adapter1;
	private List<Map<String, Object>> dates = new ArrayList<Map<String, Object>>();
	private ListView listView2;
	String geturl = "http://www.huanglinqing.com/yidongjiajiao/mystudent/getnewsJSON.php?name=";
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String jsondate = (String) msg.obj;
			try {
				
				if (jsondate.equals("没有数据")) {
					Toast.makeText(Mystudent.this, "没有学生数据", 0).show();
					adapter1 = new SimpleAdapter(Mystudent.this, dates,
							R.layout.student, new String[] { "name", "phone" },
							new int[] { R.id.name, R.id.phone });
					adapter1.notifyDataSetChanged();
					listView2.setAdapter(adapter1);
				} else {
					JSONArray array = new JSONArray(jsondate);
					for (int i = 0; i < array.length(); i++) {
						JSONObject jsonObject = array.getJSONObject(i);
						name[i] = jsonObject.getString("studentname");
						phone[i] = jsonObject.getString("studentphone");
						Map<String, Object> item = new HashMap<String, Object>();
						item.put("name", name[i]);
						item.put("phone", phone[i]);
						dates.add(item);

					}
					adapter1 = new SimpleAdapter(Mystudent.this, dates,
							R.layout.student, new String[] { "name", "phone" },
							new int[] { R.id.name, R.id.phone });
					adapter1.notifyDataSetChanged();
					listView2.setAdapter(adapter1);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mystudent1);
		Intent intent = getIntent();
		String nn = intent.getStringExtra("name");
		geturl = "http://www.huanglinqing.com/yidongjiajiao/mystudent/getnewsJSON.php?name="
				+ nn;
		listView2 = (ListView) findViewById(R.id.listView1);
		HTTPutils.getNewsJSON(geturl, handler);
		// 单击查看详细信息
		listView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String username = name[arg2];
				Intent intent = new Intent(Mystudent.this,
						Mystudentxiangqing.class);
				intent.putExtra("name", username);
				startActivity(intent);

			}
		});
		registerForContextMenu(listView2);
	}

	// 长按菜单事件
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo contextMenuInfo) {
		menu.setHeaderTitle("请选择操作");
		menu.add(0, 1, 0, "删除该记录");
		menu.add(0, 2, 0, "取消");
	}

	// 事件
	public boolean onContextItemSelected(MenuItem item) {
		final ProgressDialog dialog = new ProgressDialog(Mystudent.this);
		switch (item.getItemId()) {
		case 1:
			ContextMenuInfo info = item.getMenuInfo();
			AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			dialog.setTitle("正在删除...");
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
			int position = contextMenuInfo.position;
			String url = "http://www.huanglinqing.com/yidongjiajiao/deletemystudent";
			new deletexuesheng(name[position], url, handler2).start();
			adapter1.notifyDataSetChanged();
			break;

		default:
			break;
		}
		return false;

	}

	// 删除学生信息判断线程
	private Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			String json = (String) msg.obj;
			if (json.indexOf("成功") > 0) {
				Toast.makeText(Mystudent.this, "删除成功", 0).show();
				dates.clear();
				HTTPutils.getNewsJSON(geturl, handler);
			} else {
				Toast.makeText(Mystudent.this, "服务器繁忙，请稍后重试", 0).show();
			}

		};
	};

}
