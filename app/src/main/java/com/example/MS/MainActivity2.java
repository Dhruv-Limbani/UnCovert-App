package com.example.MS;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

//for sensors
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

public class MainActivity2 extends AppCompatActivity implements SensorEventListener{

    private TextView tv_heartRate;
    private TextView tv_heartBeat;
    private TextView tv_acc;
    private TextView tv_gyro;

    Button Stop;
    SensorEvent event;
    private static final String TAG = "____Main___";
    SensorManager sensorManager;

    private Sensor hrSensor;
    private Sensor accSensor;
    private Sensor gyroSensor;
    private Sensor hbSensor;

    private Boolean ishrSensorAvailable;
    private Boolean isaccSensorAvailable;
    private Boolean isgyroSensorAvailable;
    private Boolean ishbSensorAvailable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String gender = bundle.getString("gender");
            String age = bundle.getString("age");
            TextView Gender = findViewById(R.id.gen2);
            TextView Age = findViewById(R.id.age2);
            Gender.setText(gender);
            Age.setText(age);
            tv_heartRate = findViewById(R.id.hr);
            tv_heartBeat = findViewById(R.id.hb);
            tv_acc = findViewById(R.id.acc);
            tv_gyro = findViewById(R.id.gyro);
            Stop = findViewById(R.id.stop);
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            //checkPermission();
            checkSensorAvailability();
            // stop Button  --> to exit the app
            Stop.setOnClickListener(new View.OnClickListener() {  // function to exit the app
                @Override
                public void onClick(View view) {
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            });
        }
    }

    private void checkSensorAvailability() {

        if ((sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)) != null) { //Step 7: Check for particular sensor, if return value then the sensor is accessible

            hrSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
            tv_heartRate.setTextColor(Color.parseColor("#32cd32"));
            ishrSensorAvailable = true;
            //text color of textView
        } else { //Step 6: If return null, then sensor is not accessible and entered into else method.
            tv_heartRate.setText("Heart Rate Sensor is Inaccessible");
            tv_heartRate.setTextColor(Color.parseColor("#FF0000"));
            ishrSensorAvailable = false;
        }



        if ((sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)) != null) { //Step 7: Check for particular sensor, if return value then the sensor is accessible

            accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            tv_acc.setTextColor(Color.parseColor("#32cd32"));
            isaccSensorAvailable = true;
            //text color of textView
        } else { //Step 6: If return null, then sensor is not accessible and entered into else method.
            tv_acc.setText("Accelerometer Sensor is Inaccessible");
            tv_acc.setTextColor(Color.parseColor("#FF0000"));
            isaccSensorAvailable = false;
        }



        if ((sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)) != null) { //Step 7: Check for particular sensor, if return value then the sensor is accessible

            gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            tv_gyro.setTextColor(Color.parseColor("#32cd32"));
            isgyroSensorAvailable = true;
            //text color of textView
        } else { //Step 6: If return null, then sensor is not accessible and entered into else method.
            tv_gyro.setText("Gyroscope Sensor is Inaccessible");
            tv_gyro.setTextColor(Color.parseColor("#FF0000"));
            isgyroSensorAvailable = false;
        }


        if ((sensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT)) != null) {

            hbSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT);
            tv_heartBeat.setTextColor(Color.parseColor("#32cd32"));
            ishbSensorAvailable = true;
        } else {
            tv_heartBeat.setText("Heart Beat sensor is Inaccessible");
            tv_heartBeat.setTextColor(Color.parseColor("#FF0000"));
            ishbSensorAvailable = false;
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_HEART_RATE)
        {
            tv_heartRate.setText("Current Heart rate is " + event.values[0] + "bpm");
        }
        else if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            tv_acc.setText("Current Acceleration is " + event.values[0]);
        }
        else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
        {
            tv_gyro.setText("Gyroscope sensor reading is " + event.values[0]);
        }
        else if(event.sensor.getType() == Sensor.TYPE_HEART_BEAT)
        {
            tv_heartBeat.setText("Heartbeat sensor reading is " + event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ishrSensorAvailable)
        {
            sensorManager.registerListener(this, hrSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(isaccSensorAvailable)
        {
            sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(isgyroSensorAvailable)
        {
            sensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(ishbSensorAvailable)
        {
            sensorManager.registerListener(this, hbSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(ishrSensorAvailable)
        {
            sensorManager.unregisterListener(this);
        }
        if(isaccSensorAvailable)
        {
            sensorManager.unregisterListener(this);
        }
        if(isgyroSensorAvailable)
        {
            sensorManager.unregisterListener(this);
        }
        if(ishbSensorAvailable)
        {
            sensorManager.unregisterListener(this);
        }
    }
    /*private void checkPermission() { // step 3 started (according to content detail)

        // Runtime permission ------------
        if (checkSelfPermission(Manifest.permission.BODY_SENSORS) // check runtime permission for BODY_SENSORS
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.BODY_SENSORS}, 1); // If BODY_SENSORS permission has not been taken before then ask for the permission with popup
        } else {
            Log.d(TAG, "ALREADY GRANTED"); //if BODY_SENSORS is allowed for this app then print this line in log.
        }
    }*/
}