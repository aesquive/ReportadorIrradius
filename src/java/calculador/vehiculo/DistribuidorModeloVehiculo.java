package calculador.vehiculo;

import base.Dao;
import interfaces.TIpoReporte;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import pojos.EtpTirVeh;
import pojos.MdlVeh;
import pojos.PryVeh;
import pojos.RelMdlVeh;
import reportes.Reporteador;
import util.Eneada;
import util.Espejo;
import util.Funciones;
import util.MatrizBidimensional;
import util.reportes.ObjetoReporteable;
import util.vehiculo.ListaEtapasVehiculo;
import variables.ManejadorVariablesVehiculo;

/**
 *Clase que se encarga de distribuir el calculo de modelo de vehiculo en diferentes clases , repartiendo
 * por pestanas del excel de modelo espana
 * 
 * @author Alberto Emmanuel Esquivel Vega
 */
public class DistribuidorModeloVehiculo {

    /**
     * Manejador de las variables de vehiculo
     */
    private ManejadorVariablesVehiculo manejador;
    /**
     * Lista de proyectos que seran evaluados en el modelo
     */
    private List<PryVeh> proyectos;
    private final boolean procesarIndividuales;
    private int tiempoMinistracion;
    private int tiempoPago;

    /**
     * COnstructor
     * @param proyectos 
     */
    public DistribuidorModeloVehiculo(List<PryVeh> proyectos, boolean procesarIndividuales,int tiempoMinistracion , int tiempoPago) {
        this.tiempoMinistracion = tiempoMinistracion;
        this.tiempoPago = tiempoPago;
        this.procesarIndividuales = procesarIndividuales;
        this.proyectos = proyectos;
        this.manejador = new ManejadorVariablesVehiculo(proyectos);
    }

    /**
     * metodo que modela individualmente los proyectos del vehiculo
     * @param respectivoManejador
     * @param proyecto 
     */
    protected void modelarIndividual(ManejadorVariablesVehiculo respectivoManejador, PryVeh proyecto) {
        CalculadorEtapas calculadorEtapas = new CalculadorEtapas(getProyectos(), respectivoManejador);
        calculadorEtapas.procesar();

        ConsolidadorEtapas consolidadorEtapas = new ConsolidadorEtapas(getTiempoMinistracion(), getTiempoPago(), getProyectos(), respectivoManejador);
        consolidadorEtapas.procesar();

        CalculadorTir calculadorTir = new CalculadorTir(tiempoMinistracion, tiempoPago,getProyectos(), respectivoManejador);
        calculadorTir.procesarIndividual();

    }

    /**
     * Metodo que mandara a hacer todos los calculos del modelo espana
     */
    public void modelarPrincipal() {
        CalculadorEtapas calculadorEtapas = new CalculadorEtapas(getProyectos(), getManejador());
        calculadorEtapas.procesar();

        ConsolidadorEtapas consolidadorEtapas = new ConsolidadorEtapas(getTiempoMinistracion(), getTiempoPago(), getProyectos(), getManejador());
        consolidadorEtapas.procesar();

        CalculadorTir calculadorTir = new CalculadorTir(tiempoMinistracion,tiempoPago,getProyectos(), getManejador());
        calculadorTir.procesarPrincipal(procesarIndividuales);


    }

    public void generarEdoResultados() {
        EstadoResultados estadoResultados = new EstadoResultados(getProyectos(), getManejador());
        estadoResultados.procesar();

        Balance balance = new Balance(proyectos, getManejador());
        balance.procesar();
        
        CajaAnual cajaAnual=new CajaAnual(getManejador());
        cajaAnual.procesar();
    }

    private List<MatrizBidimensional> sacarValoresManejador(String nombreLlave, int inProyecto) {
        int numEtapas = getProyectos().get(inProyecto).getEtpVehs().size() / 3;
        List<MatrizBidimensional> matrices = new LinkedList<MatrizBidimensional>();
        int contador = 0;
        while (contador < numEtapas) {
            MatrizBidimensional actual = (MatrizBidimensional) getManejador().obtenerVariable(nombreLlave.toLowerCase() + "[" + inProyecto + "][" + contador + "]");
            matrices.add(actual);
            contador++;
        }
        return matrices;
    }

    /**
     * @return the manejador
     */
    public ManejadorVariablesVehiculo getManejador() {
        return manejador;
    }

    /**
     * @param manejador the manejador to set
     */
    public void setManejador(ManejadorVariablesVehiculo manejador) {
        this.manejador = manejador;
    }

