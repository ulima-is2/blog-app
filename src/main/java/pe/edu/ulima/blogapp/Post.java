
package pe.edu.ulima.blogapp;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author hernan
 */
@Entity("employees")
public class Post {
    @Id
    private ObjectId id;
    
    private String nombre;
    private String contenido;
    private String fecha;

    public Post() {
    }

    public Post(String nombre, String fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}

