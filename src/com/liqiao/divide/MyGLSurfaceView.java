package com.liqiao.divide;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.EGLConfig;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

public class MyGLSurfaceView extends GLSurfaceView {
    public MyGLSurfaceView(Context context){
        super(context);

        // Set the Renderer for drawing on the GLSurfaceView
        setEGLContextClientVersion(2);
        setRenderer(new MyGLRenderer());
    }
    public class MyGLRenderer implements GLSurfaceView.Renderer {
    	
    	Circle circle;
        public void onSurfaceCreated(GL10 unused, EGLConfig config) {
            // Set the background frame color
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        }

        public void onDrawFrame(GL10 unused) {
            // Redraw background color
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            Matrix.multiplyMM(circle.mMVPMatrix, 0, circle.mProjMatrix, 0, circle.mVMatrix, 0);
            circle.draw();
        }

        public void onSurfaceChanged(GL10 unused, int width, int height) {
            GLES20.glViewport(0, 0, width, height);
            float ratio = (float) width / height;

            // create a projection matrix from device screen geometry
            Matrix.frustumM(circle.mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        }

		@Override
		public void onSurfaceCreated(GL10 gl,
				javax.microedition.khronos.egl.EGLConfig config) {
			// TODO Auto-generated method stub
			GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
			circle=new Circle(0.4f,0,0,0,36);
			Matrix.setLookAtM(circle.mVMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
		}
    }
}
