package com.example.yidongjiajiao;

import com.example.yidongjiajiao.R;

import frament.Guanlizhanghu;
import frament.guanlijiaoshiFra;
import frament.guanlixueshengFra;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Guanliyuan extends FragmentActivity implements OnClickListener {

	private ImageButton left, right;
	private TextView textView1, textView2;
	private Fragment fragmentleft, fragmentright;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guanliyuan);
		init();// 系统组件初始化函数
		jianting();// 初始化监听事件
	}

	// 系统组件初始化函数
	private void init() {
		left = (ImageButton) findViewById(R.id.left);
		right = (ImageButton) findViewById(R.id.right);
		textView1 = (TextView) findViewById(R.id.t1);
		textView2 = (TextView) findViewById(R.id.t2);
		select(1);
	}

	//
	// 防止重叠
	public void onAttachFragment(Fragment fragment) {
		// TODO Auto-generated method stub
		super.onAttachFragment(fragment);
		if (fragmentleft == null && fragment instanceof guanlixueshengFra) {
			fragmentleft = (guanlixueshengFra) fragment;
		} else if (fragmentright == null && fragment instanceof guanlijiaoshiFra) {
			fragmentright = (guanlijiaoshiFra) fragment;
		}
	}
	// 底部菜单事件
	public void select(int i) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideframent(transaction);
		switch (i) {
		case 0:
			if (fragmentleft == null) {
				fragmentleft = new guanlixueshengFra();
				Intent intent = getIntent();
				String name = intent.getStringExtra("name");
				Bundle bundle = new Bundle();
				bundle.putString("name", name);
				fragmentleft.setArguments(bundle);

				transaction.add(R.id.frament, fragmentleft);
			} else {
				transaction.show(fragmentleft);
			}
			transaction.commit();
			left.setImageResource(R.drawable.gxuesheng);
			textView1.setTextColor(Color.BLUE);
			break;
		case 1:
			if (fragmentright == null) {
				fragmentright = new guanlijiaoshiFra();
				transaction.add(R.id.frament, fragmentright);
			} else {
				transaction.show(fragmentright);
			}
			transaction.commit();
			right.setImageResource(R.drawable.gjiaoshi);
			textView2.setTextColor(Color.BLUE);
			break;

		}
	}

	// 隐藏底部菜单
	private void hideframent(FragmentTransaction transaction) {
		if (fragmentleft != null) {
			transaction.hide(fragmentleft);
		}
		if (fragmentright != null) {
			transaction.hide(fragmentright);
		}

	}

	// 初始化监听事件

	private void jianting() {
		left.setOnClickListener(Guanliyuan.this);
		right.setOnClickListener(Guanliyuan.this);
	}

	@Override
	// 监听事件
	public void onClick(View v) {
		resetimage();
		switch (v.getId()) {
		case R.id.left:
			select(0);
			break;
		case R.id.right:
			select(1);
			break;

		}
	}

	// 底部菜单栏初始化
	private void resetimage() {
		// TODO Auto-generated method stub
		left.setImageResource(R.drawable.guanxuesheng0);
		right.setImageResource(R.drawable.guanjiaoshi0);
		textView2.setTextColor(Color.BLACK);
		textView1.setTextColor(Color.BLACK);
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
					finish();
				} else {
					Toast.makeText(Guanliyuan.this, "再按一次返回键退出",
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
		return super.onKeyDown(keyCode, event);
	}

	// 头像按钮点击事件
	public void xinxi(View v) {
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		String password = intent.getStringExtra("password");
		Intent intent2 = new Intent(Guanliyuan.this, Guanlizhanghu.class);
		intent2.putExtra("name", name);
		intent2.putExtra("password", password);
		startActivity(intent2);
		Guanliyuan.this.finish();

	}

}
