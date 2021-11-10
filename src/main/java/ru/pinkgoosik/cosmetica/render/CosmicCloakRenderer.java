package ru.pinkgoosik.cosmetica.render;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import ru.pinkgoosik.cosmetica.mixin.PlayerEntityModelAccessor;

public class CosmicCloakRenderer extends CloakRenderer {

    @Override
    public void renderCloak(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, PlayerEntityModel<?> contextModel) {
        matrices.push();
        setAngles(matrices, player, tickDelta);
        ((PlayerEntityModelAccessor) contextModel).getCloak().render(matrices, ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getEndGateway(), false, false), light, OverlayTexture.DEFAULT_UV);
        matrices.pop();
    }

    @Override
    public void renderElytra(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, EntityModel<AbstractClientPlayerEntity> contextModel, EntityModel<AbstractClientPlayerEntity> elytra) {
        ItemStack itemStack = player.getEquippedStack(EquipmentSlot.CHEST);
        matrices.push();
        matrices.translate(0.0, 0.0, 0.125);
        contextModel.copyStateTo(elytra);
        elytra.setAngles(player, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        if (player.canRenderCapeTexture() && player.isPartVisible(PlayerModelPart.CAPE)) {
            elytra.render(matrices, ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getEndGateway(), false, itemStack.hasGlint()), light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        matrices.pop();
    }
}
