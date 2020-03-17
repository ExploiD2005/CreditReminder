package com.example.creditreminder;

import  androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AddEditCreditFragment extends AppCompatDialogFragment  implements DatePickerFragment.onDateSelect {
    private int id = -1;
    private EditText editTextTitle, editTextLastPayDate, editTextFullAmountOfPayment,
            editTextMinAmountOfPayment;
    private TextView tvTitleFragment;
    private CalendarView calendarView;
    private Date last_pay_date = new Date();
    private Credit openCredit,  credit = new Credit();
    private boolean isNewCredit = true;
    private AddEditCreditListener addEditCreditListener;
    DatePickerFragment datePickerFragment = new DatePickerFragment();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_edit_credit_fragment, null);
        builder.setView(view)
               // .setTitle("Новый платеж")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (id != -1) {
                            credit.setId(id);
                        }
                        if (editTextTitle.getText().length() != 0) {
                            credit.setTitle(editTextTitle.getText().toString().trim());
                        }  else {
                            int random = new Random().nextInt(9999) + 1;
                            credit.setTitle("Без названия " + random);
                        }
                        if (editTextFullAmountOfPayment.getText().length() != 0) {
                            credit.setFull_amount_of_payment(Double.parseDouble(editTextFullAmountOfPayment.getText().toString().trim()));
                            //    product.setProtein(protein);
                        }  else {
                            credit.setFull_amount_of_payment(0.0);
                        }
                        if (editTextMinAmountOfPayment.getText().length() != 0) {
                            credit.setMin_amount_of_payment(Double.parseDouble(editTextMinAmountOfPayment.getText().toString().trim()));
                        }  else {
                            credit.setMin_amount_of_payment(0.0);
                        }
                        credit.setLast_pay_date(last_pay_date);

                        addEditCreditListener.saveCredit(credit);
                        //newCredit.setPayment_settlement_date(payment_settlement_day);

                        //data.putExtra(Credit.class.getSimpleName(), newCredit );
                        //setResult(RESULT_OK, data);
                        //finish();
                    }
                });

        tvTitleFragment = view.findViewById(R.id.tvTitleFragment);
        tvTitleFragment.setText("Новая запись");
        editTextTitle = view.findViewById(R.id.edit_text_title);

        editTextLastPayDate = view.findViewById(R.id.edit_text_last_pay_date);
        editTextLastPayDate.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(last_pay_date));
        editTextLastPayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFragment.show(getFragmentManager(), "LastPayDayDatePicker");
                datePickerFragment.setTargetFragment(AddEditCreditFragment.this, 1);
                datePickerFragment.openDatePicker(last_pay_date);
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
        editTextFullAmountOfPayment = view.findViewById(R.id.edit_text_full_amount_of_payment);
        editTextMinAmountOfPayment = view.findViewById(R.id.edit_text_min_amount_of_payment);
        //calendarView = view.findViewById(R.id.calendarView);
        if (!isNewCredit) {
            id = openCredit.getId();
            tvTitleFragment.setText("Редактирование записи");
            editTextTitle.setText(openCredit.getTitle());
            last_pay_date = openCredit.getLast_pay_date();
            String lastPayDay = DateFormat.getDateInstance(DateFormat.MEDIUM).format(last_pay_date);
            editTextLastPayDate.setText(lastPayDay);
            editTextFullAmountOfPayment.setText(String.valueOf(openCredit.getFull_amount_of_payment()));
            editTextMinAmountOfPayment.setText(String.valueOf(openCredit.getMin_amount_of_payment()));
            isNewCredit = true;
        }
        return builder.create();
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_credit_fragment);


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
    }*/

    /*@Override
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
    }*/

    /*private void saveCredit() {
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
    }*/

    /*@Override
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
            case R.id.delete_credit:
                deleteCredit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void deleteCredit() {
    }*/

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            addEditCreditListener = (AddEditCreditListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement AddEditCreditListener");
        }
    }

    public void openCredit(Credit newCredit) {
        openCredit = newCredit;
        isNewCredit = false;
    }

    public void setDate(Date date) {
        last_pay_date = date;
        String lastPayDay = DateFormat.getDateInstance(DateFormat.MEDIUM).format(last_pay_date);
        editTextLastPayDate.setText(lastPayDay);

        //isNewCredit = false;
    }

    @Override
    public void getDate(Date date) {
        last_pay_date = date;
        String lastPayDay = DateFormat.getDateInstance(DateFormat.MEDIUM).format(last_pay_date);
        editTextLastPayDate.setText(lastPayDay);
    }

    public interface AddEditCreditListener {
        void saveCredit(Credit newCredit);
        //void openCredit(Credit newCredit);
    }
}
