package pojos;
// Generated 29-mar-2012 18:27:14 by Hibernate Tools 3.2.1.GA



/**
 * UsuPryPryInd generated by hbm2java
 */
public class UsuPryPryInd  implements java.io.Serializable {


     private Integer id;
     private PryPryInd pryPryInd;
     private Usu usu;

    public UsuPryPryInd() {
    }

    public UsuPryPryInd(PryPryInd pryPryInd, Usu usu) {
       this.pryPryInd = pryPryInd;
       this.usu = usu;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public PryPryInd getPryPryInd() {
        return this.pryPryInd;
    }
    
    public void setPryPryInd(PryPryInd pryPryInd) {
        this.pryPryInd = pryPryInd;
    }
    public Usu getUsu() {
        return this.usu;
    }
    
    public void setUsu(Usu usu) {
        this.usu = usu;
    }




}

