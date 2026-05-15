package com.example.t2stage3v2;
import java.util.ArrayList;

public class Territory {

     ArrayList<Equipo> equipment = new ArrayList<>();

    public void addEquipment(Equipo eq) {
        equipment.add(eq);
    }

    public void moveAll(double timeStep) {

        for (Equipo eq : equipment) {
            eq.move(timeStep);
        }

    }
    public void actualizarNube(ETNube nube) {

        for (Equipo eq : equipment) {

            // =========================
            // CELLULAR
            // =========================
            if (eq instanceof Cellular celular) {

                nube.updateLocation(
                        celular.getOwnerName(),
                        "cellular",
                        (float) celular.xProperty().get(),
                        (float) celular.yProperty().get()
                );
                continue;
            }

            // =========================
            // ELOTELTAG O TABLET
            // =========================
            if (eq instanceof EloTelTag || eq instanceof Tablet) {

                Cellular masCercano = null;
                double menorDist = Double.MAX_VALUE;

                double ex = eq.xProperty().get();
                double ey = eq.yProperty().get();

                for (Equipo otro : equipment) {

                    if (otro instanceof Cellular celular) {

                        double dx = ex - celular.xProperty().get();
                        double dy = ey - celular.yProperty().get();

                        double dist = Math.sqrt(dx * dx + dy * dy);

                        if (dist < menorDist) {
                            menorDist = dist;
                            masCercano = celular;
                        }
                    }
                }

                if (masCercano != null && menorDist < 50) {

                    String nombreEquipo;

                    if (eq instanceof EloTelTag tag) {
                        nombreEquipo = tag.getName();
                    } else {
                        nombreEquipo = "tablet";
                    }

                    nube.updateLocation(
                            eq.getOwnerName(),
                            nombreEquipo,
                            (float) masCercano.xProperty().get(),
                            (float) masCercano.yProperty().get()
                    );
                }
            }
        }
    }
}