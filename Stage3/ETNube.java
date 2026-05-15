import java.awt.geom.Point2D;
import java.io.PrintStream;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class ETNube {
    public ETNube() {
        cloudData = new ArrayList<Data>();
    }

    public void updateLocation(String owner, String equipment, float x, float y) {
        Point2D location;
        if ((location=getLocation(owner, equipment)) == null) {
            location=new Point2D.Float(x,y);
            Data data = new Data(owner, equipment, location);
            cloudData.add(data);
        }
        location.setLocation(x,y);
    }

    public Point2D getLocation(String owner, String equipment) {
        for (Data d : cloudData) {
            if (d.ownerName.equals(owner) && d.equipmentName.equals(equipment)) {
                return d.location;
            }
        }
        return null;
    }

    public void printHeader(PrintStream output) {
        System.out.print("Step");
        output.print("Step,");
        for (Data d : cloudData) {
            System.out.print("\t" + d.ownerName + "." + d.equipmentName + ".x" +
                    "\t" + d.ownerName + "." + d.equipmentName + ".y");

            output.print(
                    d.ownerName + "." + d.equipmentName + ".x" + "," +
                            d.ownerName + "." + d.equipmentName + ".y,"
            );
        }
        System.out.println();
        output.println();
    }

    public void printState(PrintStream output, int step) {
        System.out.print(step);
        output.print(step + ",");
        for (Data d : cloudData) {
            System.out.print("\t" + d.location.getX() + "\t" + d.location.getY());
            output.print( d.location.getX() + "," + d.location.getY()+",");
        }
        System.out.println();
        output.println();
    }

    public String stringFormatoFigura2(String nombre){
        String aux="Bienes de " + nombre + "\n";
        String aux2 = "";
        String aux3 = "";
        for (Data d : cloudData) {
            if (d.ownerName.equals(nombre) && d.equipmentName != "celular" && d.equipmentName != "tablet") {
                aux2 = aux2 + d.equipmentName + ": "+d .location.getX()+","+d.location.getY()  + "\n";
            }
        }

        for (Data d : cloudData) {
            if (d.ownerName.equals(nombre)&& (d.equipmentName == "celular" || d.equipmentName == "tablet")) {
                aux3 = aux3 +  d.equipmentName + ": "+d.location.getX()+","+d.location.getY() + "\n";
            }
        }

        aux = aux + "Items:\n" + aux2 + "Dispositivos:\n" + aux3;
        return aux;
    }

    public ArrayList<Data> cloudData;

    public static class Data {
        public Data(String owner, String equipment, Point2D loc) {
            ownerName = owner;
            equipmentName = equipment;
            location = loc;
        }
        public Point2D location;
        public String ownerName, equipmentName;
    }

    private Pane pane;

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public void spawnRadar(double x, double y) {

        if (pane == null) return;

        Circle c = new Circle(x, y, 5);
        c.setStroke(Color.LIMEGREEN);
        c.setFill(null);

        pane.getChildren().add(c);

        ScaleTransition st = new ScaleTransition(Duration.millis(500), c);
        st.setToX(10);
        st.setToY(10);

        FadeTransition ft = new FadeTransition(Duration.millis(500), c);
        ft.setToValue(0);

        st.play();
        ft.play();

        ft.setOnFinished(e -> pane.getChildren().remove(c));
    }
}
