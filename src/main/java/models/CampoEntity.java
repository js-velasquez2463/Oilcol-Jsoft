 package models;
 
import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamStrategyValue;
 
 
/**
 *
 * @author Johan
 */
@Entity
public class CampoEntity implements Serializable {
 
    private static final long serialVersionUID = 1L;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
   
   /* @OneToMany(cascade=ALL, mappedBy="competitor")
    private Set<Producto> products;*/
 
    private String nombre;
 
    private String ciudad;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
 
    @OneToMany(cascade=ALL,  mappedBy="campo")
    private List<PozoEntity> pozos= new ArrayList<PozoEntity>();

   
 
    public CampoEntity() {
         //pozos= new ArrayList<PozoEntity>();
                  
    }
 
    public CampoEntity(String nameN, String surnameN, int ageN, String telephoneN, String cellphoneN, String addressN, String cityN, String countryN, boolean winnerN, String username, String password) {
        nombre = nameN;
        ciudad = surnameN;
       
    }
 
    /*public PozoEntity agregarPozo(String nameN){
          if(pozos==null)pozos=(List<PozoEntity>) new ArrayList<PozoEntity>();
         PozoEntity pozo=new PozoEntity();
         pozo.setNombre(nameN);
         pozos.add(pozo);
         return pozo;
    }*/
    
    
    public Date getFecha(){
        return this.fecha;
    }
    
    public void setFecha(Date nueva){
        this.fecha=nueva;
    }
    
    public void iniciarFecha(){
        this.fecha=new Date();
    }
            
     public List<PozoEntity> getPozos() {
        return pozos;
    }

    public void setPozos(List<PozoEntity> pozos) {
        this.pozos = pozos;
    }
    
    public PozoEntity getPozo(int i) {
        return this.pozos.get(i);
    }
    
    public void setPozo(int i,PozoEntity pozo) {
        this.pozos.set(i,pozo);
    }
    
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    public String getCiudad() {
        return ciudad;
    }
 
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
 
    
   
}