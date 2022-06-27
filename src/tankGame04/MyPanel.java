package tankGame04;

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

//    define Vector to store bombs.
//    When shot attack tank, add one Bomb obj to bombs
    Vector<Bomb> bombs = new Vector<>();

    int enemyTankSize = 3;

//    load 2 image to display bomb effect
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel(){
//        initialize tanks
        hero = new Hero(100,100);
        for(int i=0; i<enemyTankSize;i++){
//            create a new enemy
            EnemyTank enemyTank = new EnemyTank(100*(i+1), 0);
//            set direction
            enemyTank.setDirect(2);

            Shot shot = new Shot(enemyTank.getX() + 20,enemyTank.getY() + 60,enemyTank.getDirect());
//            add the shot object to the shots vector
            enemyTank.shots.add(shot);
//            start shooting
            new Thread(shot).start();

            enemyTanks.add(enemyTank);
        }

//        initialize image
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_3.gif"));

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

//        if bombs not empty, draw bomb
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
//            draw bomb based on its current life
            if(bomb.life > 6){
                g.drawImage(image1,bomb.x, bomb.y, 60,60,this);
            }else if(bomb.life > 3){
                g.drawImage(image2,bomb.x, bomb.y, 60,60,this);
            }else{
                g.drawImage(image3,bomb.x, bomb.y, 60, 60, this);
            }

            //decrement life
            bomb.lifeDown();

//            if life == 0, remove from bombs
            if(bomb.life == 0){
                bombs.remove(bomb);
            }


        }

//        draw enemy tank
        for(int i=0; i<enemyTanks.size();i++){
            EnemyTank enemyTank = enemyTanks.get(i);

//            if tank is alive, draw tank
            if(enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);

                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
//                    draw shot if live
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 1, 1, false);
                    } else {
                        //remove from Vector
                        enemyTank.shots.remove(shot);
                    }
                }
            }
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

//    check if our shot has hit the enemy tank and make changes
    public void hitTank(Shot s, Tank enemyTank){
        switch ((enemyTank.getDirect())){
            case 0: //up
            case 2: //down
                if(s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40
                && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60){
                    s.isLive = false;
                    enemyTank.isLive = false;
//                    remove dead enemy tank from Vector
                    enemyTanks.remove(enemyTank);
//                    create Bomb obj and add to bombs
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);

                }
                break;
            case 1: //right
            case 3: //left;
                if(s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40){
                    s.isLive = false;
                    enemyTank.isLive = false;
//                    remove dead enemy tank from Vector
                    enemyTanks.remove(enemyTank);
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
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

//            check if attack the enemy tank
            if(hero.shot != null && hero.shot.isLive){

                for(int i=0; i<enemyTanks.size();i++){
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(hero.shot,enemyTank);
                }
            }
            this.repaint();
        }
    }
}
