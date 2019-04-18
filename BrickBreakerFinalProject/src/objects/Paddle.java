package objects;

import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {
	private double paddleWidth;
	private double paddleHeight;
	private int lives;
	private double xLoc;
	private double yLoc;
	private int paneWidth;
	private int paneHeight;
	private int score;

	public Paddle(double width, double height, int paneWidth, int paneHeight, int lives) {
		super(0, paneHeight - 100, width, height);
		this.yLoc = paneHeight - 100;
		this.xLoc = 0;
		this.paddleWidth = width;
		this.paddleHeight = height;
		this.paneWidth = paneWidth;
		this.paneHeight = paneHeight;
		this.lives = lives;
		this.score = 0;

	}

	public void moveLeft(Paddle paddle) {
		double displacementx = paddle.getTranslateX();
		int dx;
		if (displacementx > 0) {
			dx = -5;
		}else {
			dx = 0;
		}
		
		paddle.setTranslateX(displacementx + dx);
	}

	public void moveRight(Paddle paddle) {
		double displacementx = paddle.getTranslateX();
		int dx;
		if (displacementx < paneWidth-paddleWidth) {
			dx = 5;
		}else {
			dx = 0;
		}
		
		paddle.setTranslateX(displacementx + dx);
	}
	
	/**
	 * 
	 * @return returns true if there are lives remaining and false if there are none left
	 */
	public boolean loseLife() {
		lives--;
		if (lives > 0) {
			return true;
		}else {
			return false;
		}
	}
	public void addScore(int points) {
		this.score = this.score + points;
	}

	public double getPaddleWidth() {
		return paddleWidth;
	}

	public void setPaddleWidth(double paddleWidth) {
		this.paddleWidth = paddleWidth;
	}

	public double getPaddleHeight() {
		return paddleHeight;
	}

	public void setPaddleHeight(double paddleHeight) {
		this.paddleHeight = paddleHeight;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public double getxLoc() {
		return xLoc;
	}

	public void setxLoc(double xLoc) {
		this.xLoc = xLoc;
	}
	

	public double getyLoc() {
		return yLoc;
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

	public int getScore() {
		return score;
	}
	
	

}
