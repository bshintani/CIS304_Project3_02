/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cis304_project3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Bryan
 */
public class CarFaxFrame extends JFrame implements ActionListener {

    //create frame controls
    private JLabel lblVin, lblMake, lblModel, lblYear, lblWelcome;
    private JTextField txtVin, txtMake, txtModel, txtYear, txtFind;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnFind;
    private JPanel pnlData, pnlButtons;

    public CarFaxFrame() {
        //initialize labels and align them
        lblVin = new JLabel("VIN:");
        lblVin.setHorizontalAlignment(SwingConstants.CENTER);
        lblMake = new JLabel("Car Make:");
        lblMake.setHorizontalAlignment(SwingConstants.CENTER);
        lblModel = new JLabel("Car Model:");
        lblModel.setHorizontalAlignment(SwingConstants.CENTER);
        lblYear = new JLabel("Car Year:");
        lblYear.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome = new JLabel("Welcome to Java DB CarFax");
        lblWelcome.setVerticalAlignment(SwingConstants.BOTTOM);
        
        //initialize textfields
        txtVin = new JTextField(5);
        txtMake = new JTextField(10);
        txtModel = new JTextField(10);
        txtYear = new JTextField(4);
        txtFind = new JTextField(5);
        
        //initialize buttons
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        btnFind = new JButton("Find");
        
        //add action listener to buttons
        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnClear.addActionListener(this);
        btnFind.addActionListener(this);
        
        //create panel to hold majority of data input
        pnlData = new JPanel();
        pnlData.setLayout(new GridLayout(5, 2));
        
        //add respective labels and textfields
        pnlData.add(new JLabel(""));
        pnlData.add(new JLabel("\tWelcome to Java DB CarFax")); 
        pnlData.add(lblVin);
        pnlData.add(txtVin);
        pnlData.add(lblMake);
        pnlData.add(txtMake);
        pnlData.add(lblModel);
        pnlData.add(txtModel);
        pnlData.add(lblYear);
        pnlData.add(txtYear);

        pnlButtons = new JPanel();

        pnlButtons.add(btnAdd);
        pnlButtons.add(btnUpdate);
        pnlButtons.add(btnDelete);
        pnlButtons.add(btnClear);
        pnlButtons.add(btnFind);
        pnlButtons.add(txtFind);

        this.setLayout(new GridLayout(2, 1));

        this.add(pnlData);
        this.add(pnlButtons);

        CarFaxDB.dbConnect();
        int records = CarFaxDB.rowCount();
        JOptionPane.showMessageDialog(null, "Welcome to Carfax App. " + records + " cars have been loaded from the database.");
        CarFaxDB.dbClose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            addCar();
        } else if (e.getSource() == btnUpdate) {
            updateCar();
        } else if (e.getSource() == btnDelete) {
            deleteCar();
        } else if (e.getSource() == btnClear) {
            clearCar();
        } else if (e.getSource() == btnFind) {
            findCar();
        }
    }

    public void addCar() {
        CarFaxValidator.clearError();

        Car aCar = new Car(CarFaxValidator.getVin(txtVin.getText()),
                CarFaxValidator.getMake(txtMake.getText()),
                CarFaxValidator.getModel(txtModel.getText()),
                CarFaxValidator.getYear(Integer.parseInt(txtYear.getText())));
        if (CarFaxValidator.getError().length() != 0) {
            JOptionPane.showMessageDialog(null, "ERROR:\n\n" + CarFaxValidator.getError());
        } else {
            CarFaxDB.dbConnect();
            CarFaxDB.add(aCar);
            CarFaxDB.dbClose();

            clearCar();
        }
    }

    public void updateCar() {
        CarFaxValidator.clearError();

        Car aCar = new Car(CarFaxValidator.getVin(txtVin.getText()),
                CarFaxValidator.getMake(txtMake.getText()),
                CarFaxValidator.getModel(txtModel.getText()),
                CarFaxValidator.getYear(Integer.parseInt(txtYear.getText())));
        if (CarFaxValidator.getError().length() != 0) {
            JOptionPane.showMessageDialog(null, "ERROR:\n\n" + CarFaxValidator.getError());
        } else {
            CarFaxDB.dbConnect();
            CarFaxDB.update(aCar);
            CarFaxDB.dbClose();

            clearCar();
        }
    }

    public void deleteCar() {
        CarFaxValidator.clearError();

        String vin = CarFaxValidator.getVin(txtVin.getText());
        if (CarFaxValidator.getError().length() != 0) {
            JOptionPane.showMessageDialog(null, "ERROR:\n\n" + CarFaxValidator.getError());
        } else {
            CarFaxDB.dbConnect();
            CarFaxDB.delete(vin);
            CarFaxDB.dbClose();

            clearCar();
        }
    }

    public void clearCar() {
        txtVin.setText("");
        txtMake.setText("");
        txtModel.setText("");
        txtYear.setText("");
        txtFind.setText("");
    }

    public void findCar() {
        CarFaxValidator.clearError();

        String find = CarFaxValidator.getVin(txtFind.getText());
        if (CarFaxValidator.getError().length() != 0) {
            JOptionPane.showMessageDialog(null, "ERROR:\n\n" + CarFaxValidator.getError());
        } else {
            CarFaxDB.dbConnect();
            Car aCar = CarFaxDB.select(find);
            CarFaxDB.dbClose();

            txtVin.setText(aCar.getVin());
            txtMake.setText(aCar.getMake());
            txtModel.setText(aCar.getModel());
            txtYear.setText(Integer.toString(aCar.getYear()));
            txtFind.setText("");
        }
    }
}
