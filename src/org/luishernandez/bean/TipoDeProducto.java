/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luishernandez.bean;

/**
 *
 * @author lphg3
 */
public class TipoDeProducto {
    private int codigoTipoProducto;
    private String descripcionProducto;

    public TipoDeProducto() {
    }

    public TipoDeProducto(int codigoTipoProducto, String descripcionProducto) {
        this.codigoTipoProducto = codigoTipoProducto;
        this.descripcionProducto = descripcionProducto;
    }

    public int getCodigoTipoProducto() {
        return codigoTipoProducto;
    }

    public void setCodigoTipoProducto(int codigoTipoProducto) {
        this.codigoTipoProducto = codigoTipoProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    @Override
    public String toString() {
        return getCodigoTipoProducto() + " | " + getDescripcionProducto() ;
    }

    

   
    
}
