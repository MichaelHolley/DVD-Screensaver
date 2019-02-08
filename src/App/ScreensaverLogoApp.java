package App;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ScreensaverLogoApp extends Application {

	public static int w;
	public static int h;
	public final double speed = 5;
	Canvas canvas;
	ImageView img;
	ColorAdjust colorAdjust = new ColorAdjust();

	@Override
	public void start(Stage primaryStage) {
		Pane canvas = new Pane();

		// get ScreenSize and set AppWindowSize to make it Fullscreen
		Screen screen = Screen.getPrimary();
		Rectangle2D screenBounds = screen.getVisualBounds();
		w = (int) screenBounds.getWidth() * 3 / 4;
		h = (int) screenBounds.getHeight();
		Scene scene = new Scene(canvas, w, h, Color.BLACK);

		// initialize DVD-Logo and place in Scene
		Random r = new Random();
		img = new ImageView(new Image("DVD_Logo.png"));
		Bounds imgBnd = img.getBoundsInLocal();
		int scaledWidth = (int) (imgBnd.getWidth() * 0.15);
		int scaledHeight = (int) (imgBnd.getHeight() * 0.15);
		img.setFitWidth(scaledWidth);
		img.setFitHeight(scaledHeight);
		img.relocate(r.nextInt(400) + 100, r.nextInt(400) + 100);
		setRandomLogoColor();
		canvas.getChildren().add(img);

		// Setting up the Stage
		primaryStage.setTitle("DVD-Screensaver");
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();

		// Using Timeline to animate the Object(text)
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {

			// object-motion speed values
			double dx = speed; // Step on x
			double dy = speed; // Step on y

			@Override
			public void handle(ActionEvent t) {
				// move the text
				img.setLayoutX(img.getLayoutX() + dx);
				img.setLayoutY(img.getLayoutY() + dy);

				Bounds bounds = canvas.getBoundsInLocal();
				Bounds imgBounds = img.getBoundsInLocal();

				if (img.getLayoutX() + imgBounds.getWidth() >= bounds.getWidth() || img.getLayoutX() <= 0) {
					dx = -dx;
					setRandomLogoColor();
				}
				if (img.getLayoutY() + imgBounds.getHeight() >= bounds.getHeight() || img.getLayoutY() <= 0) {
					dy = -dy;
					setRandomLogoColor();
				}
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

		// Escape-Key to stop App
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
				if (t.getCode() == KeyCode.ESCAPE) {
					primaryStage.close();
				}
			}
		});
	}

	public void setRandomLogoColor() {
		Random r = new Random();
		colorAdjust.setContrast(0);
		colorAdjust.setHue(r.nextDouble() * 2 - 1);
		colorAdjust.setBrightness(0);
		colorAdjust.setSaturation(1);
		img.setEffect(colorAdjust);
	}

	public static void main(String args[]) {
		launch(args);
	}

}
