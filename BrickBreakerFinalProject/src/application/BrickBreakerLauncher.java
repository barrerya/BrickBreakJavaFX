package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.BrickBreakBall;
import objects.Paddle;
import objects.Brick;

/**
 * Run this program to start the game
 * 
 * @author Ryan
 *
 */
public class BrickBreakerLauncher extends Application {
	static int paneHeight = 600;
	static int paneWidth = 400;
	boolean moveLeft, moveRight, gameState = false;
	int startingLives = 3, currentLvl = 0;
	int score = 0;

	// game objects
	ArrayList<Brick> lvlBricks = new ArrayList<Brick>();
	BrickBreakBall ball = new BrickBreakBall(5, 5, 10, paneWidth, paneHeight);
	Paddle paddle = new Paddle(100, 20, paneWidth, paneHeight, startingLives);
	Pane gamePlatform = new Pane();

	// audio effect stuff
	// background music
	File song = new File("./src/audio/bensound-scifi.mp3");
	String url = song.toURI().toString();
	Media backgroundMusic = new Media(url);
	MediaPlayer bm = new MediaPlayer(backgroundMusic);

	// sound for bounce
	File beep = new File("./src/audio/beep.wav");
	String beepurl = beep.toURI().toString();
	AudioClip bs = new AudioClip(beepurl);

	// sound for brick break
	File shatter = new File("./src/audio/glassBreak.wav");
	String shatterurl = shatter.toURI().toString();
	AudioClip bbs = new AudioClip(shatterurl);

	// need to be global for code to work
	Text scoreText = new Text(3, paneHeight - 22, "Score: ");
	Text lifeText = new Text(paneWidth - 50, paneHeight - 22, "Lives: ");

	// causes various actions to occur 60 times per second
	Timeline tline = new Timeline(new KeyFrame(Duration.millis(16.66), e -> {
		timeLineChecks();
	}));

	Stage stage = new Stage();
	Stage backStage = new Stage();

