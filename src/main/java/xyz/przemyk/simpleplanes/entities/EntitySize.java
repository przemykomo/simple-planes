package xyz.przemyk.simpleplanes.entities;

public class EntitySize {
    public final float width;
    public final float hight;

    public EntitySize(float width, float hight) {

        this.width = width;
        this.hight = hight;
    }

    public static EntitySize flexible(float width, float hight) {
        return new EntitySize(width,hight);
    }
}
