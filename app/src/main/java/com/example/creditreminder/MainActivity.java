package com.example.creditreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CreditViewModel creditViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
