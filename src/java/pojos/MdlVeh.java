package pojos;
// Generated 29-mar-2012 18:27:14 by Hibernate Tools 3.2.1.GA


import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * MdlVeh generated by hbm2java
 */
public class MdlVeh  implements java.io.Serializable {


     private Integer id;
     private Usu usu;
     private String nomMdl;
     private Calendar fec;
     private int mesMin;
     private int mesPag;
     private Set mapCalVehs = new HashSet(0);
     private Set usuMdlVehs = new HashSet(0);
     private Set relMdlVehs = new HashSet(0);

    public MdlVeh() {
    }

    public MdlVeh(Integer id, Usu usu, String nomMdl, Calendar fec, int mesMin, int mesPag) {
        this.id = id;
        this.usu = usu;
        this.nomMdl = nomMdl;
        this.fec = fec;
        this.mesMin = mesMin;
        this.mesPag = mesPag;
    }

    
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Usu getUsu() {
        return this.usu;
    }
    
    public void setUsu(Usu usu) {
        this.usu = usu;
    }
    public String getNomMdl() {
        return this.nomMdl;
    }
    
    public void setNomMdl(String nomMdl) {
        this.nomMdl = nomMdl;
    }
    public Calendar getFec() {
        return this.fec;
    }
    
    public void setFec(Calendar fec) {
        this.fec = fec;
    }
    public Set getMapCalVehs() {
        return this.mapCalVehs;
    }
    
    public void setMapCalVehs(Set mapCalVehs) {
        this.mapCalVehs = mapCalVehs;
    }
    public Set getUsuMdlVehs() {
        return this.usuMdlVehs;
    }
    
    public void setUsuMdlVehs(Set usuMdlVehs) {
        this.usuMdlVehs = usuMdlVehs;
    }
    public Set getRelMdlVehs() {
        return this.relMdlVehs;
    }
    
    public void setRelMdlVehs(Set relMdlVehs) {
        this.relMdlVehs = relMdlVehs;
    }

    /**
     * @return the mesMin
     */
    public int getMesMin() {
        return mesMin;
    }

    /**
     * @param mesMin the mesMin to set
     */
    public void setMesMin(int mesMin) {
        this.mesMin = mesMin;
    }

    /**
     * @return the mesPag
     */
    public int getMesPag() {
        return mesPag;
    }

    /**
     * @param mesPag the mesPag to set
     */
    public void setMesPag(int mesPag) {
        this.mesPag = mesPag;
    }




}


