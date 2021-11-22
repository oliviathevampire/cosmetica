package ru.pinkgoosik.cosmetica.render;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public interface CloakRenderer {

    void renderCloak(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, PlayerEntityModel<?> contextModel);

    void renderElytra(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, EntityModel<AbstractClientPlayerEntity> contextModel, EntityModel<AbstractClientPlayerEntity> elytra);

    static void setAngles(MatrixStack matrices, AbstractClientPlayerEntity player, float tickDelta){
        matrices.translate(0.0, 0.0, 0.125);
        double x = MathHelper.lerp(tickDelta, player.prevCapeX, player.capeX) - MathHelper.lerp(tickDelta, player.prevX, player.getX());
        double y = MathHelper.lerp(tickDelta, player.prevCapeY, player.capeY) - MathHelper.lerp(tickDelta, player.prevY, player.getY());
        double z = MathHelper.lerp(tickDelta, player.prevCapeZ, player.capeZ) - MathHelper.lerp(tickDelta, player.prevZ, player.getZ());
        float yRot = player.prevBodyYaw + (player.bodyYaw - player.prevBodyYaw);
        double yRotDividedByPi = MathHelper.sin(yRot * (float) (Math.PI / 180.0));
        double negativeYRotDividedByPi = -MathHelper.cos(yRot * (float) (Math.PI / 180.0));
        float yTimesTen = (float) y * 10.0F;
        yTimesTen = MathHelper.clamp(yTimesTen, -6.0F, 32.0F);
        float something = (float) (x * yRotDividedByPi + z * negativeYRotDividedByPi) * 100.0F;
        something = MathHelper.clamp(something, 0.0F, 150.0F);
        float something1 = (float) (x * negativeYRotDividedByPi - z * yRotDividedByPi) * 100.0F;
        something1 = MathHelper.clamp(something1, -20.0F, 20.0F);
        if (something < 0.0F) {
            something = 0.0F;
        }

        float bobLerp = MathHelper.lerp(tickDelta, player.prevStrideDistance, player.strideDistance);
        yTimesTen += MathHelper.sin(MathHelper.lerp(tickDelta, player.prevHorizontalSpeed, player.horizontalSpeed) * 6.0F) * 32.0F * bobLerp;
        if (player.isInSneakingPose()) {
            yTimesTen += 25.0F;
        }

        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(6.0F + something / 2.0F + yTimesTen));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(something1 / 2.0F));
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F - something1 / 2.0F));
    }

}