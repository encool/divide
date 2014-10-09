package com.liqiao.divide;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.opengl.GLES20;

import com.liqiao.divide.utility.ShaderUtil;

public class Circle {
	public Circle(float radius,float xCircle, float yCircle, float zCircle,int precision) {
		super();
		this.xCircle = xCircle;
		this.yCircle = yCircle;
		this.zCircle = zCircle;
		this.nPrecision=precision;

		vertexes=new float[nPrecision*3];
		this.radius=radius;
		initShader();
		initVertexDate();
	}
	float xCircle=0;
	float yCircle=0;
	float zCircle=0;
	float radius=1;
	int nPrecision=36;
	public static float[] mProjMatrix =new float[16];
	public static float[] mVMatrix=new float[16];
	public static float[] mMVPMatrix;
	public static float[] vertexes;
	float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
	int mProgram;
	int muMVPMatrixHandle;
	int maPositionHandle;
	int maColorHandle;
	int mVertexShader;
	int mFragmentShader;
	static float[] mMMatrix=new float[16];
	FloatBuffer mVertexBuffer;
	FloatBuffer mColorBuffer;
	int vCount=0;
	int xAngle=0;
	
	private final String vertexShaderCode =
		    "attribute vec4 vPosition;" +
		    "void main() {" +
		    "  gl_Position = vPosition;" +
		    "}";

		private final String fragmentShaderCode =
		    "precision mediump float;" +
		    "uniform vec4 vColor;" +
		    "void main() {" +
		    "  gl_FragColor = vColor;" +
		    "}";
	
	public Circle(){
		initShader();
		initVertexDate();
		vertexes=new float[nPrecision*3];
	}
	private void initShader() {
		// TODO Auto-generated method stub
		mVertexShader=ShaderUtil.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
		mFragmentShader=ShaderUtil.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
		mProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
	    GLES20.glAttachShader(mProgram, mVertexShader);   // add the vertex shader to program
	    GLES20.glAttachShader(mProgram, mFragmentShader); // add the fragment shader to program
	    GLES20.glLinkProgram(mProgram);                  // creates OpenGL ES program executables
	    maPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
	    maColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
	}
	private void initVertexDate() {
		// TODO Auto-generated method stub
		generateVertex();
        ByteBuffer bb = ByteBuffer.allocateDirect(
        // (# of coordinate values * 4 bytes per float)
                vertexes.length * 4);
        bb.order(ByteOrder.nativeOrder());
        mVertexBuffer = bb.asFloatBuffer();
        mVertexBuffer.put(vertexes);
        mVertexBuffer.position(0);
	}
	private void generateVertex() {
		// TODO Auto-generated method stub
		float x,y,z=0;
		int temp=0;
		for(int i=0;i<nPrecision;i++){
			x=(float) (xCircle+radius*Math.cos(2*Math.PI*i/nPrecision));
			y=(float) (yCircle+radius*Math.sin(2*Math.PI*i/nPrecision));
			vertexes[temp++]=x;
			vertexes[temp++]=y;
			vertexes[temp++]=z;
		}

	}
	public void draw(){
		// Add program to OpenGL ES environment
	    GLES20.glUseProgram(mProgram);

	    // Enable a handle to the triangle vertices
	    GLES20.glEnableVertexAttribArray(maPositionHandle);

	    // Prepare the triangle coordinate data
	    GLES20.glVertexAttribPointer(maPositionHandle, 3,
	                                 GLES20.GL_FLOAT, false,
	                                 3*4, mVertexBuffer);


	    // Set color for drawing the triangle
	    GLES20.glUniform4fv(maColorHandle, 1, color, 0);

	    // Draw the triangle
	    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, nPrecision);

	    // Disable vertex array
	    GLES20.glDisableVertexAttribArray(maPositionHandle);
	}
	
}
