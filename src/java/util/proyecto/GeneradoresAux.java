/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.proyecto;

import variables.ManejadorVariablesProyectos;

/**
 *
 * @author daniel
 */
public class GeneradoresAux {

  /**
   * inputs de terrenos y escrituras
   */
  private final String PREDIAL_AGUA_FC = "pry_val_tot_con_for_cal";
  private final String ESCRUTURA_AVALUO_FC = "pry_esc_ava_fid_isr_for_cal";
  private final String ADMON_FIDEICOMISO_TOT = "pry_adm_fid_imp_tot";
  private double terrenosEsc[];
  private final int terreno = 3;
  /**
   * inputs de proyecto
   */
  private final String PROY_ARQUI_FORMA_FC = "pry_pro_arq_for_cal";
  private final String PROY_ARQUI_MON = "pry_pro_arq_mon";
  private final String DRO_FIRMAS_FC = "pry_dro_fir_for_cal";
  private final String CALC_EST_INST_FC = "pry_cal_est_ins_for_cal";
  private final String CALC_EST_INST_MON = "pry_cal_est_ins_mon";
  private final String MEC_SUE_TOP_FC = "pry_mec_sue_top_for_cal";
  private final String MEC_SUE_TOP_MON = "pry_mec_sue_top_mon";
  private double proyecto[];
  private final int pro = 7;
  /**
   * inputs de tramites, licencias y derechos
   */
  private final String GAS_VEC_TERCEROS_FC = "pry_ges_vec_ter_for_cal";
  private final String GAS_VEC_TERCEROS_MON = "pry_ges_vec_ter_mon";
  private final String TRAM_LIC_FC = "pry_tra_lic_for_cal";
  private final String TRAM_LIC_MON = "pry_tra_lic_mon";
  private final String CONS_VEC_FC = "pry_con_vec_for_cal";
  private final String CONS_VEC_MON = "pry_con_vec_mon";
  private final String POZO_AGUA_FC = "pry_poz_agu_for_cal";
  private final String POZO_AGUA_MON = "pry_poz_agu_mon";
  private final String PRE_LUZ_FUE_FC = "pry_sol_pre_luz_fue_for_cal";
  private final String PRE_LUZ_FUE_MON = "pry_sol_pre_luz_fue_mon";
  private final String REG_PROPIEDAD_CON_FC = "pry_reg_pro_con_for_cal";
  private final String REG_PROPIEDAD_CON_MON = "pry_reg_pro_con_mon";
  private double traLicDer[];
  private final int tra = 12;
  /**
   * inputs de ventas
   */
  private final String COMISIONES_VENTA_FC = "pry_com_ven_for_cal";
  private final String COMISION_VENTA_CONTRATO_FC = "pry_com_ven_con_for_cal";
  private final String COMISION_VENTA_TITULACION_FC = "pry_com_ven_tit_for_cal";
  private final String PUBLICIDAD_CORP_FC = "pry_pub_cor_for_cal";
  private final String PUBLICIDAD_PROMO_FC = "pry_pub_pro_une_for_cal";
  private final String ESCENARIO_MANTO_VIGILANCIA_FC = "pry_esc_pro_man_vig_for_cal";
  private final String AMUEBLADO_DECORADO_FC = "pry_amu_dec_dep_ofi_for_cal";
  private double ventas[];
  private final int ven = 7;
  /**
   * inputs de otros
   */
  private final String AMENIDADES_FC = "pry_ame_for_cal";
  private final String AMENIDADES_MON = "pry_ame_mon";
  private final String OTROS2_FC = "pry_otr_2_for_cal";
  private final String OTROS2_MON = "pry_otr_2_mon";
  private final String OTROS3_FC = "pry_otr_3_for_cal";
  private final String OTROS3_MON = "pry_otr_3_mon";
  private double otros[];
  private final int otro = 6;
  /**
   * inputs de socios y gastos de admon
   */
  private final String GTOS_OP_ADMON_UNE_FC = "pry_gas_ope_adm_une_for_cal";
  private final String GTOS_OP_ADMON_COR_FC = "pry_gas_ope_adm_cor_for_cal";
  private double socioAdmon[];
  private final int socio = 2;
  /**
   * inputs de credito puente
   */
  private final String CREDI_PUENTE_PORCENTAJE = "pry_cre_pue_sol_for_cal";
  private final String APERTURA_CREDI_PUENTE_PORCENTAJE = "pry_ape_cre_for_cal";
  private final String SEGUROS_FINANZAS_PORCENTAJE = "pry_seg_fin_gas_not_for_cal";
  private final String SUP_OBRAS_PORCENTAJE = "pry_sup_obr_adm_rec_for_cal";
  private double creditoPuente[];
  private final int credito = 4;
  /**
   * inputs de anticipo
   */
  private final String ANTICIPO_CREDI_PUENTE_FC = "pry_ant_cre_sol_for_cal";
  private final String DISPOSICION_RECURSOS_FC = "pry_dis_rec_cre_sol_for_cal";
  private final String AMORTIZACION_CREDITO_FC = "pry_amo_cre_cre_sol_for_cal";
  private double anticipo[];
  private final int anti = 3;
  /**
   * Objeto que manipula los elementos de la base
   */
  private ManejadorVariablesProyectos manejadorVariables;

