package tankGame04;

import java.util.Vector;

public class EnemyTank extends Tank {
//    use Vector to store multiple shots
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
    }
}
