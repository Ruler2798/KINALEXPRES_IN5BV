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
import javax.swing.JOptionPane;
import org.luishernandez.bean.TipoDeProducto;
import org.luishernandez.db.Conexion;
import org.luishernandez.system.Principal;

/**
 * FXML Controller class
 *
 * @author lphg3
 */
public class MenuTipoProductoController implements Initializable {

    
    private enum operador {
        AGREGRAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operador tipoDeOperador = operador.NINGUNO;
    private ObservableList<TipoDeProducto> listaP;
    private Principal escenarioPrincipal;

    @FXML
    private TableView tvDescripcionP;
    @FXML
    private TableColumn colCodigoP;
    @FXML
    private TableColumn colDescripcionP;
    @FXML
    private TextField txtCodigoP;
    @FXML
    private TextField txtDescripcionP;
    @FXML
    private Button btnAgregarP;
    @FXML
    private Button btnEditarP;
    @FXML
    private Button btnEliminarP;
    @FXML
    private Button btnReportesP;
    @FXML
    private Button btnRegresarP;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        desactivarControles();
    }    
     public void cargarDatos() {
        tvDescripcionP.setItems(getTipoP());
        colCodigoP.setCellValueFactory(new PropertyValueFactory<TipoDeProducto, Integer>("codigoTipoProducto"));
        colDescripcionP.setCellValueFactory(new PropertyValueFactory<TipoDeProducto, String>("descripcionProducto"));
    }

    public void seleccionarElemento() {
        txtCodigoP.setText(String.valueOf(((TipoDeProducto) tvDescripcionP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        txtDescripcionP.setText((((TipoDeProducto) tvDescripcionP.getSelectionModel().getSelectedItem()).getDescripcionProducto()));
    }

    public ObservableList<TipoDeProducto> getTipoP() {
        ArrayList<TipoDeProducto> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_mostrarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoDeProducto(resultado.getInt("CodigoTipoProducto"),
                        resultado.getString("descripcionProducto")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaP = FXCollections.observableList(lista);
    }

    public void Agregar() {
        switch (tipoDeOperador) {
            case NINGUNO:
                activarControles();
                btnAgregarP.setText("Guardar");
                btnEliminarP.setText("Cancelar");
                btnEditarP.setDisable(true);
                btnReportesP.setDisable(true);
                //imgAgregar.setImage(new Image("URL"));
                tipoDeOperador = operador.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                limpiarControles();
                cargarDatos();
                desactivarControles();
                btnAgregarP.setText("Agregar");
                btnEliminarP.setText("Eliminar");
                btnEditarP.setDisable(false);
                btnReportesP.setDisable(false);
                /*regresar de nuevo a sus imagenes originales
                imgAgregar.setImage(new Image("URL"));*/
                tipoDeOperador = operador.NINGUNO;
                break;
        }

    }

    public void guardar() {
        TipoDeProducto registro = new TipoDeProducto();
        registro.setCodigoTipoProducto(Integer.parseInt(txtCodigoP.getText()));
        registro.setDescripcionProducto(txtDescripcionP.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarTipoProducto(?,?)}");
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.execute();
            listaP.add(registro);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperador) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarP.setText("Agregar");
                btnEliminarP.setText("Eliminar");
                btnEditarP.setDisable(false);
                btnReportesP.setDisable(false);
                /*regresar de nuevo a sus imagenes originales
                imgAgregar.setImage(new Image("URL"));*/
                tipoDeOperador = operador.NINGUNO;
                break;
            default:
                if (tvDescripcionP.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmas la eliminacion del registro?", "Eliminar Tipo de Productos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarTipoProducto(?)}");
                            procedimiento.setInt(1, ((TipoDeProducto) tvDescripcionP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
                            procedimiento.execute();
                            listaP.remove(tvDescripcionP.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para eliminar");
                }

                break;
        }
    }

    public void editar() {
        switch (tipoDeOperador) {
            case NINGUNO:
                if (tvDescripcionP.getSelectionModel().getSelectedItem() != null) {
                    btnEditarP.setText("Actualizar");
                    btnReportesP.setText("Cancelar");
                    btnEliminarP.setDisable(true);
                    btnAgregarP.setDisable(true);
                    activarControles();
                    txtCodigoP.setEditable(false);
                    tipoDeOperador = operador.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarP.setText("Editar");
                btnReportesP.setText("Reportes");
                btnEliminarP.setDisable(false);
                btnAgregarP.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperador = operador.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void reportes() {
        switch (tipoDeOperador) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditarP.setText("Editar");
                btnReportesP.setText("Reportes");
                btnAgregarP.setDisable(false);
                btnEliminarP.setDisable(false);
                tipoDeOperador = operador.NINGUNO;

        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_editarTipoProducto(?,?)}");
            TipoDeProducto registro = (TipoDeProducto) tvDescripcionP.getSelectionModel().getSelectedItem();
            registro.setDescripcionProducto(txtDescripcionP.getText());
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activarControles() {
        txtCodigoP.setEditable(true);
        txtDescripcionP.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoP.clear();
        txtDescripcionP.clear();
    }

    public void desactivarControles() {
        txtCodigoP.setEditable(false);
        txtDescripcionP.setEditable(false);
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresarP) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
