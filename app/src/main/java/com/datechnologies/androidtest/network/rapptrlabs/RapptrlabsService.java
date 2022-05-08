package com.datechnologies.androidtest.network.rapptrlabs;

import com.datechnologies.androidtest.api.ChatLogMessageModel;
import com.datechnologies.androidtest.api.ChatLogModel;
import com.datechnologies.androidtest.network.rapptrlabs.result.SuccessBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RapptrlabsService {

    @GET("Tests/scripts/chat_log.php")
    Call<ChatLogModel> getChatLog();

    @FormUrlEncoded
    @POST("Tests/scripts/login.php")
    Call<SuccessBody> login(@Field("email") String email, @Field("password") String password);

}
