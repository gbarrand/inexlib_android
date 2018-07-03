package fr.in2p3.lal.exlib.gl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

public class Main extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    //System.out.println("debug : Main : 000");
    super.onCreate(savedInstanceState);
    mGLView = new DemoGLSurfaceView(this);
    setContentView(mGLView);
  }

  @Override
  protected void onPause() {
    super.onPause();
    mGLView.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mGLView.onResume();
  }

  private GLSurfaceView mGLView;

  static {
    System.loadLibrary("exlib_gl");
  }
}

class DemoGLSurfaceView extends GLSurfaceView {
  public DemoGLSurfaceView(Context context) {
    super(context);
    mRenderer = new DemoRenderer();
    setRenderer(mRenderer);
  }

  public boolean onTouchEvent(final MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      nativeTouchEventDown();
    }
    return true;
  }

  DemoRenderer mRenderer;

  private static native void nativeTouchEventDown();
}

class DemoRenderer implements GLSurfaceView.Renderer {
  public void onSurfaceCreated(GL10 gl,EGLConfig config) {
    nativeSurfaceCreated();
  }

  public void onSurfaceChanged(GL10 gl,int w,int h) {
    nativeSurfaceChanged(w,h);
  }

  public void onDrawFrame(GL10 gl) {nativeDrawFrame();}

  private static native void nativeSurfaceCreated();
  private static native void nativeSurfaceChanged(int w,int h);
  private static native void nativeDrawFrame();
}
