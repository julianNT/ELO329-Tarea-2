import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TabletView extends Group {
    private final Tablet tablet;
    private final Rectangle rect;
    private final Text label;

    public TabletView(Tablet tablet) {
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
        getChildren().addAll(rect, label);
    }
}