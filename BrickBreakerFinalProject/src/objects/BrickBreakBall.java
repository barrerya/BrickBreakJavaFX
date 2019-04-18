package objects;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;

public class BrickBreakBall extends Circle {
	private double dx = 5;
	private double dy = 5;
	private int directionx = 1;
	private int directiony = 1;
	private boolean canMove = false;

	private int paneWidth;
	private int paneHeight;

	// sound for bounce
	File beep = new File("./src/audio/beep.wav");
	String url = beep.toURI().toString();
	AudioClip bs = new AudioClip(url);

	public BrickBreakBall(double x, double y, double radius, int paneWidth, int paneHeight) {
		super(x, y, radius);
		this.paneHeight = paneHeight;
		this.paneWidth = paneWidth;

	}

	public void move(Paddle paddle) {
		double displacementx = this.getTranslateX();
		double displacementy = this.getTranslateY();
		if (canMove) {
			if (displacementx > paneWidth - 10 || displacementx < 0) {
				bs.play();
				directionx *= -1;
			}
			if (displacementy < 5) {
				bs.play();
				directiony *= -1;
			}
			if (displacementy > paneHeight - 10) {
				bs.play();
				this.setTranslateX(10);
				this.setTranslateY(10);
				directionx = 1;
				directiony = 1;

			}
			if (displacementy >= paneHeight - (paneHeight - paddle.getyLoc() + 5)
					&& displacementy <= paneHeight - (paneHeight - paddle.getyLoc())
					&& paddle.getTranslateX() <= displacementx
					&& paddle.getTranslateX() + paddle.getPaddleWidth() >= displacementx) {
				bs.play();
				//System.out.println(dx);

				// this segment adjusts the x movement of the ball based on where it hits the
				// paddle
				// hitting the center third of the paddle won't adjust the ball besides bouncing
				// it
				if (displacementx > paddle.getTranslateX()
						&& displacementx <= paddle.getTranslateX() + (paddle.getPaddleWidth() / 3)) {
					// System.out.println("left side");
					if (dx > 3 && dx < 7) {
						if (directionx == -1) {
							if (dx != 6) {
								dx++;
							}
						} else {
							if(dx != 4) {
								dx--;	
							}
							
						}
					}
					

				} else if (displacementx > paddle.getTranslateX() + (paddle.getPaddleWidth() / 3 * 2)
						&& displacementx <= paddle.getTranslateX() + paddle.getPaddleWidth()) {
					// System.out.println("right side");
					if (dx > 3 && dx < 7) {
						if (directionx == -1) {
							if (dx != 4) {
							dx--;
							}
						} else {
							if (dx != 6) {
							dx++;
							}
						}
					}
					
				}
				directiony = -1;
			}

			// this detects if the ball has passed the paddle
			if (displacementy > paneHeight - 20) {
				canMove = false;
				System.out.println("you lose a life");
				if (!paddle.loseLife()) {
					System.out.println("game over");
					paddle.setVisible(false);
					this.setVisible(false);
				}
			}

			// code for detecting brick collision

			this.setTranslateX(displacementx + dx * directionx);
			this.setTranslateY(displacementy + dy * directiony);
		} else {
			this.setTranslateX(this.paneWidth / 2);
			this.setTranslateY(this.paneHeight - 125);
		}
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public int getDirectionx() {
		return directionx;
	}

	public void setDirectionx(int directionx) {
		this.directionx = directionx;
	}

	public int getDirectiony() {
		return directiony;
	}

	public void setDirectiony(int directiony) {
		this.directiony = directiony;
	}

	public int getPaneWidth() {
		return paneWidth;
	}

	public void setPaneWidth(int paneWidth) {
		this.paneWidth = paneWidth;
	}

	public int getPaneHeight() {
		return paneHeight;
	}

	public void setPaneHeight(int paneHeight) {
		this.paneHeight = paneHeight;
	}

	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

}
