package com.example.t2stage3v2;
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

public class CellularView extends Group {
    private final Cellular cellular;
    private final Rectangle rect;
    private final Text label;
    private  ETNube nube;
    public CellularView(Cellular cellular) {
        this.nube = cellular.nube;
        this.cellular = cellular;
        double width = 12;
        double height = 24;
        rect = new Rectangle(width, height);
        rect.setFill(Color.DODGERBLUE);
        rect.setArcWidth(4);   
        //. ¿....?
        label = new Text(cellular.getOwnerName());
        // Centrar el rectángulo en (x, y) del modelo
        rect.xProperty().bind(cellular.xProperty().subtract(width / 2));
        //¿....?


        rect.yProperty().bind(cellular.yProperty().subtract(height / 2));
        label.yProperty().bind(cellular.yProperty());
        getChildren().addAll(rect, label);

        // Ubicar la etiqueta a la derecha del rectángulo
        label.xProperty().bind(cellular.xProperty().add(width / 2 + 4));
        //¿...?
        rect.setArcHeight(4);

        rect.yProperty().bind(cellular.yProperty().subtract(height / 2));

        label.yProperty().bind(cellular.yProperty());

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

            String owner = cellular.getOwnerName();

            StringBuilder sb = new StringBuilder();

            sb.append("Bienes de ").append(owner).append("\n\n");

            sb.append("Items:\n");

            for (ETNube.Data d : nube.cloudData) {

                if (d.ownerName.equals(owner)
                        && !d.equipmentName.equals("cellular")
                        && !d.equipmentName.equals("tablet")) {

                    sb.append(" - ")
                            .append(d.equipmentName)
                            .append(": ")
                            .append(d.location.getX())
                            .append(", ")
                            .append(d.location.getY())
                            .append("\n");
                }
            }

            sb.append("\nDispositivos:\n");

            for (ETNube.Data d : nube.cloudData) {

                if (d.ownerName.equals(owner)
                        && d.equipmentName.equals("cellular")) {

                    sb.append(" - cellular: ")
                            .append(d.location.getX())
                            .append(", ")
                            .append(d.location.getY())
                            .append("\n");
                }

                if (d.ownerName.equals(owner)
                        && d.equipmentName.equals("tablet")) {

                    sb.append(" - tablet: ")
                            .append(d.location.getX())
                            .append(", ")
                            .append(d.location.getY())
                            .append("\n");
                }
            }

            Label msg = new Label(sb.toString());
            msg.setStyle("-fx-font-family: monospace;");

            root.getChildren().add(msg);

            Scene scene = new Scene(root, 300, 200);

            popup.setTitle("FindMy");
            popup.setScene(scene);
            popup.show();
        });



      //  getChildren().addAll(rect, label);
        // ¿....?
        this.setPickOnBounds(true);
    }
}