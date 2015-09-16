package ssau.lab1;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.UUID;

public class Genre {

    @NotNull
    private final String genreId = UUID.randomUUID().toString();

    @Nullable
    private String genreName = null;

    public Genre() {
    }

    public Genre(@NotNull final String name) {
        genreName = name;
    }

    @NotNull
    public String getGenreId() {
        return genreId;
    }

    @Nullable
    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(@Nullable final String genreName) {
        this.genreName = genreName;
    }
}
