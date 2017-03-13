package github.rudzki.michal.movieapp.detail;

import com.annimon.stream.Objects;
import com.google.gson.annotations.SerializedName;


/**
 * Created by RENT on 2017-03-13.
 */

public class MovieItem {

    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Rated")
    private String rated;

    @SerializedName("Runtime")
    private String runtime;

    @SerializedName("Director")
    private String director;

    @SerializedName("Actors")
    private String actors;

    @SerializedName("Plot")
    private String plot;

    @SerializedName("Awards")
    private String awards;

    private String imdbRating;

    @SerializedName("Type")
    private String type;

    @SerializedName("Response")
    private String response;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieItem movieItem = (MovieItem) o;
        return Objects.equals(title, movieItem.title) &&
                Objects.equals(year, movieItem.year) &&
                Objects.equals(rated, movieItem.rated) &&
                Objects.equals(runtime, movieItem.runtime) &&
                Objects.equals(director, movieItem.director) &&
                Objects.equals(actors, movieItem.actors) &&
                Objects.equals(plot, movieItem.plot) &&
                Objects.equals(awards, movieItem.awards) &&
                Objects.equals(Poster, movieItem.Poster) &&
                Objects.equals(imdbRating, movieItem.imdbRating) &&
                Objects.equals(type, movieItem.type) &&
                Objects.equals(response, movieItem.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year, rated, runtime, director, actors, plot, awards, Poster, imdbRating, type, response);
    }

    @SerializedName("Poster")
    private String Poster;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRated() {
        return rated;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getAwards() {
        return awards;
    }

    public String getPoster() {
        return Poster;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getType() {
        return type;
    }

    public String getResponse() {
        return response;
    }

}
