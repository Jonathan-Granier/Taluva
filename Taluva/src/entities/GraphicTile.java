package entities;

import org.lwjgl.util.vector.Vector3f;

import loaders.Loader;
import loaders.OBJLoader;
import materials.Material;
import terrain.Case;
import terrain.Tuile;
import terrain.Case.Orientation;

public class GraphicTile {
	
	private static final float RAY = 19.5f/4f;
	private static final float HEIGHT_OF_TILE = 1.5f;
	
	private Object3D object3d;
	private Tuile tile;
	private float angle;
	private float height;
	private Vector3f postionVolcano;
	private static Loader loader;
	
	private static Object3D[] objects3d = new Object3D[25];
	
	private static String[] texture_name = {"VFF","VFL","VFM","VFP","VFS","VLF","VLL","VLM","VLP","VLS","VMF","VML","VMM","VMP","VMS","VPF","VPL",
			"VPM","VPP","VPS","VSF","VSL","VSM","VSP","VSS"
	};
	
	private static int[] textureId = new int[25]; 
	
	public GraphicTile(Tuile tile,Loader loader,Vector3f position){
		this.object3d = new Object3D("Tile",loader,position,0,0,0,0.12f);
		this.tile = tile;
		this.angle = 0;
		this.height = 0;
	}

	public GraphicTile(GraphicTile tile){
		this.object3d = new Object3D(tile.getObject3D());
		this.tile = tile.getTile().clone();
		this.angle = tile.getAngle();
		this.height = tile.getHeight();
	}
	
	public GraphicTile(Tuile tile,Loader loader,Vector3f position,float rotY){
		this.object3d = new Object3D("Tile",loader,position,0,rotY,0,0.12f);
		this.tile = tile;
		this.angle = rotY;
		this.height = 0;
	}
	
	public static void setTexture(Loader l){
		loader = l;
		for(int i=0;i<25;i++){
			textureId[i] = loader.loadTexture("Tile/"+texture_name[i]+".png");
			objects3d[i] = new Object3D("Tile",loader,new Vector3f(0,0,0),0,0,0,0.12f);
			objects3d[i].getModel().getTexture().setID(textureId[i]);
		}
			
	}
	
	public void increaseHeight(int level){
		height = level * HEIGHT_OF_TILE;
	}
	
	public void decreaseHeight(){
		if(height>0){
			height -= HEIGHT_OF_TILE;
		}
	}
	
	public void rotate(){
		tile.Tourner_horaire();
		switch(tile.get_Orientation_Volcan()){
			case O:
				object3d.setRotY(90);
				break;
			case S_O:
				object3d.setRotY(150);
				break;
			case S_E:
				object3d.setRotY(210);
				break;
			case E:
				object3d.setRotY(270);
				break;
			case N_E:
				object3d.setRotY(330);
				break;
			case N_O:
				object3d.setRotY(30);
				break;
			default:
				System.out.println("Rotate cannot be done");
		}
	}
	
	public Vector3f getPostionVolcano() {
		return postionVolcano;
	}

	public void setPostionVolcano() {
		Vector3f center = new Vector3f(object3d.getPosition());
		//postionVolcano
		switch(tile.get_Orientation_Volcan()){
			case O:
				postionVolcano = new Vector3f(center.x-RAY,0,center.z);
				break;
			case S_O:
				postionVolcano = new Vector3f((float) (center.x-RAY*Math.cos(Math.toRadians(30))),0,(float) (center.z+RAY*Math.sin(Math.toRadians(30))));
				break;
			case S_E:
				postionVolcano = new Vector3f((float) (center.x+RAY*Math.cos(Math.toRadians(30))),0,(float) (center.z+RAY*Math.sin(Math.toRadians(30))));
				break;
			case E:
				postionVolcano = new Vector3f(center.x+RAY,0,center.z);
				break;
			case N_E:
				postionVolcano = new Vector3f((float) (center.x+RAY*Math.cos(Math.toRadians(30))),0,(float) (center.z-RAY*Math.sin(Math.toRadians(30))));
				break;
			case N_O:
				postionVolcano = new Vector3f((float) (center.x-RAY*Math.cos(30)),0,(float) (center.z-RAY*Math.sin(30)));
				break;
			default:
				System.out.println("Orientation unknow");
		}
	}

	public Object3D getObject3D() {
		return object3d;
	}

