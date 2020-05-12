package com.example.covidalert;

import android.app.Activity;
import java.io.IOException;
import java.lang.ref.WeakReference;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIHandler {



    private OkHttpClient mClient;

    public interface CallBack
    {
        public void success(String response);
    }
    public APIHandler() {
        mClient = new OkHttpClient();
    }

    public void requestReponse(StatisticActivity activity) {
        final WeakReference<StatisticActivity> statisticActivityWeakReference =
                new WeakReference<StatisticActivity>(activity);
        Request request = new Request.Builder()
                .url("https://api.covid19api.com/summary")
                .build();
        mClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();

                    StatisticActivity activity = statisticActivityWeakReference.get();
                    if (activity != null)
                    {
                        activity.success(myResponse);
                    }
                }
            }
        });
    }
}
