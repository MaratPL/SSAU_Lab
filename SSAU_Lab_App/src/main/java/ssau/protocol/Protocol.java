package ssau.protocol;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ssau.lab.Game;
import ssau.lab.Genre;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

public class Protocol implements Serializable {

    @Nullable
    private String id;

    @NotNull
    private OperationType operationType;

    @Nullable
    private ObjectType objectType;

    @Nullable
    private Object value;

    private boolean isError = false;

    public Protocol(){}

    public Protocol(
            @Nullable final String id,
            @NotNull final OperationType operationType,
            @Nullable final ObjectType objectType,
            @Nullable final Object value
    ) {
        this.id = id;
        this.operationType = operationType;
        this.objectType = objectType;
        this.value = value;
    }

    public Protocol(@NotNull final OperationType operationType, boolean isError) {
        this.operationType = operationType;
        this.isError = isError;
    }

    public Protocol(
            @NotNull final OperationType operationType,
            @Nullable final ObjectType objectType,
            @Nullable final Object value,
            boolean isError
    ) {
        this.id = id;
        this.operationType = operationType;
        this.objectType = objectType;
        this.value = value;
        this.isError = isError;
    }

    public Protocol(
            @NotNull final String id,
            @NotNull final OperationType operationType,
            @Nullable final ObjectType objectType,
            @Nullable final Object value,
            boolean isError
    ) {
        this.id = id;
        this.operationType = operationType;
        this.objectType = objectType;
        this.value = value;
        this.isError = isError;
    }

    @Nullable
    public String getId() {
        return id;
    }

    public void setId(@Nullable String id) {
        this.id = id;
    }

    @Nullable
    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(@NotNull OperationType operationType) {
        this.operationType = operationType;
    }

    @Nullable
    public Object getValue() {
        return value;
    }

    public void setValue(@Nullable Object value) {
        this.value = value;
    }

    @Nullable
    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(@Nullable ObjectType objectType) {
        this.objectType = objectType;
    }

    public boolean isError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }
}
