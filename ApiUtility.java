package com.example.weatherapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtility {
private static Retrofit retrofit=null;
    private ApiUtility() {}

public static ApiInterface getApiInterface(){
if(retrofit==null){
retrofit=new Retrofit.Builder().baseUrl(ApiInterface.Base_url).addConverterFactory(GsonConverterFactory.create()).build();

}
return retrofit.create(ApiInterface.class);
}
}
