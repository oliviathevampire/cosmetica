package ru.pinkgoosik.cosmetica.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.cosmetica.data.PlayerCloaks;

import java.util.UUID;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerMixin extends PlayerEntity {

	public AbstractClientPlayerMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}

	@Inject(method = "getCapeTexture", at = @At("HEAD"), cancellable = true)
	void getCapeTexture(CallbackInfoReturnable<Identifier> cir) {
		String template = "cosmetica:textures/cloak/name.png";
		PlayerCloaks.ENTRIES.forEach(entry -> {
			if(this.getUuid().equals(UUID.fromString(entry.uuid())) || this.getName().asString().equals(entry.name())){
				if(PlayerCloaks.CLOAKS.contains(entry.cloak())){
					if (entry.cloak().equals("uni")) cir.setReturnValue(new Identifier(getUniCloakId()));
					cir.setReturnValue(new Identifier(template.replaceAll("name", entry.cloak())));
				}
			}
		});
	}

	@Unique
	private String getUniCloakId() {
		String id = "cosmetica:textures/cloak/name.png";
		RegistryKey<World> worldKey = this.world.getRegistryKey();
		if (worldKey.equals(World.OVERWORLD)) id = id.replaceAll("name", "turtle");
		else if (worldKey.equals(World.NETHER)) id = id.replaceAll("name", "crimson");
		else if (worldKey.equals(World.END)) id = id.replaceAll("name", "violet");
		else id = id.replaceAll("name", "turtle");
		return id;
	}
}
