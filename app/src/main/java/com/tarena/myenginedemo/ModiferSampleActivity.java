package com.tarena.myenginedemo;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;

import static com.tarena.myenginedemo.Config.BLUE_F;
import static com.tarena.myenginedemo.Config.GREEN_F;
import static com.tarena.myenginedemo.Config.RED_F;

public class ModiferSampleActivity extends SimpleBaseGameActivity {

    Rectangle one;

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
        //TODO
    }

    @Override
    protected Scene onCreateScene() {
        Scene scene = new Scene();
        scene.setBackground(new Background(RED_F, GREEN_F, BLUE_F));

        one = new Rectangle(40, 40, 80, 80, getVertexBufferObjectManager());

        float[] pathXs = {40, 40, 760, 760, 40};
        float[] pathYs = {40, 440, 440, 40, 40};
        /*CardinalSplineMoveModifier.CardinalSplineMoveModifierConfig config = new CardinalSplineMoveModifier.CardinalSplineMoveModifierConfig(pathXs.length, 0.8f);
        for (int i = 0; i < pathXs.length; i++) {
            float x = pathXs[i];
            float y = pathYs[i];
            config.setControlPoint(i, x, y);

        }
        CardinalSplineMoveModifier modifer = new CardinalSplineMoveModifier(5, config);*/
        PathModifier.Path path = new PathModifier.Path(pathXs.length);
        for (int i = 0; i < pathXs.length; i++) {

            float x = pathXs[i];
            float y = pathYs[i];
            path.to(x,y);

        }
        PathModifier modifer = new PathModifier(5,path);
        LoopEntityModifier entityModifier = new LoopEntityModifier(modifer,3);
        one.registerEntityModifier(entityModifier);


        scene.attachChild(one);

        return scene;
    }
}
