package com.tarena.myenginedemo;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.DrawMode;
import org.andengine.entity.primitive.Mesh;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.color.Color;

import java.io.IOException;

public class ApplyPrimitiveActivity extends BaseGameActivity {

    @Override
    public EngineOptions onCreateEngineOptions() {

        Camera camera = new Camera(0,0,800,480);
        EngineOptions options = new EngineOptions(true,
                ScreenOrientation.LANDSCAPE_SENSOR,new FillResolutionPolicy(),camera);
        options.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return options;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {

        Scene scene = new Scene();
        scene.setBackground(new Background(Config.RED_F,Config.GREEN_F,Config.BLUE_F));
        pOnCreateSceneCallback.onCreateSceneFinished(scene);

    }

    @Override
    public void onPopulateScene(Scene mScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
        // Set the raw points for our rectangle mesh
        float baseBuffer[] = {
                // Triangle one
                -200, -100, 0, // point one
                200, -100, 0, // point two
                200, 100, 0, // point three

                // Triangle two
                200, 100, 0, // point one
                -200, 100, 0, // point two
                -200, -100, 0 // point three
        };

        // Create the base mesh at the bottom of the screen
        // 要时刻注意使用的坐标系
        // 目前使用的是AndEngine GLES2-ANCHOR，坐标系原点(0,0)为左下角
        // 创建meshBase对象时的(px,py)默认时指的是meshBase的中心点坐标
        // 即meshBase的中心位置在(400,100)
        // 而绘制meshBase时使用的baseBuffer中的顶点坐标全部是基于于(px,py)的偏移量
        // 所以在AndEngine GLES2-ANCHOR坐标系下，Triangle one 的point one坐标为(200,0)，point two坐标为(600,0),point three坐标为(600,200)
        // 同理Triangle two 的point one坐标为(600,200)，point two坐标为(200,200),point three坐标为(200,0)
        // meshBase为两个三角形拼凑起来的矩形
        //Mesh meshBase = new Mesh(400, 480 - 100, baseBuffer, 6, DrawMode.TRIANGLES, mEngine.getVertexBufferObjectManager());
        Mesh meshBase = new Mesh(400, 100, baseBuffer, 6, DrawMode.TRIANGLES, mEngine.getVertexBufferObjectManager());

        // Set the meshes color to a 'brown' color
        meshBase.setColor(0.45f, 0.164f, 0.3f);
        // Attach base mesh to the scene
        mScene.attachChild(meshBase);

        // Create the raw points for our triangle mesh
        /*float roofBuffer[] = {
                // Triangle
                -300, 0, 0, // point one
                0, -200, 0, // point two
                300, 0, 0, // point three
        };*/

        float roofBuffer[] = {
                // Triangle
                -300, 0, 0, // point one
                0, 200, 0, // point two
                300, 0, 0, // point three
        };

        // Create the roof mesh above the base mesh
        //Mesh meshRoof = new Mesh(400, 480 - 200, roofBuffer, 3, DrawMode.TRIANGLES, this.getVertexBufferObjectManager());
        // meshRoof的中心点坐标为(400,200)
        // 使用的rootBuffer中 point one坐标为(100,200),point two坐标为(400,400),point three坐标为(700,200)
        // meshRoof为三角形
        Mesh meshRoof = new Mesh(400, 200, roofBuffer, 3, DrawMode.TRIANGLES, this.getVertexBufferObjectManager());

        meshRoof.setColor(Color.RED);
        // Attach the roof to the scene
        mScene.attachChild(meshRoof);

        // Create the raw points for our line mesh
        float doorBuffer[] = {
                -25, -100, 0, // point one
                25, -100, 0, // point two
                25, 0, 0, // point three
                -25, 0, 0, // point four
                -25, -100, 0 // point five
        };

        // Create the door mesh
        //Mesh meshDoor = new Mesh(400, 480, doorBuffer, 5, DrawMode.LINE_STRIP, mEngine.getVertexBufferObjectManager());
        //meshDoor的中心点坐标为(400,100)
        // doorBuffer中 point one坐标(375,0)，point two坐标(425,0)，point three坐标(425,100),point four坐标(375,100)，point five坐标(375,0)
        // meshDoor为一个矩形(p1连线p2,p2连线p3，p3连线p4,p4连线连p5，因为p5与p1坐标位置相同，因此连线最终形成一个闭环，样子为一个封闭的空心矩形)
        Mesh meshDoor = new Mesh(400, 100, doorBuffer, 5, DrawMode.LINE_LOOP, mEngine.getVertexBufferObjectManager());

        meshDoor.setColor(Color.BLUE);
        // Attach the door to the scene
        mScene.attachChild(meshDoor);
        //这句话千万不要忘记调用，否则程序无法正常继续进行
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }




}
