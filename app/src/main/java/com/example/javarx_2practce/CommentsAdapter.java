package com.example.javarx_2practce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.javarx_2practce.eneties.Comments;

import org.w3c.dom.Comment;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsVH> {

    List<Comments> CommentsList;

    public CommentsAdapter(List<Comments> CommentsList) {
        this.CommentsList = CommentsList;
    }


    @NonNull
    @Override
    public CommentsVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(R.layout.nice_comment, viewGroup, false);

        return new CommentsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsVH commentsVH, int i) {
        final Comments comments = CommentsList.get(i);

        CommentsVH.CommentV.setText(comments.getComment());

    }

    @Override
    public int getItemCount() {
        return CommentsList.size();
    }




    public static class CommentsVH extends RecyclerView.ViewHolder  {

        private static TextView CommentV;

        public CommentsVH(@NonNull View itemView) {

            super(itemView);

            CommentV = itemView.findViewById(R.id.Comment);


        }
    }
}
