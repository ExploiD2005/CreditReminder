package com.example.creditreminder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DatePickerFragment extends AppCompatDialogFragment {
    private CalendarView calendarView;
    private Date curentDate;
    private Long dateInMillis;
    private Calendar c = Calendar.getInstance();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.datepicker_dialog, null);

        builder.setView(view)
                .setTitle("Установите дату платежа")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().dismiss();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedDateReturn.getDate(curentDate);
                        /*if (id != -1) {
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
                        //finish();*/
                        getDialog().dismiss();
                    }
                });

        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setDate(dateInMillis);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth);
                curentDate = c.getTime();
            }
        });
        /*if (!isNewCredit) {
            id = openCredit.getId();
            editTextTitle.setText(openCredit.getTitle());
            //last_pay_date = credit.getLast_pay_date();
            //String lastPayDay = DateFormat.getDateInstance(DateFormat.MEDIUM).format(last_pay_date);
            //editTextLastPayDate.setText(lastPayDay);
            editTextFullAmountOfPayment.setText(String.valueOf(openCredit.getFull_amount_of_payment()));
            editTextMinAmountOfPayment.setText(String.valueOf(openCredit.getMin_amount_of_payment()));
            isNewCredit = true;
        }*/

        return builder.create();

        //Calendar c = Calendar.getInstance();
        //int year = c.get(Calendar.YEAR);
        //int month = c.get(Calendar.MONTH);
        //int day = c.get(Calendar.DAY_OF_MONTH);
        //return  new DatePickerDialog();
        //return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
    }

    public void openDatePicker(Date date) {
        dateInMillis = date.getTime();
        curentDate = date;

    }

    public interface onDateSelect {
        void getDate(Date date);
    }

    public onDateSelect selectedDateReturn;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            selectedDateReturn = (onDateSelect) getTargetFragment();//context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DatePickerFragment");
        }
    }
}
