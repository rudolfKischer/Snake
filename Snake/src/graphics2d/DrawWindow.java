package graphics2d;
import java.awt.*;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.AWTException;
import java.util.Arrays;
import java.util.Random;


public class DrawWindow extends JFrame {
    public static Snake newSnake=new Snake();
    public static int[][][] pxlGrid=newSnake.screen.outputPixelGrid;
    public static DrawWindow drawWindow = new DrawWindow("art",pxlGrid.length,pxlGrid[0].length);
    public static int forward=0;
    public static int right=0;
    public static int up=0;
    public static int mouseX;
    public static boolean mouseLock;
    public static int mouseY;
    private Image dbImage;
    private Graphics dbg;

    public DrawWindow(String title,int h, int l) {
        super(title);

        setSize(h,l);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g){
        dbImage=createImage(getWidth(),getHeight());
        dbg= dbImage.getGraphics();
        paintComponent(dbg);
        g.drawImage(dbImage,0,0,this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponents(g);


        for(int i=0;i<pxlGrid.length;i++){
            for(int j=0;j<pxlGrid[0].length;j++) {
                    drawPixel(g, i,pxlGrid[0].length-j , pxlGrid[i][j]);

            }
        }
    }

    public void paintImmediately(int x, int y,int w,int h){



    }




    public void drawPixel(Graphics g,int x, int y,int[] rgb){

        g.setColor(new Color(rgb[0],rgb[1],rgb[2]));
        g.drawRect(x, y, 1, 1);
    }

    public static void main(String[] args) throws InterruptedException {


        Snake newSnake=new Snake();

        drawWindow.addMouseMotionListener(new MouseMotionListener()  {
            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.println("mouseDraggedx"+e.getX());
                System.out.println("mouseDraggedy"+e.getY());

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //System.out.println("mouseMovedx"+e.getX());
                //System.out.println("mouseMovedy"+e.getY());
                if(mouseLock){
                    mouseX=e.getY();
                    mouseY=e.getX();
                }else{
                    mouseX=pxlGrid.length/2;
                    mouseY=pxlGrid[0].length/2;
                }

                try {
                    Robot r = new Robot();
                    if(mouseLock){
                        r.mouseMove((int)(pxlGrid.length/2.0),(int)(pxlGrid[0].length/2.0));
                    }

                } catch (AWTException awtException) {
                    awtException.printStackTrace();
                }



            }
        });

        drawWindow.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_W){
                    forward=1;
                }
                if(e.getKeyCode()==KeyEvent.VK_S){
                    forward=-1;
                }
                if(e.getKeyCode()==KeyEvent.VK_D){
                    right=1;
                }
                if(e.getKeyCode()==KeyEvent.VK_A){
                    right=-1;
                }
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    up=1;
                }
                if(e.getKeyCode()==KeyEvent.VK_SHIFT){
                    up=-1;
                }
                if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
                    if(mouseLock){
                        mouseLock=false;
                    }else{
                        mouseLock=true;
                        mouseX=pxlGrid[0].length/2;
                        mouseY=pxlGrid.length/2;

                    }

                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_W){
                    forward=0;
                }
                if(e.getKeyCode()==KeyEvent.VK_S){
                    forward=0;
                }
                if(e.getKeyCode()==KeyEvent.VK_A){
                    right=0;
                }
                if(e.getKeyCode()==KeyEvent.VK_D){
                    right=0;
                }
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    up=0;
                }
                if(e.getKeyCode()==KeyEvent.VK_SHIFT){
                    up=0;
                }
            }
        });

        newSnake.playSnake();




    }


}
