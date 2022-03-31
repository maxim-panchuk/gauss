import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LinearSystem <N extends Number, T extends Gauss<N, T>> {
    private final List<T> list = new ArrayList<>();

    public T get(int index) {
        return list.get(index);
    }

    public void push(T element) {
        list.add(element);
    }

    public int size() {
        return list.size();
    }

    public N itemAt(int i, int j) {
        return list.get(i).at(j);
    }

    public BigDecimal determine (LinearSystem<BigDecimal, Equation> system) {
        BigDecimal determine = BigDecimal.valueOf(1.0d);
        for (int i = 0; i < system.size(); i++) {
            determine = determine.multiply((BigDecimal) itemAt(i, i));
        }
        return determine;
    }
}