	@Override
	public void start(Stage stage1) throws Exception {
		stage1.setTitle("Ryan's Brick Breaker");
		stage = stage1;
		backStage = stage1;
		
		openMenu();
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This is where the levels are programmed
	 * 
	 * @param lvlNum This input is used to determine what level to load
	 * @return Returns the completed level design
	 */
	public ArrayList<Brick> startLevel(int lvlNum) {
		int brickWidth = paneWidth / 8;// number of bricks you want per row
		int brickHeight = paneHeight / 3 * 2 / 12;// the /3*2 keeps the bricks in the top two thirds of the pane and the
													// value under it determines how many can fit in a column

		// colors used ["0000ff", "00ff00", "ffff00", "ff8000", "ff0000"]
		lvlBricks.clear();

		// level 1
		if (lvlNum == 1) {
			// row 1
			lvlBricks.add(new Brick(0, 0, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth, 0, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 2, 0, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 3, 0, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 4, 0, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 5, 0, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 6, 0, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 7, 0, brickWidth, brickHeight, 150));
			// row 2
			lvlBricks.add(new Brick(0, brickHeight, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth, brickHeight, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight, brickWidth, brickHeight, 100));
			// row 3
			lvlBricks.add(new Brick(0, brickHeight * 2, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 2, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 2, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 2, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 2, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 2, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 2, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 2, brickWidth, brickHeight, 40));
			// row 4
			lvlBricks.add(new Brick(0, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 3, brickWidth, brickHeight, 20));
			// row 5
			lvlBricks.add(new Brick(0, brickHeight * 4, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 4, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 4, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 4, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 4, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 4, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 4, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 4, brickWidth, brickHeight, 10));
		} else if (lvlNum == 2) {// Level 2
			// row 1
			lvlBricks.add(new Brick(0, 0, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth, 0, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 2, 0, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 3, 0, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 4, 0, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 5, 0, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 6, 0, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 7, 0, brickWidth, brickHeight, 150));
			// row 2
			lvlBricks.add(new Brick(0, brickHeight, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth, brickHeight, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight, brickWidth, brickHeight, 150));
			// row 3
			lvlBricks.add(new Brick(0, brickHeight * 2, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 2, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 2, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 2, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 2, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 2, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 2, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 2, brickWidth, brickHeight, 100));
			// row 4
			lvlBricks.add(new Brick(0, brickHeight * 3, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 3, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 3, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 3, brickWidth, brickHeight, 40));
			// row 5
			lvlBricks.add(new Brick(0, brickHeight * 4, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 4, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 4, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 4, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 4, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 4, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 4, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 4, brickWidth, brickHeight, 20));
			// row 6
			lvlBricks.add(new Brick(0, brickHeight * 5, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 5, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 5, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 5, brickWidth, brickHeight, 10));
		} else if (lvlNum == 3) {// Level 3
			// row 1
			lvlBricks.add(new Brick(0, 0, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth, 0, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 2, 0, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 3, 0, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 4, 0, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 5, 0, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 6, 0, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 7, 0, brickWidth, brickHeight, 150));
			// row 2
			lvlBricks.add(new Brick(0, brickHeight, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth, brickHeight, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight, brickWidth, brickHeight, 150));
			// row 3
			lvlBricks.add(new Brick(0, brickHeight * 2, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 2, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 2, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 2, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 2, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 2, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 2, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 2, brickWidth, brickHeight, 150));
			// row 4
			lvlBricks.add(new Brick(0, brickHeight * 3, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 3, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 3, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 3, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 3, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 3, brickWidth, brickHeight, 150));
			// row 5
			lvlBricks.add(new Brick(0, brickHeight * 4, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 4, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 4, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 4, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 4, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 4, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 4, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 4, brickWidth, brickHeight, 150));
			// row 5
			lvlBricks.add(new Brick(0, brickHeight * 5, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 5, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 5, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 5, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 5, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 5, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 5, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 5, brickWidth, brickHeight, 150));
			// row 6
			lvlBricks.add(new Brick(0, brickHeight * 6, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 6, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 6, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 6, brickWidth, brickHeight, 100));
			// row 7
			lvlBricks.add(new Brick(0, brickHeight * 7, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 7, brickWidth, brickHeight, 40));

		} else if (lvlNum == 4) {// Level 4
			// row 1
			lvlBricks.add(new Brick(brickWidth * 2, 0, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 3, 0, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 4, 0, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 5, 0, brickWidth, brickHeight, 10));
			// row 2
			lvlBricks.add(new Brick(brickWidth, brickHeight, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight, brickWidth, brickHeight, 10));
			// row 3
			lvlBricks.add(new Brick(0, brickHeight * 2, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 2, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 2, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 2, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 2, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 2, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 2, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 2, brickWidth, brickHeight, 10));
			// row 4
			lvlBricks.add(new Brick(0, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 3, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 3, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 3, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 3, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 3, brickWidth, brickHeight, 20));
			// row 5
			lvlBricks.add(new Brick(0, brickHeight * 4, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 4, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 4, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 4, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 4, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 4, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 4, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 4, brickWidth, brickHeight, 20));
			// row 6
			lvlBricks.add(new Brick(0, brickHeight * 5, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 5, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 5, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 5, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 5, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 5, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 5, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 5, brickWidth, brickHeight, 20));
			// row 7
			lvlBricks.add(new Brick(0, brickHeight * 6, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 6, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 6, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 6, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 6, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 6, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 6, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 6, brickWidth, brickHeight, 20));
			// row 8
			lvlBricks.add(new Brick(0, brickHeight * 7, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 7, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 7, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 7, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 7, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 7, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 7, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 7, brickWidth, brickHeight, 10));
			// row 9
			lvlBricks.add(new Brick(brickWidth, brickHeight * 8, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 8, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 8, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 8, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 8, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 8, brickWidth, brickHeight, 10));
			// row 10
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 9, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 9, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 9, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 9, brickWidth, brickHeight, 10));
		} else if (lvlNum > 4) {// level 5 and beyond
			// row 1
			lvlBricks.add(new Brick(0, 0, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth, 0, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 2, 0, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 3, 0, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 4, 0, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 5, 0, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 6, 0, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 7, 0, brickWidth, brickHeight, 150));
			// row 2
			lvlBricks.add(new Brick(0, brickHeight, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth, brickHeight, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight, brickWidth, brickHeight, 100));
			// row 3
			lvlBricks.add(new Brick(0, brickHeight * 2, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 2, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 2, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 2, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 2, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 2, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 2, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 2, brickWidth, brickHeight, 40));
			// row 4
			lvlBricks.add(new Brick(0, brickHeight * 3, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 3, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 3, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 3, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 3, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 3, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 3, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 3, brickWidth, brickHeight, 20));
			// row 5
			lvlBricks.add(new Brick(0, brickHeight * 4, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 4, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 4, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 4, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 4, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 4, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 4, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 4, brickWidth, brickHeight, 20));
			// row 6
			lvlBricks.add(new Brick(0, brickHeight * 5, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 5, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 5, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 5, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 5, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 5, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 5, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 5, brickWidth, brickHeight, 40));
			// row 7
			lvlBricks.add(new Brick(0, brickHeight * 6, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 6, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 6, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 6, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 6, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 6, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 6, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 6, brickWidth, brickHeight, 100));
			// row 8
			lvlBricks.add(new Brick(0, brickHeight * 7, brickWidth, brickHeight, 150));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 7, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 7, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 7, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 7, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 7, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 7, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 7, brickWidth, brickHeight, 150));
			// row 9
			lvlBricks.add(new Brick(0, brickHeight * 8, brickWidth, brickHeight, 100));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 8, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 8, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 3, brickHeight * 8, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 4, brickHeight * 8, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 8, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 8, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 8, brickWidth, brickHeight, 100));
			// row 10
			lvlBricks.add(new Brick(0, brickHeight * 9, brickWidth, brickHeight, 40));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 9, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 2, brickHeight * 9, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 5, brickHeight * 9, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 9, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 9, brickWidth, brickHeight, 40));
			// row 11
			lvlBricks.add(new Brick(0, brickHeight * 10, brickWidth, brickHeight, 20));
			lvlBricks.add(new Brick(brickWidth, brickHeight * 10, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 6, brickHeight * 10, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 10, brickWidth, brickHeight, 20));
			// row 12
			lvlBricks.add(new Brick(0, brickHeight * 11, brickWidth, brickHeight, 10));
			lvlBricks.add(new Brick(brickWidth * 7, brickHeight * 11, brickWidth, brickHeight, 10));

		}

		ArrayList<Brick> bricks = lvlBricks;
		return bricks;

	}

	public void checkBrickCollision() {
		Point2D ballLoc = new Point2D(ball.getTranslateX(), ball.getTranslateY());
		for (int x = 0; x < lvlBricks.size(); x++) {
			if (lvlBricks.get(x).contains(ballLoc)) {
				bbs.play();
				score = score + lvlBricks.get(x).getPointVal();

				if (ball.getTranslateX() >= lvlBricks.get(x).getX()
						&& ball.getTranslateX() <= lvlBricks.get(x).getX() + 7
						|| ball.getTranslateX() <= lvlBricks.get(x).getX() + lvlBricks.get(x).getWidth()
								&& ball.getTranslateX() >= lvlBricks.get(x).getX() + lvlBricks.get(x).getWidth() - 7) {
					ball.setDirectionx(ball.getDirectionx() * -1);
				}
				if (ball.getTranslateY() >= lvlBricks.get(x).getY()
						&& ball.getTranslateY() <= lvlBricks.get(x).getY() + 7
						|| ball.getTranslateY() <= lvlBricks.get(x).getY() + lvlBricks.get(x).getHeight()
								&& ball.getTranslateY() >= lvlBricks.get(x).getY() + lvlBricks.get(x).getHeight() - 7) {
					ball.setDirectiony(ball.getDirectiony() * -1);
				}
				lvlBricks.get(x).setVisible(false);
				lvlBricks.remove(x);
			}
		}
	}

	public void startGame() {

		paddle.setLives(startingLives);
		score = 0;
		currentLvl = 0;
		tline.setCycleCount(Timeline.INDEFINITE);
		tline.play();

		// plays the music
		bm.setCycleCount(Timeline.INDEFINITE);
		bm.setVolume(0.25);
		bm.play();

		// reduce the shatter volume
		bbs.setVolume(0.1);

		Rectangle infoBar = new Rectangle(0, paneHeight - 50, paneWidth, 50);
		infoBar.setFill(Color.GREY);
		infoBar.setStroke(Color.BLACK);
		infoBar.setStrokeWidth(2);

		gamePlatform.getChildren().addAll(ball, paddle, infoBar, scoreText, lifeText);

		Scene sceneGP = new Scene(gamePlatform, paneWidth, paneHeight);

		// detects key presses
		sceneGP.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			if (key.getCode() == KeyCode.ENTER) {
				System.out.println("You pressed enter");
			} else if (key.getCode() == KeyCode.LEFT) {
				moveLeft = true;
			} else if (key.getCode() == KeyCode.RIGHT) {
				moveRight = true;
			} else if (key.getCode() == KeyCode.SPACE) {
				if (!ball.isCanMove()) {
					ball.setCanMove(true);
				}
			}
		});

		// detects various key releases
		sceneGP.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
			if (key.getCode() == KeyCode.ENTER) {
				System.out.println("You pressed enter");
			} else if (key.getCode() == KeyCode.LEFT) {
				moveLeft = false;
			} else if (key.getCode() == KeyCode.RIGHT) {
				moveRight = false;
			}
		});
		stage.setScene(sceneGP);
	}

	public void openHSMenu() {
		GridPane gp = new GridPane();
		gp.setHgap(25);
		gp.setVgap(25);

		Button menu = new Button("Return to Menu");
		menu.setId("glass-grey");
		menu.getStylesheets().add("css/buttonStyles.css");
		menu.setOnAction(e -> {
			openMenu();
		});
		gp.add(menu, 1, 1);

		File file = new File("./src/application/highscores.txt");
		String fileOut = "";
		int y = 0;
		try {
			Scanner read = new Scanner(file);

			fileOut = read.nextLine();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("This is not a valid file");
		}
		String[] fileOutProc = fileOut.split(":");
		String[] name = new String[fileOutProc.length / 2];
		String[] score = new String[fileOutProc.length / 2];

		for (int x = 0; x < fileOutProc.length; x++) {
			if (x % 2 == 0) {
				name[x / 2] = fileOutProc[x];
			} else {
				score[y] = fileOutProc[x];
				y++;
			}

		}

		ArrayList<Text> row = new ArrayList<Text>();

		for (int x = 0; x < fileOutProc.length / 2; x++) {
			// System.out.println(name[x] + score[x]);
			row.add(new Text(x + 1 + ". " + name[x] + " Score: " + score[x]));
			gp.add(row.get(x), 1, x + 2);
		}
		Scene scene = new Scene(gp, paneWidth, paneHeight);
		stage.setScene(scene);

	}

	public void openMenu() {

		Pane menuPane = new Pane();
		Button playButton = new Button("Play");
		playButton.setPrefSize(150, 50);
		playButton.setLayoutX(paneWidth / 2 - 75);
		playButton.setLayoutY(paneHeight / 2 - 25);
		playButton.setId("round-red");
		playButton.getStylesheets().add("css/buttonStyles.css");
		Button hSButton = new Button("Highscores");
		hSButton.setPrefSize(150, 50);
		hSButton.setLayoutX(paneWidth / 2 - 75);
		hSButton.setLayoutY(paneHeight / 2 + 25);
		hSButton.setId("shiny-orange");
		hSButton.getStylesheets().add("css/buttonStyles.css");
		Button ruleButton = new Button("Instructions");
		ruleButton.setPrefSize(150, 50);
		ruleButton.setLayoutX(paneWidth / 2 - 75);
		ruleButton.setLayoutY(paneHeight / 2 + 75);
		ruleButton.setId("shiny-orange");
		ruleButton.getStylesheets().add("css/buttonStyles.css");
		Button exitButton = new Button("Exit");
		exitButton.setPrefSize(150, 50);
		exitButton.setLayoutX(paneWidth / 2 - 75);
		exitButton.setLayoutY(paneHeight / 2 + 125);
		exitButton.setId("glass-grey");
		exitButton.getStylesheets().add("css/buttonStyles.css");

		// give buttons their functions
		playButton.setOnAction(e -> {
			startGame();
		});
		hSButton.setOnAction(e -> {
			openHSMenu();
		});
		ruleButton.setOnAction(e -> {
			openRulePage();
		});
		exitButton.setOnAction(e -> {
			System.exit(0);
		});

		menuPane.getStylesheets().add("css/menuBack.css");
		menuPane.getChildren().addAll(playButton, hSButton, exitButton, ruleButton);
		Scene menuScene = new Scene(menuPane, paneWidth, paneHeight);
		stage.setScene(menuScene);
	}

	public void addHighScore(int score) {
		tline.pause();
		Pane pane = new Pane();

		Text nameInText = new Text("Please enter your name for your score of: " + score);
		nameInText.setFill(Color.WHITE);
		nameInText.setX(paneWidth / 2 - 150);
		nameInText.setLayoutY(paneHeight / 2 - 50);

		TextField nameInput = new TextField();
		nameInput.setPrefSize(150, 50);
		nameInput.setLayoutX(paneWidth / 2 - 75);
		nameInput.setLayoutY(paneHeight / 2 - 25);

		Button submitButton = new Button("Submit");
		submitButton.setPrefSize(150, 50);
		submitButton.setLayoutX(paneWidth / 2 - 75);
		submitButton.setLayoutY(paneHeight / 2 + 25);
		submitButton.setId("glass-grey");
		submitButton.getStylesheets().add("css/buttonStyles.css");

		submitButton.setOnAction(e -> {
			String name = nameInput.getText();
			System.out.println(name);
			try {
				updateHSFile(name, score);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			  
			System.exit(0);
		});

		pane.getStylesheets().add("css/menuBack.css");
		pane.getChildren().addAll(nameInput, nameInText, submitButton);
		Scene scene = new Scene(pane, paneWidth, paneHeight);
		stage.setScene(scene);

	}

	public void updateHSFile(String newName, int newScore) throws IOException {
		// code to get data
		File file = new File("./src/application/highscores.txt");
		String fileOut = "";
		int y = 0;
		try {
			Scanner read = new Scanner(file);

			fileOut = read.nextLine();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("This is not a valid file");
		}
		String[] fileOutProc = fileOut.split(":");
		String[] name = new String[fileOutProc.length / 2];
		String[] score = new String[fileOutProc.length / 2];

		for (int x = 0; x < fileOutProc.length; x++) {
			if (x % 2 == 0) {

				name[x / 2] = fileOutProc[x];
			} else {

				score[y] = fileOutProc[x];
				y++;
			}

		}

		// sorts the high scores
		if (newScore > Integer.parseInt(score[9])) {
			int pos = 0;
			for (int x = 9; newScore > Integer.parseInt(score[x]); x--) {
				pos = x;
			}
			for (int x = 9; x > pos; x--) {
				name[x] = name[x - 1];
				score[x] = score[x - 1];
			}
			name[pos] = newName;
			score[pos] = Integer.toString(newScore);

			for (int x = 0; x < 10; x++) {
				System.out.println(name[x] + score[x]);
			}
		}

		try {
			FileWriter fWriter = new FileWriter(file, false);
			
			fWriter.write(name[0] + ":" + score[0] + ":" + name[1] + ":" + score[1] + ":" + name[2] + ":" + score[2]
					+ ":" + name[3] + ":" + score[3] + ":" + name[4] + ":" + score[4] + ":" + name[5] + ":" + score[5]
					+ ":" + name[6] + ":" + score[6] + ":" + name[7] + ":" + score[7] + ":" + name[8] + ":" + score[8]
					+ ":" + name[9] + ":" + score[9]);
			fWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void timeLineChecks() {
		if (lvlBricks.isEmpty()) {
			gamePlatform.getChildren().addAll(startLevel(++currentLvl));
			ball.setCanMove(false);
		}
		ball.move(paddle);
		if (moveLeft) {
			paddle.moveLeft(paddle);
		} else if (moveRight) {
			paddle.moveRight(paddle);
		}

		checkBrickCollision();
		scoreText.setText("Score: " + score);
		lifeText.setText("Lives: " + paddle.getLives());
		// does stuff when the player has no lives remaining
		if (paddle.getLives() == 0) {
			bm.stop();
			addHighScore(score);

		}
	}

	public void openRulePage() {
		GridPane gp = new GridPane();
		gp.setHgap(25);
		gp.setVgap(25);

		Button menu = new Button("Return to Menu");
		menu.setId("glass-grey");
		menu.getStylesheets().add("css/buttonStyles.css");
		menu.setOnAction(e -> {
			openMenu();
		});
		gp.add(menu, 1, 1);

		Text introText = new Text("The goal of the game is to bounce the ball back up with the paddle."
				+ " When the ball colides with a brick that brick is broken and you score points."
				+ " If the ball passes your paddle you lose a life." + " When you lose all three lives you lose."
				+ " Levels will change as you progress, up to level 6 where it repeats."
				+ " Use the left and right arrows to move the paddle and space to start the ball moving after it has stopped."
				+ " Blue bricks are worth 10 points, green are worth 20, yellow are worth 40, orange are worth 100 and red are worth 150.");
		introText.setWrappingWidth(300);
		gp.add(introText, 1, 2);

		Scene sceneGP = new Scene(gp, paneWidth, paneHeight);
		stage.setScene(sceneGP);

	}
}
