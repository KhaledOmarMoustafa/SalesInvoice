/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sales.controller;

import com.sales.model.Invoice;
import com.sales.model.Line;
import com.sales.model.LinesModel;
import com.sales.model.TableMod;
import com.sales.view.Frame;
import com.sales.view.InvoiceDialog;
import com.sales.view.LineDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;  
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author khaled
 */
public class Controller implements ActionListener , ListSelectionListener {
    private Frame frame;
    private InvoiceDialog invoiceDialog;
    private LineDialog lineDialog;
    public Controller(Frame frame){
        this.frame = frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("Action  : " + actionCommand);
        switch (actionCommand ){
            case "Load" :
                loadFile();
                break;
                case "Save" :
                    saveFile();
                break;
                case "Delete" :
                    deleteInvoice();
                break;
                case "Create" :
                    createInvoice();
                break;
                case "Create New" :
                    createNewItem();
                break;
                case "Delete Item" :
                    deleteItem();
                break;
                case "createInvoiceCancel" :
                    createInvoiceCancel();
                    break;
                case "createInvoiceOK" :
                    createInvoiceOK();
                    break;
                case "createLineOK" :
                    createLineOK();
                    break;
                case "createLineCancel":
                    createLineCancel();
                    break;
                
        }
    }
    
        @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = frame.getInvoiceTable().getSelectedRow();
        if (selectedIndex != -1){
        System.out.println("You Have Selected row : " +frame.getInvoiceTable().getSelectedRow() );
        Invoice currentInvoice = frame.getInvoices().get(selectedIndex);
        
        frame.getInvoiceNumberLine().setText(""+currentInvoice.getNum());
        frame.getInvoiceDateLine().setText(currentInvoice.getDate());     
        frame.getCustomerNameLine().setText(currentInvoice.getName());
        frame.getInvoiceTotalLine().setText(""+currentInvoice.getTotalAmount());
        LinesModel linesModel = new LinesModel(currentInvoice.getLines());
        frame.getLineTable().setModel(linesModel);
        linesModel.fireTableDataChanged();
        }
    }
    

    private void loadFile()  {
        JFileChooser fc = new JFileChooser();
        try{
            
        
        int result = fc.showOpenDialog(frame);
       if( result == JFileChooser.APPROVE_OPTION){
           File headerFile = fc.getSelectedFile();
          Path headerPath = Paths.get(headerFile.getAbsolutePath());
        List<String> headerLines = Files.readAllLines(headerPath);
           System.out.println("Invoice Has Been Read");
           ArrayList<Invoice> invoicesArray = new ArrayList<>();
           for(String headerLine : headerLines){
              String[]headerParts =  headerLine.split(",");
              int invoiceNum = Integer.parseInt(headerParts[0]);
               String invoiceDate = headerParts[1];
               String customerName = headerParts[2];

              Invoice invoice = new Invoice (invoiceNum , invoiceDate,customerName);
              invoicesArray.add(invoice);
              
           }
           System.out.println("Check Point");
           result = fc.showOpenDialog(frame);
           if(result == JFileChooser.APPROVE_OPTION){
               File lineFile = fc.getSelectedFile();
               Path linePath = Paths.get(lineFile.getAbsolutePath());
               ArrayList<String> lineLines = (ArrayList<String>) Files.readAllLines(linePath);
               System.out.println("Lines Have Been Read");
               for(String lineLine : lineLines){
                   String lineParts[] = lineLine.split(",");
                  int invoiceNum = Integer.parseInt(lineParts[0]) ;
                  String itemName = lineParts[1];
                  double itemPrice = Double.parseDouble(lineParts[2]) ;
                  int count = Integer.parseInt(lineParts[3]) ; 
                  Invoice inv = null;
                  for(Invoice invoice  : invoicesArray){
                      if(invoice.getNum() == invoiceNum){
                          inv = invoice;
                          break;
                      }
                  }
                  Line line = new Line( itemName ,itemPrice , count , inv);
                  inv.getLines().add(line); 
               }System.out.println("Check point");
           }
           frame.setInvoices(invoicesArray);
           TableMod tableMod = new TableMod(invoicesArray);
           frame.setTableMod(tableMod);
           frame.getInvoiceTable().setModel(tableMod);
           frame.getTableMod().fireTableDataChanged();
       }      }catch (IOException ex){
           ex.printStackTrace();
               }
  
    }

    private void saveFile() {
        ArrayList<Invoice>invoices = frame.getInvoices();
        String  headers = "";
        String lines = "";
        for(Invoice invoice : invoices ){
            String invCSV = invoice.getAsCSV();
            headers += invCSV;
            headers+= "\n";
            for(Line line : invoice.getLines()){
                String lineCSV = line.getAsCSV();
                lines += lineCSV;
                lines += "\n";
            }
        }
        System.out.println("CheckPoint");
        try{
        JFileChooser fc = new JFileChooser();
       int result =  fc.showSaveDialog(frame);
       if(result == JFileChooser.APPROVE_OPTION){
           File headerFile = fc.getSelectedFile();
           FileWriter fw = new FileWriter(headerFile);
           fw.write(headers);
           fw.flush();
           fw.close();
           result = fc.showSaveDialog(frame);
           if(result == JFileChooser.APPROVE_OPTION){
               File lineFile = fc.getSelectedFile();
               FileWriter hfw = new FileWriter(headerFile);
               hfw.write(lines);
               hfw.flush();
               hfw.close();
           }}
       }catch(Exception ex){
           
       }
    }

    private void deleteInvoice() {
      int selectedRow =   frame.getInvoiceTable().getSelectedRow();
      if(selectedRow != -1){
          frame.getInvoices().remove(selectedRow);
          frame.getTableMod().fireTableDataChanged();
      }
    }

    private void createInvoice() {
        invoiceDialog = new InvoiceDialog(frame);
        invoiceDialog.setVisible(true);
    }

    private void createNewItem() {
        lineDialog = new LineDialog (frame);
        lineDialog.setVisible(true);
       
    }

    private void deleteItem() {
         int selectedRow =   frame.getLineTable().getSelectedRow();
      if(selectedRow != -1){
          LinesModel linesModel = (LinesModel)frame.getLineTable().getModel();
          linesModel.getLines().remove(selectedRow);
          linesModel.fireTableDataChanged();
          frame.getTableMod().fireTableDataChanged();
      }
        
    }

    private void createInvoiceCancel() {
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }

    private void createInvoiceOK() {
      String date =  invoiceDialog.getInvDateField().getText(); 
      String cust = invoiceDialog.getCustNameField().getText();
      int num = frame.getNextInvoiceNumber();
      Invoice invoice = new Invoice (num ,  date , cust);
      frame.getInvoices().add(invoice);
      frame.getTableMod().fireTableDataChanged();
      invoiceDialog.setVisible(false);
      invoiceDialog.dispose();
      invoiceDialog=null;
      
          
      
    }

    private void createLineOK() {
        String item = lineDialog.getItemNameField().getText();
        String countstr = lineDialog.getItemCountField().getText();
        String pricestr = lineDialog.getItemPriceField().getText();
        int count = Integer.parseInt(countstr);
        double price = Double.parseDouble(pricestr);
        int selectedInvoice = frame.getInvoiceTable().getSelectedRow();
        if(selectedInvoice != -1){
        Invoice invoice = frame.getInvoices().get(selectedInvoice);
        Line line = new Line(item , price ,count,invoice);
        invoice.getLines().add(line);
        LinesModel linesModel = (LinesModel) frame.getLineTable().getModel();
       // linesModel.getLines().add(line);
        linesModel.fireTableDataChanged();
        frame.getTableMod().fireTableDataChanged();
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog=null;
        
        }
        
    }

    private void createLineCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
     }


}
