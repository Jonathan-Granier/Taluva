package utils;

import java.awt.Point;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Object3D;
import terrain.Terrain;

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
	
	public static final float WIDTH_OF_HEXA = 8.1f;
	public static final float HEIGHT_OF_HEXA = 19f/2f;
	public static final float HEIGHT_OF_TILE = 1;
	public static final float RAY = 19.5f/4f;
	
	private static Vector2f[][] coords;
	
	//terrain.taille * HEIGHT_OF_HEXA/2
	public Grid(){
		coords = new Vector2f[Terrain.TAILLE][Terrain.TAILLE];
		float yTemp = Terrain.TAILLE * 1f/2f * WIDTH_OF_HEXA + WIDTH_OF_HEXA;
		float x = -HEIGHT_OF_HEXA/2f;
		float y;
		for(int j=0 ;j<Terrain.TAILLE;j++){
			x += 3f/4f*HEIGHT_OF_HEXA;
			yTemp -= 1f/2f * WIDTH_OF_HEXA;
			y = yTemp;
			for(int i=0 ;i<Terrain.TAILLE;i++){
				y += WIDTH_OF_HEXA;
				coords[i][j] = new Vector2f(x,y);
			}
		}
	}
	
	//racine((x_centre - x_point)� + (y_centre - y_point)�)<rayon
	public Coords snap(Object3D object3d,Vector3f mouse,float angle){
		float offsetX = 0;
		float offsetY = 0;
		
		if(angle==30 || angle==150 ||  angle==270){
			offsetX = -HEIGHT_OF_HEXA/4f;
			offsetY = WIDTH_OF_HEXA/2f;
		}
		
		Coords center = new Coords();
		int i=0;
		while(i<Terrain.TAILLE && center.worldPos==null){
			for(int j=0 ;j<Terrain.TAILLE;j++){
				if( Math.pow(mouse.x - (coords[i][j].x+offsetX),2) + Math.pow(mouse.z - (coords[i][j].y+offsetY),2) <= Math.pow(RAY,2) ){
					object3d.setAllow(true);
					center = new Coords(new Vector3f(coords[i][j].x+offsetX,0,coords[i][j].y+offsetY),new Point(j,i));
					break;
				}
			}
			i++;
		}

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
		
		for(int i=0 ;i<Terrain.TAILLE;i++){
			for(int j=0 ;j<Terrain.TAILLE;j++){
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
	
	public Coords snap(Vector3f mouse){
		
		for(int i=0 ;i<Terrain.TAILLE;i++){
			for(int j=0 ;j<Terrain.TAILLE;j++){
				if( Math.pow(mouse.x - (coords[i][j].x-HEIGHT_OF_HEXA/2f),2) + Math.pow(mouse.z - (coords[i][j].y),2) <= Math.pow(RAY,2) ){
					//System.out.println("Incices:" + i +" " + j);
					return new Coords(new Vector3f(coords[i][j].x-HEIGHT_OF_HEXA/2f,0,coords[i][j].y),new Point(j,i));
				}
			}
		}
		
		
		return null;
	}
	
	public Coords center(){
		int i = (Terrain.TAILLE)/2;
		int j = (Terrain.TAILLE)/2;
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

	public Vector3f toWorldPos(Point indice,int level){
		Point pos = new Point(indice);
		
		return new Vector3f(coords[pos.y][pos.x].x-HEIGHT_OF_HEXA/2f,level*HEIGHT_OF_TILE,coords[pos.y][pos.x].y);
	}
	
}
