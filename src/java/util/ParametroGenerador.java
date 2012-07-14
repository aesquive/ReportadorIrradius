package util;

/**
 *
 * Clase que genera los registros para los bean de vehiculo y de proyecto
 * @author Galindo Martinez Jose Cruz
 */
public class ParametroGenerador {
  
  /**
   * Descripcion.
   */
  private String descripcion;
  /**
   * Valor.
   */
  private String porcentajeValor;
  /**
   * Numero de elementos.
   */
  private String monto;

  /**
   * Crea un objeto de la clase Parametro generador.
   */
  public ParametroGenerador() {
  }

  /**
   * Crea un objeto de la clase Parametro Generador inicializandolo
   * con los parametros de entrada.
   * @param descripcion La descipcion del parametro.
   * @param porcentajeValor El valor que tiene.
   * @param monto La cantidad de articulos.
   */
  public ParametroGenerador(String descripcion, String porcentajeValor, 
          String monto) {
    this.descripcion = descripcion;
    this.porcentajeValor = porcentajeValor;
    this.monto = monto;
  }

  /**
   * Nos permite conocer la descripcion del parametro. 
   *@return La descripcion del parametro.
   */
  public String getDescripcion() {
    return descripcion;
  }

  /**
   * Nos permite modificar la descripcion del parametro.
   * @param descripcion La nueva descripcion que se asignara al parametro.
   */
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  /**
   * Nos permite conocer la cantidad de articulos del parametro.
   * @return La cantidad de articulos.
   */
  public String getMonto() {
    return monto;
  }

  /**
   * Nos permite modificar la cantidad de articulos del parametro.
   * @param monto El nuevo monto que se asignara al parametro.
   */
  public void setMonto(String monto) {
    this.monto = monto;
  }

  /**
   * Nos permite conocer el valor del parametro.
   * @return El valor que posee el parametro.
   */
  public String getPorcentajeValor() {
    return porcentajeValor;
  }

  /**
   * Nos permite modificar el valor del parametro.
   * @param porcentajeValor El nuevo valor que se asignara al parametro.
   */
  public void setPorcentajeValor(String porcentajeValor) {
    this.porcentajeValor = porcentajeValor;
  }

}