  public GeneradoresAux(ManejadorVariablesProyectos manejadorVariables) {
    this.manejadorVariables = manejadorVariables;
    terrenosEsc = new double[terreno];
    consigueTerrenosEsc();

    proyecto = new double[pro];
    consigueProyecto();

    traLicDer = new double[tra];
    consigueTraLicDer();

    ventas = new double[ven];
    consigueVentas();

    otros = new double[otro];
    consigueOtros();

    socioAdmon = new double[socio];
    consigueSocioAdmon();

    creditoPuente = new double[credito];
    consigueCrediPuente();

    anticipo = new double[anti];
    consigueAnticipo();
  }

  private void consigueTerrenosEsc() {
    terrenosEsc[0] = (Double) manejadorVariables.obtenerVariable(PREDIAL_AGUA_FC);
    terrenosEsc[1] = (Double) manejadorVariables.obtenerVariable(ESCRUTURA_AVALUO_FC);
    terrenosEsc[2] = (Double) manejadorVariables.obtenerVariable(ADMON_FIDEICOMISO_TOT);
  }

  private void consigueProyecto() {
    proyecto[0] = (Double) manejadorVariables.obtenerVariable(PROY_ARQUI_FORMA_FC);
    proyecto[1] = (Double) manejadorVariables.obtenerVariable(PROY_ARQUI_MON);
    proyecto[2] = (Double) manejadorVariables.obtenerVariable(DRO_FIRMAS_FC);
    proyecto[3] = (Double) manejadorVariables.obtenerVariable(CALC_EST_INST_FC);
    proyecto[4] = (Double) manejadorVariables.obtenerVariable(CALC_EST_INST_MON);
    proyecto[5] = (Double) manejadorVariables.obtenerVariable(MEC_SUE_TOP_FC);
    proyecto[6] = (Double) manejadorVariables.obtenerVariable(MEC_SUE_TOP_MON);
  }

  private void consigueTraLicDer() {
    traLicDer[0] = (Double) manejadorVariables.obtenerVariable(GAS_VEC_TERCEROS_FC);
    traLicDer[1] = (Double) manejadorVariables.obtenerVariable(GAS_VEC_TERCEROS_MON);
    traLicDer[2] = (Double) manejadorVariables.obtenerVariable(TRAM_LIC_FC);
    traLicDer[3] = (Double) manejadorVariables.obtenerVariable(TRAM_LIC_MON);
    traLicDer[4] = (Double) manejadorVariables.obtenerVariable(CONS_VEC_FC);
    traLicDer[5] = (Double) manejadorVariables.obtenerVariable(CONS_VEC_MON);
    traLicDer[6] = (Double) manejadorVariables.obtenerVariable(POZO_AGUA_FC);
    traLicDer[7] = (Double) manejadorVariables.obtenerVariable(POZO_AGUA_MON);
    traLicDer[8] = (Double) manejadorVariables.obtenerVariable(PRE_LUZ_FUE_FC);
    traLicDer[9] = (Double) manejadorVariables.obtenerVariable(PRE_LUZ_FUE_MON);
    traLicDer[10] = (Double) manejadorVariables.obtenerVariable(REG_PROPIEDAD_CON_FC);
    traLicDer[11] = (Double) manejadorVariables.obtenerVariable(REG_PROPIEDAD_CON_MON);
  }

