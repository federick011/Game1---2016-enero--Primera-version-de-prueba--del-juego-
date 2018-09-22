package com.candy.feder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PixmapPackerIO.ImageFormat;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Farm extends ApplicationAdapter {
	
	Acciones acciones;
	Subirnivel subirnivel;
	Music[] musica;
	Sound[] sonido;
	Animation animacion;
	//bitmap
	BitmapFont font;
	
	Preferences preferencias;
	
	SpriteBatch batch;
	Texture fondos, botonera, rombo, terrenoparcela, itemsmenu, spritemenu, formasdeterreno, botoneramenu, terrenoconstruc, subparcevacia, gotas, gota, arbolada, moneda; /*terrenosparce*/
	TextureRegion animacionlol;
	TextureRegion[] indice;
	TextureRegion[][]  regionparcela, regionsubparce, regionmenu, regionfondos, regionbotomenu, regionconstruc, spritegotas, regionitems; /*regionparce*/;
	OrthographicCamera camara;
	
	Rectangle rectangleguia, rectangleboton1, recbarriba, recbabajo, recbizq, recbder, recbarramenu, aguaregadera;
	Rectangle[] recparcelas, recsubparcelas, recmenujuego;
	Rectangle[][] recitemsmenu, recbotomenu;
	int[][][] subparcelascomp, maduressubparcela, intervalosdetiempo;
	float[][][] tiempodecrecer;
	float tiempo=0f, tiempo1=0f, posxbotonera, posybotonera, posxpersonaje=0, posypersonaje=0;
	//variable que contiene la id de el 1 al 10 es sembrable
	int tipodecultivo=-1;
	//variables para objetos si estan en 1 entonces tendran un objeto seleccionado
	int vardemoledora=0, nivel, puntos, dinero=100, valoritem=0, cortadora=0, regadera=0;
	//0 juego, 1 menu, 2 pantalla de inicio
	int pantalla=0, subpantallamenu=0;
	float[] x;
	float[] y;
	float poscamarax=0, poscamaray=0, tiempodeldia=0;
	//pociones fijas sobre la camara
	Vector3 touchPosboto, touchPosmenu, touchPosbomenua, touchPosbomenub;
	
	@Override
	public void create () {
		
		touchPosboto = new Vector3();
		touchPosmenu =new Vector3();
		touchPosbomenua =new Vector3();
		touchPosbomenub =new Vector3();
		touchPosboto.set(600, 480, 0);
		
		//tabla
		
		//============
		recbotomenu=new Rectangle[2][1];
		recitemsmenu=new Rectangle[2][5];
		subparcelascomp=new int[15][2][2];
		maduressubparcela=new int[15][2][2];
		tiempodecrecer=new float[15][2][2];
		intervalosdetiempo=new int[15][2][2];
		recsubparcelas=new Rectangle[4];
		aguaregadera=new Rectangle();
		aguaregadera.setSize(100, 30);
		font=new BitmapFont(Gdx.files.internal("menu/letrasjuegos.fnt"));
		
		x=new float[9];
		y=new float[9];
		//puntos del mapa
		//x de izquierda a derecha
		x[0]=0;
		x[1]=200;
		x[2]=400;
		x[3]=600;
		x[4]=800;
		x[5]=1000;
		x[6]=1200;
		x[7]=1400;
		x[8]=1600;
		
		//y de arriba a abajo
		y[8]=960;
		y[7]=840;
		y[6]=720;
		y[5]=600;
		y[4]=480;
		y[3]=360;
		y[2]=240;
		y[1]=120;
		y[0]=0;
		
		
		batch = new SpriteBatch();
		
        camara = new OrthographicCamera();
		
		camara.setToOrtho(false, 800, 480);
		camara.position.x=880;
		camara.position.y=600;
		//sonidos y musica
		musica=new Music[1];
		sonido=new Sound[1];
		musica[0] = Gdx.audio.newMusic(Gdx.files.getFileHandle("musica/Adventures of small brave - The Mnk.mp3", FileType.Internal));
		sonido[0]=Gdx.audio.newSound(Gdx.files.getFileHandle("sonidos/212047__18hiltc__nerds-candy-in-a-box.wav", FileType.Internal));
		//========
		
		//texturas
		fondos = new Texture("terrenos/fondos.png");
		gotas=new Texture("terrenos/spritedegotas.png");
		gota=new Texture("terrenos/gota.png");
		moneda=new Texture("terrenos/monedas.png");
		subparcevacia=new Texture("terrenos/subparcevacia.png");
		arbolada=new Texture("terrenos/arbolada.png");;
		botonera=new Texture("menu/botonera.png");
		botoneramenu=new Texture("menu/botoneramenu.png");
		formasdeterreno=new Texture("terrenos/formasdeterreno.png");
		rombo=new Texture("terrenos/spriterombo.png");
		itemsmenu=new Texture("terrenos/itemsmenu.png");
		//terrenosparce=new Texture("terrenos/terrenosparcela.png");
		terrenoparcela=new Texture("terrenos/terrenodeparcela3.png");
		terrenoconstruc=new Texture("terrenos/construcdeparcela3.png");
		spritemenu=new Texture("menu/menuSheet.png");
		
		moneda.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		gotas.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		arbolada.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		itemsmenu.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		gota.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		spritemenu.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		botoneramenu.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		formasdeterreno.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		botonera.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		rombo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		terrenoparcela.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		//terrenosparce.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		terrenoconstruc.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		//regiones texturas
		spritegotas= TextureRegion.split(gotas, gotas.getWidth()/3, gotas.getHeight());
		regionfondos= TextureRegion.split(fondos, fondos.getWidth(), fondos.getHeight()/2);
		regionparcela = TextureRegion.split(rombo, rombo.getWidth()/2, rombo.getHeight());
		//regionparce = TextureRegion.split(terrenosparce, terrenosparce.getWidth()/4, terrenosparce.getHeight());
		regionmenu=TextureRegion.split(spritemenu, spritemenu.getWidth()/7, spritemenu.getHeight());
		regionbotomenu=TextureRegion.split(botoneramenu, botoneramenu.getWidth(), botoneramenu.getHeight()/2);
		//de momento son 10 cultivos del id 1 al 9 el id 0 es no cultivo osea no esta en el sparite
		regionsubparce = TextureRegion.split(terrenoparcela, terrenoparcela.getWidth()/5, terrenoparcela.getHeight()/2);
		regionitems = TextureRegion.split(itemsmenu, itemsmenu.getWidth()/5, itemsmenu.getHeight()/2);
		regionconstruc = TextureRegion.split(terrenoconstruc, terrenoconstruc.getWidth()/5, terrenoconstruc.getHeight()/2);
		
		//cargamos los datos guardados
		preferencias = Gdx.app.getPreferences("datos de juego");
		nivel=preferencias.getInteger("nivel");
		puntos=preferencias.getInteger("puntos");
		dinero=preferencias.getInteger("dinero");
		//cargamos la hora del dia
		tiempodeldia=preferencias.getFloat("tiempodeldia");
		if(preferencias.getInteger("primeravez")==0)
		{
			dinero=100;
			nivel=1;
			
		}
		//cargamos las sub parcelas compradas
		for(int p=0; p<15; p++)
		{
			//[0][0]=1, [0][1]=0, [1][0]=3, [1][1]=2
			for(int s=0; s<2; s++)
			{
				for(int d=0; d<2; d++)
				{
					subparcelascomp[p][s][d]= preferencias.getInteger(p+"subparcela"+s+d);
					maduressubparcela[p][s][d]= preferencias.getInteger(p+"maduressubparcela"+s+d);
					tiempodecrecer[p][s][d]=preferencias.getFloat(p+"tiempodecrecer"+s+d);
					intervalosdetiempo[p][s][d]=preferencias.getInteger(p+"intervalodetiempo"+s+d);
				}
			}
		}
		//dinero = preferencias.getInteger("dinero");
		
		//====las semillas si es valor 0 entonces no hay semillas
		
		//=============
		
		//====variables de posiciones===============//	
	
		//========
		rectangleguia=new Rectangle(0, 0, 25, 25);
		recbarramenu=new Rectangle(-100, -100, 800, 60);
		
		recbarriba=new Rectangle(touchPosboto.x+76, touchPosboto.y+78, 46, 47);
		recbabajo=new Rectangle(touchPosboto.x+76, touchPosboto.y+9, 46, 47);
		recbizq=new Rectangle(touchPosboto.x+10, touchPosboto.y+9, 46, 47);
		recbder=new Rectangle(touchPosboto.x+143, touchPosboto.y+9, 46, 47);
		recmenujuego=new Rectangle[7];
		for(int c=0; c<7; c++)
		{
			recmenujuego[c]=new Rectangle();
		}
		
		recparcelas=new Rectangle[15];
		recparcelas[0]= new Rectangle(x[1]+100, y[5]+60, 200, 120);
		recparcelas[1]= new Rectangle(x[2]+100, y[4]+60, 200, 120);
		recparcelas[2]= new Rectangle(x[3]+100, y[3]+60, 200, 120);
		recparcelas[3]= new Rectangle(x[2]+100, y[6]+60, 200, 120);
		recparcelas[4]= new Rectangle(x[3]+100, y[5]+60, 200, 120);
		recparcelas[5]= new Rectangle(x[4]+100, y[4]+60, 200, 120);
		recparcelas[6]= new Rectangle(x[3]+100, y[7]+60, 200, 120);
		recparcelas[7]= new Rectangle(x[4]+100, y[6]+60, 200, 120);
		recparcelas[8]= new Rectangle(x[5]+100, y[5]+60, 200, 120);
		recparcelas[9]= new Rectangle(x[4]+100, y[2]+60, 200, 120);
		recparcelas[10]= new Rectangle(x[5]+100, y[3]+60, 200, 120);
		recparcelas[11]= new Rectangle(x[6]+100, y[4]+60, 200, 120);
		recparcelas[12]= new Rectangle(x[0]+100, y[6]+60, 200, 120);
		recparcelas[13]= new Rectangle(x[1]+100, y[7]+60, 200, 120);
		recparcelas[14]= new Rectangle(x[2]+100, y[8]+60, 200, 120);
		//subparcelas id 0
		recsubparcelas[0]= new Rectangle(-100, -100, 100, 60);
		recsubparcelas[1]=new Rectangle(-100, -100, 100, 60);
		recsubparcelas[2]=new Rectangle(-100, -100, 100, 60);
		recsubparcelas[3]=new Rectangle(-100, -100, 100, 60);
		
		//=======rectangles del menu
		for(int c=0; c<7; c++)
		{
			recmenujuego[c].setSize(100, 60);
		}
		recmenujuego[5].setSize(100, 100);
		//rectangles botones menu
		recbotomenu[0][0]=new Rectangle(-100, 0, 60, 66.5f);
		recbotomenu[1][0]=new Rectangle(-100, 0, 60, 66.5f);
		//items menu rectangles
		for(int xf=0; xf<2; xf++)
		{
			for(int s=0; s<5; s++)
			{
				recitemsmenu[xf][s]=new Rectangle(x[0], y[7], regionitems[0][0].getRegionWidth(), regionitems[0][0].getRegionHeight());
			}
		}
		
		
		
		
	}
	
	

	@Override
	public void render () 
	{
		
		//System.out.println(dinero);
		//musica[0].play();
		Gdx.gl.glClearColor(1026, 255, 255, 000);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		switch (pantalla) {
		case 0:
			
			if(tiempodeldia>1800.1f)
			{
				tiempodeldia=0;
			}
			tiempodeldia+=Gdx.graphics.getDeltaTime();
			//System.out.println(puntos+" "+dinero+" hora "+tiempodeldia+" nivel "+nivel);
			texturafija();
			//camara
			batch.setProjectionMatrix(camara.combined);
			camara.update();
			
			//el rectangleguia :v
			moverguia();
			//mover camara
			accionesoverlaps();
			
			/*asi se guarda informacion
			//preferencias.putString("name", "Federick");
			//preferencias.flush();
			*/
			batch.begin();
			batch.draw(regionfondos[0][0], 0, 0);
			batch.draw(regionfondos[0][0], 800, 0);
			batch.draw(regionfondos[0][0], 0, 480);
			batch.draw(regionfondos[0][0], 0, 960);
			batch.draw(regionfondos[0][0], 800, 480);
			batch.draw(regionfondos[0][0], 800, 960);
			
			batch.draw(arbolada, 0, 1150);
			parcelas();
			
			batch.draw(arbolada, 1200, 50);
			batch.draw(arbolada, 800, -150);
			batch.draw(arbolada, -300, 250);
			batch.draw(arbolada, 0, 0);
			
			
			//batch.draw(fondo, acciones.movercosasx(25f, camara), acciones.movercosasy(25f, camara), 25, 25);
			batch.draw(regionmenu[0][0], touchPosmenu.x, touchPosmenu.y);
			batch.draw(regionmenu[0][2], touchPosmenu.x+150, touchPosmenu.y);
			batch.draw(regionmenu[0][3], touchPosmenu.x+300, touchPosmenu.y);
			batch.draw(regionmenu[0][6], touchPosmenu.x+450, touchPosmenu.y);
			String dine=Integer.toString(dinero);
			batch.draw(moneda, touchPosmenu.x+560, touchPosmenu.y+20);
			font.draw(batch, " "+dine, touchPosmenu.x+590, touchPosmenu.y+50);
			dine=Integer.toString(nivel);
			font.draw(batch, "Level "+dine, touchPosmenu.x+600, touchPosmenu.y+20);
			dine=Integer.toString(puntos);
			font.draw(batch, "XP "+dine, touchPosmenu.x+600, touchPosmenu.y-10);
			batch.draw(acciones.cultivos1(regionitems, tipodecultivo), touchPosmenu.x, touchPosmenu.y-420, 100, 100);
			batch.draw(botonera, touchPosboto.x, touchPosboto.y);
			batch.end();
			//posxpersonaje+=10;
			//posypersonaje+=6;
			break;

		case 1:
			switch (subpantallamenu) {
			case 0:
				
				texturafija();
				//camara
				batch.setProjectionMatrix(camara.combined);
				camara.update();
				accionesoverlaps();
				//el rectangleguia :v
				moverguia();
				batch.begin();
				batch.draw(regionfondos[1][0], 0, 0);
				itemsmenu(regionitems);
				batch.draw(regionmenu[0][1], touchPosmenu.x, touchPosmenu.y);
				batch.draw(regionmenu[0][4], touchPosmenu.x+150, touchPosmenu.y);
				batch.draw(regionmenu[0][5], touchPosmenu.x+300, touchPosmenu.y);
				batch.draw(regionbotomenu[0][0], touchPosbomenua.x, touchPosbomenua.y);
				batch.draw(regionbotomenu[1][0], touchPosbomenub.x, touchPosbomenub.y);
				batch.end();
				break;

			case 1:
				texturafija();
				//camara
				batch.setProjectionMatrix(camara.combined);
				camara.update();
				accionesoverlaps();
				//el rectangleguia :v
				moverguia();
				batch.begin();
				batch.draw(regionfondos[1][0], 0, 0);
				itemsmenu(regionconstruc);
				//itemsmenug(regionparce);
				batch.draw(regionmenu[0][1], touchPosmenu.x, touchPosmenu.y);
				batch.draw(regionmenu[0][4], touchPosmenu.x+150, touchPosmenu.y);
				batch.draw(regionmenu[0][5], touchPosmenu.x+300, touchPosmenu.y);
				batch.draw(regionbotomenu[0][0], touchPosbomenua.x, touchPosbomenua.y);
				batch.draw(regionbotomenu[1][0], touchPosbomenub.x, touchPosbomenub.y);
				batch.end();
				
				break;
			}
			
			break;
		}
		
		
	}
	
	
	
	public void Terrenoparcela(int nx, int ny, int idpar)
	{
		//[0][0]=1, [0][1]=0, [1][0]=3, [1][1]=2
		//subparcelascomp[p][s][d] guarda el valor del id del cultivo
		//id 0
		if(subparcelascomp[idpar][0][1]>(-1) && subparcelascomp[idpar][0][1]<10)
		{
			
			if(maduressubparcela[idpar][0][1]==0 && subparcelascomp[idpar][0][1]>0)
			{
				recsubparcelas[0].setPosition((x[nx]+100)+50, (y[ny]+120)+30);
				
				batch.draw(subparcevacia, x[nx]+100, y[ny]+120);
				//font.draw(batch, "0", x[nx]+200, y[ny]+180);
				System.out.println(tiempodecrecer[idpar][0][1]);
				
				if(tiempodecrecer[idpar][0][1]<60.0f)
				{
					if(intervalosdetiempo[idpar][0][1]==0)
					{
						tiempodecrecer[idpar][0][1]+=Gdx.graphics.getDeltaTime();
					}
	
				}
				else if(tiempodecrecer[idpar][0][1]>59.9f && intervalosdetiempo[idpar][0][1]==0)
				{
					//font.draw(batch, "regar agua :v", x[nx]+200, y[ny]+180);
					batch.draw(gota, x[nx]+200, y[ny]+180);
				}
				else if(tiempodecrecer[idpar][0][1]>60.0f && intervalosdetiempo[idpar][0][1]==1)
				{
					
						tiempodecrecer[idpar][0][1]+=Gdx.graphics.getDeltaTime();
					
					
				}

			}
			else
			{
				//si la subparcela es igual a cero 0
				batch.draw(acciones.cultivos(idpar, 0, 1, subparcelascomp, regionsubparce), x[nx]+100, y[ny]+120);
				recsubparcelas[0].setPosition((x[nx]+100)+50, (y[ny]+120)+30);
				//batch.draw(spritemenu, (x[nx]+100)+50, (y[ny]+120)+30, 20, 150);
				//font.draw(batch, "0", x[nx]+200, y[ny]+180);	
			}
			if(tiempodecrecer[idpar][0][1]>120.0f && tiempodecrecer[idpar][0][1]<120.1f)
			{
				maduressubparcela[idpar][0][1]=1;
				preferencias.putInteger(idpar+"maduressubparcela"+0+1, maduressubparcela[idpar][0][1]);
				preferencias.flush();
				tiempodecrecer[idpar][0][1]=0;
				preferencias.putFloat(idpar+"tiempodecrecer"+0+1, tiempodecrecer[idpar][0][1]);
				preferencias.flush();
				
			}
			
		}
		else if(subparcelascomp[idpar][0][1]>9)
		{
			batch.draw(acciones.cultivos(idpar, 0, 1, subparcelascomp, regionconstruc), x[nx]+100, y[ny]+120);
			recsubparcelas[0].setPosition((x[nx]+100)+50, (y[ny]+120)+30);
		}
		else
		{
			recsubparcelas[0].setPosition((x[nx]+100)+50, (y[ny]+120)+30);
			
		}
		if(subparcelascomp[idpar][0][1]<1 && tipodecultivo>0)
		{
			//esto muestra los lugares donde se puede construir o sembrar
			batch.draw(formasdeterreno, x[nx]+100, y[ny]+120);
		}
		
		
		//==============
		
		//id 1
		if(subparcelascomp[idpar][0][0]>(-1) && subparcelascomp[idpar][0][0]<10)
		{
			
			if(maduressubparcela[idpar][0][0]==0 && subparcelascomp[idpar][0][0]>0)
			{
				recsubparcelas[1].setPosition(x[nx]+50, (y[ny]+60)+30);
				tiempodecrecer[idpar][0][0]+=Gdx.graphics.getDeltaTime();
				//System.out.println(intervalosdetiempo[idpar][0][0]);
				//System.out.println(tiempodecrecer[idpar][0][0]);
				batch.draw(subparcevacia, x[nx], y[ny]+60);
				//font.draw(batch, "1", x[nx]+100, y[ny]+120);
				
				if(tiempodecrecer[idpar][0][0]<60.0f)
				{
					if(intervalosdetiempo[idpar][0][0]==0)
					{
						tiempodecrecer[idpar][0][0]+=Gdx.graphics.getDeltaTime();
					}
					
					
					
				}
				else if(tiempodecrecer[idpar][0][0]>59.9f && intervalosdetiempo[idpar][0][0]==0)
				{
					//font.draw(batch, "regar agua :v", x[nx]+100, y[ny]+120);
					batch.draw(gota, x[nx]+100, y[ny]+120);
				}
				else if(tiempodecrecer[idpar][0][0]>60.0f && intervalosdetiempo[idpar][0][0]==1)
				{
					
						tiempodecrecer[idpar][0][0]+=Gdx.graphics.getDeltaTime();
					
					
				}
			}
			else
			{
				batch.draw(acciones.cultivos(idpar, 0, 0, subparcelascomp, regionsubparce), x[nx], y[ny]+60);
				recsubparcelas[1].setPosition(x[nx]+50, (y[ny]+60)+30);
				//batch.draw(spritemenu, x[nx]+50, (y[ny]+60)+30, 20, 150);
				//font.draw(batch, "1", x[nx]+100, y[ny]+120);
			}
			if(tiempodecrecer[idpar][0][0]>120.0f && tiempodecrecer[idpar][0][0]<120.1f)
			{
				maduressubparcela[idpar][0][0]=1;
				preferencias.putInteger(idpar+"maduressubparcela"+0+0, maduressubparcela[idpar][0][0]);
				preferencias.flush();
				tiempodecrecer[idpar][0][0]=0;
				preferencias.putFloat(idpar+"tiempodecrecer"+0+0, tiempodecrecer[idpar][0][0]);
				preferencias.flush();
				
			}
		}
		else if(subparcelascomp[idpar][0][0]>9)
		{
			batch.draw(acciones.cultivos(idpar, 0, 0, subparcelascomp, regionconstruc), x[nx], y[ny]+60);
			recsubparcelas[1].setPosition(x[nx]+50, (y[ny]+60)+30);
		}
		else
		{
			recsubparcelas[1].setPosition(x[nx]+50, (y[ny]+60)+30);
		}
		if(subparcelascomp[idpar][0][0]<1 && tipodecultivo>0)
		{
			batch.draw(formasdeterreno, x[nx], y[ny]+60);
		}
		
		//===============
		
		//id 2
		if(subparcelascomp[idpar][1][1]>(-1) && subparcelascomp[idpar][1][1]<10)
		{
			
			if(maduressubparcela[idpar][1][1]==0 && subparcelascomp[idpar][1][1]>0)
			{
				recsubparcelas[2].setPosition((x[nx]+200)+50, (y[ny]+60)+30);
				tiempodecrecer[idpar][1][1]+=Gdx.graphics.getDeltaTime();
				System.out.println(tiempodecrecer[idpar][1][1]);
				batch.draw(subparcevacia, (x[nx]+100)+100, y[ny]+60);
				//font.draw(batch, "2", (x[nx]+100)+200, y[ny]+120);
				
				if(tiempodecrecer[idpar][1][1]<60.0f)
				{
					if(intervalosdetiempo[idpar][1][1]==0)
					{
						tiempodecrecer[idpar][1][1]+=Gdx.graphics.getDeltaTime();
					}
					
					
					
				}
				else if(tiempodecrecer[idpar][1][1]>59.9f && intervalosdetiempo[idpar][1][1]==0)
				{
					//font.draw(batch, "regar agua :v", (x[nx]+100)+200, y[ny]+120);
					batch.draw(gota, (x[nx]+100)+200, y[ny]+120);
				}
				else if(tiempodecrecer[idpar][1][1]>60.0f && intervalosdetiempo[idpar][1][1]==1)
				{
					
						tiempodecrecer[idpar][1][1]+=Gdx.graphics.getDeltaTime();
					
					
				}
			}
			else
			{
				batch.draw(acciones.cultivos(idpar, 1, 1, subparcelascomp, regionsubparce), (x[nx]+100)+100, y[ny]+60);
				recsubparcelas[2].setPosition((x[nx]+200)+50, (y[ny]+60)+30);
				//batch.draw(spritemenu, (x[nx]+200)+50, (y[ny]+60)+30, 20, 150);
				//font.draw(batch, "2", (x[nx]+100)+200, y[ny]+120);
			}
			if(tiempodecrecer[idpar][1][1]>120.0f && tiempodecrecer[idpar][1][1]<120.1f)
			{
				maduressubparcela[idpar][1][1]=1;
				preferencias.putInteger(idpar+"maduressubparcela"+1+1, maduressubparcela[idpar][1][1]);
				preferencias.flush();
				tiempodecrecer[idpar][1][1]=0;
				preferencias.putFloat(idpar+"tiempodecrecer"+1+1, tiempodecrecer[idpar][1][1]);
				preferencias.flush();
				
			}
		}
		else if(subparcelascomp[idpar][1][1]>9)
		{
			batch.draw(acciones.cultivos(idpar, 1, 1, subparcelascomp, regionconstruc), (x[nx]+100)+100, y[ny]+60);
			recsubparcelas[2].setPosition((x[nx]+200)+50, (y[ny]+60)+30);
		}
		else
		{
				recsubparcelas[2].setPosition((x[nx]+200)+50, (y[ny]+60)+30);
		}
		if(subparcelascomp[idpar][1][1]<1 && tipodecultivo>0)
		{
			batch.draw(formasdeterreno, (x[nx]+100)+100, y[ny]+60);
		}
		
		//===============
		
		//id 3
		if(subparcelascomp[idpar][1][0]>(-1) && subparcelascomp[idpar][1][0]<10)
		{
			
			if(maduressubparcela[idpar][1][0]==0 && subparcelascomp[idpar][1][0]>0)
			{
				recsubparcelas[3].setPosition((x[nx]+100)+50, y[ny]+30);
				tiempodecrecer[idpar][1][0]+=Gdx.graphics.getDeltaTime();
				//System.out.println(":v 3");
				batch.draw(subparcevacia, x[nx]+100, y[ny]);
				//font.draw(batch, "2", x[nx]+200, y[ny]+60);
				
				if(tiempodecrecer[idpar][1][0]<60.0f)
				{
					if(intervalosdetiempo[idpar][1][0]==0)
					{
						tiempodecrecer[idpar][1][0]+=Gdx.graphics.getDeltaTime();
					}
					
					
					
				}
				else if(tiempodecrecer[idpar][1][0]>59.9f && intervalosdetiempo[idpar][1][0]==0)
				{
					//font.draw(batch, "regar agua :v", x[nx]+200, y[ny]+60);
					batch.draw(gota, x[nx]+200, y[ny]+60);
				}
				else if(tiempodecrecer[idpar][1][0]>60.0f && intervalosdetiempo[idpar][1][0]==1)
				{
					
						tiempodecrecer[idpar][1][0]+=Gdx.graphics.getDeltaTime();
					
					
				}
			}
			else
			{
				batch.draw(acciones.cultivos(idpar, 1, 0, subparcelascomp, regionsubparce), x[nx]+100, y[ny]);
				recsubparcelas[3].setPosition((x[nx]+100)+50, y[ny]+30);
				//batch.draw(spritemenu, (x[nx]+100)+50, y[ny]+30, 20, 150);
				//font.draw(batch, "3", x[nx]+200, y[ny]+60);
			}
			if(tiempodecrecer[idpar][1][0]>120.0f && tiempodecrecer[idpar][1][0]<120.1f)
			{
				maduressubparcela[idpar][1][0]=1;
				preferencias.putInteger(idpar+"maduressubparcela"+1+0, maduressubparcela[idpar][1][0]);
				preferencias.flush();
				tiempodecrecer[idpar][1][0]=0;
				preferencias.putFloat(idpar+"tiempodecrecer"+1+0, tiempodecrecer[idpar][1][0]);
				preferencias.flush();
				
			}
		}
		else if(subparcelascomp[idpar][1][0]>9)
		{
			batch.draw(acciones.cultivos(idpar, 1, 0, subparcelascomp, regionconstruc), x[nx]+100, y[ny]);
			recsubparcelas[3].setPosition((x[nx]+100)+50, y[ny]+30);
		}
		else
		{
		 	recsubparcelas[3].setPosition((x[nx]+100)+50, y[ny]+30);
			
		}
		if(subparcelascomp[idpar][1][0]<1 && tipodecultivo>0)
		{
			batch.draw(formasdeterreno, x[nx]+100, y[ny]);
		}
		if(!rectangleguia.overlaps(recbarramenu))
		{
			sembrarsubpar();
		}
		
		//=====================
		
	}
	//aqui agrego mas parcelas
	public void parcelas()
	{

		//centro izquierda id 14
		//batch.draw(regionparcela[0][0], x[2], y[8]);
		Terrenoparcela(2, 8, 14);
		//font.draw(batch, "14", x[2]+200, y[8]+120);
		//centro izquierda id 13
		//batch.draw(regionparcela[0][1], x[1], y[7]);
		Terrenoparcela(1, 7, 13);
		//font.draw(batch, "13", x[1]+200, y[7]+120);
        //centro izquierda id 12
        //batch.draw(regionparcela[0][1], x[0], y[6]);
        Terrenoparcela(0, 6, 12);
        //font.draw(batch, "12", x[0]+200, y[6]+120);

		//centro ababjo id 6
		//batch.draw(regionparcela[0][1], x[3], y[7]);
		Terrenoparcela(3, 7, 6);
		//font.draw(batch, "6", x[3]+200, y[7]+120);	
		//arriba id 7
		//batch.draw(regionparcela[0][1], x[4], y[6]);
		Terrenoparcela(4, 6, 7);
		//font.draw(batch, "7", x[4]+200, y[6]+120);
		//centro derecha id 8
		//batch.draw(regionparcela[0][1], x[5], y[5]);
		Terrenoparcela(5, 5, 8);
		//font.draw(batch, "8", x[5]+200, y[5]+120);
		
		
		//arriba id 3
		//batch.draw(regionparcela[0][1], x[2], y[6]);
		Terrenoparcela(2, 6, 3);
		//font.draw(batch, "3", x[2]+200, y[6]+120);
		 //centro centro id 4
		//batch.draw(regionparcela[0][1], x[3], y[5]);
		Terrenoparcela(3, 5, 4);
		//font.draw(batch, "4", x[3]+200, y[5]+120);
	  //ababajo id 5
		//batch.draw(regionparcela[0][1], x[4], y[4]);
		Terrenoparcela(4, 4, 5);
		//font.draw(batch, "5", x[4]+200, y[4]+120);
		
		//centro izquierda id 0
		//batch.draw(regionparcela[0][1], x[1], y[5]);
		Terrenoparcela(1, 5, 0);
		//font.draw(batch, "0", x[1]+200, y[5]+120);
		//ababajo id 1
		//batch.draw(regionparcela[0][1], x[2], y[4]);
		Terrenoparcela(2, 4, 1);
		//font.draw(batch, "1", x[2]+200, y[4]+120);
		 //centro arriba id 2
		//batch.draw(regionparcela[0][1], x[3], y[3]);
		Terrenoparcela(3, 3, 2);
		//font.draw(batch, "2", x[3]+200, y[3]+120);
		
		
		//parcela id 11
		//batch.draw(regionparcela[0][1], x[6], y[4]);
		Terrenoparcela(6, 4, 11);
		//font.draw(batch, "11", x[6]+200, y[4]+120);
		//parcela id 10
		 //batch.draw(regionparcela[0][1], x[5], y[3]);
		Terrenoparcela(5, 3, 10);
		//font.draw(batch, "10", x[5]+200, y[3]+120);
		//parcela is 9
		//batch.draw(regionparcela[0][1], x[4], y[2]);
		Terrenoparcela(4, 2, 9);
		//font.draw(batch, "9", x[4]+200, y[2]+120);

			
	}
	
	
	public void sembrarsubpar()
	{
		if(Gdx.input.isTouched() && regadera==1)
		if(!rectangleguia.overlaps(recbizq) && !rectangleguia.overlaps(recbder))
		{
			if(!rectangleguia.overlaps(recbarriba) && !rectangleguia.overlaps(recbabajo))
			{
				for(int c=0; c<15; c++)
				{
		
					//[0][0]=1, [0][1]=0, [1][0]=3, [1][1]=2

					//System.out.println("regando agua");
					indice=new TextureRegion[3];
					for(int cs=0; cs<3; cs++)
					{
						indice[cs]=spritegotas[0][cs];
					}
					
					animacion = new Animation(10.0f, indice);
					tiempo1+=Gdx.graphics.getDeltaTime();
					
					animacionlol= animacion.getKeyFrame(tiempo1, true);
					
					
					batch.draw(animacionlol, acciones.movercosasx(100f, camara), acciones.movercosasy(250f, camara));
					batch.draw(animacionlol, acciones.movercosasx(100f, camara), acciones.movercosasy(10f, camara)-120, 100, 30);
					aguaregadera.setPosition(acciones.movercosasx(100f, camara), acciones.movercosasy(10f, camara)-120);
					if(aguaregadera.overlaps(recparcelas[c]))
					{
						//[0][0]=1, [0][1]=0, [1][0]=3, [1][1]=2
						if(aguaregadera.overlaps(recsubparcelas[0]))
						{
							
							if(subparcelascomp[c][0][1]>0 && vardemoledora==0)
							{
								//intervalosdetiempo[c][0][1]++;
								if(tiempodecrecer[c][0][1]>59.9f && intervalosdetiempo[c][0][1]==0)
								{
									intervalosdetiempo[c][0][1]=1;
								}
								
								System.out.println("regando subparcela 0 en parcela "+c);
							}
						}
						else if(aguaregadera.overlaps(recsubparcelas[1]))
						{
							if(subparcelascomp[c][0][0]>0 && vardemoledora==0)
							{
								if(tiempodecrecer[c][0][0]>59.9f && intervalosdetiempo[c][0][0]==0)
								{
									intervalosdetiempo[c][0][0]=1;
								}
								//intervalosdetiempo[c][0][0]++;
								//System.out.println("regando subparcela 1 en parcela "+c);
							}
						}
						else if(aguaregadera.overlaps(recsubparcelas[2]))
						{
							
							if(subparcelascomp[c][1][1]>0 && vardemoledora==0)
							{
								if(tiempodecrecer[c][1][1]>59.9f && intervalosdetiempo[c][1][1]==0)
								{
									intervalosdetiempo[c][1][1]=1;
								}
								//intervalosdetiempo[c][1][1]++;
								//System.out.println("regando subparcela 2 en parcela "+c);
							}
						}
						else if(aguaregadera.overlaps(recsubparcelas[3]))
						{
							
							if(subparcelascomp[c][1][0]>0 && vardemoledora==0)
							{
								if(tiempodecrecer[c][1][0]>59.9f && intervalosdetiempo[c][1][0]==0)
								{
									intervalosdetiempo[c][1][0]=1;
								}
								//intervalosdetiempo[c][1][0]++;
								//System.out.println("regando subparcela 3 en parcela "+c);
							}
						}
					}
					
								
				}
			}
		}
		//reconoce si tocas las sub parcelas con su parcela padre
		if(Gdx.input.justTouched())
		{
			if(!rectangleguia.overlaps(recbizq) && !rectangleguia.overlaps(recbder))
			{
				if(!rectangleguia.overlaps(recbarriba) && !rectangleguia.overlaps(recbabajo))
				{
					for(int c=0; c<15; c++)
					{
						if(rectangleguia.overlaps(recparcelas[c]))
						{
							//[0][0]=1, [0][1]=0, [1][0]=3, [1][1]=2
							if(rectangleguia.overlaps(recsubparcelas[0]))
							{
								
								if(subparcelascomp[c][0][1]<1 && vardemoledora==0)
								{
									if(tipodecultivo>0 && dinero>valoritem-1)
									{
										
										subparcelascomp[c][0][1]=tipodecultivo;
										preferencias.putInteger(c+"subparcela"+0+1, tipodecultivo);
										preferencias.flush();
										dinero-=valoritem;
							
										
									}
									
								}
								if(vardemoledora==1 && subparcelascomp[c][0][1]>-1)
								{
									
									subparcelascomp[c][0][1]=0;
									preferencias.putInteger(c+"subparcela"+0+1, 0);
									preferencias.flush();
									intervalosdetiempo[c][0][1]=0;
									preferencias.putInteger(c+"intervalodetiempo"+0+1, intervalosdetiempo[c][0][1]);
									preferencias.flush();
									tiempodecrecer[c][0][1]=0;
									maduressubparcela[c][0][1]=0;
									preferencias.putInteger(c+"maduressubparcela"+0+1, maduressubparcela[c][0][1]);
									preferencias.flush();
									
								}
								if(maduressubparcela[c][0][1]==1 && cortadora==1)
								{
									sonido[0].play();
									intervalosdetiempo[c][0][1]=0;
									preferencias.putInteger(c+"intervalodetiempo"+0+1, intervalosdetiempo[c][0][1]);
									preferencias.flush();
									dinero+=acciones.ganancias(c, 0, 1, subparcelascomp);
									subparcelascomp[c][0][1]=0;
									preferencias.putInteger(c+"subparcela"+0+1, 0);
									preferencias.flush();
									puntos++;
									nivel=subirnivel.masnivel(puntos, nivel);
									maduressubparcela[c][0][1]=0;
									preferencias.putInteger(c+"maduressubparcela"+0+1, maduressubparcela[c][0][1]);
									preferencias.flush();
									
								}
								
								
							}
							else if(rectangleguia.overlaps(recsubparcelas[1]))
							{
								if(subparcelascomp[c][0][0]<1 && vardemoledora==0)
								{
									if(tipodecultivo>0 && dinero>valoritem-1)
									{
										
										subparcelascomp[c][0][0]=tipodecultivo;
										preferencias.putInteger(c+"subparcela"+0+0, tipodecultivo);
										preferencias.flush();
										dinero-=valoritem;
										
									}
									
								}
								if(vardemoledora==1 && subparcelascomp[c][0][0]>-1)
								{
									
									subparcelascomp[c][0][0]=0;
									preferencias.putInteger(c+"subparcela"+0+0, 0);
									preferencias.flush();
									intervalosdetiempo[c][0][0]=0;
									preferencias.putInteger(c+"intervalodetiempo"+0+0, intervalosdetiempo[c][0][0]);
									preferencias.flush();
									tiempodecrecer[c][0][0]=0;
									maduressubparcela[c][0][0]=0;
									preferencias.putInteger(c+"maduressubparcela"+0+0, maduressubparcela[c][0][0]);
									preferencias.flush();
								}
								if(maduressubparcela[c][0][0]==1 && cortadora==1)
								{
									sonido[0].play();
									intervalosdetiempo[c][0][0]=0;
									preferencias.putInteger(c+"intervalodetiempo"+0+0, intervalosdetiempo[c][0][0]);
									preferencias.flush();
									dinero+=acciones.ganancias(c, 0, 0, subparcelascomp);
									subparcelascomp[c][0][0]=0;
									preferencias.putInteger(c+"subparcela"+0+0, 0);
									preferencias.flush();
									puntos++;
									nivel=subirnivel.masnivel(puntos, nivel);
									maduressubparcela[c][0][0]=0;
									preferencias.putInteger(c+"maduressubparcela"+0+0, maduressubparcela[c][0][0]);
									preferencias.flush();
									
								}
								
							}
							else if(rectangleguia.overlaps(recsubparcelas[2]))
							{
								if(subparcelascomp[c][1][1]<1 && vardemoledora==0)
								{
									if(tipodecultivo>0 && dinero>valoritem-1)
									{
										
										subparcelascomp[c][1][1]=tipodecultivo;
										preferencias.putInteger(c+"subparcela"+1+1, tipodecultivo);
										preferencias.flush();
										dinero-=valoritem;
										
										
									}
									
								}
								if(vardemoledora==1 && subparcelascomp[c][1][1]>-1)
								{
									
									subparcelascomp[c][1][1]=0;
									preferencias.putInteger(c+"subparcela"+1+1, 0);
									preferencias.flush();
									intervalosdetiempo[c][1][1]=0;
									preferencias.putInteger(c+"intervalodetiempo"+1+1, intervalosdetiempo[c][1][1]);
									preferencias.flush();
									tiempodecrecer[c][1][1]=0;
									maduressubparcela[c][1][1]=0;
									preferencias.putInteger(c+"maduressubparcela"+1+1, maduressubparcela[c][1][1]);
									preferencias.flush();
								}
								if(maduressubparcela[c][1][1]==1 && cortadora==1)
								{
									sonido[0].play();
									intervalosdetiempo[c][1][1]=0;
									preferencias.putInteger(c+"intervalodetiempo"+1+1, intervalosdetiempo[c][1][1]);
									preferencias.flush();
									dinero+=acciones.ganancias(c, 1, 1, subparcelascomp);
									subparcelascomp[c][1][1]=0;
									preferencias.putInteger(c+"subparcela"+1+1, 0);
									preferencias.flush();
									puntos++;
									nivel=subirnivel.masnivel(puntos, nivel);
									maduressubparcela[c][1][1]=0;
									preferencias.putInteger(c+"maduressubparcela"+1+1, maduressubparcela[c][1][1]);
									preferencias.flush();
									
								}
							}
							else if(rectangleguia.overlaps(recsubparcelas[3]))
							{
								if(subparcelascomp[c][1][0]<1 && vardemoledora==0)
								{
									if(tipodecultivo>0 && dinero>valoritem-1)
									{
										
										subparcelascomp[c][1][0]=tipodecultivo;
										preferencias.putInteger(c+"subparcela"+1+0, tipodecultivo);
										preferencias.flush();
										dinero-=valoritem;
										
										
									}
									
								}
								if(vardemoledora==1 && subparcelascomp[c][1][0]>-1)
								{
									
									subparcelascomp[c][1][0]=0;
									preferencias.putInteger(c+"subparcela"+1+0, 0);
									preferencias.flush();
									intervalosdetiempo[c][1][0]=0;
									preferencias.putInteger(c+"intervalodetiempo"+1+0, intervalosdetiempo[c][1][0]);
									preferencias.flush();
									tiempodecrecer[c][1][0]=0;
									maduressubparcela[c][1][0]=0;
									preferencias.putInteger(c+"maduressubparcela"+1+0, maduressubparcela[c][1][0]);
									preferencias.flush();
								}
								if(maduressubparcela[c][1][0]==1 && cortadora==1)
								{
									sonido[0].play();
									intervalosdetiempo[c][1][0]=0;
									preferencias.putInteger(c+"intervalodetiempo"+1+0, intervalosdetiempo[c][1][0]);
									preferencias.flush();
									dinero+=acciones.ganancias(c, 1, 0, subparcelascomp);
									subparcelascomp[c][1][0]=0;
									preferencias.putInteger(c+"subparcela"+1+0, 0);
									preferencias.flush();
									puntos++;
									nivel=subirnivel.masnivel(puntos, nivel);
									maduressubparcela[c][1][0]=0;
									preferencias.putInteger(c+"maduressubparcela"+1+0, maduressubparcela[c][1][0]);
									preferencias.flush();
									
								}
								
							}
						}
					}
					
				}
			}
		}
	}
	
	
	
	public void texturafija()
	{
		//esto es para que las cosas queden fijas en la camara
		touchPosboto.set(acciones.porcenposwitdth(25), acciones.porcenposheight(0.2f), 0);
		camara.unproject(touchPosboto);
		touchPosmenu.set(acciones.porcenposwitdth(100), acciones.porcenposheight(87.5f), 0);
		camara.unproject(touchPosmenu);
		touchPosbomenua.set(acciones.porcenposwitdth(7.5f), acciones.porcenposheight(85.5f), 0);
		camara.unproject(touchPosbomenua);
		touchPosbomenub.set(acciones.porcenposwitdth(7.5f), acciones.porcenposheight(1.5f), 0);
		camara.unproject(touchPosbomenub);
		
		//pos botones
		recbarriba.setPosition(touchPosboto.x+76, touchPosboto.y+78);
		recbabajo.setPosition(touchPosboto.x+76, touchPosboto.y+9);
		recbizq.setPosition(touchPosboto.x+10, touchPosboto.y+9);
		recbder.setPosition(touchPosboto.x+143, touchPosboto.y+9);
		
		recbotomenu[0][0].setPosition(touchPosbomenua.x, touchPosbomenua.y);
		recbotomenu[1][0].setPosition(touchPosbomenub.x, touchPosbomenub.y);
		
		recbarramenu.setPosition(touchPosmenu.x, touchPosmenu.y);
		
	}
		//==========mover el rectangle
		public void moverguia()
		{
			if(Gdx.input.isTouched())
			{
				rectangleguia.x=acciones.movercosasx(25f, camara);
				rectangleguia.y=acciones.movercosasy(25f, camara);
				
				
			}
			else
			{
				rectangleguia.x=-40;
				rectangleguia.y=-40;
			}
			
			
			
			//==============menu
			
			//rectangle boton menu y en pantalla menu es el boton de regreso
			recmenujuego[0].setPosition(touchPosmenu.x, touchPosmenu.y);
			//rectangle boton hoz
			recmenujuego[1].setPosition(touchPosmenu.x+150, touchPosmenu.y);
			//rec ciltvivos sub pantalla menu
			recmenujuego[2].setPosition(touchPosmenu.x+150, touchPosmenu.y);
			//rec construccion sub pantalla menu
			recmenujuego[3].setPosition(touchPosmenu.x+300, touchPosmenu.y);
			//rectangle demoledora
			recmenujuego[4].setPosition(touchPosmenu.x+300, touchPosmenu.y);
			//rectangle tipo de cultivo seleccionado a sembrar
			recmenujuego[5].setPosition(touchPosmenu.x, touchPosmenu.y-420);
			//rectangle regadera
			recmenujuego[6].setPosition(touchPosmenu.x+450, touchPosmenu.y);
			if(Gdx.input.justTouched())
			{
				
				if(rectangleguia.overlaps(recmenujuego[0]))
				{
					
					if(pantalla==0)
					{
						vardemoledora=0;
						valoritem=0;
						tipodecultivo=-1;
						pantalla=1;
						regadera=0;
						poscamarax=camara.position.x;
						poscamaray=camara.position.y;
						camara.position.x=400;
						camara.position.y=900;
					}
					else if(pantalla==1)
					{
						camara.position.x=poscamarax;
						camara.position.y=poscamaray;
						pantalla=0;
					}
				}
				else if(rectangleguia.overlaps(recmenujuego[1]) && pantalla==0)
				{
					regadera=0;
					if(cortadora==0)
					{
						cortadora=1;
					}
					else if(cortadora==1)
					{
						cortadora=0;
					}
					vardemoledora=0;
					tipodecultivo=0;
					valoritem=0;
					
				}
				else if(rectangleguia.overlaps(recmenujuego[4]) && pantalla==0)
				{
					//System.out.println("haz tocado la demoledora");
					regadera=0;
					if(vardemoledora==0)
					{
						vardemoledora=1;
					}
					else if(vardemoledora==1)
					{
						vardemoledora=0;
					}
					cortadora=0;
					tipodecultivo=0;
					valoritem=0;
				}
				else if(rectangleguia.overlaps(recmenujuego[5]))
				{
					regadera=0;
					if(tipodecultivo>0)
					{
						tipodecultivo=0;
					}
					
				}
				else if(rectangleguia.overlaps(recmenujuego[6]) && pantalla==0)
				{
					cortadora=0;
					vardemoledora=0;
					tipodecultivo=0;
					//System.out.println("regadera");
					if(regadera==1)
					{
						regadera=0;
					}
					else if(regadera==0)
					{
						regadera=1;
					}
					
				}
				if(pantalla==1)
				{
					if(rectangleguia.overlaps(recmenujuego[2]))
					{
						subpantallamenu=0;
						System.out.println("haz tocado la pantalla cultivos");
					}
					else if(rectangleguia.overlaps(recmenujuego[3]))
					{
						subpantallamenu=1;
						System.out.println("haz tocado la pantalla construccion");
					}
				}
				
			}
			
			
		}
		//===================================
		
		
		public void accionesoverlaps()
		{
			
			//System.out.println("x: "+camara.position.x+" y: "+camara.position.y);
			if(Gdx.input.isTouched())
			{
				
				if(pantalla==0)
				{

					if(rectangleguia.overlaps(recbarriba))
					{
						if(camara.position.y<1199)
						{
							camara.position.y+=8.0f;
						}
							
					}
					else if(rectangleguia.overlaps(recbabajo))
					{
						if(camara.position.y>240)
						{
							camara.position.y-=8.0f;
						}
							
					}
					else if(rectangleguia.overlaps(recbder))
					{
						if(camara.position.x<1200)
						{
							camara.position.x+=8.0f;
						}
					}
					
					else if(rectangleguia.overlaps(recbizq))
					{
						
						if(camara.position.x>400)
						{
							camara.position.x-=8.0f;
						}
						
					}
				}
				else if(pantalla==1)
				{
					if(rectangleguia.overlaps(recbotomenu[0][0]))
					{
						if(camara.position.y<900.0f)
						{
							camara.position.y+=6.0f;
						}
						
					}
					else if(rectangleguia.overlaps(recbotomenu[1][0]))
					{
						if(camara.position.y>600)
						{
							camara.position.y-=6.0f;
						}
							
						
						
					}
				}
				
				
			}
			
			
		}
		
		//items pequeños cultivos y construccion
		public void itemsmenu(TextureRegion[][] regionsubparce)
		{
			if(subpantallamenu==0)
			{
				//item id 0
				/*batch.draw(regionsubparce[0][0], x[0], y[7]);
				recitemsmenu[0][0].setPosition(x[0], y[7]);
				if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
				{
					if(rectangleguia.overlaps(recitemsmenu[0][0]) && dinero>0)
					{
						
						
							System.out.println("haz tocado el item 0");
							tipodecultivo=0;
							valoritem=1;
						
						
							
						
						
					}
				}*/
				
				//item id 1
				batch.draw(regionsubparce[0][1], x[1], y[7]);
				recitemsmenu[0][1].setPosition(x[1], y[7]);
				font.draw(batch, "$25", x[1]+85, y[7]+30);
				if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
				{
					if(rectangleguia.overlaps(recitemsmenu[0][1]) && dinero>0)
					{
						
							System.out.println("haz tocado el item 1");
							tipodecultivo=1;
							valoritem=25;
						
					}
				}
				//item id 2
					batch.draw(regionsubparce[0][2], x[2], y[7]);
					recitemsmenu[0][2].setPosition(x[2], y[7]);
					font.draw(batch, "$25", x[2]+99, y[7]+30);
					if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
						{
							if(rectangleguia.overlaps(recitemsmenu[0][2]) && dinero>0)
							{
								
								
									System.out.println("haz tocado el item 2");
									tipodecultivo=2;
									valoritem=25;
								
							}
						}
					//item id 3
					batch.draw(regionsubparce[0][3], x[0], y[5]);
					recitemsmenu[0][3].setPosition(x[0], y[5]);
					font.draw(batch, "$40", x[0]+99, y[5]+30);
					if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
						{
							if(rectangleguia.overlaps(recitemsmenu[0][3]) && dinero>0)
							{
								
									System.out.println("haz tocado el item 3");
									tipodecultivo=3;
									valoritem=40;

							}
						}
					//nivel 2
					//item id 4
					batch.draw(regionsubparce[0][4], x[1], y[5]);
					recitemsmenu[0][4].setPosition(x[1], y[5]+30);
						font.draw(batch, "$50", x[1]+99, y[5]+30);
						if(nivel<2)
						{
							font.draw(batch, "Level 2", x[1]+55, y[5]+50); 
						}
					 
					if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
						{
							if(rectangleguia.overlaps(recitemsmenu[0][4]) && dinero>1)
							{
								
								if(nivel>1)
								{
									System.out.println("haz tocado el item 4");
									tipodecultivo=4;
									valoritem=50;
								}

							}
						}
					//nivel 5
					//item id 5
					batch.draw(regionsubparce[1][0], x[2], y[5]);
					recitemsmenu[1][0].setPosition(x[2], y[5]);
					
					font.draw(batch, "$50", x[2]+99, y[5]+30);
					if(nivel<5)
					{
						font.draw(batch, "Level 5", x[2]+55, y[5]+50);
					}
					
					if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
						{
							if(rectangleguia.overlaps(recitemsmenu[1][0]) && dinero>1)
							{
								
								if(nivel>4)
								{
									System.out.println("haz tocado el item 5");
									tipodecultivo=5;
									valoritem=50;
								}
		
							}
						}
					//nivel 7
					//item id 6
					batch.draw(regionsubparce[1][1], x[0], y[3]);
					recitemsmenu[1][1].setPosition(x[0], y[3]);
					
					font.draw(batch, "$100", x[0]+99, y[3]+30);
					if(nivel<7)
					{
						font.draw(batch, "Level 7", x[0]+55, y[3]+50);
					}
					 
					if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
						{
							if(rectangleguia.overlaps(recitemsmenu[1][1]) && dinero>1)
							{
								
								if(nivel>6)
								{
									System.out.println("haz tocado el item 6");
									tipodecultivo=6;
									valoritem=100;
								}
			
							}
						}
					//nivel 8
					//item id 7
					batch.draw(regionsubparce[1][2], x[1], y[3]);
					recitemsmenu[1][2].setPosition(x[1], y[3]);
					
					font.draw(batch, "$150", x[1]+99, y[3]+30);
					if(nivel<8)
					{
						font.draw(batch, "Level 8", x[1]+55, y[3]+50);
					}
					
					if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
						{
							if(rectangleguia.overlaps(recitemsmenu[1][2]) && dinero>1)
							{
								if(nivel>7)
								{

									System.out.println("haz tocado el item 7");
									tipodecultivo=7;
									valoritem=150;
								}
								
								
								
							}
						}
					//nivel 10
					//item id 8
					batch.draw(regionsubparce[1][3], x[2], y[3]);
					recitemsmenu[1][3].setPosition(x[2], y[3]);
					
					font.draw(batch, "$200", x[2]+99, y[3]+30);
					if(nivel<10)
					{
						font.draw(batch, "Level 10", x[2]+55, y[3]+50);
					}
					
					if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
						{
							if(rectangleguia.overlaps(recitemsmenu[1][3]) && dinero>2)
							{
								
								if(nivel>9)
								{
									System.out.println("haz tocado el item 8");
									tipodecultivo=8;
									valoritem=200;
								}
									
								
								
							}
						}
					//item id 9
					/*batch.draw(regionsubparce[1][4], x[0], y[1]);
					recitemsmenu[1][4].setPosition(x[0], y[1]);
					if(nivel>9)
					{
						font.draw(batch, "$200", x[0]+99, y[1]);
					}else{ font.draw(batch, "Level 10", x[0]+99, y[1]); }
					if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
						{
							if(rectangleguia.overlaps(recitemsmenu[1][4]) && dinero>2)
							{
								if(nivel>9)
								{
									System.out.println("haz tocado el item 9");
									tipodecultivo=9;
									valoritem=200;
								}
								
									
								
								
							}
						}*/
			}
			else if(subpantallamenu==1)
			{
				//item id 10
				batch.draw(regionsubparce[0][0], x[0], y[7]);
				recitemsmenu[0][0].setPosition(x[0], y[7]);
				if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
				{
					if(rectangleguia.overlaps(recitemsmenu[0][0]) && dinero>0)
					{
						
						
							System.out.println("haz tocado el item 10");
							tipodecultivo=10;
							valoritem=3;
						
							
						
						
					}
				}
				
				//item id 11
				batch.draw(regionsubparce[0][1], x[1], y[7]);
				recitemsmenu[0][1].setPosition(x[1], y[7]);
				if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
				{
					if(rectangleguia.overlaps(recitemsmenu[0][1]) && dinero>0)
					{
						
							System.out.println("haz tocado el item 11");
							tipodecultivo=11;
							valoritem=3;
						
					
						
					}
				}
				//item id 12
					batch.draw(regionsubparce[0][2], x[2], y[7]);
					recitemsmenu[0][2].setPosition(x[2], y[7]);
					if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
						{
							if(rectangleguia.overlaps(recitemsmenu[0][2]) && dinero>0)
							{
								
								
									System.out.println("haz tocado el item 12");
									tipodecultivo=12;
									valoritem=3;
								
							}
						}
					//item id 13
					batch.draw(regionsubparce[0][3], x[0], y[5]);
					recitemsmenu[0][3].setPosition(x[0], y[5]);
					if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
						{
							if(rectangleguia.overlaps(recitemsmenu[0][3]) && dinero>0)
							{
								
								
									System.out.println("haz tocado el item 13");
									tipodecultivo=13;
									valoritem=3;
								
							}
						}
					//item id 14
					/*batch.draw(regionsubparce[0][4], x[1], y[5]);
					recitemsmenu[0][4].setPosition(x[1], y[5]);
					if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
						{
							if(rectangleguia.overlaps(recitemsmenu[0][4]) && dinero>0)
							{
								
							
									System.out.println("haz tocado el item 14");
									tipodecultivo=14;
									valoritem=3;
								
								
							}
						}
					//item id 15
					batch.draw(regionsubparce[1][0], x[2], y[5]);
					recitemsmenu[1][0].setPosition(x[2], y[5]);
					if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
						{
							if(rectangleguia.overlaps(recitemsmenu[1][0]) && dinero>0)
							{
								
								
									System.out.println("haz tocado el item 15");
									tipodecultivo=15;
									valoritem=3;
								
								
							}
						}
					//item id 16
					batch.draw(regionsubparce[1][1], x[0], y[3]);
					recitemsmenu[1][1].setPosition(x[0], y[3]);
					if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
						{
							if(rectangleguia.overlaps(recitemsmenu[1][1]) && dinero>0)
							{
								
								
									System.out.println("haz tocado el item 16");
									tipodecultivo=16;
									valoritem=3;
								
								
							}
						}
					//item id 17
					batch.draw(regionsubparce[1][2], x[1], y[3]);
					recitemsmenu[1][2].setPosition(x[1], y[3]);
					if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
						{
							if(rectangleguia.overlaps(recitemsmenu[1][2]) && dinero>0)
							{
								
								
									System.out.println("haz tocado el item 17");
									tipodecultivo=17;
									valoritem=3;
								
								
							}
						}
					//item id 18
					batch.draw(regionsubparce[1][3], x[2], y[3]);
					recitemsmenu[1][3].setPosition(x[2], y[3]);
					if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
						{
							if(rectangleguia.overlaps(recitemsmenu[1][3]) && dinero>0)
							{
								
								
									System.out.println("haz tocado el item 18");
									tipodecultivo=18;
									valoritem=3;
								
								
							}
						}
					//item id 19
					batch.draw(regionsubparce[1][4], x[0], y[1]);
					recitemsmenu[1][4].setPosition(x[0], y[1]);
					if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
						{
							if(rectangleguia.overlaps(recitemsmenu[1][4]) && dinero>0)
							{
								
								
									System.out.println("haz tocado el item 19");
									tipodecultivo=19;
									valoritem=3;
								
								
							}
						}*/
					
			}
			
			
			
		}
		
		//contrucciones grandes se muestran en subpantalla 1 en menu
		/*public void itemsmenug(TextureRegion[][] regionparce)
		{
			 
			
			//item id 20
			batch.draw(regionparce[0][0], x[0], y[0]-100, regionconstruc[0][0].getRegionWidth(), regionconstruc[0][0].getRegionHeight());
			recitemsmenu[2][0].setPosition(x[0], y[0]-100);
			if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
				{
					if(rectangleguia.overlaps(recitemsmenu[2][0]) && dinero>0)
					{
						
						
							System.out.println("haz tocado el item 20");
							tipodecultivo=20;
							valoritem=15;
						
						
					}
				}
			//item id 21
			batch.draw(regionparce[0][1], x[1], y[0]-100, regionconstruc[0][0].getRegionWidth(), regionconstruc[0][0].getRegionHeight());
			recitemsmenu[2][1].setPosition(x[1], y[0]-100);
			if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
				{
					if(rectangleguia.overlaps(recitemsmenu[2][1]) && dinero>0)
					{
						
						
							System.out.println("haz tocado el item 21");
							tipodecultivo=21;
							valoritem=15;
						
						
					}
				}
			//item id 22
			batch.draw(regionparce[0][2], x[2], y[0]-100, regionconstruc[0][0].getRegionWidth(), regionconstruc[0][0].getRegionHeight());
			recitemsmenu[2][2].setPosition(x[2], y[0]-100);
			if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
				{
					if(rectangleguia.overlaps(recitemsmenu[2][2]) && dinero>0)
					{
						
						
							System.out.println("haz tocado el item 22");
							tipodecultivo=22;
							valoritem=15;
						
						
					}
				}
			//item id 23
			batch.draw(regionparce[0][3], x[0], y[0]-300, regionconstruc[0][0].getRegionWidth(), regionconstruc[0][0].getRegionHeight());
			recitemsmenu[2][3].setPosition(x[0], y[0]-300);
			if(Gdx.input.justTouched() && !rectangleguia.overlaps(recbarramenu))
				{
					if(rectangleguia.overlaps(recitemsmenu[2][3]) && dinero>0)
					{
						
						
							System.out.println("haz tocado el item 23");
							tipodecultivo=23;
							valoritem=15;
						
						
					}
				}
			
		}*/
		@Override
		public void pause() {
			// TODO Auto-generated method stub
			super.pause();
			preferencias.putFloat("tiempodeldia", tiempodeldia);
			preferencias.flush();
			preferencias.putInteger("dinero", dinero);
			preferencias.flush();
			preferencias.putInteger("nivel", nivel);
			preferencias.flush();
			if(preferencias.getInteger("primeravez")==0)
			{
				preferencias.putInteger("primeravez", 1);				
			}
			preferencias.putInteger("puntos", puntos);
			preferencias.flush();
			for(int p=0; p<15; p++)
			{
				//[0][0]=1, [0][1]=0, [1][0]=3, [1][1]=2
				for(int s=0; s<2; s++)
				{
					for(int d=0; d<2; d++)
					{
						if(tiempodecrecer[p][s][d]>0)
						{
							
							preferencias.putFloat(p+"tiempodecrecer"+s+d, tiempodecrecer[p][s][d]);
							preferencias.flush();
						}
						if(intervalosdetiempo[p][s][d]>0)
						{
							preferencias.putInteger(p+"intervalodetiempo"+s+d, intervalosdetiempo[p][s][d]);
							preferencias.flush();
						}
						if (maduressubparcela[p][s][d]>0)
						{
							preferencias.putInteger(p+"maduressubparcela"+s+d, maduressubparcela[p][s][d]);
							preferencias.flush();
						}
						
						
					}
				}
			}
		}
		
		@Override
		public void resume() {
			// TODO Auto-generated method stub
			super.resume();
		}
		
		@Override
		public void dispose() 
		{	
			super.dispose();
			fondos.dispose();
			terrenoparcela.dispose();
			terrenoconstruc.dispose();
			spritemenu.dispose();
			botonera.dispose();
			rombo.dispose();
			arbolada.dispose();
			botoneramenu.dispose();
			gota.dispose();
			gotas.dispose();
			itemsmenu.dispose();
			preferencias.putFloat("tiempodeldia", tiempodeldia);
			preferencias.flush();
			preferencias.putInteger("dinero", dinero);
			preferencias.flush();
			preferencias.putInteger("nivel", nivel);
			preferencias.flush();
			if(preferencias.getInteger("primeravez")==0)
			{
				preferencias.putInteger("primeravez", 1);				
			}
			preferencias.putInteger("puntos", puntos);
			preferencias.flush();
			for(int p=0; p<15; p++)
			{
				//[0][0]=1, [0][1]=0, [1][0]=3, [1][1]=2
				for(int s=0; s<2; s++)
				{
					for(int d=0; d<2; d++)
					{
						if(tiempodecrecer[p][s][d]>0)
						{
							preferencias.putFloat(p+"tiempodecrecer"+s+d, tiempodecrecer[p][s][d]);
							preferencias.flush();
						}
						if(intervalosdetiempo[p][s][d]>0)
						{
							preferencias.putInteger(p+"intervalodetiempo"+s+d, intervalosdetiempo[p][s][d]);
							preferencias.flush();
						}
						if (maduressubparcela[p][s][d]>0)
						{
							preferencias.putInteger(p+"maduressubparcela"+s+d, maduressubparcela[p][s][d]);
							preferencias.flush();
						}
					}
				}
			}
		}
		
		@Override
		public void resize(int width, int height) {
			//camara.viewportWidth=width;
			//camara.viewportHeight=height;
			
		}
}
