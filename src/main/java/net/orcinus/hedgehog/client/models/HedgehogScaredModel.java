package net.orcinus.hedgehog.client.models;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
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
import net.orcinus.hedgehog.entities.Hedgehog;

@SuppressWarnings("FieldCanBeLocal, unused")
@OnlyIn(Dist.CLIENT)
public class HedgehogScaredModel<T extends Hedgehog> extends AgeableListModel<T> {
	private final ModelPart root;

	private final ModelPart body;
	private final ModelPart spines_top0;
	private final ModelPart spines_top1;
	private final ModelPart spines_top2;
	private final ModelPart spines_top3;
	private final ModelPart spines_front0;
	private final ModelPart spines_front1;
	private final ModelPart spines_left0;
	private final ModelPart spines_left1;
	private final ModelPart spines_left2;
	private final ModelPart spines_left3;
	private final ModelPart spines_right0;
	private final ModelPart spines_right1;
	private final ModelPart spines_right2;
	private final ModelPart spines_right3;

	public HedgehogScaredModel(ModelPart root) {
		this.root = root;

		this.body = root.getChild("body");

		this.spines_top0 = body.getChild("spines_top0");
		this.spines_top1 = body.getChild("spines_top1");
		this.spines_top2 = body.getChild("spines_top2");
		this.spines_top3 = body.getChild("spines_top3");
		this.spines_right0 = body.getChild("spines_right0");
		this.spines_right1 = body.getChild("spines_right1");
		this.spines_right2 = body.getChild("spines_right2");
		this.spines_right3 = body.getChild("spines_right3");
		this.spines_left0 = body.getChild("spines_left0");
		this.spines_left1 = body.getChild("spines_left1");
		this.spines_left2 = body.getChild("spines_left2");
		this.spines_left3 = body.getChild("spines_left3");
		this.spines_front0 = body.getChild("spines_front0");
		this.spines_front1 = body.getChild("spines_front1");
	}

	public static LayerDefinition createBodyLayer() {

		MeshDefinition data = new MeshDefinition();
		PartDefinition root = data.getRoot();

		PartDefinition body = root.addOrReplaceChild(
			"body",
			CubeListBuilder.create()
							.texOffs(0, 1)
							.mirror(false)
							.addBox(-3.0F, -3.0F, -4.5F, 6.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(0.0F, 21.0F, -1.0F, 0.0F, 0.0F, 0.0F)
		);

		PartDefinition spines_top0 = body.addOrReplaceChild(
			"spines_top0",
			CubeListBuilder.create()
							.texOffs(21, 8)
							.mirror(false)
							.addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(0.0F, -3.0F, -4.0F, -1.0472F, 0.0F, 0.0F)
		);

		PartDefinition spines_top1 = body.addOrReplaceChild(
			"spines_top1",
			CubeListBuilder.create()
							.texOffs(21, 5)
							.mirror(false)
							.addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(0.0F, -3.0F, -2.0F, -1.0472F, 0.0F, 0.0F)
		);

		PartDefinition spines_top2 = body.addOrReplaceChild(
			"spines_top2",
			CubeListBuilder.create()
							.texOffs(21, 8)
							.mirror(false)
							.addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -1.0472F, 0.0F, 0.0F)
		);

		PartDefinition spines_top3 = body.addOrReplaceChild(
			"spines_top3",
			CubeListBuilder.create()
							.texOffs(21, 5)
							.mirror(false)
							.addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(0.0F, -3.0F, 2.0F, -1.0472F, 0.0F, 0.0F)
		);

		PartDefinition spines_front0 = body.addOrReplaceChild(
			"spines_front0",
			CubeListBuilder.create()
							.texOffs(21, 5)
							.mirror(false)
							.addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(0.0F, 2.0F, -4.5F, 0.5236F, 0.0F, 0.0F)
		);

		PartDefinition spines_front1 = body.addOrReplaceChild(
			"spines_front1",
			CubeListBuilder.create()
							.texOffs(21, 8)
							.mirror(false)
							.addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(0.0F, -1.0F, -4.5F, 0.5236F, 0.0F, 0.0F)
		);

		PartDefinition spines_left0 = body.addOrReplaceChild(
			"spines_left0",
			CubeListBuilder.create()
							.texOffs(26, 0)
							.mirror(false)
							.addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(3.0F, -0.5F, -4.0F, 0.0F, -1.0472F, 0.0F)
		);

		PartDefinition spines_left1 = body.addOrReplaceChild(
			"spines_left1",
			CubeListBuilder.create()
							.texOffs(22, 0)
							.mirror(false)
							.addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(3.0F, -0.5F, -2.0F, 0.0F, -1.0472F, 0.0F)
		);

		PartDefinition spines_left2 = body.addOrReplaceChild(
			"spines_left2",
			CubeListBuilder.create()
							.texOffs(26, 0)
							.mirror(false)
							.addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(3.0F, -0.5F, 0.0F, 0.0F, -1.0472F, 0.0F)
		);

		PartDefinition spines_left3 = body.addOrReplaceChild(
			"spines_left3",
			CubeListBuilder.create()
							.texOffs(22, 0)
							.mirror(false)
							.addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(3.0F, -0.5F, 2.0F, 0.0F, -1.0472F, 0.0F)
		);

		PartDefinition spines_right0 = body.addOrReplaceChild(
			"spines_right0",
			CubeListBuilder.create()
							.texOffs(26, 0)
							.mirror(true)
							.addBox(-2.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-3.0F, -0.5F, -4.0F, 0.0F, 1.0472F, 0.0F)
		);

		PartDefinition spines_right1 = body.addOrReplaceChild(
			"spines_right1",
			CubeListBuilder.create()
							.texOffs(22, 0)
							.mirror(true)
							.addBox(-2.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-3.0F, -0.5F, -2.0F, 0.0F, 1.0472F, 0.0F)
		);

		PartDefinition spines_right2 = body.addOrReplaceChild(
			"spines_right2",
			CubeListBuilder.create()
							.texOffs(26, 0)
							.mirror(true)
							.addBox(-2.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-3.0F, -0.5F, 0.0F, 0.0F, 1.0472F, 0.0F)
		);

		PartDefinition spines_right3 = body.addOrReplaceChild(
			"spines_right3",
			CubeListBuilder.create()
							.texOffs(22, 0)
							.mirror(true)
							.addBox(-2.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-3.0F, -0.5F, 2.0F, 0.0F, 1.0472F, 0.0F)
		);

		return LayerDefinition.create(data, 32, 16);
	}

