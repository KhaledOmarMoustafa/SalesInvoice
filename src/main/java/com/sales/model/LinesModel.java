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
public class LinesModel extends AbstractTableModel {

    private ArrayList<Line> lines ;
     private String [] col = {"Number","Item","Price","Count","Total"};

    public LinesModel(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }
     
    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public int getColumnCount() {
        return col.length;
           }
    @Override
    public String getColumnName(int x) {
        return col[x];
           }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       Line line = lines.get(rowIndex);
       switch(columnIndex) {
           case 0 : return line.getInvoice().getNum();
           case 1 : return line.getItem();
           case 2 : return line.getPrice();
           case 3 : return line.getCount();
           case 4 : return line.getLineTotal();
           default : return "";
       }
       
    }
    
}
