package frament;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import servers.QiNiuUpload;
import servers.teLoginServer;

import com.example.yidongjiajiao.R;
import com.example.yidongjiajiao.R.id;
import com.example.yidongjiajiao.R.layout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Framentteacher extends Fragment {
	private static int RESULT_LOAD_IMAGE = 1;
	private String n;
	private EditText editText, editText2, editText3, editText4, editText5,
			editText6, editText7;
	private String m;
	// 从服务器端获取注册返回信息
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String json = (String) msg.obj;
			if (json.indexOf("注册成功") > 0) {
				Toast.makeText(getActivity(), "注册成功,请等待管理员审核", 0).show();
			} else {
				Toast.makeText(getActivity(), "服务器繁忙，请稍后重试", 0).show();
			}

		};
	};
	// /
	// 得到用户头像的url
	Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			n = (String) msg.obj;

		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.teacherregister, null);
		ImageView imageView = (ImageView) v.findViewById(R.id.image);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			// 选择头像点击事件
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, RESULT_LOAD_IMAGE);
			}
		});
		editText = (EditText) v.findViewById(R.id.name);
		editText2 = (EditText) v.findViewById(R.id.pass1);
		editText3 = (EditText) v.findViewById(R.id.pass2);
		editText4 = (EditText) v.findViewById(R.id.phone);
		editText5 = (EditText) v.findViewById(R.id.sex);
		editText6 = (EditText) v.findViewById(R.id.age);
		editText7 = (EditText) v.findViewById(R.id.userid);
		Button button = (Button) v.findViewById(R.id.zhuce);
		button.setOnClickListener(new OnClickListener() {
			final ProgressDialog dialog = new ProgressDialog(getActivity());
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = editText.getText().toString();
				String pass1 = editText2.getText().toString();
				String pass2 = editText3.getText().toString();
				String phone = editText4.getText().toString();
				String sex = editText5.getText().toString();
				String age = editText6.getText().toString();
				String shenfen = editText7.getText().toString();
				if (name.isEmpty() || pass1.isEmpty() || pass2.isEmpty()
						|| phone.isEmpty() || sex.isEmpty()) {
					Toast.makeText(getActivity(), "您输入的信息不完整", 0).show();
				} else if (!pass1.equals(pass2)) {
					Toast.makeText(getActivity(), "两次输入的密码不一致", 0).show();
				} else if (n == null || n.equals("文件过大")) {
					Toast.makeText(getActivity(), "未选择您的头像或图片过大", 0).show();
				} else {
					
					dialog.setTitle("正在注册...");
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
					// 将用户信息存入后台数据库
					String url = "http://www.huanglinqing.com/yidongjiajiao/jiaoshizhuce/";
					new teLoginServer(name, pass1, phone, sex, n,age,shenfen, url, handler)
							.start();
				}

			}
		});

		return v;
	}

	// 选择照片返回事件
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE
				&& resultCode == getActivity().RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getActivity().getContentResolver().query(
					selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);// 获取图片路径
			ImageView imageView = (ImageView) getActivity().findViewById(
					R.id.image);

			imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			new QiNiuUpload().uploadPic(handler2, picturePath);// 上传至七牛云
		}
		if (requestCode == 3) {
			// 调用裁剪（若需要）
			if (data == null) {
				return;
			} else {
				Bundle bundle = data.getExtras();
				Bitmap bitmap = bundle.getParcelable("data");
				ImageView imageView = (ImageView) getActivity().findViewById(
						R.id.userimage);
				imageView.setImageBitmap(bitmap);

				m = bitaString(bitmap);
				m = m.replaceAll("\\s*", "");
			}
		}

	}

	// 将bitmap转化为base64字符换
	public static String bitaString(Bitmap bitmap) {
		String result = "";
		ByteArrayOutputStream bos = null;
		if (bitmap != null) {
			bos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, bos);
			try {
				bos.flush();
				bos.close();
				byte[] bit = bos.toByteArray();
				result = Base64.encodeToString(bit, Base64.DEFAULT);
				result.replace("+", "%2B");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return result;

	}

	private Uri zhuanhuan(Uri uri) throws IOException {
		try {
			InputStream is = getActivity().getContentResolver()
					.openInputStream(uri);
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			is.close();
			return save(bitmap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uri;

	}

	// 把bitamap转化为fileuri类型的uri
	private Uri save(Bitmap bitmap) throws IOException {
		File file = new File(Environment.getExternalStorageDirectory()
				+ "avater.png");
		if (!file.exists()) {
			file.mkdir();
		}
		File img = new File(file.getAbsolutePath() + "avater.png");
		FileOutputStream image = new FileOutputStream(img);
		bitmap.compress(Bitmap.CompressFormat.PNG, 80, image);
		image.flush();
		image.close();
		return Uri.fromFile(img);

	}

	// 图像裁剪
	private void caijian(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "ture");
		intent.putExtra("aspectX", 33); // 这个是裁剪时候的 裁剪框的 X 方向的比例。
		intent.putExtra("aspectY", 43); // 同上Y 方向的比例. (注意： aspectX,
		intent.putExtra("outputX", 200); // 返回数据的时候的 X 像素大小。
		intent.putExtra("outputY", 200); // 返回的时候 Y 的像素大小。
		intent.putExtra("return-data", true); // 是否要返回值。
		startActivityForResult(intent, 3);
	}
}
