import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TabletView extends Group {

    private final Tablet tablet;
    private final Rectangle rect;
    private final Text label;

    public TabletView(Tablet tablet, ETNube nube) {
        this.tablet = tablet;

        double width = 18;
        double height = 12;

        rect = new Rectangle(width, height);
        rect.setFill(Color.DARKORANGE);

        label = new Text(tablet.getOwnerName() + " Tablet");

        rect.xProperty().bind(tablet.xProperty().subtract(width / 2));
        rect.yProperty().bind(tablet.yProperty().subtract(height / 2));

        label.xProperty().bind(tablet.xProperty().add(width / 2 + 4));
        label.yProperty().bind(tablet.yProperty());

        getChildren().addAll(rect, label);

        ContextMenu menu = new ContextMenu();
        rect.setMouseTransparent(true);
        label.setMouseTransparent(true);
        MenuItem findMy = new MenuItem("FindMy");
        menu.getItems().add(findMy);

        this.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                menu.show(this, e.getScreenX(), e.getScreenY());
            }
        });

        findMy.setOnAction(e -> {
            Stage popup = new Stage();
            VBox root = new VBox();
            root.setStyle("-fx-padding: 20; -fx-alignment: center-left;");
            String owner = tablet.getOwnerName();

            Label msg = new Label();
            msg.setStyle("-fx-font-family: monospace;");
            root.getChildren().add(msg);

            Runnable refresh = () -> {
                StringBuilder sb = new StringBuilder();
                sb.append("Bienes de ").append(owner).append("\n\n");
                sb.append("Items:\n");
                for (ETNube.Data d : nube.cloudData) {
                    if (d.ownerName.equals(owner)
                            && !d.equipmentName.equals("cellular")
                            && !d.equipmentName.equals("tablet")) {
                        sb.append(" - ").append(d.equipmentName)
                                .append(": ").append(d.location.getX())
                                .append(", ").append(d.location.getY()).append("\n");
                    }
                }
                sb.append("\nDispositivos:\n");
                for (ETNube.Data d : nube.cloudData) {
                    if (d.ownerName.equals(owner) && d.equipmentName.equals("cellular")) {
                        sb.append(" - cellular: ").append(d.location.getX())
                                .append(", ").append(d.location.getY()).append("\n");
                    }
                    if (d.ownerName.equals(owner) && d.equipmentName.equals("tablet")) {
                        sb.append(" - tablet: ").append(d.location.getX())
                                .append(", ").append(d.location.getY()).append("\n");
                    }
                }
                msg.setText(sb.toString());
            };

            refresh.run();
            Timeline updateTimeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> refresh.run()));
            updateTimeline.setCycleCount(Timeline.INDEFINITE);
            updateTimeline.play();
            popup.setOnHidden(ev -> updateTimeline.stop());

            Scene scene = new Scene(root, 300, 200);
            popup.setTitle("FindMy");
            popup.setScene(scene);
            popup.show();
        });

        this.setPickOnBounds(true);
    }
}