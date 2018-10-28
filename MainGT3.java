package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainGT3 {
	  public static void main(String[] args) {
		  MyJFrame N = new MyJFrame();/*�s�WN����MyJFrame���O�ð���*/
//		  N.dispose();
	  }
}

class MyJFrame extends JFrame{
  int x[],y[];/*���ax�y��,y�y��*/ int players;/*���a�H��*/
  int HP[];/*��q*/  int Cool[];/*�l�u�N�o�ɶ�*/
  boolean Fire[],BFire[];/*�o�g�P�_*/  boolean move[][];/*���ʻP�_*/
  ArrayList<Bullet> bullets = new ArrayList<Bullet>();/*�l�u���}�C*/
  ArrayList<BBullet> Bigbullets = new ArrayList<BBullet>();/*�j�Ƽu���}�C*/
  
  MyJFrame() {
	setTitle("�p�C��:�Z�J�Ԫ�PvP");
    Pstart();/*�I�sPstart()��k*/
    Gstart(2);/*�I�sGstart()��k,�ǤJ��2*/
    run();/*�I�srun()��k*/
  }
  
  /*�{����l��*/
  public void Pstart() {
    setSize(640,640);/*�X�{���ȹ��j�p(�e,��)*/
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
          JOptionPane.showMessageDialog(null,"1P(�W��)\n ��V��(�W�U���k)���ʡA?���q�u;>�j�Ƽu\n\n2P(�U��)wasd���ʡAq���q�ue�j�Ƽu","�C�����U",JOptionPane.INFORMATION_MESSAGE);
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
  
  /*�C���C������l��*/
  public void Gstart(int n)/*n = 2*/ {
	players = n;/*���a�H�ƪ�l�]�w*/
    x = new int[players];/*x�}�C��l�]�w*/
    y = new int[players];/*y�}�C��l�]�w*/
    HP = new int[players];/*HP�}�C��l�]�w*/
    for(int i = 0;i < players;i++) {
      x[i] = 320;/*���a1�M���a2��X�b��l�]�w*/
      HP[i] = 300;/*�ͩR�Ȫ�l�]�w*/
    }
    y[0] = 120;/*���a1��Y�b��l�]�w*/
    y[1] = 540;/*���a2��Y�b��l�]�w*/
    bullets.clear();/*�l�u��l�]�w*/
    Bigbullets.clear();/*�j�Ƽu��l�]�w*/
    Fire = new boolean[players];/*�}���T�{��l�]�w*/
    BFire = new boolean[players];/*�}���T�{��l�]�w*/
    Cool = new int[players];/*�N�o�ɶ���l�]�w*/
    move = new boolean[players][4];/*���ʪ�l�]�w*/
  }
  
  /*�C�������*/
  public void run() {
	  boolean SE = true;
    while(SE) {
      for(int i = 0;i < players;i++) {
    	  int PMR = 2;/*�ۨ��C�������ʽd��(Person Move Range)��2�I*/
          int X = x[i];
          if(move[i][2])X -= PMR;/*X�b�y�Цۨ������ʳt��-�W�[�����ʽd��*/
          if(move[i][3])X += PMR;/*X�b�y�Цۨ������ʳt��+�W�[�����ʽd��*/
          int Y = y[i] ;
          if(move[i][0])Y -= PMR;/*Y�b�y�Цۨ������ʳt��-�W�[�����ʽd��*/
          if(move[i][1])Y += PMR;/*Y�b�y�Цۨ������ʳt��+�W�[�����ʽd��*/
          if(X > 40 && X < 590)
          	x[i] = X;
          if(Y > (i%2 == 0?40:360) && Y < (i%2 == 0?290:590))/*�Ԩ�������ʪ�X(40~590)Y(0~290|70�I(��)|360~640)�b�d��*/
          	y[i] = Y;
          
        if(Cool[i] <= 0) {
          if(Fire[i] && HP[i] > 0)/*�T�{Fight��true(���������)��HP>0,�~�����g��*/ {
            bullets.add(new Bullet(x[i],y[i] + (i%2 == 0?20:-20),i));/*�@��g���y�ФΰѦҭ�*/
            Cool[i] = 20;/*�@�뱡�p�U���l�u�N�o�ɶ�0.2��*/
          }
          if(BFire[i] && HP[i] > 0)/*�T�{BFight��true(���������)��HP>0,�~�����g��*/ {
              Bigbullets.add(new BBullet(x[i],y[i] + (i%2 == 0?20:-20),i));/*�S��g���y�ФΰѦҭ�*/
              Cool[i] = 50;/*�@�뱡�p�U���l�u�N�o�ɶ�0.5��*/
            }
        }
        else Cool[i] -- ;/*��U�@�o�l�u�X�{�ɪ��ɶ�*/
        
      }
      /*--------------*/
      for(Bullet bu:bullets) {
        if(bu.btype%2 == 1)bu.by -= 20;
        else bu.by += 20;
        for(int i = 0;i < players;i++) {
          if(bu.btype%2 == i%2 || HP[i] <= 0)
        	  continue;
          if(Math.abs(bu.bx - x[i]) < 25 && Math.abs(bu.by - y[i]) < 25) {
            HP[i] -= 5;/*�C���Q�C���l�u�����ᦩ5�I��q*/
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
              HP[i] -= 25;/*�C���Q�C���l�u�����ᦩ25�I��q*/
              BU.dead = true;
            }
          }
     }
      /*--------------*/
      
      for(int i = 0;i < bullets.size();i++) {
        if(bullets.get(i).by < -50 || bullets.get(i).by > 700 || bullets.get(i).dead)/*�l�u�X�{�b�ȹ��W���d��*/ {
          bullets.remove(i);
          i--;
        }
      }
      /*--------------*/
      for(int i = 0;i < Bigbullets.size();i++) {
          if(Bigbullets.get(i).By < -50 || Bigbullets.get(i).By > 700 || Bigbullets.get(i).dead)/*�j�Ƽu�X�{�b�ȹ��W���d��*/ {
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
    		      	( null,"!!!�����ӭt!!!\n-----------------------------\n�T�w�G�~��C��\n�_�w�G�����C��\n�����G�O���쪬","�ӭt���",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
    		      	SE = false;
    		      	
    		      	if(opt_c==JOptionPane.YES_OPTION){
    		      		Pstart();/*�I�sPstart()��k*/
    		            Gstart(2);/*�I�sGstart()��k,�ǤJ��2*/
    		            run();/*�I�srun()��k*/
    		      	}
    		        else if (opt_c==JOptionPane.NO_OPTION) {
    		        	dispose();
    		                }
    		        else if (opt_c==JOptionPane.CANCEL_OPTION) {
    		                }
      }
      else if(HP[0] > 0 && HP[1] <= 0) {
    	  opt_c=JOptionPane.showConfirmDialog
    		      	( null,"!!!�Ť����!!!\n-----------------------------\n�T�w�G�~��C��\n�_�w�G�����C��\n�����G�O���쪬","�ӭt���",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
    		      	SE = false;
    		      	if(opt_c==JOptionPane.YES_OPTION){
    		      		Pstart();/*�I�sPstart()��k*/
    		            Gstart(2);/*�I�sGstart()��k,�ǤJ��2*/
    		            run();/*�I�srun()��k*/
    		      	}
    		        else if (opt_c==JOptionPane.NO_OPTION) {
    		        	dispose();
    		                }
    		        else if (opt_c==JOptionPane.CANCEL_OPTION) {
    		                }
      }
      else if(HP[1] > 0 && HP[0] <= 0) {
    	opt_c=JOptionPane.showConfirmDialog
      	( null,"!!!������!!!\n-----------------------------\n�T�w�G�~��C��\n�_�w�G�����C��\n�����G�O���쪬","�ӭt���",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
      	SE = false;
      	
      	if(opt_c==JOptionPane.YES_OPTION){
      		Pstart();/*�I�sPstart()��k*/
            Gstart(2);/*�I�sGstart()��k,�ǤJ��2*/
            run();/*�I�srun()��k*/
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
    	  /*�Ԩ����y������骺�C��.��m�]�w*/
    	  g.setColor(Color.darkGray);/*�t�Ǧ�*/
    	  g.fillOval(x[i] -18, y[i] - 18, 35, 35);
    	  /*�����C��.��m�]�w*/
    	  g.setColor(Color.orange);
    	  if(i%2 == 0) {
    		  g.fill3DRect(x[i] - 5, y[i] - 5, 10, 40, true);/*�Ŷ����ަ�m*/
    	  }
    	  else {
    		  g.fill3DRect(x[i] - 5, y[i] - 35, 10, 40, true);/*�񶤯��ަ�m*/
    	  }
      }
    }
    /*�@��l�u�C��*/
    g.setColor(Color.red);
    for(Bullet b:bullets)
    	g.fillOval(b.bx - 6, b.by, 10, 15);/*�@��l�u�j�p��m*/
    /*�j�Ƥl�u�C��*/
    g.setColor(Color.red);
    for(BBullet B:Bigbullets)
    	g.fillOval(B.Bx - 6, B.By, 10, 25);/*�j�Ƥl�u�j�p��m*/
    /*��ܦ�q�C��.�r��.�j�p.��m�]�w*/
    g.setColor(Color.white);
    g.setFont(new Font("�L�n������ Light", Font.PLAIN|Font.BOLD, 18));
    for(int i = 0;i < players;i++) {
    	if(HP[i] > 0) {
    	  if(i%2 == 0) {
    		g.drawString(HP[i] + "", x[i] - 18, y[i] - 33);/*�Ŷ���q��m*/
      	  }
      	  else {
      		g.drawString(HP[i] + "", x[i] - 18, y[i] + 48);/*�񶤦�q��m*/
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
    this.btype = t;//�ݩ������
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