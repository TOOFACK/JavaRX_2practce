package com.example.javarx_2practce.eneties;

public class Comments {
    protected String comment;
    protected int id;
    Comments comments;


     public Comments(){

    }

    public Comments(Comments comments){
        this.comments = comments;
    }

    public Comments(String comment, int id){
        this.comment = comment;
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
