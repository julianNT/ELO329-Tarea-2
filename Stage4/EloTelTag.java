/**
 * Representa un dispositivo EloTelTag rastreable perteneciente a una persona.
 * <p>
 * Un EloTelTag es un equipo móvil de bajo consumo que emite señales de radar
 * periódicamente para ser detectado por el celular más cercano dentro del territorio.
 * Cuando un celular lo alcanza, reporta la posición del tag a la nube ({@link ETNube}).
 * </p>
 *
 * @see Equipo
 * @see EloTelTagView
 */
public class EloTelTag extends Equipo {

    /** Nombre identificador del tag (p. ej. "maleta", "llaves"). */
    private final String name;

    /**
     * Crea un EloTelTag con nombre, dueño, posición y parámetros de movimiento.
     *
     * @param owner nombre del dueño del tag
     * @param n     nombre identificador del tag
     * @param x     posición horizontal inicial en píxeles
     * @param y     posición vertical inicial en píxeles
     * @param r     rapidez de desplazamiento en píxeles/segundo
     * @param theta ángulo inicial de movimiento en radianes
     * @param dt    variación máxima del ángulo por paso de simulación en radianes
     */
    public EloTelTag(String owner, String n,
                     double x, double y,
                     double r, double theta, double dt) {

        super(owner, x, y, r, theta, dt);

        name = n;
    }

    /**
     * Retorna el nombre identificador de este tag.
     *
     * @return nombre del tag
     */
    public String getName() {
        return name;
    }
}
