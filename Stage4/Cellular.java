public class Cellular extends Equipo {
    public Cellular(String owner, double x, double y, double r, double theta, double dt, ETNube nube) {
        super(owner,x,y,r,theta,dt);
        this.nube = nube;
    }

    public void reportTagLocation(EloTelTag tag) {
        nube.updateLocation(
                ownerName,
                "cellular",
                (float) this.x.get(),
                (float) this.y.get()
        );
    }
}