package com.example.MS;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.content.pm.PackageManager;
import android.util.Log;

import com.example.MS.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    private static final String TAG = "____Main___";
    private RadioGroup Gender;
    private RadioButton sGen;
    private EditText Age;
    private Button Start;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //mTextView = binding.text;
        Gender = binding.gender;
        Age = binding.age;
        Start = binding.start;
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Gender.getCheckedRadioButtonId();
                sGen = (RadioButton) findViewById(id);
                String gen = sGen.getText().toString().trim();
                String age = Age.getText().toString().trim();
                //Toast.makeText(MainActivity.this, gen + " " + age, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("gender",gen);
                bundle.putString("age",age);
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    private void checkPermission() { // step 3 started (according to content detail)

        // Runtime permission ------------
        if (checkSelfPermission(Manifest.permission.BODY_SENSORS) // check runtime permission for BODY_SENSORS
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.BODY_SENSORS}, 1); // If BODY_SENSORS permission has not been taken before then ask for the permission with popup
        } else {
            Log.d(TAG, "ALREADY GRANTED"); //if BODY_SENSORS is allowed for this app then print this line in log.
        }
    }
}