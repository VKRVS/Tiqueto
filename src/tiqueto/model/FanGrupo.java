package tiqueto.model;

import tiqueto.EjemploTicketMaster;

public class FanGrupo extends Thread {

    final WebCompraConciertos webCompra;
    int numeroFan;
    private String tabuladores = "\t\t\t\t";
    int entradasCompradas = 0;

    public FanGrupo(WebCompraConciertos web, int numeroFan) {
        super();
        this.numeroFan = numeroFan;
        this.webCompra = web;
    }

    @Override
    public void run() {
        while ((this.entradasCompradas < EjemploTicketMaster.MAX_ENTRADAS_POR_FAN) && ((EjemploTicketMaster.restantes > 0) || (webCompra.hayEntradas()))) {
            synchronized (webCompra) {
                if (webCompra.hayEntradas()) {
                    mensajeFan("Me dispongo a comprar una entrada");
                    if (webCompra.comprarEntrada()) {
                        this.entradasCompradas++;
                        mensajeFan("Compré una, ya tengo: " + this.entradasCompradas);
                    }
                }
            }
            try {
                Thread.sleep((int) ((Math.random() * (3000 - 1000 + 1000)) + 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        mensajeFan("Se ha cerrado la venta :(");
    }


    public void dimeEntradasCompradas() {
        mensajeFan("Sólo he conseguido: " + entradasCompradas);
    }

    public int cuantasEntradas() {
        return this.entradasCompradas;
    }

    /**
     * Método a usar para cada impresión por pantalla
     *
     * @param mensaje Mensaje que se quiere lanzar por pantalla
     */
    private void mensajeFan(String mensaje) {
        System.out.println(System.currentTimeMillis() + "|" + tabuladores + " Fan " + this.numeroFan + ": " + mensaje);
    }
}
