package tankGame04;

import javax.swing.*;

public class TankGame04 extends JFrame {

//    define a Panel
    MyPanel mp = null;

    public static void main(String[] args) {
        TankGame04 tankGame01 = new TankGame04();
    }

    public TankGame04(){
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
