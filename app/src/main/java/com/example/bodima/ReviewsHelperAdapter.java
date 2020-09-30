package com.example.bodima;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bodima.Model.Reviews;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

public class ReviewsHelperAdapter extends RecyclerView.Adapter {

    List<Reviews> reviewsList;

//    private int count = getItemCount();
//    private int sum = (int) getRatingSum();
//
//    private int getRatingSum(DataSnapshot dataSnapshot) {
//        for (DataSnapshot ds: dataSnapshot.getChildren()){
//            sum=sum+
//        }
//    }

    public ReviewsHelperAdapter(List<Reviews> reviewsList) {
        this.reviewsList = reviewsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_layout,parent,false);

        viewHolderClass viewHolderClass = new viewHolderClass(view);

        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        viewHolderClass viewHolderClass = (viewHolderClass)holder;
        Reviews ratingsAndReviews = reviewsList.get(position);
        Reviews fetchData = reviewsList.get(position);

        viewHolderClass.rate.setRating(fetchData.getRating());
        viewHolderClass.review.setText(fetchData.getReview());
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public class viewHolderClass extends RecyclerView.ViewHolder{

        TextView review;
        RatingBar rate;

        public viewHolderClass(@NonNull View itemView) {
            super(itemView);
            review = itemView.findViewById(R.id.myreview);
            rate = itemView.findViewById(R.id.smallRatingBar);
        }
    }
}
