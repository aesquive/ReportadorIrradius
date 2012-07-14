package reportes;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import util.Funciones;
import util.reportes.ObjetoReporteable;

/**
 * Clase que se encarga de reportar los resultados obtenidos
 * durante los calculos de vehiculo.
 * @author Alberto Esquivel
 * @author Galindo Martinez Jose Cruz
 * @author Salazar Sastre Martin
 * @version 1.0.3
 */
public class Reporteador {

  private String nombreArchivo;
  private List<ObjetoReporteable> listaReporteables;
  private int tipo;
  private int renglon = 7;
  private int maximo = 0;
  private final int REPORTE_EDO_RESULTADOS = 0;
  private final int REPORTE_BALANCE = 1;
  private final int REPORTE_CAJA = 2;
  private final int REPORTE_RATIOS = 3;
  private final int REPORTE_PARAMETRICAS = 4;
  private static String sufijo = "/home/alberto/apache-tomcat-7.0.14/webapps/ReportadorIrradius/";
  private final String LOGO_IRRADIUS = sufijo+"irradiusLogo.png";
  private final String LOGO_QUANTS = sufijo+"logoQuants.png";
  private final String LOGO_JUNTOS = sufijo+"logo_PDF.png";

  /**
   * Crea un objeto que se encarga de reportar los resultados obtenidos 
   * en los calculos de vehiculo.
   * @param nombreArchivo El nombre de los archivos de salida.
   * @param listaReporteables Los datos que se van a escribir en los documentos.
   * @param tipo El tipo de documento del que proviene.
   */
  public Reporteador(String nombreArchivo,
          List<ObjetoReporteable> listaReporteables, int tipo) {
    this.tipo = tipo;
    this.nombreArchivo = Funciones.removeCar(nombreArchivo);
    this.listaReporteables = listaReporteables;
  }

  /**
   * Se encarga de generar los reportes en Excel y PDF.
   * @return EL arreglo de todas las rutas donde guardamos los documentos.
   */
  public String[] reportar() {
    String[] salida = new String[2];
    String xls=convertirXLS();
    String pdf=convertirPDF();
        String[] arrXls = xls.split("/");
        String[] arrPdf=pdf.split("/"); 
    salida[0] = arrXls[arrXls.length-2]+"/"+arrXls[arrXls.length-1];
    salida[1] = arrPdf[arrPdf.length-2]+"/"+arrPdf[arrPdf.length-1];
    return salida;
  }//end reportar

  /**
   * Se encarga de convertir los documentos dados los datos de entrada
   * en un formato XLS
   * @return La ruta de donde se guarda el archivo.
   */
  private String convertirXLS() {
    String salidaXLS = creaNombreXLS();
    WritableWorkbook workbook = null;
    WritableSheet hoja = null;
    try {
      System.out.println("la ruta es " + salidaXLS);
      File archivo = new File(salidaXLS);
      workbook = Workbook.createWorkbook(archivo);
      hoja = workbook.createSheet("Hoja 1", 0);
      WritableImage imagenIrradius = new WritableImage(0, 1, 1, 4, new File(LOGO_IRRADIUS));
      WritableImage imagenQuants = new WritableImage(3, 1, 2, 4, new File(LOGO_QUANTS));
      hoja.addImage(imagenIrradius);
      hoja.addImage(imagenQuants);
      //hoja.setColumnView(0, 35);
      hoja.setColumnView(1, 15);
      hoja.setColumnView(2, 15);
      hoja.setColumnView(3, 15);
      hoja.setColumnView(4, 15);
    } catch (IOException ex) {
      Logger.getLogger(Reporteador.class.getName()).log(Level.SEVERE, null, ex);
    }
    for (ObjetoReporteable reportable : listaReporteables) {
      boolean esTitulo = reportable.isEstilo();
      if (esTitulo) {
        escribeTitulosXLS(reportable, hoja);
      } else {
        escribeDatosXLS(reportable, hoja);
      }
    }
    try {
      workbook.write();
      workbook.close();
    } catch (IOException ex) {
      Logger.getLogger(Reporteador.class.getName()).log(Level.SEVERE, null, ex);
    } catch (WriteException ex) {
      Logger.getLogger(Reporteador.class.getName()).log(Level.SEVERE, null, ex);
    }
    return salidaXLS;
  }

