import java.lang.Math;

public class Player {
	float x,y=0;
	float angle = 0;
	float FOV = 3.1415f/8f;
	int depth = 25*25;
	
	Player(){
		
	}
	
	public float Ray(String map, float a) {
		float dist2wall = 0;
		boolean hit = false;
		float testX,testY;
		float dirX = (float)Math.cos(angle+a);
		float dirY = (float)Math.sin(angle+a);
		while(!hit && dist2wall<depth) {
			dist2wall+=0.005;
			testX = (x+25/2+dirX*dist2wall);
			testY = (y+25/2+dirY*dist2wall);
			
			if(testX<0||testX>=25*25||testY<0||testY>=25*25) {
				hit=true;
				dist2wall=depth;
			}
			else {
				if(map.charAt((int)testX+25*(int)testY)=='#') {
					hit=true;
				}
			}
			
		}
		//System.out.println(dist2wall);
		return dist2wall;
	}

	public void explore(int walk,int strafe, float turn) {
		x+=walk*0.1f*Math.cos(angle);
    	y+=walk*0.1f*Math.sin(angle);
    	
    	x+=0.1f*strafe*Math.sin(angle);
    	y-=0.1f*strafe*Math.cos(angle);
    	
    	angle+=turn;
	}
	public void strafe(byte dir) {
		
	}
	public void turn(byte dir) {
		
	}
}
