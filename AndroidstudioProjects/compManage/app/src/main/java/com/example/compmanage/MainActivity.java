package com.example.compmanage;


import android.support.v7.app.ActionBarActivity;
import android.content.DialogInterface.OnClickListener;
import android.content.MutableContextWrapper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.net.URL;
import java.net.MalformedURLException;
import android.util.Log;
public class MainActivity extends ActionBarActivity {

	public static final int LOCK_SCREEN = 100;
	public static final int SHUT_DOWN = 200;
	public static final int REBOOT = 210;
	public static final int GET_PC_CLIPBOARD = 300;
	public static final int SET_PC_CLIPBOARD = 310;
	String laptopIp = "Searching laptop...";
	int commPort = 6675;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = (Button)findViewById(R.id.button1);
        final Button shutbutton = (Button)findViewById(R.id.button2);
        final Button rebootButton = (Button)findViewById(R.id.button3);
        final EditText et = (EditText)findViewById(R.id.editText2);
    	et.setText(laptopIp);
    	new Thread(){
    		public void run(){
    			try {
    			URL laptopUrl = new URL("http://10.104.255.237/laptopIp/laptopIp");
    			BufferedReader in = new BufferedReader(new InputStreamReader(laptopUrl.openStream())); 
    			laptopIp = in.readLine();
    			String [] laptopIpOctets = laptopIp.split("\\.");
    			laptopIp = laptopIpOctets[3] + "." + laptopIpOctets[2] + "." + laptopIpOctets[1] + "." + laptopIpOctets[0];
    			runOnUiThread(new Runnable()
			    {            
			        @Override
			        public void run()
			        {
			        	
			        	et.setText(laptopIp);
			        }
			    });
    			
    			in.close();
    			
    			}
    			catch (MalformedURLException e1)
    			{
    				e1.printStackTrace();
    			}
    			catch (IOException e2)
    			{
    				e2.printStackTrace();
    			}
    		}
    		
    	}.start();
    	
        button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new Thread()
				{
					public void run() {
				String Message = String.valueOf(LOCK_SCREEN);
				 
				
				
				DatagramChannel androidChannel = null;
				DatagramSocket androidSocket = null;
				try{
					androidChannel = DatagramChannel.open();
				androidSocket = androidChannel.socket();
				}
				catch(SocketException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				InetAddress PCAddress = null;
				try 
				{
				laptopIp = et.getText().toString();
				PCAddress = InetAddress.getByName(laptopIp);
				}
				catch(UnknownHostException h1)
				{
					Log.i("unknown", "host");
					h1.printStackTrace();
				}
				int messageLength = Message.length();
				byte [] byteMessage = Message.getBytes();
				DatagramPacket androidPacket = new DatagramPacket(byteMessage, messageLength, PCAddress, commPort);
			try
			{
				androidSocket.send(androidPacket);
				Log.i(WIFI_SERVICE, PCAddress.toString());
				 runOnUiThread(new Runnable()
				    {            
				        @Override
				        public void run()
				        {
				        	
				        	TextView tv = (TextView)findViewById(R.id.textView1);
							tv.setText("Locked");      
				        }
				    });
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			//androidSocket.close();
					}
				}.start();
				
				
			}
		});
        shutbutton.setOnClickListener(new View.OnClickListener() {
			
   			@Override
   			public void onClick(View v) {
   				
   				new Thread()
   				{
   					public void run() {
   				String Message = String.valueOf(SHUT_DOWN);
   				 
   				
   				
   				DatagramChannel androidChannel = null;
   				DatagramSocket androidSocket = null;
   				try{
   					androidChannel = DatagramChannel.open();
   				androidSocket = androidChannel.socket();
   				}
   				catch(SocketException e)
   				{
   					e.printStackTrace();
   				}
   				catch (IOException e)
   				{
   					e.printStackTrace();
   				}
   				InetAddress PCAddress = null;
   				try 
   				{
   					laptopIp = et.getText().toString();
   				PCAddress = InetAddress.getByName(laptopIp);
   				}
   				catch(UnknownHostException h1)
   				{
   					Log.i("unknown", "host");
   					h1.printStackTrace();
   				}
   				int messageLength = Message.length();
   				byte [] byteMessage = Message.getBytes();
   				DatagramPacket androidPacket = new DatagramPacket(byteMessage, messageLength, PCAddress, commPort);
   			try
   			{
   				androidSocket.send(androidPacket);
   				Log.i(WIFI_SERVICE, PCAddress.toString());
   				 runOnUiThread(new Runnable()
   				    {            
   				        @Override
   				        public void run()
   				        {
   				        	
   				        	TextView tv = (TextView)findViewById(R.id.textView1);
   							tv.setText("Shut down");      
   				        }
   				    });
   			}
   			catch(IOException e)
   			{
   				e.printStackTrace();
   			}
   			//androidSocket.close();
   					}
   				}.start();
   				
   				
   			}
   		});
	rebootButton.setOnClickListener(new View.OnClickListener() {
			
   			@Override
   			public void onClick(View v) {
   				
   				new Thread()
   				{
   					public void run() {
   				String Message = String.valueOf(REBOOT);
   				 
   				
   				
   				DatagramChannel androidChannel = null;
   				DatagramSocket androidSocket = null;
   				try{
   					androidChannel = DatagramChannel.open();
   				androidSocket = androidChannel.socket();
   				}
   				catch(SocketException e)
   				{
   					e.printStackTrace();
   				}
   				catch (IOException e)
   				{
   					e.printStackTrace();
   				}
   				InetAddress PCAddress = null;
   				try 
   				{
   					laptopIp = et.getText().toString();
   				PCAddress = InetAddress.getByName(laptopIp);
   				}
   				catch(UnknownHostException h1)
   				{
   					Log.i("unknown", "host");
   					h1.printStackTrace();
   				}
   				int messageLength = Message.length();
   				byte [] byteMessage = Message.getBytes();
   				DatagramPacket androidPacket = new DatagramPacket(byteMessage, messageLength, PCAddress, commPort);
   			try
   			{
   				androidSocket.send(androidPacket);
   				Log.i(WIFI_SERVICE, PCAddress.toString());
   				 runOnUiThread(new Runnable()
   				    {            
   				        @Override
   				        public void run()
   				        {
   				        	
   				        	TextView tv = (TextView)findViewById(R.id.textView1);
   							tv.setText("Rebooting");      
   				        }
   				    });
   			}
   			catch(IOException e)
   			{
   				e.printStackTrace();
   			}
   			//androidSocket.close();
   					}
   				}.start();
   				
   				
   			}
   		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
