package io.github.ifariskh.donationsystem.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.core.EndUser;
import io.github.ifariskh.donationsystem.core.RequestHandler;
import io.github.ifariskh.donationsystem.helper.Constant;

public class KYCActivity extends AppCompatActivity implements View.OnClickListener {

    // Define globals variable
    private Button uploadFront, confirm;
    private TextInputLayout name, id, dob, salary, socialStatus;
    private ImageView front;
    private Bitmap bitmap, tempBitmap;
    private RadioGroup radioGroup;
    private String gender = "Male";
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc);

        uploadFront = findViewById(R.id.upload_front);
        confirm = findViewById(R.id.confirm);
        front = findViewById(R.id.front_image);
        name = findViewById(R.id.full_name);
        id = findViewById(R.id.id);
        dob = findViewById(R.id.dob);
        salary = findViewById(R.id.salary);
        socialStatus = findViewById(R.id.ss);
        radioGroup = findViewById(R.id.radio_group);

        uploadFront.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upload_front:
                startCrop();
                break;
            case R.id.confirm:
                String nameText = name.getEditText().getText().toString().trim();
                String idText = id.getEditText().getText().toString().trim();
                String dobText = dob.getEditText().getText().toString().trim();
                if (!(nameText.isEmpty() && idText.isEmpty() && dobText.isEmpty())) {
                    StringRequest stringRequest = new StringRequest(
                            Request.Method.POST,
                            Constant.KYC,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jObj = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                        String isValid = jObj.getString("valid");
                                        if (isValid.equals("true")) {
                                            Toast.makeText(getApplicationContext(), "KYC in process", Toast.LENGTH_LONG).show();
                                            upload();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("SignIn", "Response: " + error.toString());
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("id", EndUser.ID);
                            map.put("salary", salary.getEditText().getText().toString().trim());
                            map.put("socialStatus", socialStatus.getEditText().getText().toString().trim());
                            map.put("gender", gender);
                            return map;
                        }
                    };

                    RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
                }
        }
    }


    private void upload() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        tempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String ImageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "onResponse: " + response);
                        try {
                            JSONObject jObj = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            String isValid = jObj.getString("valid");
                            if (isValid.equals("true")) {
                                Toast.makeText(getApplicationContext(), "Information uploaded", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SignIn", "Response: " + error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", name.getEditText().getText().toString().trim());
                map.put("id", EndUser.ID);
                map.put("dob", dob.getEditText().getText().toString().trim());
                map.put("image", ImageString);
                return map;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

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
                    tempBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                    front.setImageBitmap(bitmap);
                    getData();
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
                                        name.getEditText().setText(result.trim());
                                    } else if (result.contains("No:")) {
                                        id.getEditText().setText(result.substring(3).trim());
                                    } else if (result.contains("DOB:") || result.contains("DO8")) {
                                        dob.getEditText().setText(result.substring(5).trim());
                                        break;
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("TAG", "onFailure: failed to process image");
                                    }
                                });
    }

    public void getGender(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        gender = radioButton.getText().toString();
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