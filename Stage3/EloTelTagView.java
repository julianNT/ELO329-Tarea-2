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

        circle = new Circle(6);
        circle.setFill(Color.LIMEGREEN);
        circle.centerXProperty().bind(tag.xProperty());
        circle.centerYProperty().bind(tag.yProperty());

        label = new Text(tag.getName());
        label.xProperty().bind(tag.xProperty().add(10));
        label.yProperty().bind(tag.yProperty());

        getChildren().addAll(circle, label);
    }
}
