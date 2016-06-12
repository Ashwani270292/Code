package com.zozocab.app.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zozocab.app.Adapter.ApiService;
import com.zozocab.app.R;
import com.zozocab.app.model.Location;
import com.zozocab.app.model.MyProfile;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserProfileActivity extends AppCompatActivity {

    LinearLayout todisplay, toedit;
    Button btnedit;
    ImageButton changeimage;
    ImageView userimage;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1000;
    private static final int SELECT_PHOTO = 1;

    SharedPreferences sharedPreferences;
    private String FIRST_NAME_KEY = "firstname";
    private String LAST_NAME_KEY="lastname";
    private String IMAGE_KEY = "image";
    private String CONTACT_KEY = "contact";
    private String EMAIL_KEY = "email";
    private String ID_KEY = "id";
    private String PREFERENCE_NAME = "zozocab_pref";


    private TextView tvusername,tvcontact,tvuserlastname;
    private EditText etusername,etcontact,etuserlastname;
    private String imagestring;

    private MyProfile updatedProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        sharedPreferences = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);

        tvusername = (TextView)findViewById(R.id.tvusername);
        tvcontact = (TextView)findViewById(R.id.tvusermobile);
        tvuserlastname = (TextView)findViewById(R.id.tvuserlastname);
        etusername = (EditText)findViewById(R.id.etusername);
        etuserlastname = (EditText)findViewById(R.id.etuserlastname);
        etcontact = (EditText)findViewById(R.id.etusermobile);
        changeimage = (ImageButton) findViewById(R.id.changeuserimage);
        todisplay = (LinearLayout) findViewById(R.id.displayinfo);
        toedit = (LinearLayout) findViewById(R.id.editinfo);
        userimage = (ImageView) findViewById(R.id.userimage);

        btnedit = (Button) findViewById(R.id.btneditdetails);
        changeimage.setVisibility(View.GONE);


        tvusername.setText(sharedPreferences.getString(FIRST_NAME_KEY,""));
        tvuserlastname.setText(sharedPreferences.getString(LAST_NAME_KEY,""));
        tvcontact.setText(sharedPreferences.getString(CONTACT_KEY,""));

        etusername.setText(sharedPreferences.getString(FIRST_NAME_KEY,""));
        etuserlastname.setText(sharedPreferences.getString(LAST_NAME_KEY,""));
        etcontact.setText(sharedPreferences.getString(CONTACT_KEY,""));

        updatedProfile = new MyProfile();

        final Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl("http://104.197.214.216:3000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        changeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chandprofilepic();
            }
        });

        final Location userloc = new Location();
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnedit.getText().toString().contains("Edit")) {
                    final SharedPreferences.Editor editor = sharedPreferences.edit();
                    if(validate()){
                        final ProgressDialog progressDialog = new ProgressDialog(UserProfileActivity.this,ProgressDialog.STYLE_SPINNER);
                        progressDialog.setMessage("Please Wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        final String fname = etusername.getText().toString();
                        final String lname = etuserlastname.getText().toString();
                        final String mobile = etcontact.getText().toString();
                        final String email = sharedPreferences.getString(EMAIL_KEY,"");
                        final String image = getimage();

                       userloc.setLatitude(""+LoadingScreen.latitude);
                        userloc.setLongitude(""+LoadingScreen.longitude);
                        userloc.setMobile(sharedPreferences.getString(CONTACT_KEY,""));

                        updatedProfile.setLocation(userloc);
                        updatedProfile.setFirstName(fname);
                        updatedProfile.setLastName(lname);
                        updatedProfile.setEmail(email);
                        updatedProfile.setPicture(image);
                        btnedit.setText("Save");

                        ApiService service = retrofit.create(ApiService.class);
                        final Call<MyProfile> profiletoupdate= service.updateProfile(sharedPreferences.getString(ID_KEY,""),updatedProfile);
                        profiletoupdate.enqueue(new Callback<MyProfile>() {
                            @Override
                            public void onResponse(Call<MyProfile> call, Response<MyProfile> response) {
                                if(response.isSuccessful()) {
                                    progressDialog.cancel();
                                    editor.putString(FIRST_NAME_KEY, fname);
                                    editor.putString(LAST_NAME_KEY, lname);
                                    editor.putString(CONTACT_KEY, mobile);
                                    editor.putString(IMAGE_KEY, getimage());
                                    toedit.setVisibility(View.VISIBLE);
                                    todisplay.setVisibility(View.GONE);
                                    changeimage.setVisibility(View.VISIBLE);
                                    raiseToast("Profile Successfully Updated!");
                                }else{
                                    raiseToast("Something Went Wrong! Try again after sometime!");
                                }
                            }

                            @Override
                            public void onFailure(Call<MyProfile> call, Throwable t) {
                                editor.putString(FIRST_NAME_KEY,etusername.getText().toString());
                                editor.putString(LAST_NAME_KEY,etuserlastname.getText().toString());
                                editor.putString(CONTACT_KEY,etcontact.getText().toString());
                                editor.putString(IMAGE_KEY,getimage());
                                toedit.setVisibility(View.VISIBLE);
                                todisplay.setVisibility(View.GONE);
                                changeimage.setVisibility(View.VISIBLE);

                            }
                        });


                    }
                } else {
                    btnedit.setText("Edit Details");

                    tvusername.setText(sharedPreferences.getString(FIRST_NAME_KEY,""));
                    tvuserlastname.setText(sharedPreferences.getString(LAST_NAME_KEY,""));
                    tvcontact.setText(sharedPreferences.getString(CONTACT_KEY,""));
                    todisplay.setVisibility(View.VISIBLE);
                    toedit.setVisibility(View.GONE);
                    changeimage.setVisibility(View.GONE);
                }
            }
        });
    }

    private boolean validate() {
        String username = etusername.getText().toString();
        String mobile = etcontact.getText().toString();

        if(username.length()==0){
            raiseToast("Please Enter A valid Username");
            return false;
        }else if(mobile.length()<10){
            raiseToast("Enter a valid mobile number");
            return false;
        }
        return true;

    }

    private void raiseToast(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void chandprofilepic() {
        if (Build.VERSION.SDK_INT >= 23) {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(UserProfileActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(UserProfileActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(UserProfileActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                    // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                ActivityCompat.requestPermissions(UserProfileActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        } else {

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        userimage.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }

    private String getimage() {

        userimage.buildDrawingCache();
        Bitmap bmap = userimage.getDrawingCache();
        String encodedImageData = getEncoded64ImageStringFromBitmap(bmap);
        return encodedImageData;
    }

    private String getEncoded64ImageStringFromBitmap(final Bitmap bmap) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                byte[] byteFormat = stream.toByteArray();
                // get the base 64 string
                imagestring = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

            }
        });

        return imagestring;
    }
}
