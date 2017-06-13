package com.tarena.myenginedemo;

import android.util.Log;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
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
import org.andengine.util.math.MathUtils;

import java.io.IOException;

import static com.tarena.myenginedemo.Config.BLUE_F;
import static com.tarena.myenginedemo.Config.GREEN_F;
import static com.tarena.myenginedemo.Config.RED_F;

public class RelaiveRotationSampleActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener {

    BuildableBitmapTextureAtlas atlas;
    ITextureRegion arrowTextureRegion;
    ITextureRegion marbleTextureRegion;

    Sprite marble;
    Sprite arrow;

    @Override
    public EngineOptions onCreateEngineOptions() {
        Camera camera = new Camera(0, 0, 800, 480);
        EngineOptions op = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), camera);
        return op;

    }

    @Override
    protected void onCreateResources() throws IOException {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        atlas = new BuildableBitmapTextureAtlas(getTextureManager(),128,128, TextureOptions.BILINEAR);
        arrowTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas,this,"arrow.png");
        marbleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas,this,"marble.png");
        try {
            atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,1,1));
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }
        atlas.load();


    }

    @Override
    protected Scene onCreateScene() {

        Scene s = new Scene();
        s.setBackground(new Background(RED_F, GREEN_F, BLUE_F));

        marble = new Sprite(marbleTextureRegion.getWidth(),marbleTextureRegion.getHeight(),
                marbleTextureRegion,getVertexBufferObjectManager());
        arrow = new Sprite(400,240,arrowTextureRegion,getVertexBufferObjectManager());
        arrow.setFlipped(true,false);
        s.attachChild(marble);
        s.attachChild(arrow);

        s.setOnSceneTouchListener(this);



        return s;
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

            marble.setPosition(pSceneTouchEvent.getX(),pSceneTouchEvent.getY());

            // Calculate the difference between the sprites x and y values.
            final float dX = (arrow.getX() + (arrow.getWidth() / 2)) - (marble.getX() + (marble.getWidth() / 2));
            final float dY = (arrow.getY() + (arrow.getHeight() / 2)) - (marble.getY() + (marble.getHeight() / 2)) ;

            // We can use the atan2 function to find the angle
            // Additionally, OpenGL works with degrees so we must convert
            // from radians
            // 注意：Math.atan2拿到的是弧度值，取值范围[-π,π]
            // 如果是0~180度的角度，atan2得到正值，如果是0~-180(或者说180~360)度的角度得到负值
            // 利用MathUtil将弧度值转为角度值
            // 交给arrow精灵的setRotation方法使用，进行角度的旋转
            final float rotation = MathUtils.radToDeg((float) Math.atan2(-dY, dX));

            Log.d("TAG", "onSceneTouchEvent: 角度："+Math.atan2(-dY, dX)+",弧度："+rotation);

            // Set the new rotation for the arrow
            arrow.setRotation(rotation);

            return true;
        }
        return false;
    }
}
