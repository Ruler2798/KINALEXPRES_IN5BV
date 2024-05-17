
package org.luishernandez.system;


import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.luishernandez.controller.MenuClientesController;
import org.luishernandez.controller.MenuPrincipalController;
import org.luishernandez.controller.MenuProductosController;
import org.luishernandez.controller.MenuProveedoresController;
import org.luishernandez.controller.MenuTipoProductoController;

/**
 *Documentacion 
 * Nombre completo:Luis Pedro Hernandez Garcia
 * Fecha de creacion: 05/04/2024
 * Fecha de Modificacion: 8/04/2024 10/04/2024 11/04
 * @author lphg3
 */
public class Principal extends Application {
  private Stage escenarioPrincipal;
  private Scene escena;
  private final String URLVIEW = "/org/luishernandez/view/";
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
       this.escenarioPrincipal = escenarioPrincipal;
       this.escenarioPrincipal.setTitle("Kinal Express");
        escenarioPrincipal.getIcons().add(new Image(Principal.class.getResourceAsStream("/org/luishernandez/images/LogoKinalExpress.png")));
       menuPrincipalView();
      //Parent root = FXMLLoader.load(getClass().getResource("/org/luishernandez/view/MenuPrincipalView.fxml"));
      // Scene escena = new Scene(root);
      // escenarioPrincipal.setScene(escena);
       escenarioPrincipal.show();      
    }
     public Initializable cambiarEscena(String fxmlName, int width, int heigth) throws Exception{
         Initializable resultado = null;
         FXMLLoader loader = new FXMLLoader();
         
         InputStream file = Principal.class.getResourceAsStream( URLVIEW + fxmlName);
         loader.setBuilderFactory(new JavaFXBuilderFactory());
         loader.setLocation(Principal.class.getResource(URLVIEW + fxmlName));
         
         escena = new Scene ((AnchorPane)loader.load(file), width, heigth);
         escenarioPrincipal.setScene(escena);
         escenarioPrincipal.sizeToScene();
         
         resultado = (Initializable)loader.getController();
         
        return resultado;
              }
   
    public void menuPrincipalView (){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 1022,579);
            menuPrincipalView.setEscenarioPrincipal(this);  
        }catch(Exception e){
           // System.out.println(e.getMessage());
           e.printStackTrace();
        }
    }
    
    public void menuClientesView(){
        try{
            MenuClientesController menuClienteView = (MenuClientesController)cambiarEscena("MenuClientesView.fxml", 1018,572);
            menuClienteView.setEscenarioPrincipal(this);
        }catch(Exception e){
            //System.out.println(e.getMessage()); 
            e.printStackTrace();
        }
    
    }
     public void tipoProductoView(){
        try{
            MenuTipoProductoController TproductoView = (MenuTipoProductoController)cambiarEscena
        ("MenuTipoProductoView.fxml", 1034, 582);
            TproductoView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ProveedorView(){
            try{
                MenuProveedoresController proveedorView = (MenuProveedoresController)cambiarEscena
        ("MenuProveedoresView.fxml", 1181,665);
                proveedorView.setEscenarioPrincipal(this);
            }catch(Exception e){
            e.printStackTrace();
            }
    }
    public void ProductosView(){
            try{
                MenuProductosController productosView = (MenuProductosController)cambiarEscena
        ("MenuProductosView.fxml", 1127,649);
                productosView.setEscenarioPrincipal(this);
            }catch(Exception e){
            e.printStackTrace();
            }
    }
    
            
    public static void main(String[] args) {
        launch(args);   
    }

}
