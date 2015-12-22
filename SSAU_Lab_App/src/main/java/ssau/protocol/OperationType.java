package ssau.protocol;


import org.jetbrains.annotations.NotNull;

public enum OperationType {
    SUBSCRIBE("�����������"),
    GET_ENTITY("�������� ��������"),
    GET_LIST_ENTITY("�������� ������ ���������"),
    CREATE_ENTITY("�������� ��������"),
    DELETE_ENTITY("�������� ��������"),
    BEGIN_EDITING_ENTITY("������ �������������� ��������"),
    END_EDITING_ENTITY("��������� �������������� ��������"),
    UNSUBSCRIBE("����������"),
    ERROR("��������� ������");

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
