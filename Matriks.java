/*
LINEAR EQUATION SOLVER
Name : Mathias Novianto, Louis Leslie, Alvin Limassa
Date : 5 November 2017
*/

import java.util.*;
import javax.swing.*;
import java.io.*;
import java.lang.Math;
import java.lang.Double; 

//Class Matriks
public class Matriks
{	
	
	//Atribut
	public int NBrs, NBrsEff;
	public int NKol, NKolEff;
	public double[][] M;
	public double[] hasil;
	public String[] text;
	public double[][] tabelinfinite;
	public char[] tabelvariabel;
	
	//Konstruktor kelas Matriks
	//Membentuk matriks ukuran NBaris x NKolom berisi 0
	//Matriks diisi mulai dari indeks (1,1)
	public Matriks(int NBaris, int NKolom)
	{
		this.NBrs = NBaris;
		this.NKol = NKolom;
		this.M = new double[this.NBrs+1][this.NKol+1];
	}
	
	public void BacaMatriksFile(String namafile)
	{
		//KAMUS LOKAL
		File f = new File(namafile);
		Scanner s = null;
		try {
            s = new Scanner(f);
        } 
        catch (FileNotFoundException e) 
        {
			System.out.println("File tidak ditemukan!");
        }
        int i, j, k;
        String line;
        String[] arrLine;
        double parseTemp;
        int lenLine; //length of line
        
        //ALGORITMA
        i = 0;
        j = 0;
        
		while (s.hasNext())
		{
			line = s.nextLine();
			arrLine = line.split(" ");
			lenLine = arrLine.length;
			j = 0;
			i++;
			
			for (k = 0; k < lenLine; k++)
			{
				j++;
				parseTemp = Double.parseDouble(arrLine[k]);
				this.M[i][j] = parseTemp;	
			}
		}
		
		//Setelah mengisi matriks, didapat ukuran matriks hasil increment
		this.NKolEff = j;
		this.NBrsEff = i;
		
	}
	
	public void BacaMatriks()
	{
		//KAMUS LOKAL
		int NBaris, NKolom;
		int i, j;
		Scanner s = null;
		
		//ALGORITMA
		s = new Scanner (System.in);
		System.out.print("Masukkan Jumlah Baris Matriks : ");
		NBaris = s.nextInt();
		System.out.print("Masukkan Jumlah Kolom Matriks : ");
		NKolom = s.nextInt();
		for (i = 1; i <= NBaris; i++)
		{
			for (j = 1;j <= NKolom; j++)
			{
				this.M[i][j] = s.nextDouble();
			}
		}
		
		this.NBrsEff = NBaris;
		this.NKolEff = NKolom;
	}
	
	public void TulisMatriks()
	{
		//KAMUS LOKAL
		int i, j;
		
		//ALGORITMA
		for (i = 1; i <= this.NBrsEff; i++)
		{
			for (j = 1; j <= this.NKolEff; j++)
			{
				System.out.print(String.format("%.2f ", this.M[i][j]));
			}
			System.out.println();
		}
	}
	
	public void SwapBaris(int i1, int i2) //Parameter i1 dan i2 merepresentasi dua baris mana yang hendak ditukar di dalam matriks 
	{
		//KAMUS LOKAL
		double temp;
		int j;
		
		//ALGORITMA
		for (j = 1; j <= NKolEff; j++)
		{
			temp = this.M[i1][j];
			this.M[i1][j] = this.M[i2][j];
			this.M[i2][j] = temp;
		}
	}
	
	public int MaxKol(int i, int j)
	{
		//Fungsi MaxKol
		//Mengembalikan indeks baris dengan nilai tertinggi pada suatu kolom pada matriks
		//atau jika nilai pada suatu kolom semua sama mengembalikian MaxKol(i, j+1) (Rekursif)
		//Pre-kondisi : Matriks tidak kosong
		
		//KAMUS LOKAL
		int imax, k, m;
		double tempSama;
		//boolean pengecek apakah kolom yang diperiksa sama
		boolean IsKolomSama;
		
		boolean foundsatu;
		
		//ALGORITMA
		imax = i;
		IsKolomSama = true;
		tempSama = this.M[i][j];
		m = i;
		
		for (k = i+1; k <= NBrsEff; k++)
		{
			IsKolomSama = this.M[k][j] == tempSama;

			if(!IsKolomSama && this.M[k][j]!=0)
			{
				tempSama = this.M[k][j];
				m = k;
			}
			
			foundsatu=this.M[k][j]==1;
			if(foundsatu)
			{
				return k;
			}
			
			
			if(Math.abs(this.M[k][j]) > Math.abs(this.M[imax][j]))
			{
				imax = k;
			}
		}

		if (IsKolomSama && j < NKolEff-1)
		{
			return (MaxKol(m, j+1));
		}
		else
		{
			return imax;
		}
	}
	
