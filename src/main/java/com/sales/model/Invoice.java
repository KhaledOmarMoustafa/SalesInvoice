/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.model;

import java.util.ArrayList;

/**
 *
 * @author khaled
 */
public class Invoice {
   private int num;
   private String date;
   private String name;
   
   private ArrayList<Line>lines;
    public Invoice() {
    }

    public Invoice(int num,  String date,String name) {
        this.num = num;
        this.date = date;
        this.name = name;
        
    }
    public double getTotalAmount (){
        double total = 0;
        for(Line line :  getLines()){
            total += line.getLineTotal();
     
    }   return total;
    }

    public ArrayList<Line> getLines() {
        if (lines == null){
            lines = new ArrayList<>();
        }
        return lines;
    }
    

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
           return "Invoice{" + "num=" + num + ", date=" + date + ", customer=" + name + '}';
 }
    public String getAsCSV(){
        return num + " , "+date +" , "+name ;
    }
    
    
    
    
    
}
