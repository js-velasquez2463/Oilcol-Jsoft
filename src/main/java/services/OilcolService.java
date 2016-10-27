/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dto.CampoDTO;
import dto.PozoDTO;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import main.PersistenceManager;
import models.Competitor;
import dto.CompetitorDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.CampoEntity;
import models.PozoEntity;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Mauricio
 */
@Path("/oilcol")
@Produces(MediaType.APPLICATION_JSON)
public class OilcolService {

    @PersistenceContext(unitName = "Oilcol")
    EntityManager entityManager; 
    

     @PostConstruct
    public void init() {
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    
   @GET
    @Path("/getCampos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        Query q = entityManager.createQuery("select u from CampoEntity u order by u.id" );
        List<CampoEntity> campos = q.getResultList();
         for (int i=0;i<campos.size();i++){
             List<PozoEntity> lista=(List<PozoEntity>) campos.get(i).getPozos();
            for(int j=0;j<lista.size();j++){
                lista.get(j).setCampo(null);
            }
            campos.get(i).setPozos(lista);
         }
         return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(campos).build();
    } 
    
    
    
    @GET
    @Path("/filtroPozos/{id: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFiltroPozos(@PathParam("id") String id) {
        Query q = entityManager.createQuery("select u from PozoEntity u order by u.id");
        System.out.println("entro a filtro");
        List<PozoEntity> pozos = q.getResultList();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try { 
            Date date = format.parse(id);
            
            for (int i=0;i<pozos.size();i++){
            CampoEntity campo= pozos.get(i).getCampo();
            if(campo!=null)campo.setPozos(null);
            
            if(!campo.getFecha().equals(date)){
                pozos.remove(i);
            }

            }
            } catch (ParseException ex) {
                Logger.getLogger(OilcolService.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(pozos).build();
    }
    
    @GET
    @Path("/getPozos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPozos() {
        Query q = entityManager.createQuery("select u from PozoEntity u order by u.id");
        List<PozoEntity> pozos = q.getResultList();
        for (int i=0;i<pozos.size();i++){
        CampoEntity campo= pozos.get(i).getCampo();
        if(campo!=null)campo.setPozos(null);
              
         }
           
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(pozos).build();
    } 
    
    @GET
    @Path("/getPozo/{id: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPozo(@PathParam("id") Long id) {
       
        PozoEntity pozo =entityManager.find(PozoEntity.class, id);
        PozoDTO encontrado=new PozoDTO();
        if(pozo!=null){
            encontrado.setId(pozo.getId());
            encontrado.setNombre(pozo.getNombre());
            encontrado.setConsumoEnergetico(pozo.getConsumoEnergetico());
            encontrado.setEmergencia(pozo.isEmergencia());
            encontrado.setEstado(pozo.getEstado());
            encontrado.setNumeroBarriles(pozo.getNumeroBarriles());
            encontrado.setTemperatura(pozo.getTemperatura());
            encontrado.setFechaCreacion(pozo.getCreationTimestamp());
            encontrado.setFecha(pozo.getCreationTimestamp());
            
        }
     return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(encontrado).build();
     } 
    
    @DELETE
    @Path("/deleteCampo/{id: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCampo(@PathParam("id") Long id) {
        JSONObject rta = new JSONObject();
                  
          try {
              CampoEntity entity=entityManager.find(CampoEntity.class, id);
              //entity.setCampo(null);
                if(entity!=null){
                     entityManager.getTransaction().begin();
                    // entityManager.merge(entity);
                     entityManager.remove(entity);
                     entityManager.getTransaction().commit();
                     // entityManager.refresh(entity);
                    rta.put("campo_id", entity.getId());
                }
           } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
         } finally {
            entityManager.clear();
            entityManager.close();
        }
          return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta).build();
    }
    
    
    @GET
    @Path("/getCampo/{id: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCampo(@PathParam("id") Long id) {
       CampoEntity campo =entityManager.find(CampoEntity.class, id);
       CampoDTO encontrado=null;
       if(campo!=null){
         encontrado=new CampoDTO();
        
            encontrado.setCiudad(campo.getCiudad());
            encontrado.setId(campo.getId());
            encontrado.setNombre(campo.getNombre());
            encontrado.setFecha(campo.getFecha());
            List<PozoEntity> pozos = campo.getPozos();
            List<PozoDTO>lista=new ArrayList<PozoDTO>();
        for (int i=0;i<pozos.size();i++){
            lista.add(pozoEntity_DTO(pozos.get(i)));
        }
            
            encontrado.setPozos(lista);
        }
     return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(encontrado).build();
      }

    @POST
    @Path("/addCampo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCampo(CampoDTO campo) {
        JSONObject rta = new JSONObject();
        CampoEntity campoTmp = new CampoEntity();
        campoTmp.setCiudad(campo.getCiudad());
        campoTmp.setNombre(campo.getNombre());
        campoTmp.iniciarFecha();
         
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(campoTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(campoTmp);
            rta.put("campo_id", campoTmp.getId());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            campoTmp = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta).build();
    } 
    
    
    @POST
    @Path("/addCampoYpozos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCampo2(CampoDTO campo) {
        JSONObject rta = new JSONObject();
        CampoEntity campoTmp = new CampoEntity();
        campoTmp.setCiudad(campo.getCiudad());
        campoTmp.setNombre(campo.getNombre());
        campoTmp.iniciarFecha();
        
        ArrayList<PozoEntity> lista=new ArrayList<PozoEntity>();
        //campoTmp.s
        for(int i=0;i<campo.getPozos().size();i++){
            PozoDTO pozo=campo.getPozos().get(i);
           // PozoEntity nuevo=new PozoEntity();
            //nuevo.setNombre(pozo.getNombre());
            
            PozoEntity nuevo=pozoDTO_Entity(pozo);
            
            
            nuevo.setCampo(campoTmp);
            lista.add(nuevo);
           try {
            entityManager.getTransaction().begin();
            entityManager.persist(nuevo);
            entityManager.getTransaction().commit();
            entityManager.refresh(nuevo);
           
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            campoTmp = null;
        } 
        }
        campoTmp.setPozos(lista);
         System.out.println("nuevo: "+campoTmp.getPozos().size());
        //
 
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(campoTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(campoTmp);
            rta.put("campo_id", campoTmp.getId());
            rta.put("pozos_id", campo.getPozos().size());
            rta.put("pozos_nuevos", campoTmp.getPozos().size());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            campoTmp = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta).build();
    } 
    
    
    @POST
    @Path("/addPozo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPozo(PozoDTO pozo) {
        JSONObject rta = new JSONObject();
        PozoEntity campoTmp = new PozoEntity();
        
        //campoTmp.setNombre(pozo.getNombre());
        if(pozo.getNombre()!=null && !pozo.getNombre().equals("")) 
            campoTmp.setNombre(pozo.getNombre());
        if(pozo.getConsumoEnergetico()!=0.0 )
            campoTmp.setConsumoEnergetico(pozo.getConsumoEnergetico());
        
        campoTmp.setEmergencia(pozo.isEmergencia());
        if(pozo.getEstado()!=null && pozo.getEstado().equals(""))
        campoTmp.setEstado(pozo.getEstado());
        if(pozo.getNumeroBarriles()!=0.0)
          campoTmp.setNumeroBarriles(pozo.getNumeroBarriles());
        if(pozo.getTemperatura()!=0.0)
         campoTmp.setTemperatura(pozo.getTemperatura());      
 
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(campoTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(campoTmp);
            rta.put("pozo_id", campoTmp.getId());
           // rta.put("pozo_id", ((CampoDTO)campoTmp.getPozos().get(0)).getNombre());
            
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            campoTmp = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta).build();
    } 
    
    
    
    
    @PUT
    @Path("/editPozo/{id: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePozo(@PathParam("id") Long id, PozoDTO pozo) {
    
        JSONObject rta = new JSONObject();
        if (pozo != null) {
        PozoEntity entity = entityManager.find(PozoEntity.class, id);
        if(entity ==null)entity=new PozoEntity();
        entity.setId(id);
        
        if(pozo.getNombre()!=null && !pozo.getNombre().equals("")) 
            entity.setNombre(pozo.getNombre());
        if(pozo.getConsumoEnergetico()!=0.0 )
            entity.setConsumoEnergetico(pozo.getConsumoEnergetico());
        
        entity.setEmergencia(pozo.isEmergencia());
        if(pozo.getEstado()!=null && pozo.getEstado().equals(""))
        entity.setEstado(pozo.getEstado());
        if(pozo.getNumeroBarriles()!=0.0)
          entity.setNumeroBarriles(pozo.getNumeroBarriles());
        if(pozo.getTemperatura()!=0.0)
         entity.setTemperatura(pozo.getTemperatura());
      //  entityManager.merge(entity);
       try {
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
           // entityManager.refresh(entity);
            rta.put("pozo_id", entity.getId());
          
            
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            entity = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta).build();
    
        }
    
    
    @DELETE
    @Path("/deletePozo/{id: \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePozo(@PathParam("id") Long id) {
        JSONObject rta = new JSONObject();
                  
          try {
              PozoEntity entity=entityManager.find(PozoEntity.class, id);
              
                if(entity!=null){
                    entity.setCampo(null);
                     entityManager.getTransaction().begin();
                     entityManager.merge(entity);
                     entityManager.remove(entity);
                     entityManager.getTransaction().commit();
                     // entityManager.refresh(entity);
                    rta.put("pozo_id", entity.getId());
                }
          
            
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            
        } finally {
            entityManager.clear();
            entityManager.close();
        }
           
        
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta).build();
    }
    
    
     
    
   
    
    
       @POST
    @Path("/log")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginCompetitor(CompetitorDTO competitor) {
        JSONObject rta = new JSONObject();
        Competitor competitorTmp = new Competitor();
        competitorTmp.setUsername(competitor.getUsername());
        competitorTmp.setPassword(competitor.getPassword());

            Query q = entityManager.createQuery("select u from Competitor u where u.username='"+competitor.getUsername()+"' and u.password='"+competitor.getPassword()+"'");
             List<Competitor> competitors = q.getResultList();
             if(competitors.isEmpty())
             {
                 return Response.status(401).header("NotAuthorizedException", "*").build();
             }else
             {
                return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(competitors).build();
             }

    } 

    /**
     *
     * @param pozo
     * @return
     */
    public PozoDTO pozoEntity_DTO(PozoEntity pozo){
    
    PozoDTO encontrado=new PozoDTO();
        if(pozo!=null){
            encontrado.setId(pozo.getId());
            encontrado.setNombre(pozo.getNombre());
            encontrado.setConsumoEnergetico(pozo.getConsumoEnergetico());
            encontrado.setEmergencia(pozo.isEmergencia());
            encontrado.setEstado(pozo.getEstado());
            encontrado.setNumeroBarriles(pozo.getNumeroBarriles());
            encontrado.setTemperatura(pozo.getTemperatura());
        }
        return encontrado;
}
    
    public PozoEntity pozoDTO_Entity(PozoDTO  pozo){
    
    PozoEntity encontrado=new PozoEntity();
        if(pozo!=null){
            encontrado.setId(pozo.getId());
            encontrado.setNombre(pozo.getNombre());
            encontrado.setConsumoEnergetico(pozo.getConsumoEnergetico());
            encontrado.setEmergencia(pozo.isEmergencia());
            encontrado.setEstado(pozo.getEstado());
            encontrado.setNumeroBarriles(pozo.getNumeroBarriles());
            encontrado.setTemperatura(pozo.getTemperatura());
        }
        return encontrado;
}
            
    
    
}
