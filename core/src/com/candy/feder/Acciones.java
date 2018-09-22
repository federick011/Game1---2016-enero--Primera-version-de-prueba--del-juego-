package com.candy.feder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class Acciones
{
	//==funciones para mover cosas con respecto a la camara :D=============//
		public static float movercosasx(float numero, Camera cam)
		{
			Vector3 touchPos = new Vector3();
		      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		      cam.unproject(touchPos);
		      
		      return touchPos.x-numero/2;
		}
		
		public static float movercosasy(float numero, Camera cam)
		{
			Vector3 touchPos = new Vector3();
		      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		      cam.unproject(touchPos);
		      
		      return touchPos.y-numero/2;
		}
		//===============================================
		
		public static float porcenposwitdth(float num)
		{
			float d;
			d=Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()*num/100);
			
			return d;
		}
		public static float porcenposheight(float num)
		{
			float d;
			d=Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()*num/100);
			
			return d;
		}
		
		public static TextureRegion cultivos(int a, int s, int d, int[][][] idcul, TextureRegion[][] regionsubparcela)
		{
			int x=0, y=0;
			if(idcul[a][s][d]<10)
			{
				if(idcul[a][s][d]==0)
				{
					x=0;
					y=0;
				}
				else if(idcul[a][s][d]==1)
				{
					x=0;
					y=1;
				}
				else if(idcul[a][s][d]==2)
				{
					x=0;
					y=2;
				}
				else if(idcul[a][s][d]==3)
				{
					x=0;
					y=3;
				}
				else if(idcul[a][s][d]==4)
				{
					x=0;
					y=4;
				}
				else if(idcul[a][s][d]==5)
				{
					x=1;
					y=0;
				}
				else if(idcul[a][s][d]==6)
				{
					x=1;
					y=1;
				}
				else if(idcul[a][s][d]==7)
				{
					x=1;
					y=2;
				}
				else if(idcul[a][s][d]==8)
				{
					x=1;
					y=3;
				}
				else if(idcul[a][s][d]==9)
				{
					x=1;
					y=4;
				}
			}
			else if(idcul[a][s][d]>9)
			{
				if(idcul[a][s][d]==10)
				{
					x=0;
					y=0;
				}
				else if(idcul[a][s][d]==11)
				{
					x=0;
					y=1;
				}
				else if(idcul[a][s][d]==12)
				{
					x=0;
					y=2;
				}
				else if(idcul[a][s][d]==13)
				{
					x=0;
					y=3;
				}
				else if(idcul[a][s][d]==14)
				{
					x=0;
					y=4;
				}
				else if(idcul[a][s][d]==15)
				{
					x=1;
					y=0;
				}
				else if(idcul[a][s][d]==16)
				{
					x=1;
					y=1;
				}
				else if(idcul[a][s][d]==17)
				{
					x=1;
					y=2;
				}
				else if(idcul[a][s][d]==18)
				{
					x=1;
					y=3;
				}
				else if(idcul[a][s][d]==19)
				{
					x=1;
					y=4;
				}
				else if(idcul[a][s][d]==19)
				{
					x=1;
					y=4;
				}
			}
			
	
			return regionsubparcela[x][y];
			
		}
		
		//esto es para seleccionar las construcciones grandes
		/*public static TextureRegion casas()
		{
			
		}*/
		
		//esto es para mostar el cultivo a sembrar
		public static TextureRegion cultivos1(TextureRegion[][] textura, int idsubparce)
		{
            int x=0, y=0;
			if(idsubparce==0)
			{
				x=0;
				y=0;
			}
			else if(idsubparce==1)
			{
				x=0;
				y=1;
			}
			else if(idsubparce==2)
			{
				x=0;
				y=2;
			}
			else if(idsubparce==3)
			{
				x=0;
				y=3;
			}
			else if(idsubparce==4)
			{
				x=0;
				y=4;
			}
			else if(idsubparce==5)
			{
				x=1;
				y=0;
			}
			else if(idsubparce==6)
			{
				x=1;
				y=1;
			}
			else if(idsubparce==7)
			{
				x=1;
				y=2;
			}
			else if(idsubparce==8)
			{
				x=1;
				y=3;
			}
			else if(idsubparce==9)
			{
				x=1;
				y=4;
			}
			return textura[x][y];
		}
		
		public static int ganancias(int a,int b,int c, int[][][] itemsubparce)
		{
			int ganancia=0;
			if(itemsubparce[a][b][c]==1)
			{
				ganancia=30;
			}
			else if(itemsubparce[a][b][c]==2)
			{
				ganancia=30;
			}
			else if(itemsubparce[a][b][c]==3)
			{
				ganancia=50;
			}
			else if(itemsubparce[a][b][c]==4)
			{
				ganancia=60;
			}
			else if(itemsubparce[a][b][c]==5)
			{
				ganancia=60;
			}
			else if(itemsubparce[a][b][c]==6)
			{
				ganancia=120;
			}
			else if(itemsubparce[a][b][c]==7)
			{
				ganancia=180;
			}
			else if(itemsubparce[a][b][c]==8)
			{
				ganancia=240;
			}
			else if(itemsubparce[a][b][c]==9)
			{
				ganancia=240;
			}
			return ganancia;
		}

}
