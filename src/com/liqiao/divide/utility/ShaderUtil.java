package com.liqiao.divide.utility;

import android.opengl.GLES20;

public class ShaderUtil {
	public static int loadShader(int shaderT,String source){
		int shader = GLES20.glCreateShader(shaderT);
		if(shader!=0){
			GLES20.glShaderSource(shader, source);
			GLES20.glCompileShader(shader);
		}
		return shader;
	}
	public static int createProgram(String vertexSource,String FragmentSource){
		int vertexShader=loadShader(GLES20.GL_VERTEX_SHADER,vertexSource);
		int fragmentshader=loadShader(GLES20.GL_FRAGMENT_SHADER,FragmentSource);
		int program=GLES20.glCreateProgram();
		GLES20.glAttachShader(program, vertexShader);
		GLES20.glAttachShader(program, fragmentshader);
		GLES20.glLinkProgram(program);
		return program;
	}
}
