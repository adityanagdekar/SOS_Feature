package com.example.forestsos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {
    TextView tvSos;
    FloatingActionButton fabSos;
    String FILE_NAME="Local_Contact";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSos = findViewById(R.id.tvSos);
        fabSos = findViewById(R.id.fabSos);

        final File file = new File(String.valueOf(getApplicationContext().getFilesDir()),FILE_NAME);

        fabSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = bufferedReader.readLine();
                    while (line != null){
                        stringBuilder.append(line).append("\n");
                        line = bufferedReader.readLine();
                    }
                    if (line.length()==0){
                        tvSos.setText("You have not Registered your Contact Number for SOS Feature");
                    }
                    bufferedReader.close();
                }
                catch (Exception e){
                    e.getStackTrace();
                }
            }
        });

    }
}