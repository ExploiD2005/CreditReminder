package com.example.creditreminder;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.CreditHolder> {
    private List<Credit> credits = new ArrayList<>();
    private OnItemClickListener listener;
    private AdapterView.OnItemLongClickListener longClickListener;

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
        String full_ammount_of_payment = String.valueOf(currentCredit.getFull_amount_of_payment());
        String full_ammount_of_payment_r = full_ammount_of_payment + "р.";
        holder.textViewFullAmountOfPayment.setText(full_ammount_of_payment_r);
        if (currentCredit.getMin_amount_of_payment() > 0) {
            holder.textViewMinAmountOfPayment.setVisibility(View.VISIBLE);
            holder.textViewMinAmmount.setVisibility(View.VISIBLE);
            String min_ammount_of_payment = String.valueOf(currentCredit.getMin_amount_of_payment());
            String min_ammount_of_payment_r = min_ammount_of_payment + "р.";
            holder.textViewMinAmountOfPayment.setText(min_ammount_of_payment_r);
        } else {
            holder.textViewMinAmountOfPayment.setVisibility(View.GONE);
            holder.textViewMinAmmount.setVisibility(View.GONE);
        }
        String already_payed = String.valueOf(currentCredit.getAlready_payed());
        String already_payed_r = already_payed + "р.";
        holder.textViewAlreadyPayed.setText(already_payed_r);
    }

    @Override
    public int getItemCount() {
        return credits.size();
    }

    public void setCredit(List<Credit> credits) {
        this.credits = credits;
        notifyDataSetChanged();
    }

    class CreditHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView textViewTitle;
        private TextView textViewLastPayDay;
        private TextView textViewFullAmountOfPayment;
        private TextView textViewMinAmountOfPayment;
        private TextView textViewMinAmmount;
        private TextView textViewAlreadyPayed;

        public CreditHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textview_title);
            textViewLastPayDay = itemView.findViewById(R.id.textview_last_pay_date);
            textViewFullAmountOfPayment = itemView.findViewById(R.id.textview_full_amount_of_payment);
            textViewMinAmountOfPayment = itemView.findViewById(R.id.textview_min_amount_of_payment);
            textViewMinAmmount = itemView.findViewById(R.id.textview_min_ammount);
            textViewAlreadyPayed = itemView.findViewById(R.id.textview_already_payed);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(credits.get(position));
                    }

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menu.setHeaderTitle("Выберите действие");
            menu.add(this.getAdapterPosition(), 121, 0, "Удалить запись");
            //menu.add(this.getAdapterPosition(), 122, 0, "Редактировать запись");
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Credit credit);
        void onItemLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void removeCredit(int position) {
        credits.remove(position);
        notifyDataSetChanged();
    }
}
