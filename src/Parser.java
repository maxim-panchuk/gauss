import java.math.BigDecimal;

public class Parser {
    String line;

    public Parser (String line) {
        this.line = line;
    }

    public LinearSystem<BigDecimal, Equation> getSys () {
        LinearSystem<BigDecimal, Equation> system = new LinearSystem<>();
        String [] arr = line.split(";");
        for (String s : arr) {
            String[] eq = s.split(" ");
            Equation equation = new Equation();
            equation.parse(eq);
            system.push(equation);
        }
        return system;
    }
}
