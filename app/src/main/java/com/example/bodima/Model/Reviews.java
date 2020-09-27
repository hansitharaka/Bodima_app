package com.example.bodima.Model;

public class Reviews {
    //String userId;
    private int rating;
    private String review;

    public Reviews() {
    }

    public Reviews(String userId, int rating, String review) {
        //this.userId = userId;
        this.rating = rating;
        this.review = review;
    }

//    public String getUserId() {
//        return userId;
//    }

//    public void setUserId(String userId) {
//        this.userId = userId;
//    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
