package com.liqiao.divide;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import com.liqiao.divide.MyGLSurfaceView.MyGLRenderer;
import com.liqiao.divide.utility.ShaderUtil;

public class AnimationOneToFour {

	int precision;//԰��Բ����
	int position;//1 right top;2 r.b.;3 l.b.; 4 l.t.;
	int degreeMark=0;//1 to 100 
	float xFrom;
	float yFrom;
	float zFrom;
	float radiusFrom;
	float xNew;
	float yNew;
	float zNew;
	float radiusNew;
	float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
//	public static float[] mProjMatrix =new float[16];
//	public static float[] mVMatrix=new float[16];
	public static float[] mMVPMatrix=new float[16];
	public static float[] vertexes;
	int vertexesMark=0;//��¼�����������λ��
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
	GLSurfaceView glSurfaceView;
	public AnimationOneToFour(Renderer myGLRenderer, float xFrom, float yFrom, float zFrom,
			float radiusFrom,int position,int precision) {
		super();
		this.xFrom = xFrom;
		this.yFrom = yFrom;
		this.zFrom = zFrom;
		this.radiusFrom = radiusFrom;
		this.position=position;
		this.precision=precision;
		this.glSurfaceView=myGLRenderer;
		vertexes=new float[4*(3*precision*4+6)];
		initShader();
		initVertexDate();
	}
	public void draw(){
		// Add program to OpenGL ES environment
	    GLES20.glUseProgram(mProgram);

	    // Enable a handle to the triangle vertices
	    GLES20.glEnableVertexAttribArray(maPositionHandle);
	    GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0);
	    // Prepare the triangle coordinate data
	    GLES20.glVertexAttribPointer(maPositionHandle, 3,
	                                 GLES20.GL_FLOAT, false,
	                                 3*4, mVertexBuffer);


	    // Set color for drawing the triangle
	    GLES20.glUniform4fv(maColorHandle, 1, color, 0);

	    // Draw the triangle
	    GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, precision*4+2);
	    GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, precision*4+2, precision*4+2);
	    GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 2*(precision*4+2), precision*4+2);
	    GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 3*(precision*4+2), precision*4+2);
	    // Disable vertex array
