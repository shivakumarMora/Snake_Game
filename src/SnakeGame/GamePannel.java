package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import static java.awt.Font.ITALIC;
public class GamePannel extends JPanel  implements ActionListener, KeyListener {

    private int[] snakexlength=new int[750];
    private int[] snakeylength=new int[750];
    private int[] xpos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private int[] ypos={100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    private Random random=new Random();
    private int foodx,foody;
    private int LengthOfSnake=3;
    private boolean left=false;
    private boolean right=true;
    private boolean up=false;

    private boolean down=false;
    boolean gameOver=false;

    int moves=0;
    int score=0;
    private ImageIcon snaketitle=new ImageIcon(getClass().getResource("snaketitle.jpeg"));
    private ImageIcon leftmouth=new ImageIcon(getClass().getResource("leftmouth.jpeg"));
    private ImageIcon rightmouth=new ImageIcon(getClass().getResource("rightmouth.jpeg"));
    private ImageIcon upmouth=new ImageIcon(getClass().getResource("upmouth.jpeg"));
    private ImageIcon downmouth=new ImageIcon(getClass().getResource("downmouth.jpeg"));
    private ImageIcon snakeimage=new ImageIcon(getClass().getResource("snakeimage.jpeg"));
    private ImageIcon enemy=new ImageIcon(getClass().getResource("enemy.jpeg"));
    private Timer timer;
    private int delay=100;
    GamePannel(){
        addKeyListener(this);
        setFocusable(true);
        //   setFocusTraversalKeys(true);
        timer=new Timer(delay,  this);
        timer.start();
        newFood();

    }

    private void newFood() {
        foodx=xpos[random.nextInt(34)];
        foody=ypos[random.nextInt(21)];
        for(int i=LengthOfSnake-1;i>=0;i--){
            if(snakexlength[i]==foodx&&snakeylength[i]==foody){
                newFood();
            }
        }
    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.blue);
        g.drawRect(24,10,851,55);
        g.drawRect(24,74,851,576);

        snaketitle.paintIcon(this,g,25,11);
        g.setColor(Color.black);
        g.fillRect(25,75,850,575);
        if(moves==0){
            snakexlength[0]=100;
            snakexlength[1]=75;
            snakexlength[2]=50;

            snakeylength[0]=100;
            snakeylength[1]=100;
            snakeylength[2]=100;


        }
        if(left){
            leftmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
        }
        if(right){
            rightmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
        }
        if(up){
            upmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
        }
        if(down){
            downmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
        }
        for(int i=1;i<LengthOfSnake;i++){
            snakeimage.paintIcon(this,g,snakexlength[i],snakeylength[i]);
        }
        enemy.paintIcon(this,g,foodx,foody);
        if(gameOver){
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));

            g.drawString("Game over",300,300);
            g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,10));
            g.drawString("Press Space to restart",330,360);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.PLAIN,15));
        g.drawString("Score : "+ score,750,30);
        g.drawString("Length : "+LengthOfSnake, 750,50);
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=LengthOfSnake-1;i>0;i--){
            snakexlength[i]=snakexlength[i-1];
            snakeylength[i]=snakeylength[i-1];
        }
        if(left){
            snakexlength[0]=snakexlength[0]-25;
        }
        if(right){
            snakexlength[0]=snakexlength[0]+25;
        }
        if(up){
            snakeylength[0]=snakeylength[0]-25;
        }
        if(down){
            snakeylength[0]=snakeylength[0]+25;
        }
        if(snakexlength[0]>850) snakexlength[0]=25;
        if(snakexlength[0]<25) snakexlength[0]=850;
        if(snakeylength[0]>625) snakeylength[0]=75;
        if(snakeylength[0]<75) snakeylength[0]=625;
        collideswithenemy();
        collideswithbody();
        repaint();
    }

    private void collideswithbody() {
        for(int i=LengthOfSnake-1;i>0;i--){
            if(snakexlength[i]==snakexlength[0]&&snakeylength[i]== snakeylength[0]){
                timer.stop();
                gameOver=true;
            }
        }
    }

    private void collideswithenemy() {
        if(snakexlength[0]==foodx&&snakeylength[0]==foody)
        {
            newFood();
            LengthOfSnake++;
            snakexlength[LengthOfSnake-1]=snakexlength[LengthOfSnake-2];
            snakeylength[LengthOfSnake-1]=snakeylength[LengthOfSnake-2];
            score++;
        }
    }
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE && gameOver)
        {
            restart();
        }



        if(e.getKeyCode()==KeyEvent.VK_LEFT&&(!right)){
            left=true;
            right=false;
            up=false;
            down=false;
            moves++;
        }if(e.getKeyCode()==KeyEvent.VK_RIGHT&&(!left)){
            left=false;
            right=true;
            up=false;
            down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP&&(!down)){
            left=false;
            right=false;
            up=true;
            down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN&&(!up)){
            left=false;
            right=false;
            up=false;
            down=true;
            moves++;
        }
    }

    private void restart() {
        gameOver=false;
        moves=0;
        score=0;
        LengthOfSnake=3;
        left=false;
        right=true;
        up=false;
        down=false;
        timer.start();
        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
}
