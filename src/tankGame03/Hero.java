package tankGame03;

public class Hero extends Tank {
//    create shot for our tank
    Shot shot = null;

    public Hero(int x, int y) {
        super(x, y);
    }

    public void shotEnemyTank(){
        switch (getDirect()){
            case 0: //up
                shot = new Shot(getX() + 20,getY(),0);
                break;
            case 1: //right
                shot = new Shot(getX() + 60,getY() + 20,1);
                break;
            case 2: //down
                shot = new Shot(getX()+ 20 ,getY()+60,2);
                break;
            case 3: //left
                shot = new Shot(getX(),getY() + 20,3);
                break;
        }

//        start shot thread
        new Thread(shot).start();

    }

}
