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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ScreensaverApp extends Application {

	public static int w;
	public static int h;
	Canvas canvas;

	@Override
	public void start(Stage primaryStage) {
		Pane canvas = new Pane();

		//get ScreenSize and set AppWindowSize to make it Fullscreen
		Screen screen = Screen.getPrimary();
		Rectangle2D screenBounds = screen.getVisualBounds();
		w = (int) screenBounds.getWidth();
		h = (int) screenBounds.getHeight();
		Scene scene = new Scene(canvas, w, h, Color.BLACK);

		//initialize Text-Object and place in Scene
		Random r = new Random();
		Text text = new Text();
		text.setText("I am a Placeholder");
		text.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		text.setFill(Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
		text.setTextAlignment(TextAlignment.CENTER);
		text.relocate(50, 50);
		canvas.getChildren().add(text);

		//Setting up the Stage
		primaryStage.setTitle("DVD-Screensaver");
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();

		//Using Timeline to animate the Object(text)
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {

			//object-motion speed values
			double dx = r.nextInt(3) + 1; // Step on x
			double dy = r.nextInt(3) + 1; // Step on y

			@Override
			public void handle(ActionEvent t) {
				// move the text
				text.setLayoutX(text.getLayoutX() + dx);
				text.setLayoutY(text.getLayoutY() + dy);

				Bounds bounds = canvas.getBoundsInLocal();
				Bounds tBounds = text.getBoundsInLocal();

				// If the text reaches the left or right border make the step negative and change its color
				if (text.getLayoutX() <= (bounds.getMinX() + tBounds.getMinX())
						|| text.getLayoutX() >= (bounds.getMaxX() - tBounds.getMaxX())) {
					dx = -dx;
					text.setFill(Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
				}

				// If the text reaches the bottom or top border make the step negative and change its color
				if ((text.getLayoutY() >= (bounds.getMaxY() - tBounds.getMaxY()))
						|| (text.getLayoutY() <= (bounds.getMinY() - tBounds.getMinY()))) {
					dy = -dy;
					text.setFill(Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
				}
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

		//Escape-Key to stop App
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
				if (t.getCode() == KeyCode.ESCAPE) {
					primaryStage.close();
				}
			}
		});
	}

	public static void main(String args[]) {
		launch(args);
	}

}
