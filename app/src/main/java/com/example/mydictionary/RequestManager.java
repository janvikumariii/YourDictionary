package com.example.mydictionary;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.mydictionary.Models.APIResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class RequestManager {
    Context context;
    //create object of retrofit
    Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.dictionaryapi.dev/api/v2/")
            .addConverterFactory(GsonConverterFactory.create()).
            build();
    //constructor of request manager
    public RequestManager(Context context) {
        this.context = context;
    }
    //handle calling API
    public void getWordMeaning(OnFetchDataListener listener,String word){
        CallDictionary callDictionary=retrofit.create(CallDictionary.class);//Creating object of CallDictionary interface
        Call<List<APIResponse>> call=callDictionary.callMeanings(word);

        try{
            call.enqueue(new Callback<List<APIResponse>>() {
                @Override
                public void onResponse(Call<List<APIResponse>> call, Response<List<APIResponse>> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                         return;
                    }
                    listener.onFetchData(response.body().get(0),response.message()); //response body is a list of API response and get(0) passes the firs object of APIResponse
                }

                @Override
                public void onFailure(Call<List<APIResponse>> call, Throwable t) {
                    listener.onError("Request Failed!!");
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "An error occured", Toast.LENGTH_SHORT).show();
        }
    } //Listener which will capture response in the Main Activity and the word which we are searching for


    //interface of API calling
    public interface CallDictionary{
        //API calling method, rest part of the url (end point)
        @GET("entries/en/{word}")//{word}->word to be searched and entered by the user
        //use call function defined by retrofit to call the API and whole response is an array that is why use list
        Call<List<APIResponse>> callMeanings(
                @Path("word")String word//{word} is defined here
        );
    }
}
