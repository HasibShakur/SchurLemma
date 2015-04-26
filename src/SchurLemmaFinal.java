package com.test.HW;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SchurLemmaFinal {

	public static void main(String[] args) throws IOException {
		if(args == null || args.length < 2)
		{
			throw new IOException("Number of Balls and Bins are not specified");
		}
		int n = Integer.parseInt(args[0]);
		int k = Integer.parseInt(args[1]);
		int innerClauses = 0;
		for(int x=1;x<=n;x++)
		{
			innerClauses +=(n-x);
		}
		
		BufferedWriter outputWriter = new BufferedWriter(new FileWriter("schur"+n+"balls"+k+"boxes"+".cnf"));
		outputWriter.write("p cnf "+(n*k)+" "+(n+(n*(getFactorial(k)/(2*getFactorial(k-2))))+(k*innerClauses)));
		outputWriter.newLine();
		int val = 0;
		int[][] m = new int[n][k];
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<k;j++)
			{
				val +=1;
				m[i][j] = val;
				outputWriter.append(m[i][j]+" ");
			}
			outputWriter.append("0");
			outputWriter.newLine();
		}
		for(int i=0;i<n;i++)
		{
			printCombination(m[i],k,2,outputWriter);
		}
		for(int z=0;z<k;z++)
		{
			for(int x = 0; x<n;x++ )
			{
				for(int y=0; ((x+1)+(y+1)-1) < n;y++)
				{	 
					outputWriter.append("-"+m[x][z]+" "+"-"+m[y][z]+" "+"-"+m[(x+1)+(y+1)-1][z]+" "+"0");
					outputWriter.newLine();
				}
			}
		}
		outputWriter.flush();
		outputWriter.close();
	}
	public static void printCombination(int[] a,int n,int r,BufferedWriter outputWriter) throws IOException
	{
		int[] data = new int[r];
		combination(a,data,0,n-1,0,r,outputWriter);
	}
	private static void combination(int[] original,int[] temp,int start,int end,int index, int combinationSize,BufferedWriter outputWriter ) throws IOException
	{
		if(index == combinationSize)
		{
			for(int j=0;j<combinationSize;j++)
			{
				outputWriter.append("-"+temp[j]+" ");
			}
			outputWriter.append("0");
			outputWriter.newLine();
			return;
		}
		for(int i=start;i<=end && end-i+1 >= combinationSize - index;i++)
		{
			temp[index] = original[i];
			combination(original,temp,i+1,end,index+1,combinationSize,outputWriter);
		}
	}
	private static int getFactorial(int k)
	{
		if(k<=1)
			return 1;
		else
			return k*getFactorial(k-1);
			
	}

}
