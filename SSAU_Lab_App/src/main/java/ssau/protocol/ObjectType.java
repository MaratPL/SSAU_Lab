package ssau.protocol;

import org.jetbrains.annotations.NotNull;

public enum ObjectType {

    GAME(),
    GENRE(),
    GAME_LIST(),
    GENRE_LIST();

    @NotNull
    private String name = "";

    private ObjectType() {
    }

    ObjectType(@NotNull final String name) {
        this.name = name;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public String getId() {
        return name();
    }
}
