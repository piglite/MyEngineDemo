package com.tarena.myenginedemo;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;

public class SpriteSampleActivity extends SimpleBaseGameActivity {

    private ITextureRegion playerTextureRegion;
    private ITexture texture;
    private BuildableBitmapTextureAtlas atlas;

    @Override
    public EngineOptions onCreateEngineOptions() {

        Camera camera = new Camera(0, 0, Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT);
        EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), camera);
        options.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return options;
    }

    @Override
    protected void onCreateResources() throws IOException {

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        //注意在创建纹理地图时一定要保证纹理地图的尺寸足够容纳所有的纹理区域
        //否则无法进行正常的显示
        atlas = new BuildableBitmapTextureAtlas(getTextureManager(), 512,1024, TextureOptions.BILINEAR);
        playerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, this, "sprite.png");
        try {
            atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }
        atlas.load();

/*        texture = new BitmapTexture(getTextureManager(), new IInputStreamOpener() {
            @Override
            public InputStream open() throws IOException {
                return getAssets().open("gfx/sprite.png");
            }
        });

        texture.load();
        playerTextureRegion = TextureRegionFactory.extractFromTexture(texture);*/


    }

    @Override
    protected Scene onCreateScene() {

        Scene scene = new Scene();
        scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
        Sprite sprite = new Sprite(400, 480, playerTextureRegion, getVertexBufferObjectManager()){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                pGLState.enableDither();
                super.preDraw(pGLState, pCamera);
            }

            @Override
            protected void postDraw(GLState pGLState, Camera pCamera) {
                pGLState.disableDither();
                super.postDraw(pGLState, pCamera);
            }
        };

        sprite.setAnchorCenterY(1.0f);
        scene.attachChild(sprite);
        return scene;
    }
}
