package com.example.creditreminder;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "credit_table")
public class Credit implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Date last_pay_date;
    //private Date payment_settlement_date;
    private double full_amount_of_payment;
    private double min_amount_of_payment;
    private double already_payed;

    public Credit(String title, Date last_pay_date, //Date payment_settlement_date,
                  double full_amount_of_payment, double min_amount_of_payment, double already_payed) {
        this.title = title;
        this.last_pay_date = last_pay_date;
        //this.payment_settlement_date = payment_settlement_date;
        this.full_amount_of_payment = full_amount_of_payment;
        this.min_amount_of_payment = min_amount_of_payment;
        this.already_payed = already_payed;
    }

    @Ignore
    public Credit() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLast_pay_date(Date last_pay_date) {
        this.last_pay_date = last_pay_date;
    }

    //public void setPayment_settlement_date(Date payment_settlement_date) {
    //    this.payment_settlement_date = payment_settlement_date;
    //}

    public void setFull_amount_of_payment(double full_amount_of_payment) {
        this.full_amount_of_payment = full_amount_of_payment;
    }

    public void setMin_amount_of_payment(double min_amount_of_payment) {
        this.min_amount_of_payment = min_amount_of_payment;
    }

    public void setAlready_payed(double already_payed) {
        this.already_payed = already_payed;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getLast_pay_date() {
        return last_pay_date;
    }

    //public Date getPayment_settlement_date() {
    //    return payment_settlement_date;
    //}

    public double getFull_amount_of_payment() {
        return full_amount_of_payment;
    }

    public double getMin_amount_of_payment() {
        return min_amount_of_payment;
    }

    public double getAlready_payed() {
        return already_payed;
    }
}
