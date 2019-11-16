import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import java.util.Random;
import java.lang.Math;
import java.nio.DoubleBuffer;
import java.awt.Font;

public class Main {
	
	public static Random rand = new Random();

	public static int scrW = 1920;
	public static int scrH = 1080;
	public static float scrR = (float)scrH/scrW;
	
	public static float PIXEL = (float)1/scrH;
	
	public static double previousTime;
	public static int frameCount;

	public static int mapX = 25;
	public static int mapY = 25;
	
	public static Map map = new Map("res/maps/"+mapX+"x"+mapY+"/test.map");
	public static Player player = new Player();
	
	public static void Quad(float[][] v, float r, float g, float b, float a) {
		glBegin(GL_QUADS);
		glColor4f(r,g,b,a);
		glVertex2f(v[0][0],v[0][1]);
		glVertex2f(v[1][0],v[1][1]);
		glVertex2f(v[2][0],v[2][1]);
		glVertex2f(v[3][0],v[3][1]);
		glEnd();
	}
	
	public static void Strip(float[] pos, float width, float height, float r, float g, float b, float a) {
		glBegin(GL_QUADS);
		glColor4f(r,g,b,a);
		glVertex2f(pos[0]+width,pos[1]+height);
		glVertex2f(pos[0]-width,pos[1]+height);
		glVertex2f(pos[0]-width,pos[1]-height);
		glVertex2f(pos[0]+width,pos[1]-height);
		glEnd();
	}
	
	public static void PixelStrip(float[] pos, float height, float r, float g, float b, float a) {
		glBegin(GL_QUADS);
		glColor4f(r,g,b,a);
		glVertex2f(pos[0]+PIXEL,pos[1]+height);
		glVertex2f(pos[0]-PIXEL,pos[1]+height);
		glVertex2f(pos[0]-PIXEL,pos[1]-height);
		glVertex2f(pos[0]+PIXEL,pos[1]-height);
		glEnd();
	}
	
	public static void PixelStripX(float x, float height, float r, float g, float b, float a) {
		glBegin(GL_QUADS);
		glColor4f(r,g,b,a);
		glVertex2f(x+PIXEL,height);
		glVertex2f(x-PIXEL,height);
		glVertex2f(x-PIXEL,-height);
		glVertex2f(x+PIXEL,-height);
		glEnd();
	}
	
	public static void main(String[] args) {
		
		map.setMapPos(-1f, 1f);
		map.setMapSize(0.25f, 0.25f);
		
		if(!glfwInit()) {
			throw new IllegalStateException("GLFW init failed");
		}
		glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
		long window = glfwCreateWindow(scrW,scrH,"DS Engine",0,0);
		if(window==0) {
			throw new IllegalStateException("Window creation failed");
		}
		
		glfwShowWindow(window);
		glfwMakeContextCurrent(window);
		
		GL.createCapabilities();
		
		glEnable(GL_TEXTURE_2D);
		
		Texture tex = new Texture("./res/sprites/tex.png",scrR);
		
		float[][] q = {{0.15f+0.5f,0.15f+0.5f},{-0.15f+0.5f,0.15f+0.5f},{-0.15f+0.5f,-0.15f+0.5f},{0.15f+0.5f,-0.15f+0.5f}};
		float[] s = {-1.0f,0.0f};
		
		DoubleBuffer mouseX = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer mouseY = BufferUtils.createDoubleBuffer(1);
		double newX = scrW/2;
        double newY = scrH/2;

        double prevX = 0;
        double prevY = 0;
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
		
		while(!glfwWindowShouldClose(window)) {
			double currentTime = glfwGetTime();
		    frameCount++;
		    // If a second has passed.
		    if ( currentTime - previousTime >= 1.0 )
		    {
		        // Display the frame count here any way you want.
		    	System.out.println(frameCount);

		        frameCount = 0;
		        previousTime = currentTime;
		    }
		    
		    glfwGetCursorPos(window, mouseX, mouseY);
		    mouseX.rewind();
		    mouseY.rewind();

            newX = mouseX.get(0);
            newY = mouseY.get(0);

            double deltaX = newX - scrW/2;
            double deltaY = newY - scrH/2;

            prevX = newX;
            prevY = newY;

            glfwSetCursorPos(window, scrW/2, scrH/2);
		    
		    
		    player.explore(glfwGetKey(window,GLFW_KEY_W)-glfwGetKey(window,GLFW_KEY_S), glfwGetKey(window,GLFW_KEY_A)-glfwGetKey(window,GLFW_KEY_D), (float)(2*deltaX/scrW));
		      
		    if(Math.abs(player.x)>mapX/2) {
		    	player.x=player.x/Math.abs(player.x)*12f;
		    }
		    if(Math.abs(player.y)>mapY/2) {
		    	player.y=player.y/Math.abs(player.y)*12f;
		    }
		    
		    
		    
		    if(glfwGetKey(window,GLFW_KEY_ESCAPE)==GL_TRUE) {
				glfwDestroyWindow(window);
				break;
			}
			
			glfwPollEvents();
			glClear(GL_COLOR_BUFFER_BIT);
			
			float x,y;
			
			for(int i=0; i<scrW; i++) {
				x = -1f+2*PIXEL*i;
				y = (scrH/(float)player.Ray(map.getMap(), player.FOV*x));
				PixelStripX(x,0.001f*y,0.7f*y/player.depth,0.7f*y/player.depth,0.7f*y/player.depth,0f);
			}
			map.drawMap(scrR);
			map.drawPlayerOnMap(player,0.01f,scrR);
			
			tex.draw(q);
			
			glfwSwapBuffers(window);
			
		}
	
		glfwTerminate();
	}
}
