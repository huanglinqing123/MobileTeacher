package com.example.yidongjiajiao;

import com.example.yidongjiajiao.R;

import frament.Scenter;
import frament.Sleft;
import frament.Sright;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Student extends FragmentActivity implements OnClickListener {
	private ImageButton left, right, center;
	private TextView textView1, textView2, textView3;
	private Fragment fragmentleft, fragmentright, fragmentcenter;
	private LinearLayout l1, l2, l3;

	// 防止重叠
	public void onAttachFragment(Fragment fragment) {
		// TODO Auto-generated method stub
		super.onAttachFragment(fragment);
		if (fragmentleft == null && fragment instanceof Sleft) {
			fragmentleft = (Sleft) fragment;
		} else if (fragmentright == null && fragment instanceof Sright) {
			fragmentright = (Sright) fragment;
		} else if (fragmentcenter == null && fragment instanceof Scenter) {
			fragmentcenter = (Scenter) fragment;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_student);
		init();// 系统组件初始化函数
		jianting();// 初始化监听事件
	}

	// 系统组件初始化函数
	private void init() {
		center = (ImageButton) findViewById(R.id.center);
		left = (ImageButton) findViewById(R.id.left);
		right = (ImageButton) findViewById(R.id.right);
		l1 = (LinearLayout) findViewById(R.id.l1);
		l2 = (LinearLayout) findViewById(R.id.l2);
		l3 = (LinearLayout) findViewById(R.id.l3);
		textView3 = (TextView) findViewById(R.id.t3);
		textView1 = (TextView) findViewById(R.id.t1);
		textView2 = (TextView) findViewById(R.id.t2);
		select(2);
	}

	// 底部菜单事件
	public void select(int i) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideframent(transaction);
		switch (i) {
		case 0:
			if (fragmentleft == null) {
				fragmentleft = new Sleft();
				Intent intent = getIntent();
				String name = intent.getStringExtra("name");
				String password = intent.getStringExtra("password");
				Bundle bundle = new Bundle();
				bundle.putString("name", name);
				bundle.putString("password", password);
				fragmentleft.setArguments(bundle);

				transaction.add(R.id.frament, fragmentleft);
			} else {
				transaction.show(fragmentleft);
			}
			transaction.commit();
			left.setImageResource(R.drawable.kecheng);
			textView1.setTextColor(Color.BLUE);
			break;
		case 1:
			if (fragmentright == null) {
				fragmentright = new Sright();
				Intent intent = getIntent();
				String name = intent.getStringExtra("name");
				String password = intent.getStringExtra("password");
				Bundle bundle = new Bundle();
				bundle.putString("name", name);
				bundle.putString("password", password);
				fragmentright.setArguments(bundle);
				transaction.add(R.id.frament, fragmentright);
			} else {
				transaction.show(fragmentright);
			}
			transaction.commit();
			right.setImageResource(R.drawable.my);
			textView2.setTextColor(Color.BLUE);
			break;
		case 2:
			if (fragmentcenter == null) {
				fragmentcenter = new Scenter();
				Intent intent = getIntent();
				String name = intent.getStringExtra("name");
				String password = intent.getStringExtra("password");
				Bundle bundle = new Bundle();
				bundle.putString("name", name);
				bundle.putString("password", password);
				fragmentcenter.setArguments(bundle);

				transaction.add(R.id.frament, fragmentcenter);
			} else {
				transaction.show(fragmentcenter);
			}
			transaction.commit();
			center.setImageResource(R.drawable.yunzuoye);
			textView3.setTextColor(Color.BLUE);
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
		if (fragmentcenter != null) {
			transaction.hide(fragmentcenter);
		}

	}

	// 初始化监听事件

	private void jianting() {
		left.setOnClickListener(Student.this);
		right.setOnClickListener(Student.this);
		center.setOnClickListener(Student.this);
		l1.setOnClickListener(Student.this);
		l2.setOnClickListener(Student.this);
		l3.setOnClickListener(Student.this);
	}

	@Override
	// 监听事件
	public void onClick(View v) {
		resetimage();
		switch (v.getId()) {
		case R.id.l1:
			select(0);
			break;
		case R.id.l3:
			select(1);
			break;
		case R.id.l2:
			select(2);
			break;
		case R.id.left:
			select(0);
			break;
		case R.id.right:
			select(1);
			break;
		case R.id.center:
			select(2);
			break;

		}
	}

	// 底部菜单栏初始化
	private void resetimage() {
		// TODO Auto-generated method stub
		center.setImageResource(R.drawable.yunzuoye0);
		left.setImageResource(R.drawable.kecheng1);
		right.setImageResource(R.drawable.my0);
		textView3.setTextColor(Color.BLACK);
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
					Toast.makeText(Student.this, "再按一次返回键退出",
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

}
