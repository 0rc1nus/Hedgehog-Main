package net.orcinus.hedgehog.client.models.old;

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
import net.orcinus.hedgehog.entities.HedgehogEntity;

public class OldHedgehogScaredModel<T extends HedgehogEntity> extends EntityModel<T> {
	private final ModelPart body;

	public OldHedgehogScaredModel(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 27.0F, -3.0F));

		PartDefinition spike1 = body.addOrReplaceChild("spike1", CubeListBuilder.create(), PartPose.offset(0.0F, -12.8368F, -1.5058F));

		PartDefinition spike9_r1 = spike1.addOrReplaceChild("spike9_r1", CubeListBuilder.create().texOffs(35, 18).addBox(-3.5F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 5.5F, -1.0F, 0.3491F, 0.0F, 1.5708F));

		PartDefinition spike8_r1 = spike1.addOrReplaceChild("spike8_r1", CubeListBuilder.create().texOffs(35, 20).addBox(-3.5F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 5.5F, 3.0F, 0.3491F, 0.0F, 1.5708F));

		PartDefinition spike7_r1 = spike1.addOrReplaceChild("spike7_r1", CubeListBuilder.create().texOffs(35, 22).addBox(-3.5F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 5.5F, 7.0F, 0.3491F, 0.0F, 1.5708F));

		PartDefinition spike6_r1 = spike1.addOrReplaceChild("spike6_r1", CubeListBuilder.create().texOffs(35, 24).addBox(-3.5F, -1.5F, -1.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 5.5F, 11.0F, 0.3491F, 0.0F, 1.5708F));

		PartDefinition spike5_r1 = spike1.addOrReplaceChild("spike5_r1", CubeListBuilder.create().texOffs(35, 26).addBox(-3.5F, -0.5F, -1.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 5.5F, 11.0F, -0.3491F, 0.0F, 1.5708F));

		PartDefinition spike4_r1 = spike1.addOrReplaceChild("spike4_r1", CubeListBuilder.create().texOffs(0, 36).addBox(-3.5F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 5.5F, 7.0F, -0.3491F, 0.0F, 1.5708F));

		PartDefinition spike3_r1 = spike1.addOrReplaceChild("spike3_r1", CubeListBuilder.create().texOffs(36, 0).addBox(-3.5F, -1.0F, 0.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 5.5F, 3.0F, -0.3491F, 0.0F, 1.5708F));

		PartDefinition spike2_r1 = spike1.addOrReplaceChild("spike2_r1", CubeListBuilder.create().texOffs(36, 2).addBox(-3.5F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 5.5F, -1.0F, -0.3491F, 0.0F, 1.5708F));

		PartDefinition spike1_r1 = spike1.addOrReplaceChild("spike1_r1", CubeListBuilder.create().texOffs(24, 34).addBox(-6.0F, -0.5F, -1.0F, 12.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition spike2 = body.addOrReplaceChild("spike2", CubeListBuilder.create(), PartPose.offset(0.0F, -12.8368F, -1.5058F));

		PartDefinition spike2_r2 = spike2.addOrReplaceChild("spike2_r2", CubeListBuilder.create().texOffs(0, 34).addBox(-6.0F, -1.75F, 1.0F, 12.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition spike3 = body.addOrReplaceChild("spike3", CubeListBuilder.create(), PartPose.offset(0.0F, -12.8368F, -1.5058F));

		PartDefinition spike3_r2 = spike3.addOrReplaceChild("spike3_r2", CubeListBuilder.create().texOffs(24, 32).addBox(-6.0F, -0.75F, -1.0F, 12.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.2679F, 6.4641F, -0.5236F, 0.0F, 0.0F));

		PartDefinition spike4 = body.addOrReplaceChild("spike4", CubeListBuilder.create(), PartPose.offset(0.0F, -12.8368F, -1.5058F));

		PartDefinition spike4_r2 = spike4.addOrReplaceChild("spike4_r2", CubeListBuilder.create().texOffs(0, 32).addBox(-6.0F, -0.5F, -2.0F, 12.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5359F, 10.9282F, -0.5236F, 0.0F, 0.0F));

		PartDefinition inner = body.addOrReplaceChild("inner", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -9.0F, -7.0F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition outer = body.addOrReplaceChild("outer", CubeListBuilder.create().texOffs(0, 18).addBox(-6.0F, -12.0F, -6.5F, 12.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

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
			this.body.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			poseStack.popPose();
		} else {
			this.body.render(poseStack, buffer, packedLight, packedOverlay);
		}
	}
}