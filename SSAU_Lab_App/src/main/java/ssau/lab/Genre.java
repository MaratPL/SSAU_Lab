package ssau.lab;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.UUID;

public class Genre implements Serializable{

    @NotNull
    private String genreId = UUID.randomUUID().toString();

    @Nullable
    private String genreName = null;

    public Genre() {
    }

    public Genre(@Nullable final String id, @Nullable final String name) {
        genreId = id;
        genreName = name;
    }

    public Genre(@Nullable final String name) {
        genreName = name;
    }

    @NotNull
    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(@NotNull String genreId) {
        this.genreId = genreId;
    }

    @Nullable
    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(@Nullable final String genreName) {
        this.genreName = genreName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        return genreId.equals(genre.genreId);

    }

    @Override
    public int hashCode() {
        return genreId.hashCode();
    }
}
