/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.util.Calendar;
/**
 *
 * @author RUMA
 */
public class CeldaFechaValor2 implements Comparable, Serializable {
    
    private Calendar fecha;
    private double valor;
    private double valor2;
    private String fechaTexto;
    private double valorRedondo;

    public CeldaFechaValor2(Calendar fecha, double valor, double valor2){
        this.fecha = fecha;
        this.valor = valor;
        this.valor2 = valor2;
    }

    /**
     * @return the fecha
     */
    public Calendar getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * @return the valor2
     */
    public double getValor2() {
        return valor2;
    }

    /**
     * @param valor2 the valor2 to set
     */
    public void setValor2(double valor2) {
        this.valor2 = valor2;
    }
   
    
    @Override
    public int compareTo(Object t){
        CeldaFechaValor2 comparar = (CeldaFechaValor2) t;
        return this.getFecha().compareTo(comparar.fecha);
    }

    /**
     * @return the fechaTexto
     */
    public String getFechaTexto() {
        return fechaTexto;
    }

    /**
     * @param fechaTexto the fechaTexto to set
     */
    public void setFechaTexto(String fechaTexto) {
        this.fechaTexto = fechaTexto;
    }

    /**
     * @return the valorRedondo
     */
    public double getValorRedondo() {
        return valorRedondo;
    }

    /**
     * @param valorRedondo the valorRedondo to set
     */
    public void setValorRedondo(double valorRedondo) {
        this.valorRedondo = valorRedondo;
    }
    
}
