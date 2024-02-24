package net.orcinus.hedgehog.client.models;// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.orcinus.hedgehog.client.animations.HedgehogAnimations;
import net.orcinus.hedgehog.entities.Hedgehog;

public class HedgehogModel<T extends Hedgehog> extends AgeableHierarchicalModel<T> {
	private final ModelPart root;

	public HedgehogModel(ModelPart root) {
		super(0.6F, 16.02F);
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 1).addBox(-3.0F, -3.0F, -4.5F, 6.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -1.0F));

		PartDefinition snout = body.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -4.5F));

		PartDefinition spines_top1 = body.addOrReplaceChild("spines_top1", CubeListBuilder.create().texOffs(21, 5).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -2.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition spines_top2 = body.addOrReplaceChild("spines_top2", CubeListBuilder.create().texOffs(21, 8).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition spines_top3 = body.addOrReplaceChild("spines_top3", CubeListBuilder.create().texOffs(21, 5).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 2.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition spines_right1 = body.addOrReplaceChild("spines_right1", CubeListBuilder.create().texOffs(22, 0).mirror().addBox(-2.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -0.5F, -2.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition spines_right2 = body.addOrReplaceChild("spines_right2", CubeListBuilder.create().texOffs(26, 0).mirror().addBox(-2.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -0.5F, 0.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition spines_right3 = body.addOrReplaceChild("spines_right3", CubeListBuilder.create().texOffs(22, 0).mirror().addBox(-2.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -0.5F, 2.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition spines_left1 = body.addOrReplaceChild("spines_left1", CubeListBuilder.create().texOffs(22, 0).addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -0.5F, -2.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition spines_left2 = body.addOrReplaceChild("spines_left2", CubeListBuilder.create().texOffs(26, 0).addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -0.5F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition spines_left3 = body.addOrReplaceChild("spines_left3", CubeListBuilder.create().texOffs(22, 0).addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -0.5F, 2.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition left_ear = body.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 0.5F, -2.5F, 0.0F, -0.7854F, 0.0F));

		PartDefinition right_ear = body.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.5F, -2.5F, 0.0F, 0.7854F, 0.0F));

		PartDefinition right_hand = root.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -1.0F, -2.5F));

		PartDefinition left_hand = root.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -1.0F, -2.5F));

		PartDefinition left_foot = root.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -1.0F, 1.0F));

		PartDefinition right_foot = root.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -1.0F, 1.0F));

		PartDefinition scared_body = root.addOrReplaceChild("scared_body", CubeListBuilder.create().texOffs(0, 25).addBox(-3.0F, -3.0F, -4.5F, 6.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -1.0F));

		PartDefinition spines_top4 = scared_body.addOrReplaceChild("spines_top4", CubeListBuilder.create().texOffs(21, 29).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -2.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition spines_top5 = scared_body.addOrReplaceChild("spines_top5", CubeListBuilder.create().texOffs(21, 32).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition spines_top6 = scared_body.addOrReplaceChild("spines_top6", CubeListBuilder.create().texOffs(21, 29).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 2.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition spines_right4 = scared_body.addOrReplaceChild("spines_right4", CubeListBuilder.create().texOffs(22, 24).mirror().addBox(-2.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -0.5F, -2.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition spines_right5 = scared_body.addOrReplaceChild("spines_right5", CubeListBuilder.create().texOffs(26, 24).mirror().addBox(-2.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -0.5F, 0.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition spines_right6 = scared_body.addOrReplaceChild("spines_right6", CubeListBuilder.create().texOffs(22, 24).mirror().addBox(-2.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -0.5F, 2.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition spines_left4 = scared_body.addOrReplaceChild("spines_left4", CubeListBuilder.create().texOffs(22, 24).addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -0.5F, -2.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition spines_left5 = scared_body.addOrReplaceChild("spines_left5", CubeListBuilder.create().texOffs(26, 24).addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition spines_left6 = scared_body.addOrReplaceChild("spines_left6", CubeListBuilder.create().texOffs(22, 24).addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -0.5F, 2.0F, 0.0F, -0.2618F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 40);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		ModelPart modelPart = this.root;
		if (entity.isHiding()) {
			modelPart.getChild("scared_body").visible = true;
			modelPart.getChild("body").visible = false;
			modelPart.getChild("right_hand").visible = false;
			modelPart.getChild("left_hand").visible = false;
			modelPart.getChild("right_foot").visible = false;
			modelPart.getChild("left_foot").visible = false;
		} else {
			modelPart.getChild("scared_body").visible = false;
			modelPart.getChild("body").visible = true;
			modelPart.getChild("right_hand").visible = true;
			modelPart.getChild("left_hand").visible = true;
			modelPart.getChild("right_foot").visible = true;
			modelPart.getChild("left_foot").visible = true;
		}
		this.animateWalk(HedgehogAnimations.HEDGEHOG_WALK, limbSwing, limbSwingAmount, 2.0F, 2.5F);
		this.animate(entity.idleAnimationState, HedgehogAnimations.HEDGEHOG_IDLE, ageInTicks, 1.0F);
		this.animate(entity.splinterAnimationState, HedgehogAnimations.HEDGEHOG_SPLINTER, ageInTicks, 1.0F);
		this.animate(entity.hidingSplinterAnimationState, HedgehogAnimations.HEDGEHOG_HIDE_SPLINTER, ageInTicks, 1.0F);
		this.animate(entity.hideAnimationState, HedgehogAnimations.HEDGEHOG_HIDE, ageInTicks, 1.0F);
		this.animate(entity.hidingIdleAnimationState, HedgehogAnimations.HEDGEHOG_HIDE_IDLE, ageInTicks, 1.0F);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}