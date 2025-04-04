package net.frozenblock.happierghasts.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.happierghasts.client.renderer.entity.state.HappyGhastRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class HappyGhastHarnessModel extends EntityModel<HappyGhastRenderState> {
	private final ModelPart body;
	private final ModelPart gogglesUp;
	private final ModelPart goggles;

	public HappyGhastHarnessModel(ModelPart root) {
		super(root);
		this.body = root.getChild("body");
		this.gogglesUp = this.body.getChild("goggles_up");
		this.goggles = this.body.getChild("goggles");
	}

	public static @NotNull LayerDefinition createBabyBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild(
			"body",
			CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-8F, -16.5F, -8F, 16F, 16F, 16F, new CubeDeformation(0.5F)),
			PartPose.offset(0F, 24F, 0F)
		);

		PartDefinition gogglesUp = body.addOrReplaceChild(
			"goggles_up",
			CubeListBuilder.create(),
			PartPose.offset(0F, -15.5F, -5.6F)
		).addOrReplaceChild(
			"goggles",
			CubeListBuilder.create()
				.texOffs(0, 32)
				.addBox(-8F, -2.5F, -2.5F, 16F, 5F, 5F, new CubeDeformation(0.65F)),
			PartPose.offsetAndRotation(0F, 0F, 0F, -0.7854F, 0F, 0F)
		);

		PartDefinition goggles = body.addOrReplaceChild(
			"goggles",
			CubeListBuilder.create()
				.texOffs(0, 32)
				.addBox(-8F, -1F, -2.5F, 16F, 5F, 5F, new CubeDeformation(0.65F)),
			PartPose.offset(0F, -11.5F, -5.5F)
		);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public static @NotNull LayerDefinition createBodyLayer() {
		return createBabyBodyLayer().apply(MeshTransformer.scaling(4.5F));
	}

	@Override
	public void setupAnim(HappyGhastRenderState happyGhastRenderState) {
		super.setupAnim(happyGhastRenderState);
		if (happyGhastRenderState.isBeingRidden) {
			this.gogglesUp.visible = false;
			this.goggles.visible = true;
		} else {
			this.gogglesUp.visible = true;
			this.goggles.visible = false;
		}
	}
}
