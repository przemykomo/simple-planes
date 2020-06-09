package xyz.przemyk.simpleplanes;

public class PlanesHelper {
    public enum TYPE {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE,
        ACACIA,
        DARK_OAK;

        public static TYPE byId(int id) {
            TYPE[] types = values();
            if (id < 0 || id >= types.length) {
                id = 0;
            }

            return types[id];
        }
    }
}
