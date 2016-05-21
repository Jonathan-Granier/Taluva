package water;

import org.lwjgl.util.vector.Matrix4f;
import shaders.ShaderProgram;
import utils.Matrix;
import entities.Camera;
import entities.Light;

public class WaterShader extends ShaderProgram {

	private final static String VERTEX_FILE = "src/water/waterVertex.txt";
	private final static String FRAGMENT_FILE = "src/water/waterFragment.txt";

	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;
	private int location_diffuseMap;
	private int location_dudvMap;
	private int location_moveFactor;
	private int location_normalMap;
	private int location_lightPosition;
	private int location_lightColour;
	private int location_cameraPosition;

	public WaterShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = getUniformLocation("projectionMatrix");
		location_viewMatrix = getUniformLocation("viewMatrix");
		location_modelMatrix = getUniformLocation("modelMatrix");
		location_diffuseMap = getUniformLocation("diffuseMap");
		location_dudvMap = getUniformLocation("dudvMap");
		location_moveFactor = getUniformLocation("moveFactor");
		location_normalMap = getUniformLocation("normalMap");
		location_lightPosition = getUniformLocation("lightPosition");
		location_lightColour = getUniformLocation("lightColour");
		location_cameraPosition = getUniformLocation("cameraPosition");
	}

	public void conectTexture(){
		super.loadInt(location_diffuseMap, 0);
		super.loadInt(location_dudvMap, 1);
		super.loadInt(location_normalMap, 2);
	}
	
	public void loadCamera(Camera camera){
		super.loadVector(location_cameraPosition, camera.getPosition());
	}
	
	public void loadlight(Light light){
		super.loadVector(location_lightColour, light.getColour());
		super.loadVector(location_lightPosition, light.getPosition());
	}
	
	public void loadMoveFactor(float moveFactor){
		super.loadFloat(location_moveFactor, moveFactor);
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Matrix.createViewMatrix(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
	}

	public void loadModelMatrix(Matrix4f modelMatrix){
		loadMatrix(location_modelMatrix, modelMatrix);
	}

}
