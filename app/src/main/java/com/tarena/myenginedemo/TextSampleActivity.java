package com.tarena.myenginedemo;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.color.Color;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.tarena.myenginedemo.Config.BLUE_F;
import static com.tarena.myenginedemo.Config.GREEN_F;
import static com.tarena.myenginedemo.Config.RED_F;

public class TextSampleActivity extends BaseGameActivity {

    Font font;
    Text text;
    private  Scene scene;

    @Override
    public EngineOptions onCreateEngineOptions() {
        Camera camera = new Camera(0,0,800,480);
        EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,new FillResolutionPolicy(),camera);
        return options;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        //利用FontFactory创建字体(该过程类似在文件纹理地图)
        font = FontFactory.create(getFontManager(),getTextureManager(),256,256,32.0f,true, Color.WHITE_ABGR_PACKED_INT);
        //加载
        font.load();
        //具体要显示文字的预加载
        font.prepareLetters("Time:0123456789".toCharArray());
        //在继承BaseGameActivity时，在onCreateResources方法中必须调用这句话，否则程序无法正常继续执行
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }


    int lastSec = 0;

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        scene = new Scene();
        scene.setBackground(new Background(RED_F,GREEN_F,BLUE_F));
        String time = "Time:"+new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis());
        //Text对象是真正可以显示在屏幕上的文字内容。
        //但是创建Text对象的时候必须传入一个Font对象，里面包含了文字的具体样式
        text = new Text(0,480-font.getLineHeight()*0.5f,font,time,getVertexBufferObjectManager()){
            /**
             * 会被定期回调(1/60秒回调一次)
             * @param pSecondsElapsed
             */
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                //注意，该方法约16ms就会被调用一次
                //但是更新显示时间的text每1s做一次即可
                //如果过于频繁的更细文本的显示会导致屏幕文字的闪烁
                int second = Calendar.getInstance().get(Calendar.SECOND);
                if(lastSec!=second) {
                    String time = "Time:" + new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis());
                    setText(time);
                    setX(getWidth()*0.5f);
                    setY(480-getHeight()*0.5f);
                    lastSec = second;
                }
                super.onManagedUpdate(pSecondsElapsed);
            }
        };
        //text.setColor(0,0,1);
        scene.attachChild(text);
        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }


}
