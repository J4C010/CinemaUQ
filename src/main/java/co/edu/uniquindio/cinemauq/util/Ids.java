package co.edu.uniquindio.cinemauq.util;

import java.util.UUID;

public final class Ids {
    private Ids() {
    }
    public static String nuevo(String prefijo) {
        return prefijo + "-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
