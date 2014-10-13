package com.liqiao.divide;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.EGLConfig;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Handler;

public class MyGLSurfaceView extends GLSurfaceView {
    public MyGLSurfaceView(Context context){
        super(context);

        // Set the Renderer for drawing on the GLSurfaceView
        setEGLContextClientVersion(2);
        setRenderer(new MyGLRenderer());
    }
	public static float[] mProjMatrix =new float[16];
	public static float[] mVMatrix=new float[16];
    public class MyGLRenderer implements GLSurfaceView.Renderer {
    	AnimationOneToFour ani;
    	Circle circle;
		private AnimationOneToFour ani2;
		private AnimationOneToFour ani3;
        public void onSurfaceCreated(GL10 unused, EGLConfig config) {
            // Set the background frame color
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        }

        public void onDrawFrame(GL10 unused) {
            // Redraw background color
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            Matrix.multiplyMM(circle.mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);
            Matrix.multiplyMM(ani.mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);
//            circle.draw();
            ani.draw();
//            ani2.draw();
//            ani3.draw();
        }

        public void onSurfaceChanged(GL10 unused, int width, int height) {
            GLES20.glViewport(0, 0, width, height);
            float ratio = (float) width / height;

            // create a projection matrix from device screen geometry
            Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        }

		@Override
		public void onSurfaceCreated(GL10 gl,
				javax.microedition.khronos.egl.EGLConfig config) {
			// TODO Auto-generated method stub
			GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
			circle=new Circle(0.4f,0,0,0,36);
			ani=new AnimationOneToFour(this,0f, 0f, 0f, 0.4f, 1, 12);
			new Thread((ani.new ChangeThread())).start();
//			ani2=new AnimationOneToFour(0f, 0f, 0f, 0.4f, 2, 12);
//			ani3=new AnimationOneToFour(0f, 0f, 0f, 0.4f, 3, 12);
			Matrix.setLookAtM(mVMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
		}
    }
}
