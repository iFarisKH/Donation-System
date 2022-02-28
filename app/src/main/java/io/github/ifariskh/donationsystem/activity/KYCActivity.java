package io.github.ifariskh.donationsystem.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

import io.github.ifariskh.donationsystem.R;

public class KYCActivity extends AppCompatActivity implements View.OnClickListener {

    private Button uploadFront, uploadBack;
    private TextInputLayout name, id, dob;
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
        name = findViewById(R.id.full_name);
        id = findViewById(R.id.id);
        dob = findViewById(R.id.dob);

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
                        getData();
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

    private void getData() {
        Mat source = new Mat();
        Utils.bitmapToMat(bitmap, source);

        Mat gray = new Mat();
        Imgproc.cvtColor(source, gray, Imgproc.COLOR_RGB2GRAY);

        Mat medianBlur = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size((2 * 2) + 1, (2 * 2) + 1));
        Imgproc.dilate(gray, medianBlur, kernel);
        Imgproc.medianBlur(medianBlur, medianBlur, 21);

        Mat absdiff = new Mat();
        Core.absdiff(gray, medianBlur, absdiff);

        Mat normalize = new Mat();
        Core.normalize(absdiff, normalize, 0, 255, Core.NORM_MINMAX, CvType.CV_8UC1);

//        Mat thresh = new Mat();
//        Imgproc.threshold(normalize, thresh, 127, 255, Imgproc.THRESH_OTSU);
        Bitmap bitmap = Bitmap.createBitmap(source.width(), source.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(normalize, bitmap);

        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        InputImage image = InputImage.fromBitmap(bitmap, 0);

        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text visionText) {
                                String result;
                                for (Text.TextBlock block : visionText.getTextBlocks()) {
                                    result = block.getText();
                                    if (result.contains(",") && Character.isUpperCase(result.charAt(0))) {
                                        name.getEditText().setText(result);
                                        Log.d("TAG", "onSuccess: " + result);
                                    } else if (result.contains("No:")) {
                                        id.getEditText().setText(result.substring(3));
                                        Log.d("TAG", "onSuccess: " + result);
                                    } else if (result.contains("DOB:") || result.contains("DO8")) {
                                        dob.getEditText().setText(result.substring(5));
                                        Log.d("TAG", "onSuccess: " + result);
                                        break;
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
    }

    private BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i("TAG", "OpenCV loaded successfully");
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d("TAG", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, baseLoaderCallback);
        } else {
            Log.d("TAG", "OpenCV library found inside package. Using it!");
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }


}