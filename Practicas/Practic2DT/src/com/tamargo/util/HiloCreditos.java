package com.tamargo.util;

import javax.swing.*;

public class HiloCreditos extends Thread {

    private JLabel fondo;
    private JLabel gif1;
    private JLabel gif2;
    private boolean bucle;

    public HiloCreditos() {
    }

    public HiloCreditos(JLabel fondo, JLabel gif1, JLabel gif2) {
        this.fondo = fondo;
        this.gif1 = gif1;
        this.gif2 = gif2;
    }

    @Override
    public void run() {
        fondo.setIcon(new ImageIcon("./assets/4-creditosFadeIn.gif"));

        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        fondo.setIcon(new ImageIcon("./assets/4-creditosFondo.gif"));

        bucle = true;
        int tiempoEspera = 25;

        int n = -147;
        int i = 0;
        while (bucle) {
            i++;
            n++;
            if (i > 233 + 68)
                i = 1;
            if (n > 233 + 68)
                n = 1;
            gif1.setIcon(new ImageIcon("./assets/creditos/creditos (" + i + ").png"));
            gif2.setIcon(new ImageIcon("./assets/creditos/creditos (" + n + ").png"));
            try {
                Thread.sleep(tiempoEspera);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void parar() {
        this.bucle = false;
    }
}
