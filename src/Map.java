import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;
import java.lang.Math;

public class Map {
	
	public static String mapString;
	public static float wpx,hpx;
	public static char wall = '#';
	public static char orb = 'O';
	public static float x,y;
	public static float w,h;
	
	Map(String filePath){
		StringBuilder contentBuilder = new StringBuilder();
	    try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
	    {
	        stream.forEach(s -> contentBuilder.append(s));
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    mapString = contentBuilder.toString();
	}
	
	void setMapPos(float _x, float _y) {
		x=_x;
		y=_y;
	}
	
	void setMapSize(float _w, float _h) {
		w=_h;
		h=_h;
	}
	
	String getMap() {
		return mapString;
	}
	
	void drawMap(float screenRatio) {
		wpx = w/24;
		hpx = h/24;
		
		for(byte i=0;i<25;i++) {
			for(byte j=0;j<25;j++) {
					glBegin(GL_QUADS);
					if(mapString.charAt(i+25*j)==wall) {
						glColor4f(1f,1f,1f,1f);
					}
					else {
						glColor4f(0f,0f,0f,1f);
					}
					glVertex2f(x+screenRatio*(i)*wpx,y-(j)*hpx);
					glVertex2f(x+screenRatio*(i+1)*wpx,y-(j)*hpx);
					glVertex2f(x+screenRatio*(i+1)*wpx,y-(j+1)*hpx);
					glVertex2f(x+screenRatio*(i)*wpx,y-(j+1)*hpx);
					glEnd();
				
			}
		}
		
	}
	void drawPlayerOnMap(Player player,float t,float screenRatio) {
		float posX = x + w*player.x/25*screenRatio + 0.5f*w*screenRatio;
		float posY = y - h*player.y/25 - 0.5f*h;
		float a = -player.angle;
		float[][] orient = {{(float)Math.cos(a-2.5),(float)Math.sin(a-2.5)},{(float)Math.cos(a),(float)Math.sin(a)},{(float)Math.cos(a+2.5),(float)Math.sin(a+2.5)}};
		
		glBegin(GL_TRIANGLES);
		glColor4f(1f,1f,1f,1f);
		for (byte i=0;i<3;i++) {
			glVertex2f(posX+t*screenRatio*orient[i][0],posY+t*orient[i][1]);
		}
		glEnd();
	}

}
