package frament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import servers.HTTPutils;
import servers.TgetUrl;

import com.example.yidongjiajiao.R;
import com.example.yidongjiajiao.R.id;
import com.example.yidongjiajiao.R.layout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import adapter.TMyAdapter;
import android.graphics.Paint.Join;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Tleft extends Fragment {
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
	private String[] sname = new String[1024];
	private String[] content = new String[1024];
	private SimpleAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.tleft, null);
		listView = (ListView) v.findViewById(R.id.listView1);
		final String name = getArguments().getString("name");
		final String url = "http://www.huanglinqing.com/yidongjiajiao/pinglun/getnewsJSON.php?tname="
				+ name;
		new HTTPutils().getNewsJSON(url, handler);
		return v;
	}
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
				adapter = new SimpleAdapter(getActivity(), dates,
						R.layout.pinglunneirong, new String[] { "sname",
								"content" }, new int[] { R.id.name,
								R.id.content });
				listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

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
