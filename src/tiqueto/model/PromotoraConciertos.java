package tiqueto.model;

import tiqueto.EjemploTicketMaster;

public class PromotoraConciertos extends Thread {

    final WebCompraConciertos webCompra;

    public PromotoraConciertos(WebCompraConciertos webCompra) {
        super();
        this.webCompra = webCompra;
    }

    @Override
    public void run() {

        while (EjemploTicketMaster.restantes > 0) {

            if (!webCompra.hayEntradas()) {
                mensajePromotor("Toca reponer entradas. Aún quedan: " + EjemploTicketMaster.restantes);
                webCompra.reponerEntradas(EjemploTicketMaster.REPOSICION_ENTRADAS);
                try {
                    Thread.sleep((int) ((Math.random() * (8000 - 3000 + 3000)) + 3000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        }
        mensajePromotor("Se acabaron las entradas. Toca cerrar venta");
        webCompra.cerrarVenta();
    }

    /**
     * Método a usar para cada impresión por pantalla
     *
     * @param mensaje Mensaje que se quiere lanzar por pantalla
     */
    private void mensajePromotor(String mensaje) {
        System.out.println(System.currentTimeMillis() + "| Promotora: " + mensaje);

    }
}
