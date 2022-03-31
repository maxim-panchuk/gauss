import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class Equation implements Gauss<BigDecimal, Equation> {

    private final List<BigDecimal> equation = new ArrayList<>();

    /*public void generate(int size) {
        if (size < 2) size = 2;
        this.equation.clear();
        for (int i = 0; i < size; i++) {
            Random random = new Random();
            this.equation.add((double) (random.nextInt()%10) + 1);
        }
    }*/

    public void parse (String [] line) {
        this.equation.clear();
        for (String s : line) {
            this.equation.add(BigDecimal.valueOf(Double.parseDouble(s)));
        }
    }

    @Override
    public void addEq(Equation item) {
        ListIterator<BigDecimal> i = equation.listIterator();
        ListIterator<BigDecimal> j = item.getEquation().listIterator();
        while (i.hasNext() && j.hasNext()) {
            BigDecimal a = i.next();
            BigDecimal b = j.next();
            i.set(a.add(b));
        }
    }

    public void swap (Equation item) {
        ListIterator<BigDecimal> i = equation.listIterator();
        ListIterator<BigDecimal> j = item.getEquation().listIterator();
        while (i.hasNext() && j.hasNext()) {
            BigDecimal a = j.next();
            BigDecimal b = i.next();
            i.set(a);
            j.set(b);
        }
    }

    @Override
    public void mul(BigDecimal coefficient) {
        for (ListIterator<BigDecimal> i = equation.listIterator(); i.hasNext();) {
            BigDecimal next = i.next();
            i.set(next.multiply(coefficient));
        }
    }

    @Override
    public BigDecimal calcCoefficient(BigDecimal first, BigDecimal second) {
        if (!(first.compareTo(BigDecimal.ZERO) == 0)) {
            return second.divide(first, 8, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(-1.0));
        }

        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal at(int index) {
        return equation.get(index);
    }

    @Override
    public int size() {
        return equation.size();
    }

    public List<BigDecimal> getEquation() {
        return equation;
    }
}
