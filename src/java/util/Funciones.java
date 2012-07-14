/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import base.Dao;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import variables.ManejadorVariablesProyectos;

/**
 *
 * @author Alberto
 */
public class Funciones {

    public static String DateToString(Object fecha) {

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String string = format.format(fecha);
        return string;
    }

    public static String removeCar(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }//remove1

    public static Date StringToDate(String fecha) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date date = format.parse(fecha);
            return date;
        } catch (ParseException ex) {
            Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String castearMatrizMes(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMMM-yyyy");
        String format = sdf.format(time);
        return format;
    }

    public static int diferenciaMeses(Date fechaInicial, Date fechaFinal) {
        int mesInicial = fechaInicial.getMonth();
        int mesFinal = fechaFinal.getMonth();
        int anioInicial = fechaInicial.getYear();
        int anioFinal = fechaFinal.getYear();
        int contador = 1;
        while ((mesInicial != mesFinal) || (anioInicial != anioFinal)) {
            int aux = (mesInicial + 1);
            mesInicial = aux % 12;
            if (aux > 11) {
                anioInicial++;
            }
            contador++;
        }
        return contador;
    }

//    public static double redondear(double numero) {
//        Double copia = new Double(numero);
//        String numeroString = copia.toString();
//        String[] split = numeroString.split("\\.");
//        double izquierda=Double.parseDouble(split[0]);
//        boolean sumar=false;
//        if (split.length == 2) {
//            int derecha=Integer.parseInt(String.valueOf(split[1].charAt(0)));
//            if(derecha>=5){
//                sumar=true;
//            }
//        }
//        
//        if(sumar){
//            izquierda+=1.0;
//        }
//        return izquierda;
//    }
    public static double redondearDecimales(double numero, int decimales) {
        DecimalFormat num = new DecimalFormat(".###");
        String format = num.format(numero);
        String replace = format.replace(",", ".");
        
        return Double.parseDouble(replace);
            
    }

    public static String redondear(String numero, int decimales) {
        BigDecimal big = new BigDecimal(numero);
        big = big.setScale(decimales, RoundingMode.HALF_UP);
        return big.toString();
    }

    static Date StringToDateMatriz(String fecha) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMMM-yyyy");
            return sdf.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static double autoSumar(MatrizBidimensional matrizCostoVentas) {
        double suma = 0;
        for (CeldaFechaValor c : matrizCostoVentas.getCeldas()) {
            suma += c.getValor();
        }
        return suma;
    }

    public static String ponerComasCantidades(double round) {
        String[] split=String.valueOf(round).split("\\.");
        DecimalFormat format = new DecimalFormat("###,###,###,###,###.###");
        String format1 = format.format(Double.valueOf(split[0]));
        return format1.replace('.', ',')+"."+split[1];
    }

    public static String cambiarPorcentajes(String val) {
        val = val.replace("%", "");
        String[] split = val.split("\\.");
        boolean sigue = true;
        while (sigue) {
            if (split[0].charAt(0) == '0') {
                split[0] = split[0].substring(1, split[0].length());
            } else {
                sigue = false;
            }
        }
        if (split.length == 2) {

            sigue = true;
            while (sigue) {
                if (split[1].length() > 0 && split[1].charAt(split[1].length() - 1) == '0') {
                    split[1] = split[1].substring(0, split[1].length() - 1);
                } else {
                    sigue = false;
                }
            }
            return split[1].length() > 0 ? split[0] + "." + split[1] + "%" : split[0] + "%";
        }
        return split[0] + "%";
    }

    public static List<String> cambiarTipoArreglo(List list) {
        List<String> nueva = new LinkedList<String>();
        for (Object o : list) {
            Double d = Double.parseDouble(redondear(o.toString(), 2));
            if (d >= 1000) {

                nueva.add(Funciones.ponerComasCantidades(d));
            }else{
                nueva.add(String.valueOf(d));
            }
        }
        return nueva;
    }
    
    public static void main(String[] args) {
        String ponerComasCantidades = Funciones.ponerComasCantidades(1895.5);
        System.out.println(ponerComasCantidades);
    }
}
