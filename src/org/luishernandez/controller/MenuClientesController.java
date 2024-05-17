/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luishernandez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.luishernandez.bean.Clientes;
import org.luishernandez.db.Conexion;
import org.luishernandez.system.Principal;
import org.luishernandez.utils.KinalExpressAlerts;

/**
 *
 * @author lphg3
 */
public class MenuClientesController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {AGREGAR, ELMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Clientes> listaClientes;
    
    @FXML private Button btnRegresar;
    @FXML private TextField txtDireccionC;
    @FXML private TextField txtCorreoC;
    @FXML private TextField txtCodigoC;
    @FXML private TextField txtNit;
    @FXML private TextField txtNombreC;
    @FXML private TextField txtApellidoC;
    @FXML private TextField txtTelefonoC;
    @FXML private TableView tblClientes;
    @FXML private TableColumn colCodigoC;
    @FXML private TableColumn colNombreC;
    @FXML private TableColumn colApellidoC;
    @FXML private TableColumn colNit;
    @FXML private TableColumn colDireccionC;
    @FXML private TableColumn colTefelonoC;
    @FXML private TableColumn colCorreoC;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
     
    
           
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    public void cargarDatos (){
        tblClientes.setItems(getClientes());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("clienteID"));
        colNombreC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreClientes"));
        colApellidoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidoClientes"));
        colNit.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nitClientes"));
        colTefelonoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoClientes"));
        colDireccionC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionClientes"));
        colCorreoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));
    }
    //castear al modelo de datos clientes
    public void selecionarElemento (){
        txtCodigoC.setText(String.valueOf(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getClienteID()));
        txtNombreC.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getNombreClientes());
        txtApellidoC.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getApellidoClientes());
        txtNit.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getNitClientes());
        txtTelefonoC.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getTelefonoClientes());
        txtDireccionC.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getDireccionClientes());
        txtCorreoC.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCorreoCliente());
    }
    
    
    public ObservableList<Clientes> getClientes (){
    
        ArrayList<Clientes> lista =  new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
           //excute los demas y excute querry
           // si su consulta lleva un select debe de usar Ex querry 
           ResultSet resultado = procedimiento.executeQuery();
           while(resultado.next()){
               lista.add(new Clientes (resultado.getInt("clienteID"),
                        resultado.getString("nombreClientes"),
                        resultado.getString("apellidoClientes"),
                        resultado.getString("nitClientes"),
                        resultado.getString("telefonoClientes"),
                        resultado.getString("direccionClientes"),
                        resultado.getNString("correoClientes")
               ));
           }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaClientes = FXCollections.observableArrayList(lista);
    }
    
    public void agregar(){
     switch (tipoDeOperacion){
        
         case NINGUNO:
             activarControles();
             btnAgregar.setText("Guardar");
             btnEliminar.setText("Cancelar");
             btnEditar.setDisable(true);
             btnReporte.setDisable(true);
             imgAgregar.setImage(new Image("/org/luishernandez/images/Guardar.png"));
             imgEliminar.setImage (new Image("/org/luishernandez/images/Cancelar.png"));
             tipoDeOperacion = operaciones.ACTUALIZAR;
             break;
         case ACTUALIZAR:
             guardar ();
             KinalExpressAlerts.getInstance().mostrarAlerta(400);
             desactivarControles();
             limpiarControles ();
             btnAgregar.setText("Agregar");
             btnEliminar.setText("Eliminar");
             btnEditar.setDisable(false);
             btnReporte.setDisable(false);
             imgAgregar.setImage(new Image("/org/luishernandez/images/AgregarClientes.png"));
             imgEliminar.setImage(new Image("/org/luishernandez/images/EliminarClientes.png"));
             tipoDeOperacion = operaciones.NINGUNO;
             break;
          
        }
    
    }
    
    public void guardar (){
        Clientes   registro = new Clientes();
        registro.setClienteID(Integer.parseInt(txtCodigoC.getText()));
        registro.setNombreClientes(txtNombreC.getText());
        registro.setApellidoClientes(txtApellidoC.getText());
        registro.setNitClientes(txtNit.getText());
        registro.setTelefonoClientes(txtTelefonoC.getText());
        registro.setDireccionClientes(txtDireccionC.getText());
        registro.setCorreoCliente(txtCorreoC.getText());
        try{
            // mismo nombre por que es local y se llama abrir conexion
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarClientes(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getClienteID());
            procedimiento.setString(2, registro.getNombreClientes());
            procedimiento.setString(3, registro.getApellidoClientes());
            procedimiento.setString(4, registro.getNitClientes());
            procedimiento.setString(5, registro.getTelefonoClientes());
            procedimiento.setString(6, registro.getDireccionClientes());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
            listaClientes.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public void eliminar(){
        switch (tipoDeOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/luishernandez/images/AgregarClientes.png"));
                imgEliminar.setImage(new Image("/org/luishernandez/images/EliminarClientes.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if (tblClientes.getSelectionModel().getSelectedItem() != null){
                   int respuesta = JOptionPane.showConfirmDialog(null, "Confirma eliminar el registro", 
                           "Eliminar Clientes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                   if (respuesta  == JOptionPane.YES_NO_OPTION){
                       try{
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarClientes(?)}");
                        procedimiento.setInt(1, ((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getClienteID());
                        procedimiento.execute();
                        listaClientes.remove(tblClientes.getSelectionModel().getSelectedItem());
                        limpiarControles();
                       }catch(Exception e){
                           e.printStackTrace();
                       }
                   }
                }else
                    JOptionPane.showMessageDialog(null,"Debe de selecionar un cliente a eliminar");
                    
        }
    }
    //editar lleva el mismo concepto  de agregar y cancelar
    
    public void editar (){
            switch (tipoDeOperacion){

                 case NINGUNO:
                     if (tblClientes.getSelectionModel().getSelectedItem() != null){
                       btnEditar.setText("Actualizar");
                       btnReporte.setText("Cancelar");
                       btnAgregar.setDisable(true);
                       btnEliminar.setDisable(true);
                       imgEditar.setImage(new Image("/org/luishernandez/images/EditarClientes.png"));
                       imgReporte.setImage(new Image("/org/luishernandez/images/Cancelar.png"));
                       activarControles();
                       txtCodigoC.setEditable(false);
                       tipoDeOperacion = operaciones.ACTUALIZAR;
                       
                     }else
                         JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento");
                     
                    break;
                 case ACTUALIZAR:
                      actualizar();
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reportes");
                    btnAgregar.setDisable(false);
                    btnEliminar.setDisable(false);
                    imgEditar.setImage(new Image("/org/luishernandez/images/ActualizarClientes.png"));
                    imgReporte.setImage(new Image("/org/luishernandez/images/ReportesClientes.png"));
                    desactivarControles();
                    limpiarControles();
                    tipoDeOperacion = operaciones.NINGUNO;
                    cargarDatos();
                    break;
            }
                     
    }
    
    public void actualizar (){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarClientes(?, ?, ?, ?, ?, ?, ?)}");
            Clientes registro = (Clientes)tblClientes.getSelectionModel().getSelectedItem();
            registro.setNombreClientes(txtNombreC.getText());
            registro.setApellidoClientes(txtApellidoC.getText());
            registro.setNitClientes(txtNit.getText());
            registro.setTelefonoClientes(txtTelefonoC.getText());
            registro.setDireccionClientes(txtDireccionC.getText());
            registro.setCorreoCliente(txtCorreoC.getText());
            procedimiento.setInt(1, registro.getClienteID());
            procedimiento.setString(2,registro.getNombreClientes());
            procedimiento.setString(3, registro.getApellidoClientes());
            procedimiento.setString(4, registro.getNitClientes());
            procedimiento.setString(5, registro.getTelefonoClientes());
            procedimiento.setString(6, registro.getDireccionClientes());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
        }catch(Exception e){
          e.printStackTrace();
        }
    }
    
    public void reporte (){
        switch (tipoDeOperacion){
                case ACTUALIZAR:
                    desactivarControles();
                    limpiarControles();
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnAgregar.setDisable(false);
                    btnEliminar.setDisable(false);
                    imgEditar.setImage(new Image("/org/luishernandez/images/EditarClientes.png"));
                    imgReporte.setImage(new Image("/org/luishernandez/images/ReportesClientes.png"));
                    tipoDeOperacion = operaciones.NINGUNO;
                    break;
    
        }
    }
    
    public void desactivarControles (){
       txtCodigoC.setEditable(false);
       txtNombreC.setEditable(false);
       txtApellidoC.setEditable(false);
       txtDireccionC.setEditable(false);
       txtCorreoC.setEditable(false); 
       txtNit.setEditable(false);
       txtTelefonoC.setEditable(false);
    }
    public void activarControles (){
       txtCodigoC.setEditable(true);
       txtNombreC.setEditable(true);
       txtApellidoC.setEditable(true);
       txtDireccionC.setEditable(true);
       txtCorreoC.setEditable(true); 
       txtNit.setEditable(true);
       txtTelefonoC.setEditable(true);
    }
    public void limpiarControles (){
       txtCodigoC.clear();
       txtNombreC.clear();
       txtApellidoC.clear();
       txtDireccionC.clear();
       txtCorreoC.clear();
       txtNit.clear();
       txtTelefonoC.clear();
       tblClientes.getSelectionModel().clearSelection();
    }
    

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    
    
    @FXML
  public void handleButtonAction (ActionEvent event){
        if (event.getSource() == btnRegresar){
        escenarioPrincipal.menuPrincipalView();
        }
    }
    
}
