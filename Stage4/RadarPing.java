import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.scene.Group;

public class RadarPing extends Group {

    public RadarPing(double x, double y, double maxRadius) {

        Circle circle = new Circle(0);
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setStroke(Color.LIMEGREEN);
        circle.setFill(Color.TRANSPARENT);
        circle.setStrokeWidth(2);

        getChildren().add(circle);

        ScaleTransition st = new ScaleTransition(Duration.millis(20000), circle);
        st.setFromX(0);
        st.setFromY(0);
        st.setToX(maxRadius);
        st.setToY(maxRadius);

        FadeTransition ft = new FadeTransition(Duration.millis(20000), circle);
        ft.setFromValue(1);
        ft.setToValue(0);

        st.play();
        ft.play();

        ft.setOnFinished(e -> ((Group) getParent()).getChildren().remove(this));
    }
}
