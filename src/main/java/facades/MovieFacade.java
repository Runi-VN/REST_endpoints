package facades;

import dto.MovieDTO;
import entities.Movie;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MovieFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getMovieCount() {
        EntityManager em = getEntityManager();
        try {
            return (long) em.createQuery("SELECT COUNT(r) FROM Movie r").getSingleResult();
        } finally {
            em.close();
        }
    }

    public Movie addMovie(Movie movie) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
            return movie;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new IllegalArgumentException("Could not add movie: " + ex.getMessage());
        } finally {
            em.close();
        }
    }

    public Movie addMovie(String name, Date releaseDate, int score, int earnings) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Movie movie = new Movie(name, releaseDate, score, earnings);
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
            return movie;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new IllegalArgumentException("Could not add movie: " + ex.getMessage());
        } finally {
            em.close();
        }
    }

    public List<MovieDTO> getAllMovieDTOs() throws Exception {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT new dto.MovieDTO(m) FROM Movie m", MovieDTO.class).getResultList();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new IllegalArgumentException("Could not retrieve movies: " + ex.getMessage());
        } finally {
            em.close();
        }
    }

    public MovieDTO getMovieByID(Long id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            return new MovieDTO(em.find(Movie.class, id));
        } catch (IllegalStateException | NullPointerException ex) {
            em.getTransaction().rollback();
            throw new IllegalArgumentException("Could not retrieve specific movie: " + ex.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * For retrieving all details in form of a Movie and not a DTO
     *
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    public Movie getMovieByIDDetailed(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Movie.class, id);
        } catch (Exception ex) {
            em.getTransaction().rollback();
            return null;
            //throw new Exception("Could not retrieve specific movie: " + ex.getMessage());
        } finally {
            em.close();
        }
    }

    public void removeMovie(Movie movie) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(movie)); //first we merge ("find"), then we remove
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public MovieDTO getMovieDTOByName(String name) throws Exception {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT new dto.MovieDTO(m) FROM Movie m where m.name LIKE :name ", MovieDTO.class).setParameter("name", name).getSingleResult();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Couldn't find movie with that name.");
        } finally {
            em.close();
        }
    }
}
