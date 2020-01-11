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
import java.util.Random;

public class AddEditCreditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private int id = -1;
    private EditText editTextTitle;
    private EditText editTextLastPayDate;
    //private EditText editTextPaymentSettlementDay;
    private EditText editTextFullAmmountOfPayment;
    private EditText editTextMinAmmountOfPayment;

    private Date last_pay_date = new Date();
    //private Date payment_settlement_day = new Date();

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
                datePicker.show(getSupportFragmentManager(), "LastPayDayDatePicker");
            }
        });
        /*editTextPaymentSettlementDay = findViewById(R.id.edit_text_Payment_settlement_date);
        editTextPaymentSettlementDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker2 = new DatePickerFragment();
                datePicker2.show(getSupportFragmentManager(), "PaymentSettlementDayDatePicker");
            }
        });*/
        editTextFullAmmountOfPayment = findViewById(R.id.edit_text_full_ammount_of_payment);
        editTextMinAmmountOfPayment = findViewById(R.id.edit_text_min_ammount_of_payment);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra("AddEditId")) {
            setTitle("Радактировать платеж");
            Credit credit = (Credit) intent.getSerializableExtra(Credit.class.getSimpleName());
            id = credit.getId();
            editTextTitle.setText(credit.getTitle());
            last_pay_date = credit.getLast_pay_date();
            String lastPayDay = DateFormat.getDateInstance(DateFormat.MEDIUM).format(last_pay_date);
            editTextLastPayDate.setText(lastPayDay);
            editTextFullAmmountOfPayment.setText(String.valueOf(credit.getFull_amount_of_payment()));
            editTextMinAmmountOfPayment.setText(String.valueOf(credit.getMin_amount_of_payment()));
        } else {
            setTitle("Добавить платеж");
            String lastPayDay = DateFormat.getDateInstance(DateFormat.MEDIUM).format(last_pay_date);
            editTextLastPayDate.setText(lastPayDay);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        //Object tag = view.getTag();
        //if ("LastPayDayDatePicker".equals(tag)) {
            last_pay_date = c.getTime();
            String lastPayDay = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
            editTextLastPayDate.setText(lastPayDay);
        //} else if ("PaymentSettlementDayDatePicker".equals(tag)) {
        //    payment_settlement_day = c.getTime();
        //    String paymentSettlementDay = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        //    editTextPaymentSettlementDay.setText(paymentSettlementDay);
        //}
    }

    private void saveCredit() {
        //String title = editTextTitle.getText().toString().trim();
        Intent data = new Intent();
        Credit newCredit = new Credit();
        //Date last_pay_date = Date.from(editTextLastPayDate.getText().toString().trim());
        //Date payment_settlement_day = editTextPaymentSettlementDay.getText().toString();
        if (id != -1) {
            newCredit.setId(id);
        }
        if (editTextTitle.getText().length() != 0) {
            newCredit.setTitle(editTextTitle.getText().toString().trim());
        }  else {
            int random = new Random().nextInt(9999) + 1;
            newCredit.setTitle("Без названия " + random);
        }
        if (editTextFullAmmountOfPayment.getText().length() != 0) {
            newCredit.setFull_amount_of_payment(Double.parseDouble(editTextFullAmmountOfPayment.getText().toString().trim()));
        //    product.setProtein(protein);
        }  else {
            newCredit.setFull_amount_of_payment(0.0);
        }
        if (editTextMinAmmountOfPayment.getText().length() != 0) {
            newCredit.setMin_amount_of_payment(Double.parseDouble(editTextMinAmmountOfPayment.getText().toString().trim()));
        }  else {
            newCredit.setMin_amount_of_payment(0.0);
        }
        newCredit.setLast_pay_date(last_pay_date);
        //newCredit.setPayment_settlement_date(payment_settlement_day);

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
