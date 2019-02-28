package com.example.javarx_2practce;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.javarx_2practce.eneties.Comments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class JsonPareserHolder {

    private String urlPath;
    private int id= 0;
    private String comment;

    public JsonPareserHolder(String urlPath)  {
        this.urlPath = urlPath;
    }

//    public Comments getUser(JSONObject jsonObject) throws JSONException {
//        JSONObject userRoot =jsonObject;
//
//        int userId = userRoot.getInt("id");
//        String userComent = userRoot.getString("body");
//
//        return new Comments(userComent, userId);
//    }

    public Single<ArrayList<Comments>> getUserComments(final Button button)throws IOException, JSONException {//Здесь данные получаешь и отправляешь из в observer, a eto observable
        return Single.create(new SingleOnSubscribe<ArrayList<Comments>>() {
            @Override
            public void subscribe(SingleEmitter<ArrayList<Comments>> emitter) throws Exception {
                String userJsonStrike = getJsonFromServer(urlPath,10000);
                final JSONArray array = new JSONArray(userJsonStrike);
                final ArrayList<Comments> userArrayList = new ArrayList<>();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JSONObject curobj;

                        for (int i = 0; i < array.length(); i++) {

                            try {
                                curobj = array.getJSONObject(i);
                                id = curobj.getInt("id");
                                comment = curobj.getString("body");
                                Comments comments = new Comments(comment, id);
                                userArrayList.add(comments);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                });
                emitter.onSuccess(userArrayList);
            }

        });

    }


    private String getJsonFromServer(String urlPath, int timeout) throws IOException {
        URL url = new URL(urlPath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        connection.connect();

        int serverResponseCode = connection.getResponseCode();
        switch (serverResponseCode) {
            case 200:
            case 201:
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String tmpLine;
                while ((tmpLine = br.readLine()) != null) {
                    sb.append(tmpLine).append("\n");
                }
                br.close();
                return sb.toString();
            case 404:
                Log.d(JsonPareserHolder.class.getName(), "page not found!");
                break;
            case 400:
                Log.d(JsonPareserHolder.class.getName(), "Bad request!");
                break;
            case 500:
                Log.d(JsonPareserHolder.class.getName(), "Internal server error");
                break;
        }

        return null;
    }
}
