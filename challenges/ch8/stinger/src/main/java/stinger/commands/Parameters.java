package stinger.commands;

import java.util.Map;

public class Parameters {

    private final Map<String, Object> mParameters;

    public Parameters(Map<String, Object> parameters) {
        mParameters = parameters;
    }

    public <T> T get(String key, Class<T> type) throws ParamNotFoundException, ParamTypeMismatchException {
        Object value = mParameters.get(key);
        if (value == null) {
            throw new ParamNotFoundException(key);
        }
        if (!type.isInstance(value)) {
            throw new ParamTypeMismatchException(key, value.getClass(), type);
        }

        return type.cast(value);
    }

    public int getInt(String key) throws ParamNotFoundException, ParamTypeMismatchException {
        return get(key, Integer.class);
    }

    public double getDouble(String key) throws ParamNotFoundException, ParamTypeMismatchException {
        return get(key, Double.class);
    }

    public String getString(String key) throws ParamNotFoundException, ParamTypeMismatchException {
        return get(key, String.class);
    }
}
