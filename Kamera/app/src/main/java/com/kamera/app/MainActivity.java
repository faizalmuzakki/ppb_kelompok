package com.kamera.app;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kamera.app.Retrofit.ApiClient;
import com.kamera.app.Response.ApiResponse;
import com.kamera.app.Retrofit.Server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MainActivity extends AppCompatActivity {

    Button b1, btnUpload;
    ImageView iv;
    private static final int kodekamera=222;
    private static final int MY_PERMISSIONS_REQUEST_WRITE=223;
    String nmFile;
    Context context = this;
    EditText label;
    final int PIC_CROP = 3;
    final int CAMERA_CAPTURE = 1;
    private Uri picUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        askWritePermission();
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.button);
        iv = (ImageView) findViewById(R.id.imageView);
        label = (EditText) findViewById(R.id.label);
        btnUpload = (Button) findViewById(R.id.btnUpload);

        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent it = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                it.putExtra("android.intent.extras.CAMERA_FACING", 1);
                File imagesFolder = new File(Environment.getExternalStorageDirectory(),"/HasilTest");
                if (!imagesFolder.exists()) {
                    imagesFolder.mkdirs();
                }

                Date d = new Date();
                CharSequence s= DateFormat.format("yyyyMMdd-hh-mm-ss",d.getTime());
                nmFile=imagesFolder + File.separator+ s.toString()+".jpg";
                File image=new File(nmFile);

                picUri = Uri.fromFile(image);
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                onSelectImageClick(view);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
       super.onActivityResult(requestCode, resultCode, data);

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                picUri = imageUri;
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                iv.setImageURI(result.getUri());
                Toast.makeText(this, "Cropping successful", Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void uploadImage(View v){
        String encodedImage = "";
        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        try {
            encodedImage = URLEncoder.encode(Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        encodedImage = "data:image/png;base64," + encodedImage;

        ApiClient api = Server.getClient().create(ApiClient.class);
        Call<ApiResponse> store_image = api.store_image(label.getText().toString(), encodedImage);
        store_image.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.code() == 201) {
                    Toast.makeText(MainActivity.this, "Upload berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, result_activity.class);
                    startActivity(intent);
                }
                else{
                    String.valueOf(response.code());
                    Toast.makeText(MainActivity.this, "Upload gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Upload gagal", Toast.LENGTH_SHORT).show();
            }
        });

//        iv.setImageResource(android.R.color.transparent);
//        label.setVisibility(View.GONE);
//        btnUpload.setVisibility(View.GONE);
    }

    private void prosesKamera(Intent datanya){
        Bitmap bm;
        BitmapFactory.Options options;

        options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        bm = BitmapFactory.decodeFile(nmFile,options);
        iv.setImageBitmap(bm); // Set imageview to image that was    Toast.makeText(this,"Data Telah Terload ke ImageView" +nmFile, Toast.LENGTH_SHORT).show();

        label.setVisibility(View.VISIBLE);
        btnUpload.setVisibility(View.VISIBLE);
    }

    private void askWritePermission() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE);}
        }
    }

    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (picUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(picUri);
        } else {
            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .setMultiTouchEnabled(true)
                .start(this);
    }

}
