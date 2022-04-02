package xyz.przemyk.simpleplanes.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CapClientConfigProvider implements ICapabilitySerializable<CompoundTag> {

    public static Capability<CapClientConfig> CLIENT_CONFIG_CAP = CapabilityManager.get(new CapabilityToken<>() {});
    private CapClientConfig capClientConfig = null;
    private final LazyOptional<CapClientConfig> clientConfigLazyOptional = LazyOptional.of(this::createClientConfig);

    private CapClientConfig createClientConfig() {
        if (capClientConfig == null) {
            capClientConfig = new CapClientConfig();
        }
        return capClientConfig;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CLIENT_CONFIG_CAP) {
            return clientConfigLazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        createClientConfig().save(compoundTag);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        createClientConfig().load(compoundTag);
    }

    public void invalidate() {
        clientConfigLazyOptional.invalidate();
    }
}
