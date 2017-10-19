package frament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.yidongjiajiao.Jiaoshixiangxi;
import com.example.yidongjiajiao.R;
import com.example.yidongjiajiao.R.id;
import com.example.yidongjiajiao.R.layout;

import servers.HTTPutils;
import servers.getUrl;

import adapter.MyAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class guanlijiaoshiFra extends Fragment {
	private ListView listView;
	private List<getUrl> urllist;
	private getUrl url;
	private String name[] = new String[200];
	private String age[] = new String[200];
	private String phone[] = new String[200];
	private String sex[] = new String[200];
	private String touxiang[] = new String[200];
	private String shenfen[] = new String[200];
	private int yanzheng[] = new int[200];
	private MyAdapter adapter1;
	private List<Map<String, Object>> dates = new ArrayList<Map<String, Object>>();
	// 读取所有学生信息线程
	public static final String geturl = "http://www.huanglinqing.com/yidongjiajiao/jiaoshixinxi/getnewsJSON.php";
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String jsondate = (String) msg.obj;
			try {
				JSONArray array = new JSONArray(jsondate);
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject = array.getJSONObject(i);
					name[i] = jsonObject.getString("name");
					sex[i] = jsonObject.getString("sex");
					phone[i] = jsonObject.getString("phone");
					age[i] = jsonObject.getString("tsage");
					shenfen[i] = jsonObject.getString("shenfen");
					yanzheng[i] = jsonObject.getInt("yanzheng");
					touxiang[i] = jsonObject.getString("touxiang");

					urllist.add(new getUrl(name[i], sex[i], phone[i],
							touxiang[i], yanzheng[i]));

				}
				//
				
				adapter1.notifyDataSetChanged();
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
		View v = inflater.inflate(
				com.example.yidongjiajiao.R.layout.guanlijiaoshi, null);
		listView = (ListView) v.findViewById(R.id.listView1);
		
		HTTPutils.getNewsJSON(geturl, handler);
		urllist = new ArrayList<getUrl>();
		adapter1 = new MyAdapter(getActivity(), urllist);
	
		
		// listview单击事件查看教师详细信息
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (yanzheng[position] == 0) {// 点击查看详细信息
					Intent intent = new Intent(getActivity(),
							Jiaoshixiangxi.class);
				    intent.putExtra("flag", "1");
					intent.putExtra("name", name[position]);
					intent.putExtra("age", age[position]);
					intent.putExtra("shenfen", shenfen[position]);
					intent.putExtra("sex", sex[position]);
					intent.putExtra("phone", phone[position]);
					intent.putExtra("touxiang", touxiang[position]);
					startActivity(intent);
				} else {
					Intent intent = new Intent(getActivity(),
							Jiaoshixiangxi.class);
				    intent.putExtra("flag", "0");
					intent.putExtra("name", name[position]);
					intent.putExtra("age", age[position]);
					intent.putExtra("shenfen", shenfen[position]);
					intent.putExtra("sex", sex[position]);
					intent.putExtra("phone", phone[position]);
					intent.putExtra("touxiang", touxiang[position]);
					startActivity(intent);
				}

			}
		});
		// registerForContextMenu(listView);
		return v;
	}

	

}
