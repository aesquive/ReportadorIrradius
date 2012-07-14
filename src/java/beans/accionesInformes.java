package beans;


import base.Dao;
import calculador.vehiculo.DistribuidorModeloVehiculo;
import interfaces.TIpoReporte;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import pojos.MdlVeh;
import pojos.PryVeh;
import pojos.RelMdlVeh;
import pojos.RepVeh;
import reportes.Reporteador;

public class accionesInformes {
    private String exelIndicadoresClave;
    private String pdfIndicadoresClave;
    private String exelInformeBalance;
    private String pdfInformeBalance;
    private String exelInformeCaja;
    private String pdfInformeCaja;
    private String exelInformeResultados;
    private String pdfInformeResultados;
    private String exelParametros;
    private String pdfParametros;

    public accionesInformes(){
        System.out.println("los beans son");
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Set<String> keySet = sessionMap.keySet();
        for(String s:keySet){
            System.out.println("la llave"+s);
            System.out.println("valor "+sessionMap.get(s));
        }
        generarNuevasRutas();
    }
    /**
     * @return the exelIndicadoresClave
     */
    public String getExelIndicadoresClave() {
        return exelIndicadoresClave;
    }

    /**
     * @param exelIndicadoresClave the exelIndicadoresClave to set
     */
    public void setExelIndicadoresClave(String exelIndicadoresClave) {
        this.exelIndicadoresClave = exelIndicadoresClave;
    }

    /**
     * @return the pdfIndicadoresClave
     */
    public String getPdfIndicadoresClave() {
        return pdfIndicadoresClave;
    }

    /**
     * @param pdfIndicadoresClave the pdfIndicadoresClave to set
     */
    public void setPdfIndicadoresClave(String pdfIndicadoresClave) {
        this.pdfIndicadoresClave = pdfIndicadoresClave;
    }

    /**
     * @return the exelInformeBalance
     */
    public String getExelInformeBalance() {
        return exelInformeBalance;
    }

    /**
     * @param exelInformeBalance the exelInformeBalance to set
     */
    public void setExelInformeBalance(String exelInformeBalance) {
        this.exelInformeBalance = exelInformeBalance;
    }

    /**
     * @return the pdfInformeBalance
     */
    public String getPdfInformeBalance() {
        return pdfInformeBalance;
    }

    /**
     * @param pdfInformeBalance the pdfInformeBalance to set
     */
    public void setPdfInformeBalance(String pdfInformeBalance) {
        this.pdfInformeBalance = pdfInformeBalance;
    }

    /**
     * @return the exelInformeCaja
     */
    public String getExelInformeCaja() {
        return exelInformeCaja;
    }

    /**
     * @param exelInformeCaja the exelInformeCaja to set
     */
    public void setExelInformeCaja(String exelInformeCaja) {
        this.exelInformeCaja = exelInformeCaja;
    }

    /**
     * @return the pdfInformeCaja
     */
    public String getPdfInformeCaja() {
        return pdfInformeCaja;
    }

    /**
     * @param pdfInformeCaja the pdfInformeCaja to set
     */
    public void setPdfInformeCaja(String pdfInformeCaja) {
        this.pdfInformeCaja = pdfInformeCaja;
    }

    /**
     * @return the exelInformeResultados
     */
    public String getExelInformeResultados() {
        return exelInformeResultados;
    }

    /**
     * @param exelInformeResultados the exelInformeResultados to set
     */
    public void setExelInformeResultados(String exelInformeResultados) {
        this.exelInformeResultados = exelInformeResultados;
    }

    /**
     * @return the pdfInformeResultados
     */
    public String getPdfInformeResultados() {
        return pdfInformeResultados;
    }

    /**
     * @param pdfInformeResultados the pdfInformeResultados to set
     */
    public void setPdfInformeResultados(String pdfInformeResultados) {
        this.pdfInformeResultados = pdfInformeResultados;
    }

