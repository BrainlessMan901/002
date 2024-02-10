package curl;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;



class swept_{
	int x;
	int y;
	int tick = 128;
}
class yellow_stone{
	int x;
	int y;
	int power=0;
	int xpower;
	int xtick;
	int tick = 30;
	ArrayList yellow_c = new ArrayList();
	ArrayList red_c = new ArrayList();
	boolean lock = false;
}
class red_stone{
	int x;
	int y;
	int power;
	int xpower;
	int xtick;
	int tick = 30;
	ArrayList yellow_c = new ArrayList();
	ArrayList red_c = new ArrayList();
	boolean lock = false;
}

class cl extends JFrame implements KeyListener, Runnable, MouseListener, MouseMotionListener{
	int spp = 0;
	int f_width = 700;
	int f_height = 800;
	int mouse_x = 0;
	int mouse_y = 0;
	int atker;
	int player=0;
	int round=1;
	int mode = 0;
	int swpx = 0;
	int swpy = 0;
	int space = 0;
	int time = 0;
	int point = 0;
	float ydelt = 0;
	Thread th;
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image bg = tk.getImage("bg.png");
	Image hori = tk.getImage("hori.png");
	Image vert = tk.getImage("vert.png");
	Image bl = tk.getImage("blue.png");
	Image re = tk.getImage("re.png");
	Image ys = tk.getImage("ys.png");
	Image rs = tk.getImage("rs.png");
	Image yes = tk.getImage("yes.png");
	Image res = tk.getImage("res.png");
	Image swp = tk.getImage("sweeper.png");
	Image pwr = tk.getImage("power.png");
	Image swept = tk.getImage("swept.png");
	Image help = tk.getImage("help.png");
	Image p2x2 = tk.getImage("2x2.png");
	Image sp = tk.getImage("sp.png");
	
	AlphaComposite alphaComposite;
	
	ArrayList ys_list = new ArrayList();
	ArrayList rs_list = new ArrayList();
	ArrayList swept_list = new ArrayList();
	
	
	yellow_stone yest;
	red_stone rest;
	swept_ swst;
	
