package com.tarena.myenginedemo;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.particle.BatchedSpriteParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.UncoloredSprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;

import static com.tarena.myenginedemo.Config.BLUE_F;
import static com.tarena.myenginedemo.Config.GREEN_F;
import static com.tarena.myenginedemo.Config.RED_F;

public class ParticleSampleActivity extends SimpleBaseGameActivity {
    BuildableBitmapTextureAtlas atlas;
    ITexture fireTexture;
    ITextureRegion fireRegion;
    PointParticleEmitter particleEmitter = new PointParticleEmitter(400,240);
    BatchedSpriteParticleSystem particleSystem;
    @Override
    public EngineOptions onCreateEngineOptions() {
        Camera c = new Camera(0,0,800,480);
        EngineOptions op = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,new FillResolutionPolicy(),c);
        op.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        op.getRenderOptions().setDithering(true);
        op.getRenderOptions().getConfigChooserOptions().setRequestedAlphaSize(8);
        op.getRenderOptions().getConfigChooserOptions().setRequestedRedSize(8);
        op.getRenderOptions().getConfigChooserOptions().setRequestedGreenSize(8);
        op.getRenderOptions().getConfigChooserOptions().setRequestedBlueSize(8);
        return op;
    }

    @Override
    protected void onCreateResources() throws IOException {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        atlas = new BuildableBitmapTextureAtlas(getTextureManager(),33,33);
        fireRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas,this,"particle_fire.png");
        try {
            atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }
        atlas.load();


    }

    @Override
    protected Scene onCreateScene() {

        Scene scene = new Scene();
        scene.setBackground(new Background(RED_F,GREEN_F,BLUE_F));
        particleSystem = new BatchedSpriteParticleSystem(
                particleEmitter,
                15,
                50,
                500,
                fireRegion,
                getVertexBufferObjectManager());
        particleSystem.addParticleInitializer(new AccelerationParticleInitializer<UncoloredSprite>(25f,-25f,50f,100f));
        particleSystem.addParticleInitializer(new ExpireParticleInitializer<UncoloredSprite>(3,6));
        particleSystem.addParticleModifier(new ScaleParticleModifier<UncoloredSprite>(0,3,0.2f,1f));
        scene.attachChild(particleSystem);
        return scene;
    }
}
