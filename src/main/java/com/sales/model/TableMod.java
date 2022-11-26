/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author khaled
 */
public class TableMod extends AbstractTableModel {
    private ArrayList <Invoice>invoices ;
    private String [] col = {"Number","Date","Customer","Total"};

    public TableMod(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public int getRowCount() {
       return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return col.length;
        
    }
    @Override
    public String getColumnName(int column) {
        return col[column];
        
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Invoice invoice = invoices.get(rowIndex);
        switch (columnIndex){
            case 0: return invoice.getNum();
            case 1: return invoice.getDate();
            case 2: return invoice.getName();
            case 3: return invoice.getTotalAmount();
            default : return "";
            
        }
    }
}