	//PROSEDUR BagiBaris
	//I.S. : Matriks terdefinisi
	//F.S. : Baris ke i dimulai dari kolom ke j sampai NKolEff dibagi dengan M[i][j]
	public void BagiBaris(int i, int j)
	{
		//KAMUS LOKAL
		int k;
		double faktor;
		
		//ALGORITMA
		faktor = this.M[i][j];
		
		if (faktor != 0)
		{
			for (k = j; k <= NKolEff; k++)
			{
				this.M[i][k] = this.M[i][k] / faktor;
			}
		}
	}
	
	public void Eliminasi(int i, int j, int iS) 
	// iS = i Source = baris yang menjadi pengurang bagi baris yang ingin dikurangkan
	// j = kolom bersesuaian yang ingin dieliminasi
	{
		//KAMUS LOKAL
		int k;
		double faktor;
		double nilaiS; //nilai sumber M[iS][j]
		
		//ALGORITMA
		nilaiS = this.M[iS][j];
		if (nilaiS != 0)
		{
			faktor = this.M[i][j] / nilaiS;
			for (k = j; k <= NKolEff; k++)
			{	
				this.M[i][k] -= this.M[iS][k] * faktor;
			}
		}
	}

	
	public void MatriksEchelon()
	//I.S.	: Matriks Terdefinisi
	//F.S.	: Matriks berbentuk Row Echelon
	{
		//KAMUS LOKAL
		int pass, i, j, k;
		
		//ALGORITMA
		j = 1;
		for(pass = 1; pass <= NBrsEff; pass++)
		{
			//Dapatkan index baris pertama dari pass yang bukan nol untuk mencari indeks j selanjutnya
			k = pass;
			while(IsBarisNol(k, j) && k <= NBrsEff)
			{
				k ++;
			}

			//Pivoting 1 untuk mendapatkan baris terpanjang (dengan angka 0 di depan paling sedikit)
			SwapBaris(pass, MaxKol(pass, j));
			
			//Dapatkan index j yaitu index kolom pertama di baris bukan nol pertama dihitung dari pass yang bukan nol
			while(this.M[k][j] == 0 && j < NKolEff-1)
			{
				j++;
			}
			
			//Pivoting 2 untuk memastikan urutan baris max di atas
			SwapBaris(pass, MaxKol(pass, j));
			
			
			//Proses penyederhanaan baris dan eliminasi terhadap baris lainnya
			if (!IsBarisNol(pass, j))
			{
				BagiBaris(pass, j);
				for(i = pass+1; i <= NBrsEff; i++)
				{
					Eliminasi(i, j, pass);	
				}
			}

			if(j < NKolEff-1)
			{
				j++;
			}
		}
		DeleteBarisNol();
	}
	
	public void DeleteBarisNol()
	{
		//KAMUS LOKAL
		int i;
		
		//ALGORITMA
		for(i = 1; i<= NBrsEff; i++)
		{
			if(IsBarisNol(i, 1))
			{
				SwapBaris(i, NBrsEff);
				NBrsEff --;
				i--;
			}
		}
	}

	
	public boolean IsBarisNol(int i, int j)
	{
		//FUNGSI IsBarisNol
		//mengembalikan true apabila satu baris matriks di baris ke-i memiliki elemen yang semua nol
		
		//KAMUS LOKAL
		boolean nol;
		
		//ALGORITMA
		nol = true;
		while (j <= NKolEff && nol)
		{
			nol = Math.abs(this.M[i][j]) == 	0;
			j++;
		}
		
		return nol;
	}
	
