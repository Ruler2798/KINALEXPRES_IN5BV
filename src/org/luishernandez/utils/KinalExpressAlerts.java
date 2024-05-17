
package org.luishernandez.utils;

import javafx.scene.control.Alert;


public class KinalExpressAlerts {
    private static KinalExpressAlerts instance;

    private KinalExpressAlerts() {
    }
    
    public static KinalExpressAlerts getInstance (){
        if (instance == null)
            instance = new KinalExpressAlerts();
        return instance;
    }    
    
    public void mostrarAlerta (int messageCode){
        if( messageCode == 400){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacion");
            alert.setHeaderText(null);
            alert.setContentText("Se regitro el cliente correctamente");
            alert.showAndWait();
        }
    }
    
    
}
