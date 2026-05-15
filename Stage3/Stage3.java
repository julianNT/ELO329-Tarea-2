import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Stage3 extends Application {
    private Territory territory;
    private TerritoryView territoryView;
    private double timeStep;
    private File configFileRef;
    private final ETNube nube = new ETNube();
    private final List<EloTelTag> tags = new ArrayList<>();
    private final List<Tablet> tablets = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        Scanner configFile = openConfig(primaryStage);
        territory = new Territory();
        String imageUri = new File(configFileRef.getParent(), configFile.next()).toURI().toString();
        territoryView = new TerritoryView(territory, imageUri);
        timeStep = configFile.nextDouble();
        BorderPane scenePane = new BorderPane();
        scenePane.setTop(createMenuBar());
        scenePane.setCenter(territoryView);
        setupSimulator(configFile);
        setupScanTimelines();
        Scene scene = new Scene(scenePane, 1000, 700);
        primaryStage.setTitle("EloTelTag Simulation: Stage 3");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupScanTimelines() {
        Timeline tagScan = new Timeline(
            new KeyFrame(Duration.seconds(4), e -> {
                List<Cellular> cellulars = territory.getCellulars();
                for (EloTelTag tag : tags) tag.scan(cellulars, nube);
            })
        );
        tagScan.setCycleCount(Timeline.INDEFINITE);
        tagScan.play();

        Timeline tabletScan = new Timeline(
            new KeyFrame(Duration.seconds(5), e -> {
                List<Cellular> cellulars = territory.getCellulars();
                for (Tablet tablet : tablets) tablet.scan(cellulars, nube);
            })
        );
        tabletScan.setCycleCount(Timeline.INDEFINITE);
        tabletScan.play();
    }

    private Scanner openConfig(Stage stage) {
        Scanner configFile;
        do {
            try {
                configFileRef = fileChooser(stage);
                configFile = new Scanner(configFileRef);
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
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(1000 * timeStep), e -> territory.moveAll(timeStep))
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
        Cellular cellular = new Cellular(personName, x, y, r, theta, dt);
        CellularView cView = new CellularView(cellular, nube);
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
            TabletView tabletView = new TabletView(tablet, nube);
            territory.addEquipment(tablet);
            territoryView.add(tabletView);
            tablets.add(tablet);
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
        tags.add(tag);
    }

    public static void main(String[] args) {
        launch(args);
    }
}