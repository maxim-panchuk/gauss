import java.math.BigDecimal;

public class Algorithm <N extends Number, T extends Gauss<N, T>> {

    LinearSystem<N, T> system;

    public Algorithm(LinearSystem<N, T> system) {
        this.system = system;
    }

    public void calculate() throws NullPointerException, ArithmeticException {
        if (system == null) throw new NullPointerException("LinearSystem<N, T> instance equal null");
        if (!checkSystem(system)) throw new ArithmeticException("Incorrect system for Gauss Method");
        for (int i = 0; i < system.size() - 1; i++) {
            swap(i);
            for (int j = i + 1; j < system.size(); j++) {
                N k = system.get(j).calcCoefficient(system.get(j).at(i), system.get(i).at(i));
                if (((BigDecimal)k).compareTo(BigDecimal.ZERO) == 0) continue;
                system.get(j).mul(k);
                system.get(j).addEq(system.get(i));
            }
        }
    }

    private boolean checkSystem (LinearSystem<N, T> system) {
        if (system.size() < 2) return false;
        for (int i = 0; i < system.size(); i++) {
            if (system.get(i).size() != (system.size() + 1)) return false;
        }
        return true;
    }

    /*private void swap (int i) {
        BigDecimal max = Math.abs((BigDecimal) system.get(i).at(i));
        int maxEq = i;
        for (int k = i + 1; k < system.size(); k++) {
            if (Math.abs((Double) system.get(k).at(i)) > max) {
                max = Math.abs((Double) system.get(k).at(i));
                maxEq = k;
            }
        }
        ((Equation) system.get(i)).swap((Equation) system.get(maxEq));
    }*/

    private void swap (int i) {
        BigDecimal max = (BigDecimal) system.get(i).at(i);
        BigDecimal absMax;
        int maxEq = i;
        if (max.compareTo(BigDecimal.ZERO) < 0) {
            absMax = max.multiply(BigDecimal.valueOf(-1));
        } else {
            absMax = max;
        }
        for (int k = i + 1; k < system.size(); k++) {
            BigDecimal absCurr;
            BigDecimal curr = (BigDecimal) system.get(k).at(i);
            if (curr.compareTo(BigDecimal.ZERO) < 0) {
                absCurr = curr.multiply(BigDecimal.valueOf(-1));
            } else {
                absCurr = curr;
            }
            if (absCurr.compareTo(absMax) > 0) {
                absMax = absCurr;
                maxEq = k;
            }
        }
        ((Equation) system.get(i)).swap((Equation) system.get(maxEq));
    }
}
