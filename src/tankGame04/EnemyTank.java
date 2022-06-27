package tankGame04;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
//    use Vector to store multiple shots
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while (true){
//            continue move based on direction
            switch (getDirect()){
                case 0: //up
                    for (int i = 0; i < 30; i++) {
//                        stop moving when meeting edge
                        if(getY() > 0){
                            moveUp();
                        }


                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                case 1: // right
                    for (int i = 0; i < 30; i++) {
                        if(getX() + 60 < 1000){
                            moveRight();
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2: // down
                    for (int i = 0; i < 30; i++) {
                        if(getY() + 60 < 750){
                            moveDown();
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                case 3: // left
                    for (int i = 0; i < 30; i++) {
                        if(getX() > 0){
                            moveLeft();
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }


//            randomly change the direction 0-4
            setDirect((int)(Math.random()*4));

//          condition to quit thread
            if(!isLive){
                break; //exit
            }
        }
    }
}
