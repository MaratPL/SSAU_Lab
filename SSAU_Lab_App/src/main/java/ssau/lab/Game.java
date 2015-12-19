package ssau.lab;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game implements Serializable{

    @NotNull
    private final String gameId = UUID.randomUUID().toString();

    @Nullable
    private String gameName;

    @Nullable
    private String gameCompany;

    @NotNull
    private List<Genre> genreList = new ArrayList<Genre>();
    
    public Game() {

    }

    public Game(@Nullable final String gameName, @Nullable final String gameCompany, @NotNull final List<Genre> genreList) {
        this.gameName = gameName;
        this.gameCompany = gameCompany;
        this.genreList = genreList;
    }

    @NotNull
    public String getGameId() {
        return gameId;
    }

    @Nullable
    public String getGameName() {
        return gameName;
    }

    public void setGameName(@Nullable final String gameName) {
        this.gameName = gameName;
    }

    @Nullable
    public String getGameCompany() {
        return gameCompany;
    }

    public void setGameCompany(@Nullable final String gameCompany) {
        this.gameCompany = gameCompany;
    }

    @NotNull
    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(@NotNull final List<Genre> genreList) {
        this.genreList = genreList;
    }
}
