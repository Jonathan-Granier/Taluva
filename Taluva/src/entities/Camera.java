package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import IHM.Menu_circulaire_creation;
import terrain.Terrain;
import utils.Grid;
import utils.OSValidator;

public class Camera {
	
	private class Vue{
		public Vector3f lookAt;
		public float pitch;
		public float angleAroundPivot;
		public float distanceFromPivot;
		public Vue(Vector3f lookAt, float pitch, float angleAroundPivot, float distanceFromPivot) {
			this.lookAt = lookAt;
			this.pitch = pitch;
			this.angleAroundPivot = angleAroundPivot;
			this.distanceFromPivot = distanceFromPivot;
		}
	}
	
	private static final float ZOOM_OUT_MAX = 200;
	
	private static final float SPEED = 1.5f;
	
	private Vector3f position = new Vector3f(0,0,0);
	private Vector3f lookAt = new Vector3f(0,0,0);
	private float pitch=40;
	private float angleAroundPivot=270;
	private float distanceFromPivot=100;
	private float yangle;
	private float roll;
	
	public static final int nb_vues = 4;
	private Vue[] vues;
	
	public Camera(){
		float x = 3f/4f*Terrain.CENTRE.y*Grid.HEIGHT_OF_HEXA - Grid.HEIGHT_OF_HEXA/2f;
		float y = Terrain.TAILLE*Grid.WIDTH_OF_HEXA/2f + Grid.WIDTH_OF_HEXA*2f + Terrain.CENTRE.x * Grid.WIDTH_OF_HEXA/2f;
		lookAt = new Vector3f(x,0,y);
		//position.x = Terrain.TAILLE/2*Grid.HEIGHT_OF_HEXA*2f/3f+100;
		//position.z = Terrain.TAILLE*Grid.WIDTH_OF_HEXA*3f/4f;
		vues = new Vue[nb_vues];
		vues[0] = new Vue(lookAt,40,270,100);
		vues[1] = new Vue(lookAt,40,0,100);
		vues[2] = new Vue(lookAt,40,90,100);
		vues[3] = new Vue(lookAt,40,180,100);
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

		//float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(angleAroundPivot)));
		//float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(angleAroundPivot)));

		if(OSValidator.isWindows()){
			if(Keyboard.isKeyDown(Keyboard.KEY_Z)){
				if(lookAt.z + SPEED * Math.cos(Math.toRadians(angleAroundPivot))<Terrain.TAILLE * Grid.WIDTH_OF_HEXA && lookAt.z + SPEED * Math.cos(Math.toRadians(angleAroundPivot))>0)
					lookAt.z += SPEED * Math.cos(Math.toRadians(angleAroundPivot));
				if(lookAt.x + SPEED * Math.sin(Math.toRadians(angleAroundPivot))<Terrain.TAILLE * Grid.WIDTH_OF_HEXA && lookAt.x + SPEED * Math.sin(Math.toRadians(angleAroundPivot))>0)
					lookAt.x += SPEED * Math.sin(Math.toRadians(angleAroundPivot));
				Menu_circulaire_creation.setDraw(false);
				
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
				if(lookAt.z - SPEED * Math.cos(Math.toRadians(angleAroundPivot))<Terrain.TAILLE * Grid.WIDTH_OF_HEXA && lookAt.z - SPEED * Math.cos(Math.toRadians(angleAroundPivot))>0)
					lookAt.z -= SPEED * Math.cos(Math.toRadians(angleAroundPivot));
				if(lookAt.x - SPEED * Math.sin(Math.toRadians(angleAroundPivot))<Terrain.TAILLE * Grid.WIDTH_OF_HEXA && lookAt.x - SPEED * Math.sin(Math.toRadians(angleAroundPivot))>0)
					lookAt.x -= SPEED * Math.sin(Math.toRadians(angleAroundPivot));
				Menu_circulaire_creation.setDraw(false);
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
				if(lookAt.z - SPEED * Math.sin(Math.toRadians(angleAroundPivot))<Terrain.TAILLE * Grid.WIDTH_OF_HEXA && lookAt.z - SPEED * Math.sin(Math.toRadians(angleAroundPivot))>0)
					lookAt.z -= SPEED * Math.sin(Math.toRadians(angleAroundPivot));
				if(lookAt.x + SPEED * Math.cos(Math.toRadians(angleAroundPivot))<Terrain.TAILLE * Grid.WIDTH_OF_HEXA && lookAt.x + SPEED * Math.cos(Math.toRadians(angleAroundPivot))>0)	
					lookAt.x += SPEED * Math.cos(Math.toRadians(angleAroundPivot));
				Menu_circulaire_creation.setDraw(false);
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
				if(lookAt.z + SPEED * Math.sin(Math.toRadians(angleAroundPivot))<Terrain.TAILLE * Grid.WIDTH_OF_HEXA && lookAt.z + SPEED * Math.sin(Math.toRadians(angleAroundPivot))>0)
					lookAt.z += SPEED * Math.sin(Math.toRadians(angleAroundPivot));
				if(lookAt.x - SPEED * Math.cos(Math.toRadians(angleAroundPivot))<Terrain.TAILLE * Grid.WIDTH_OF_HEXA && lookAt.x - SPEED * Math.cos(Math.toRadians(angleAroundPivot))>0)
					lookAt.x -= SPEED * Math.cos(Math.toRadians(angleAroundPivot));
				Menu_circulaire_creation.setDraw(false);
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
				reset();
				Menu_circulaire_creation.setDraw(false);
			}
		}
		
		if(Mouse.isButtonDown(2)){
			float offsetDX = -Mouse.getDX() * 0.1f;
			if(lookAt.z + offsetDX * Math.sin(Math.toRadians(angleAroundPivot))<Terrain.TAILLE * Grid.WIDTH_OF_HEXA && lookAt.z + offsetDX * Math.sin(Math.toRadians(angleAroundPivot))>0)
				lookAt.z += offsetDX * Math.sin(Math.toRadians(angleAroundPivot));
			if(lookAt.x - offsetDX * Math.cos(Math.toRadians(angleAroundPivot))<Terrain.TAILLE * Grid.WIDTH_OF_HEXA && lookAt.x - offsetDX * Math.cos(Math.toRadians(angleAroundPivot))>0)			
				lookAt.x -= offsetDX * Math.cos(Math.toRadians(angleAroundPivot));
			
			float offsetDY = -Mouse.getDY() * 0.1f;
			if(lookAt.z + offsetDX * Math.cos(Math.toRadians(angleAroundPivot))<Terrain.TAILLE * Grid.WIDTH_OF_HEXA && lookAt.z + offsetDX * Math.cos(Math.toRadians(angleAroundPivot))>0)
				lookAt.z += offsetDY * Math.cos(Math.toRadians(angleAroundPivot));
			if(lookAt.x + offsetDX * Math.sin(Math.toRadians(angleAroundPivot))<Terrain.TAILLE * Grid.WIDTH_OF_HEXA && lookAt.x + offsetDX * Math.sin(Math.toRadians(angleAroundPivot))>0)
				lookAt.x += offsetDY * Math.sin(Math.toRadians(angleAroundPivot));
			Menu_circulaire_creation.setDraw(false);
		}
		
	}
	
