package com.zozocab.app.Adapter;

import com.zozocab.app.model.DriverModel;
import com.zozocab.app.model.MyProfile;
import com.zozocab.app.model.admin.Admin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ashwani on 4/10/2016.
 */
public interface ApiService {
//
//    @GET("Users/{username}")
//    Call<Profile> getUser(@Path("id") String username, @Query("filter") String filter);
//
//    @GET("group/{id}/users")
//    Call<List<Profile>> groupList(@Path("id") int groupId, @Query("sort") String sort);
//
//    @POST("Users")
//    Call<Profile> createUser(@Body Profile user);
//
//    @GET("Users")
//    Call<Profile> createUser(@Body Profile user, @Query("id") String string);

    @GET("my_profiles/{id}")
    Call<MyProfile> getMyProfile(@Path("id") String id);

    @Headers("Accept: application/json")
    @POST("my_profiles")
    Call<MyProfile> createProfile(@Body MyProfile myProfile);

    @Headers("Accept: application/json")
    @PUT("my_profiles/{id}")
    Call<MyProfile> updateProfile(@Path("id") String id, @Body MyProfile updateProfile);

    @Headers("Accept: application/json")
    @GET("drivers")
    Call<List<DriverModel>> getDrivers();



}
