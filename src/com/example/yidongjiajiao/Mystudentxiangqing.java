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

import com.bumptech.glide.Glide;
import com.example.yidongjiajiao.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Mystudentxiangqing extends Activity {
	private List<sxiangxin> urllist;
	private xiangadapter adapter1;
	private SimpleAdapter adapter;
	private ListView listView;
	private String name[] = new String[1020];
	private String phone[] = new String[1020];
	private String sex[] = new String[1020];
	private String touxiang[] = new String[1020];
	private TextView textView;
	private TextView textView1;
	private TextView textView2;
	private ImageView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mystudentxiangqing);

		// 得到传至用户名
		Intent intent = getIntent();
		String name1 = intent.getStringExtra("name");
		String url = "http://www.huanglinqing.com/yidongjiajiao/xinxixue/getnewsJSON.php?name="
				+ name1;
		new HTTPutils().getNewsJSON(url, handler);
		urllist = new ArrayList<sxiangxin>();
		adapter1 = new xiangadapter(Mystudentxiangqing.this, urllist);
		textView = (TextView) findViewById(R.id.name);
		textView1 = (TextView) findViewById(R.id.sex);
		textView2 = (TextView) findViewById(R.id.phone);

		view = (ImageView) findViewById(R.id.imageview);
//		view.getSettings().setJavaScriptEnabled(false);
//		view.getSettings().setSupportZoom(false);
//		view.getSettings().setBuiltInZoomControls(false);
//		view.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//		view.getSettings().setDefaultFontSize(18);

	}

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
				Glide.with(Mystudentxiangqing.this).load(touxiang[0])
				.into(view);
				view.setImageBitmap(base64ToBitmap(touxiang[0]));
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
}
