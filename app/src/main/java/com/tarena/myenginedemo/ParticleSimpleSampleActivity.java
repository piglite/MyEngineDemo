package com.tarena.myenginedemo;

import android.opengl.GLES20;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.CircleOutlineParticleEmitter;
import org.andengine.entity.particle.initializer.AlphaParticleInitializer;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import java.io.IOException;

import static com.tarena.myenginedemo.Config.CAMERA_HEIGHT;
import static com.tarena.myenginedemo.Config.CAMERA_WIDTH;

public class ParticleSimpleSampleActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener {

    BuildableBitmapTextureAtlas atlas;
    ITextureRegion region;
    private CircleOutlineParticleEmitter emitter;


    @Override
    public EngineOptions onCreateEngineOptions() {

        Camera c = new Camera(0,0,CAMERA_WIDTH,CAMERA_HEIGHT);
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
        atlas = new BuildableBitmapTextureAtlas(getTextureManager(),32,32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas,this,"particle_point.png");
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
        scene.setBackground(new Background(Color.BLACK));

        emitter = new CircleOutlineParticleEmitter(400,240,120);
      SpriteParticleSystem particleSystem = new SpriteParticleSystem(emitter,60,60,360,region,getVertexBufferObjectManager());

        particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1,0,0));
        particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
        particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA,GLES20.GL_ONE));
        particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-2,2,-20,10));
        particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f,360.0f));
        particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(6));

        particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0,5,1.0f,2.0f));
        particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0,3,1,1,0,0.5f,0,0));
        particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(4,6,1,1,0.5f,1,0,1));
        particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0,1,0,1));
        particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(5,6,1,0));





        scene.attachChild(particleSystem);

        scene.setOnSceneTouchListener(this);

        return scene;
    }

    /**
     * Called when a {@link TouchEvent} is dispatched to a {@link Scene}.
     *
     * @param pScene           The {@link Scene} that the {@link TouchEvent} has been dispatched to.
     * @param pSceneTouchEvent The {@link TouchEvent} object containing full information about the event.
     * @return <code>true</code> if this {@link IOnSceneTouchListener} has consumed the {@link TouchEvent}, <code>false</code> otherwise.
     */
    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

        if(pSceneTouchEvent.isActionUp()){
            emitter.setCenter(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
            return true;
        }

        return false;
    }
}
