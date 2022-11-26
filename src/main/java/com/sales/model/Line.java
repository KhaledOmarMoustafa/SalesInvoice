/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.model;

/**
 *
 * @author khaled
 */
public class Line {
    private String item;
    private double price;
    private int count;
    private Invoice invoice;
    

    public Line() {
    }

    public Line( String item, double price, int count, Invoice invoice) {
        this.item = item;
        this.price = price;
        this.count = count;
        this.invoice = invoice;
    }
    public double getLineTotal (){
        return price * count;
    }

  /*  public Line(int num, String item, double price, int count) {
        this.item = item;
        this.price = price;
        this.count = count;
    }*/

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

  
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Line{" + "num=" + invoice.getNum() + ", item=" + item + ", price=" + price + ", count=" + count + ", invoice=" + invoice + '}';
    }

    public Invoice getInvoice() {
        return invoice;
    }
     public String getAsCSV(){
        return invoice.getNum() + " , "+item +" , "+price + " , " + count ;
    }
    
    
}
