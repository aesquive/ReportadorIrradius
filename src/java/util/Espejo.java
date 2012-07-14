package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Clase que hace reflexion sobre cualquier objeto
 * @author Alberto
 */
public class Espejo {
    
    public  static Method getMetodo(Class clase,String nombre){
        Method[] methods = clase.getMethods();
        for(Method m :methods){
            if(m.getName().contains(nombre)){
                return m;
            }
        }
        return null;
    }
    
    
    public  static Method getMetodoExacto(Class clase,String nombre){
        Method[] methods = clase.getMethods();
        for(Method m :methods){
            if(m.getName().equals(nombre)){
                return m;
            }
        }
        return null;
    }
    
    public static List<Method> getGetters(Class clase){
        Method[] methods= clase.getDeclaredMethods();
        List<Method> metodos=new LinkedList<Method>();
        for(Method m :methods){
            if(m.getName().contains("get")){
                metodos.add(m);
            }
        }
        return metodos;
    }
    
    public static Object invocarGetter(Object target,Method getter){
        
        try {
            return getter.invoke(target,null);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    

    public static void invocaSetterVehiculoLista(String nombre, Object target, List<MatrizBidimensional> val) {
        try {
            System.out.println("nombresinsissisisisisisin "+nombre);
            Method metodo = Espejo.getMetodo(target.getClass(), "set"+nombre);
            metodo.invoke(target, val);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void invocarSetterMatriz(String nombreMetodo, Object target, MatrizBidimensional matriz) {
        try {
            Method metodo=Espejo.getMetodo(target.getClass(), "set"+nombreMetodo);
            metodo.invoke(target, matriz);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    
    public static void invocarSetterString(Eneada actual, String metodo, String obtenerValorDistribuidor) {
        try {
            Method metodo1 = Espejo.getMetodo(actual.getClass(), metodo);
            metodo1.invoke(actual, obtenerValorDistribuidor);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static void invocaSetterProyectoCadena(String nombre, Object target, String cadena) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    try {
      Method metodo = Espejo.getMetodo(target.getClass(), "set" + nombre);
      metodo.invoke(target, cadena);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalArgumentException ex) {
      Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InvocationTargetException ex) {
      Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public static void invocaSetterProyectoLista(String nombre, Object target, LinkedList lista) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    try {
      Method metodo = Espejo.getMetodoExacto(target.getClass(), "set" + nombre);
      metodo.invoke(target, lista);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalArgumentException ex) {
      Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InvocationTargetException ex) {
      Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

    public static void invocarSetterDouble(Object target , String nombre, int valor) {
        try {
            Method metodo = Espejo.getMetodo(target.getClass(), nombre);
          metodo.invoke(target, valor);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Espejo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
