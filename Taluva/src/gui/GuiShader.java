package gui;

import org.lwjgl.util.vector.Matrix4f;
 
import shaders.ShaderProgram;
 
public class GuiShader extends ShaderProgram{
     
    private static final String VERTEX_FILE = "src/gui/guiVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/gui/guiFragmentShader.txt";
     
    private int location_transformationMatrix;
    private int location_hover;
    private int location_clicked;
 
    public GuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
     
    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }
 
    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_hover = super.getUniformLocation("hover");
        location_clicked = super.getUniformLocation("clicked");
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
     
    public void loadHover(boolean hover){
    	super.loadBoolean(location_hover, hover);
    }
 
    public void loadClicked(boolean clicked){
    	super.loadBoolean(location_clicked, clicked);
    }
    
}