	Image buffImage;
	Graphics buffg;
	cl(){
		
		init();
		start();
		
		setTitle("jump game");
		setSize(f_width, f_height);
		
		Dimension screen = tk.getScreenSize();
		
		int f_xpos = (int)(screen.getWidth() / 2 - f_width /2);
		int f_ypos = (int)(screen.getHeight() / 2 - f_height /2);
		
		setLocation(f_xpos, f_ypos);
		setResizable(false);
		setVisible(true);
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	public void init() {
		
	}
	public void start() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);
		th = new Thread(this);
		th.start();
	}
	public void run() {
		try {
			
		
			while(true) {
				if(mode<4) {
					//System.out.println("mode : "+ mode + " round : " + round);
				}
				
				
				if(mode==0&&player==0) {
					//System.out.println((int) Math.pow(125, 2.0));
					player = atker;
					if(atker == 1){
						mode=1;
						yellow_spawn();
						
					}
					if(atker == 2){
						mode=5;
						rest = new red_stone();
						rest.y=705;
						rs_list.add(rest);
						for(int i = 0 ; i<rs_list.size();++i) {
							rest = (red_stone)rs_list.get(i);}
					}
				}
				repaint();
                
				if(mode ==1 ) {
					if(round%2==1) {
					for(int i =0 ; i<ys_list.size();++i) {
						yest = (yellow_stone)ys_list.get(i);
						if(i==((round+1)/2)-1) {
							yest.x = mouse_x;
						}
					}
					}else {
						for(int i =0 ; i<rs_list.size();++i) {
							rest = (red_stone)rs_list.get(i);
							if(i==(round/2) -1) {
								rest.x = mouse_x;
							}
						}
					}
				}
				
				if(mode==2) {//////////////////////////////////////////////////////////////////////////////////
					//¾µ±â Á¦°Å
					for(int j =0 ; j<swept_list.size();++j) {
						swst = (swept_)swept_list.get(j);
						swst.tick -=1;
						if(swst.tick <= 0 ) {
							swept_list.remove(j);
						}
					}
					// power>0 (yellow)
					for(int i =0 ; i<ys_list.size();++i) {
						yest = (yellow_stone)ys_list.get(i);
						//y°¨¼Ò
						if(yest.power>0) {
							if(yest.tick<0) {
								yest.power -=1;
								yest.tick=30;
								if(yest.power <= 0) {
									yest.xpower = 0;
									yest.power =0;
								}
							}
							else {yest.tick-=1;}
							if(yest.tick%3==0) {
								yest.y -= (yest.power/2)+1;
							}
							
						}
						//È¾ ÀÌµ¿
						if(yest.power >0&&yest.xpower!=0) {
							if(yest.xpower <0) {
								if(yest.xtick<=0) {
									yest.x -=1;
									yest.xtick = yest.xpower*-1 ;
								}
								else {
									yest.xtick -=1;
								}
							}
							else if(yest.xpower >0) {
								if(yest.xtick<=0) {
									yest.x +=1;
									yest.xtick = yest.xpower ;
								}
								else {
									yest.xtick -=1;
								}
							}
							
						}
						//¾µ±â È¿°ú
						int yx = yest.x+2;
						int yy = yest.y+8;
						int lefter =0;
						int righter = 0;
						for(int j =0 ; j<swept_list.size();++j) {
							swst = (swept_)swept_list.get(j);
							if(yx>=swst.x&&yx<=swst.x+32 && yy>=swst.y && yy<=swst.y+32) {
								lefter++;
							}
						}
						yx = yest.x+30;
						yy = yest.y+8;
						for(int j =0 ; j<swept_list.size();++j) {
							swst = (swept_)swept_list.get(j);
							if(yx>=swst.x&&yx<=swst.x+32 && yy>=swst.y && yy<=swst.y+32) {
								righter++;
							}
						}
						if(yest.tick % 3 == 0&&yest.power>0) {
							//System.out.println("left : "+ lefter + " right : " + righter);
							if(lefter-righter>5 && lefter >= 18) {
								yest.x -=1;
							}
							if(righter-lefter>5 && righter >= 18) {
								yest.x +=1;
							}
							if(lefter+righter>32) {
								yest.tick+=3;
							}
						}
						//Ãæµ¹
						//ÇöÀç½ºÅæ
						yest = (yellow_stone)ys_list.get(i);
						int cx = yest.x;
						int cy = yest.y;
						int cpower = yest.power;
						//Ãæµ¹´ë»ó½ºÅæ(³ë¶õ»ö)
						for(int j =0 ; j<ys_list.size();j++) {
							if(i==j) {
								continue;
							}
							yest = (yellow_stone)ys_list.get(j);
							//Ãæµ¹´ë»ó½ºÅæ(¿ÞÂÊ)
							//System.out.println(cy + " "+yest.y);
							if(yest.x <= cx && yest.x+32 >=cx &&cy-yest.y<=32&&cy-yest.y>0) {
								yest = (yellow_stone)ys_list.get(i);
								int gy = 0;
								for(int k = 0 ; k<yest.yellow_c.size();++k) {
									if(yest.yellow_c.get(k).equals(j)) {
										gy+=1;
									}
								}
								if(gy==0) {
									yest.yellow_c.add(j);
									System.out.println(i + "¹ø yellow½ºÅæ ->" + j+"¹ø yellow½ºÅæ !!!" + yest.yellow_c);
									yest = (yellow_stone)ys_list.get(j);
									int delt = cx-yest.x;
									yest.xpower -= (delt)/2;
									//ydelt = (float)(1 + yest.xpower / 2 *0.125);
									
									
									//System.out.println("Èû(ÇÇÃæµ¹) : "+ yest.power + " n11 : "+ j);
									//System.out.println(i + "¹ø ½ºÅæ ->" + j +"¹ø ½ºÅæ xÂ÷ÀÌ :"+delt + " °è¼ö : "+ ydelt + " Èû : "+ cpower);	
									
									ydelt = (float)(1 + yest.xpower / 2 *0.125);
									
									//System.out.println("Èû(ÇÇÃæµ¹) : "+ yest.power + " n22 : "+ j);
									//yest.power -=  cpower;
									yest.power += (int) ((int)cpower*ydelt) ;
									//System.out.println("Èû(ÇÇÃæµ¹) : "+ yest.power + " n33 : "+ j);
									
									
									yest = (yellow_stone)ys_list.get(i);
									yest.xpower += (delt)/2;	
									yest.power -= (int)(cpower*(ydelt));									
								}
							}
							//Ãæµ¹´ë»ó½ºÅæ(¿À¸¥ÂÊ)
							if(yest.x <= cx+32 && yest.x+32 >=cx+32&& cy-yest.y<=32&&cy-yest.y>0) {
								yest = (yellow_stone)ys_list.get(i);
								int gy = 0;
								for(int k = 0 ; k<yest.yellow_c.size();++k) {
									if(yest.yellow_c.get(k).equals(j)) {
										gy+=1;
									}
								}
								if(gy==0) {
									yest.yellow_c.add(j);
									System.out.println(i + "¹ø yellow½ºÅæ ->" + j+"¹ø yellow½ºÅæ !!!" + yest.yellow_c);
									yest = (yellow_stone)ys_list.get(j);
									int delt = cx-yest.x;
									yest.xpower -= (delt)/2;
									float ydelt = (float) (1 - yest.xpower / 2 *0.125);
									//System.out.println(delt + " °è¼ö : "+ydelt+" Èû : "+ cpower);
									//System.out.println(yest.power + " n11 : "+ j);
									yest.power += (int) ((int)cpower*ydelt) ;
									//System.out.println(yest.power + " n22 : "+ j);
									yest = (yellow_stone)ys_list.get(i);
									yest.xpower +=(delt)/2;	
									yest.power -= (int)(cpower*(ydelt));	
								}
								
							}
							
						}
						yest = (yellow_stone)ys_list.get(i);
						cx = yest.x;
						cy = yest.y;
						cpower = yest.power;
						//Ãæµ¹´ë»ó½ºÅæ(»¡°£»ö)
						for(int j =0 ; j<rs_list.size();j++) {
							if(i==j) {
								//continue;
							}
							rest = (red_stone)rs_list.get(j);
							//Ãæµ¹´ë»ó½ºÅæ(¿ÞÂÊ)
							//System.out.println(cy + " "+yest.y);
							if(rest.x <= cx && rest.x+32 >=cx &&cy-rest.y<=32&&cy-rest.y>0) {
								yest = (yellow_stone)ys_list.get(i);
								int gy = 0;
								for(int k = 0 ; k<yest.red_c.size();++k) {
									if(yest.red_c.get(k).equals(j)) {
										gy+=1;
									}
								}
								if(gy==0) {
									yest.red_c.add(j);
									System.out.println(i + "¹ø yellow½ºÅæ ->" + j+"¹ø red½ºÅæ !!!" + yest.red_c);
									rest = (red_stone)rs_list.get(j);
									int delt = cx-rest.x;
									rest.xpower -= (delt)/2;
									//ydelt = (float)(1 + yest.xpower / 2 *0.125);
									
									
									//System.out.println("Èû(ÇÇÃæµ¹) : "+ yest.power + " n11 : "+ j);
									//System.out.println(i + "¹ø ½ºÅæ ->" + j +"¹ø ½ºÅæ xÂ÷ÀÌ :"+delt + " °è¼ö : "+ ydelt + " Èû : "+ cpower);	
									
									ydelt = (float)(1 + rest.xpower / 2 *0.125);
									
									//System.out.println("Èû(ÇÇÃæµ¹) : "+ yest.power + " n22 : "+ j);
									//yest.power -=  cpower;
									rest.power += (int) ((int)cpower*ydelt) ;
									//System.out.println("Èû(ÇÇÃæµ¹) : "+ yest.power + " n33 : "+ j);
									
									
									yest = (yellow_stone)ys_list.get(i);
									yest.xpower += (delt)/2;	
									yest.power -= (int)(cpower*(ydelt));									
								}
							}
							//Ãæµ¹´ë»ó½ºÅæ(¿À¸¥ÂÊ)
							if(rest.x <= cx+32 && rest.x+32 >=cx+32&& cy-rest.y<=32&&cy-rest.y>0) {
								yest = (yellow_stone)ys_list.get(i);
								int gy = 0;
								for(int k = 0 ; k<yest.red_c.size();++k) {
									if(yest.red_c.get(k).equals(j)) {
										gy+=1;
									}
								}
								if(gy==0) {
									yest.red_c.add(j);
									System.out.println(i + "¹ø yellow½ºÅæ ->" + j+"¹ø red½ºÅæ !!!" + yest.red_c);
									rest = (red_stone)rs_list.get(j);
									int delt = cx-rest.x;
									rest.xpower -= (delt)/2;
									float ydelt = (float) (1 - rest.xpower / 2 *0.125);
									//System.out.println(delt + " °è¼ö : "+ydelt+" Èû : "+ cpower);
									//System.out.println(yest.power + " n11 : "+ j);
									rest.power += (int) ((int)cpower*ydelt) ;
									//System.out.println(yest.power + " n22 : "+ j);
									yest = (yellow_stone)ys_list.get(i);
									yest.xpower +=(delt)/2;	
									yest.power -= (int)(cpower*(ydelt));	
								}
								
							}
							
						}
						
					}
					// power>0 (red)
					for(int i =0 ; i<rs_list.size();++i) {
						rest = (red_stone)rs_list.get(i);
						if(rest.power>0) {
							//y°¨¼Ò
							if(rest.power>0) {
								if(rest.tick<0) {
									rest.power -=1;
									rest.tick=30;
								}
								else {rest.tick-=1;}
								if(rest.tick%3==0) {
									rest.y -= (rest.power/2)+1;
								}
								
							}
							//È¾ ÀÌµ¿
							if(rest.power >0&&rest.xpower!=0) {
								if(rest.xpower <0) {
									if(rest.xtick<=0) {
										rest.x -=1;
										rest.xtick = rest.xpower*-1 ;
									}
									else {
										rest.xtick -=1;
									}
								}
								else if(rest.xpower >0) {
									if(rest.xtick<=0) {
										rest.x +=1;
										rest.xtick = rest.xpower ;
									}
									else {
										rest.xtick -=1;
									}
								}
								
							}
						}
					//¾µ±â È¿°ú
					int yx = rest.x+2;
					int yy = rest.y+8;
					int lefter = 0;
					int righter =0;
					for(int j =0 ; j<swept_list.size();++j) {
						swst = (swept_)swept_list.get(j);
						if(yx>=swst.x&&yx<=swst.x+32 && yy>=swst.y && yy<=swst.y+32) {
							lefter++;
						}
					}
					yx = rest.x+30;
					yy = rest.y+8;
					for(int j =0 ; j<swept_list.size();++j) {
						swst = (swept_)swept_list.get(j);
						if(yx>=swst.x&&yx<=swst.x+32 && yy>=swst.y && yy<=swst.y+32) {
							righter++;
						}
					}
					if(rest.tick % 5 == 0&&rest.power>=1) {
						//System.out.println("left : "+ lefter + " right : " + righter);
						if(lefter-righter>5 && lefter >= 18) {
							rest.x -=1;
						}
						if(righter-lefter>5 && righter >= 18) {
							rest.x +=1;
						}
						if(lefter+righter>32) {
							rest.tick+=3;
						}
					}
					//Ãæµ¹
					//ÇöÀç½ºÅæ
					rest = (red_stone)rs_list.get(i);
					int cx = rest.x;
					int cy = rest.y;
					int cpower = rest.power;
					//Ãæµ¹´ë»ó½ºÅæ(³ë¶õ»ö)
					for(int j =0 ; j<ys_list.size();j++) {
						if(i==j) {
							//continue;
						}
						yest = (yellow_stone)ys_list.get(j);
						//Ãæµ¹´ë»ó½ºÅæ(¿ÞÂÊ)
						//System.out.println(cy + " "+yest.y);
						if(yest.x <= cx && yest.x+32 >=cx &&cy-yest.y<=32&&cy-yest.y>0) {
							rest = (red_stone)rs_list.get(i);
							int gy = 0;
							for(int k = 0 ; k<rest.yellow_c.size();++k) {
								if(rest.yellow_c.get(k).equals(j)) {
									gy+=1;
								}
							}
							if(gy==0) {
								rest.yellow_c.add(j);
								System.out.println(i + "¹ø red½ºÅæ ->" + j+"¹ø yellow½ºÅæ !!!"+rest.yellow_c);
								yest = (yellow_stone)ys_list.get(j);
								int delt = cx-yest.x;
								yest.xpower -= (delt)/2;
								//ydelt = (float)(1 + yest.xpower / 2 *0.125);
								
								
								//System.out.println("Èû(ÇÇÃæµ¹) : "+ yest.power + " n11 : "+ j);
								//System.out.println(i + "¹ø ½ºÅæ ->" + j +"¹ø ½ºÅæ xÂ÷ÀÌ :"+delt + " °è¼ö : "+ ydelt + " Èû : "+ cpower);	
								
								ydelt = (float)(1 + yest.xpower / 2 *0.125);
								
								//System.out.println("Èû(ÇÇÃæµ¹) : "+ yest.power + " n22 : "+ j);
								//yest.power -=  cpower;
								yest.power += (int) ((int)cpower*ydelt) ;
								//System.out.println("Èû(ÇÇÃæµ¹) : "+ yest.power + " n33 : "+ j);
								
								
								rest = (red_stone)rs_list.get(i);
								rest.xpower += (delt)/2;	
								rest.power -= (int)(cpower*(ydelt));									
							}
						}
						//Ãæµ¹´ë»ó½ºÅæ(¿À¸¥ÂÊ)
						yest = (yellow_stone)ys_list.get(j);
						if(yest.x <= cx+32 && yest.x+32 >=cx+32&& cy-yest.y<=32&&cy-yest.y>0) {
							rest = (red_stone)rs_list.get(i);
							int gy = 0;
							for(int k = 0 ; k<rest.yellow_c.size();++k) {
								if(rest.yellow_c.get(k).equals(j)) {
									gy+=1;
								}
							}
							if(gy==0) {
								rest.yellow_c.add(j);
								System.out.println(i + "¹ø red½ºÅæ ->" + j+"¹ø yellow½ºÅæ !!!"+rest.yellow_c);
								yest = (yellow_stone)ys_list.get(j);
								int delt = cx-yest.x;
								yest.xpower -= (delt)/2;
								float ydelt = (float) (1 - yest.xpower / 2 *0.125);
								//System.out.println(delt + " °è¼ö : "+ydelt+" Èû : "+ cpower);
								//System.out.println(yest.power + " n11 : "+ j);
								yest.power += (int) ((int)cpower*ydelt) ;
								//System.out.println(yest.power + " n22 : "+ j);
								rest = (red_stone)rs_list.get(i);
								rest.xpower +=(delt)/2;	
								rest.power -= (int)(cpower*(ydelt));	
							}
							
						}
						
					}
					rest = (red_stone)rs_list.get(i);
					cx = rest.x;
					cy = rest.y;
					cpower = rest.power;
					//Ãæµ¹´ë»ó½ºÅæ(»¡°£»ö)
					for(int j =0 ; j<rs_list.size();j++) {
						if(i==j) {
							continue;
						}
						rest = (red_stone)rs_list.get(j);
						//Ãæµ¹´ë»ó½ºÅæ(¿ÞÂÊ)
						//System.out.println(cy + " "+yest.y);
						if(rest.x <= cx && rest.x+32 >=cx &&cy-rest.y<=32&&cy-rest.y>0) {
							rest = (red_stone)rs_list.get(i);
							int gy = 0;
							for(int k = 0 ; k<rest.red_c.size();++k) {
								if(rest.red_c.get(k).equals(j)) {
									gy+=1;
								}
							}
							if(gy==0) {
								rest.red_c.add(j);
								System.out.println(i + "¹ø red½ºÅæ ->" + j+"¹ø red½ºÅæ !!!"+rest.red_c);
								rest = (red_stone)rs_list.get(j);
								int delt = cx-rest.x;
								rest.xpower -= (delt)/2;
								//ydelt = (float)(1 + yest.xpower / 2 *0.125);
								
								
								//System.out.println("Èû(ÇÇÃæµ¹) : "+ yest.power + " n11 : "+ j);
								//System.out.println(i + "¹ø ½ºÅæ ->" + j +"¹ø ½ºÅæ xÂ÷ÀÌ :"+delt + " °è¼ö : "+ ydelt + " Èû : "+ cpower);	
								
								ydelt = (float)(1 + rest.xpower / 2 *0.125);
								
								//System.out.println("Èû(ÇÇÃæµ¹) : "+ yest.power + " n22 : "+ j);
								//yest.power -=  cpower;
								rest.power += (int) ((int)cpower*ydelt) ;
								//System.out.println("Èû(ÇÇÃæµ¹) : "+ yest.power + " n33 : "+ j);
								
								
								rest = (red_stone)rs_list.get(i);
								rest.xpower += (delt)/2;	
								rest.power -= (int)(cpower*(ydelt));									
							}
						}
						//Ãæµ¹´ë»ó½ºÅæ(¿À¸¥ÂÊ)
						if(rest.x <= cx+32 && rest.x+32 >=cx+32&& cy-rest.y<=32&&cy-rest.y>0) {
							rest = (red_stone)rs_list.get(i);
							int gy = 0;
							for(int k = 0 ; k<rest.red_c.size();++k) {
								if(rest.red_c.get(k).equals(j)) {
									gy+=1;
								}
							}
							if(gy==0) {
								rest.red_c.add(j);
								System.out.println(i + "¹ø red½ºÅæ ->" + j+"¹ø red½ºÅæ !!!"+rest.red_c);
								rest = (red_stone)rs_list.get(j);
								int delt = cx-rest.x;
								rest.xpower -= (delt)/2;
								float ydelt = (float) (1 - rest.xpower / 2 *0.125);
								//System.out.println(delt + " °è¼ö : "+ydelt+" Èû : "+ cpower);
								//System.out.println(yest.power + " n11 : "+ j);
								rest.power += (int) ((int)cpower*ydelt) ;
								//System.out.println(yest.power + " n22 : "+ j);
								rest = (red_stone)rs_list.get(i);
								rest.xpower +=(delt)/2;	
								rest.power -= (int)(cpower*(ydelt));	
							}
							
						}
						
					}
					
					}
					
					
					
				}//////////////////////////////////////////////////////////////////////////////////////////////
				if(mode==4) {
					float ym =1000;
					float rm =1000;
					for(int i = 0 ; i<ys_list.size();++i) {
						yest = (yellow_stone)ys_list.get(i);
						int dx = (int) Math.pow(280 - yest.x, 2.0);
						int dy = (int) Math.pow(175 - yest.y, 2.0);
						if(ym> Math.sqrt(dy + dx)) {
							ym = (float) Math.sqrt(dy + dx) ;
						}
					}
					for(int i = 0 ; i<rs_list.size();++i) {
						rest = (red_stone)rs_list.get(i);
						int dx = (int) Math.pow(280 - rest.x, 2.0);
						int dy = (int) Math.pow(175 - rest.y, 2.0);
						if(rm> Math.sqrt(dy + dx)) {
							rm = (float) Math.sqrt(dy + dx);
						}
					}
					if(ym<rm) {
						for(int i = 0 ; i<ys_list.size();++i) {
							yest = (yellow_stone)ys_list.get(i);
							int dx = (int) Math.pow(280 - yest.x, 2.0);
							int dy = (int) Math.pow(175 - yest.y, 2.0);
							if(rm> Math.sqrt(dy + dx)&&rm<=15625) {
								point +=1 ;
							}
						}
						System.out.println("Yellow Win");
					}else {
						for(int i = 0 ; i<rs_list.size();++i) {
							rest = (red_stone)rs_list.get(i);
							int dx = (int) Math.pow(280 - rest.x, 2.0);
							int dy = (int) Math.pow(175 - rest.y, 2.0);
							if(ym> Math.sqrt(dy + dx)&&rm<=15625) {
								point +=1;
							}
						}
						System.out.println("Red Win");
					}
					System.out.println(">> "+point +" point");
					mode=5;
					
				}
				
				time+=1;
				Thread.sleep(8);
		 }
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
	}
	public void paint(Graphics g) {
		buffImage = createImage(f_width, f_height);
		buffg = buffImage.getGraphics();
	
		update(g);
	}
	public void update(Graphics g) {
		bg();
		if(mode ==2) {
			for(int i =0 ; i<swept_list.size() -1;++i) {
				swst = (swept_)swept_list.get(i);
				buffg.drawImage(swept,swst.x,swst.y,this);
					
			}
		}
		if(space==1) {
			buffg.drawImage(swp,swpx,swpy,this);
		}
		for(int i =0 ; i<ys_list.size();++i) {
			yest = (yellow_stone)ys_list.get(i);
			buffg.drawImage(ys,yest.x,yest.y,this);
		}
		for(int i =0 ; i<rs_list.size();++i) {
			rest = (red_stone)rs_list.get(i);
			buffg.drawImage(rs,rest.x,rest.y,this);
		}
		if(mode ==1) {
			if(round % 2== 1) {
				for(int i = 0 ; i<ys_list.size();++i) {
				yest = (yellow_stone)ys_list.get(i);
				if(i==((round+1)/2)-1) {
					for(int j = 1 ;j<=yest.power ; j++) {
						buffg.drawImage(pwr,15,700-(25*j),this);
					}
				}
				}
			}
			else {
				for(int i = 0 ; i<rs_list.size();++i) {
					rest = (red_stone)rs_list.get(i);
					if(i==(round/2)-1) {
						for(int j = 1 ;j<=rest.power ; j++) {
							buffg.drawImage(pwr,15,700-(25*j),this);
						}
					}
					}
			}
		}
		
		
		g.drawImage(buffImage,0,0,this);
	}
	public void bg() {
		buffg.clearRect(0,0,f_width,f_width);
		buffg.drawImage(bg,0,0,this);
		if(mode ==2) {
			buffg.drawImage(sp,spp,690,this);
		}
		
		buffg.drawImage(hori,0,700,this);
		buffg.drawImage(hori,0,788,this);
		buffg.drawImage(hori,550,30,this);
		buffg.drawImage(vert,7,0,this);
		buffg.drawImage(vert,550,0,this);
		buffg.drawImage(vert,620,-150,this);
		buffg.drawImage(vert,688,0,this);
		buffg.drawImage(bl,155,50,this);
		buffg.drawImage(re,232,127,this);
		buffg.drawImage(help,555,707,this);
		//buffg.drawImage(p2x2,280,300,this);
		for(int i = 1 ;i<=7;i++){
			buffg.drawImage(hori,550,30 + 83*i,this);
		}
		for(int i = 0 ;i<=(int)(16-round)/2 -1;i++){
			buffg.drawImage(yes,570,58 + 83*i,this);
		}
		for(int i = 0 ;i<=(int)(17-round)/2 -1;i++){
			buffg.drawImage(res,640,58 + 83*i,this);
		}
		
	
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		swpx = e.getX()-15;
		swpy = e.getY()-15;
		if(e.getX()>10 && e.getX()<518) {
			mouse_x = e.getX()-4;
			
		}
		if(e.getY() >=705) {
			mouse_y = e.getY();
			
		}
		if(mode ==2) {
			if(spp>=-550) {
				if(e.getX()>10 && e.getX()<518 && e.getY()<705&&space ==1) {
				swst = new swept_();
				spp-=6;
				swst.x = e.getX()-15;
				swst.y = e.getY()-15;
				swept_list.add(swst);
				for(int i = 0 ; i<ys_list.size();++i) {
					yest = (yellow_stone)ys_list.get(i);}
				}
			}
			
			
		}
			
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if(mode ==1) {
			if(round%2==1) {
				for(int i = 0 ; i<ys_list.size();++i) {
					yest = (yellow_stone)ys_list.get(i);
					int dx = (int) Math.pow(e.getX() - yest.x, 2.0);
					int dy = (int) Math.pow(e.getY() - yest.y, 2.0);
					if(i==((round+1)/2)-1) {
						yest.lock = true;
						yest.power = (int)Math.sqrt(dy + dx)/30;
						if(yest.power > 16) {
							yest.power = 16;
						}
					}
				}
			}
			else {
				for(int i = 0 ; i<rs_list.size();++i) {
					rest = (red_stone)rs_list.get(i);
					int dx = (int) Math.pow(e.getX() - rest.x, 2.0);
					int dy = (int) Math.pow(e.getY() - rest.y, 2.0);
					if(i==(round/2) -1) {
						rest.lock = true;
						rest.power = (int)Math.sqrt(dy + dx)/30;
						if(rest.power > 16) {
							rest.power = 16;
						}
					}
				}
			}
		}
		
		
		
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void red_spawn() {
		rest = new red_stone();
		rest.y=705;
		rs_list.add(rest);
		for(int i = 0 ; i<rs_list.size();++i) {
			rest = (red_stone)rs_list.get(i);}
	}
	public void yellow_spawn() {
		yest = new yellow_stone();
		yest.y=705;
		ys_list.add(yest);
		for(int i = 0 ; i<ys_list.size();++i) {
			yest = (yellow_stone)ys_list.get(i);}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(mode ==1) {
			if(round%2==1) {
				for(int i = 0 ; i<ys_list.size();++i) {
				yest = (yellow_stone)ys_list.get(i);
				int dx = (int) Math.pow(e.getX() - yest.x, 2.0);
				int dy = (int) Math.pow(e.getY() - yest.y, 2.0);
				if(i==((round+1)/2)-1) {
					
					yest.lock = false;
					yest.power = (int)Math.sqrt(dy + dx)/30;
					if(yest.power > 16) {
						yest.power = 16;
					}
					spp=0;
					mode=2;
				}
				}
			}
			else {
				for(int i = 0 ; i<rs_list.size();++i) {
					rest = (red_stone)rs_list.get(i);
					int dx = (int) Math.pow(e.getX() - rest.x, 2.0);
					int dy = (int) Math.pow(e.getY() - rest.y, 2.0);
					if(i==(round/2) -1) {
						
						rest.lock = false;
						rest.power = (int)Math.sqrt(dy + dx)/30;
						if(rest.power > 16) {
							rest.power = 16;
						}
						spp=0;
						mode=2;
					}
					
			}
				
			}
			}
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_SPACE : space=1; break;
		case KeyEvent.VK_SHIFT : 
			for(int i = 0 ; i<ys_list.size();++i) {
				yest = (yellow_stone)ys_list.get(i);
				System.out.println(i+"¹ø yellow½ºÅæÀÌ (yellow)Ãæµ¹ÇßÀ½ : "+yest.yellow_c);
				System.out.println(i+"¹ø yellow½ºÅæÀÌ (red)Ãæµ¹ÇßÀ½ : "+yest.red_c);
				}
			for(int i = 0 ; i<rs_list.size();++i) {
			rest = (red_stone)rs_list.get(i);
			System.out.println(i+"¹ø red½ºÅæÀÌ (yellow)Ãæµ¹ÇßÀ½ : "+rest.yellow_c);
			System.out.println(i+"¹ø red½ºÅæÀÌ (red)Ãæµ¹ÇßÀ½ : "+rest.red_c);
			}
			break;
		case KeyEvent.VK_ENTER : mode=1;
		round+=1;
		for(int i = 0 ; i<rs_list.size();++i) {
			rest = (red_stone)rs_list.get(i);
			rest.yellow_c.clear();
			rest.red_c.clear();
			rest.xpower = 0;
			rest.power = 0 ;
		}
		for(int i = 0 ; i<ys_list.size();++i) {
			yest = (yellow_stone)ys_list.get(i);
			yest.yellow_c.clear();
			yest.red_c.clear();
			yest.xpower = 0;
			yest.power = 0;
		}
		if(round == 17) {mode =4;}
			else if(round%2==1){yellow_spawn();}
			else {red_spawn();}
		System.out.println("round"+round+" Á¾·á");
		break;
		case KeyEvent.VK_P :
			for(int i = 0 ; i<ys_list.size();++i) {
				yest = (yellow_stone)ys_list.get(i);
				System.out.println("yellow"+i+"¹ø Èû : "+yest.power+" xÈû : "+yest.xpower);
			}
		
	}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_SPACE : space=0; break;
		}
	}
	
}
class at extends JFrame implements ActionListener{
	