    /**
     * @return the proyectos
     */
    public List<PryVeh> getProyectos() {
        return proyectos;
    }

    /**
     * @param proyectos the proyectos to set
     */
    public void setProyectos(List<PryVeh> proyectos) {
        this.proyectos = proyectos;
    }
    private Object sacarValorEscalonTir(int idVar, int indice, Set<EtpTirVeh> etpTirVehs) {
        Iterator<EtpTirVeh> iterator = etpTirVehs.iterator();
        while (iterator.hasNext()) {
            EtpTirVeh next = iterator.next();
            if (next.getNumEta() == indice && next.getVarVeh().getId() == idVar) {
                return castearVAlor(next.getVal());
            }
        }
        return null;
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


    public static void main(String[] args) {
        Dao dao = new Dao();
        List<PryVeh> proyectos = new LinkedList<PryVeh>();
        MdlVeh mdl=dao.getMdlVeh(171);
        Set<RelMdlVeh> relMdlVehs = mdl.getRelMdlVehs();
        for(RelMdlVeh rel:relMdlVehs){
            proyectos.add(rel.getPryVeh());
        }
        DistribuidorModeloVehiculo dis = new DistribuidorModeloVehiculo(proyectos, false,18,12);
        dis.modelarPrincipal();
        dis.generarEdoResultados();
        System.out.println("/////////////////////");
        System.out.println(dis.getManejador().obtenerVariable("veh_bal_tot_act_anu"));
        System.out.println("-----------------");
        System.out.println(dis.getManejador().obtenerVariable("veh_bal_tot_pas_anu"));
        System.out.println("************************");
        System.out.println(dis.getManejador().obtenerVariable("veh_bal_tot_pat_anu"));
        
        System.out.println("las restas");
        dis.restarBalances();
//        dis.reportar("MI MODELO");
        
    }
    
    public void restarBalances(){
        List<Double> obtenerVariable = (List<Double>) manejador.obtenerVariable("veh_bal_tot_act_anu");
        List<Double> pas=(List<Double>) manejador.obtenerVariable("veh_bal_tot_pas_anu");
        List<Double> pat=(List<Double>) manejador.obtenerVariable("veh_bal_tot_pat_anu");
        for(int t=0;t<obtenerVariable.size();t++){
            int act=obtenerVariable.get(t).intValue();
            int ps=pas.get(t).intValue();
            int pt=pat.get(t).intValue();
            System.out.println("t= "+t+" val= "+ (pt - (act-ps)));
        }
        
        
    }

    private double sacarCrecimiento(String primerParametro) {
        if (primerParametro.equals("Precio Promedio")) {
            return .05;
        }
        if (primerParametro.equals("Tasa Interes")) {
            return .5;
        }
        if (primerParametro.equals("Porcentaje Apalancamiento")) {
            return 2;
        }
        return 0;
    }

    private void calcularEneadas(List<Eneada> eneadas, String primerParametro, String segundoParametro, Double tir) {
        Eneada primefaFila = eneadas.get(0);
        for (int indicePrimera = 0; indicePrimera < 9; indicePrimera++) {
            Eneada actual = eneadas.get(indicePrimera + 1);
            for (int indiceSegunda = 0; indiceSegunda < 9; indiceSegunda++) {
                modificarProyectos(primerParametro, String.valueOf(Espejo.invocarGetter(primefaFila, Espejo.getMetodo(primefaFila.getClass(), "getVal" + indiceSegunda))));
                modificarProyectos(segundoParametro, String.valueOf(Espejo.invocarGetter(actual, Espejo.getMetodo(actual.getClass(), "getDes"))));
                String tirTmp = obtenerValorDistribuidor();
                Espejo.invocarSetterString(actual, "setVal" + indiceSegunda, tirTmp);
                double copia = Double.parseDouble(tirTmp);
                int valor = (copia - 2) > tir ? 1 : (copia + 2) < tir ? -1 : 0;
                Espejo.invocarSetterDouble(actual, "setCol" + indiceSegunda, valor);
            }
        }
    }

    public String obtenerValorDistribuidor() {
        this.modelarPrincipal();
        Double obtenerVariable = (Double) manejador.obtenerVariable("veh_cet_ptj_tir_pry") * 100;
        String cadena2 = String.valueOf(Funciones.redondearDecimales(obtenerVariable, 2));
        return cadena2;
    }

    private void modificarProyectos(String parametro, String valor) {
        String clave = parametro.equals("Precio Promedio") ? "veh_val_pro_viv" : parametro.equals("Tasa Interes") ? "veh_tsa_anu_crd_pte" : "veh_por_apa";
        if (clave.equals("veh_val_pro_viv")) {
            modificarPrecioCasas(clave, valor);
            return;
        }
        Object[] arreglo = (Object[]) manejador.obtenerVariable(clave);
        for (int t = 0; t < proyectos.size(); t++) {
            arreglo[t] = valor;
        }
        manejador.guardarVariable(clave, arreglo);
    }

    private void modificarPrecioCasas(String clave, String valor) {
        double precioPromedioViejo = sacarPrecioPromedioViejo();
        for (int indiceProyecto = 0; indiceProyecto < proyectos.size(); indiceProyecto++) {
            double proporcion = sacarValorVivienda(indiceProyecto) / precioPromedioViejo;
            double nuevo = Double.parseDouble(valor) * proporcion;
            guardarValorVivienda(indiceProyecto, nuevo);
        }
    }

    private double sacarPrecioPromedioViejo() {
        double suma = 0;
        for (int indiceProyecto = 0; indiceProyecto < proyectos.size(); indiceProyecto++) {
            Object[] valoresViviendas = (Object[]) manejador.obtenerVariable("veh_val_pro_viv");
            suma += Double.parseDouble(String.valueOf(valoresViviendas[indiceProyecto]));
        }
        return suma / proyectos.size();
    }

    private double sacarValorVivienda(int indiceProyecto) {
        Object[] valoresViviendas = (Object[]) manejador.obtenerVariable("veh_val_pro_viv");
        return Double.parseDouble(String.valueOf(valoresViviendas[indiceProyecto]));
    }

    private void guardarValorVivienda(int indiceProyecto, double nuevo) {

        Object[] valoresViviendas = (Object[]) manejador.obtenerVariable("veh_val_pro_viv");
        valoresViviendas[indiceProyecto] = nuevo;
    }

    

    private Date obtenerFechaInicialTodo() {
        ListaEtapasVehiculo[] etapasProyectos = (ListaEtapasVehiculo[]) manejador.obtenerVariable("veh_eta");
        Date fechaMenor = null;
        for (ListaEtapasVehiculo listaEtapas : etapasProyectos) {
            int numeroEtapas = listaEtapas.getListaEtapas().size();
            for (int indiceEtapa = 0; indiceEtapa < numeroEtapas; indiceEtapa++) {
                Date fechaActual = (listaEtapas.getListaEtapas().get(indiceEtapa).getFechaInicioEtapa());
                fechaMenor = fechaMenor == null ? fechaActual : fechaMenor.compareTo(fechaActual) < 0 ? fechaMenor : fechaActual;
            }
        }
        return (Date) fechaMenor.clone();
    }

    private HashMap<String, String> generarMapeoGraficas() {
        String[] nombres = new String[]{"veh_bal_roe", "veh_bal_roa", "veh_bal_ebi", "veh_pal_ope", "veh_pal_apa", "veh_liq_anu", "veh_cap_tra_anu",
            "veh_cob_deu", "veh_rot_inv", "veh_dia_cta_por_cob", "veh_dia_cta_por_pag", "veh_cob_ser_deu", "veh_mar_ope", "veh_mar_ant_imp", "veh_mar_net",
            "veh_bal_efe_anu", "veh_bal_cta_cob_anu", "veh_bal_inv_viv_anu", "veh_bal_tot_act_anu", "veh_bal_cta_por_pag_anu", "veh_bal_deu_anu", "veh_bal_tot_pas_anu",
            "veh_bal_cap_anu", "veh_utl_per_anu", "veh_utl_ret_anu", "veh_bal_tot_pat_anu", 
            
            "veh_cet_uni_edf_anu", "veh_cet_uni_edf_acu_anu", "veh_cet_uni_dis_anu",
            "veh_cet_uni_dis_acu_anu", "veh_cet_uni_ven_anu","veh_cet_uni_ven_acu_anu",
        
        
            "veh_edo_vta_viv_anu","veh_edo_cto_vta_anu","veh_total_utl_oprt_anu",
           "veh_gst_opt_anu","veh_cet_int_cre_pte_anu","veh_utl_a_ipt_anu","veh_imp_sob_rta_anu",
            "veh_utl_net_anu",
        
                
                
                "veh_cet_ori_anu","veh_cet_ing_anu","veh_cet_fir_ctr_cpr_vta_anu","veh_cet_des_ini_anu",
            "veh_cet_cto_fin_anu","veh_cet_ant_cre_pte_anu","veh_cet_min_cre_pte_anu",
            "veh_cet_apl_anu","veh_cet_cto_vta_anu","veh_cet_gav_anu","veh_cet_gas_ind_anu","veh_cet_adm_pry_anu","veh_cet_cto_fin_anu","veh_cet_pgo_cre_pte_anu",
             "veh_cet_mov_net_anu",
            "veh_cet_cap_anu","veh_cet_apt_anu","veh_cet_ret_anu",
            "veh_cet_rep_uti_anu","veh_cet_rep_uti_lim_prt_anu","veh_cet_rep_uti_grl_prt_anu",
            };


        String[] valores = new String[]{"ROE","ROA","EBITDA","Palanca de Operacion","Apalancamiento","Liquidez","Capital de Trabajo",
            "Cobertura de Deuda","Rotacion de Inventarios","Dias de Cuentas por Cobrar","Dias de Cuenta por Pagar","Cobertura de Serivicio de Deuda","Margen Operativo","Margen Antes de Impuestos","Margen Neto",
        "Efectivo","Cuentas por Cobrar","Inventario de Vivienda","Total de Activos","Cuentas por pagar","Deuda Credito","Total Pasivos",
        "Capital","Utilidad del Periodo","Utilidad Retenida","Total Patrimonio",
        
        "Unidades Edificadas","Unidades Edificadas Acumuladas","Unidades Disponibles",
        "Unidades Disponibles Acumuladas","Unidades Vendidas","Unidades Vendidas Acumuladas",
        
        
            "Venta de Vivienda","Costo de Venta","Utilidad Operativa","Gastos Operativos","Intereses","Utilidad Antes de Impuestos","Impuestos Sobre la Renta"
        ,"Utilidad Neta",
        
              "Origen","Ingresos","Firma Cont Compra Venta","Desembolso Inicial",
              "Financieros","Anticipo Credito Puente","Ministracion Credito Puente",
              "Aplicacion","Costo de Venta","GAV","Gastos por individualizacion","Administracion de Proyecto","Costo Financiero","Pago Credito Puente",
              "Movimiento Neto",
              "Capital","Aportaciones","Retiros",
              "Reparto de Utilidades","Limited Partner","General Partner"
        };
        System.out.println();
        HashMap<String,String> mapeo=new HashMap<String, String>();
        for(int t=0;t<nombres.length;t++){
            mapeo.put(nombres[t], valores[t]);
        }
        return mapeo;
    }

    public HashMap<Integer, String[]> reportar(String nombreModelo) {
        int[] tipos = new int[]{TIpoReporte.REPORTE_EDO_RESULTADOS, TIpoReporte.REPORTE_BALANCE, TIpoReporte.REPORTE_CAJA,
            TIpoReporte.REPORTE_RATIOS, TIpoReporte.REPORTE_PARAMETRICAS};
        Reporteador reporteador;
        HashMap<Integer, String[]> mapa = new HashMap<Integer, String[]>();
        for (int t = 0; t < tipos.length; t++) {
            reporteador = new Reporteador(nombreModelo, generarListaObjetos(tipos[t]), tipos[t]);
            mapa.put(tipos[t], reporteador.reportar());
        }
        return mapa;
    }

    public List<ObjetoReporteable> generarListaObjetos(int tipoReporte) {
        List<String[]> variables = new LinkedList<String[]>();
        switch (tipoReporte) {
            
            case TIpoReporte.REPORTE_EDO_RESULTADOS:
                variables.add(new String[]{"Ingresos","veh_edo_vta_viv_anu","veh_edo_cto_vta_anu","veh_total_utl_oprt_anu"});
                variables.add(new String[]{"Otros Gastos/Ingresos","veh_gst_opt_anu","veh_cet_int_cre_pte_anu"});
                variables.add(new String[]{"Utilidad Antes de Impuestos","veh_utl_a_ipt_anu"});
                variables.add(new String[]{"Impuesto Sobre la Renta","veh_imp_sob_rta_anu"});
                variables.add(new String[]{"Utilidad Neta","veh_utl_net_anu"});
                break;

            case TIpoReporte.REPORTE_BALANCE:
                
                variables.add(new String[]{"Activos","veh_bal_efe_anu", "veh_bal_cta_cob_anu", "veh_bal_inv_viv_anu", "veh_bal_tot_act_anu"});
                variables.add(new String[]{"Pasivos", "veh_bal_cta_por_pag_anu", "veh_bal_deu_anu", "veh_bal_tot_pas_anu"});
                variables.add(new String[]{"Patrimonio","veh_bal_cap_anu", "veh_utl_per_anu", "veh_utl_ret_anu", "veh_bal_tot_pat_anu"});
                break;

            case TIpoReporte.REPORTE_CAJA:
                variables.add(new String[]{"Origen","veh_cet_ori_anu","veh_cet_ing_anu","veh_cet_fir_ctr_cpr_vta_anu","veh_cet_des_ini_anu"});
                variables.add(new String[]{"Financieros","veh_cet_cto_fin_anu","veh_cet_ant_cre_pte_anu","veh_cet_min_cre_pte_anu",});
                variables.add(new String[]{"Aplicacion", "veh_cet_apl_anu","veh_cet_cto_vta_anu","veh_cet_gav_anu","veh_cet_gas_ind_anu","veh_cet_adm_pry_anu","veh_cet_cto_fin_anu","veh_cet_pgo_cre_pte_anu"});
                variables.add(new String[]{"Movimiento Neto","veh_cet_mov_net_anu"});
                variables.add(new String[]{"Capital","veh_cet_cap_anu","veh_cet_apt_anu","veh_cet_ret_anu"});
                variables.add(new String[]{"Reparto de Utilidades","veh_cet_rep_uti_anu","veh_cet_rep_uti_lim_prt_anu","veh_cet_rep_uti_grl_prt_anu"});
                break;

            case TIpoReporte.REPORTE_PARAMETRICAS:
                variables.add(new String[]{"Parametros","veh_cet_uni_edf_anu", "veh_cet_uni_edf_acu_anu", "veh_cet_uni_dis_anu",
            "veh_cet_uni_dis_acu_anu", "veh_cet_uni_ven_anu","veh_cet_uni_ven_acu_anu",});
                break;

            case TIpoReporte.REPORTE_RATIOS:
                variables.add(new String[]{"Rentabilidad","veh_bal_roe", "veh_bal_roa", "veh_bal_ebi"});
                variables.add(new String[]{"Apalancamiento","veh_pal_ope", "veh_pal_apa"});
                variables.add(new String[]{"Liquidez","veh_liq_anu", "veh_cap_tra_anu"});
                variables.add(new String[]{"Operacion","veh_cob_deu", "veh_rot_inv", "veh_dia_cta_por_cob", "veh_dia_cta_por_pag", "veh_cob_ser_deu"});
                variables.add(new String[]{"Margenes","veh_mar_ope", "veh_mar_ant_imp", "veh_mar_net"});
                
                break;
        }
        HashMap<String, String> mapeoGraficas = generarMapeoGraficas();
        return sacarValoresReportes(variables,mapeoGraficas);
    }

    private List<ObjetoReporteable> sacarValoresReportes(List<String[]> variables, HashMap<String, String> mapeoGraficas) {
        List<ObjetoReporteable> lista=new LinkedList<ObjetoReporteable>();
        agregarEncabezadoAnios(lista);
        for(String[] arreglo:variables){
            ObjetoReporteable reportable=new ObjetoReporteable(null,arreglo[0], null, true);
            lista.add(reportable);
            for(int t=1;t<arreglo.length;t++){
                System.out.println(arreglo[t]);
                List<String> datos=Funciones.cambiarTipoArreglo((List<Double>) manejador.obtenerVariable(arreglo[t]));
                reportable=new ObjetoReporteable(datos, mapeoGraficas.get(arreglo[t]), null, false);
                lista.add(reportable);
            }
        }
        
        System.out.println(lista);
        return lista;
    }

    private void agregarEncabezadoAnios(List<ObjetoReporteable> lista) {
        List<String> valores=new LinkedList<String>();
        
        for(int t=0;t<5;t++){
            valores.add(String.valueOf(t+1));
        }
        ObjetoReporteable objeto=new ObjetoReporteable(valores,"  ","", false);
        lista.add(objeto);
    }

    /**
     * @return the tiempoMinistracion
     */
    public int getTiempoMinistracion() {
        return tiempoMinistracion;
    }

    /**
     * @param tiempoMinistracion the tiempoMinistracion to set
     */
    public void setTiempoMinistracion(int tiempoMinistracion) {
        this.tiempoMinistracion = tiempoMinistracion;
    }

    /**
     * @return the tiempoPago
     */
    public int getTiempoPago() {
        return tiempoPago;
    }

    /**
     * @param tiempoPago the tiempoPago to set
     */
    public void setTiempoPago(int tiempoPago) {
        this.tiempoPago = tiempoPago;
    }


}