	public void SubstitusiBalik()
	//IS: Menerima matriks Echelon dengan matriks yang banyak solusinya berhingga
	//FS: Menghasilkan array hasil dengan x1 di indeks 1, x2 di indeks 2, dan seterusnya
	//dengan metode substitusi balik
	{
		//KAMUS LOKAL
		int i,j, k;
		double tempkurang;
		
		//ALGORITMA
		this.hasil=new double[this.NKolEff];
		j = NKolEff - 1;
		for(i=this.NBrsEff;i>=1;i--)
		{
			tempkurang = 0;
			for(k = j+1; k<=NKolEff-1; k++)
			{
				tempkurang = tempkurang + this.M[i][k]*this.hasil[k];
			}
			this.hasil[j] = this.M[i][this.NKolEff] - tempkurang;
			j--;
		}
		/*for(i=1;i<=NKolEff-1;i++){
			System.out.println(String.format("%.2f", hasil[i]));
		}*/
	}

	public int CekNoSolution ()
	{
		int i,j;
		int cek;
		i=1;
		cek=0;
		while ((cek==0) && (i<=this.NBrsEff))
		{
			j=1;
			cek=1;
			while((cek==1)&&(j<=this.NKolEff-1))
			{
				if ((this.M[i][j]!=0)||(this.M[i][NKolEff]==0))
				{
					cek=0;
				}
				j++;
			}
			i++;
		}
		return cek;
	}

	public void TulisHasil ()
	{
		for(int i=1;i<this.hasil.length;i++)
		{
			System.out.print("x"+i+" = ");
			System.out.println(String.format("%.10f ", this.hasil[i]));
		}
	}

	public void HasilMatriks ()
	{
		int i,j,sudahcetak;
		FileOutputStream fout;
		try
		{
			fout = new FileOutputStream ("Hasil.txt");
			if (this.CekNoSolution()==1)
			{
				new PrintStream(fout).println("Tidak Ada Solusi");
			}
			else if(NBrsEff<(NKolEff-1))
			{
				for(j=1;j<=NKolEff-1;j++)
				{
					sudahcetak=0;
					if (Double.isNaN(this.tabelinfinite[1][j]))
					{
						new PrintStream(fout).print(("X"+j+" = "));
						new PrintStream(fout).println(this.tabelvariabel[j]);
					}
					else
					{
						if (this.tabelinfinite[1][j]!=0)
						{
							new PrintStream(fout).print("X"+j+" = "+this.tabelinfinite[1][j]);
							sudahcetak=1;
						}
						else
						{
							new PrintStream(fout).print("X"+j+" = ");
						}
						for(i=2;i<=NKolEff;i++)
						{
							if((this.tabelinfinite[i][j]>0)&&(sudahcetak==1))
							{
								new PrintStream(fout).print("+");
								new PrintStream(fout).print(this.tabelinfinite[i][j]);
								new PrintStream(fout).print(this.tabelvariabel[i-1]);
								sudahcetak=1;
							}
							else if(this.tabelinfinite[i][j]<0)
							{
								new PrintStream(fout).print(this.tabelinfinite[i][j]);
								new PrintStream(fout).print(this.tabelvariabel[i-1]);
								sudahcetak=1;
							}
							else if (this.tabelinfinite[i][j]>0)
							{
								new PrintStream(fout).print(this.tabelinfinite[i][j]);
								new PrintStream(fout).print(this.tabelvariabel[i-1]);
								sudahcetak=1;
							}
						}
						new PrintStream(fout).println();
					}
				}
			}
			else	
			{
				for(i=1;i<this.NBrsEff+1;i++)
				{
					new PrintStream(fout).println("X"+i+" = "+String.format("%.2f",this.hasil[i]));
				}
			}
			fout.close();
		}
		catch (IOException e)
		{
			System.err.println("Unable to write file");
			System.exit(-1);
		}
	}
	
	public void EliminasiBalik(int i, int j, int iS) 
	// iS = i Source = baris yang menjadi pengurang bagi baris yang ingin dikurangkan
	// j = kolom bersesuaian yang ingin dieliminasi
	{
		//KAMUS LOKAL
		int k;
		double faktor;
		double nilaiS; //nilai sumber M[iS][j]
		
		//ALGORITMA
		nilaiS = this.M[iS][j];
		if(nilaiS!=0)
		{
			faktor = this.M[i][j] / nilaiS;
			for (k = NKolEff; k >= 1; k--)
			{	
				this.M[i][k] -= this.M[iS][k] * faktor;
			}	
		}
		
	}

