package interfejsi;

public interface Serijalizacija<T, B> {
    public T izCSV(B id);
    public void uCSV(T objekat);
}
