package tiqueto;

import java.util.ArrayList;
import java.util.List;

import tiqueto.model.FanGrupo;
import tiqueto.model.PromotoraConciertos;
import tiqueto.model.WebCompraConciertos;

public class EjemploTicketMaster {

    // Total de entradas que se vender�n: 10
    public static int TOTAL_ENTRADAS = 10;
    public static int restantes = TOTAL_ENTRADAS;

    // El número de entradas que reponerá cada vez el promotor: 2
    public static int REPOSICION_ENTRADAS = 2;

    // El número máximo de entradas por fan: 10
    public static int MAX_ENTRADAS_POR_FAN = 10;

    // El número total de fans: 5
    public static int NUM_FANS = 5;

    public static void main(String[] args) throws InterruptedException {

        String mensajeInicial = "[ Empieza la venta de tickets. Se esperan %d fans, y un total de %d entradas ]";
        System.out.println(String.format(mensajeInicial, NUM_FANS, TOTAL_ENTRADAS));
        WebCompraConciertos webCompra = new WebCompraConciertos();
        PromotoraConciertos liveNacion = new PromotoraConciertos(webCompra);
        List<FanGrupo> fans = new ArrayList<>();

        // Creamos todos los fans
        for (int numFan = 1; numFan <= NUM_FANS; numFan++) {
            FanGrupo fan = new FanGrupo(webCompra, numFan);
            fans.add(fan);
            fan.start();
        }

        //Lanzamos al promotor para que empiece a reponer entradas
        liveNacion.start();

        //Esperamos a que el promotor termine, para preguntar a los fans cu�ntas entradas tienen compradas
        liveNacion.join();

        System.out.println("\n [ Terminada la fase de venta - Sondeamos a pie de calle a los compradores ] \n");
        System.out.println("Total entradas ofertadas: " + TOTAL_ENTRADAS);
        System.out.println("Total entradas disponibles en la web: " + webCompra.entradasRestantes());

        int total = 0;
        // Les preguntamos a cada uno
        for (FanGrupo fan : fans) {
            fan.dimeEntradasCompradas();
            total += fan.cuantasEntradas();
        }
        System.out.println("En total se vendieron: " + total);

        Thread[] threads = new Thread[Thread.activeCount()];
        Thread.enumerate(threads);

        // Opcional: Imprimir información sobre todos los hilos para ver cuáles están en ejecución llegado este punto:
        for (Thread thread : threads) {
            if (thread != null && thread.isAlive()) {
                System.out.println("Hilo activo: " + thread.getName() + " de la clase " + thread.getClass());
            }
        }

    }


}
