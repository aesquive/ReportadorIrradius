package util;

import java.util.LinkedList;

/**
 *
 * @author Alberto
 * @version 1.2
 * @author Galindo Martinez Jose Cruz
 */
public class EtapaProyecto {
     /**
   * lista de parametros de la etapa (porcentajes)
   */
  private LinkedList<Parametros> listaParametrosPorcentajes ;
  /**
   * lista de parametros de la etapa
   */
  private LinkedList<Parametros> listaParametrosNo;
  
  private LinkedList<RegistroFecha> listaFechasVenta;
  private LinkedList<RegistroFecha> listaFechasTitulacion;
  
  private String nomEta;

    public EtapaProyecto(LinkedList<Parametros> listaParametrosPorcentajes, 
            LinkedList<Parametros> listaParametros, 
            LinkedList<RegistroFecha> listaVenta, 
            LinkedList<RegistroFecha> listatitulacion, String nomEta) {
        this.listaParametrosPorcentajes = listaParametrosPorcentajes;
        this.listaParametrosNo = listaParametros;
        this.listaFechasVenta = listaVenta;
        this.listaFechasTitulacion = listatitulacion;
        this.nomEta = nomEta;
    }

    /**
     * @return the listaParametrosPorcentajes
     */
    public LinkedList<Parametros> getListaParametrosPorcentajes() {
        return listaParametrosPorcentajes;
    }

    /**
     * @param listaParametrosPorcentaje01/01/2012s the listaParametrosPorcentajes to set
     */
    public void setListaParametrosPorcentajes(LinkedList<Parametros> listaParametrosPorcentajes) {
        this.listaParametrosPorcentajes = listaParametrosPorcentajes;
    }

    /**
     * @return the listaParametros
     */
    public LinkedList<Parametros> getListaParametrosNo() {
        return listaParametrosNo;
    }

    /**
     * @param listaParametros the listaParametros to set
     */
    public void setListaParametrosNo(LinkedList<Parametros> listaParametros) {
        this.listaParametrosNo = listaParametros;
    }

    /**
     * @return the nomEta
     */
    public String getNomEta() {
        return nomEta;
    }

    /**
     * @param nomEta the nomEta to set
     */
    public void setNomEta(String nomEta) {
        this.nomEta = nomEta;
    }

  /**
   * @return the listaFechasVenta
   */
  public LinkedList<RegistroFecha> getListaFechasVenta() {
    return listaFechasVenta;
  }

  /**
   * @param listaFechasVenta the listaFechasVenta to set
   */
  public void setListaFechasVenta(LinkedList<RegistroFecha> listaFechasVenta) {
    this.listaFechasVenta = listaFechasVenta;
  }

  /**
   * @return the listaFechasTitulacion
   */
  public LinkedList<RegistroFecha> getListaFechasTitulacion() {
    return listaFechasTitulacion;
  }

  /**
   * @param listaFechasTitulacion the listaFechasTitulacion to set
   */
  public void setListaFechasTitulacion(LinkedList<RegistroFecha> listaFechasTitulacion) {
    this.listaFechasTitulacion = listaFechasTitulacion;
  }
    
    
} 