    /**
     * @return the exelParametros
     */
    public String getExelParametros() {
        return exelParametros;
    }

    /**
     * @param exelParametros the exelParametros to set
     */
    public void setExelParametros(String exelParametros) {
        this.exelParametros = exelParametros;
    }

    /**
     * @return the pdfParametros
     */
    public String getPdfParametros() {
        return pdfParametros;
    }

    /**
     * @param pdfParametros the pdfParametros to set
     */
    public void setPdfParametros(String pdfParametros) {
        this.pdfParametros = pdfParametros;
    }

    private void generarNuevasRutas() {
        int ultimoReporte=buscarArchivoReporte();
        if(ultimoReporte==-1){
            System.out.println("no habia nada");
            return;
        }
        Dao dao=new Dao();
        MdlVeh modelo = dao.getMdlVeh(ultimoReporte);
        DistribuidorModeloVehiculo distribuidorModeloVehiculo=new DistribuidorModeloVehiculo(jalarProyectos(modelo), false,modelo.getMesMin(),modelo.getMesPag());
        distribuidorModeloVehiculo.modelarPrincipal();
        distribuidorModeloVehiculo.generarEdoResultados();
        HashMap<Integer, String[]> mapeo = distribuidorModeloVehiculo.reportar(modelo.getNomMdl());
        llenarBean(mapeo);
    }
    
    private List<PryVeh> jalarProyectos(MdlVeh modelo) {
        Set<RelMdlVeh> relMdlVehs = modelo.getRelMdlVehs();
        List<PryVeh> proyectos = new LinkedList<PryVeh>();
        for (RelMdlVeh r : relMdlVehs) {
            proyectos.add(r.getPryVeh());
        }
        return proyectos;
    }

    private void llenarBean(HashMap<Integer, String[]> mapeo) {
        setExelInformeResultados(mapeo.get(TIpoReporte.REPORTE_EDO_RESULTADOS)[0]);
        setPdfInformeResultados(mapeo.get(TIpoReporte.REPORTE_EDO_RESULTADOS)[1]);
        setExelInformeBalance(mapeo.get(TIpoReporte.REPORTE_BALANCE)[0]);
        setPdfInformeBalance(mapeo.get(TIpoReporte.REPORTE_BALANCE)[1]);
        setExelInformeCaja(mapeo.get(TIpoReporte.REPORTE_CAJA)[0]);
        setPdfInformeCaja(mapeo.get(TIpoReporte.REPORTE_CAJA)[1]);
        setExelIndicadoresClave(mapeo.get(TIpoReporte.REPORTE_RATIOS)[0]);
        setPdfIndicadoresClave(mapeo.get(TIpoReporte.REPORTE_RATIOS)[1]);
        setExelParametros(mapeo.get(TIpoReporte.REPORTE_PARAMETRICAS)[0]);
        setPdfParametros(mapeo.get(TIpoReporte.REPORTE_PARAMETRICAS)[1]);
    }

    private int buscarArchivoReporte() {
        try {
            String sufijo="/home/alberto/apache-tomcat-7.0.14/webapps/Faces/";
            String ruta=sufijo+"Reportes/"+"archivo.conf";
            BufferedReader reader=new BufferedReader(new FileReader(ruta));
            String readLine = reader.readLine();
            if(readLine==null||readLine.isEmpty()){
                return -1;
            }
            return Integer.parseInt(readLine);
        } catch (IOException ex) {
            Logger.getLogger(accionesInformes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public static void main(String[] args) {
        try {
            String sufijo="/home/alberto/apache-tomcat-7.0.14/webapps/Faces/";
                String ruta=sufijo+"Reportes/"+"archivo.conf";
                BufferedReader reader=new BufferedReader(new FileReader(ruta));
                String readLine = reader.readLine();
                System.out.println(readLine);
        } catch (IOException ex) {
            Logger.getLogger(accionesInformes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
