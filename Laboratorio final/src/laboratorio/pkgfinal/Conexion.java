
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laboratorio.pkgfinal;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.mongodb.morphia.query.UpdateOperations;

/**
 * @author Maria Fernanda Lopez 17160
 * @author Jose Pablo Cifuentes 17509
 * @version 3/11/2017
 */
public class Conexion {
    private Datastore ds;
    private List<Tanque> Todos;
    
    /**
     *Constructor for objects of class Conexion
     */
    public Conexion(){
        Todos = new ArrayList<Tanque>();
        MongoClient mongo = new MongoClient();
        Morphia morphia = new Morphia();
        morphia.map(Tanque.class).map(Registro.class).map(Region.class).map(Cilindro.class).map(Ortogonal.class).map(Cubico.class);
        ds = morphia.createDatastore(mongo, "Acueducto"); // Base Datos
    }   
    
    /**
     * Metodo para agregar un cilindro a la base de datos
     * @param cili 
     */
    public void addCilindro(Cilindro cili){
        ds.save(cili);
    }
    
    /**
     * metodo para agregar un tanque ortogonal a la base de datos
     * @param orto 
     */
    public void addOrtogonal(Ortogonal orto){
        
        ds.save(orto);
        
    }
    
    /**
     * metodo para agregar un tanque cubico a la base de datos
     * @param cubo 
     */
    public void addCubo(Cubico cubo){
        
        ds.save(cubo);
       
    }
    
    /**
     * metodo para agregar una region a la base de datos
     * @param r 
     */
    public void addRegion(Region r){
        ds.save(r);
    }
    
    /**
     * metodo para agregar el registro
     * @param r 
     */
    public void addRegistro(Registro r){
        ds.save(r);
    }
    
    
    /**
     * metodo que si sirve y devulve una lista polimorfica de los tanque guardados
     * @return tanques guardados
     */
    public List<Tanque> mostrarGuardados(){
        
        Query<Cubico> query = ds.createQuery(Cubico.class);
        List<Cubico> cubos = query.asList();
        for(Cubico misCubos: cubos){
            Todos.add(misCubos);
        }
        Query<Cilindro> query2 = ds.createQuery(Cilindro.class);
        List<Cilindro> cilindros = query2.asList();
        for(Cilindro misCilindros: cilindros){
            Todos.add(misCilindros);
        }
        Query<Ortogonal> query3 = ds.createQuery(Ortogonal.class);
        List<Ortogonal> ortos = query3.asList();
        for(Ortogonal misOrtos: ortos){
            Todos.add(misOrtos);
        }
        
        return Todos;
        
    }
    
    public void updatePorcentaje(double porcentaje,String id){
        try{
        for(Tanque i: Todos){
            if(i instanceof Ortogonal ){
                UpdateOperations xx = ds.createUpdateOperations(Ortogonal.class).set("porcentaje",porcentaje);
            }else if (i instanceof Cubico){
                UpdateOperations xx = ds.createUpdateOperations(Cubico.class).set("porcentaje",porcentaje);
            } else {
                UpdateOperations xx = ds.createUpdateOperations(Cilindro.class).set("porcentaje",porcentaje);
            }
            
            if (i.getIde().equals(id)) 
            {
                ds.save(i);
                System.out.println("se actualiza");
                
            }
            
            
        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "NO SIRVE");
        }
        
        
        
    }
    
    /**
     * Metodo que sirve para recuperar todos los datos de las regiones guardadas 
     * @return lista de regiones
     */
    public List<Region> mostrarRegiones(){
        List<Region> todos = new ArrayList<Region>();
        Query<Region> query = ds.createQuery(Region.class);
        todos = query.asList();
        return todos;
    }
    
    
}
