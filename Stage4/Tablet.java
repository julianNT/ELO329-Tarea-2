import java.util.List;

public class Tablet extends Equipo {
    public Tablet(String owner, double x, double y, double r, double theta, double dt) {
        super(owner, x, y, r, theta, dt);
    }

    public void scan(List<Cellular> cellulars, ETNube nube) {
        for (Cellular c : cellulars) {
            double dx = c.x.get() - x.get();
            double dy = c.y.get() - y.get();
            if (Math.sqrt(dx * dx + dy * dy) <= 50)
                nube.addReporte(ownerName + "-tablet", ownerName, c.getName(), c.x.get(), c.y.get());
        }
    }
}

