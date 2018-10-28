package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainGT3 {
	  public static void main(String[] args) {
		  MyJFrame N = new MyJFrame();/*新增N物件為MyJFrame類別並執行*/
//		  N.dispose();
	  }
}

class MyJFrame extends JFrame{
  int x[],y[];/*玩家x座標,y座標*/ int players;/*玩家人數*/
  int HP[];/*血量*/  int Cool[];/*子彈冷卻時間*/
  boolean Fire[],BFire[];/*發射與否*/  boolean move[][];/*移動與否*/
  ArrayList<Bullet> bullets = new ArrayList<Bullet>();/*子彈的陣列*/
  ArrayList<BBullet> Bigbullets = new ArrayList<BBullet>();/*強化彈的陣列*/
  
  MyJFrame() {
	setTitle("小遊戲:坦克戰爭PvP");
    Pstart();/*呼叫Pstart()方法*/
    Gstart(2);/*呼叫Gstart()方法,傳入值2*/
    run();/*呼叫run()方法*/
  }
  
  /*程式初始化*/
  public void Pstart() {
    setSize(640,640);/*出現的銀幕大小(寬,高)*/
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    setLocationRelativeTo(null);
    setFocusable(true);
    addKeyListener(new KeyListener() {
      public void keyTyped(KeyEvent e) {}
      
      public void keyPressed(KeyEvent e) {
        int Keyboard = e.getKeyCode();
        requestFocus();
        switch(Keyboard) {
          case KeyEvent.VK_UP: move[0][0] = true; break;
          case KeyEvent.VK_DOWN: move[0][1] = true; break;
          case KeyEvent.VK_LEFT: move[0][2] = true; break;
          case KeyEvent.VK_RIGHT: move[0][3] = true; break;
          case KeyEvent.VK_SLASH: Fire[0] = true; break;
          case KeyEvent.VK_PERIOD: BFire[0] = true; break;
          
          case KeyEvent.VK_W: move[1][0] = true; break;
          case KeyEvent.VK_S: move[1][1] = true; break;
          case KeyEvent.VK_A: move[1][2] = true; break;
          case KeyEvent.VK_D: move[1][3] = true; break;
          case KeyEvent.VK_Q: Fire[1] = true; break;
          case KeyEvent.VK_E: BFire[1] = true; break;
          
          case KeyEvent.VK_H:
          JOptionPane.showMessageDialog(null,"1P(上方)\n 方向鍵(上下左右)移動，?普通彈;>強化彈\n\n2P(下方)wasd移動，q普通彈e強化彈","遊戲幫助",JOptionPane.INFORMATION_MESSAGE);
        }
      }
 
      public void keyReleased(KeyEvent e) {
        int Keyboard = e.getKeyCode();
        requestFocus();
        switch(Keyboard) {
          case KeyEvent.VK_UP: move[0][0] = false; break;
          case KeyEvent.VK_DOWN: move[0][1] = false; break;
          case KeyEvent.VK_LEFT: move[0][2] = false; break;
          case KeyEvent.VK_RIGHT: move[0][3] = false; break;
          case KeyEvent.VK_SLASH: Fire[0] = false; break;
          case KeyEvent.VK_PERIOD: BFire[0] = false; break;

          case KeyEvent.VK_W: move[1][0] = false; break;
          case KeyEvent.VK_S: move[1][1] = false; break;
          case KeyEvent.VK_A: move[1][2] = false; break;
          case KeyEvent.VK_D: move[1][3] = false; break;
          case KeyEvent.VK_Q: Fire[1] = false; break;
          case KeyEvent.VK_E: BFire[1] = false; break;
        }
      }
      
    });
  }
  
  /*每場遊戲的初始化*/
  public void Gstart(int n)/*n = 2*/ {
	players = n;/*玩家人數初始設定*/
    x = new int[players];/*x陣列初始設定*/
    y = new int[players];/*y陣列初始設定*/
    HP = new int[players];/*HP陣列初始設定*/
    for(int i = 0;i < players;i++) {
      x[i] = 320;/*玩家1和玩家2的X軸初始設定*/
      HP[i] = 300;/*生命值初始設定*/
    }
    y[0] = 120;/*玩家1的Y軸初始設定*/
    y[1] = 540;/*玩家2的Y軸初始設定*/
    bullets.clear();/*子彈初始設定*/
    Bigbullets.clear();/*強化彈初始設定*/
    Fire = new boolean[players];/*開火確認初始設定*/
    BFire = new boolean[players];/*開火確認初始設定*/
    Cool = new int[players];/*冷卻時間初始設定*/
    move = new boolean[players][4];/*移動初始設定*/
  }
  
