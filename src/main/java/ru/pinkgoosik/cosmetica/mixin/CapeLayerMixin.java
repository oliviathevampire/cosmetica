package ru.pinkgoosik.cosmetica.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.cosmetica.util.ColorUtil;
import ru.pinkgoosik.cosmetica.util.DyeUtils;
import ru.pinkgoosik.cosmetica.data.PlayerCloaks;

@Mixin(CapeFeatureRenderer.class)
public abstract class CapeLayerMixin extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

	public CapeLayerMixin(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> renderLayerParent) {
		super(renderLayerParent);
	}

//	@Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
//	public void render(MatrixStack poseStack, VertexConsumerProvider vertexConsumerProvider, int light, AbstractClientPlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, CallbackInfo info) {
//		if (player.canRenderCapeTexture() && !player.isInvisible() && player.isPartVisible(PlayerModelPart.CAPE) && player.getCapeTexture() != null) {
//			for (PlayerCloaks.Entry entry : PlayerCloaks.ENTRIES) {
//				if (entry.playerUuid().equals(player.getGameProfile().getId().toString())) {
//					ItemStack itemStack = player.getEquippedStack(EquipmentSlot.CHEST);
//					if (!(itemStack.getItem() instanceof ElytraItem)) {
//						poseStack.push();
//						poseStack.translate(0.0, 0.0, 0.125);
//						double x = MathHelper.lerp(tickDelta, player.prevCapeX, player.capeX) - MathHelper.lerp(tickDelta, player.prevX, player.getX());
//						double y = MathHelper.lerp(tickDelta, player.prevCapeY, player.capeY) - MathHelper.lerp(tickDelta, player.prevY, player.getY());
//						double z = MathHelper.lerp(tickDelta, player.prevCapeZ, player.capeZ) - MathHelper.lerp(tickDelta, player.prevZ, player.getZ());
//						float yRot = player.prevBodyYaw + (player.bodyYaw - player.prevBodyYaw);
//						double yRotDividedByPi = MathHelper.sin(yRot * (float) (Math.PI / 180.0));
//						double negativeYRotDividedByPi = -MathHelper.cos(yRot * (float) (Math.PI / 180.0));
//						float yTimesTen = (float) y * 10.0F;
//						yTimesTen = MathHelper.clamp(yTimesTen, -6.0F, 32.0F);
//						float something = (float) (x * yRotDividedByPi + z * negativeYRotDividedByPi) * 100.0F;
//						something = MathHelper.clamp(something, 0.0F, 150.0F);
//						float something1 = (float) (x * negativeYRotDividedByPi - z * yRotDividedByPi) * 100.0F;
//						something1 = MathHelper.clamp(something1, -20.0F, 20.0F);
//						if (something < 0.0F) {
//							something = 0.0F;
//						}
//
//						float bobLerp = MathHelper.lerp(tickDelta, player.prevStrideDistance, player.strideDistance);
//						yTimesTen += MathHelper.sin(MathHelper.lerp(tickDelta, player.prevHorizontalSpeed, player.horizontalSpeed) * 6.0F) * 32.0F * bobLerp;
//						if (player.isInSneakingPose()) {
//							yTimesTen += 25.0F;
//						}
//
//						poseStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(6.0F + something / 2.0F + yTimesTen));
//						poseStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(something1 / 2.0F));
//						poseStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F - something1 / 2.0F));
//
//						for (String type : entry.type().split("\\|")) {
//							if (type.equals("jeb")) {
//								float[] color = DyeUtils.createJebColorTransition(player, tickDelta);
//								this.getContextModel().renderCape(poseStack, ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(new Identifier("cosmetica:textures/cape/cape_layer1.png")), false, entry.type().contains("enchanted")), light, OverlayTexture.DEFAULT_UV);
//								((PlayerEntityModelAccessor) this.getContextModel()).getCloak().render(poseStack, ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(new Identifier("cosmetica:textures/cape/cape_layer2.png")), false, entry.type().contains("enchanted")), light, OverlayTexture.DEFAULT_UV, color[0], color[1], color[2], 1.0F);
//							}
//							if (type.equals("cosmic")) {
//								((PlayerEntityModelAccessor) this.getContextModel()).getCloak().render(poseStack, ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getEndGateway(), false, entry.type().contains("enchanted")), light, OverlayTexture.DEFAULT_UV);
//							}
//							if (type.equals("swirly")) {
//								float f = player.age + tickDelta;
//								((PlayerEntityModelAccessor) this.getContextModel()).getCloak().render(poseStack, ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getEnergySwirl(new Identifier("textures/entity/creeper/creeper_armor.png"), f * 0.01F % 1.0F, f * 0.01F % 1.0F), false, entry.type().contains("enchanted")), light, OverlayTexture.DEFAULT_UV);
//							}
//							if (type.equals("glowing")) {
//								float[] color = ColorUtil.toFloatArray(ColorUtil.color(entry.color().replace("0x", "")));
//								((PlayerEntityModelAccessor) this.getContextModel()).getCloak().render(poseStack, ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getLightning(), false, entry.type().contains("enchanted")), light, OverlayTexture.DEFAULT_UV, color[0], color[1], color[2], color[3]);
//							}
//							if (type.equals("normal")) {
//								((PlayerEntityModelAccessor) this.getContextModel()).getCloak().render(poseStack, ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(player.getCapeTexture()), false, entry.type().contains("enchanted")), light, OverlayTexture.DEFAULT_UV);
//							}
//						}
//						poseStack.pop();
//					}
//					info.cancel();
//				}
//			}
//		}
//	}
}
