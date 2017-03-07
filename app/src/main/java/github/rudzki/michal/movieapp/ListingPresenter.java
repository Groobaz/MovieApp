package github.rudzki.michal.movieapp;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import nucleus.presenter.Presenter;

public class ListingPresenter extends Presenter<ListingActivity>{

    public void getDataAsync(String title){
        new Thread(){
            @Override
            public void run(){
                try{
                    String result = getData(title);
                    SearchResults searchResults = new Gson().fromJson(result, SearchResults.class);
                    getView().setDataOnUiThread(searchResults);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public String getData(String title) throws IOException {
        String stringUrl = "http://www.omdbapi.com/?s=" + title;
        URL url = new URL(stringUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        return convertStreamToString(inputStream);
    }

    private String convertStreamToString(InputStream inputStream) {
        java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
