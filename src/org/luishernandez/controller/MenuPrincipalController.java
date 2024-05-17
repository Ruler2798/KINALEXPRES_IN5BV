
package org.luishernandez.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.luishernandez.system.Principal;

/**
 *
 * @author lphg3
 */
public class MenuPrincipalController implements Initializable {
    private Principal escenarioPrincipal;
    
    @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnProveedores;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuClientesView(){
        escenarioPrincipal.menuClientesView();
    }
      
    public void ProveedorView(){
        escenarioPrincipal.ProveedorView();
    }
    public void tipoProductoView(){
        escenarioPrincipal.tipoProductoView();
    }
     public void ProductosView(){
        escenarioPrincipal.ProductosView();
    }
    
    @FXML
    public void handleButtonAction (ActionEvent event){
        if (event.getSource() == btnMenuClientes){
        escenarioPrincipal.menuClientesView();
        }
    }
   
}
