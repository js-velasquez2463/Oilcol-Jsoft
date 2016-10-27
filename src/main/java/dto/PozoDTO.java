
package dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Johan
 */
public class PozoDTO {
 
    private Long id;

    private String nombre;
    
    private String estado;
    
    private double temperatura;

    private double consumoEnergetico;
    
    private double numeroBarriles;
    
    private boolean emergencia; 
    
    private Date fechaCreacion;
    
    private String fecha;
 
    public PozoDTO(){
        
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
 public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getConsumoEnergetico() {
        return consumoEnergetico;
    }

    public void setConsumoEnergetico(double consumoEnergetico) {
        this.consumoEnergetico = consumoEnergetico;
    }

    public double getNumeroBarriles() {
        return numeroBarriles;
    }

    public void setNumeroBarriles(double numeroBarriles) {
        this.numeroBarriles = numeroBarriles;
    }

    public boolean isEmergencia() {
        return emergencia;
    }

    public void setEmergencia(boolean emergencia) {
        this.emergencia = emergencia;
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
   
   
}
