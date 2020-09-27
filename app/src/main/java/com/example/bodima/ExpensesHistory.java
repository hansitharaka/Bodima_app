package com.example.bodima;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bodima.Model.ExpenseAdapter;
import com.example.bodima.Model.ExpenseData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ExpensesHistory extends AppCompatActivity {


//    private List<ExpenseData> expenseData;
//    private RecyclerView rv;
//    private ExpenseAdapter adapter;

    List<ExpenseData> expenseData;
    RecyclerView recyclerView;
    ExpenseAdapter adapter;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    ExpenseData ed;


    TextView AmountView;
    TextView TypeView;
    TextView DateView;
    //
    ImageButton deleteBtn;
    String clubkey;
    String Amount;
    String Type_;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_history);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Intent intent = getIntent();

        Type_ = intent.getStringExtra("typepass");


//        deleteBtn =findViewById(R.id.btnDel);


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        AmountView = findViewById(R.id.amount_ex_text);
//        TypeView = findViewById(R.id.Type1);
//        DateView = findViewById(R.id.date_expense);
//        get from previous intent
//        AmountView.setText(intent.getStringExtra("AmountEnter"));
//        TypeView.setText(intent.getStringExtra("Type_in"));
//        DateView.setText(intent.getStringExtra("DateAndTime"));
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }



// Running Code
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 121:
                expenseData = new ArrayList<>();

                if (Type_.equalsIgnoreCase("Revanue")){
                    databaseReference = FirebaseDatabase.getInstance().getReference("ExpenseManeger").child("Revanue");
                }
                else {
                    databaseReference = FirebaseDatabase.getInstance().getReference("ExpenseManeger").child("Expenses");
                }


                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ExpenseData data = ds.getValue(ExpenseData.class);

                            clubkey = ds.getKey();


//                            Double val= .;
//                            System.out.println(val);

//                            databaseReference.child(clubkey).removeValue();
//                            System.out.println("llllllll"+clubkey);

//                            LoaddatafromdatabaseRevanue();

                        }
                        if(clubkey.isEmpty()){
                            Toast.makeText(ExpensesHistory.this, "Empty ", Toast.LENGTH_SHORT).show();
                            LoaddatafromdatabaseRevanue();

                        }else {
                            databaseReference.child(clubkey).removeValue();
                            LoaddatafromdatabaseRevanue();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                return true;

//            case 122:
//                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onStart() {
        super.onStart();

        if (Type_.equalsIgnoreCase("Revanue")) {
            LoaddatafromdatabaseRevanue();
        }
        if (Type_.equalsIgnoreCase("Expense")) {
            Context context = getApplicationContext();
            CharSequence message = "Loading...";
            int duration = Toast.LENGTH_SHORT; //How long the toastmessage will lasts
            Toast toast = Toast.makeText(context, message, duration);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 100);
            toast.show();


            LoaddatafromdatabaseExpenses();
        } else {
            Context context = getApplicationContext();
            CharSequence message = "Loading...";
            int duration = Toast.LENGTH_SHORT; //How long the toastmessage will lasts
            Toast toast = Toast.makeText(context, message, duration);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 100);
            toast.show();

        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }

//    public  void  deleteAll(){
//        deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                databaseReference = FirebaseDatabase.getInstance().getReference("ExpenseManeger").child("Expenses");
//                id =databaseReference.push().getKey();
//               databaseReference.child(id).removeValue();
//            }
//        });
//    }


    //load data from firebase
    public void LoaddatafromdatabaseExpenses() {

        setContentView(R.layout.activity_expenses_history);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        expenseData = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("ExpenseManeger").child("Expenses");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ExpenseData data = ds.getValue(ExpenseData.class);
                    expenseData.add(data);
                    adapter = new ExpenseAdapter(expenseData);
                    //////////////////////////////////////////////////////
                    id = ds.getKey();
                    System.out.println("nnnnnnnnnnnnnnnn"+id);

                    ////////////////////////////////////////////////////////
                    recyclerView.setAdapter(adapter);

                }

                System.out.println(id);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void LoaddatafromdatabaseRevanue() {

        setContentView(R.layout.activity_expenses_history);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        expenseData = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("ExpenseManeger").child("Revanue");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ExpenseData data = ds.getValue(ExpenseData.class);
                    expenseData.add(data);
                    adapter = new ExpenseAdapter(expenseData);

                    recyclerView.setAdapter(adapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}