	public void MatriksReduce()
	{
		//KAMUS LOKAL
		int pass, i, j;
		
		//ALGORITMA
		for(pass = NBrsEff; pass >=1; pass--)
		{
			j=CariAngka1(pass);
			for(i = pass-1; i >=1; i--)
			{
				EliminasiBalik(i,j,pass);
			}
		}
		DeleteBarisNol();
		/*
		j = NKolEff-1;
		for(pass = NBrsEff; pass >=1; pass--)
		{
			for(i = pass-1; i >=1; i--)
			{
				EliminasiBalik(i, j, pass);
			}
			j--;
		}
		DeleteBarisNol();
		*/
	}

	public int CekBaris1(int i)
	{
		int jumlah=0;
		int j=1;
		while (j<NKolEff)
		{
			if (this.M[i][j]!=0)
			{
				jumlah++;
			}
			j++;
		}
		if (jumlah==1)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

	public int CariAngka1(int i)
	{
		int j=1;
		while ((this.M[i][j]!=1)&&(j<=NKolEff-1))
		{
			j++;
		}
		return j;
	}


	public void InfiniteSolution ()
	{
		int jumlahpemisalan;

		jumlahpemisalan=NKolEff-NBrsEff-1;
		this.tabelinfinite = new double[NKolEff+1][NKolEff];
		this.tabelvariabel = new char [NKolEff];

		int i,j,k;
		int C_kol;
		int cvar=0;
		int asci='a';
		//pengisian array variabel bebas
		for (i=1;i<=NKolEff-1;i++)
		{
			this.tabelvariabel[i]=(char)asci;
			asci++;
		}
		//pengisian tabel hasil baris pertama NaN
		for (j=1;j<=NKolEff-1;j++)
		{
			this.tabelinfinite [1][j]=Double.NaN;
		}
		//pengisian sisa tabel hasil dengan 0
		for (i=2;i<=NKolEff;i++)
		{
			for (j=1;j<=NKolEff-1;j++)
			{
				this.tabelinfinite[i][j]=0;
			}
		}

		//proses
		for (i=NBrsEff;i>=1;i--)
		{
			C_kol=CariAngka1(i);
			//System.out.println("CKOL = "+C_kol);
			//System.out.println("M[i][NKolEff] = "+this.M[i][NKolEff]);
			this.tabelinfinite[1][C_kol]=this.M[i][NKolEff];
			for (j=(C_kol+1);j<=NKolEff-1;j++)
			{
				//System.out.println("J = "+j);
				if (this.M[i][j]!=0)
				{
					if(Double.isNaN(this.tabelinfinite[1][j]))
					{
						//System.out.println("MASUK!!!!!!!!!!!!");
						this.tabelinfinite[1+j][C_kol]=(-1*this.M[i][j]);
					}
					else
					{
						for(k=1;k<=NKolEff;k++)
						{
							this.tabelinfinite[k][C_kol]=this.tabelinfinite[k][C_kol]+(-1*this.M[i][j]*this.tabelinfinite[k][j]);
						}
					}
				}
			}
		}
	}

	public void TulisInfiniteSolutionKelayar()
	{
		int i,j,k;
		int sudahcetak;
		for(j=1;j<=NKolEff-1;j++)
		{
			sudahcetak=0;
			if (Double.isNaN(this.tabelinfinite[1][j]))
			{
				System.out.print("X"+j+" = ");
				System.out.println(this.tabelvariabel[j]);
			}
			else
			{
				if (this.tabelinfinite[1][j]!=0)
				{
					System.out.print("X"+j+" = "+this.tabelinfinite[1][j]);
					sudahcetak=1;
				}
				else
				{
					System.out.print("X"+j+" = ");
				}
				for(i=2;i<=NKolEff;i++)
				{
					if((this.tabelinfinite[i][j]>0)&&(sudahcetak==1))
					{
						System.out.print("+");
						System.out.print(this.tabelinfinite[i][j]);
						System.out.print(this.tabelvariabel[i-1]);
						sudahcetak=1;
					}
					else if(this.tabelinfinite[i][j]<0)
					{
						System.out.print(this.tabelinfinite[i][j]);
						System.out.print(this.tabelvariabel[i-1]);
						sudahcetak=1;
					}
					else if (this.tabelinfinite[i][j]>0)
					{
						System.out.print(this.tabelinfinite[i][j]);
						System.out.print(this.tabelvariabel[i-1]);
						sudahcetak=1;
					}
				}
				System.out.println();
			}
		}
	}
}
