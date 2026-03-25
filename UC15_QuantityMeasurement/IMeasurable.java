
public interface IMeasurable {
    double toBase(double value);
    double fromBase(double baseValue);

    default void validateOperationSupport(String operation) {}
}
