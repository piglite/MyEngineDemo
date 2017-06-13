package com.tarena.myenginedemo;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;

import static com.tarena.myenginedemo.Config.BLUE_F;
import static com.tarena.myenginedemo.Config.CAMERA_HEIGHT;
import static com.tarena.myenginedemo.Config.CAMERA_WIDTH;
import static com.tarena.myenginedemo.Config.GREEN_F;
import static com.tarena.myenginedemo.Config.RED_F;

public class RectangleSampleActivity extends SimpleBaseGameActivity {


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


        Rectangle rec1 = makeColorRectangle(0,0,1f,0f,0f);
        rec1.setAnchorCenter(1,1);
        Rectangle rec2 = makeColorRectangle(0,0,0f,1f,0f);
        rec2.setAnchorCenter(1,0);
        Rectangle rec3 = makeColorRectangle(0,0,0f,0f,1f);
        rec3.setAnchorCenter(0,1);
        Rectangle rec4 = makeColorRectangle(0,0,1f,1f,0f);
        rec4.setAnchorCenter(0,0);

        Entity entity = new Entity(CAMERA_WIDTH/2,CAMERA_HEIGHT/2);

        entity.attachChild(rec1);
        entity.attachChild(rec2);
        entity.attachChild(rec3);
        entity.attachChild(rec4);

        scene.attachChild(entity);
        scene.setBackground(new Background(RED_F,GREEN_F,BLUE_F));

        return scene;
    }

    private Rectangle makeColorRectangle(int px, int py, float r, float g, float b) {

        Rectangle rect = new Rectangle(px,py,180.0f,180.0f,getVertexBufferObjectManager());
        rect.setColor(r,g,b);
        return rect;
    }
}
