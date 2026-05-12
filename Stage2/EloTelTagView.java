import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

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
        getChildren().addAll(circle, label);
    }

}
