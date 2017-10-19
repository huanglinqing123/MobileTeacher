package frament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.yidongjiajiao.Jiaoshixiangxi;
import com.example.yidongjiajiao.R;
import com.example.yidongjiajiao.deletexuesheng;
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
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;

public class guanlixueshengFra extends Fragment {
	private ListView listView;
	private List<getUrl> urllist;
	private getUrl url;
	private String name[] = new String[200];
	private String phone[] = new String[200];
	private String sex[] = new String[200];
	private String touxiang[] = new String[200];
	private MyAdapter adapter1;
	private List<Map<String, Object>> dates = new ArrayList<Map<String, Object>>();
	// 读取所有学生信息线程
	public static final String geturl = "http://www.huanglinqing.com/yidongjiajiao/xueshengxinxi/getnewsJSON.php";
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
					touxiang[i] = jsonObject.getString("touxiang");
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("name", name[i]);
					item.put("sex", sex[i]);
					item.put("phone", phone[i]);
					item.put("touxiang", touxiang[i]);
					urllist.add(new getUrl(name[i], sex[i], phone[i],
							touxiang[i],1));
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
		View v = inflater.inflate(R.layout.guanlixuesheng, null);
		listView = (ListView) v.findViewById(R.id.listView1);
		HTTPutils.getNewsJSON(geturl, handler);
		urllist = new ArrayList<getUrl>();
		adapter1 = new MyAdapter(getActivity(), urllist);
		
		registerForContextMenu(listView);
//		
		return v;
	}

	// 长按菜单事件
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo contextMenuInfo) {
		menu.setHeaderTitle("请选择操作");
		menu.add(0, 1, 0, "删除该学生");
		menu.add(0, 2, 0, "取消");
	}

	// 事件
	public boolean onContextItemSelected(MenuItem item) {
		final ProgressDialog dialog = new ProgressDialog(getActivity());
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
			String url = "http://www.huanglinqing.com/yidongjiajiao/deletexuesheng";
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
				Toast.makeText(getActivity(), "删除成功", 0).show();
				urllist.clear();
				HTTPutils.getNewsJSON(geturl, handler);
			} else {
				Toast.makeText(getActivity(), "服务器繁忙，请稍后重试", 0).show();
			}

		};
	};

}
