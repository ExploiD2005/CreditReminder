package com.example.creditreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Date;

public class AddCreditActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextLastPayDate;
    private EditText editTextPaymentSettlementDay;
    private EditText editTextFullAmmountOfPayment;
    private EditText editTextMinAmmountOfPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextLastPayDate = findViewById(R.id.edit_text_last_pay_date);
        editTextPaymentSettlementDay = findViewById(R.id.edit_text_Payment_settlement_date);
        editTextFullAmmountOfPayment = findViewById(R.id.edit_text_full_ammount_of_payment);
        editTextMinAmmountOfPayment = findViewById(R.id.edit_text_min_ammount_of_payment);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Добавить платеж");
    }

    private void saveCredit() {
        String title = editTextTitle.getText().toString();
        //Date last_pay_date = Date.from(editTextLastPayDate.getText().toString().trim());
        //Date payment_settlement_day = editTextPaymentSettlementDay.getText().toString();
        Double full_ammount_of_payment = Double.parseDouble(editTextFullAmmountOfPayment.getText().toString().trim());
        Double min_ammount_of_payment = Double.parseDouble(editTextMinAmmountOfPayment.getText().toString().trim());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_credit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_credit:
                saveCredit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
