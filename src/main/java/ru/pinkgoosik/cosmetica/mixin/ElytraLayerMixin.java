package ru.pinkgoosik.cosmetica.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.cosmetica.cosmetics.cloak.FancyCloaks;
import ru.pinkgoosik.cosmetica.data.PlayerCloaks;

import java.util.Optional;

@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraLayerMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
	@Shadow
	@Final
	private ElytraEntityModel<T> elytra;

	public ElytraLayerMixin(FeatureRendererContext<T, M> renderLayerParent) {
		super(renderLayerParent);
	}

	@SuppressWarnings("unchecked")
	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T livingEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, CallbackInfo info) {
		if (livingEntity instanceof AbstractClientPlayerEntity player) {
			for (PlayerCloaks.Entry entry : PlayerCloaks.ENTRIES) {
				if (entry.uuid().equals(player.getGameProfile().getId().toString()) || player.getName().asString().equals(entry.name())) {
					ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
					if (itemStack.getItem() instanceof ElytraItem) {
						if(FancyCloaks.CLOAK_NAMES.contains(entry.cloak())){
							Optional<FancyCloaks.FancyCloak> optional = FancyCloaks.getCloakByName(entry.cloak());
							optional.ifPresent(cloak -> cloak.cloakRenderer().renderElytra(matrices, vertexConsumers, light, player, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch, (EntityModel<AbstractClientPlayerEntity>)this.getContextModel(), (EntityModel<AbstractClientPlayerEntity>)elytra));
							info.cancel();
						}
					}
				}
			}
		}
	}
}
