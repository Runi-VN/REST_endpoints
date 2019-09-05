package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.MovieDTO;
import entities.Movie;
import utils.EMF_Creator;
import facades.MovieFacade;
import java.sql.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("movies")
public class MovieResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.DROP_AND_CREATE);
    private static final MovieFacade FACADE = MovieFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(new Movie("The Fellowship of the Ring", Date.valueOf("2001-12-19"), 10, 100000));
            em.persist(new Movie("The Two Towers", Date.valueOf("2002-12-18"), 9, 123455678));
            em.persist(new Movie("The Return of the King", Date.valueOf("2003-12-17"), 8, 999999999));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return "{\"msg\":\"Populated DB\"}";
    }

    @Path("/count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieCount() {
        long count = FACADE.getMovieCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getByID(@PathParam("id") Long id) throws Exception {
        return GSON.toJson(FACADE.getMovieByID(id));
    }

    @GET
    @Path("/{id}/details")
    @Produces({MediaType.APPLICATION_JSON})
    public String getByIDDetailed(Movie entity, @PathParam("id") Long id) throws Exception {
        return GSON.toJson(FACADE.getMovieByIDDetailed(id));
    }

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllMovies() throws Exception {
        return GSON.toJson(FACADE.getAllMovieDTOs());
    }

//    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
//    public void create(Movie entity) {
//        throw new UnsupportedOperationException();
//    }
    //No rest endpoints for addMovie, removeMovie
}
