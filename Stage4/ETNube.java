import java.util.*;

public class ETNube {

    public static class Reporte {
        public final String deviceName;    // nombre del tag/tablet que detectó
        public final String deviceOwner;   // dueño del tag/tablet
        public final String cellularOwner; // dueño del celular detectado
        public final double x, y;          // posición del celular al momento de la detección

        public Reporte(String deviceName, String deviceOwner, String cellularOwner, double x, double y) {
            this.deviceName = deviceName;
            this.deviceOwner = deviceOwner;
            this.cellularOwner = cellularOwner;
            this.x = x;
            this.y = y;
        }
    }

    private final Map<String, List<Reporte>> reportesPorDueno = new HashMap<>();

    public void addReporte(String deviceName, String deviceOwner, String cellularOwner, double x, double y) {
        reportesPorDueno.computeIfAbsent(deviceOwner, k -> new ArrayList<>())
                        .add(new Reporte(deviceName, deviceOwner, cellularOwner, x, y));
    }

    public List<Reporte> getReportes(String ownerName) {
        return reportesPorDueno.getOrDefault(ownerName, Collections.emptyList());
    }
}