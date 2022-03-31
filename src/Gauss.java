public interface Gauss <N extends Number, T extends  Gauss<N, T>>{
    void addEq(T item);
    void mul (N coefficient);
    N calcCoefficient (N first, N second);
    N at (int index);
    int size ();
}
