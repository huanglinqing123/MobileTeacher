package servers;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;
import android.os.Message;

public class Updateke extends Thread {
	private String kname;
	private String tname;
	private String address;
	private Handler handler;
	private String url;
	private String price;
	public Updateke(String kname, String address,String price,String tname, String url,Handler handler) {
		this.kname = kname;
		this.tname = tname;
		this.address = address;
		this.price=price;
		this.url = url;
		this.handler=handler;
	}

	private void doget() {
		url=url+"?kname="+kname+"&&address="+address+"&&price="+price+"&&tname="+tname;
         try {
			URL httpurl=new URL(url);
			try {
				HttpURLConnection connection=(HttpURLConnection) httpurl.openConnection();
				connection.setRequestMethod("GET");
				connection.setReadTimeout(5000);
				BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String str;
				StringBuffer stringBuffer=new StringBuffer();
				while((str=bufferedReader.readLine())!=null)
				{
					stringBuffer.append(str);
				}
				String jkkk=stringBuffer.toString();
				Message message=new Message();
				message.obj=jkkk;
				handler.sendMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		doget();
	}
}