	@Override
	public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		float speed = 1.0f;
		float degree = 1.0f;
		this.body.zRot = Mth.cos(animationProgress * speed * 0.4F) * degree * 0.2F * 0.25F;
		this.spines_top0.xRot = Mth.cos(animationProgress * speed * 0.8F) * degree * -0.6F * 0.25F - 1.0F;
		this.spines_top1.xRot = Mth.cos(animationProgress * speed * 0.8F) * degree * 0.6F * 0.25F - 1.0F;
		this.spines_top2.xRot = Mth.cos(animationProgress * speed * 0.8F) * degree * -0.6F * 0.25F - 1.0F;
		this.spines_top3.xRot = Mth.cos(animationProgress * speed * 0.8F) * degree * 0.6F * 0.25F - 1.0F;
		this.spines_right0.yRot = Mth.cos(animationProgress * speed * 0.8F) * degree * -0.6F * 0.25F + 1.0F;
		this.spines_right1.yRot = Mth.cos(animationProgress * speed * 0.8F) * degree * 0.6F * 0.25F + 1.0F;
		this.spines_right2.yRot = Mth.cos(animationProgress * speed * 0.8F) * degree * -0.6F * 0.25F + 1.0F;
		this.spines_right3.yRot = Mth.cos(animationProgress * speed * 0.8F) * degree * 0.6F * 0.25F + 1.0F;
		this.spines_left0.yRot = Mth.cos(animationProgress * speed * 0.8F) * degree * 0.6F * 0.25F - 1.0F;
		this.spines_left1.yRot = Mth.cos(animationProgress * speed * 0.8F) * degree * -0.6F * 0.25F - 1.0F;
		this.spines_left2.yRot = Mth.cos(animationProgress * speed * 0.8F) * degree * 0.6F * 0.25F - 1.0F;
		this.spines_left3.yRot = Mth.cos(animationProgress * speed * 0.8F) * degree * -0.6F * 0.25F - 1.0F;
		this.spines_front1.xRot = Mth.cos(animationProgress * speed * 0.8F) * degree * -0.6F * 0.25F + 0.5F;
		this.spines_front0.xRot = Mth.cos(animationProgress * speed * 0.8F) * degree * 0.6F * 0.25F + 0.5F;
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body);
	}

}