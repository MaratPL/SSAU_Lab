package ssau.lab;


import org.jetbrains.annotations.NotNull;
import ssau.view.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModel {

    @NotNull
    protected final List<Observer> observerList = new ArrayList<>();

    public void attach(@NotNull Observer observer) {
        observerList.add(observer);
    }

    public void detach(@NotNull Observer observer) {
        observerList.remove(observer);
    }

}
