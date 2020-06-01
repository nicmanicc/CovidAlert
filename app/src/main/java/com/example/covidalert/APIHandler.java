package com.example.covidalert;

import java.io.IOException;
import java.lang.ref.WeakReference;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIHandler {

    private OkHttpClient mClient;

    //CallBack interface that will be used to send the response to Statistic Activity
    public interface CallBack
    {
        //Executed if response was successful
        void onSuccess(String response);
    }
    //Class constructor. Creates an OkHTTP client.
    public APIHandler() {
        mClient = new OkHttpClient();
    }
    /* Overview: Creates a request and sends it to StatisticActivity to
        handle. */
    public void requestReponse(StatisticActivity activity) {
        //Weak reference to a StatisticActivity
        final WeakReference<StatisticActivity> statisticActivityWeakReference =
                new WeakReference<StatisticActivity>(activity);
        //URL to request from
        Request request = new Request.Builder()
                .url("https://api.covid19api.com/summary")
                .build();
        //Execute request and handle response
        mClient.newCall(request).enqueue(new Callback() {
            //Print stack trace if the request fails
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            //Send response to StatisticActivity to handle
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Check if response was successful
                if (response.isSuccessful()) {
                    //Turn response into string
                    String myResponse = response.body().string();
                    //Access weak reference
                    StatisticActivity activity = statisticActivityWeakReference.get();
                    //Make sure reference isn't null
                    if (activity != null)
                    {
                        //Send response to activity to be handled
                        activity.onSuccess(myResponse);
                    }

                }
            }
        });
    }
}
