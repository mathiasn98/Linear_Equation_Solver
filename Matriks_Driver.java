/*
LINEAR EQUATION SOLVER
Name : Mathias Novianto, Louis Leslie, Alvin Limassa
Date : 5 November 2017
*/
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.lang.Math;

public class Matriks_Driver
{
	public static void main(String[] args)
	{
		//KAMUS
		Matriks m;
		//Variable penampung banyak solusi
		int x;
		//Variable pilihan
		int menu;
		//Scanner
		Scanner s = null;
		//Variable boolean menentukan apakah matriks sudah dibaca.
		boolean IsMatriksExist = false;
		//Variable boolean untuk exit
		boolean exit = false;
		
		s = new Scanner (System.in);
		
		
		//ALGORITMA
		m = new Matriks(30,30);
		
		System.out.println("===================================================");
		System.out.println("|                                                 |");
		System.out.println("|  |\\    /|    /\\   ===|===  |===\\  |===\\  |===|  |");
		System.out.println("|  | \\  / |   /--\\     |     |===/  |===/  |   |  |");
		System.out.println("|  |  \\/  |  /    \\    |     |      |  \\   |===|  |");
		System.out.println("|                                                 |");
		System.out.println("===================================================");
		System.out.println();
		System.out.println("==============Program to solve Matrixes============");
		
		while(!exit)
		{
			System.out.println("\nMenu : ");
			System.out.println("1. Read matrix's external file");
			System.out.println("2. Input the matrix");
			System.out.println("3. Print the solution");
			System.out.println("4. Output the solution to external file");
			System.out.println("5. Print the matrix");
			System.out.println("6. Exit");
			System.out.print("Masukkan nomor untuk memilih menu : ");
			menu = s.nextInt();
			switch (menu)
			{
				case 1 : {
							m.BacaMatriksFile("testcase.txt");
							//m.TulisMatriks();
							System.out.println();
							m.MatriksEchelon();
							//m.TulisMatriks();
							m.MatriksReduce();
							//m.TulisMatriks();
							x=m.CekNoSolution();
							if (x!=1)
							{
								if(m.NBrsEff<(m.NKolEff-1))
								{
									m.InfiniteSolution();
								}
								else
								{
									m.SubstitusiBalik();
								}
							}
							
							System.out.println("Matrix have read.");
							IsMatriksExist = true;
							break;
						 }
						 
				case 2 : {
							m.BacaMatriks();
							m.MatriksEchelon();
							m.MatriksReduce();
							x=m.CekNoSolution();
							if (x!=1)
							{
								if(m.NBrsEff<(m.NKolEff-1))
								{
									m.InfiniteSolution();
								}
								else
								{
									m.SubstitusiBalik();
								}
							}
							IsMatriksExist = true;
							break;
						 }
						 
				case 3 : {
							
							if (IsMatriksExist)
							{
								
								x=m.CekNoSolution();
								if (x==1)
								{
									System.out.println("No solution");
								}
								else if(m.NBrsEff<(m.NKolEff-1))
								{
									m.TulisInfiniteSolutionKelayar();
									break;
								}
								else
								{
									m.TulisHasil();
								}
								break;
							}
							else
							{
								System.out.println("Please input the matrix first !");
								break;
							}
						 }
						 
				case 4 : {
							if (IsMatriksExist)
							{
								m.HasilMatriks();
								break;
							}
							else
							{
								System.out.println("Input matrix first !");
								break;
							}
						 }
						 
				case 5 : {
							if (IsMatriksExist)
							{
								m.TulisMatriks();
								break;
							}
							else
							{
								System.out.println("Input matrix first !");
								break;
							}
						 }
				case 6 : {
							exit=true;
							break;
							
						 }
			}			
		}
	}

}
