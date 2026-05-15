import java.util.List;

public class EloTelTag extends Equipo {
    private final String name;

    public EloTelTag(String owner, String n, double x, double y, double r, double theta, double dt) {
        super(owner, x, y, r, theta, dt);
        this.name = n;
    }

    public String getName() { return name; }

    public void scan(List<Cellular> cellulars, ETNube nube) {
        for (Cellular c : cellulars) {
            double dx = c.x.get() - x.get();
            double dy = c.y.get() - y.get();
            if (Math.sqrt(dx * dx + dy * dy) <= 50)
                nube.addReporte(name, ownerName, c.getName(), c.x.get(), c.y.get());
        }
    }
}
