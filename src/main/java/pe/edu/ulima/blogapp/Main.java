package pe.edu.ulima.blogapp;

import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.stop;

/**
 *
 * @author hernan
 */
public class Main {
 
    public static void main(String[] args) {
        port(obtenerPuertoHeroku());
        //Spark.staticFiles.location("/public");
        
        final Morphia morphia = new Morphia();
        morphia.mapPackage("pe.edu.ulima.blogapp");
        final Datastore datastore = morphia.createDatastore(new MongoClient("localhost", 27017), 
                "blogapp_db");
        datastore.ensureIndexes();
        
        
        get("/parar", (req, resp)->{
            stop();
            return "";
        });
        
        get("/listar_posts", (req, resp)->{
            final Query<Post> query = datastore.createQuery(Post.class);
            List<Post> listaPosts = query.asList();
//            List<Post> listaPosts = new ArrayList<>();
//            listaPosts.add(new Post("Post 1", "01/01/2017"));
//            listaPosts.add(new Post("Post 2", "04/01/2017"));
//            listaPosts.add(new Post("Post 3", "05/01/2017"));
            
            
            HashMap<String, Object> map = new HashMap<>();
            map.put("posts", listaPosts);
            return new ModelAndView(map, "main.html");
        }, new Jinja2TemplateEngine());
        
        get("/add_post", (req, resp)->{
            return new ModelAndView(null, "add-post.html");
        }, new Jinja2TemplateEngine());
        
        post("/add_post", (req, resp)->{
            String nombrePost = req.queryParams("nombre_post");
            String descripcionPost = req.queryParams("descripcion_post");
            String fechaPost = req.queryParams("fecha_post");
            
            datastore.save(new Post(nombrePost, fechaPost));
            
            LoggerFactory.getLogger(Main.class).info(nombrePost);
            resp.redirect("/listar_posts");
            return null;
        });
    }

    static int obtenerPuertoHeroku() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
}
