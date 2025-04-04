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
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class HappyGhastModel extends EntityModel<HappyGhastRenderState> {
	private final ModelPart body;
	private final ModelPart[] tentacles = new ModelPart[9];

	public HappyGhastModel(ModelPart root) {
		super(root);
		this.body = root.getChild("body");

		for (int i = 0; i < this.tentacles.length; i++) {
			this.tentacles[i] = this.body.getChild(createTentacleName(i));
		}
	}

	@Contract(pure = true)
	private static @NotNull String createTentacleName(int i) {
		return "tentacle" + i;
	}

	public static @NotNull LayerDefinition createBabyBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild(
			"body",
			CubeListBuilder.create()
				.texOffs(0, 0).addBox(-8F, -16F, -8F, 16F, 16F, 16F, new CubeDeformation(0F))
				.texOffs(0, 32).addBox(-8F, -16F, -8F, 16F, 16F, 16F, new CubeDeformation(-0.5F)),
			PartPose.offset(0F, 24F, 0F)
		);

		PartDefinition tentacle0 = body.addOrReplaceChild(
			"tentacle0",
			CubeListBuilder.create()
				.texOffs(0, 0).addBox(-1F, 0F, -1F, 2F, 5F, 2F, new CubeDeformation(0F)),
			PartPose.offset(-3.8F, -1F, -5F)
		);
		PartDefinition tentacle1 = body.addOrReplaceChild(
			"tentacle1",
			CubeListBuilder.create()
				.texOffs(0, 0).addBox(-1F, 0F, -1F, 2F, 7F, 2F, new CubeDeformation(0F)),
			PartPose.offset(1.3F, -1F, -5F)
		);
		PartDefinition tentacle2 = body.addOrReplaceChild(
			"tentacle2",
			CubeListBuilder.create()
				.texOffs(0, 0).addBox(-1F, 0F, -1F, 2F, 4F, 2F, new CubeDeformation(0F)),
			PartPose.offset(6.3F, -1F, -5F)
		);
		PartDefinition tentacle3 = body.addOrReplaceChild(
			"tentacle3",
			CubeListBuilder.create()
				.texOffs(0, 0).addBox(-1F, 0F, -1F, 2F, 5F, 2F, new CubeDeformation(0F)),
			PartPose.offset(-6.3F, -1F, 0F)
		);
		PartDefinition tentacle4 = body.addOrReplaceChild(
			"tentacle4",
			CubeListBuilder.create()
				.texOffs(0, 0).addBox(-1F, 0F, -1F, 2F, 5F, 2F, new CubeDeformation(0F)),
			PartPose.offset(-1.3F, -1F, 0F)
		);
		PartDefinition tentacle5 = body.addOrReplaceChild(
			"tentacle5",
			CubeListBuilder.create()
				.texOffs(0, 0).addBox(-1F, 0F, -1F, 2F, 7F, 2F, new CubeDeformation(0F)),
			PartPose.offset(3.8F, -1F, 0F)
		);
		PartDefinition tentacle6 = body.addOrReplaceChild(
			"tentacle6",
			CubeListBuilder.create()
				.texOffs(0, 0).addBox(-1F, 0F, -1F, 2F, 8F, 2F, new CubeDeformation(0F)),
			PartPose.offset(-3.8F, -1F, 5F)
		);
		PartDefinition tentacle7 = body.addOrReplaceChild(
			"tentacle7",
			CubeListBuilder.create()
				.texOffs(0, 0).addBox(-1F, 0F, -1F, 2F, 8F, 2F, new CubeDeformation(0F)),
			PartPose.offset(1.3F, -1F, 5F)
		);
		PartDefinition tentacle8 = body.addOrReplaceChild(
			"tentacle8",
			CubeListBuilder.create()
				.texOffs(0, 0).addBox(-1F, 0F, -1F, 2F, 5F, 2F, new CubeDeformation(0F)),
			PartPose.offset(6.3F, -1F, 5F)
		);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public static @NotNull LayerDefinition createBodyLayer() {
		return createBabyBodyLayer().apply(MeshTransformer.scaling(4.5F));
	}

	@Override
	public void setupAnim(HappyGhastRenderState happyGhastRenderState) {
		super.setupAnim(happyGhastRenderState);

		for (int i = 0; i < this.tentacles.length; i++) {
			this.tentacles[i].xRot = 0.2F * Mth.sin(happyGhastRenderState.ageInTicks * 0.3F + i) + 0.4F;
		}
	}
}
