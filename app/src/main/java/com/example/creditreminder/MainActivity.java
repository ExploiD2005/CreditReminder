package com.example.creditreminder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_CREDIT_REQUEST = 1;
    private CreditViewModel creditViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddCredit = findViewById(R.id.button_add_credit);
        buttonAddCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCreditActivity.class);
                startActivityForResult(intent, ADD_CREDIT_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CreditAdapter adapter = new CreditAdapter();
        recyclerView.setAdapter(adapter);

        creditViewModel = ViewModelProviders.of(this).get(CreditViewModel.class);
        //creditViewModel = ViewModelProviders.of(this).get(CreditViewModel.class);
        creditViewModel.getAllCredits().observe(this, new Observer<List<Credit>>() {
            @Override
            public void onChanged(List<Credit> credits) {
                adapter.setCredit(credits);
                //Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_CREDIT_REQUEST && resultCode == RESULT_OK) {
            Credit newCredit = (Credit) data.getSerializableExtra(Credit.class.getSimpleName());
            creditViewModel.insert(newCredit);
            Toast.makeText(MainActivity.this, "Запись добавлена", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Запись не добавлена", Toast.LENGTH_SHORT).show();
        }
    }
}
