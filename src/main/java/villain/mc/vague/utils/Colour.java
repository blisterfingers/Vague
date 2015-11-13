package villain.mc.vague.utils;

public class Colour {
	
	private float r = 1f;
	private float g = 1f;
	private float b = 1f;
	private float a = 1f;
	
	public Colour(){
		
	}
	
	public Colour(float r, float g, float b, float a){
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Colour(int argb8888){
		a = ((argb8888 & 0xFF000000) >>> 24) / 255f;
		r = ((argb8888 & 0x00FF0000) >>> 16) / 255f;
		g = ((argb8888 & 0x0000FF00) >>> 8) / 255f;
		b = (argb8888 & 0x000000FF) / 255f;
	}
	
	public Colour copy(){
		return new Colour(r, g, b, a);
	}
	
	@Override
	public String toString() {
		return "{ r: " + r + ", g: " + g + ", b: " + b + ", a: " + a + "}";
	}
				
	public float getR(){
		return r;
	}
	
	public float getG(){
		return g;
	}
	
	public float getB(){
		return b;
	}
	
	public float getA(){
		return a;
	}
}