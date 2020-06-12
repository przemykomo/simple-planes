package xyz.przemyk.simpleplanes;

public enum PlaneType {
    OAK,
    SPRUCE,
    BIRCH,
    JUNGLE,
    ACACIA,
    DARK_OAK;

    public static PlaneType byId(int id) {
        PlaneType[] types = values();
        if (id < 0 || id >= types.length) {
            id = 0;
        }

        return types[id];
    }
}
