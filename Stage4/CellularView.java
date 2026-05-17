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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Circle;
import java.io.File;

public class CellularView extends Group {
    private final Cellular cellular;
    private final Rectangle rect;
    private final Text label;
    private ETNube nube;

    public CellularView(Cellular cellular) {
        this.nube = cellular.nube;
        this.cellular = cellular;
        double width = 12;
        double height = 24;
        rect = new Rectangle(width, height);
        rect.setFill(Color.DODGERBLUE);
        rect.setArcWidth(4);
        label = new Text(cellular.getOwnerName());
        rect.xProperty().bind(cellular.xProperty().subtract(width / 2));
        rect.yProperty().bind(cellular.yProperty().subtract(height / 2));
        label.yProperty().bind(cellular.yProperty());
        label.xProperty().bind(cellular.xProperty().add(width / 2 + 4));
        rect.setArcHeight(4);
        rect.yProperty().bind(cellular.yProperty().subtract(height / 2));
        label.yProperty().bind(cellular.yProperty());
        getChildren().addAll(rect, label);

        ContextMenu menu = new ContextMenu();
        rect.setMouseTransparent(true);
        label.setMouseTransparent(true);
        MenuItem findMy = new MenuItem("FindMy");
	MenuItem gFindMy = new MenuItem("GFindMy");
        menu.getItems().addAll(findMy, gFindMy);

        this.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                menu.show(this, e.getScreenX(), e.getScreenY());
            }
        });

        findMy.setOnAction(e -> {
            Stage popup = new Stage();
            VBox root = new VBox();
            root.setStyle("-fx-padding: 20; -fx-alignment: center-left;");
            String owner = cellular.getOwnerName();

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
	gFindMy.setOnAction(e -> {
            Stage gPopup = new Stage();
            gPopup.setTitle("GFindMy: " + cellular.getOwnerName());
            
            // Cargar la imagen de fondo de la simulación de forma estática
            Image bgImage = new Image(new File("Placeres.jpg").toURI().toString());
            ImageView bgView = new ImageView(bgImage);
            
            // Capa para renderizar los dispositivos de forma dinámica
            Pane layerEquipos = new Pane();
            Pane rootPane = new Pane(bgView, layerEquipos);

            // Tarea síncrona de refresco gráfico
            Runnable gRefresh = () -> {
                layerEquipos.getChildren().clear(); // Limpiar solo los iconos viejos
                String owner = cellular.getOwnerName();
                
                // Buscar en la base de datos centralizada de la nube
                for (ETNube.Data d : nube.cloudData) {
                    if (d.ownerName.equals(owner)) {
                        Group icon = new Group();
                        
                        if (d.equipmentName.equals("cellular")) {
                            Rectangle cRect = new Rectangle(12, 24, Color.DODGERBLUE);
                            cRect.setX(d.location.getX() - 6);
                            cRect.setY(d.location.getY() - 12);
                            icon.getChildren().add(cRect);
                            
                        } else if (d.equipmentName.equals("tablet")) {
                            Rectangle tRect = new Rectangle(16, 26, Color.ORANGE);
                            tRect.setX(d.location.getX() - 8);
                            tRect.setY(d.location.getY() - 13);
                            icon.getChildren().add(tRect);
                            
                        } else {
                            // Representación para los EloTelTags de este dueño
                            Circle tagCircle = new Circle(d.location.getX(), d.location.getY(), 6, Color.LIMEGREEN);
                            Text tagLabel = new Text(d.location.getX() + 10, d.location.getY(), d.equipmentName);
                            icon.getChildren().addAll(tagCircle, tagLabel);
                        }
                        layerEquipos.getChildren().add(icon);
                    }
                }
            };

            // Ejecución inicial inmediata antes del bucle periódico
            gRefresh.run();
            
            // Configurar Timeline para actualizar la ventana cada 1 segundo
            Timeline gTimeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> gRefresh.run()));
            gTimeline.setCycleCount(Timeline.INDEFINITE);
            gTimeline.play();
            
            // Detener el reloj al cerrar la ventana emergente para evitar Memory Leaks
            gPopup.setOnHidden(ev -> gTimeline.stop());

            ScrollPane scroll = new ScrollPane(rootPane);
            Scene gScene = new Scene(scroll, 600, 400);
            gPopup.setScene(gScene);
            gPopup.show();
        });

        this.setPickOnBounds(true);
    }
}
