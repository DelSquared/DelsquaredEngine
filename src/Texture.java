import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {
	private int id;
	private int width;
	private int height;
	private float scrR;
	
	public Texture(String file,float ratio) {
		scrR=ratio;
		BufferedImage bi;
		try {
			bi=ImageIO.read(new File(file));
			width = bi.getWidth();
			height = bi.getHeight();
			
			int[] pixelsRaw = new int[width*height*4];
			pixelsRaw = bi.getRGB(0, 0, width, height, null, 0, width);
			
			ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*4);
			
			for(int i=0;i<width;i++) {
				for(int j=0;j<height;j++) {
					int pixel = pixelsRaw[i*width+j];
					pixels.put((byte)((pixel>>16)&0xFF));
					pixels.put((byte)((pixel>>8)&0xFF));
					pixels.put((byte)((pixel)&0xFF));
					pixels.put((byte)((pixel>>24)&0xFF));
				}
			}
			pixels.flip();
			
			id=glGenTextures();
			glBindTexture(GL_TEXTURE_2D,id);
			glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
			glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,width,height,0,GL_RGBA,GL_UNSIGNED_BYTE,pixels);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D,id);
		
	}
	
	public void draw(float[][] v) {
		bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2f(scrR*v[0][0],v[0][1]);
		glTexCoord2f(0,1);
		glVertex2f(scrR*v[1][0],v[1][1]);
		glTexCoord2f(1,1);
		glVertex2f(scrR*v[2][0],v[2][1]);
		glTexCoord2f(1,0);
		glVertex2f(scrR*v[3][0],v[3][1]);
		glEnd();
	}
	

}