    public at(){
        setUndecorated(true);
        Toolkit tk = Toolkit.getDefaultToolkit();
        setSize(620,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        Dimension screens = tk.getScreenSize();
    	int xx = (int)(screens.getWidth() / 2 -300);
		int yy = (int)(screens.getHeight() / 2 -100);
		setLocation(xx, yy);
		
        Font font1 = new Font("¸¼Àº °íµñ", Font.BOLD, 35);
        
        JButton btn1=new JButton("1ÆÀ ¼±°ø");
        JButton btn2=new JButton("1ÆÀ ¼±°ø");
        JButton btn3=new JButton("1ÆÀ");
        btn1.setBackground(new Color(255,255,100));
        btn1.setFont(font1);
        btn1.setPreferredSize(new Dimension(200, 200));
        btn2.setBackground(new Color(241,119,119));
        btn2.setFont(font1);
        btn2.setPreferredSize(new Dimension(200, 200));
        btn3.setBackground(new Color(255,255,255));
        btn3.setFont(font1);
        btn3.setPreferredSize(new Dimension(200, 200));
        add(btn1);
        add(btn2);
        add(btn3);
        
        btn1.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		cl fms = new cl();
        		fms.atker = 1;
        		dispose();
        	}
        });
        
        btn2.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		cl fms = new cl();
        		fms.atker =1;
        		dispose();
        	}
        });
        
        btn3.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		cl fms = new cl();
        		Random a = new Random();
        		fms.atker = a.nextInt(1,3);
        		fms.atker = 1;
        		dispose();
        		
        		
        	}
        });
        
        setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
public class curling {

	public static void main(String[] args) {
		
		cl fms = new cl();
		fms.atker = 1;
		

	}

}

