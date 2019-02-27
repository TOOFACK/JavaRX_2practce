package com.example.javarx_2practce;

import android.util.Log;

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

public class JsonPareserHolder {

    private String urlPath;

    public JsonPareserHolder(String urlPath)  {
        this.urlPath = urlPath;
    }

    public Comments getUser(JSONObject jsonObject) throws JSONException {
        JSONObject userRoot =jsonObject;

        int userId = userRoot.getInt("id");
        String userComent = userRoot.getString("body");

        return new Comments(userComent, userId);
    }

    public SingleArrayList<Comments> getUserComments()throws IOException, JSONException{//Здесь данные получаешь и отправляешь из в observer, a eto observable

        String userJsonStroke = getJsonFromServer(urlPath, 100000);

        JSONArray array = new JSONArray(userJsonStroke);

        ArrayList<Comments> userArrayList = new ArrayList<>();

        for(int i = 0; i<array.length(); i++){
            JSONObject curobj = array.getJSONObject(i);
            Log.d(Comments.class.getName(),curobj.toString());
            Comments newUser = getUser(curobj);
            userArrayList.add(newUser);
        }
        Log.d("CheckJson", String.valueOf(userArrayList.get(1)));
        return  userArrayList;
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
