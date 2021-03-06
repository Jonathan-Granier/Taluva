package shaders;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import utils.Matrix;

public class Shader extends ShaderProgram{
	
	private static final int MAX_LIGHTS = 4;
	
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_diffuse;
	private int location_lightPosition[];
	private int location_lightColour[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_textured;
	private int location_diffuseMap;
	private int loacation_notAllow;
	private int location_playerColour;
	
	public Shader() {
			super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normals");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_textured = super.getUniformLocation("textured");
		location_diffuse = super.getUniformLocation("diffuseColour");
		location_diffuseMap = super.getUniformLocation("diffuseMap"); 
		loacation_notAllow = super.getUniformLocation("notAllow");
		location_playerColour = super.getUniformLocation("playerColour");
		
		location_lightPosition = new int[MAX_LIGHTS];
		location_lightColour = new int[MAX_LIGHTS];
		for(int i=0;i<MAX_LIGHTS;i++){
			location_lightPosition[i] = super.getUniformLocation("lightPosition["+i+"]");
			location_lightColour[i] = super.getUniformLocation("lightColour["+i+"]");
		}
	}
	
	public void connectTextureUnits(){
		super.loadInt(location_diffuseMap, 0);
	}
	
	public void loadDiffuse(Vector3f diffuse){
		super.loadVector(location_diffuse, diffuse);
	}
	
	public void loadLights(List<Light> lights){
		for(int i=0;i<MAX_LIGHTS;i++){
			if(i<lights.size()){
				super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
				super.loadVector(location_lightColour[i], lights.get(i).getColour());
			}else{	// Shader get static array, make sure we don't have empty information
				super.loadVector(location_lightPosition[i], new Vector3f(0,0,0));
				super.loadVector(location_lightColour[i], new Vector3f(0,0,0));				
			}
		}
	}
	
	public void loadPlayerColour(Vector3f Colour){
		super.loadVector(location_playerColour, Colour);
	}
	
	public void loadShineVariable(float damper, Vector3f relectivity){
		super.loadFloat(location_shineDamper, damper);
		super.loadVector(location_reflectivity, relectivity);
	}
	
	public void loadNotAllow(boolean notAllow){
		super.loadBoolean(loacation_notAllow, notAllow);
	}
	
	public void loadTextured(boolean textured){
		super.loadBoolean(location_textured, textured);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Matrix.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	

}
