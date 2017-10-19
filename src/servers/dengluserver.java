package servers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;
import android.os.Message;

public class dengluserver extends Thread {
	private String name;
	private Handler handler;
	private String url;
	private String password;
	public dengluserver(String name,String password, String url, Handler handler) {
		this.name = name;
        this.password=password;
		this.url = url;
		this.handler = handler;
	}

	private void doget() {
		url = url + "?name="+name+"&&password="+password;
		try {
			URL httpurl = new URL(url);
			try {
				HttpURLConnection connection = (HttpURLConnection) httpurl
						.openConnection();
				connection.setRequestMethod("GET");
				connection.setReadTimeout(5000);
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
				String str;
				StringBuffer stringBuffer = new StringBuffer();
				while ((str = bufferedReader.readLine()) != null) {
					stringBuffer.append(str);
				}
				String jkkk = stringBuffer.toString();
				Message message = new Message();
				message.obj = jkkk;
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

	private void dopost() {
		try {
			URL httpurl = new URL(url);
			try {
				HttpURLConnection connection = (HttpURLConnection) httpurl
						.openConnection();
				connection.setRequestMethod("POST");
				connection.setReadTimeout(5000);
				OutputStream outputStream = connection.getOutputStream();
				String content = "name=" + name +"&&password="+password;
				outputStream.write(content.getBytes());
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
				StringBuffer stringBuffer = new StringBuffer();
				String str;
				while ((str = bufferedReader.readLine()) != null) {
					stringBuffer.append(str);
				}
				String jkkk = stringBuffer.toString();
				Message message = new Message();
				message.obj = jkkk;
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
