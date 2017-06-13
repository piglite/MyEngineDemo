package com.tarena.myenginedemo;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;

public class AnimateSpriteSampleActivity extends SimpleBaseGameActivity {

    BuildableBitmapTextureAtlas atlas;
    ITiledTextureRegion boxRegion;
    ITiledTextureRegion circleRegion;
    ITiledTextureRegion hexRegion;
    ITiledTextureRegion triangleRegion;

    @Override
    public EngineOptions onCreateEngineOptions() {
        Camera camera = new Camera(0, 0, 800, 480);
        EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), camera);
        options.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        options.getRenderOptions().setDithering(true);
        options.getRenderOptions().getConfigChooserOptions().setRequestedAlphaSize(8);
        options.getRenderOptions().getConfigChooserOptions().setRequestedRedSize(8);
        options.getRenderOptions().getConfigChooserOptions().setRequestedGreenSize(8);
        options.getRenderOptions().getConfigChooserOptions().setRequestedBlueSize(8);

        return options;
    }



    @Override
    protected void onCreateResources() throws IOException {

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        atlas = new BuildableBitmapTextureAtlas(getTextureManager(), 128, 128, TextureOptions.BILINEAR);
        boxRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(atlas, getAssets(), "face_box_tiled.png", 2, 1);
        circleRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(atlas, getAssets(), "face_circle_tiled.png", 2, 1);
        hexRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(atlas, getAssets(), "face_hexagon_tiled.png", 2, 1);
        triangleRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(atlas, getAssets(), "face_triangle_tiled.png", 2, 1);
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
        scene.setBackground(new Background(Config.RED_F, Config.GREEN_F, Config.BLUE_F));
        AnimatedSprite boxSprite = new AnimatedSprite(
                360 * 1.0f,
                280 * 1.0f,
                boxRegion,
                this.getVertexBufferObjectManager());
        scene.attachChild(boxSprite);
        boxSprite.animate(500);

        AnimatedSprite circleSprite = new AnimatedSprite(
                440 * 1.0f,
                280 * 1.0f,
                circleRegion,
                this.getVertexBufferObjectManager());
        scene.attachChild(circleSprite);
        circleSprite.animate(500);

        AnimatedSprite hexSprite = new AnimatedSprite(
                360 * 1.0f,
                200 * 1.0f,
                hexRegion,
                this.getVertexBufferObjectManager());
        scene.attachChild(hexSprite);
        hexSprite.animate(500);

        AnimatedSprite triSprite = new AnimatedSprite(
                440 * 1.0f,
                200 * 1.0f,
                triangleRegion,
                this.getVertexBufferObjectManager());
        scene.attachChild(triSprite);
        triSprite.animate(500);
        return scene;
    }
}
