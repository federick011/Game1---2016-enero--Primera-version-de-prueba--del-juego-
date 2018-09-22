package com.candy.feder;

public class Subirnivel 
{
	public static int masnivel(int puntos, int nivel)
	{
		if(puntos>20 && puntos<39)
		{
			nivel=2;
		}
		else if(puntos>39 && puntos<79)
		{
			nivel=3;
		}
		else if(puntos>79 && puntos<159)
		{
			nivel=4;
		}
		else if(puntos>159 && puntos<319)
		{
			nivel=5;
		}
		else if(puntos>319 && puntos<639)
		{
			nivel=6;
		}
		else if(puntos>639 && puntos<1279)
		{
			nivel=7;
		}
		else if(puntos>1279 && puntos<2559)
		{
			nivel=8;
		}
		else if(puntos>2559 && puntos<5119)
		{
			nivel=9;
		}
		else if(puntos>5119 && puntos<10239)
		{
			nivel=10;
		}
		else if(puntos>-1 && puntos<19)
		{
			nivel=1;
		}
		
		return nivel;
	}

}
