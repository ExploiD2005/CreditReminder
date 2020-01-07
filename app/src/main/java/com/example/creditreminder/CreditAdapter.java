package com.example.creditreminder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.CreditHolder> {
    private List<Credit> credits = new ArrayList<>();

    @NonNull
    @Override
    public CreditHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.credit_item, parent, false);
        return new CreditHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditHolder holder, int position) {
        Credit currentCredit = credits.get(position);
        holder.textViewTitle.setText(currentCredit.getTitle());
        holder.textViewLastPayDay.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentCredit.getLast_pay_date()));
        holder.textViewFullAmountOfPayment.setText(String.valueOf(currentCredit.getFull_amount_of_payment()));
        if (currentCredit.getMin_amount_of_payment() > 0) {
            holder.textViewMinAmountOfPayment.setText(String.valueOf(currentCredit.getMin_amount_of_payment()));
        } else {
            holder.textViewMinAmountOfPayment.setVisibility(View.GONE);
            holder.textViewMinAmmount.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return credits.size();
    }

    public void setCredit(List<Credit> credits) {
        this.credits = credits;
        notifyDataSetChanged();
    }

    class CreditHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewLastPayDay;
        private TextView textViewFullAmountOfPayment;
        private TextView textViewMinAmountOfPayment;
        private TextView textViewMinAmmount;

        public CreditHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textview_title);
            textViewLastPayDay = itemView.findViewById(R.id.textview_last_pay_date);
            textViewFullAmountOfPayment = itemView.findViewById(R.id.textview_full_amount_of_payment);
            textViewMinAmountOfPayment = itemView.findViewById(R.id.textview_min_amount_of_payment);
            textViewMinAmmount = itemView.findViewById(R.id.textview_min_ammount);
        }
    }
}