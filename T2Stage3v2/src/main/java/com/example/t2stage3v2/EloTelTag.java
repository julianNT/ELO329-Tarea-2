package com.example.t2stage3v2;
public class EloTelTag extends Equipo {

    private final String name;

    public EloTelTag(String owner, String n,
                     double x, double y,
                     double r, double theta, double dt) {

        super(owner, x, y, r, theta, dt);

        name = n;
    }

    public String getName() {
        return name;
    }
}