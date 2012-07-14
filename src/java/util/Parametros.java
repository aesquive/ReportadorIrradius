package util;

/**
 * Clase que genera los registros para los bean de vehiculo y de proyecto
 * @author Hector Daniel Gonzalez Teran
 */
public class Parametros implements Comparable {
  
  private String registroDescripcion;
  private String registroIngreso;

  /**
   * Crea una nueva instancia de la clase parametros inicializandola
   * con los atributos dados.
   * @param registroDescripcion La descripcion del parametro. 
   * @param registroIngreso El ingreso con el cual cuenta el parametro.
   */
  public Parametros(String registroDescripcion, String registroIngreso) {
    this.registroDescripcion = registroDescripcion;
    this.registroIngreso = registroIngreso;
  }

  /**
   * @return the registroDescripcion
   */
   public String getRegistroDescripcion() {
    return registroDescripcion;
  }

  /**
   * @param registroDescripcion the registroDescripcion to set
   */
  public void setRegistroDescripcion(String registroDescripcion) {
    this.registroDescripcion = registroDescripcion;
  }

  /**
   * @return the registroIngreso
   */
  public String getRegistroIngreso() {
    return registroIngreso;
  }

  /**
   * @param registroIngreso the registroIngreso to set
   */
  public void setRegistroIngreso(String registroIngreso) {
    this.registroIngreso = registroIngreso;
  }

    @Override
    public int compareTo(Object t) {
        Parametros p=(Parametros)t;
        try{
            Double primero=Double.parseDouble(registroIngreso);
            Double segundo=Double.parseDouble(p.registroIngreso);
            return segundo.compareTo(primero);
        }catch(NumberFormatException num){
            return 0;
        }
    }
  
  
  
}
