package utils;

import java.awt.Point;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Loaders.Loader;
import entities.Object3D;
import terrain.Terrain;
import renderEngine.Renderer;
import shaders.Shader;

public class Grid {
	
	public class Coords{
		public Vector3f worldPos;
		public Point indices;

		public Coords(){
			this.worldPos = null;
			this.indices = null;
		}
		
		public Coords(Vector3f worldPos,Point indices){
			this.worldPos = worldPos;
			this.indices = indices;
		}
		
		public void increaseIndices(){
			indices.x = indices.x+1;
		}
	}
	
	public static final float WIDTH_OF_HEXA = 34f;
	public static final float HEIGHT_OF_HEXA = 39f;
	public static final float HEIGHT_OF_TILE = 2;
	public static final float RAY = 39f/2f;
	
	private static Terrain terrain;
	private static Vector2f[][] coords;
	private static Object3D[][] object;
	private static Loader loader;
	//terrain.taille * HEIGHT_OF_HEXA/2
	public Grid(Terrain terrain, Loader loader){
		setTerrain(terrain);
		this.loader = loader;
		coords = new Vector2f[terrain.TAILLE][terrain.TAILLE];
		object = new Object3D[terrain.TAILLE][terrain.TAILLE];
		Object3D temp = new Object3D("","hexa",loader,new Vector3f(0,0,0),0,0,0,0.5f);
		float yTemp = terrain.TAILLE * 1f/2f * WIDTH_OF_HEXA + WIDTH_OF_HEXA;
		float x = -HEIGHT_OF_HEXA/2f;
		float y;
		for(int j=0 ;j<terrain.TAILLE;j++){
			x += 3f/4f*HEIGHT_OF_HEXA;
			yTemp -= 1f/2f * WIDTH_OF_HEXA;
			y = yTemp;
			for(int i=0 ;i<terrain.TAILLE;i++){
				y += WIDTH_OF_HEXA;
				coords[i][j] = new Vector2f(x,y);
			}
		}
	}
	
	
	private void setTerrain(Terrain terrain){
		this.terrain = terrain;
	}
	

	public void draw(Renderer render,Shader shader){
		for(int i=0 ;i<terrain.TAILLE;i++)
			for(int j=0 ;j<terrain.TAILLE;j++){
				//render.draw(object[i][j], shader);
			}
	}
	
	private Point convert(Point p, boolean equalize){
		Point res = new Point(Math.abs((p.x-(terrain.TAILLE-1))%(terrain.TAILLE-1)),p.y);
		if(p.x==0)
			res.x=(terrain.TAILLE-1);

		return res;
	}
	
	//racine((x_centre - x_point)² + (y_centre - y_point)²)<rayon
	public Coords snap(Object3D object3d,Vector3f mouse,float angle){
		float offsetX = 0;
		float offsetY = 0;
		Point indices = new Point();
		
		if(angle==30 || angle==150 ||  angle==270){
			offsetX = -HEIGHT_OF_HEXA/4f;
			offsetY = WIDTH_OF_HEXA/2f;
		}
		
		Coords center = new Coords();
		int i=0;
		while(i<terrain.TAILLE && center.worldPos==null){
			for(int j=0 ;j<terrain.TAILLE;j++){
				if( Math.pow(mouse.x - (coords[i][j].x+offsetX),2) + Math.pow(mouse.z - (coords[i][j].y+offsetY),2) <= Math.pow(RAY,2) ){
					object3d.setAllow(true);
					center = new Coords(new Vector3f(coords[i][j].x+offsetX,0,coords[i][j].y+offsetY),new Point(j,i));
					break;
				}
			}
			i++;
		}
		
		Vector2f point = new Vector2f();
		if(center.worldPos!=null){
			if((angle == 90 || angle ==330 || angle == 210)){
				//point.x = (float) (center.worldPos.x + Math.cos(Math.toRadians(60)) * HEIGHT_OF_HEXA/2f);
				//point.y = (float) (center.worldPos.z - Math.sin(Math.toRadians(60)) * HEIGHT_OF_HEXA/2f);
				center.increaseIndices();
			}
			//System.out.println("Incices:"+ center.indices.x+" "+center.indices.y);
			return new Coords(new Vector3f(center.worldPos.x,0,center.worldPos.z),new Point(center.indices.x,center.indices.y));
		}
		
		
		object3d.setAllow(false);
		
		return null;
	}
	
	public Coords snap(Object3D object3d,Vector3f mouse){
		
		Coords center = new Coords();
		
		for(int i=0 ;i<terrain.TAILLE;i++){
			for(int j=0 ;j<terrain.TAILLE;j++){
				if( Math.pow(mouse.x - (coords[i][j].x-HEIGHT_OF_HEXA/2f),2) + Math.pow(mouse.z - (coords[i][j].y),2) <= Math.pow(RAY,2) ){
					object3d.setAllow(true);
					//System.out.println("Incices:" + i +" " + j);
					return new Coords(new Vector3f(coords[i][j].x-HEIGHT_OF_HEXA/2f,0,coords[i][j].y),new Point(j,i));
				}
			}
		}
		
		object3d.setAllow(false);
		
		return null;
	}
	
	public Coords center(){
		int i = (terrain.TAILLE)/2;
		int j = (terrain.TAILLE)/2;
		return new Coords(new Vector3f(coords[i][j].x,0,coords[i][j].y),new Point(i,j));
	}
	
	public Vector3f toWorldPos(Point indice,float angle,int level){
		Point pos = new Point(indice);
		
		float offsetX = 0;
		float offsetY = 0;
		
		int offset = 0;
		
		if(angle==30 || angle==150 ||  angle==270){
			offsetX = -HEIGHT_OF_HEXA/4f;
			offsetY = WIDTH_OF_HEXA/2f;
			
		}
		else
			offset = -1;
		
		return new Vector3f(coords[pos.y][pos.x+offset].x+offsetX,level*HEIGHT_OF_TILE,coords[pos.y][pos.x+offset].y+offsetY);
	}
	
}
