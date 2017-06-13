package com.tarena.myenginedemo;

import android.util.Log;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import java.io.IOException;

import static com.tarena.myenginedemo.Config.BLUE_F;
import static com.tarena.myenginedemo.Config.GREEN_F;
import static com.tarena.myenginedemo.Config.RED_F;

public class TowRectangleSampleActivity extends SimpleBaseGameActivity {

    Rectangle one, two;
    private static final int ROTATION_SPEED = 25;
    private static final int TRANSLATE_SPEED = 5;

    boolean direction = true;//true 右，false 左


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
        one = new Rectangle(400, 240, 160, 80, getVertexBufferObjectManager()) {
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {

                float offset = pSecondsElapsed * ROTATION_SPEED;
                setRotation(getRotation() + offset);

                super.onManagedUpdate(pSecondsElapsed);
            }
        };

        one.setColor(Color.RED_ARGB_PACKED_INT);
        //特别注意：在GLES2_ANCHOR版本中，锚点是一个非常重要的内容
        //一切与坐标点相关的内容和方法均是以锚点(ANCHOR)为基准来进行的
        //默认的锚点为中心点，同时默认的实体旋转，缩放，扭曲的中心点均为锚点
        //例如，如果我以new Rectangle(40,40, 80, 80, getVertexBufferObjectManager())创建矩形
        //默认是将一个80*80的矩形的中心点置于(40,40)坐标位置
        //这样当显示在屏幕上时，该矩形恰好会位于屏幕的左下角
        //此时调用矩形的getX和getY方法，获得的值为40,40
        //因为getX和getY都是去获取锚点的坐标位置
        //如果在调用了new Rectangle(40,40, 80, 80, getVertexBufferObjectManager())之后
        //调用setAnchorCenter(0,0)，则意味着将锚点从默认的中心点位置改为矩形的左下角
        //也就是显示时，矩形将距离屏幕左侧边缘和下方边缘各40的距离
        //同时实体的默认旋转，缩放，扭曲的中心点也移动到了左下角新的锚点处

        two = new Rectangle(40,40, 80, 80, getVertexBufferObjectManager()) {


            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {

                if (direction && this.getX() < 840) {
                    this.setPosition(this.getX() + TRANSLATE_SPEED, this.getY());
                } else {
                    if(getX()>=840){
                        direction = false;
                        this.setPosition(getX(),getY()+80);
                    }
                    this.setPosition(getX()-TRANSLATE_SPEED, this.getY());
                    if(getX()<-80){
                        direction = true;
                        this.setPosition(getX(),getY()+80);
                    }
                }
                if(getY()>440){
                    this.setPosition(40,40);
                }
                if(collidesWith(one)&&getColor()!=Color.GREEN){
                    setColor(Color.GREEN);
                }else if(getColor()!=Color.RED){
                    setColor(Color.WHITE);
                }
               setRotation(getRotation()+180*pSecondsElapsed);
                super.onManagedUpdate(pSecondsElapsed);
            }
        };

        //two.setAnchorCenter(0,0);
        scene.attachChild(one);
        scene.attachChild(two);
        Log.d("TAG", "two方块的getX: "+two.getX()+" , two.getY: "+two.getY());
        return scene;
    }
}
