package stinger.storage;

public enum ProductType {
    GET_FILE(1);

    private final int mIntValue;

    ProductType(int intValue) {
        mIntValue = intValue;
    }

    public int intValue() {
        return mIntValue;
    }

    public static ProductType fromInt(int value) {
        for (ProductType type : values()) {
            if (type.intValue() == value) {
                return type;
            }
        }

        throw new EnumConstantNotPresentException(ProductType.class,
                String.valueOf(value));
    }
}
