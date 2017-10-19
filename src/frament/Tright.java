package frament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.yidongjiajiao.Jiaoshizhanghu;
import com.example.yidongjiajiao.Mykecheng;
import com.example.yidongjiajiao.Mystudent;
import com.example.yidongjiajiao.R;
import com.example.yidongjiajiao.Zhuangtai;
import com.example.yidongjiajiao.R.drawable;
import com.example.yidongjiajiao.R.id;
import com.example.yidongjiajiao.R.layout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Tright extends Fragment {
	private ListView listView;
	private String titles[] = { "账户信息", "我的学生", "我的课程", "我的状态" };
	private int Image[] = { R.drawable.teacher, R.drawable.student0,
			R.drawable.kecheng, R.drawable.statue };
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private SimpleAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.jiaoshixinxi, null);
		listView = (ListView) v.findViewById(R.id.listView1);
		final String name = getArguments().getString("name");

		final String pass = getArguments().getString("password");
		for (int i = 0; i < 4; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", titles[i]);
			map.put("image", Image[i]);
			list.add(map);
		}
		adapter = new SimpleAdapter(getActivity(), list, R.layout.jiaoshiwode,
				new String[] { "title", "image" }, new int[] { R.id.text1,
						R.id.image1 });
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 0) {
					Intent intent = new Intent(getActivity(),
							Jiaoshizhanghu.class);
					intent.putExtra("name", name);
					intent.putExtra("pass", pass);
					startActivity(intent);
					getActivity().finish();
				} else if (arg2 == 1) {
					Intent intent = new Intent(getActivity(), Mystudent.class);
					intent.putExtra("name", name);
					startActivity(intent);
				} else if (arg2 == 2) {
					// 点击的是我的课程的按钮
					Intent intent = new Intent(getActivity(), Mykecheng.class);
					intent.putExtra("name", name);
					startActivity(intent);
				} else if (arg2 == 3) {
					// 点击的是我的状态
					Intent intent = new Intent(getActivity(), Zhuangtai.class);
					intent.putExtra("name", name);
					startActivity(intent);
				}
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
