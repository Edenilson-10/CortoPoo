/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import dao.ProductoDao;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;

/**
 *
 * @author LN710Q
 */

public class Consulta extends JFrame{
    
    public JLabel lblCarnet, lblNombre,lblApellidos, lblEdad, lblStock, lblUniversidad, lblEstado;
    public JTextField carnet,apellidos, nombre, edad;
    public JComboBox Universidad, Stock, Estado;
    
    ButtonGroup existencia = new ButtonGroup();
    public JRadioButton no;
    public JRadioButton si;
    public JTable resultados;
    
    public JPanel table;
    
    public JButton buscar,eliminar,insertar,limpiar,actualizar;
    
    private static final int ANCHOC = 130, ALTOC = 30;
    
    DefaultTableModel tm;
    
    public Consulta(){
        super("Inventario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        agregarLabels();
        formulario();
        llenarTabla();
        Container container = getContentPane();
        container.add(lblCarnet);
        container.add(lblNombre);
        container.add(lblApellidos);
        container.add(lblEdad);
        //container.add(lblStock);
        container.add(carnet);
        container.add(Universidad);
      //  container.add(Stock);
        container.add(si);
        container.add(no);
        container.add(buscar);
        container.add(insertar);
        container.add(actualizar);
        container.add(eliminar);
        container.add(limpiar);
        container.add(table);
        container.add(nombre);
        container.add(lblNombre);
        container.add(apellidos);
        container.add(lblApellidos);
        container.add(Universidad);
        container.add(Estado);
        
        setSize(600,600);
        eventos();
    }
    
    public final void agregarLabels(){
        lblNombre = new JLabel("Nombre");
        lblCarnet = new JLabel("Carnet");
        lblApellidos = new JLabel("Apellidos");
        lblUniversidad = new JLabel("Universidad");
        lblEstado = new JLabel("Estado");
        lblCarnet.setBounds(10,60,ANCHOC,ALTOC);
        lblUniversidad.setBounds(300,60,ANCHOC,ALTOC);
        lblStock.setBounds(10,140,ANCHOC,ALTOC);
        lblNombre.setBounds(10, 10, ANCHOC, ALTOC);
        lblApellidos.setBounds(10,100,ANCHOC, ALTOC);
        
    }
    
    public final void formulario(){
        nombre = new JTextField();
        apellidos = new JTextField();
        carnet = new JTextField();
        Universidad = new JComboBox();
        edad = new JTextField();
        si = new JRadioButton("si", true);
        no = new JRadioButton("no");
        resultados = new JTable();
        buscar = new JButton("Buscar");
        insertar = new JButton("Insertar");
        eliminar =  new JButton("Eliminar");
        actualizar = new JButton("Actualizar");
        limpiar = new JButton("Limpiar");
        
        table = new JPanel();
        Universidad.addItem("UCA");
        Universidad.addItem("UDB");
        Universidad.addItem("UFG");
        Universidad.addItem("UGB");
        
        existencia = new ButtonGroup();
        existencia.add(si);
        existencia.add(no);
        
        nombre.setBounds(140,10, ANCHOC, ALTOC);
        carnet.setBounds(140,50,ANCHOC,ALTOC);
        Universidad.setBounds(350,60,ANCHOC,ALTOC);
        apellidos.setBounds(140, 100, ANCHOC, ALTOC);
        //stock.setBounds(140,140,ANCHOC,ALTOC);
        si.setBounds(100,190,50,ALTOC);
        no.setBounds(150,190,50,ALTOC);
        
        buscar.setBounds(300,10,ANCHOC,ALTOC);
        insertar.setBounds(10,310,ANCHOC,ALTOC);
        actualizar.setBounds(150,310,ANCHOC,ALTOC);
        eliminar.setBounds(300,310,ANCHOC,ALTOC);
        limpiar.setBounds(450,310,ANCHOC,ALTOC);
        resultados = new JTable();
        table.setBounds(10,350,500,200);
        table.add(new JScrollPane(resultados));
    }
    
    public void llenarTabla(){
        
        tm = new DefaultTableModel(){
            @Override
            public Class<?> getColumnClass(int column){
                switch(column){
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        tm.addColumn("nombre");
        tm.addColumn("Carnet");
        tm.addColumn("Universidad");
        tm.addColumn("Apellidos");
        tm.addColumn("Estado");
        
        
        ProductoDao fd = new ProductoDao();
        ArrayList <Producto> productos = fd.readAll();
        
        for (Producto fi: productos){
            tm.addRow(new Object[]{ fi.getCarnet(),fi.getUniversidad(),fi.getExistencia()});
        }
        
        resultados.setModel(tm);
    }
    
    public void eventos(){
        insertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoDao fd = new ProductoDao();
                Producto f = new Producto(carnet.getText(),Universidad.getSelectedItem().toString(), Integer.parseInt(stock.getText()),true);
                if(no.isSelected()){
                    f.setExistencia(false);
                }
                if(fd.create(f)){
                    JOptionPane.showMessageDialog(null,"Producto registrado con exito");
                    limpiarCampos();
                    llenarTabla();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Ocurrio un problema al momento de insertar el producto");
                    
                }
            }        
        });
        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoDao fd = new ProductoDao();
                Producto f = new Producto(carnet.getText(),Universidad.getSelectedItem().toString(),
                        Integer.parseInt(stock.getText()),true);
                if(no.isSelected()){
                    f.setExistencia(false);
                }
                if(fd.create(f)){
                    JOptionPane.showMessageDialog(null,"SE HAN ACTUALIZADO LOS PRODUCTOS!!");
                    limpiarCampos();
                    llenarTabla();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Ocurrio UN PROBLEMA AL ACTUALIZAR");
                    
                }
            } 
        });
        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoDao fd = new ProductoDao();
                if(fd.delete(carnet.getText())){
                    JOptionPane.showMessageDialog(null,"Producto borrado con exito");
                    limpiarCampos();
                    llenarTabla();
                }
                else{
                    JOptionPane.showMessageDialog(null,"OCURRIO UN PROBLEMA AL ELIMIAR...");
                    
                }
            } 
        });
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoDao fd = new ProductoDao();
                Producto f = fd.read(carnet.getText());
                if(f == null){
                    JOptionPane.showMessageDialog(null,"ENCONTRADO!! ");
                }
                else{
                    carnet.setText(f.getCarnet());
                    Universidad.setSelectedItem(f.getUniversidad());
                    stock.setText(Integer.toString(f.getStock()));
                    if(f.getExistencia()){
                        si.setSelected(true);
                    }
                    else{
                        no.setSelected(true);
                    }
                }
            } 
        });
        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            } 
        });
    }
    
    public void limpiarCampos(){
        nombre.setText("");
        apellidos.setText("");
        carnet.setText("");
        Universidad.setSelectedItem("Universidades");
        edad.setText("");
    }
    
    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                new Consulta().setVisible(true);
            }
            
        });
    }
}


