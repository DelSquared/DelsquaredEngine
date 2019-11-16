
public class UIElement {
	
	Texture[] texture;
	boolean clickable;
	boolean mouseOver;
	float x,y;
		
	
	UIElement(float _x, float _y, boolean click, boolean mouse, String texturefiles[],float ratio){
		x=_x;
		y=_y;
		clickable = click;
		mouseOver = mouse;
		texture = new Texture[texturefiles.length];
		for (int i=0;i<texturefiles.length;i++) {
			texture[i] = new Texture(texturefiles[i],ratio);
		}
		
		
	}
	
	public void Draw() {
		
	}

}
