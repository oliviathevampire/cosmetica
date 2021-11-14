package ru.pinkgoosik.cosmetica.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.cosmetica.cosmetics.cloak.FancyCloaks;
import ru.pinkgoosik.cosmetica.data.PlayerCloaks;

import java.util.Optional;

@Mixin(CapeFeatureRenderer.class)
public abstract class CapeLayerMixin extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

	public CapeLayerMixin(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> renderLayerParent) {
		super(renderLayerParent);
	}

	@Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, CallbackInfo info) {
		if (player.canRenderCapeTexture() && !player.isInvisible() && player.isPartVisible(PlayerModelPart.CAPE)) {
			PlayerCloaks.ENTRIES.forEach(entry -> {
				if (entry.uuid().equals(player.getGameProfile().getId().toString()) || player.getName().asString().equals(entry.name())) {
					ItemStack itemStack = player.getEquippedStack(EquipmentSlot.CHEST);
					if (!(itemStack.getItem() instanceof ElytraItem)) {
						Optional<FancyCloaks.FancyCloak> optional = FancyCloaks.getCloakByName(entry.cloak());
						optional.ifPresent(cloak -> {
							cloak.cloakRenderer().renderCloak(matrices, vertexConsumers, light, player, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch, this.getContextModel());
							info.cancel();
						});
					}
				}
			});
		}
	}
}
