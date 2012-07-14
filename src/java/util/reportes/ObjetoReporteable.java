package util.reportes;

import com.sun.org.apache.xpath.internal.functions.FuncBoolean;
import java.util.LinkedList;
import java.util.List;
import util.Funciones;

/**
 *
 * @author Alberto Esquivel Vega
 */

public class ObjetoReporteable {

    private List<String> valores;
    private String nombre;
    private String urlGrafica;
    private boolean estilo;

    public ObjetoReporteable(){
        
    }
    
    public ObjetoReporteable(List<String> valores, String nombre, String urlGrafica, boolean estilo) {
        this.valores = valores;
        this.nombre = nombre;
        this.urlGrafica = urlGrafica;
        this.estilo = estilo;
    }

    /**
     * @return the valores
     */
    public List<String> getValores() {
        List<String> regreso=new LinkedList<String>();
        for(String s:valores){
            double numero=0;
            try{
               numero=Double.parseDouble(s);
            }catch(NumberFormatException num){
                
            }
            String cadena= numero>1000 ? Funciones.ponerComasCantidades(Math.round(numero)) : s;
        }
        return valores;
    }

    /**
     * @param valores the valores to set
     */
    public void setValores(List<String> valores) {
        this.valores = valores;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the urlGrafica
     */
    public String getUrlGrafica() {
        return urlGrafica;
    }

    /**
     * @param urlGrafica the urlGrafica to set
     */
    public void setUrlGrafica(String urlGrafica) {
        this.urlGrafica = urlGrafica;
    }

    /**
     * @return the estilo
     */
    public boolean isEstilo() {
        return estilo;
    }

    /**
     * @param estilo the estilo to set
     */
    public void setEstilo(boolean estilo) {
        this.estilo = estilo;
    }

    @Override
    public String toString() {
        return "ObjetoReporteable{" + "valores=" + valores + ", nombre=" + nombre + ", urlGrafica=" + urlGrafica + ", estilo=" + estilo + '}';
    }

    
    
}
