package io.github.ifariskh.donationsystem.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

import io.github.ifariskh.donationsystem.R;

public class KYCActivity extends AppCompatActivity implements View.OnClickListener {

    private Button uploadFront, uploadBack;
    private ImageView front, back;
    private Bitmap bitmap;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc);

        uploadFront = findViewById(R.id.upload_front);
        uploadBack = findViewById(R.id.upload_back);
        front = findViewById(R.id.front_image);
        back = findViewById(R.id.back_image);

        uploadFront.setOnClickListener(this);
        uploadBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upload_front:
                flag = true;
                startCrop();
                break;
            case R.id.upload_back:
                flag = false;
                startCrop();
                break;
        }
    }

    private void startCrop() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                    if (flag) {
                        front.setImageBitmap(bitmap);
                    } else {
                        back.setImageBitmap(bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}