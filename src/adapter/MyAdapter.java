package adapter;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import servers.getUrl;

import com.bumptech.glide.Glide;
import com.example.yidongjiajiao.R;
import com.example.yidongjiajiao.R.id;
import com.example.yidongjiajiao.R.layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private List<getUrl> list;
	private int posi;
	private String name;
	private String sex;
	private String phone;
	private String touxiang;
	private Bitmap bmp;
	Context context;

	public MyAdapter(Context context, List<getUrl> list) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		posi = arg0;
		view = LayoutInflater.from(context).inflate(R.layout.xueshengxinxi,
				null);
		TextView name = (TextView) view.findViewById(R.id.name);
		TextView cursono = (TextView) view.findViewById(R.id.cursono);
		ImageView image = (ImageView) view.findViewById(R.id.imageView1);
		getUrl url = list.get(arg0);
		name.setText(url.getName());
		cursono.setText(url.getphone());
	    String picurl = url.gettouxiang();
	    int id = url.getid();
	    TextView shen=(TextView) view.findViewById(R.id.shenhe);
	    if(id==0){
	    	shen.setText("未通过");
	    }else{
	    	shen.setText("通过");
	    }
	    Glide.with(context).load(picurl).into(image);
		image.setImageBitmap(base64ToBitmap(url.gettouxiang()));
		return view;
	}

public static Bitmap base64ToBitmap(String base64Data) {
		
//		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
//		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		byte[] by = base64Data.getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(by);
		Bitmap bitmap = BitmapFactory.decodeStream(bais);
		return bitmap;
	}

}
