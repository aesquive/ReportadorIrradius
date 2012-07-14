/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package variables;

/**
 *
 * @author alberto
 */
import base.Catalogueador;
import base.Dao;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import pojos.DatPryInd;
import pojos.EtpMatPryInd;
import pojos.EtpPryInd;
import pojos.MatPryInd;
import pojos.PryPryInd;
import pojos.VarPryInd;
import util.CeldaFechaValor;
import util.MatrizBidimensional;
import util.proyecto.ListaEtapas;

public class ManejadorVariablesProyectos {

    private Map<String, Object> mapeoVariables;
    private PryPryInd proyecto;
    private Catalogueador catalogueador;
    private int MATRIZ_PAGO_TERRENO = 1;
    private int MATRIZ_TASA_INTERES_CREDITO_PUENTE = 2;
    private int MATRIZ_INFLACION = 3;
    private int MATRIZ_TASAS_CETES = 4;
    private int MATRIZ_VENTA_ETAPA = 5;
    private int MATRIZ_TITULACION_ETAPA = 6;

    public ManejadorVariablesProyectos(PryPryInd proyecto) {
        this.proyecto = proyecto;
        this.catalogueador = new Catalogueador();
        mapeoVariables = new HashMap<String, Object>();
        llenarMapeo();
    }

    public Object obtenerVariable(String nombre) {
        return getMapeoVariables().get(nombre);
    }

    public void guardarVariable(String nombre, Object valor) {
        getMapeoVariables().put(nombre, valor);
    }

    private void llenarMapeo() {
        llenarDatosProyecto();
        llenarDatosMatrices();
        llenarDatosEtapas();
        llenarMatricesEtapas();
    }

    private void llenarDatosProyecto() {
        Set<DatPryInd> datos = proyecto.getDatPryInds();
        for (DatPryInd dato : datos) {
            guardarVariable(dato.getVarPryInd().getDesTca(), castearVAlor(dato.getVal()));
        }
    }

    private void llenarDatosMatrices() {
        guardarVariable("pry_arr_pro_pgo_ter", llenarMatrices(this.MATRIZ_PAGO_TERRENO));
        guardarVariable("pry_arr_num_mes_cre_pue", llenarMatrices(this.MATRIZ_TASA_INTERES_CREDITO_PUENTE));
        guardarVariable("pry_arr_num_mes_inf", llenarMatrices(this.MATRIZ_INFLACION));
        guardarVariable("pry_arr_num_mes_cet", llenarMatrices(this.MATRIZ_TASAS_CETES));
    }

    private MatrizBidimensional llenarMatrices(int tipoMAtriz) {
        Set<MatPryInd> matrices = getProyecto().getMatPryInds();
        List<CeldaFechaValor> lista = new LinkedList<CeldaFechaValor>();
        for (MatPryInd m : matrices) {
            if (m.getTipMatPryInd().getId() == tipoMAtriz) {
                CeldaFechaValor celda = new CeldaFechaValor(m.getFch(), Double.parseDouble(String.valueOf(castearVAlor(m.getVal()))));
                lista.add(celda);
            }
        }
        MatrizBidimensional m = new MatrizBidimensional();
        m.setCeldas(lista);
        return m;
    }

    private Object castearVAlor(String val) {
        Object regreso;
        if (val.contains("%")) {
            regreso = val.substring(0, val.length() - 1);
        } else {
            regreso = val;
        }
        try {
            return Double.parseDouble(String.valueOf(regreso));

        } catch (NumberFormatException num) {
            return val;
        }

    }

    /**
     * @return the mapeoVariables
     */
    public Map<String, Object> getMapeoVariables() {
        return mapeoVariables;
    }

    /**
     * @param mapeoVariables the mapeoVariables to set
     */
    public void setMapeoVariables(Map<String, Object> mapeoVariables) {
        this.mapeoVariables = mapeoVariables;
    }

    /**
     * @return the proyecto
     */
    public PryPryInd getProyecto() {
        return proyecto;
    }

    /**
     * @param proyecto the proyecto to set
     */
    public void setProyecto(PryPryInd proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * @return the catalogueador
     */
    public Catalogueador getCatalogueador() {
        return catalogueador;
    }

    /**
     * @param catalogueador the catalogueador to set
     */
    public void setCatalogueador(Catalogueador catalogueador) {
        this.catalogueador = catalogueador;
    }

    private void llenarDatosEtapas() {
        Map<String, List<ListaEtapas>> mapeo = new HashMap<String, List<ListaEtapas>>();
        Set<EtpPryInd> etpPryInds = proyecto.getEtpPryInds();

        for (EtpPryInd fila : etpPryInds) {
            VarPryInd variable = fila.getVarPryInd();
            if (variable != null) {
                String llave = fila.getVarPryInd().getDesTca();

                if (!mapeo.containsKey(llave)) {
                    mapeo.put(llave, new LinkedList<ListaEtapas>());
                }
                List<ListaEtapas> lista = mapeo.get(llave);
                lista.add(new ListaEtapas(fila.getNumEta(), castearVAlor(fila.getVal())));
                mapeo.put(llave, lista);
            }

        }
        Set<String> keySet = mapeo.keySet();
        for (String llave : keySet) {
            List auxiliar = new LinkedList();
            List<ListaEtapas> valor = mapeo.get(llave);
            Collections.sort(valor);
            for (ListaEtapas c : valor) {
                auxiliar.add(castearVAlor(String.valueOf(c.getValor())));
            }
            Object[] arreglo = new Object[valor.size()];
            auxiliar.toArray(arreglo);
            guardarVariable(llave, arreglo);
        }
    }

    private void llenarMatricesEtapas() {
        guardarVariable("pry_arr_mat_pro_vnt_eta", llenarMatricesEtapas(this.MATRIZ_VENTA_ETAPA));
        guardarVariable("pry_arr_mat_pro_tit_eta", llenarMatricesEtapas(this.MATRIZ_TITULACION_ETAPA));
    }

    private Object llenarMatricesEtapas(int tipoMatriz) {
        Set<EtpMatPryInd> etpMatPryInds = proyecto.getEtpMatPryInds();
        Map<Integer, MatrizBidimensional> mapeo = new HashMap<Integer, MatrizBidimensional>();
        for (EtpMatPryInd fila : etpMatPryInds) {
            if (fila.getTipMatPryInd().getId() == tipoMatriz) {
                if (!mapeo.containsKey(fila.getNumEta())) {
                    mapeo.put(fila.getNumEta(), new MatrizBidimensional());
                }
                MatrizBidimensional matriz = mapeo.get(fila.getNumEta());
                matriz.agregarCelda(new CeldaFechaValor(fila.getFch(), Double.parseDouble(String.valueOf(castearVAlor(fila.getVal())))));
                mapeo.put(fila.getNumEta(), matriz);
            }

        }
        MatrizBidimensional[] arreglo = new MatrizBidimensional[mapeo.size()];
        for (int t = 0; t < arreglo.length; t++) {
            arreglo[t] = mapeo.get(t);
        }
        return arreglo;
    }

   
}
