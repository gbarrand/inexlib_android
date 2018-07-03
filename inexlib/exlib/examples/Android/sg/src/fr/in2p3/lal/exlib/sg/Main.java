package fr.in2p3.lal.exlib.sg;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

import java.lang.String;
import java.io.File;

public class Main extends Activity {
  private GLView m_view;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //System.out.println("debug : Main : begin : 001");
    m_view = new GLView(this);
    //NOTE : we want the below to master when the actual
    //       rendering is done.
    m_view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    setContentView(m_view);
  }

  @Override
  protected void onPause() {
    super.onPause();
    m_view.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    m_view.onResume();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    //System.out.println("debug : Main : onDestroy : 001");
    m_view.onDestroy();
  }

  static {
    System.loadLibrary("exlib_sg");
  }
}

class Exa {
  private long m_cpp_obj;

  public Exa(String a_doc_dir,String a_tmp_dir) {
    m_cpp_obj = nativeCreateExaObj(a_doc_dir,a_tmp_dir);
  }

  public void finalize() {
    //System.out.println("debug : Exa : finalize : 000");
    nativeDeleteExaObj(m_cpp_obj);
    m_cpp_obj = 0;
  }

  public boolean touchEventDown(float a_x,float a_y) {
    return nativeTouchEventDown(m_cpp_obj,a_x,a_y);
  }
  public void surfaceCreated(){nativeSurfaceCreated(m_cpp_obj);}

  public void surfaceChanged(int a_w, int a_h) {
    nativeSurfaceChanged(m_cpp_obj,a_w,a_h);
  }
  public void drawFrame() {nativeDrawFrame(m_cpp_obj);}

  private static native long nativeCreateExaObj(String a_doc_dir,String a_tmp_dir);

  private synchronized native void nativeDeleteExaObj(long a_jni_test);
  private synchronized native boolean nativeTouchEventDown(long a_jni_test,float a_x,float a_y);
  private synchronized native void nativeSurfaceCreated(long a_jni_test);
  private synchronized native void nativeSurfaceChanged(long a_jni_test,int a_w, int a_h);
  private synchronized native void nativeDrawFrame(long a_jni_test);
}

class GLView extends GLSurfaceView {
  private Exa m_test;
  private GLRenderer m_renderer;
  private Thread m_thread;

  public GLView(Context context) {
    super(context);
    //System.out.println("debug : GLView : begin");

    String doc_dir = context.getFilesDir().getPath();
    String tmp_dir = doc_dir+"/";
    //System.out.println("debug : DeomRenderer : doc_dir "+doc_dir);
    m_test = new Exa(doc_dir,tmp_dir);

    m_renderer = new GLRenderer(context,m_test);
    setRenderer(m_renderer);
  }

  protected void onDestroy() {
    //System.out.println("debug : GLView : onDestroy : 000");
    m_test.finalize();
  }

  public boolean onTouchEvent(final MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      m_thread = new Thread() {
        public void run() {
          if(m_test.touchEventDown(event.getX(),event.getY())==true) {
                  //NOTE : the Renderer runs in another thread
                  //       than the UI/GLView. Then the C++ exlib::test
                  //       object is used in two threads !
                  //       For the moment we have not put a mutex on
                  //       the C++ object. We hope that the below
                  //       will do a exlib::test::do_gl before no
                  //       other java UI/GLView event is triggered.
            requestRender();
          }
        }
      };
      m_thread.start();
    }
    return true;
  }
}

class GLRenderer implements GLSurfaceView.Renderer {
  private Exa m_test;

  public GLRenderer(Context context,Exa a_test) {
    super();
    m_test = a_test;
  }

  public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    m_test.surfaceCreated();
  }

  public void onSurfaceChanged(GL10 gl, int w, int h) {
    m_test.surfaceChanged(w, h);
  }

  public void onDrawFrame(GL10 gl) {
    m_test.drawFrame();
  }
}
