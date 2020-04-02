package com.example.androidfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "my_file";
    EditText etContentFile;
    TextView tvFilePath;
    Button btnCreateFile, btnDeleteFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        String path = getFilesDir().getAbsolutePath();
        tvFilePath.setText(path);

        this.btnCreateFile.setOnClickListener(this::createFile);
    }

    private void createFile(View view) {

        FileOutputStream fileOutputStream;
        String data = etContentFile.getText().toString();
        try {
            fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.flush();
            tvFilePath.setText("Written Completed");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView() {
         etContentFile = findViewById(R.id.file_content);
         tvFilePath = findViewById(R.id.file_path);

         btnCreateFile = findViewById(R.id.create_file);
         btnDeleteFile = findViewById(R.id.delete_file);
    }
}
