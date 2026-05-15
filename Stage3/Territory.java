import java.util.ArrayList;
import java.util.List;

public class Territory {  // Piece of land where cellulars, tags, and tablets are located and moved.
    private ArrayList<Equipo> equipment = new ArrayList<>();
    public void addEquipment(Equipo eq) {
        equipment.add(eq);
    }
    public void moveAll(double timeStep) {
        for (Equipo eq : equipment) eq.move(timeStep);
    }
    public List<Cellular> getCellulars() {
        List<Cellular> cellulars = new ArrayList<>();
        for (Equipo eq : equipment)
            if (eq instanceof Cellular) cellulars.add((Cellular) eq);
        return cellulars;
    }
}
