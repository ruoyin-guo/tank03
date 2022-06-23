package tankGame03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener,Runnable {
//    define my tank
    Hero hero = null;
//    define enemy tank, put into vector
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTankSize = 3;

    public MyPanel(){
//        initialize tanks
        hero = new Hero(100,100);
        for(int i=0; i<enemyTankSize;i++){
            EnemyTank enemyTank = new EnemyTank(100*(i+1), 0);
            enemyTank.setDirect(2);
            enemyTanks.add(enemyTank);
        }


    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);

        //draw our tank
        drawTank(hero.getX(),hero.getY(),g,hero.getDirect(),1);

//        draw shot from our tank
//        hero.shot.setSpeed(5);
        if(hero.shot!= null && hero.shot.isLive){
            g.draw3DRect(hero.shot.x, hero.shot.y,2,2,false);
        }

//        draw enemy tank
        for(int i=0; i<enemyTanks.size();i++){
            EnemyTank enemyTank = enemyTanks.get(i);
            drawTank(enemyTank.getX(),enemyTank.getY(),g,enemyTank.getDirect(),0);
        }




    }

    /**
     *
     * @param x left upper x-coordinate of tank
     * @param y left upper y-coordinate of tank
     * @param g painter
     * @param direct direction(up,down, left, right)
     * @param type type of tank
     */
//    draw tank
    public void drawTank(int x, int y, Graphics g, int direct, int type){

//        set the color according to tank type
        switch (type){
            case 0: //enemy tank
                g.setColor(Color.cyan);
                break;
            case 1: // our tank
                g.setColor(Color.yellow);
                break;

        }

//        draw tank based on direction
        // 0:up
        // 1: right
        // 2: down
        // 3: left
        switch (direct){
            case 0: // up
                g.fill3DRect(x, y, 10,60,false); //left wheel
                g.fill3DRect(x + 30, y , 10,60,false); //right wheel
                g.fill3DRect(x + 10, y + 10, 20, 40, false); // cap
                g.fillOval(x+10,y+20,20,20); // circle cap
                g.drawLine(x+20, y, x+20, y+30);
                break;
            case 1: // right
                g.fill3DRect(x, y, 60,10,false); //left wheel
                g.fill3DRect(x, y + 30 , 60,10,false); //right wheel
                g.fill3DRect(x + 10, y + 10, 40, 20, false); // cap
                g.fillOval(x+20,y+10,20,20); // circle cap
                g.drawLine(x+30, y+20, x+60, y+20);
                break;
            case 2: // down
                g.fill3DRect(x, y, 10,60,false); //left wheel
                g.fill3DRect(x + 30, y , 10,60,false); //right wheel
                g.fill3DRect(x + 10, y + 10, 20, 40, false); // cap
                g.fillOval(x+10,y+20,20,20); // circle cap
                g.drawLine(x+20, y+30, x+20, y+60); //attack
                break;
            case 3: // left
                g.fill3DRect(x, y, 60,10,false); //left wheel
                g.fill3DRect(x, y + 30 , 60,10,false); //right wheel
                g.fill3DRect(x + 10, y + 10, 40, 20, false); // cap
                g.fillOval(x+20,y+10,20,20); // circle cap
                g.drawLine(x+30, y+20, x, y+20);
                break;
            default:
                System.out.println("no action");
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

//    wdsa
    @Override
    public void keyPressed(KeyEvent e) {
//        press "W"
        if(e.getKeyCode() == KeyEvent.VK_W){ //up
//            change direction
            hero.setDirect(0);
//            change coordinate
            hero.moveUp();
        }else if(e.getKeyCode() == KeyEvent.VK_D){ //right
            hero.setDirect(1);
            hero.moveRight();
        }else if(e.getKeyCode() == KeyEvent.VK_S){ //down
            hero.setDirect(2);
            hero.moveDown();
        }else if(e.getKeyCode() == KeyEvent.VK_A){ //left
            hero.setDirect(3);
            hero.moveLeft();
        }

        //user press J, shot
        if(e.getKeyCode() == KeyEvent.VK_J) {
            System.out.println("press J, start shooting");
            hero.shotEnemyTank();
        }

        this.repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
//        in order to make the shot move, repaint the panel every 100ms
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.repaint();
        }
    }
}