//	    GLES20.glDisableVertexAttribArray(maPositionHandle);
	}
	private final String vertexShaderCode =
			"uniform mat4 uMVPMatrix;"+
		    "attribute vec4 vPosition;" +
		    "void main() {" +
		    "  gl_Position = uMVPMatrix*vPosition;" +
		    "}";

		private final String fragmentShaderCode =
		    "precision mediump float;" +
		    "uniform vec4 vColor;" +
		    "void main() {" +
		    "  gl_FragColor = vColor;" +
		    "}";
		private float currentBigradius;
		private float currentSmaradius;
		private float currentBigX;
		private float currentBigY;
		private float currentBigZ;
		private float currentSmaX;
		private float currentSmaY;
		private float currentSmaZ;
		private float xSmaFrom,ySmaFrom,zSmaFrom;
		private float xSmaNew,ySmaNew,zSmaNew;
		private float radiusSmaFrom=10f;//СԲ�뾶��1000���ɱ�С
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
		    muMVPMatrixHandle =  GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
		}
		private void initVertexDate() {
			// TODO Auto-generated method stub
			generateVertex(1);
			generateVertex(2);
			generateVertex(3);
			generateVertex(4);
	        ByteBuffer bb = ByteBuffer.allocateDirect(
	        // (# of coordinate values * 4 bytes per float)
	                vertexes.length * 4);
	        bb.order(ByteOrder.nativeOrder());
	        mVertexBuffer = bb.asFloatBuffer();
	        mVertexBuffer.put(vertexes);
	        mVertexBuffer.position(0);
		}
		private void calculateNewCoords(int position2){		
			radiusNew=radiusFrom/2;
			float radiusSmaDiff=radiusSmaFrom-radiusNew;
			float coodsDiff=(float) (radiusSmaDiff/Math.sqrt(2));
			switch(position2){
			case 1://��һ���޵ĵ�
				xNew=xFrom+radiusNew;
				yNew=yFrom+radiusNew;
				zNew=zFrom;
				xSmaFrom=xFrom+coodsDiff;
				ySmaFrom=yFrom+coodsDiff;
				zSmaFrom=zFrom;
				break;
			case 2:
				xNew=xFrom-radiusNew;
				yNew=yFrom+radiusNew;
				zNew=zFrom;
				xSmaFrom=xFrom-coodsDiff;
				ySmaFrom=yFrom+coodsDiff;
				zSmaFrom=zFrom;
				break;
				
			case 3:
				xNew=xFrom-radiusNew;
				yNew=yFrom-radiusNew;
				zNew=zFrom;
				xSmaFrom=xFrom-coodsDiff;
				ySmaFrom=yFrom-coodsDiff;
				zSmaFrom=zFrom;
				break;
			case 4:
				xNew=xFrom+radiusNew;
				yNew=yFrom-radiusNew;
				zNew=zFrom;
				xSmaFrom=xFrom+coodsDiff;
				ySmaFrom=yFrom-coodsDiff;
				zSmaFrom=zFrom;	
				break;
			}
		}
		private void generateVertex(int position) {
			// TODO Auto-generated method stub
			calculateNewCoords(position);
			calculateBigPart(position);
			catculateSmaPart(position);
		}
		private void catculateSmaPart(int position2) {
			// TODO Auto-generated method stub
			float ratio=degreeMark/100f;
			currentSmaradius=radiusSmaFrom-(radiusSmaFrom-radiusNew)*ratio;
			currentSmaX=xSmaFrom+(xNew-xSmaFrom)*ratio;
			currentSmaY=ySmaFrom+(yNew-ySmaFrom)*ratio;
			currentSmaZ=zSmaFrom+(zNew-zSmaFrom)*ratio;
			float angleStart = 0;
			float angleEnd = 0;
			float angle;
			switch(position2){
			case 1:
				angle=(float) Math.acos((currentSmaY-yFrom)/currentSmaradius);
				angleStart=(float) (Math.PI+angle);
				angleEnd=(float) (Math.PI*1.5f-angle);
				break;
			case 2:
				angle=(float) Math.acos((currentSmaY-yFrom)/currentSmaradius);
				angleStart=(float) (1.5*Math.PI+angle);
				angleEnd=(float) (2*Math.PI-angle);
				break;
			case 3:
				angle=(float) Math.acos(-(currentSmaY-yFrom)/currentSmaradius);
				angleStart=angle;
				angleEnd=(float) (0.5*Math.PI-angle);
				break;
			case 4:
				angle=(float) Math.acos(-(currentSmaY-yFrom)/currentSmaradius);
				angleStart=(float) (0.5*Math.PI+angle);
				angleEnd=(float) (Math.PI-angle);
				break;
			}
			float anglerange=angleEnd-angleStart;
			int precisionSma=precision;
			float anglepiece=anglerange/precisionSma;
			float x,y,z=0;
			for(int i = 0;i<=precisionSma;i++){
				x=(float) (currentSmaX+currentSmaradius*Math.cos(angleStart+anglepiece*i));
				y=(float) (currentSmaY+currentSmaradius*Math.sin(angleStart+anglepiece*i));
				vertexes[vertexesMark++]=x;
				vertexes[vertexesMark++]=y;
				vertexes[vertexesMark++]=z;
			}
			Log.i("vertexNum", String.valueOf(vertexesMark));
		}
		private void calculateBigPart(int position2) {
			// TODO Auto-generated method stub
			float ratio=degreeMark/100f;
			currentBigradius=radiusFrom-radiusNew*ratio;//Ϊ�˴ﵽƽ����Ч���˴������������õĸ���
			currentBigX=xFrom+(xNew-xFrom)*ratio;
			currentBigY=yFrom+(yNew-yFrom)*ratio;
			currentBigZ=zFrom+(zNew-zFrom)*ratio;
			float angleStart = 0;
			float angleEnd = 0;
			float angle;
			switch(position2){
			case 1://��һ���޵ĵ�
				angle=(float) Math.acos((currentBigY-yFrom)/currentBigradius);
				angleStart=(float) (-Math.PI/2+angle);
				angleEnd=(float) (Math.PI-angle);
				break;
			case 2:
				angle=(float) Math.acos((currentBigY-yFrom)/currentBigradius);
				angleStart=angle;
				angleEnd=(float) (1.5f*Math.PI-angle);
				break;
			case 3:
				angle=(float) Math.acos(-(currentBigY-yFrom)/currentBigradius);
				angleStart=(float) (Math.PI/2+angle);
				angleEnd=(float) (2*Math.PI-angle);
				break;
			case 4:
				angle=(float) Math.acos(-(currentBigY-yFrom)/currentBigradius);
				angleStart=(float) (Math.PI+angle);
				angleEnd=(float) (2.5f*Math.PI-angle);
				
			}
			float anglerange=angleEnd-angleStart;
			int precisionbig=3*precision;
			float anglepiece=anglerange/precisionbig;
			float x,y,z=0;
			for(int i = 0;i<=precisionbig;i++){
				x=(float) (currentBigX+currentBigradius*Math.cos(angleStart+anglepiece*i));
				y=(float) (currentBigY+currentBigradius*Math.sin(angleStart+anglepiece*i));
				vertexes[vertexesMark++]=x;
				vertexes[vertexesMark++]=y;
				vertexes[vertexesMark++]=z;
			}
			Log.i("vertexNum", String.valueOf(vertexesMark));
		}
		public class ChangeThread implements Runnable{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(degreeMark<101){
					degreeMark=degreeMark+1;
					draw();
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			
		}
}
