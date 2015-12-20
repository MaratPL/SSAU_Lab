package ssau.web;


import org.jetbrains.annotations.NotNull;

public enum OperationType {
    SUBSCRIBE("Подписаться"),
    GET_ENTITY("Получить сущность"),
    GET_LIST_ENTITY("Получить список сущностей"),
    CREATE_ENTITY("Создание сущности"),
    DELETE_ENTITY("Удаление сущности"),
    BEGIN_EDITING_ENTITY("Начало редактирования сущности"),
    END_EDITING_ENTITY("Окончание редактирования сущности"),
    UNSUBSCRIBE("Отписаться"),
    ERROR("Произошла ошибка");

    @NotNull
    private String name = "";

    private OperationType() {
    }

    OperationType(@NotNull final String name) {
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
