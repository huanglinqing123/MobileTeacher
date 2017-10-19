package com.example.yidongjiajiao;

import com.example.yidongjiajiao.R;

import servers.dengluserver;
import servers.shenheserver;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Login extends Activity {
	public static SharedPreferences sp;
	public static  EditText editText1, editText2;
	private RadioButton radioButton1, radioButton2, radioButton3;
	private String name;
	private String password;
	private CheckBox rem_pwd, auto_login;
	private int status;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		editText1 = (EditText) findViewById(R.id.name);
		editText2 = (EditText) findViewById(R.id.pass);
		radioButton1 = (RadioButton) findViewById(R.id.radio1);
		radioButton2 = (RadioButton) findViewById(R.id.radio2);
		radioButton3 = (RadioButton) findViewById(R.id.radio3);
		sp = getSharedPreferences("userInfo", MODE_WORLD_READABLE);
		if (sp.getBoolean("IsCheck", false)) {
			// rem_pwd.setChecked(true);
			editText1.setText(sp.getString("username", ""));
			editText2.setText(sp.getString("password", ""));
			status = sp.getInt("status", 0);
			if (sp.getBoolean("IsAuto", false)) {
				if (status==1) {
					Intent intent = new Intent(Login.this, Student.class);
					intent.putExtra("name", editText1.getText().toString());
					intent.putExtra("password", editText2.getText().toString());
					startActivity(intent);
					Login.this.finish();
				} else if (status==2) {
					Intent intent = new Intent(Login.this, Teacher.class);
					intent.putExtra("name", editText1.getText().toString());
					System.out.println(name + "45454545");
					intent.putExtra("password", editText2.getText().toString());
					startActivity(intent);
					Login.this.finish();
				} else if (status==3) {
					Intent intent = new Intent(Login.this, Guanliyuan.class);
					intent.putExtra("name", editText1.getText().toString());
					System.out.println(name + "45454545");
					intent.putExtra("password", editText2.getText().toString());
					startActivity(intent);
					Login.this.finish();
				}

			}
		}
		// //记住密码
		// rem_pwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// // TODO Auto-generated method stub
		// if(rem_pwd.isChecked()){
		// System.out.println("记住密码已经选中");
		// sp.edit().putBoolean("IsCheck", true).commit();
		// }else{
		// System.out.println("记住密码没有选中");
		// sp.edit().putBoolean("IsCheck", false).commit();
		// }
		// }
		// });
		// //自动登录事件
		// auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// // TODO Auto-generated method stub
		// if(auto_login.isChecked()){
		// System.out.println("自动登录已经选中");
		// sp.edit().putBoolean("IsAuto", true).commit();
		// }else{
		// System.out.println("自动登录没有选中");
		// sp.edit().putBoolean("IsAuto", false).commit();
		// }
		// }
		// });
	}

	// 注册按钮监听事件
	public void zhuce(View v) {
		startActivity(new Intent(Login.this, Regeister.class));
	}

	// 登录按钮监听事件
	public void denglu(View v) {
		final ProgressDialog dialog = new ProgressDialog(Login.this);
		// 记录账户
		sp.edit().putBoolean("IsCheck", true).commit();
		sp.edit().putBoolean("IsAuto", true).commit();
		// 判断信息是否为空
		String name = editText1.getText().toString();
		String pass = editText2.getText().toString();
		if (name.isEmpty() || pass.isEmpty()) {
			Toast.makeText(Login.this, "用户名或密码为空", 0).show();
		} else if (!radioButton1.isChecked() && !radioButton2.isChecked()
				&& !radioButton3.isChecked()) {
			Toast.makeText(Login.this, "请选择您的身份", 0).show();
		} else if (radioButton1.isChecked()) {// 学生身份登录
			// 密码验证
			Editor editor = sp.edit();
			editor.putString("username", name);
			editor.putString("password", pass);
			editor.putInt("status", 1);
			editor.commit();
			sp.edit().putBoolean("IsAuto", true).commit();
			String url = "http://www.huanglinqing.com/yidongjiajiao/xueshengdenglu/";
			new dengluserver(name, pass, url, handler).start();
			dialog.setTitle("正在登录...");
			dialog.show();
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					try {
//						Thread.sleep(2000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					dialog.dismiss();
//				}
//			}).start();
		} else if (radioButton2.isChecked()) {// 教师身份登录
			// 首先判断 教师信息是否通过审核
			Editor editor = sp.edit();
			editor.putString("username", name);
			editor.putString("password", pass);
			editor.putInt("status", 2);
			editor.commit();
			sp.edit().putBoolean("IsAuto", true).commit();
			String url1 = "http://www.huanglinqing.com/yidongjiajiao/jiaoshishenhe/";
			new shenheserver(name, url1, handler2).start();
			dialog.setTitle("正在登录...");
			dialog.show();
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					try {
//						Thread.sleep(2000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					dialog.dismiss();
//				}
//			}).start();

		} else {// 管理员登录
			// 密码验证
			Editor editor = sp.edit();
			editor.putString("username", name);
			editor.putString("password", pass);
			editor.putInt("status", 3);
			editor.commit();
			sp.edit().putBoolean("IsAuto", true).commit();
			String url = "http://www.huanglinqing.com/yidongjiajiao/guanliyuan/";
			new dengluserver(name, pass, url, handler4).start();
			dialog.setTitle("正在登录...");
			dialog.show();
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					try {
//						Thread.sleep(2000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					dialog.dismiss();
//				}
//	 		}).start();
		}

	}

	// 学生验证密码线程机制
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String json = (String) msg.obj;
			if (json.indexOf("登陆成功") > 0) {
				Intent intent = new Intent(Login.this, Student.class);
				intent.putExtra("name", editText1.getText().toString());
				intent.putExtra("password", editText2.getText().toString());

				// File fAccount = new File(accountFilePath);
				// // if (!fAccount.exists()) {//不存在创建
				// try {
				// fAccount.createNewFile();
				// PrintWriter pw = new PrintWriter(fAccount);
				// String accountInfo = editText1.getText().toString() + "\n"
				// + editText2.getText().toString() + "\n"
				// + String.valueOf(userType);
				//
				// pw.write(accountInfo);
				// Toast.makeText(Login.this, accountInfo, 0).show();
				// pw.close();
				//
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// // }

				Toast.makeText(Login.this, "登录成功", 0).show();

				startActivity(intent);
				Login.this.finish();
				// Login.this.finish();
			} else if (json.indexOf("密码错误") > 0) {
				Toast.makeText(Login.this, "密码错误", 0).show();
			} else if (json.indexOf("服务器繁忙") > 0) {
				Toast.makeText(Login.this, "服务器繁忙，请稍后重试", 0).show();
			} else {
				Toast.makeText(Login.this, "用户名不存在", 0).show();
			}
		};
	};
	// 教师验证密码线程机制
	private Handler handler3 = new Handler() {
		public void handleMessage(Message msg) {
			String json = (String) msg.obj;
			if (json.indexOf("登陆成功") > 0) {
				Intent intent = new Intent(Login.this, Teacher.class);
				intent.putExtra("name", editText1.getText().toString());
				System.out.println(name + "45454545");
				intent.putExtra("password", editText2.getText().toString());
				startActivity(intent);
				Login.this.finish();
			} else if (json.indexOf("密码错误") > 0) {
				Toast.makeText(Login.this, "密码错误", 0).show();
			} else if (json.indexOf("服务器繁忙") > 0) {
				Toast.makeText(Login.this, "服务器繁忙，请稍后重试", 0).show();
			} else {
				Toast.makeText(Login.this, "用户名不存在", 0).show();
			}
		};
	};
	// 管理员
	private Handler handler4 = new Handler() {
		public void handleMessage(Message msg) {
			String json = (String) msg.obj;
			if (json.indexOf("登陆成功") > 0) {
				Intent intent = new Intent(Login.this, Guanliyuan.class);
				intent.putExtra("name", editText1.getText().toString());
				System.out.println(name + "45454545");
				intent.putExtra("password", editText2.getText().toString());
				startActivity(intent);
				Login.this.finish();
			} else if (json.indexOf("密码错误") > 0) {
				Toast.makeText(Login.this, "密码错误", 0).show();
			} else if (json.indexOf("服务器繁忙") > 0) {
				Toast.makeText(Login.this, "服务器繁忙，请稍后重试", 0).show();
			} else {
				Toast.makeText(Login.this, "用户名不存在", 0).show();

			}
		};
	};
	// 教师信息审核判断
	private Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			String json = (String) msg.obj;
			String name = editText1.getText().toString();
			String pass = editText2.getText().toString();
			if (json.indexOf("yes") > 0) {
				String url = "http://www.huanglinqing.com/yidongjiajiao/jiaoshidenglu/";
				new dengluserver(name, pass, url, handler3).start();

			} else if (json.indexOf("no") > 0) {
				Toast.makeText(Login.this, "对不起,您的信息暂时未通过审核", 0).show();
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
					finish();
				} else {
					Toast.makeText(Login.this, "再按一次返回键退出", Toast.LENGTH_SHORT)
							.show();
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
