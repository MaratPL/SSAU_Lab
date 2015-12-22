package ssau.lab;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@XmlRootElement(name = "game")
@XmlType(propOrder = {"gameId","gameName", "gameCompany","genreList"})
public class Game implements Serializable{

    @NotNull
    private String gameId = UUID.randomUUID().toString();

    @Nullable
    private String gameName;

    @Nullable
    private String gameCompany;

    @XmlElement(name = "genreList")
    @XmlElementWrapper
    @NotNull
    private List<Genre> genreList = new ArrayList<>();

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

    public void setGameId(@NotNull String gameId) {
        this.gameId = gameId;
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
