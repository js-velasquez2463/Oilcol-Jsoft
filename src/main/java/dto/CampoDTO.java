
package dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.PozoEntity;





/**
 *
 * @author Johan
 */
public class CampoDTO {
 
    private Long id;

    private String nombre;
    
    private String ciudad;
        
    private List<PozoDTO> pozos=new ArrayList<PozoDTO>();
    
    private PozoDTO pozo;
    
    private Date fechaCreacion;
    
    private String fecha;

    public PozoDTO getPozo() {
        return pozo;
    }

    public void setPozo(PozoDTO pozo) {
        this.pozo = pozo;
    }
    
    
    public CampoDTO(){
        
    }
    
  public Date getFechaCreacion(){
       return this.fechaCreacion;
   }
   
   public void setFechaCreacion(Date fecha){
     //  System.out.println("fecha: "+fecha.getCalendarType());
       this.fechaCreacion=fecha;
   }
   
   public void setFecha(Date fecha){
       //System.out.println("fecha: "+fecha.getCalendarType());
      //   fecha.add(Calendar.DATE, 1);
       //  Date date = fecha.getTime();             
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = format1.format(fecha);   
      this.fecha = date1; 
       
   }
   public String getFecha(){
       return this.fecha;
   }
    
    public void iniciarFecha(){
        setFecha(new Date());
    }
    
   
    public PozoDTO agregarPozo(String nombreP){
        
        PozoDTO nuevo=new PozoDTO();
        nuevo.setNombre(nombreP);
        nuevo.setEmergencia(false);
        nuevo.setEstado("normal");
        pozos.add(nuevo);
        return nuevo;
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

      
    public List<PozoDTO> getPozos() {
        return pozos;
    }
    
   /* public List<PozoEntity> getPozosEntity( List<PozoDTO> pozosDTO) {
        List<PozoEntity>lista=new List<PozoEntity>
        for(pozos)
        return pozos;
    }*/

    public void setPozos(List<PozoDTO> pozos) {
        this.pozos = pozos;
    }
    
    
    public PozoEntity convertirAentidad(PozoDTO dto){
        PozoEntity pozoN=new PozoEntity();
        pozoN.setNombre(dto.getNombre());
        return pozoN;
    }
    
     
    public PozoDTO convertirAdto(PozoEntity entity){
        PozoDTO pozoN=new PozoDTO();
        pozoN.setId(entity.getId());
        pozoN.setNombre(entity.getNombre());
        return pozoN;
    }
   
}
