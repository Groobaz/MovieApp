package github.rudzki.michal.movieapp;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieApplication extends Application implements RetrofitProvider {
    private Retrofit retrofit;

    @Override
    public void onCreate(){
        super.onCreate();
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://omdbapi.com")
                .build();
    }

    @Override
    public Retrofit provideRetrofit() {
        return retrofit;
    }
}
