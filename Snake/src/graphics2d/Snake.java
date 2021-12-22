package graphics2d;

import java.util.Arrays;
import java.util.Random;

public class Snake {
    Scene2d snakeDisplay;
    Screen screen;
    int snakeLength;
    rectangle[] bodyPosition;
    double bodySize=35;
    //0=right,1=down,2=left,3=up
    int directionVertical;
    int directionHorizontal;
    double timeLast;
    double speed=bodySize;
    double[] foodPos;
    rectangle food;
    Random rand= new Random();
    //boolean




    Snake(){
        snakeDisplay = new Scene2d();

        screen= new Screen(960,520);
        snakeLength=0;
        bodyPosition= new rectangle[80];
        directionHorizontal=1;

        foodPos=new double[2];
        food=new rectangle(bodySize,bodySize,0,0);
        addFruit();
        snakeDisplay.addObject(food);
        addBody();
        addBody();
        addBody();
        addBody();

    }

    public void resetGame(){
        snakeDisplay = new Scene2d();

        screen= new Screen(960,520);
        snakeLength=0;
        bodyPosition= new rectangle[80];
        directionHorizontal=1;
        directionVertical=0;
        foodPos=new double[2];
        food=new rectangle(bodySize,bodySize,0,0);
        addFruit();
        snakeDisplay.addObject(food);
        addBody();
        addBody();
        addBody();
        addBody();
    }
    public void addFruit(){
        double x=bodySize/2+rand.nextInt((int)(screen.pixelGrid.length/bodySize))*bodySize;
        double y=bodySize/2+rand.nextInt((int)(screen.pixelGrid[0].length/bodySize))*bodySize;
        boolean spotFound=false;
        while(!spotFound){
            x=bodySize/2+rand.nextInt((int)(screen.pixelGrid.length/bodySize))*bodySize;
            y=bodySize/2+rand.nextInt((int)(screen.pixelGrid[0].length/bodySize))*bodySize;
            spotFound=true;
            for(int i=1;i<snakeLength;i++){
                double[] bodpos=bodyPosition[i].getPosition();
                if(Math.abs(bodpos[0]-x)<bodySize/2 && Math.abs(bodpos[1]-y)<bodySize/2 ){
                    spotFound=false;
                }
            }
        }

        foodPos[0]=x;
        foodPos[1]=y;
        food.setPosition(x,y);


    }

    public void addBody(){

        if(snakeLength==0){
            bodyPosition[snakeLength]=new rectangle(bodySize,bodySize,(int)(bodySize/2)+(int)(((int)((screen.pixelGrid.length/bodySize)/2))*bodySize),(int)(bodySize/2)+(int)(((int)((screen.pixelGrid[0].length/bodySize)/2))*bodySize));
        }else{
            bodyPosition[snakeLength]=new rectangle(bodySize,bodySize,0,0);
            bodyPosition[snakeLength].setPosition((int)(this.bodyPosition[snakeLength-1].getPosition()[0]-speed*directionHorizontal),(int)(this.bodyPosition[snakeLength-1].getPosition()[1]-speed*directionVertical));
        }

        double[] col={0,0,255,0};
        bodyPosition[snakeLength].setColor(col);
        this.snakeDisplay.addObject(this.bodyPosition[snakeLength]);
        snakeLength+=1;
    }




    public void playSnake(){



        double counter=0;
        while(true){
            double newTime=System.nanoTime();
            double deltaTime= (newTime-this.timeLast)/(double)1000000000;
            this.timeLast=newTime;


            counter+=deltaTime;






            if(this.directionHorizontal==0 && DrawWindow.right!=0){
                this.directionHorizontal=DrawWindow.right;
                this.directionVertical=0;
            }
            if(this.directionVertical==0 && DrawWindow.forward!=0){
                this.directionVertical=DrawWindow.forward;
                this.directionHorizontal=0;
            }




            if(counter>=0.1){
                //System.out.println("H"+this.directionHorizontal);
                //System.out.println("V"+this.directionVertical);
                counter=0;
                for(int i=this.snakeLength-1;i>0;i--){
                    this.bodyPosition[i].setPosition(this.bodyPosition[i-1].getPosition()[0],this.bodyPosition[i-1].getPosition()[1]);
                }
                this.bodyPosition[0].translate(this.speed* this.directionHorizontal, this.speed* this.directionVertical);
            }

            if(Math.abs(bodyPosition[0].getPosition()[0]-foodPos[0])<bodySize/2 && Math.abs(bodyPosition[0].getPosition()[1]-foodPos[1])<bodySize/2 ){
                System.out.println("Score:"+(this.snakeLength-3));
                addBody();
                addFruit();
            }
            boolean collision;
            double[] headPos=bodyPosition[0].getPosition();

            for(int i=1;i<snakeLength;i++){
                double[] bodpos=bodyPosition[i].getPosition();
                if(Math.abs(bodpos[0]-headPos[0])<bodySize/2 && Math.abs(bodpos[1]-headPos[1])<bodySize/2 ){
                    resetGame();
                }
            }
            if(headPos[0]>screen.pixelGrid.length || headPos[0]<0 || headPos[1]>screen.pixelGrid[0].length || headPos[1]<0){
                resetGame();
            }

            this.screen.clear();
            this.screen.drawScene2dColorInterpolated(this.snakeDisplay);
            this.screen.gridResize();
            this.screen.windowUpdate();

        }


    }
/*
    public static void main(String[] args){
        System.out.println("cheese");    }
*/
}
