package xyz.przemyk.simpleplanes.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.EntityDamageSource;

public class PlaneCrashDamageSource extends EntityDamageSource {
    public PlaneCrashDamageSource(Entity source) {
        super("plain_crash", source);
        this.setExplosive();
        this.setFire();
        this.setUnblockable();
    }
}
