package app;

public class WordLocation {

    private final int mPage;
    private final int mIndex;

    public WordLocation(int page, int index) {
        mPage = page;
        mIndex = index;
    }

    public int getPage() {
        return mPage;
    }

    public int getIndex() {
        return mIndex;
    }
}
