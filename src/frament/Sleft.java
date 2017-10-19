package frament;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import servers.HTTPutils;
import servers.TgetUrl;

import com.example.yidongjiajiao.R;
import com.example.yidongjiajiao.Xiangxinjs;
import com.example.yidongjiajiao.R.id;
import com.example.yidongjiajiao.R.layout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import adapter.TMyAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Sleft extends Fragment {
	private ListView listView;
	private List<TgetUrl> urllist;
	private String[] kechengname = new String[200];
	private String[] address = new String[200];
	private String[] price = new String[200];
	private String[] teachername = new String[200];
	private String[] jiaoshitu = new String[200];
	private String[] sex = new String[200];
	private String[] shenfen = new String[200];
	private String[] tsage = new String[200];
	private int[] statue = new int[200];
	private String[] phone = new String[200];
	private TMyAdapter adapter1;
	private List<Map<String, Object>> dates = new ArrayList<Map<String, Object>>();
	// 读取所有课程信息
	public static final String geturl = "http://www.huanglinqing.com/yidongjiajiao/jiaoshixi/getnewsJSON.php";
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String jsondate = (String) msg.obj;

			try {
				JSONArray array = new JSONArray(jsondate);
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject = array.getJSONObject(i);
					kechengname[i] = jsonObject.getString("kechengname");
					address[i] = jsonObject.getString("address");
					price[i] = jsonObject.getString("price");
					teachername[i] = jsonObject.getString("name");
					jiaoshitu[i] = jsonObject.getString("touxiang");
					sex[i] = jsonObject.getString("sex");
					shenfen[i] = jsonObject.getString("shenfen");
					tsage[i] = jsonObject.getString("tsage");
					statue[i] = jsonObject.getInt("statue");
					phone[i] = jsonObject.getString("phone");
					urllist.add(new TgetUrl(teachername[i], kechengname[i],
							price[i], teachername[i], jiaoshitu[i], address[i],
							statue[i]));

				}
				listView.setAdapter(adapter1);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.sleft, null);
		
		listView = (ListView) v.findViewById(R.id.listView1);
		final String name = getArguments().getString("name");
         
		HTTPutils.getNewsJSON(geturl, handler);
		urllist = new ArrayList<TgetUrl>();
		adapter1 = new TMyAdapter(getActivity(), urllist);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int po = arg2;
				String string = getArguments().getString("name");
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), Xiangxinjs.class);
				intent.putExtra("teachername", teachername[po]);
				intent.putExtra("age", tsage[po]);
				intent.putExtra("shenfen", shenfen[po]);
				intent.putExtra("statue", statue[po]);
				intent.putExtra("kecheng", kechengname[po]);
				intent.putExtra("price", price[po]);
				intent.putExtra("address", address[po]);
				intent.putExtra("dianhua", phone[po]);
				intent.putExtra("name", string);
				startActivity(intent);

			}
		});
		return v;
	}

	// 再按一次退出事件
	private static final int MSG_EXIT = 1;
	private static final int MSG_EXIT_WAIT = 2;
	private static final long EXIT_DELAY_TIME = 2000;
	private Handler mHandle = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_EXIT:
				if (mHandle.hasMessages(MSG_EXIT_WAIT)) {
					getActivity().finish();
				} else {
					Toast.makeText(getActivity(), "再按一次返回键退出",
							Toast.LENGTH_SHORT).show();
					mHandle.sendEmptyMessageDelayed(MSG_EXIT_WAIT,
							EXIT_DELAY_TIME);
				}
				break;
			case MSG_EXIT_WAIT:
				break;
			}
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			mHandle.sendEmptyMessage(MSG_EXIT);
			return true;
		}
		return super.getActivity().onKeyDown(keyCode, event);
	}
}
