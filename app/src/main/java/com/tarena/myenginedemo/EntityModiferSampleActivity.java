package com.tarena.myenginedemo;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import java.io.IOException;

import static com.tarena.myenginedemo.Config.BLUE_F;
import static com.tarena.myenginedemo.Config.GREEN_F;
import static com.tarena.myenginedemo.Config.RED_F;

public class EntityModiferSampleActivity extends SimpleBaseGameActivity {


    Rectangle rectangle;

    @Override
    public EngineOptions onCreateEngineOptions() {
        Camera camera = new Camera(0,0,800,480);
        EngineOptions op = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,new FillResolutionPolicy(),camera);
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
        scene.setBackground(new Background(
                RED_F,GREEN_F,BLUE_F));

        rectangle = new Rectangle(400,240,80,80,getVertexBufferObjectManager());
        rectangle.setColor(Color.WHITE);

        RotationModifier rotationModifier = new RotationModifier(5,0,720);
        ScaleModifier scaleModifier = new ScaleModifier(5,1,3);

        ParallelEntityModifier entityModifer = new ParallelEntityModifier(rotationModifier,scaleModifier);
        rectangle.registerEntityModifier(entityModifer);

        scene.attachChild(rectangle);

        return scene;
    }
}
