package utils;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Loaders.Loader;
import entities.Object3D;
import main.Terrain;
import renderEngine.Renderer;
import shaders.StaticShader;

public class Grid {
	
	private static final float WIDTH_OF_HEXA = 34;
	private static final float HEIGHT_OF_HEXA = 39;
	private static final float RAY = 39/2;
	
	private static Terrain terrain;
	private static Vector2f[][] coords;
	private static Object3D[][] object;
	private static Loader loader;
	
	public Grid(Terrain terrain, Loader loader){
		setTerrain(terrain);
		this.loader = loader;
		coords = new Vector2f[terrain.TAILLE-196][terrain.TAILLE-196];
		object = new Object3D[terrain.TAILLE-196][terrain.TAILLE-196];
		float width = 0;
		for(int j=0 ;j<terrain.TAILLE-196;j++){
			for(int i=0 ;i<terrain.TAILLE-196;i++){
				if(j%2==0)
					width = WIDTH_OF_HEXA/2;
				else
					width = WIDTH_OF_HEXA;
				coords[i][j] = new Vector2f(width+(float)i*WIDTH_OF_HEXA,(float)j*3/4*HEIGHT_OF_HEXA);
				object[i][j] = new Object3D("","hexa",loader,new Vector3f(coords[i][j].x,0,coords[i][j].y),0,0,0,0.5f);
			}
		}
	}
	
	
	private void setTerrain(Terrain terrain){
		this.terrain = terrain;
	}
	

	public void draw(Renderer render,StaticShader shader){
		for(int i=0 ;i<terrain.TAILLE-196;i++)
			for(int j=0 ;j<terrain.TAILLE-196;j++){
				render.draw(object[i][j], shader);
			}
	}
	
	//racine((x_centre - x_point)² + (y_centre - y_point)²)<rayon
	public Vector3f snap(Vector3f positionVolcano,float angle){
		float offsetX = 0;
		float offsetY = 0;
		
		if(angle==60 || angle==180 ||  angle==300){
			offsetX = WIDTH_OF_HEXA/2;
			offsetY = -HEIGHT_OF_HEXA/4;
		}
		
		for(int i=0 ;i<terrain.TAILLE-196;i++){
			for(int j=0 ;j<terrain.TAILLE-196;j++){
				if( Math.pow(positionVolcano.x - (coords[i][j].x+offsetX),2) + Math.pow(positionVolcano.z - (coords[i][j].y+offsetY),2) <= Math.pow(RAY,2) ){
					return new Vector3f(coords[i][j].x+offsetX,0,coords[i][j].y+offsetY);
				}
			}
		}
		//System.out.println("false");
		return null;
	}
	
	
}
