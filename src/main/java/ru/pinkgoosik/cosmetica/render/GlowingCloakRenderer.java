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
import ru.pinkgoosik.cosmetica.util.ColorUtil;

public class GlowingCloakRenderer implements CloakRenderer {

    @Override
    public void renderCloak(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, PlayerEntityModel<?> contextModel) {
        matrices.push();
        CloakRenderer.setAngles(matrices, player, tickDelta);
        float[] color = ColorUtil.toFloatArray(ColorUtil.color("FFFFFF"));
        ((PlayerEntityModelAccessor) contextModel).getCloak().render(matrices, ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getLightning(), false, false), light, OverlayTexture.DEFAULT_UV, color[0], color[1], color[2], color[3]);
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
            float[] color = ColorUtil.toFloatArray(ColorUtil.color("FFFFFF"));
            elytra.render(matrices, ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getLightning(), false, itemStack.hasGlint()), light, OverlayTexture.DEFAULT_UV, color[0], color[1], color[2], color[3]);
        }
        matrices.pop();
    }

}