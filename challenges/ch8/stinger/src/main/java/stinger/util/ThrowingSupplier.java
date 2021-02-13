package stinger.util;

@FunctionalInterface
public interface ThrowingSupplier<T, E extends Exception> {

    T get() throws E;
}