  /**
   *Se encarga de escribir el titulo de los Datos.
   * @param reportable El objeto del cual vamos a extraer los titulos
   * @param hoja La hoja del documento en la cual se va a escribir.
   */
  private void escribeTitulosXLS(ObjetoReporteable reportable, WritableSheet hoja) {
    String nombre = reportable.getNombre();
    WritableFont fuente = new WritableFont(WritableFont.TIMES, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.RED);
    WritableCellFormat celdaFormato = new WritableCellFormat(fuente);
    //tratamos de ajustar el tamano de la tabla
    Label titulo = new Label(0, renglon, nombre, celdaFormato);
    CellView celda = hoja.getColumnView(0);
    celda.setAutosize(true);
    hoja.setColumnView(0, celda);
    try {
      hoja.addCell(titulo);
    } catch (WriteException we) {
      System.err.println("Error al escribir");
    }
    renglon++;
    //String urlGrafica = reportable.getUrlGrafica();
  }

  /**
   * Se encarga de escribir los datos de un objeto a un documento xml
   * @param reportable El objeto del cual se van a extraer los datos.
   * @param hoja La hoja en la cual vamos a escribir los datos.
   */
  private void escribeDatosXLS(ObjetoReporteable reportable, WritableSheet hoja) {
    String nombre = reportable.getNombre();
    int numeCaracteres = nombre.length();
    maximo = (maximo >= numeCaracteres) ? maximo : numeCaracteres;
    List<String> valores = reportable.getValores();
    String urlGrafica = reportable.getUrlGrafica();
    WritableFont fontTitle = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false);
    WritableCellFormat fc = new WritableCellFormat(fontTitle);
    Label celdaNombre = new Label(0, renglon, nombre, fc);
    System.out.println("este es el nombre");
    try {
      hoja.addCell(celdaNombre);
    } catch (WriteException we) {
      System.err.println("Error al escribir");
    }
    Label etiValor = null;
    String valor;
    WritableFont font = new WritableFont(WritableFont.ARIAL);
    WritableCellFormat wcf = new WritableCellFormat(font);
    try {
      wcf.setBackground(Colour.GREY_25_PERCENT);
    } catch (WriteException we) {
      //algo paso
    }

