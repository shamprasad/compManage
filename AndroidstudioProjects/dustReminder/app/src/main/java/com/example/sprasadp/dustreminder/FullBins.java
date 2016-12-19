package com.example.sprasadp.dustreminder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


import java.nio.charset.MalformedInputException;


public class FullBins extends ActionBarActivity {

    BluetoothDevice mmDevice;
    BluetoothSocket mmSocket;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    boolean stopWorker = true;
    Thread workerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_bins);
        final TextView mainTV = (TextView)findViewById(R.id.message_String_id);
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        new Thread(){
            public void run() {
                if (mBluetoothAdapter != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            mainTV.setText("Adapter found.");
                        }
                    });

                }
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!mBluetoothAdapter.isEnabled()) {
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent, 0);

                }
                while (!mBluetoothAdapter.isEnabled());
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice device : pairedDevices) {
                        if (device.getName().equals("MTKBTDEVICE")) {
                            mmDevice = device;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    mainTV.setText("BT found.");
                                }
                            });
                            break;
                        }
                    }
                }
                if (mmDevice == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            mainTV.setText("BT device not found.");
                        }
                    });
                } else {
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                    try {
                        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
                        mmSocket.connect();
                        mmOutputStream = mmSocket.getOutputStream();
                        mmInputStream = mmSocket.getInputStream();
                        final byte delimiter = 10;
                        workerThread = new Thread(new Runnable()
                        {
                            public void run()
                            {
                                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                                {
                                    try
                                    {
                                        int bytesAvailable = mmInputStream.available();
                                        if(bytesAvailable > 0)
                                        {
                                            byte[] packetBytes = new byte[bytesAvailable];
                                            mmInputStream.read(packetBytes);
                                            for(int i=0;i<bytesAvailable;i++)
                                            {
                                                byte b = packetBytes[i];
                                                if(b == delimiter)
                                                {
                                                    byte[] encodedBytes = new byte[readBufferPosition];
                                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                                    final String data = new String(encodedBytes, "US-ASCII");
                                                    readBufferPosition = 0;

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            mainTV.setText(data);
                                                        }
                                                    });
                                                }
                                                else
                                                {
                                                    readBuffer[readBufferPosition++] = b;
                                                }
                                            }
                                        }
                                    }
                                    catch (IOException ex)
                                    {
                                        stopWorker = true;
                                    }
                                }
                            }
                        });

                        workerThread.start();


                        mmOutputStream.close();
                        mmInputStream.close();
                        mmSocket.close();


                    } catch (IOException i) {
                        i.printStackTrace();
                    }
                }
            }
        }.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_full_bins, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
