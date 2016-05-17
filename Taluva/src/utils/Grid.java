package utils;

import java.awt.Point;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Loaders.Loader;
import entities.Object3D;
import terrain.Terrain;
import renderEngine.Renderer;
import shaders.StaticShader;

public class Grid {
	
	public class Coords{
		public Vector3f worldPos;
		public Point indices;
		
		public Coords(Vector3f worldPos,Point indices){
			this.worldPos = worldPos;
			this.indices = indices;
		}
	}
	
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
		coords = new Vector2f[terrain.TAILLE-190][terrain.TAILLE-190];
		object = new Object3D[terrain.TAILLE-190][terrain.TAILLE-190];
		Object3D temp = new Object3D("","hexa",loader,new Vector3f(0,0,0),0,0,0,0.5f);
		float width = 0;
		for(int j=0 ;j<terrain.TAILLE-190;j++){
			for(int i=0 ;i<terrain.TAILLE-190;i++){
				if(j%2==0)
					width = WIDTH_OF_HEXA/2;
				else
					width = WIDTH_OF_HEXA;
				coords[i][j] = new Vector2f(width+(float)i*WIDTH_OF_HEXA,(float)j*3/4*HEIGHT_OF_HEXA);
				object[i][j] = new Object3D(temp);
				object[i][j].setPosition(new Vector3f(coords[i][j].x,0,coords[i][j].y));
			}
		}
	}
	
	
	private void setTerrain(Terrain terrain){
		this.terrain = terrain;
	}
	

	public void draw(Renderer render,StaticShader shader){
		for(int i=0 ;i<terrain.TAILLE-190;i++)
			for(int j=0 ;j<terrain.TAILLE-190;j++){
				render.draw(object[i][j], shader);
			}
	}
	
	private Point convert(Point p, boolean equalize){
		Point res = new Point(Math.abs((p.x-(terrain.TAILLE-190-1))%(terrain.TAILLE-190-1)),Math.abs((p.y-(terrain.TAILLE-190-1))%(terrain.TAILLE-190-1)));
		if(p.x==0)
			res.x=(terrain.TAILLE-190-1);
		if(p.y==0)
			res.y=(terrain.TAILLE-190-1);
		if(equalize){
			if(res.x<9)
				res.x ++;
			res.y --;
		}
			
		//System.out.println(res);
		return res;
	}
	
	//racine((x_centre - x_point)� + (y_centre - y_point)�)<rayon
	public Coords snap(Object3D object3d,Vector3f positionVolcano,float angle){
		float offsetX = 0;
		float offsetY = 0;
		Point indices = new Point();
		
		if(angle==60 || angle==180 ||  angle==300){
			offsetX = WIDTH_OF_HEXA/2;
			offsetY = -HEIGHT_OF_HEXA/4;
		}
		
		for(int i=0 ;i<terrain.TAILLE-190;i++){
			for(int j=0 ;j<terrain.TAILLE-190;j++){
				if( Math.pow(positionVolcano.x - (coords[i][j].x+offsetX),2) + Math.pow(positionVolcano.z - (coords[i][j].y+offsetY),2) <= Math.pow(RAY,2) ){
					object3d.setAllow(true);
					//System.out.println("Incices:" + i +" " + j);
					if(angle==60 || angle==180 ||  angle==300)
						indices = convert(new Point(i,j),false);
					else
						indices = convert(new Point(i,j),true);
					return new Coords(new Vector3f(coords[i][j].x+offsetX,0,coords[i][j].y+offsetY),indices);
				}
			}
		}
		
		object3d.setAllow(false);
		
		return null;
	}
	
	public Coords snap(Object3D object3d,Vector3f positionVolcano){

		
		for(int i=0 ;i<terrain.TAILLE-190;i++){
			for(int j=0 ;j<terrain.TAILLE-190;j++){
				if( Math.pow(positionVolcano.x - coords[i][j].x,2) + Math.pow(positionVolcano.z - (coords[i][j].y-HEIGHT_OF_HEXA/2),2) <= Math.pow(RAY,2) ){
					object3d.setAllow(true);
					System.out.println("Incices:" + i +" " + j);
					return new Coords(new Vector3f(coords[i][j].x,0,coords[i][j].y-HEIGHT_OF_HEXA/2),new Point(i,j));
				}
			}
		}
		
		object3d.setAllow(false);
		
		return null;
	}
	
	public Coords center(){
		int i = (terrain.TAILLE-190)/2;
		int j = (terrain.TAILLE-190)/2;
		return new Coords(new Vector3f(coords[i][j].x,0,coords[i][j].y-HEIGHT_OF_HEXA/2),new Point(i,j));
	}
}