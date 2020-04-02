package com.example.androidfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "my_file";
    private static final String IMAGE = "image";
    EditText etContentFile;
    TextView tvFilePath;
    Button btnCreateFile, btnDeleteFile, btnCreateImageFile, btnDeleteImageFile, btnReadFile, btnReadImage, btnFileList;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        String path = getFilesDir().getAbsolutePath();
        tvFilePath.setText(path);

        this.btnCreateFile.setOnClickListener(this::createFile);
        this.btnCreateImageFile.setOnClickListener(this::createImageFile);
        this.btnReadFile.setOnClickListener(this::readFile);
        this.btnReadImage.setOnClickListener(this::readImage);
        this.btnDeleteFile.setOnClickListener(this::deleteFiles);
        this.btnFileList.setOnClickListener(this::fileLists);
    }

    private void fileLists(View view) {
        String[] fileList = fileList();
        for (String files: fileList) {
            tvFilePath.append(files+"\n");
        }
    }

    private void deleteFiles(View view) {
        boolean deleteFile = deleteFile(FILE_NAME);
        Toast.makeText(this, "File is Deleted"+deleteFile, Toast.LENGTH_LONG).show();
    }

    private void readImage(View view) {
        InputStream inputStream = null;
        Bitmap image = null;
        try {
            inputStream = openFileInput(IMAGE);
            image = BitmapFactory.decodeStream(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(image);
    }

    private void readFile(View view) {
        InputStream inputStream = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            inputStream = openFileInput(FILE_NAME);
            int read;
            while ((read = inputStream.read()) != -1){
                stringBuilder.append((char)read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (inputStream != null){
                    inputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        tvFilePath.setText(stringBuilder.toString());
    }

    private void createImageFile(View view) {

        Bitmap data = getImage();

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(IMAGE, MODE_PRIVATE);
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
        btnReadFile = findViewById(R.id.btn_read_file);
        btnReadImage = findViewById(R.id.btn_read_image);
        imageView = findViewById(R.id.imageView);
        btnFileList = findViewById(R.id.btn_file_list);
    }
}
