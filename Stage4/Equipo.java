import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Representa un equipo móvil que se desplaza por el territorio.
 * <p>
 * Cada equipo tiene una posición (x, y) expresada en píxeles, una rapidez {@code r},
 * un ángulo de movimiento {@code theta} y una variación aleatoria de ángulo {@code dtheta}
 * que se aplica en cada paso de simulación. Al alcanzar los bordes del territorio,
 * el equipo rebota invirtiendo la componente perpendicular de su velocidad.
 * </p>
 */
public class Equipo {

    /** Nombre del dueño del equipo. */
    protected final String ownerName;

    /** Referencia a la nube de datos compartida (puede ser {@code null} en subclases que no la usen). */
    public ETNube nube;

    /** Posición horizontal en píxeles, observable mediante binding JavaFX. */
    protected DoubleProperty x = new SimpleDoubleProperty();

    /** Posición vertical en píxeles, observable mediante binding JavaFX. */
    protected DoubleProperty y = new SimpleDoubleProperty();

    private double r, theta, dtheta;

    /**
     * Crea un equipo con posición y parámetros de movimiento iniciales.
     *
     * @param owner  nombre del dueño del equipo
     * @param _x     posición horizontal inicial en píxeles
     * @param _y     posición vertical inicial en píxeles
     * @param r      rapidez de desplazamiento en píxeles/segundo
     * @param theta  ángulo inicial de movimiento en radianes
     * @param dtheta variación máxima del ángulo por paso de simulación en radianes
     */
    public Equipo(String owner, double _x, double _y,
                  double r, double theta, double dtheta) {

        ownerName = owner;

        x.set(_x);
        y.set(_y);

        this.r = r;
        this.theta = theta;
        this.dtheta = dtheta;
    }

    /**
     * Avanza la posición del equipo un paso de simulación de duración {@code dt}.
     * <p>
     * El ángulo varía aleatoriamente en el rango {@code (-dtheta/2, +dtheta/2)}.
     * Si el equipo sale del territorio rebota sobre el borde correspondiente.
     * </p>
     *
     * @param dt duración del paso de simulación en segundos
     */
    public void move(double dt) {

        theta += (Math.random() - 0.5) * dtheta;

        x.set(x.get() + r * Math.cos(theta) * dt);
        y.set(y.get() + r * Math.sin(theta) * dt);

        if (x.get() < 0 || x.get() > TerritoryView.WIDTH)
            theta = Math.PI - theta;

        if (y.get() < 0 || y.get() > TerritoryView.HEIGHT)
            theta = -theta;
    }

    /**
     * Retorna la propiedad JavaFX de la coordenada horizontal.
     *
     * @return propiedad {@code DoubleProperty} para binding de la posición x
     */
    public DoubleProperty xProperty() {
        return x;
    }

    /**
     * Retorna la propiedad JavaFX de la coordenada vertical.
     *
     * @return propiedad {@code DoubleProperty} para binding de la posición y
     */
    public DoubleProperty yProperty() {
        return y;
    }

    /**
     * Retorna el nombre del dueño de este equipo.
     *
     * @return nombre del dueño
     */
    public String getOwnerName() {
        return ownerName;
    }
}