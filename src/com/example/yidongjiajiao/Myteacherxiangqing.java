package com.example.yidongjiajiao;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import servers.HTTPutils;
import servers.addpinglun;
import servers.deletepinglun;

import com.bumptech.glide.Glide;
import com.example.yidongjiajiao.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Myteacherxiangqing extends Activity {
	private List<sxiangxin> urllist;
	private xiangadapter adapter1;
	private SimpleAdapter adapter;
	private ListView listView;
	private String name[] = new String[1020];
	private String sname[] = new String[1020];
	private String tsname[] = new String[1020];
	private String content[] = new String[1020];
	private String phone[] = new String[1020];
	private String sex[] = new String[1020];
	private String touxiang[] = new String[1020];
	private TextView textView;
	private TextView textView1;
	private TextView textView2;
	private ImageView imageView;
	private EditText editText;
	private ListView listView2;
	private List<Map<String, Object>> dates = new ArrayList<Map<String, Object>>();
	private SimpleAdapter adapter2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myteacherxiangqing);
		listView2 = (ListView) findViewById(R.id.xiaoshipl);
		// 得到传至用户名
		Intent intent = getIntent();
		final String name1 = intent.getStringExtra("name");// 教师
		final String name2 = intent.getStringExtra("sname");// 学生
		String url = "http://www.huanglinqing.com/yidongjiajiao/xinxishi/getnewsJSON.php?name="
				+ name1;
		new HTTPutils().getNewsJSON(url, handler);
		urllist = new ArrayList<sxiangxin>();
		adapter1 = new xiangadapter(Myteacherxiangqing.this, urllist);
		textView = (TextView) findViewById(R.id.name);
		textView1 = (TextView) findViewById(R.id.sex);
		textView2 = (TextView) findViewById(R.id.phone);
		editText = (EditText) findViewById(R.id.pinglun);
		imageView = (ImageView) findViewById(R.id.imageview);
		String piurl = "http://www.huanglinqing.com/yidongjiajiao/gerenpinglun/getnewsJSON.php?sname="
				+ name2 + "&&tname=" + name1;
		//
		new HTTPutils().getNewsJSON(piurl, handler3);// 学生查看自己的评论

		registerForContextMenu(listView2);

	}

	// 事件
	public boolean onContextItemSelected(MenuItem item) {
		final ProgressDialog dialog = new ProgressDialog(
				Myteacherxiangqing.this);
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
			Intent intent = getIntent();

			final String name1 = intent.getStringExtra("name");// 教师
			final String name2 = intent.getStringExtra("sname");// 学生
			int position = contextMenuInfo.position;
			String sn = content[position];
			String deleteurl = "http://www.huanglinqing.com/yidongjiajiao/deletepinglun/";
			new deletepinglun(name2, name1, sn, deleteurl, handler5).start();
			adapter2.notifyDataSetChanged();
			break;

		default:
			break;
		}
		return false;

	}

	// 长按菜单事件
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo contextMenuInfo) {
		menu.setHeaderTitle("请选择操作");
		menu.add(0, 1, 0, "删除该记录");
		menu.add(0, 2, 0, "取消");
	}

	// 删除个人评论
	private Handler handler5 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String json = (String) msg.obj;
			int in = json.indexOf("成功");
			if (in > 0) {
				Toast.makeText(Myteacherxiangqing.this, "删除成功", 0).show();
				//

				Intent intent = getIntent();
				final String name1 = intent.getStringExtra("name");// 教师
				final String name2 = intent.getStringExtra("sname");// 学生
				String piurl = "http://www.huanglinqing.com/yidongjiajiao/gerenpinglun/getnewsJSON.php?sname="
						+ name2 + "&&tname=" + name1;
				//
				dates.clear();
				new HTTPutils().getNewsJSON(piurl, handler3);// 学生查看自己的评论

			} else {
				Toast.makeText(Myteacherxiangqing.this, "服务器繁忙，请稍后重试", 0)
						.show();
			}
		};
	};

	// 读取个人评论信息
	private Handler handler3 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String jsondate = (String) msg.obj;
			try {
				if (jsondate.equals("没有数据")) {
					Toast.makeText(Myteacherxiangqing.this, "暂无您的评论", 0).show();
					adapter2 = new SimpleAdapter(Myteacherxiangqing.this,
							dates, R.layout.pinglunneirong,
							new String[] { "content" },
							new int[] { R.id.content });
					adapter2.notifyDataSetChanged();
					listView2.setAdapter(adapter2);
				} else {
					dates.clear();
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
					adapter2 = new SimpleAdapter(Myteacherxiangqing.this,
							dates, R.layout.pinglunneirong,
							new String[] { "content" },
							new int[] { R.id.content });
					adapter2.notifyDataSetChanged();
					listView2.setAdapter(adapter2);

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

	// 读取学生详细信息
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			String jsondate = (String) msg.obj;

			try {
				JSONArray array = new JSONArray(jsondate);
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject = array.getJSONObject(i);
					name[i] = jsonObject.getString("name");
					touxiang[i] = jsonObject.getString("touxiang");
					phone[i] = jsonObject.getString("phone");
					sex[i] = jsonObject.getString("sex");

				}
				textView.setText(name[0]);
				textView1.setText(sex[0]);
				textView2.setText(phone[0]);
				Glide.with(Myteacherxiangqing.this).load(touxiang[0])
						.into(imageView);
				imageView.setImageBitmap(base64ToBitmap(touxiang[0]));
				// view.loadUrl(touxiang[0]);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

	public static Bitmap base64ToBitmap(String base64Data) {

		// byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		// return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		byte[] by = base64Data.getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(by);
		Bitmap bitmap = BitmapFactory.decodeStream(bais);
		return bitmap;
	}

	// 评论按钮监听事件
	public void send(View v) {
		String content = editText.getText().toString();
		if (content.isEmpty()) {
			Toast.makeText(Myteacherxiangqing.this, "您还未输入评论", 0).show();
		} else {
			// 插入评论
			String url = "http://www.huanglinqing.com/yidongjiajiao/cpl/";
			Intent intent = getIntent();
			String tname = intent.getStringExtra("sname");
			String name = intent.getStringExtra("name");

			new addpinglun(tname, name, content, url, handler2).start();
		}
	}

	private Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String js = (String) msg.obj;
			int i = js.indexOf("成功");
			if (i > 0) {
				Toast.makeText(Myteacherxiangqing.this, "评论成功", 0).show();
				editText.setText("");
				// adapter2.notifyDataSetChanged();
				Intent intent = getIntent();
				String name1 = intent.getStringExtra("name");// 教师
				String name2 = intent.getStringExtra("sname");// 学生
				String piurl = "http://www.huanglinqing.com/yidongjiajiao/gerenpinglun/getnewsJSON.php?sname="
						+ name2 + "&&tname=" + name1;
				new HTTPutils().getNewsJSON(piurl, handler3);// 学生查看自己的评论
			} else {
				Toast.makeText(Myteacherxiangqing.this, "服务器繁忙", 0).show();
			}
		};
	};

}