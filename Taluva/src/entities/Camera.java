package entities;

import java.awt.Point;

import javax.swing.JFrame;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import IHM.Menu_circulaire_creation;
import terrain.Terrain;
import utils.FPS;
import utils.Grid;
import utils.InputHandler;
import utils.OSValidator;
import utils.InputHandler.inputType;

public class Camera {
	
	private static final float ZOOM_OUT_MAX = 200;
	
	private static final float SPEED = 1.5f;
	
	private Vector3f position = new Vector3f(0,0,0);
	private Vector3f lookAt = new Vector3f(0,0,0);
	private float pitch=40;
	private float angleAroundPivot=270;
	private float distanceFromPivot=100;
	private float yangle;
	private float roll;
	private JFrame frame;
	
	public static final int MARGIN = 50;
	
	public Camera(JFrame frame){
		this.frame = frame;
		float x = 3f/4f*Terrain.CENTRE.y*Grid.HEIGHT_OF_HEXA - Grid.HEIGHT_OF_HEXA/2f;
		float y = Terrain.TAILLE*Grid.WIDTH_OF_HEXA/2f + Grid.WIDTH_OF_HEXA*2f + Terrain.CENTRE.x * Grid.WIDTH_OF_HEXA/2f;
		lookAt = new Vector3f(x,0,y);
		position.x = Terrain.TAILLE/2*Grid.HEIGHT_OF_HEXA*2f/3f+100;
		position.z = Terrain.TAILLE*Grid.WIDTH_OF_HEXA*3f/4f;
	}

	public boolean between(float start,float end){
		float cap = angleAroundPivot % 360;
		if(cap<0)
			cap = 360 + cap;
		if(cap >= start && cap <= end)
			return true;
		return false;
	}
	
