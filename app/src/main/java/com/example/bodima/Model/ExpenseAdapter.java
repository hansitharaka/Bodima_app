package com.example.bodima.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bodima.R;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolderClass> {


    private List<ExpenseData> listdata;


    public ExpenseAdapter(List<ExpenseData> listdata) {
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ex_data_card,parent,false);
        ViewHolderClass viewHolderClass =new ViewHolderClass(view);

        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {

        ViewHolderClass viewHolderClass =(ViewHolderClass)holder;

        ExpenseData fetchdata =listdata.get(position);

        viewHolderClass.amount_ex_text1.setText(fetchdata.getAmount());
        viewHolderClass.date_expense1.setText(fetchdata.getDate());
        viewHolderClass.Type123.setText(fetchdata.getType());





    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {

//        TextView amount_ex_text;
//        TextView Type;
//        TextView date_expense;

         TextView amount_ex_text1 , Type123 ,date_expense1;


        public ViewHolderClass(View itemView) {

            super(itemView);
            amount_ex_text1 =(TextView) itemView.findViewById(R.id.amount_ex_text);
            Type123 =(TextView) itemView.findViewById(R.id.Type1);
            date_expense1 = (TextView) itemView.findViewById(R.id.date_expense);
        }
    }


}
