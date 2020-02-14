package kmerrill285.trewrite.entities.models.flails;
import kmerrill285.trewrite.entities.projectiles.flails.EntityBallOHurt;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;

public class ModelBallOHurtChain extends EntityModel<EntityBallOHurt> {
	private final RendererModel Main;
	private final RendererModel Spikes;
	private final RendererModel bone2;
	private final RendererModel bone;
	private final RendererModel Spikes2;
	private final RendererModel bone3;
	private final RendererModel bone4;

	public ModelBallOHurtChain() {
		textureWidth = 64;
		textureHeight = 64;

		Main = new RendererModel(this);
		Main.setRotationPoint(0.0F, 24.0F, 0.0F);
		Main.cubeList.add(new ModelBox(Main, 0, 35, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F, false));

		Spikes = new RendererModel(this);
		Spikes.setRotationPoint(0.0F, 24.0F, 0.0F);

		bone2 = new RendererModel(this);
		bone2.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone2, 0.0F, -1.5708F, 0.0F);
		Spikes.addChild(bone2);

		bone = new RendererModel(this);
		bone.setRotationPoint(0.0F, 0.0F, 0.0F);
		Spikes.addChild(bone);

		Spikes2 = new RendererModel(this);
		Spikes2.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(Spikes2, 3.1416F, 0.0F, 0.0F);

		bone3 = new RendererModel(this);
		bone3.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone3, 0.0F, -1.5708F, 0.0F);
		Spikes2.addChild(bone3);

		bone4 = new RendererModel(this);
		bone4.setRotationPoint(0.0F, 0.0F, 0.0F);
		Spikes2.addChild(bone4);
	}

	@Override
	public void render(EntityBallOHurt entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Main.render(f5);
		Spikes.render(f5);
		Spikes2.render(f5);
	}
	public void setRotationAngle(RendererModel RendererModel, float x, float y, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}
}