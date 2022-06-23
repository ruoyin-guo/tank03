package tankGame03;

import javax.swing.*;

public class TankGame03 extends JFrame {

//    define a Panel
    MyPanel mp = null;

    public static void main(String[] args) {
        TankGame03 tankGame01 = new TankGame03();
    }

    public TankGame03(){
        mp = new MyPanel();
//        repaint panel
        Thread thread = new Thread(mp);
        thread.start();

        this.add(mp);
        this.setSize(1000,750);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
}
