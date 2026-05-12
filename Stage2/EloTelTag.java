public class EloTelTag extends Equipo {
    public EloTelTag(String owner, String n, double x, double y, double r, double theta, double dt) {
        super(owner, x, y, r, theta, dt);
        this.name=n;
    }
    public String getName(){
        return name;
    }
    private final String name;
}
