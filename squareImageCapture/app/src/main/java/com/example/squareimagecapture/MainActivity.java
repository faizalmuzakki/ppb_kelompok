package com.example.squareimagecapture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String nmFile;
    private static final int MY_PERMISSIONS_REQUEST_WRITE=223;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CameraView cameraView = (CameraView) findViewById(R.id.cameraSquareView);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.captureContainer);
        final Button captureButton = (Button) findViewById(R.id.captureButton);
        String appName = getString(R.string.app_name);
//        final File directory = new File(Environment.getExternalStorageDirectory().getPath() + "/" + appName);

        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] jpeg) {
                final File fileName = new File(System.currentTimeMillis() + "_image.jpg");
//                CameraUtils.decodeBitmap(jpeg, new CameraUtils.BitmapCallback() {
//                    @Override
//                    public void onBitmapReady(Bitmap bitmap) {
//                        return;
//                    }
//                });
                super.onPictureTaken(jpeg);
            }
        });
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraView.capturePicture();
//                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                File imagesFolder = new File(Environment.getExternalStorageDirectory(),"/HasilTest");
//                if (!imagesFolder.exists()) {
//                    imagesFolder.mkdirs();
//                }
//
//                Date d = new Date();
//                CharSequence s= DateFormat.format("yyyyMMdd-hh-mm-ss",d.getTime());
//                nmFile=imagesFolder + File.separator+ s.toString()+".jpg";
//                File image=new File(nmFile);
            }
        });
    }
    @Override
    public void onResume() {
        CameraView cameraView = (CameraView) findViewById(R.id.cameraSquareView);
        super.onResume();
        cameraView.start();
    }

    private void showProgressDialog() {

    }

    @Override
    public void onPause() {
        CameraView cameraView = (CameraView) findViewById(R.id.cameraSquareView);
        super.onPause();
        cameraView.stop();
    }
}
