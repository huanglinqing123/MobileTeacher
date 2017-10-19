package frament;

import servers.UpdateServer;
import servers.UpdateServer2;

import com.example.yidongjiajiao.Login;
import com.example.yidongjiajiao.R;
import com.example.yidongjiajiao.R.id;
import com.example.yidongjiajiao.R.layout;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class Guanlizhanghu extends Activity {
	private EditText editText1, editText2, editText3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guanlizhanghu);

		editText1 = (EditText) findViewById(R.id.name);
		editText2 = (EditText) findViewById(R.id.pass);
		editText3 = (EditText) findViewById(R.id.pass2);
		editText1.setFocusable(false);
		editText2.setFocusable(false);
		editText3.setFocusable(false);
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		String pass = intent.getStringExtra("password");
		editText1.setText(name);
		editText2.setText(pass);
		editText3.setText(pass);
		init();
	}

	// 点击退出当前登录
	public void zhuxiao(View v) {
		Login.sp.edit().putBoolean("IsAuto", false).commit();
		startActivity(new Intent(Guanlizhanghu.this, Login.class));
		Guanlizhanghu.this.finish();
	}

	// 点击修改密码
	public void update(View v) {
		// editText1.setFocusableInTouchMode(true);
		editText2.setFocusableInTouchMode(true);
		editText3.setFocusableInTouchMode(true);

	}

	// 保存修改
	public void xiugai(View v) {
		Intent intent = getIntent();
		String pass = intent.getStringExtra("pass");
		if (editText2.getText().toString().isEmpty()
				|| editText3.getText().toString().isEmpty()) {
			Toast.makeText(Guanlizhanghu.this, "密码不能为空", 0).show();
		} else if (!editText2.getText().toString()
				.equals(editText3.getText().toString())) {
			Toast.makeText(Guanlizhanghu.this, "两次输入的密码不一致", 0).show();
		} else if (editText2.getText().toString().equals(pass)) {
			Toast.makeText(Guanlizhanghu.this, "新密码与当前密码相同", 0).show();
		} else {
			// 更新用户密码的功能
			String url = "http://www.huanglinqing.com/yidongjiajiao/up/";
			String password = editText2.getText().toString();
			String name = intent.getStringExtra("name");
			new UpdateServer2(name, password, url, handler).start();
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result = (String) msg.obj;
			int result1 = result.indexOf("修改成功");
			if (result1 > 0) {
				Toast.makeText(Guanlizhanghu.this, "密码修改成功，请重新登录", 0).show();
				startActivity(new Intent(Guanlizhanghu.this, Login.class));
				Guanlizhanghu.this.finish();
			}
		};
	};

	private void init() {
		editText1.setFocusable(false);
		editText2.setFocusable(false);
		editText3.setFocusable(false);
	}

}