	public void setObject3D(Object3D object3d) {
		this.object3d = object3d;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	public void setAngle(){
		switch(tile.get_Orientation_Volcan()){
		case O:
			object3d.setRotY(90);
			break;
		case S_O:
			object3d.setRotY(150);
			break;
		case S_E:
			object3d.setRotY(210);
			break;
		case E:
			object3d.setRotY(270);
			break;
		case N_E:
			object3d.setRotY(330);
			break;
		case N_O:
			object3d.setRotY(30);
			break;
		default:
			System.out.println("Rotate cannot be done");
		}
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Tuile getTile() {
		return tile;
	}

	public void setTile(Tuile tile) {
		this.tile = tile.clone();
		int i = 0;
		boolean found = false;
		while(i<25 && !found){
			System.out.println(texture_name[i] + "--" + texture_name[i].charAt(1) + "--" + texture_name[i].charAt(2));
			switch(tile.get_type_case(Orientation.S)){
			case MONTAGNE:
				if(texture_name[i].charAt(1) == 'M' )
					switch(tile.get_type_case(Orientation.N)){
					case MONTAGNE:
						if(texture_name[i].charAt(2) == 'M' )
							found = true;
						break;
					case PLAINE:
						if(texture_name[i].charAt(2) == 'P' )
							found = true;
						break;
					case LAC:
						if(texture_name[i].charAt(2) == 'L' )
							found = true;
						break;
					case SABLE:
						if(texture_name[i].charAt(2) == 'S' )
							found = true;
						break;
					case FORET:
						if(texture_name[i].charAt(2) == 'F' )
							found = true;
						break;
					default:
						break;
					}
				break;
			case PLAINE:
				if(texture_name[i].charAt(1) == 'P' )
					switch(tile.get_type_case(Orientation.N)){
					case MONTAGNE:
						if(texture_name[i].charAt(2) == 'M' )
							found = true;
						break;
					case PLAINE:
						if(texture_name[i].charAt(2) == 'P' )
							found = true;
						break;
					case LAC:
						if(texture_name[i].charAt(2) == 'L' )
							found = true;
						break;
					case SABLE:
						if(texture_name[i].charAt(2) == 'S' )
							found = true;
						break;
					case FORET:
						if(texture_name[i].charAt(2) == 'F' )
							found = true;
						break;
					default:
						break;
					}
				break;
			case LAC:
				if(texture_name[i].charAt(1) == 'L' )
					switch(tile.get_type_case(Orientation.N)){
					case MONTAGNE:
						if(texture_name[i].charAt(2) == 'M' )
							found = true;
						break;
					case PLAINE:
						if(texture_name[i].charAt(2) == 'P' )
							found = true;
						break;
					case LAC:
						if(texture_name[i].charAt(2) == 'L' )
							found = true;
						break;
					case SABLE:
						if(texture_name[i].charAt(2) == 'S' )
							found = true;
						break;
					case FORET:
						if(texture_name[i].charAt(2) == 'F' )
							found = true;
						break;
					default:
						break;
					}
				break;
			case SABLE:
				if(texture_name[i].charAt(1) == 'S' )
					switch(tile.get_type_case(Orientation.N)){
					case MONTAGNE:
						if(texture_name[i].charAt(2) == 'M' )
							found = true;
						break;
					case PLAINE:
						if(texture_name[i].charAt(2) == 'P' )
							found = true;
						break;
					case LAC:
						if(texture_name[i].charAt(2) == 'L' )
							found = true;
						break;
					case SABLE:
						if(texture_name[i].charAt(2) == 'S' )
							found = true;
						break;
					case FORET:
						if(texture_name[i].charAt(2) == 'F' )
							found = true;
						break;
					default:
						break;
					}
				break;
			case FORET:
				if(texture_name[i].charAt(1) == 'F' )
					switch(tile.get_type_case(Orientation.N)){
					case MONTAGNE:
						if(texture_name[i].charAt(2) == 'M' )
							found = true;
						break;
					case PLAINE:
						if(texture_name[i].charAt(2) == 'P' )
							found = true;
						break;
					case LAC:
						if(texture_name[i].charAt(2) == 'L' )
							found = true;
						break;
					case SABLE:
						if(texture_name[i].charAt(2) == 'S' )
							found = true;
						break;
					case FORET:
						if(texture_name[i].charAt(2) == 'F' )
							found = true;
						break;
					default:
						break;
					}
				break;
			default:
				break;
			}
			if(!found)
				i++;
		}
		System.out.println(texture_name[i]);
		if(i<25)
			object3d = new Object3D(objects3d[i]);
	}
	
}
