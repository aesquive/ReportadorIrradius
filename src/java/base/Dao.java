package base;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojos.EstPryInd;
import pojos.EstPryVeh;
import pojos.MdlPryInd;
import pojos.MdlVeh;
import pojos.PryPryInd;
import pojos.PryVeh;
import pojos.RepVeh;
import pojos.TipMatPryInd;
import pojos.Usu;
import pojos.UsuPryPryInd;
import pojos.UsuPryVeh;
import pojos.VarVeh;

/**
 * 29/12/2011
 * Clase que interactua con la base de datos  
 * @author Alberto Emmanuel Esquivel Vega
 */
public class Dao {

    /**
     * sesion en base de datos , estatica para solo mantener una sesion
     */
    protected static Session sesion;

    /**
     * @return the sesion
     */
    public static Session getSesion() {
        return sesion;
    }

    public Dao() {
        sesion = sesion==null ? HibernateUtil.getSessionFactory().openSession() : sesion;
    }

    /**
     * regresa el contenido en una tabla de la base en forma de lista
     * @param tabla , tabla deseada
     * @return 
     */
    public List getTabla(Class tabla) {
        List list = getSesion().createCriteria(tabla).list();
        return list;
    }

    /**
     * guarda el objeto en base de datos y regresa el id con el que se guardo
     * @param objeto a guardar en base
     * @return 
     */
    public void guardar(Object objeto) {
        Transaction transaction = getSesion().beginTransaction();
        getSesion().save(objeto);
        transaction.commit();
    }

    /**
     * reinicia la sesion del dao
     */
    public void reiniciarSesion() {
        sesion.close();
        sesion = HibernateUtil.getSessionFactory().openSession();

    }

    /**
     * actualiza un objeto en base de datos 
     * @param objeto a actualizar
     */
    public void actualizar(Object objeto) {
        Transaction transaction = getSesion().beginTransaction();
        getSesion().saveOrUpdate(objeto);
        transaction.commit();

    }

    public static void main(String[] args) {

        Dao dao = new Dao();
//        RepVeh ultimoReporte = dao.getUltimoReporte();
//        System.out.println(ultimoReporte.getMdlVeh());
    }


    private List<PryVeh> buscarTodosProyectos() {

        return getTabla(PryVeh.class);
    }

    public EstPryVeh getEstatusProyecto(int estatus) {
        EstPryVeh est = (EstPryVeh) sesion.createCriteria(EstPryVeh.class).add(
                Restrictions.eq("id", estatus)).uniqueResult();

        return est;
    }

    public EstPryInd getEstatusProyectoInd(int estatus) {
        EstPryInd est = (EstPryInd) sesion.createCriteria(EstPryInd.class).add(
                Restrictions.eq("id", estatus)).uniqueResult();

        return est;
    }

    public void remove(Object proyectoSeleccionado) {
        Transaction transaction = getSesion().beginTransaction();
        getSesion().delete(proyectoSeleccionado);
        transaction.commit();

    }

    public PryVeh getProyectoVehiculo(int id) {
        PryVeh pry = (PryVeh) sesion.createCriteria(PryVeh.class).add(Restrictions.eq("id", id)).uniqueResult();
        return pry;
    }

    public List<MdlVeh> getModelos(Usu usu) {
        Usu usuario=(Usu) sesion.createCriteria(Usu.class).add(Restrictions.eq("id", usu.getId())).uniqueResult();
        Set<MdlVeh> mdlVehs = usuario.getMdlVehs();
        List<MdlVeh> modelos = new LinkedList<MdlVeh>();
        for (MdlVeh m : mdlVehs) {
            modelos.add(m);
        }
        return modelos;
    }

    public TipMatPryInd obtenerTipoMatriz(String att) {
        return (TipMatPryInd) sesion.createCriteria(TipMatPryInd.class).add(Restrictions.eq("desTipMat", att)).uniqueResult();
    }

    public PryPryInd getPryInd(int i) {
        return (PryPryInd) sesion.createCriteria(PryPryInd.class).add(Restrictions.eq("id", i)).uniqueResult();
    }

    public MdlVeh buscarUltimoModelo() {
        List list = sesion.createCriteria(MdlVeh.class).list();
        return (MdlVeh) list.get(list.size() - 1);
    }

    public List<PryPryInd> getProyectosIndividual(Usu usuario) {
        Set<UsuPryPryInd> proyectosUsuario = usuario.getUsuPryPryInds();
        if (usuario.getTipUsu().getId() == 3) {
            return buscarTodosProyectosInd();
        }
        List<PryPryInd> proyectos = new LinkedList<PryPryInd>();
        for (UsuPryPryInd proyectoUsuario : proyectosUsuario) {
            PryPryInd pryInd = proyectoUsuario.getPryPryInd();
            if (pryInd.getEstPryInd() != null && pryInd.getEstPryInd().getId() != 2) {
                proyectos.add(pryInd);
            }
        }
        return proyectos;
    }

    private List<PryPryInd> buscarTodosProyectosInd() {
        return getTabla(PryPryInd.class);
    }

    public List<MdlPryInd> getModelosPryInd(Usu usuario) {
        Set<MdlPryInd> mdlPryInd = usuario.getMdlPryInds();
        List<MdlPryInd> proyectos = new LinkedList<MdlPryInd>();
        for (MdlPryInd m : mdlPryInd) {
            proyectos.add(m);
        }
        return proyectos;
    }

    public PryVeh getPryVeh(int proyectoActual) {
        return (PryVeh) sesion.createCriteria(PryVeh.class).add(Restrictions.eq("id", proyectoActual)).uniqueResult();
    }

    

    public MdlVeh getMdlVeh(Integer mdlVeh) {
        return (MdlVeh) sesion.createCriteria(MdlVeh.class).add(Restrictions.eq("id", mdlVeh)).uniqueResult();
    }

}