	public void increaseZ(float dz){
		if(lookAt.z + SPEED * dz<Terrain.TAILLE * Grid.WIDTH_OF_HEXA && lookAt.z + SPEED * dz>0)
			lookAt.z += SPEED * dz;
	}

	public void increaseX(float dx){
		if(lookAt.x + SPEED * dx<Terrain.TAILLE * Grid.WIDTH_OF_HEXA && lookAt.x + SPEED * dx>0)
			lookAt.x += SPEED * dx;
	}
	
	public void decreaseZ(float dz){
		if(lookAt.z - SPEED * dz<Terrain.TAILLE * Grid.WIDTH_OF_HEXA && lookAt.z - SPEED * dz>0)
			lookAt.z -= SPEED * dz;
	}
	
	public void decreaseX(float dx){
		if(lookAt.x - SPEED * dx<Terrain.TAILLE * Grid.WIDTH_OF_HEXA && lookAt.x - SPEED * dx>0)
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
		lookAt = new Vector3f(vues[0].lookAt);
	}
	
	public void reset(){
		updateVue(0);
	}
	
	public void updateVue(int num_vue){
		lookAt = new Vector3f(vues[num_vue].lookAt);
		pitch = vues[num_vue].pitch;
		angleAroundPivot = vues[num_vue].angleAroundPivot;
		distanceFromPivot = vues[num_vue].distanceFromPivot;
	}
	
	public void setVueIA(){
		updateVue(0);
		pitch = 60;
		distanceFromPivot = 150;
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
