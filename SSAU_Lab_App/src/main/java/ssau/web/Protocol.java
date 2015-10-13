package ssau.web;


import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public class Protocol implements Serializable {

    @Nullable
    private String id;

    @Nullable
    private OperationType operationType;

    @Nullable
    private String value;

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

    public void setOperationType(@Nullable OperationType operationType) {
        this.operationType = operationType;
    }

    @Nullable
    public String getValue() {
        return value;
    }

    public void setValue(@Nullable String value) {
        this.value = value;
    }
}
