import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class FindMyView extends Stage {

    public FindMyView(String ownerName, ETNube nube) {
        setTitle("Find My – " + ownerName);

        VBox box = new VBox(8);
        box.setPadding(new Insets(12));

        List<ETNube.Reporte> reportes = nube.getReportes(ownerName);

        if (reportes.isEmpty()) {
            box.getChildren().add(new Label("No hay reportes disponibles para " + ownerName));
        } else {
            for (ETNube.Reporte r : reportes) {
                String texto = String.format("[%s] detectó a %s en (%.0f, %.0f)",
                        r.deviceName, r.cellularOwner, r.x, r.y);
                box.getChildren().add(new Label(texto));
            }
        }

        setScene(new Scene(box, 350, Math.max(100, reportes.size() * 30 + 40)));
        show();
    }
}