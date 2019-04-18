package objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Brick extends Rectangle{
	private int pointVal;
	
	public Brick(int x, int y, double width, double height, int pointVal) {
		super(x,y,width,height);
		this.pointVal = pointVal;
		this.setStrokeWidth(2);
		this.setStroke(Color.BLACK);
		if(pointVal>0 && pointVal <= 10) {
			this.setFill(Color.BLUE);
		}else if(pointVal>10 && pointVal <= 25) {
			this.setFill(Color.GREEN);
		}else if(pointVal>25 && pointVal <= 50) {
			this.setFill(Color.YELLOW);
		}else if(pointVal>50 && pointVal <= 100){
			this.setFill(Color.ORANGE);
		}else if(pointVal>100) {
			this.setFill(Color.RED);
		}
		
	}
	public int getPointVal() {
		return pointVal;
	}
	public void setPointVal(int pointVal) {
		this.pointVal = pointVal;
	}

	
}
