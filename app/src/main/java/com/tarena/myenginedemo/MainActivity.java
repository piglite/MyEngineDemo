package com.tarena.myenginedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_simpleparticlesample,R.id.btn_particlinterfaceimplesample,R.id.btn_particlesample,R.id.btn_entitymodifersample,R.id.btn_modifersample,R.id.btn_tworectanglesample,R.id.btn_relativerotatesample,R.id.btn_textsample,R.id.btn_animatesprite,R.id.btn_linesample,R.id.btn_rectanglesample,R.id.btn_spritesample,R.id.btn_applyprimitive})
    public void jumpTo(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.btn_linesample:
                intent = new Intent(this,LineSampleActivity.class);
                break;
            case R.id.btn_rectanglesample:
                intent = new Intent(this,RectangleSampleActivity.class);
                break;
            case R.id.btn_spritesample:
                intent = new Intent(this,SpriteSampleActivity.class);
                break;
            case R.id.btn_applyprimitive:
                intent = new Intent(this,ApplyPrimitiveActivity.class);
                break;
            case R.id.btn_animatesprite:
                intent = new Intent(this,AnimateSpriteSampleActivity.class);
                break;
            case R.id.btn_textsample:
                intent = new Intent(this,TextSampleActivity.class);
                break;
            case R.id.btn_relativerotatesample:
                intent = new Intent(this,RelaiveRotationSampleActivity.class);
                break;
            case R.id.btn_tworectanglesample:
                intent = new Intent(this,TowRectangleSampleActivity.class);
                break;
            case R.id.btn_modifersample:
                intent = new Intent(this,ModiferSampleActivity.class);
                break;
            case R.id.btn_entitymodifersample:
                intent = new Intent(this,EntityModiferSampleActivity.class);
                break;
            case R.id.btn_particlesample:
                intent = new Intent(this,ParticleSampleActivity.class);
                break;
            case R.id.btn_particlinterfaceimplesample:
                intent = new Intent(this,ParticleInterfaceImplSampleActiviey.class);
                break;
            case R.id.btn_simpleparticlesample:
                intent = new Intent(this,ParticleSimpleSampleActivity.class);
                break;

            default:
                throw new RuntimeException("不正确的按钮设置");
        }
        startActivity(intent);
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