  private void consigueVentas() {
    ventas[0] = (Double) manejadorVariables.obtenerVariable(COMISIONES_VENTA_FC);
    ventas[1] = (Double) manejadorVariables.obtenerVariable(COMISION_VENTA_CONTRATO_FC);
    ventas[2] = (Double) manejadorVariables.obtenerVariable(COMISION_VENTA_TITULACION_FC);
    ventas[3] = (Double) manejadorVariables.obtenerVariable(PUBLICIDAD_CORP_FC);
    ventas[4] = (Double) manejadorVariables.obtenerVariable(PUBLICIDAD_PROMO_FC);
    ventas[5] = (Double) manejadorVariables.obtenerVariable(ESCENARIO_MANTO_VIGILANCIA_FC);
    ventas[6] = (Double) manejadorVariables.obtenerVariable(AMUEBLADO_DECORADO_FC);
  }

  private void consigueOtros() {
    otros[0] = (Double) manejadorVariables.obtenerVariable(AMENIDADES_FC);
    otros[1] = (Double) manejadorVariables.obtenerVariable(AMENIDADES_MON);
    otros[2] = (Double) manejadorVariables.obtenerVariable(OTROS2_FC);
    otros[3] = (Double) manejadorVariables.obtenerVariable(OTROS2_MON);
    otros[4] = (Double) manejadorVariables.obtenerVariable(OTROS3_FC);
    otros[5] = (Double) manejadorVariables.obtenerVariable(OTROS3_MON);
  }

  private void consigueSocioAdmon() {
    socioAdmon[0] = (Double) manejadorVariables.obtenerVariable(GTOS_OP_ADMON_UNE_FC);
    socioAdmon[1] = (Double) manejadorVariables.obtenerVariable(GTOS_OP_ADMON_COR_FC);
  }

  private void consigueCrediPuente() {
    creditoPuente[0] = (Double) manejadorVariables.obtenerVariable(CREDI_PUENTE_PORCENTAJE);
    creditoPuente[1] = (Double) manejadorVariables.obtenerVariable(APERTURA_CREDI_PUENTE_PORCENTAJE);
    creditoPuente[2] = (Double) manejadorVariables.obtenerVariable(SEGUROS_FINANZAS_PORCENTAJE);
    creditoPuente[3] = (Double) manejadorVariables.obtenerVariable(SUP_OBRAS_PORCENTAJE);
  }

  private void consigueAnticipo() {
    anticipo[0] = (Double) manejadorVariables.obtenerVariable(ANTICIPO_CREDI_PUENTE_FC);
    anticipo[1] = (Double) manejadorVariables.obtenerVariable(DISPOSICION_RECURSOS_FC);
    anticipo[2] = (Double) manejadorVariables.obtenerVariable(AMORTIZACION_CREDITO_FC);
  }

  /**
   * @return the terrenosEsc
   */
  public double[] getTerrenosEsc() {
    return terrenosEsc;
  }

  /**
   * @param terrenosEsc the terrenosEsc to set
   */
  public void setTerrenosEsc(double[] terrenosEsc) {
    this.terrenosEsc = terrenosEsc;
  }

  /**
   * @return the proyecto
   */
  public double[] getProyecto() {
    return proyecto;
  }

  /**
   * @param proyecto the proyecto to set
   */
  public void setProyecto(double[] proyecto) {
    this.proyecto = proyecto;
  }

  /**
   * @return the traLicDer
   */
  public double[] getTraLicDer() {
    return traLicDer;
  }

  /**
   * @param traLicDer the traLicDer to set
   */
  public void setTraLicDer(double[] traLicDer) {
    this.traLicDer = traLicDer;
  }

  /**
   * @return the ventas
   */
  public double[] getVentas() {
    return ventas;
  }

  /**
   * @param ventas the ventas to set
   */
  public void setVentas(double[] ventas) {
    this.ventas = ventas;
  }

  /**
   * @return the otros
   */
  public double[] getOtros() {
    return otros;
  }

  /**
   * @param otros the otros to set
   */
  public void setOtros(double[] otros) {
    this.otros = otros;
  }

  /**
   * @return the socioAdmon
   */
  public double[] getSocioAdmon() {
    return socioAdmon;
  }

  /**
   * @param socioAdmon the socioAdmon to set
   */
  public void setSocioAdmon(double[] socioAdmon) {
    this.socioAdmon = socioAdmon;
  }

  /**
   * @return the creditoPuente
   */
  public double[] getCreditoPuente() {
    return creditoPuente;
  }

  /**
   * @param creditoPuente the creditoPuente to set
   */
  public void setCreditoPuente(double[] creditoPuente) {
    this.creditoPuente = creditoPuente;
  }

  /**
   * @return the anticipo
   */
  public double[] getAnticipo() {
    return anticipo;
  }

  /**
   * @param anticipo the anticipo to set
   */
  public void setAnticipo(double[] anticipo) {
    this.anticipo = anticipo;
  }
}
