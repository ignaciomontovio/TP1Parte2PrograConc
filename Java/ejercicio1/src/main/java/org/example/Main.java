package org.example;

import java.util.Formatter;
import java.util.Random;

public class Main
{

  static Random rnd = new Random();
  static final int FIL = 5;
  static final int COL = 5;
  static int[][] matriz1 = new int[5][5];
  static int[][] matriz2 = new int[5][5];
  static int[][] matrizResultadoCS = new int[5][5];
  static int[][] matrizResultadoCG = new int[5][5];

  public static void main(String[] args) throws InterruptedException
  {

    //Inicilizar matriz con numeros aleatorios
    inicializarMatriz(matriz1);
    inicializarMatriz(matriz2);

    //Mostrar matriz inicial
    System.out.println("\nMatriz inicial 1:\n");
    mostrarMatriz(matriz1);
    System.out.println("\nMatriz inicial 2:\n");
    mostrarMatriz(matriz2);

    System.out.println("\n-------------------------------\n");

    //Sumar matriz secuencialmente
    sumaSecuencial(matriz1, matriz2, matrizResultadoCS);

    Thread[] threads = new Thread[5];

    for (int i = 0; i < FIL; i++)
    {
      threads[i] = new Hilo(i);
      threads[i].start();
    }

    for (int i = 0; i < FIL; i++)
    {
      threads[i].join();
    }

    System.out.println("Matriz resultado secuencial:\n");
    mostrarMatriz(matrizResultadoCS);

    System.out.println("\nMatriz resultado con hilos:\n");
    mostrarMatriz(matrizResultadoCG);

    compararMatriz(matrizResultadoCS, matrizResultadoCG);
  }


  public static class Hilo extends Thread
  {

    private int position;

    public Hilo(int i)
    {
      position = i;
    }

    public void run()
    {
      for (int j = 0; j < COL; j++)
      {
        matrizResultadoCG[position][j] = matriz1[position][j] + matriz2[position][j];
      }
    }

  }

  private static void inicializarMatriz(int[][] matriz)
  {
    for (int i = 0; i < FIL; i++)
    {
      for (int j = 0; j < COL; j++)
      {
        matriz[i][j] = (rnd.nextInt(65) - 32);
      }
    }

  }

  private static void sumaSecuencial(int[][] matriz1, int[][] matriz2, int[][] matrizR)
  {
    for (int i = 0; i < FIL; i++)
    {
      for (int j = 0; j < COL; j++)
      {
        matrizR[i][j] = matriz1[i][j] + matriz2[i][j];
      }
    }
  }


  public static void mostrarMatriz(int[][] matriz)
  {
    for (int i = 0; i < FIL; i++)
    {
      for (int j = 0; j < COL; j++)
      {
        System.out.print(new Formatter().format("%3d ", matriz[i][j]));
      }
      System.out.println();
    }

  }

  private static void compararMatriz(int[][] matrizResultadoCS, int[][] matrizResultadoCG)
  {
    for (int i = 0; i < FIL; i++)
    {
      for (int j = 0; j < COL; j++)
      {
        if (!(matrizResultadoCS[i][j] == matrizResultadoCG[i][j]))
        {
          System.out.println("Las matrices no son iguales!");
          System.exit(1);
        }
      }
    }
    System.out.println("Las matrices son iguales!");
  }
}