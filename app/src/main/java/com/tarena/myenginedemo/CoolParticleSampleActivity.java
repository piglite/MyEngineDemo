package com.tarena.myenginedemo;

import android.opengl.GLES20;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
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

public class CoolParticleSampleActivity extends SimpleBaseGameActivity {

    BuildableBitmapTextureAtlas atlas;
    ITextureRegion region;

    @Override
    public EngineOptions onCreateEngineOptions() {

        Camera camera = new Camera(0, 0, 800, 480);
        EngineOptions op = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), camera);
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
        atlas = new BuildableBitmapTextureAtlas(getTextureManager(), 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, this, "particle_fire.png");
        try {
            atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }
        atlas.load();
        ;
    }

    @Override
    protected Scene onCreateScene() {
        Scene scene = new Scene();
        scene.setBackground(new Background(Color.BLACK));
        {
            SpriteParticleSystem particleSystem = new SpriteParticleSystem(
                    new PointParticleEmitter(0, 480), 6, 10, 200, region, getVertexBufferObjectManager()
            );

            particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
            //生成粒子时为粒子提供一个初速度，其中水平方向的速度为(15,22)之间，保证粒子向屏幕右侧移动；垂直方向速度为(60,90)之间，保证粒子向屏幕下方移动
            particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(15, 22, -60, -90));
            //如果没有AccelerationParticleInitializer的话，那么创建出来的粒子将以恒定不变的速度持续移动11.5秒(ExpireParticleInitializer决定的)
            //在有AccelerationParticleInitializer的情况下，横向速度每秒增加5，纵向速度每秒增加15
            particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(5, 15));
            //生成粒子时让粒子有一个0~360之间的旋转角度
            particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
            //生成粒子时粒子的颜色为红色
            particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1.0f, 0.0f, 0.0f));
            particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(11.5f));
            //粒子生成之后，在0~11.5秒之间从原始大小的一半放大到原始大小的一倍
            particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 11.5f, 0.5f, 2.0f));
            particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(2.5f, 3.5f, 1.0f, 0.0f));
            particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(3.5f, 4.5f, 0.0f, 1.0f));
            //粒子生成后，在0~11.5秒之间从原始的红色逐渐的变成蓝色
            particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0.0f, 11.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f));
            particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(4.5f, 11.5f, 1.0f, 0.0f));

            scene.attachChild(particleSystem);
        }
        {
            SpriteParticleSystem particleSystem = new SpriteParticleSystem(new PointParticleEmitter
                    (800 - 32, 480), 8, 12, 200, region, this.getVertexBufferObjectManager());
            particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
            particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-15, -22, -60, -90));
            particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(-5, 15));
            particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
            particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(0.0f, 0.0f, 1.0f));
            particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(11.5f));

            particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 0.5f, 2f));
            particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(2.5f, 3.5f, 1.0f, 0.0f));
            particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(3.5f, 4.5f, 0.0f, 1.0f));
            particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0.0f, 11.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f));
            particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(4.5f, 11.5f, 1.0f, 0.0f));

            scene.attachChild(particleSystem);
        }

        return scene;
    }
}
