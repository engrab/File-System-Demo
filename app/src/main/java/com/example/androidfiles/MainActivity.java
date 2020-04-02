package com.example.androidfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "my_file";
    private static final String FILE = "image";
    EditText etContentFile;
    TextView tvFilePath;
    Button btnCreateFile, btnDeleteFile, btnCreateImageFile, btnDeleteImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        String path = getFilesDir().getAbsolutePath();
        tvFilePath.setText(path);

        this.btnCreateFile.setOnClickListener(this::createFile);
        this.btnCreateImageFile.setOnClickListener(this::createImageFile);
    }

    private void createImageFile(View view) {

        Bitmap data = getImage();

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(FILE, MODE_PRIVATE);
            data.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);

            tvFilePath.setText("Image Written");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Bitmap getImage() {

        Bitmap bitmap = null;
        try {
            InputStream inputStream;
            inputStream = getAssets().open("carton.png");
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void createFile(View view) {

        FileOutputStream fileOutputStream = null;
        String data = etContentFile.getText().toString();
        try {
            fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.flush();
            tvFilePath.setText("Written Completed");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void initView() {
        etContentFile = findViewById(R.id.file_content);
        tvFilePath = findViewById(R.id.file_path);

        btnCreateFile = findViewById(R.id.create_file);
        btnDeleteFile = findViewById(R.id.delete_file);
        btnCreateImageFile = findViewById(R.id.btn_create_image_file);
        btnDeleteImageFile = findViewById(R.id.btn_delete_image_file);
    }
}
