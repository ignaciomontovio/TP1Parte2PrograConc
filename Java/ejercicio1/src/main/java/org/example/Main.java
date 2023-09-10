package org.example;

import java.util.Formatter;
import java.util.Random;

public class Main {
    static int[][] matriz1 = new int[5][5];
    static int[][] matriz2 = new int[5][5];
    static int[][] matrizResult1 = {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
    static int[][] matrizResult2 = {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};

    public static void main(String[] args) throws InterruptedException {
        Random rnd = new Random();


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matriz1[i][j] = (rnd.nextInt(65) - 32);
                matriz2[i][j] = (rnd.nextInt(65) - 32);
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrizResult1[i][j] = matriz1[i][j] + matriz2[i][j];
            }
        }

        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Hilo(i);
            threads[i].start();
        }
        for (int i = 0; i < 5; i++) {
            threads[i].join();
        }


        System.out.println("Matriz resultado secuencial");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(new Formatter().format("%3d ", matrizResult1[i][j]));
            }
            System.out.println();
        }
        System.out.println("Matriz resultado con hilos");
        Formatter format = new Formatter();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(new Formatter().format("%3d ", matrizResult2[i][j]));
            }
            System.out.println();
        }
    }

    public static class Hilo extends Thread {
        int position;
        public Hilo(int i) {
            position= i;
        }

        @Override
        public void run()
        {
            for (int j = 0; j < 5; j++) {
                matrizResult2[position][j] = matriz1[position][j] + matriz2[position][j];
            }
        }
    }
}