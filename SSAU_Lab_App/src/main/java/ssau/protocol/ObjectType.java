package ssau.protocol;

import org.jetbrains.annotations.NotNull;

public enum ObjectType {

    GAME("Игра"),
    GENRE("Жанр"),
    GAME_LIST("Сиисок игр"),
    GENRE_LIST("Список жанров");

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
