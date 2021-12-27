package net.orcinus.hedgehog.client.models;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.hedgehog.entities.HedgehogEntity;

@OnlyIn(Dist.CLIENT)
public class HedgehogModel<T extends HedgehogEntity> extends EntityModel<T> {
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart right_front_leg;
	private final ModelPart left_front_leg;
	private final ModelPart right_back_leg;
	private final ModelPart left_back_leg;
	private final ModelPart left_side_spike;
	private final ModelPart left_side_spike2;
	private final ModelPart left_side_spike3;
	private final ModelPart left_side_spike4;
	private final ModelPart right_side_spike;
	private final ModelPart right_side_spike2;
	private final ModelPart right_side_spike3;
	private final ModelPart right_side_spike4;

	public HedgehogModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.right_front_leg = root.getChild("right_front_leg");
		this.left_front_leg = root.getChild("left_front_leg");
		this.right_back_leg = root.getChild("right_back_leg");
		this.left_back_leg = root.getChild("left_back_leg");
		this.left_side_spike = root.getChild("left_side_spike");
		this.left_side_spike2 = root.getChild("left_side_spike2");
		this.left_side_spike3 = root.getChild("left_side_spike3");
		this.left_side_spike4 = root.getChild("left_side_spike4");
		this.right_side_spike = root.getChild("right_side_spike");
		this.right_side_spike2 = root.getChild("right_side_spike2");
		this.right_side_spike3 = root.getChild("right_side_spike3");
		this.right_side_spike4 = root.getChild("right_side_spike4");

	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 18.5F, -5.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 36).addBox(-5.0F, -9.0F, -11.0F, 10.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.5F, 9.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition ear = head.addOrReplaceChild("ear", CubeListBuilder.create().texOffs(7, 6).addBox(5.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(7, 0).addBox(-8.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(36, 24).addBox(-2.0F, -1.0F, -4.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, -2.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(10, 0).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 3.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(16, 46).addBox(-6.0F, -9.0F, -3.0F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition spike1 = body.addOrReplaceChild("spike1", CubeListBuilder.create(), PartPose.offset(0.0F, -12.6047F, -5.1038F));

		PartDefinition spike1_r1 = spike1.addOrReplaceChild("spike1_r1", CubeListBuilder.create().texOffs(36, 18).addBox(-6.0F, 1.0F, -3.0F, 12.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.2321F, 3.5981F, -0.5236F, 0.0F, 0.0F));

		PartDefinition spike2 = body.addOrReplaceChild("spike2", CubeListBuilder.create(), PartPose.offset(0.0F, -12.8368F, -1.5058F));

		PartDefinition spike2_r1 = spike2.addOrReplaceChild("spike2_r1", CubeListBuilder.create().texOffs(36, 18).addBox(-6.0F, -1.0F, 0.0F, 12.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition spike3 = body.addOrReplaceChild("spike3", CubeListBuilder.create(), PartPose.offset(0.0F, -12.5688F, 2.9583F));

		PartDefinition spike3_r1 = spike3.addOrReplaceChild("spike3_r1", CubeListBuilder.create().texOffs(36, 22).addBox(-6.0F, -1.0F, 0.0F, 12.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition spike4 = body.addOrReplaceChild("spike4", CubeListBuilder.create(), PartPose.offset(0.0F, -12.3009F, 7.4224F));

		PartDefinition spike5_r1 = spike4.addOrReplaceChild("spike5_r1", CubeListBuilder.create().texOffs(36, 20).addBox(-6.0F, -1.0F, 0.0F, 12.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(36, 20).addBox(-6.0F, -1.0F, 0.0F, 12.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 22.5F, -4.5F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 22.5F, -4.5F));

		PartDefinition right_back_leg = partdefinition.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 22.5F, 5.5F));

		PartDefinition left_back_leg = partdefinition.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 22.5F, 5.5F));

		PartDefinition left_side_spike = partdefinition.addOrReplaceChild("left_side_spike", CubeListBuilder.create(), PartPose.offset(6.5716F, 16.6632F, -1.405F));

		PartDefinition left_side_spike_r1 = left_side_spike.addOrReplaceChild("left_side_spike_r1", CubeListBuilder.create().texOffs(36, 18).addBox(-3.5F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 1.5708F));

		PartDefinition left_side_spike2 = partdefinition.addOrReplaceChild("left_side_spike2", CubeListBuilder.create(), PartPose.offset(6.4852F, 16.6632F, 1.7561F));

		PartDefinition left_side_spike_2_r1 = left_side_spike2.addOrReplaceChild("left_side_spike_2_r1", CubeListBuilder.create().texOffs(36, 18).addBox(-3.5F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 1.5708F));

		PartDefinition left_side_spike3 = partdefinition.addOrReplaceChild("left_side_spike3", CubeListBuilder.create(), PartPose.offset(6.3989F, 16.6632F, 4.9172F));

		PartDefinition left_side_spike_3_r1 = left_side_spike3.addOrReplaceChild("left_side_spike_3_r1", CubeListBuilder.create().texOffs(36, 18).addBox(-3.5F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 1.5708F));

		PartDefinition left_side_spike4 = partdefinition.addOrReplaceChild("left_side_spike4", CubeListBuilder.create(), PartPose.offset(6.3125F, 16.6632F, 8.0783F));

		PartDefinition left_side_spike_4_r1 = left_side_spike4.addOrReplaceChild("left_side_spike_4_r1", CubeListBuilder.create().texOffs(36, 18).addBox(-3.5F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 1.5708F));

		PartDefinition right_side_spike = partdefinition.addOrReplaceChild("right_side_spike", CubeListBuilder.create(), PartPose.offset(-6.4977F, 16.6632F, -1.6572F));

		PartDefinition right_side_spike_r1 = right_side_spike.addOrReplaceChild("right_side_spike_r1", CubeListBuilder.create().texOffs(36, 18).addBox(-3.5F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 1.5708F));

		PartDefinition right_side_spike2 = partdefinition.addOrReplaceChild("right_side_spike2", CubeListBuilder.create(), PartPose.offset(-6.4113F, 16.6632F, 1.5039F));

		PartDefinition right_side_spike2_r1 = right_side_spike2.addOrReplaceChild("right_side_spike2_r1", CubeListBuilder.create().texOffs(36, 18).addBox(-3.5F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 1.5708F));

		PartDefinition right_side_spike3 = partdefinition.addOrReplaceChild("right_side_spike3", CubeListBuilder.create(), PartPose.offset(-6.325F, 16.6632F, 4.665F));

		PartDefinition right_side_spike3_r1 = right_side_spike3.addOrReplaceChild("right_side_spike3_r1", CubeListBuilder.create().texOffs(36, 18).addBox(-3.5F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 1.5708F));

		PartDefinition right_side_spike4 = partdefinition.addOrReplaceChild("right_side_spike4", CubeListBuilder.create(), PartPose.offset(-6.2386F, 16.6632F, 7.8261F));

		PartDefinition right_side_spike4_r1 = right_side_spike4.addOrReplaceChild("right_side_spike4_r1", CubeListBuilder.create().texOffs(36, 18).addBox(-3.5F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 1.5708F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		int i = entity.getSnifingTicks();
		this.head.getChild("nose").xRot = i > 0 ? -Mth.abs(Mth.sin(ageInTicks) * 0.3F) : 0.0F;
		this.head.getChild("ear").xRot = !entity.isInSittingPose() ? Mth.sin(ageInTicks * 1.0F * 0.2F) * 0.3F : 0.0F;
		this.head.xRot = Mth.sin(limbSwing) * limbSwingAmount;
		this.body.xRot = Mth.sin(limbSwing) * limbSwingAmount * 0.25F;
		this.right_back_leg.yRot = Mth.sin(limbSwing) * limbSwingAmount * 0.5F;
		this.right_back_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
		this.left_back_leg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount;
		this.left_back_leg.yRot = Mth.sin(limbSwing) * limbSwingAmount * 0.5F;
		this.right_front_leg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.0F * limbSwingAmount;
		this.right_front_leg.yRot = Mth.sin(limbSwing) * limbSwingAmount * 0.5F;
		this.left_front_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
		this.left_front_leg.yRot = Mth.sin(limbSwing) * limbSwingAmount * 0.5F;
	}

	@Override
	public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float ageInTicks) {
		this.left_back_leg.visible = !entity.isInSittingPose();
		this.right_back_leg.visible = !entity.isInSittingPose();
		this.left_front_leg.visible = !entity.isInSittingPose();
		this.right_front_leg.visible = !entity.isInSittingPose();
		this.head.xRot = entity.getHeadRollAngle(ageInTicks);
		if (entity.isInSittingPose()) {
			this.head.getChild("ear").setPos(0.0F, 1.0F, -1.5F);
			this.body.setPos(0.0F, 26.5F, -3.0F);
			this.left_side_spike.setPos(7.0716F, 19.6632F, -3.405F);
			this.left_side_spike2.setPos(6.9852F, 19.6632F, -0.905F);
			this.left_side_spike3.setPos(6.8989F, 19.6632F, 1.595F);
			this.left_side_spike4.setPos(6.8125F, 19.6632F, 4.095F);
			this.right_side_spike.setPos(-6.9977F, 19.6632F, -4.1572F);
			this.right_side_spike2.setPos(-6.9113F, 19.6632F, -0.9961F);
			this.right_side_spike3.setPos(-6.825F, 19.6632F, 2.165F);
			this.right_side_spike4.setPos(-6.7386F, 19.6632F, 5.3261F);
		} else {
			this.head.getChild("ear").setPos(0.0F, -2.0F, 0.0F);
			this.left_back_leg.setPos(3.0F, 22.5F, 5.5F);
			this.right_front_leg.setPos(-3.0F, 22.5F, -4.5F);
			this.right_back_leg.setPos(-3.0F, 22.5F, 5.5F);
			this.left_front_leg.setPos(3.0F, 22.5F, -4.5F);
			this.body.setPos(0.0F, 24.0F, 0.0F);
			this.left_side_spike.setPos(6.5716F, 16.6632F, -1.405F);
			this.left_side_spike2.setPos(6.4852F, 16.6632F, 1.7561F);
			this.left_side_spike3.setPos(6.3989F, 16.6632F, 4.9172F);
			this.left_side_spike4.setPos(6.3125F, 16.6632F, 8.0783F);
			this.right_side_spike.setPos(-6.4977F, 16.6632F, -1.6572F);
			this.right_side_spike2.setPos(-6.4113F, 16.6632F, 1.5039F);
			this.right_side_spike3.setPos(-6.325F, 16.6632F, 4.665F);
			this.right_side_spike4.setPos(-6.2386F, 16.6632F, 7.8261F);
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (this.young) {
			poseStack.pushPose();
			poseStack.translate(0.0D, (10 / 16.0F), (4 / 16.0F));
			poseStack.popPose();
			poseStack.pushPose();
			float f1 = 1.0F / 2.0F;
			poseStack.scale(f1, f1, f1);
			poseStack.translate(0.0D, (24.0F / 16.0F), 0.0D);
			ImmutableList.of(this.left_side_spike, this.left_side_spike2, this.left_side_spike3, this.left_side_spike4, this.right_side_spike, this.right_side_spike2, this.right_side_spike3, this.right_side_spike4, this.head, this.body, this.left_back_leg, this.right_back_leg, this.right_front_leg, this.left_front_leg).forEach((model) -> {
				model.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			});
			poseStack.popPose();
		} else {
			this.head.render(poseStack, buffer, packedLight, packedOverlay);
			this.body.render(poseStack, buffer, packedLight, packedOverlay);
			this.right_front_leg.render(poseStack, buffer, packedLight, packedOverlay);
			this.left_front_leg.render(poseStack, buffer, packedLight, packedOverlay);
			this.right_back_leg.render(poseStack, buffer, packedLight, packedOverlay);
			this.left_back_leg.render(poseStack, buffer, packedLight, packedOverlay);
			left_side_spike.render(poseStack, buffer, packedLight, packedOverlay);
			left_side_spike2.render(poseStack, buffer, packedLight, packedOverlay);
			left_side_spike3.render(poseStack, buffer, packedLight, packedOverlay);
			left_side_spike4.render(poseStack, buffer, packedLight, packedOverlay);
			right_side_spike.render(poseStack, buffer, packedLight, packedOverlay);
			right_side_spike2.render(poseStack, buffer, packedLight, packedOverlay);
			right_side_spike3.render(poseStack, buffer, packedLight, packedOverlay);
			right_side_spike4.render(poseStack, buffer, packedLight, packedOverlay);
		}
	}
}