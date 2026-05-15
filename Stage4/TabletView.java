import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TabletView extends Group {
    private final Tablet tablet;
    private final Rectangle rect;
    private final Text label;

    public TabletView(Tablet tablet, ETNube nube) {
        this.tablet = tablet;
        double width = 20;
        double height = 28;
        rect = new Rectangle(width, height);
        rect.setFill(Color.ORANGE);
        rect.setArcWidth(4);
        rect.setArcHeight(4);
        label = new Text(tablet.getOwnerName());
        rect.xProperty().bind(tablet.xProperty().subtract(width / 2));
        rect.yProperty().bind(tablet.yProperty().subtract(height / 2));
        label.xProperty().bind(tablet.xProperty().add(width / 2 + 4));
        label.yProperty().bind(tablet.yProperty());

        Circle radar = new Circle(0);
        radar.setFill(Color.TRANSPARENT);
        radar.setStroke(Color.ORANGE);
        radar.centerXProperty().bind(tablet.xProperty());
        radar.centerYProperty().bind(tablet.yProperty());

        Timeline radarAnim = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(radar.radiusProperty(), 0),
                new KeyValue(radar.opacityProperty(), 1)),
            new KeyFrame(Duration.seconds(1),
                new KeyValue(radar.radiusProperty(), 50),
                new KeyValue(radar.opacityProperty(), 0))
        );

        Timeline trigger = new Timeline(
            new KeyFrame(Duration.seconds(5), e -> radarAnim.playFromStart())
        );
        trigger.setCycleCount(Timeline.INDEFINITE);
        trigger.play();

        ContextMenu menu = new ContextMenu();
        MenuItem findMy = new MenuItem("Find My");
        findMy.setOnAction(e -> new FindMyView(tablet.getOwnerName(), nube));
        menu.getItems().add(findMy);
        setOnMouseClicked(e -> menu.show(this, e.getScreenX(), e.getScreenY()));

        getChildren().addAll(radar, rect, label);
    }
}