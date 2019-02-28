package com.example.javarx_2practce;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.javarx_2practce.eneties.Comments;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private Button button;
    private RecyclerView recyclerView;
    static String url = "https://jsonplaceholder.typicode.com/comments";
    private JsonPareserHolder jsonPareserHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.getCom);
        recyclerView = findViewById(R.id.CommentsRecycle);
        try {
            getSingle();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void getSingle() throws IOException, JSONException{
        jsonPareserHolder = new JsonPareserHolder(url);
        jsonPareserHolder.getUserComments(button)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<Comments>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ArrayList<Comments> comments) {
                        recyclerView.setLayoutManager( new GridLayoutManager(MainActivity.this, 1));
                        recyclerView.setAdapter(new CommentsAdapter(comments));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}

