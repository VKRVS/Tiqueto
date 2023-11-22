package tiqueto.model;

import tiqueto.EjemploTicketMaster;
import tiqueto.IOperacionesWeb;

public class WebCompraConciertos implements IOperacionesWeb {

    public int entradasDisponibles;
    public boolean cierre = false;

    public WebCompraConciertos() {
        super();
        this.entradasDisponibles = 0;
    }


    @Override
    public synchronized boolean comprarEntrada() {
        if (hayEntradas()) {
            this.entradasDisponibles--;
            return true;
        } else return false;
    }


    @Override
    public synchronized int reponerEntradas(int numeroEntradas) {
        if (this.entradasDisponibles <= 0) {
            for (int i = 0; i < Math.min(numeroEntradas, EjemploTicketMaster.restantes); i++) {
                EjemploTicketMaster.restantes--;
                this.entradasDisponibles++;
            }

        }

        return this.entradasDisponibles;
    }


    @Override
    public synchronized void cerrarVenta() {
        cierre = true;
    }


    @Override
    public synchronized boolean hayEntradas() {
        if (this.entradasDisponibles > 0) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public synchronized int entradasRestantes() {
        return this.entradasDisponibles;
    }


    /**
     * Método a usar para cada impresión por pantalla
     *
     * @param mensaje Mensaje que se quiere lanzar por pantalla
     */
    private void mensajeWeb(String mensaje) {
        System.out.println(System.currentTimeMillis() + "| WebCompra: " + mensaje);

    }

}
