import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * Vista JavaFX de un {@link EloTelTag} en el territorio.
 * <p>
 * Muestra el tag como un círculo verde de radio 6 px con el nombre del tag
 * a su derecha. La posición del círculo y la etiqueta se mantienen sincronizadas
 * con el modelo mediante bindings sobre las propiedades {@code xProperty()} e
 * {@code yProperty()} del {@link EloTelTag}.
 * </p>
 *
 * @see EloTelTag
 * @see Equipo
 */
public class EloTelTagView extends Group {

    /** Modelo del tag que esta vista representa. */
    private final EloTelTag tag;

    /** Círculo que representa visualmente el tag. */
    private final Circle circle;

    /** Etiqueta con el nombre del tag. */
    private final Text label;

    /**
     * Crea la vista para el {@link EloTelTag} dado y configura los bindings de posición.
     *
     * @param tag el modelo {@link EloTelTag} a visualizar
     */
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