	public void move(){
		float zoomLevel = Mouse.getDWheel() *0.1f;
		
		//if(Mouse.getDWheel()!=0)
			//Menu_circulaire_creation.setDraw(false);
		
		if (distanceFromPivot - zoomLevel > 5f && distanceFromPivot - zoomLevel < ZOOM_OUT_MAX )
			distanceFromPivot -= zoomLevel;
		
		
		if(Mouse.isButtonDown(0)){
			float pitchChange = Mouse.getDY() * 0.3f;
			if(pitch - pitchChange >1 && pitch - pitchChange <85)
				pitch -= pitchChange;
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPivot -= angleChange;
			if(pitchChange!=0 && angleChange!=0)
				Menu_circulaire_creation.setDraw(false);
		}
		
		if(zoomLevel!=0)
			Menu_circulaire_creation.setDraw(false);
			
		float horizontalDistance = calculateHorizontal();
		float verticalDistance = calculateVertical();
		calculateCameraPosition(horizontalDistance,verticalDistance);
		
		yangle = 180 - angleAroundPivot;
		
		
		float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(angleAroundPivot)));
		float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(angleAroundPivot)));
		
		if(OSValidator.isWindows()){
			if(Keyboard.isKeyDown(Keyboard.KEY_Z)){
				lookAt.z += SPEED * Math.cos(Math.toRadians(angleAroundPivot));
				lookAt.x += SPEED * Math.sin(Math.toRadians(angleAroundPivot));
				Menu_circulaire_creation.setDraw(false);
				
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
				lookAt.z -= SPEED * Math.cos(Math.toRadians(angleAroundPivot));
				lookAt.x -= SPEED * Math.sin(Math.toRadians(angleAroundPivot));
				Menu_circulaire_creation.setDraw(false);
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
				lookAt.z -= SPEED * Math.sin(Math.toRadians(angleAroundPivot));
				lookAt.x += SPEED * Math.cos(Math.toRadians(angleAroundPivot));
				Menu_circulaire_creation.setDraw(false);
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
				lookAt.z += SPEED * Math.sin(Math.toRadians(angleAroundPivot));
				lookAt.x -= SPEED * Math.cos(Math.toRadians(angleAroundPivot));
				Menu_circulaire_creation.setDraw(false);
			}
		}
		Point mouse = frame.getMousePosition();
		
		if(Mouse.isInsideWindow()){
			if(Mouse.getX()<Display.getWidth() && Mouse.getX()>Display.getWidth()-MARGIN){
				lookAt.z += SPEED * Math.sin(Math.toRadians(angleAroundPivot));
				lookAt.x -= SPEED * Math.cos(Math.toRadians(angleAroundPivot));
			}
			if(Mouse.getX()<MARGIN && Mouse.getX()>0){
				lookAt.z -= SPEED * Math.sin(Math.toRadians(angleAroundPivot));
				lookAt.x += SPEED * Math.cos(Math.toRadians(angleAroundPivot));
			}
			if(Mouse.getY()<Display.getHeight() && Mouse.getY()>Display.getHeight()-MARGIN){
				lookAt.z += SPEED * Math.cos(Math.toRadians(angleAroundPivot));
				lookAt.x += SPEED * Math.sin(Math.toRadians(angleAroundPivot));
			}
		}
		if(mouse!=null){
			if(mouse.x<frame.getWidth() && mouse.x>frame.getWidth()-MARGIN && mouse.y>MARGIN){
				lookAt.z += SPEED * Math.sin(Math.toRadians(angleAroundPivot));
				lookAt.x -= SPEED * Math.cos(Math.toRadians(angleAroundPivot));
			}
			if(mouse.x<MARGIN && mouse.x>0 && mouse.y>MARGIN){
				lookAt.z -= SPEED * Math.sin(Math.toRadians(angleAroundPivot));
				lookAt.x += SPEED * Math.cos(Math.toRadians(angleAroundPivot));
			}
			if( mouse.y<frame.getHeight() && mouse.y>frame.getHeight() - MARGIN){
				lookAt.z -= SPEED * Math.cos(Math.toRadians(angleAroundPivot));
				lookAt.x -= SPEED * Math.sin(Math.toRadians(angleAroundPivot));
			}
		}
		
	}
	
	public void increaseZ(float dz){
		lookAt.z += SPEED * dz;
	}

	public void increaseX(float dx){
		lookAt.x += SPEED * dx;
	}
	
	public void decreaseZ(float dz){
		lookAt.z -= SPEED * dz;
	}
	
	public void decreaseX(float dx){
		lookAt.x -= SPEED * dx;
	}
	
	private void calculateCameraPosition(float hD, float vD){
		float offsetX = (float) (hD * Math.sin(Math.toRadians(angleAroundPivot)));
		float offsetZ = (float) (hD * Math.cos(Math.toRadians(angleAroundPivot)));
		position.x = lookAt.x - offsetX;
		position.y = lookAt.y + vD;
		position.z = lookAt.z - offsetZ;
	}
	
	private float calculateHorizontal(){
		return (float) (distanceFromPivot * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVertical(){
		return (float) (distanceFromPivot * Math.sin(Math.toRadians(pitch)));
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYangle() {
		return yangle;
	}

	public float getRoll() {
		return roll;
	}
	
	public void recenter(){
		float x = 3f/4f*Terrain.CENTRE.y*Grid.HEIGHT_OF_HEXA - Grid.HEIGHT_OF_HEXA/2f;
		float y = Terrain.TAILLE*Grid.WIDTH_OF_HEXA/2f + Grid.WIDTH_OF_HEXA*2f + Terrain.CENTRE.x * Grid.WIDTH_OF_HEXA/2f;
		lookAt = new Vector3f(x,0,y);
	}
	
	public void reset(){
		recenter();
		pitch=40;
		angleAroundPivot=270;
		distanceFromPivot=100;
	}

	public float getDistanceFromPivot() {
		return distanceFromPivot;
	}

	public void setDistanceFromPivot(float distanceFromPivot) {
		this.distanceFromPivot = distanceFromPivot;
	}

	public float getAngleAroundPivot() {
		return angleAroundPivot;
	}
	
}
