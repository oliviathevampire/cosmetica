package ru.pinkgoosik.cosmetica.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.cosmetica.cosmetics.cloak.Cloak;
import ru.pinkgoosik.cosmetica.cosmetics.cloak.Cloaks;
import ru.pinkgoosik.cosmetica.data.PlayerCloaks;

import java.util.Optional;
import java.util.UUID;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerMixin extends PlayerEntity {

	public AbstractClientPlayerMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}

	@Inject(method = "getCapeTexture", at = @At("HEAD"), cancellable = true)
	void getCapeTexture(CallbackInfoReturnable<Identifier> cir) {
		PlayerCloaks.ENTRIES.forEach(entry -> {
			if(this.getUuid().equals(UUID.fromString(entry.uuid())) || this.getName().asString().equals(entry.name())){
				Optional<Cloak> optional = Cloaks.getCloakByName(entry.cloak());
				optional.ifPresent(cloak -> cir.setReturnValue(cloak.getIdProvider().getId(this, world)));
			}
		});
	}
}
