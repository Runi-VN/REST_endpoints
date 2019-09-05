package dto;

import entities.Movie;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author runin
 */
public class MovieDTO {
    
    private Long movieID;
    private String movieName;
    private Date releaseDate;
    private int movieScore; //0-10

    public MovieDTO() {
    }

    public MovieDTO(Movie movie) {
        this.movieID = movie.getId();
        this.movieName = movie.getName();
        this.releaseDate = movie.getReleaseDate();
        this.movieScore = movie.getRating();
    }

    public Long getMovieID() {
        return movieID;
    }

    public void setMovieID(Long movieID) {
        this.movieID = movieID;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getMovieScore() {
        return movieScore;
    }

    public void setMovieScore(int movieScore) {
        this.movieScore = movieScore;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MovieDTO other = (MovieDTO) obj;
        if (this.movieScore != other.movieScore) {
            return false;
        }
        if (!Objects.equals(this.movieName, other.movieName)) {
            return false;
        }
        if (!Objects.equals(this.releaseDate, other.releaseDate)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
}
