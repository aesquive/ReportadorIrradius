package pojos;
// Generated 29-mar-2012 18:27:14 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * VarPryInd generated by hbm2java
 */
public class VarPryInd  implements java.io.Serializable {


     private Integer id;
     private String desTca;
     private String desLar;
     private Set datPryInds = new HashSet(0);
     private Set etpPryInds = new HashSet(0);

    public VarPryInd() {
    }

    public VarPryInd(String desTca, String desLar, Set datPryInds, Set etpPryInds) {
       this.desTca = desTca;
       this.desLar = desLar;
       this.datPryInds = datPryInds;
       this.etpPryInds = etpPryInds;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDesTca() {
        return this.desTca;
    }
    
    public void setDesTca(String desTca) {
        this.desTca = desTca;
    }
    public String getDesLar() {
        return this.desLar;
    }
    
    public void setDesLar(String desLar) {
        this.desLar = desLar;
    }
    public Set getDatPryInds() {
        return this.datPryInds;
    }
    
    public void setDatPryInds(Set datPryInds) {
        this.datPryInds = datPryInds;
    }
    public Set getEtpPryInds() {
        return this.etpPryInds;
    }
    
    public void setEtpPryInds(Set etpPryInds) {
        this.etpPryInds = etpPryInds;
    }




}


