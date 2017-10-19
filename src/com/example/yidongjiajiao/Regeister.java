package com.example.yidongjiajiao;

import com.example.yidongjiajiao.R;

import frament.Framentstudent;
import frament.Framentteacher;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Regeister extends FragmentActivity implements OnClickListener {

	private Button student, teacher;
	private Fragment fragments, fragmentt;

	public void onAttachFragment(Fragment fragment) {
		// TODO Auto-generated method stub
		super.onAttachFragment(fragment);
		if (fragments == null && fragment instanceof Framentstudent) {
			fragments = (Framentstudent) fragment;
		} else if (fragmentt == null && fragment instanceof Framentteacher) {
			fragmentt = (Framentteacher) fragment;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_regeister);
		init();
		jianting();
		resetimage();
		select(0);
	}

	// 布局初始化
	private void init() {
		student = (Button) findViewById(R.id.student);
		teacher = (Button) findViewById(R.id.teacher);

	}

	// 初始化监听事件
	private void jianting() {
		student.setOnClickListener(Regeister.this);
		teacher.setOnClickListener(Regeister.this);

	}

	// 底部菜单事件
	public void select(int i) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideframent(transaction);
		switch (i) {
		case 0:
			if (fragments == null) {

				fragments = new Framentstudent();

				transaction.add(R.id.frament1, fragments);
			} else {
				transaction.show(fragments);
			}
			transaction.commit();
			student.setBackgroundResource(R.drawable.head);

			break;
		case 1:
			if (fragmentt == null) {
				fragmentt = new Framentteacher();
				transaction.add(R.id.frament1, fragmentt);
			} else {
				transaction.show(fragmentt);
			}
			transaction.commit();
			teacher.setBackgroundResource(R.drawable.head);

			break;

		}
	}

	private void hideframent(FragmentTransaction transaction) {
		if (fragments != null) {
			transaction.hide(fragments);
		}
		if (fragmentt != null) {
			transaction.hide(fragmentt);
		}

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		resetimage();
		switch (v.getId()) {
		case R.id.student:
			select(0);
			break;
		case R.id.teacher:
			select(1);
			break;

		}
	}

	private void resetimage() {
		// TODO Auto-generated method stub
		student.setBackgroundColor(Color.LTGRAY);
		teacher.setBackgroundColor(Color.LTGRAY);

	}
}
