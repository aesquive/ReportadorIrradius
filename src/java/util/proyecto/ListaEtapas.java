/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.proyecto;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alberto
 */
public class ListaEtapas implements Comparable {
    private int indiceEtapa;
    private Object valor;

    
    public ListaEtapas(int indiceEtapa){
        this.indiceEtapa=indiceEtapa;
        this.valor=new LinkedList();
    }
    
    public ListaEtapas(int indiceEtapa, Object elementos) {
        this.indiceEtapa = indiceEtapa;
        this.valor = elementos;
    }

    @Override
    public int compareTo(Object t) {
        ListaEtapas comparada=(ListaEtapas) t;
        Integer indice=(Integer)getIndiceEtapa();
        return indice.compareTo(comparada.getIndiceEtapa());
    }

    /**
     * @return the indiceEtapa
     */
    public int getIndiceEtapa() {
        return indiceEtapa;
    }

    /**
     * @param indiceEtapa the indiceEtapa to set
     */
    public void setIndiceEtapa(int indiceEtapa) {
        this.indiceEtapa = indiceEtapa;
    }

    /**
     * @return the valor
     */
    public Object getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Object valor) {
        this.valor = valor;
    }
    
    
    
}
