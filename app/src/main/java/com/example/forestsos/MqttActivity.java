package com.example.forestsos;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

import static com.example.forestsos.MainActivity.latitude;
import static com.example.forestsos.MainActivity.longitude;

public class MqttActivity extends Thread {
    public static final String TAG = "MqttActivity";

    public final static String ipAddress = "192.168.0.104";

    private final String protocol = "tcp";
    private final String port = "1883";
    private final String url = protocol + "://" + ipAddress + ":" + port;

    private final String clientId = MqttClient.generateClientId();
    private Context context;

    // hunter detection alerts will be published on this topic
    private final String local_alert__topic = "forest/local";

    private static MqttAndroidClient client;
    private static IMqttToken token;

    private String payload=latitude+"/"+longitude;

    public MqttActivity(Context context) {
        this.context = context;
    }

    public void run () {
        Log.d(TAG, "inside run");
//        Log.d(TAG, token.getClient().toString());
//        Log.d(TAG, payload);
        client = new MqttAndroidClient(context, url, clientId);
        try {
            token = client.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        token.setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                // We are connected
                Log.d(TAG, "inside success");
                byte[] encodedPayload = new byte[0];
                MqttMessage message;
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                try {
                    payload+=payload+"/"+timestamp.toString();
                    encodedPayload = payload.getBytes("UTF-8");
                    message = new MqttMessage(encodedPayload);
                    client.publish(local_alert__topic, message);
                    System.out.println("message published");
                } catch (NullPointerException | UnsupportedEncodingException | MqttException e) {
                    System.out.println("error occured");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                System.out.println("Inside failure");
                Log.d(TAG, String.valueOf(exception));
            }
        });

    }

}
