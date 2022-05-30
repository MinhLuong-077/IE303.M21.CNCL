package com.example.ewallet.api;

import com.example.ewallet.model.BankAcount;
import com.example.ewallet.model.PutData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

//http:192.168.211.1//:8080/stock.com/api/v1/bank-account/918591724612/BIDV
public interface ApiBank {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiBank apibank= new Retrofit.Builder()
            .baseUrl("http://172.21.176.1:8080/stock.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiBank.class);
    @GET("bank-account/{account}/{bank}")
    Call<BankAcount>callApiBank(@Path("account") String accountId,@Path("bank") String bank);
//    @GET("sms")
//    @POST("bank-account/:account/:bank/:userid/update/money")
//    Call<BankAcount> updateUser(@Part("photo") RequestBody photo, @Part("description") RequestBody description)
//    ;

    @PUT("bank-account/{account}/{bank}/{userid}/update/money")
    Call<PutData> updateUser(@Path("account") String accountId,@Path("bank") String bank ,@Path("userid") String userid, @Body PutData putData);

//    @POST("users/transaction/create")
//    Call<>
}
