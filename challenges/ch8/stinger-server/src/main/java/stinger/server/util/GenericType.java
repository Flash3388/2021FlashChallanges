package stinger.server.util;

public interface GenericType<KEY> {

    boolean matchesKey(KEY key);
}
