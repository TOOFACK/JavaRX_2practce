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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = findViewById(R.id.getCom);

        getObservable(start)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Comments>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Comments> comments) {
                        RecyclerView comRec = findViewById(R.id.CommentsRecycle);
                        comRec.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                        comRec.setAdapter(new CommentsAdapter(comments));

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        }



   public static Observable<List<Comments>> getObservable(final Button button){
        return Observable.create(new ObservableOnSubscribe<List<Comments>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Comments>> emitter) throws Exception {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JsonPareserHolder jsonPareserHolder = new JsonPareserHolder("https://jsonplaceholder.typicode.com/comments");

                        try {
                            ArrayList<Comments> comments = jsonPareserHolder.getUserComments();
                            emitter.onNext(comments);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
   }


}
