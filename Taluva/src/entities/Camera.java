package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import terrain.Terrain;
import utils.Grid;

public class Camera {
	
	private static final float ZOOM_OUT_MAX = 600;
	
	private static final float SPEED = 3f;
	
	private Vector3f position = new Vector3f(0,0,0);
	private Vector3f lookAt = new Vector3f(0,0,0);
	private float pitch=40;
	private float angleAroundPivot=90;
	private float distanceFromPivot=400;
	private float yangle;
	private float roll;
	
	
	public Camera(){
		position.x = Terrain.TAILLE/2*Grid.HEIGHT_OF_HEXA*2f/3f+200;
		position.z = Terrain.TAILLE*Grid.WIDTH_OF_HEXA*3f/4f;
		lookAt.x = Terrain.TAILLE/2*Grid.HEIGHT_OF_HEXA*2f/3f+200;
		lookAt.z = Terrain.TAILLE*Grid.WIDTH_OF_HEXA*3f/4f;
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
		
		
		if (distanceFromPivot - zoomLevel > 5f && distanceFromPivot - zoomLevel < ZOOM_OUT_MAX )
			distanceFromPivot -= zoomLevel;
		
		
		if(Mouse.isButtonDown(0) && Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			float pitchChange = Mouse.getDY() * 0.3f;
			if(pitch - pitchChange >1 && pitch - pitchChange <85)
				pitch -= pitchChange;
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPivot -= angleChange;
			
		}
		
		float horizontalDistance = calculateHorizontal();
		float verticalDistance = calculateVertical();
		calculateCameraPosition(horizontalDistance,verticalDistance);
		
		yangle = 180 - angleAroundPivot;
		
		between(0,90);
		
        /*position.x = (float) ( -Math.sin(cameraX*(Math.PI/180)) * Math.cos((cameraY)*(Math.PI/180)));
        position.y = (float) (  -Math.sin((cameraY)*(Math.PI/180)));
        position.z = (float) ( -Math.cos((cameraX)*(Math.PI/180)) * Math.cos((cameraY)*(Math.PI/180)));*/
		
		float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(angleAroundPivot)));
		float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(angleAroundPivot)));
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Z)){
			if(between(0,45) || between(315,360) )
				lookAt.z += SPEED;
			else if(between(180,255) || between(135,180))
				lookAt.z -= SPEED;
			else if(between(90,135) || between(45,90))
				lookAt.x += SPEED;
			else if(between(270,315) || between(225,270))
				lookAt.x -= SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			if(between(0,45) || between(315,360) )
				lookAt.z -= SPEED;
			else if(between(180,255) || between(135,180))
				lookAt.z += SPEED;
			else if(between(90,135) || between(45,90))
				lookAt.x -= SPEED;
			else if(between(270,315) || between(225,270))
				lookAt.x += SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			if(between(0,45) || between(315,360) )
				lookAt.x += SPEED;
			else if(between(180,255) || between(135,180))
				lookAt.x -= SPEED;
			else if(between(90,135) || between(45,90))
				lookAt.z -= SPEED;
			else if(between(270,315) || between(225,270))
				lookAt.z += SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			if(between(0,45) || between(315,360) )
				lookAt.x -= SPEED;
			else if(between(180,255) || between(135,180))
				lookAt.x += SPEED;
			else if(between(90,135) || between(45,90))
				lookAt.z += SPEED;
			else if(between(270,315) || between(225,270))
				lookAt.z -= SPEED;
		}
		
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
	
	
	
}
