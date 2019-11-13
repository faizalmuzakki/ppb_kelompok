package com.kamera.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button b1;
    ImageView iv;
    private static final int kodekamera=222;
    private static final int MY_PERMISSIONS_REQUEST_WRITE=223;
    String nmFile;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        askWritePermission();
        setContentView(R.layout.activity_main);

        b1=(Button)findViewById(R.id.button);
        iv=(ImageView)findViewById(R.id.imageView);
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        b1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent it=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File imagesFolder = new File(Environment.getExternalStorageDirectory(),"/HasilTest");
                if (!imagesFolder.exists()) {
                    imagesFolder.mkdirs();
                }

                Date d=new Date();
                CharSequence s= DateFormat.format("yyyyMMdd-hh-mm-ss",d.getTime());
                nmFile=imagesFolder + File.separator+ s.toString()+".jpg";
                File image=new File(nmFile);

                Uri uriSavedImage = Uri.fromFile(image);
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                it.putExtra(MediaStore.EXTRA_OUTPUT,uriSavedImage);
                startActivityForResult(it,kodekamera);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case (kodekamera):prosesKamera(data);break;
            }
        }
    }

    private void prosesKamera(Intent datanya){
        Bitmap bm;
          BitmapFactory.Options options;
          options = new BitmapFactory.Options();
          options.inSampleSize = 2;
          bm = BitmapFactory.decodeFile(nmFile,options);
          iv.setImageBitmap(bm); // Set imageview to image that was    Toast.makeText(this,"Data Telah Terload ke ImageView" +nmFile, Toast.LENGTH_SHORT).show();
}
    private void askWritePermission() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE);}
        }
    }


}