    for (int i = 0; i < valores.size(); i++) {
      valor = valores.get(i);
      //etiValor = new Label(i, renglon, valor);
      try {
        if (i % 2 != 0) {
          etiValor = new Label(i+1, renglon, valor);
        } else {
          etiValor = new Label(i+1, renglon, valor);
        }
        hoja.addCell(etiValor);
      } catch (WriteException we) {
        System.err.println("Error al escribir");
      }
    }
    renglon++;
    //adjuntarGrafica(urlGrafica, hoja);
  }

  /**
   * Se encarga de convertir los datos de entrada
   * en un formato PDF.
   * @return La ruta de donde guardo el Documento.
   */
  private String convertirPDF() {
    String salidaPDF = creaNombrePDF();
    Document documento = new Document();
    FileOutputStream fos = null;

    //Creamos el documento de salida
    try {
      fos = new FileOutputStream(salidaPDF);
    } catch (FileNotFoundException fnfe) {
      System.err.println("El archivo no se encontro");
    }
    //lo pasamos al escritor de PDF's
    try {
      PdfWriter.getInstance(documento, fos);
    } catch (DocumentException de) {
      System.err.println("Error al crear el documento");
    }
    documento.open();
    Image logos = null;
    Paragraph blanco = new Paragraph("\n");
    try {
      logos = Image.getInstance(LOGO_JUNTOS);
      logos.scaleAbsolute(400f, 95f);
      logos.setAlignment(Image.ALIGN_MIDDLE);
    } catch (BadElementException bee) {
      //mensaje de error
    } catch (MalformedURLException murle) {
      //mensaje de error
    } catch (IOException ioe) {
      //emsaje de error
    }
    String formato = "dd/MMMM/yyyy HH:mm:ss a";
    Calendar fecha = Calendar.getInstance();
    SimpleDateFormat formatoFecha = new SimpleDateFormat(formato);
    String format = formatoFecha.format(fecha.getTime());

    //agregamos la imagen al documento
    // y lo demas
    try {
      documento.add(logos);
      //documento.add(blanco);
      System.out.println("El tamano de la lista " + listaReporteables.size());
      int contador = 0;
      for (ObjetoReporteable objReportable : listaReporteables) {
        boolean esTitulo = objReportable.isEstilo();
        System.out.println("El elemento da " + esTitulo + " para titulo");
        if (esTitulo) {
          escribirTitulosPDF(documento, objReportable);
          contador++;
        } else {
          if (contador == 1) {
            escribirConColor(documento, objReportable);
            contador++;
          } else {
            System.out.println("Si entra a escribir los datos");
            escribirDatosPDF(documento, objReportable);
            contador++;
          }
        }
      }
      String creation = "Fecha de creacion: " + format;
      Chunk positionDate = new Chunk(creation, new Font(Font.FontFamily.TIMES_ROMAN, 8));
      positionDate.setTextRise(-600f);
      documento.add(positionDate);
    } catch (DocumentException de) {
      System.err.println("Hubo un error al tratar de escribir el archivo");
    }
    documento.close();

    return salidaPDF;
  }

  /**
   * Se encarga de escribir los titolos de los datos en entrada en 
   * una forma visualmente agradable.
   * @param doc El documento en el cual se va a escribir.
   * @param reportable El objeto que contiene los datos de entrada.
   */
  private void escribirTitulosPDF(Document doc, ObjetoReporteable reportable) {
    String nombre = reportable.getNombre();
    Font fuente = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    Phrase titulo = new Phrase(nombre, fuente);
    try {
      doc.add(titulo);
      titulo = new Phrase("\n");
      doc.add(titulo);
      doc.add(Chunk.NEWLINE);
      doc.add(new LineSeparator());
    } catch (DocumentException de) {
      System.err.println("El Documento tuvo errores al guardarse");
    }
  }

  /**
   * Se encarga de poner un color agradable a los datos que se le pasen, los
   * datos de entrada no pueden ser titulos.
   * @param doc El documento en el cual se va a escribir.
   * @param reportable El objeto que contiene los datos de entrada.
   */
  private void escribirConColor(Document doc, ObjetoReporteable reportable) {
    BaseColor color = new BaseColor(159, 182, 205);
    String nombre = reportable.getNombre();
    List<String> valores = reportable.getValores();
    int tamano = valores.size();
    float[] relativeWidths = new float[tamano + 1];
    relativeWidths[0] = 0.9f;
    //le damos tamano a las celdas
    for (int i = 1; i < tamano + 1; i++) {
      relativeWidths[i] = 0.4f;
    }
    PdfPTable tabla = new PdfPTable(relativeWidths);
    Chunk tituloC = new Chunk(nombre);
    tituloC.setFont(new Font(Font.FontFamily.HELVETICA, 11));
    Paragraph title = new Paragraph(tituloC);
    tabla.setWidthPercentage(100);
    PdfPCell nueCell = new PdfPCell(title);
    nueCell.setBackgroundColor(color);
    nueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
    nueCell.setBorder(60);
    nueCell.setBorder(Rectangle.NO_BORDER);
    tabla.addCell(nueCell);
    Paragraph parraf = null;
    PdfPCell celdaValor = null;
    Chunk form = null;
    for (String c : valores) {
      form = new Chunk(c);
      form.setFont(new Font(Font.FontFamily.HELVETICA, 10));
      parraf = new Paragraph(form);
      celdaValor = new PdfPCell(parraf);
      celdaValor.setBackgroundColor(color);
      celdaValor.setBorder(Rectangle.NO_BORDER);
      celdaValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
      tabla.addCell(celdaValor);
    }
    try {
      doc.add(tabla);
    } catch (DocumentException de) {
      System.err.println("El Documento tuvo errores al guardarse");
    }
  }

  /**
   * Se encarga de escribir los datos obtenidos al documento acomodandolos 
   * en una matriz.
   * @param doc El documento en el cual se va a escribir.
   * @param reportable El objeto que contiene los datos de entrada.
   */
  private void escribirDatosPDF(Document doc, ObjetoReporteable reportable) {
    String nombre = reportable.getNombre();
    List<String> valores = reportable.getValores();
    int tamano = valores.size();
    //creamos la tabla
    float[] relativeWidths = new float[tamano + 1];
    relativeWidths[0] = 0.9f;
    for (int i = 1; i < tamano + 1; i++) {
      relativeWidths[i] = 0.4f;
    }
    PdfPTable tabla = new PdfPTable(relativeWidths);
    Chunk tituloC = new Chunk(nombre);
    //le damos formato al nombre
    tituloC.setFont(new Font(Font.FontFamily.HELVETICA, 11));
    Paragraph title = new Paragraph(tituloC);
    tabla.setWidthPercentage(100);
    PdfPCell nueCell = new PdfPCell(title);
    nueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
    nueCell.setBorder(60);
    nueCell.setBorder(Rectangle.NO_BORDER);
    tabla.addCell(nueCell);
    Paragraph parraf = null;
    PdfPCell celdaValor = null;
    Chunk form = null;
    for (String c : valores) {
      form = new Chunk(c);
      form.setFont(new Font(Font.FontFamily.HELVETICA, 10));
      parraf = new Paragraph(form);
      celdaValor = new PdfPCell(parraf);
      celdaValor.setBorder(Rectangle.NO_BORDER);
      celdaValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
      tabla.addCell(celdaValor);
    }
    try {
      doc.add(tabla);
    } catch (DocumentException de) {
      System.err.println("El Documento tuvo errores al guardarse");
    }
  }

  /*****/
  private void adjuntarGrafica(String urlGrafica, WritableSheet hoja) {
    WritableImage grafica = new WritableImage(3, renglon, 15, 15, new File(urlGrafica));
    hoja.addImage(grafica);
    renglon += 16;
  }

