import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class EloTelTagView extends Group {
    private final EloTelTag tag;
    private final Circle circle;
    private final Text label;

    public EloTelTagView(EloTelTag tag) {
        this.tag = tag;
        double radius = 6;
        circle = new Circle(radius);
        circle.setFill(Color.GREEN);
        circle.centerXProperty().bind(tag.xProperty());
        circle.centerYProperty().bind(tag.yProperty());
        label = new Text(tag.getName());
        label.xProperty().bind(tag.xProperty().add(radius + 4));
        label.yProperty().bind(tag.yProperty());

        Circle radar = new Circle(0);
        radar.setFill(Color.TRANSPARENT);
        radar.setStroke(Color.GREEN);
        radar.centerXProperty().bind(tag.xProperty());
        radar.centerYProperty().bind(tag.yProperty());

        Timeline radarAnim = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(radar.radiusProperty(), 0),
                new KeyValue(radar.opacityProperty(), 1)),
            new KeyFrame(Duration.seconds(1),
                new KeyValue(radar.radiusProperty(), 50),
                new KeyValue(radar.opacityProperty(), 0))
        );

        Timeline trigger = new Timeline(
            new KeyFrame(Duration.seconds(4), e -> radarAnim.playFromStart())
        );
        trigger.setCycleCount(Timeline.INDEFINITE);
        trigger.play();

        getChildren().addAll(radar, circle, label);
    }
}
