package com.tarena.myenginedemo;

import android.opengl.GLES20;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.particle.BatchedSpriteParticleSystem;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.CircleParticleEmitter;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.AlphaParticleInitializer;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.OffCameraExpireParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.UncoloredSprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;

public class ParticleFireSampleActivity extends SimpleBaseGameActivity {

    BuildableBitmapTextureAtlas atlas;
    ITextureRegion smokeRegion;
    ITextureRegion fireRegion;

    @Override
    public EngineOptions onCreateEngineOptions() {
        Camera camera = new Camera(0, 0, 800, 480);
        EngineOptions op = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy()
                , camera);
        op.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        op.getRenderOptions().setDithering(true);
        return op;
    }

    @Override
    protected void onCreateResources() throws IOException {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        atlas = new BuildableBitmapTextureAtlas(getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        smokeRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, this, "smoke.png");
        fireRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, this, "smoke2.png");
        try {
            atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }
        atlas.load();

    }

    @Override
    protected Scene onCreateScene() {

        Scene scene = new Scene();
        scene.getBackground().setColor(0.0f, 0.0f, 0.0f);

        {
            BatchedSpriteParticleSystem particleSystem = new BatchedSpriteParticleSystem(
                    new CircleParticleEmitter(400, 240,60),
                    20, 40, 300, smokeRegion, getVertexBufferObjectManager());
            particleSystem.addParticleInitializer(new VelocityParticleInitializer<UncoloredSprite>(-25, 25, 20, 60));
            particleSystem.addParticleInitializer(new AccelerationParticleInitializer<UncoloredSprite>(0, 20));
            particleSystem.addParticleInitializer(new ExpireParticleInitializer<UncoloredSprite>(12f));
            particleSystem.addParticleInitializer(new ScaleParticleInitializer<UncoloredSprite>(0.1f, 0.5f));
            particleSystem.addParticleInitializer(new RotationParticleInitializer<UncoloredSprite>(0f, 360f));

            particleSystem.addParticleModifier(new OffCameraExpireParticleModifier<UncoloredSprite>(getEngine().getCamera()));
            particleSystem.addParticleModifier(new AlphaParticleModifier<UncoloredSprite>(0f, 0.5f, 0f, 0.2f));
            particleSystem.addParticleModifier(new AlphaParticleModifier<UncoloredSprite>(2f, 12f, 0.2f, 0f));
            scene.attachChild(particleSystem);
        }

        {
            SpriteParticleSystem fireParticleSystem = new SpriteParticleSystem(
                    new PointParticleEmitter(400, 40),
                    20, 30, 400, fireRegion, getVertexBufferObjectManager());
            fireParticleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
            fireParticleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1f, 0.4f, 0.1f));
            fireParticleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0f));
            fireParticleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-15, 15, 20, 90));
            fireParticleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(4.5f));
            fireParticleSystem.addParticleInitializer(new ScaleParticleInitializer<Sprite>(0.5f));
            fireParticleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0f, 360f));

            fireParticleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0f, 0.5f, 0f, 0.2f));
            fireParticleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(3f, 4.5f, 0.2f, 0f));
            fireParticleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(3f, 4.5f, 0.5f, 0f));

            scene.attachChild(fireParticleSystem);
        }


        return scene;
    }
}
