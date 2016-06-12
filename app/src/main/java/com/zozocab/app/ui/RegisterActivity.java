package com.zozocab.app.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.zozocab.app.Adapter.ApiService;
import com.zozocab.app.R;
import com.zozocab.app.model.MyProfile;
import com.zozocab.app.model.admin.Admin;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, mobile, email, referal_Code, lname;
    private MyProfile newProfile;
    private Button button;
    private ImageButton changeimage;
    private ImageView userprofileimage;
    String imgString;
    SharedPreferences sharedPreferences;
    private String FIRST_NAME_KEY = "firstname";
    private String LAST_NAME_KEY="lastname";
    private String IMAGE_KEY = "image";
    private String CONTACT_KEY = "contact";
    private String EMAIL_KEY = "email";
    private String ID_KEY = "id";
    private String REGISTERATION_KEY = "registered";
    private String PREFERENCE_NAME = "zozocab_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences  = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);

        newProfile = new MyProfile();
        init();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://104.197.214.216:3000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       /* ApiService service = retrofit.create(ApiService.class);
        final Call<MyProfile> myProfileCall = service.getMyProfile("007");*/
        final com.zozocab.app.model.Location userloc = new com.zozocab.app.model.Location();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

                final String tmDevice, tmSerial, androidId;
                tmDevice = "" + tm.getDeviceId();
                tmSerial = "" + tm.getSimSerialNumber();
                androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

                UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
                final String id = deviceUuid.toString();

                newProfile.setFirstName(name.getText().toString());
                newProfile.setLastName(lname.getText().toString());
                newProfile.setEmail(email.getText().toString());
                newProfile.setUuid(id);
                newProfile.setMobileVerified(true);
                newProfile.setLocationId(mobile.getText().toString());
                newProfile.setPicture(getimage());
                newProfile.setMobilenumber(mobile.getText().toString());
                //TODO
                userloc.setLatitude(""+LoadingScreen.latitude);
                userloc.setLongitude(""+LoadingScreen.longitude);
                userloc.setMobile(mobile.getText().toString());
                newProfile.setLocation(userloc);

                final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                ApiService service = retrofit.create(ApiService.class);
                final Call<MyProfile> myProfileCall = service.createProfile(newProfile);
                myProfileCall.enqueue(new Callback<MyProfile>() {
                    @Override
                    public void onResponse(Call<MyProfile> call, Response<MyProfile> response) {
                        if(response.isSuccessful()) {
                            progressDialog.cancel();
                            Toast.makeText(RegisterActivity.this, "Thank You For Registeration!!", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(FIRST_NAME_KEY, name.getText().toString());
                            editor.putString(LAST_NAME_KEY, lname.getText().toString());
                            editor.putString(EMAIL_KEY, email.getText().toString());
                            editor.putString(IMAGE_KEY, imgString);
                            editor.putString(ID_KEY, id);
                            editor.putBoolean(REGISTERATION_KEY,true);
                            editor.putString(CONTACT_KEY, mobile.getText().toString());
                            editor.commit();

                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this, "Something Went Wrong! Try again afer sometime", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<MyProfile> call, Throwable t) {
                        progressDialog.cancel();
                        Toast.makeText(RegisterActivity.this, "Something went wrong, try again later", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        changeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private String getimage() {

        userprofileimage.buildDrawingCache();
        Bitmap bmap = userprofileimage.getDrawingCache();
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
                imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

            }
        });

        return imgString;
    }

    private void init() {
        name = (EditText) findViewById(R.id.name);
        mobile = (EditText) findViewById(R.id.mobile);
        email = (EditText) findViewById(R.id.email);
        referal_Code = (EditText) findViewById(R.id.referal_code);
        button = (Button) findViewById(R.id.register);
        lname = (EditText) findViewById(R.id.last_name);
        userprofileimage = (ImageView) findViewById(R.id.userimage);
        changeimage = (ImageButton) findViewById(R.id.changeuserimage);

    }


}
