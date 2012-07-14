/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author RUMA
 */

public class MatrizBidimensional2 implements Serializable {
    private List<CeldaFechaValor2> celdas;
    
    public MatrizBidimensional2 (){
        celdas = new LinkedList<CeldaFechaValor2> ();
    }
    
    public void agregarCelda (CeldaFechaValor2 celda){
        getCeldas().add(celda);
    }

    /**
     * @return the celdas
     */
    public List<CeldaFechaValor2> getCeldas() {
        return celdas;
    }

    /**
     * @param celdas the celdas to set
     */
    public void setCeldas(List<CeldaFechaValor2> celdas) {
        this.celdas = celdas;
    }
    
    
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (CeldaFechaValor2 c:celdas){
            builder.append("fecha" + c.getFechaTexto() + "\tvalor" + c.getValor() +"\tvalor2 "+ c.getValor2() + "\n" );
        }
        return builder.toString();
    }
    
    
}
