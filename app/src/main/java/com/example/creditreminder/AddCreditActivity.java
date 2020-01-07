package com.example.creditreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddCreditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String EXTRA_TITLE = "com.example.creditreminder.EXTRA_TITLE";
    public static final Date EXTRA_LAST_PAY_DAY = new Date();
    public static final String EXTRA_FULL_AMMOUNT_OF_PAYMENT = "com.example.creditreminder.EXTRA_FULL_AMMOUNT_OF_PAYMENT";
    public static final String EXTRA_MIN_AMMOUNT_OF_PAYMENY = "com.example.creditreminder.EXTRA_MIN_AMMOUNT_OF_PAYMENY";
    private EditText editTextTitle;
    private EditText editTextLastPayDate;
    private EditText editTextPaymentSettlementDay;
    private EditText editTextFullAmmountOfPayment;
    private EditText editTextMinAmmountOfPayment;

    private Date last_pay_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextLastPayDate = findViewById(R.id.edit_text_last_pay_date);
        editTextLastPayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        editTextPaymentSettlementDay = findViewById(R.id.edit_text_Payment_settlement_date);
        editTextFullAmmountOfPayment = findViewById(R.id.edit_text_full_ammount_of_payment);
        editTextMinAmmountOfPayment = findViewById(R.id.edit_text_min_ammount_of_payment);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Добавить платеж");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        last_pay_date = c.getTime();
        String lastPayDay = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        editTextLastPayDate.setText(lastPayDay);
    }

    private void saveCredit() {
        String title = editTextTitle.getText().toString();
        //Date last_pay_date = Date.from(editTextLastPayDate.getText().toString().trim());
        //Date payment_settlement_day = editTextPaymentSettlementDay.getText().toString();
        Double full_ammount_of_payment = Double.parseDouble(editTextFullAmmountOfPayment.getText().toString().trim());
        Double min_ammount_of_payment = Double.parseDouble(editTextMinAmmountOfPayment.getText().toString().trim());
        if (title.trim().isEmpty() ) {
            Toast.makeText(this, "Введите заголовок", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        Credit newCredit = new Credit();
        newCredit.setTitle(title);
        newCredit.setLast_pay_date(last_pay_date);
        newCredit.setFull_amount_of_payment(full_ammount_of_payment);
        newCredit.setMin_amount_of_payment(min_ammount_of_payment);
        data.putExtra(Credit.class.getSimpleName(), newCredit );
        setResult(RESULT_OK, data);
        finish();
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