//  public static void main(String[] args) {
//    LinkedList<String> lista = new LinkedList<String>();
//    for (int i = 0; i < 7; i++) {
//      long random = (long) (Math.random() * 100000 + 1);
//      String scad = String.valueOf(random);
//      lista.add(scad);
//    }
//    LinkedList<String> la = new LinkedList<String>();
//    la.add("0");
//    la.add("1");
//    la.add("2");
//    la.add("3");
//    la.add("4");
//    la.add("5");
//    la.add("6");
//
//    ObjetoReporteable or2 = new ObjetoReporteable(null, "Titulo", "", true);
//    ObjetoReporteable or4 = new ObjetoReporteable(la, "Nombres", "", false);
//    ObjetoReporteable or = new ObjetoReporteable(lista, "12345678912345", "", false);
//    ObjetoReporteable or3 = new ObjetoReporteable(lista, "Otra prueba ", "", false);
//    LinkedList<ObjetoReporteable> obrl = new LinkedList<ObjetoReporteable>();
//
//    obrl.add(or2);
//    obrl.add(or4);
//    obrl.add(or);
//    obrl.add(or3);
//
//    Reporteador r = new Reporteador("prueba03", obrl, 2);
//    r.reportar();
//    //r.creaNombrePDF();
//    //r.convertirPDF();
//  }

  /**
   * Dado el tipo de documento que estamos manejando se encarga de crear
   * el nombre que tendra el documento.
   * @return El nombre del documento dependiendo del tipo.
   */
  private String creaNombreXLS() {
    String ruta = sufijo;
    ruta += "Reportes/";
    switch (tipo) {
      case REPORTE_EDO_RESULTADOS:
        ruta += nombreArchivo + "edo" + ".xls";
        return ruta;
      case REPORTE_BALANCE:
        ruta += nombreArchivo + "bal" + ".xls";
        return ruta;
      case REPORTE_CAJA:
        ruta += nombreArchivo + "cja" + ".xls";
        return ruta;
      case REPORTE_RATIOS:
        ruta += nombreArchivo + "rat" + ".xls";
        return ruta;
      case REPORTE_PARAMETRICAS:
        ruta += nombreArchivo + "par" + ".xls";
        return ruta;
    }
    return null;
  }

  /**
   * Dado el tipo de documento que estamos manejando se encarga de crear
   * el nombre que tendra el documento.
   * @return El nombre del documento dependiendo del tipo.
   */
  private String creaNombrePDF() {
    String ruta = sufijo;
    ruta += "Reportes/";
    System.out.println(ruta);
    switch (tipo) {
      case REPORTE_EDO_RESULTADOS:
        ruta += nombreArchivo + "edo" + ".pdf";
        return ruta;
      case REPORTE_BALANCE:
        ruta += nombreArchivo + "bal" + ".pdf";
        return ruta;
      case REPORTE_CAJA:
        ruta += nombreArchivo + "cja" + ".pdf";
        return ruta;
      case REPORTE_RATIOS:
        ruta += nombreArchivo + "rat" + ".pdf";
        return ruta;
      case REPORTE_PARAMETRICAS:
        ruta += nombreArchivo + "par" + ".pdf";
        return ruta;
    }
    return null;
  }
}//end reportes

