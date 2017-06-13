package com.tarena.myenginedemo;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;
import java.util.Random;

import static com.tarena.myenginedemo.Config.BLUE_F;
import static com.tarena.myenginedemo.Config.CAMERA_HEIGHT;
import static com.tarena.myenginedemo.Config.CAMERA_WIDTH;
import static com.tarena.myenginedemo.Config.GREEN_F;
import static com.tarena.myenginedemo.Config.RED_F;

public class LineSampleActivity extends SimpleBaseGameActivity {

    private static final int LINE_COUNT = 100;

    @Override
    public EngineOptions onCreateEngineOptions() {
        IResolutionPolicy policy = new FillResolutionPolicy();
        Camera camera = new Camera(0,0,CAMERA_WIDTH,CAMERA_HEIGHT);
        EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,policy,camera);
        return options;
    }

    @Override
    protected void onCreateResources() throws IOException {

    }

    @Override
    protected Scene onCreateScene() {

        Scene scene = new Scene();
        Random rand = new Random();
        for(int i=0;i<LINE_COUNT;i++){

            float px1 = rand.nextFloat()*CAMERA_WIDTH;
            float py1 = rand.nextFloat()*CAMERA_HEIGHT;
            float px2 = rand.nextFloat()*CAMERA_WIDTH;
            float py2 = rand.nextFloat()*CAMERA_HEIGHT;
            Line line  = new Line(0,0,px2,py2,getVertexBufferObjectManager());
            line.setColor(rand.nextFloat(),rand.nextFloat(),rand.nextFloat());
            scene.attachChild(line);

        }
        scene.setBackground(new Background(RED_F,GREEN_F,BLUE_F));
        return scene;
    }
}
