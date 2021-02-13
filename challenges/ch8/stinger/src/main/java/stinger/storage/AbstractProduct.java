package stinger.storage;

public abstract class AbstractProduct implements Product {

    private final ProductType mType;

    public AbstractProduct(ProductType type) {
        mType = type;
    }

    @Override
    public ProductType getType() {
        return mType;
    }
}
