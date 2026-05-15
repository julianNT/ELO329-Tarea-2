import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Stage4 extends Application {
    private Territory territory;
    public ETNube nube;
    private TerritoryView territoryView;
    private double timeStep;
    private Pane pane;
    private int contador = 0;

    private File configDir;

    @Override
    public void start(Stage primaryStage) {
        Scanner configFile = openConfig(primaryStage);
        nube = new ETNube();
        territory = new Territory();
        String imageName = configDir.getAbsolutePath() + File.separator + configFile.next();
        territoryView = new TerritoryView(territory, imageName);
        timeStep = configFile.nextDouble();
        BorderPane scenePane = new BorderPane();
        pane = territoryView.getPane();
        nube.setPane(territoryView.getPane());
        scenePane.setTop(createMenuBar());
        scenePane.setCenter(territoryView);
        setupSimulator(configFile);
        Scene scene = new Scene(scenePane, 1000, 700);
        primaryStage.setTitle("EloTelTag Simulation: Stage 4");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Scanner openConfig(Stage stage) {
        Scanner configFile;
        do {
            try {
                File file = fileChooser(stage);
                configDir = file.getParentFile();
                configFile = new Scanner(file);
            } catch (FileNotFoundException e) {
                configFile = null;
            }
        } while (configFile == null);
        return configFile;
    }

    private File fileChooser(Stage stage) {
        FileChooser fChooser = new FileChooser();
        fChooser.setTitle("Select configuration file");
        fChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text file", "*.txt"));
        return fChooser.showOpenDialog(stage);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu simulMenu = new Menu("Simulation");
        MenuItem playMenuItem = new MenuItem("Play");
        MenuItem pauseMenuItem = new MenuItem("Pause");

        simulMenu.getItems().addAll(playMenuItem, pauseMenuItem);
        menuBar.getMenus().add(simulMenu);

        int etInterval = (int) Math.round(4.0 / timeStep);
        int tabletInterval = (int) Math.round(5.0 / timeStep);

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1000 * timeStep),
                        e -> {
                            territory.moveAll(timeStep);
                            contador++;

                            if (contador % etInterval == 0) {
                                for (Equipo eq : territory.equipment) {
                                    if (eq instanceof EloTelTag) {
                                        spawnRadar(eq.x.get(), eq.y.get());
                                    }
                                }
                                territory.actualizarNube(nube);
                            }

                            if (contador % tabletInterval == 0) {
                                for (Equipo eq : territory.equipment) {
                                    if (eq instanceof Tablet) {
                                        spawnRadar(eq.x.get(), eq.y.get());
                                    }
                                }
                                territory.actualizarNube(nube);
                            }

                            for (ETNube.Data dato : nube.cloudData) {
                                System.out.println(dato.ownerName + "    " + dato.equipmentName + "       " + dato.location);
                            }
                        }
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        playMenuItem.setOnAction(e -> timeline.play());
        pauseMenuItem.setOnAction(e -> timeline.pause());
        return menuBar;
    }

    private void setupSimulator(Scanner in) {
        int personNumber = in.nextInt();
        for (int i = 0; i < personNumber; i++)
            setupPersonEquipment(in);
    }

    private void setupPersonEquipment(Scanner in) {
        double x, y, r, theta, dt;

        String personName = in.next();
        int tagNumber = in.nextInt();
        boolean isThereTablet = in.nextInt() == 1;
        x = in.nextFloat();
        y = in.nextFloat();
        r = in.nextFloat();
        theta = Math.toRadians(in.nextFloat());
        dt = Math.toRadians(in.nextFloat());
        Cellular cellular = new Cellular(personName, x, y, r, theta, dt, nube);
        CellularView cView = new CellularView(cellular);
        territory.addEquipment(cellular);
        territoryView.add(cView);
        for (int j = 0; j < tagNumber; j++)
            setupEloTags(in, personName);
        if (isThereTablet) {
            x = in.nextFloat();
            y = in.nextFloat();
            r = in.nextFloat();
            theta = Math.toRadians(in.nextFloat());
            dt = Math.toRadians(in.nextFloat());
            Tablet tablet = new Tablet(personName, x, y, r, theta, dt);
            TabletView tView = new TabletView(tablet, nube);
            territory.addEquipment(tablet);
            territoryView.add(tView);
        }
    }

    private void setupEloTags(Scanner in, String personName) {
        double x, y, r, theta, dt;
        String tagName = in.next();
        x = in.nextFloat();
        y = in.nextFloat();
        r = in.nextFloat();
        theta = Math.toRadians(in.nextFloat());
        dt = Math.toRadians(in.nextFloat());
        EloTelTag tag = new EloTelTag(personName, tagName, x, y, r, theta, dt);
        EloTelTagView tagView = new EloTelTagView(tag);
        territory.addEquipment(tag);
        territoryView.add(tagView);
    }

    public void spawnRadar(double x, double y) {
        if (pane == null) return;
        Circle c = new Circle(x, y, 5);
        c.setStroke(Color.LIMEGREEN);
        c.setFill(null);
        pane.getChildren().add(c);
        ScaleTransition st = new ScaleTransition(Duration.millis(1000), c);
        st.setToX(10);
        st.setToY(10);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), c);
        ft.setToValue(0);
        ParallelTransition pt = new ParallelTransition(st, ft);
        pt.setOnFinished(e -> pane.getChildren().remove(c));
        pt.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}