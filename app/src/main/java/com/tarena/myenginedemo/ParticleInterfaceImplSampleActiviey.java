package com.tarena.myenginedemo;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.modifier.CardinalSplineMoveModifier;
import org.andengine.entity.particle.BatchedSpriteParticleSystem;
import org.andengine.entity.particle.Particle;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.IParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.UncoloredSprite;
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

public class ParticleInterfaceImplSampleActiviey extends SimpleBaseGameActivity {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;

    public static final float HEIGHT_OFFSET = (HEIGHT - 50) / 9;

    BuildableBitmapTextureAtlas atlas;
    ITextureRegion region;

    PointParticleEmitter emitter = new PointParticleEmitter(400, 480);

    // CardinalSplineMoveModifier Modifier x coordinates
    private final float pointsX[] = {
            WIDTH / 2 - 90, // x1
            WIDTH / 2 + 90, // x2
            WIDTH / 2 - 180, // x3
            WIDTH / 2 + 180, // x4
            WIDTH / 2 - 40, // x5
            WIDTH / 2 + 40, // x6
            WIDTH / 2 - 100, // x7
            WIDTH / 2 + 100  // x8
    };

    // CardinalSplineMoveModifier Modifier y coordinates
    private final float pointsY[] = {
            HEIGHT - (HEIGHT_OFFSET * 2), // y1
            HEIGHT - (HEIGHT_OFFSET * 3), // y2
            HEIGHT - (HEIGHT_OFFSET * 4), // y3
            HEIGHT - (HEIGHT_OFFSET * 5), // y4
            HEIGHT - (HEIGHT_OFFSET * 6), // y5
            HEIGHT - (HEIGHT_OFFSET * 7), // y6
            HEIGHT - (HEIGHT_OFFSET * 8), // y7
            HEIGHT - (HEIGHT_OFFSET * 9), // y8
    };

    CardinalSplineMoveModifier.CardinalSplineMoveModifierConfig mConfig = new CardinalSplineMoveModifier.CardinalSplineMoveModifierConfig(pointsX.length, 0);


    @Override
    public EngineOptions onCreateEngineOptions() {

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), new Camera(0, 0, 800, 480));
    }

    @Override
    protected void onCreateResources() throws IOException {

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        atlas = new BuildableBitmapTextureAtlas(getTextureManager(), 48, 48);
        region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, this, "heart.png");
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
        scene.setBackground(new Background(Color.BLACK));

        BatchedSpriteParticleSystem particleSystem = new BatchedSpriteParticleSystem(emitter, 1, 2, 20, region, getVertexBufferObjectManager());

        // Initialize the sprite's color (random)
        particleSystem.addParticleInitializer(new ColorParticleInitializer<UncoloredSprite>(0, 1, 0, 1, 0, 1));

        // Add the expire modifier (particles expire after 10 seconds)
        // 粒子的整个生命周期设定为10秒
        particleSystem.addParticleInitializer(new ExpireParticleInitializer<UncoloredSprite>(10));

        // Add 4 sequential scale modifiers
        particleSystem.addParticleModifier(new ScaleParticleModifier<UncoloredSprite>(
                1, 2, 1.3f, 0.4f));//生命周期中1秒到2秒的这一秒钟，粒子体积从1.3倍变化到0.4倍
        particleSystem.addParticleModifier(new ScaleParticleModifier<UncoloredSprite>(
                3, 4, 0.4f, 1.3f));//生命周期中3秒到4秒的这一秒钟，粒子体积从0.4倍变化到1.3倍
        particleSystem.addParticleModifier(new ScaleParticleModifier<UncoloredSprite>(
                5, 6, 1.3f, .4f));//生命周期中5秒到6秒的这一秒钟，粒子体积从1.3倍变化到0.4倍
        particleSystem.addParticleModifier(new ScaleParticleModifier<UncoloredSprite>(
                7, 9, 0.4f, 1.3f));//生命周期中7秒到9秒的这二秒钟，粒子体积从0.4倍变化到1.3倍

        // Add alpha ('fade out') modifier
        particleSystem.addParticleModifier(new AlphaParticleModifier<UncoloredSprite>(
                9, 10, 1, 0));//生命周期中9秒到10秒的这一秒钟，粒子的透明度从1.0变化到0

        for (int i = 0; i < pointsX.length; i++) {
            //初始化CardinalSplineMoveModifier的Config对象，准备使用
            mConfig.setControlPoint(i, pointsX[i], pointsY[i]);
        }

        // Create a custom particle modifier via the Particle Modifier interface
        // 注意，这里需要添加的是粒子Modifier，而我们打算使用的CardinalSplineMoveModifier是实体Modifier
        // 是不可以直接作为addParticleModifier方法的参数的
        // 因此需要通过实现IParticleModifier<UncoloredSprite>接口，来完成IParticleModifier<UncoloredSprite>的引用
        // 通过粒子particle可以拿到粒子中的实体entity，然后再为entity添加实体modifier即可
        particleSystem.addParticleModifier(new IParticleModifier<UncoloredSprite>() {


            // temporary particle color values
            float red;
            float green;
            float blue;

            // color check booleans
            boolean incrementRed = true;
            boolean incrementGreen = true;
            boolean incrementBlue = true;


            // Called when a particle is created
            @Override
            public void onInitializeParticle(Particle<UncoloredSprite> pParticle) {
                // Create our movement modifier
                CardinalSplineMoveModifier moveModifier = new CardinalSplineMoveModifier(10, mConfig);

                // Register our modifier to each individual particle
                // 通过粒子拿到实体，为实体添加实体modifier
                pParticle.getEntity().registerEntityModifier(moveModifier);
            }

            // Called when a particle is updated (every frame)
            @Override
            public void onUpdateParticle(Particle<UncoloredSprite> pParticle) {
                // Get the particle's sprite/entity
                UncoloredSprite sprite = pParticle.getEntity();

                // Get the particle's current color values
                red = sprite.getRed();
                green = sprite.getGreen();
                blue = sprite.getBlue();

                // Red reversion checks
                if (red >= 0.75f)
                    incrementRed = false;
                else if (red <= 0.3f)
                    incrementRed = true;
                // Green reversion checks
                if (green >= 0.75f)
                    incrementGreen = false;
                else if (green <= 0.3f)
                    incrementGreen = true;
                // Blue reversion checks
                if (blue >= 0.75f)
                    incrementBlue = false;
                else if (blue <= 0.3f)
                    incrementBlue = true;

                // Inc/dec red value
                if (incrementRed)
                    red += 0.075f;
                else
                    red -= 0.075f;
                // Inc/dec green value
                if (incrementGreen)
                    green += 0.075f;
                else
                    green -= 0.075f;
                // Inc/dec blue value
                if (incrementBlue)
                    blue += 0.075f;
                else
                    blue -= 0.075f;

                // Set the new color values for the particle's sprite
                sprite.setColor(red, green, blue);
            }

        });

        scene.attachChild(particleSystem);

        return scene;
    }
}