  /*遊戲執行緒*/
  public void run() {
	  boolean SE = true;
    while(SE) {
      for(int i = 0;i < players;i++) {
    	  int PMR = 2;/*自身每次的移動範圍(Person Move Range)為2點*/
          int X = x[i];
          if(move[i][2])X -= PMR;/*X軸座標自身的移動速度-增加的移動範圍*/
          if(move[i][3])X += PMR;/*X軸座標自身的移動速度+增加的移動範圍*/
          int Y = y[i] ;
          if(move[i][0])Y -= PMR;/*Y軸座標自身的移動速度-增加的移動範圍*/
          if(move[i][1])Y += PMR;/*Y軸座標自身的移動速度+增加的移動範圍*/
          if(X > 40 && X < 590)
          	x[i] = X;
          if(Y > (i%2 == 0?40:360) && Y < (i%2 == 0?290:590))/*戰車能夠移動的X(40~590)Y(0~290|70點(牆)|360~640)軸範圍*/
          	y[i] = Y;
          
        if(Cool[i] <= 0) {
          if(Fire[i] && HP[i] > 0)/*確認Fight為true(有按按鍵時)及HP>0,才能執行射擊*/ {
            bullets.add(new Bullet(x[i],y[i] + (i%2 == 0?20:-20),i));/*一般射擊座標及參考值*/
            Cool[i] = 20;/*一般情況下的子彈冷卻時間0.2秒*/
          }
          if(BFire[i] && HP[i] > 0)/*確認BFight為true(有按按鍵時)及HP>0,才能執行射擊*/ {
              Bigbullets.add(new BBullet(x[i],y[i] + (i%2 == 0?20:-20),i));/*特殊射擊座標及參考值*/
              Cool[i] = 50;/*一般情況下的子彈冷卻時間0.5秒*/
            }
        }
        else Cool[i] -- ;/*當下一發子彈出現時的時間*/
        
      }
      /*--------------*/
      for(Bullet bu:bullets) {
        if(bu.btype%2 == 1)bu.by -= 20;
        else bu.by += 20;
        for(int i = 0;i < players;i++) {
          if(bu.btype%2 == i%2 || HP[i] <= 0)
        	  continue;
          if(Math.abs(bu.bx - x[i]) < 25 && Math.abs(bu.by - y[i]) < 25) {
            HP[i] -= 5;/*每次被每顆子彈打中後扣5點血量*/
            bu.dead = true;
          }
        }
      }
      /*--------------*/
      for(BBullet BU:Bigbullets) {
          if(BU.Btype%2 == 1)BU.By -= 20;
          else BU.By += 20;
          for(int i = 0;i < players;i++) {
            if(BU.Btype%2 == i%2 || HP[i] <= 0)
          	  continue;
            if(Math.abs(BU.Bx - x[i]) < 25 && Math.abs(BU.By - y[i]) < 25) {
              HP[i] -= 25;/*每次被每顆子彈打中後扣25點血量*/
              BU.dead = true;
            }
          }
     }
      /*--------------*/
      
      for(int i = 0;i < bullets.size();i++) {
        if(bullets.get(i).by < -50 || bullets.get(i).by > 700 || bullets.get(i).dead)/*子彈出現在銀幕上的範圍*/ {
          bullets.remove(i);
          i--;
        }
      }
      /*--------------*/
      for(int i = 0;i < Bigbullets.size();i++) {
          if(Bigbullets.get(i).By < -50 || Bigbullets.get(i).By > 700 || Bigbullets.get(i).dead)/*強化彈出現在銀幕上的範圍*/ {
        	Bigbullets.remove(i);
            i--;
          }
        }
      repaint();
      /*--------------*/
      try {
        Thread.sleep(20);
      }
      
      catch (InterruptedException e) {
        e.printStackTrace();
      }
      
      int opt_c;
      
      
      if(HP[0] <= 0 && HP[1] <= 0) {
    	  opt_c=JOptionPane.showConfirmDialog
    		      	( null,"!!!不分勝負!!!\n-----------------------------\n確定：繼續遊戲\n否定：關閉遊戲\n取消：保持原狀","勝負顯示",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
    		      	SE = false;
    		      	
    		      	if(opt_c==JOptionPane.YES_OPTION){
    		      		Pstart();/*呼叫Pstart()方法*/
    		            Gstart(2);/*呼叫Gstart()方法,傳入值2*/
    		            run();/*呼叫run()方法*/
    		      	}
    		        else if (opt_c==JOptionPane.NO_OPTION) {
    		        	dispose();
    		                }
    		        else if (opt_c==JOptionPane.CANCEL_OPTION) {
    		                }
      }
      else if(HP[0] > 0 && HP[1] <= 0) {
    	  opt_c=JOptionPane.showConfirmDialog
    		      	( null,"!!!藍方獲勝!!!\n-----------------------------\n確定：繼續遊戲\n否定：關閉遊戲\n取消：保持原狀","勝負顯示",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
    		      	SE = false;
    		      	if(opt_c==JOptionPane.YES_OPTION){
    		      		Pstart();/*呼叫Pstart()方法*/
    		            Gstart(2);/*呼叫Gstart()方法,傳入值2*/
    		            run();/*呼叫run()方法*/
    		      	}
    		        else if (opt_c==JOptionPane.NO_OPTION) {
    		        	dispose();
    		                }
    		        else if (opt_c==JOptionPane.CANCEL_OPTION) {
    		                }
      }
      else if(HP[1] > 0 && HP[0] <= 0) {
    	opt_c=JOptionPane.showConfirmDialog
      	( null,"!!!綠方獲勝!!!\n-----------------------------\n確定：繼續遊戲\n否定：關閉遊戲\n取消：保持原狀","勝負顯示",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
      	SE = false;
      	
      	if(opt_c==JOptionPane.YES_OPTION){
      		Pstart();/*呼叫Pstart()方法*/
            Gstart(2);/*呼叫Gstart()方法,傳入值2*/
            run();/*呼叫run()方法*/
      	}
        else if (opt_c==JOptionPane.NO_OPTION) {
        	dispose();
                }
        else if (opt_c==JOptionPane.CANCEL_OPTION) {
                }
      	}
    }
      	
      }
  public void paint(Graphics paint) {
    Image offscreen = createImage(630, 630);
    Graphics2D g = (Graphics2D)offscreen.getGraphics();    
    g.fillRect(0, 0, getWidth(), getHeight());
    for(int i = 0;i < players;i++) {
      g.setColor(new Color[]{Color.blue,Color.green}[i]);
      if(HP[i] > 0) {
    	  g.fill3DRect(x[i] - 25, y[i] - 25, 50, 50, true);
    	  /*戰車底座中間圓圈的顏色.位置設定*/
    	  g.setColor(Color.darkGray);/*暗灰色*/
    	  g.fillOval(x[i] -18, y[i] - 18, 35, 35);
    	  /*砲管顏色.位置設定*/
    	  g.setColor(Color.orange);
    	  if(i%2 == 0) {
    		  g.fill3DRect(x[i] - 5, y[i] - 5, 10, 40, true);/*藍隊砲管位置*/
    	  }
    	  else {
    		  g.fill3DRect(x[i] - 5, y[i] - 35, 10, 40, true);/*綠隊砲管位置*/
    	  }
      }
    }
    /*一般子彈顏色*/
    g.setColor(Color.red);
    for(Bullet b:bullets)
    	g.fillOval(b.bx - 6, b.by, 10, 15);/*一般子彈大小位置*/
    /*強化子彈顏色*/
    g.setColor(Color.red);
    for(BBullet B:Bigbullets)
    	g.fillOval(B.Bx - 6, B.By, 10, 25);/*強化子彈大小位置*/
    /*顯示血量顏色.字型.大小.位置設定*/
    g.setColor(Color.white);
    g.setFont(new Font("微軟正黑體 Light", Font.PLAIN|Font.BOLD, 18));
    for(int i = 0;i < players;i++) {
    	if(HP[i] > 0) {
    	  if(i%2 == 0) {
    		g.drawString(HP[i] + "", x[i] - 18, y[i] - 33);/*藍隊血量位置*/
      	  }
      	  else {
      		g.drawString(HP[i] + "", x[i] - 18, y[i] + 48);/*綠隊血量位置*/
      	  }
    	}	
    }
    paint.drawImage(offscreen, 0, 0, getWidth(), getHeight(), null);
  }

}
class Bullet {
  int bx,by,btype;
  boolean dead = false;
  Bullet(int x,int y,int t) {
    this.bx = x;
    this.by = y;
    this.btype = t;//屬於哪隊的
  }
}
class BBullet {
	int Bx,By,Btype;
	boolean dead = false;
	BBullet(int x,int y,int t) {
		this.Bx = x;
		this.By = y;
		this.Btype = t;
	}
}