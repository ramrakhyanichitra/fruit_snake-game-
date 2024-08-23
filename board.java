package snakegame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class board extends JPanel implements ActionListener{
    private Image apple;
    private Image head;
    private Image dot;
    private final int ALL_DOTS=900;
    private final int DOT_SIZE=10;
    private final int x[]= new int[ALL_DOTS];
    private final int y[]= new int[ALL_DOTS];
    private boolean leftdirection=false;
    private boolean rightdirection=true; // initially move to right only
    private boolean updirection=false;
    private boolean downdirection=false;
    private boolean inGame=true;
    private final int RANDOM_POSITION=29;
    private int apple_x;
    private int apple_y;
    private Timer timer;
    private int dots;
    
    private int score;
    
    board()
    {   addKeyListener(new TAdapter());
    
        setBackground(Color.black);
        setPreferredSize(new Dimension(300,300));
        setFocusable(true);
        loadImages();
        initGame();
    }
    public void loadImages()
    {
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("snakegame/apple 1.png"));
        apple=i1.getImage();
        ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("snakegame/dot 1.png"));
        dot=i2.getImage();
        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("snakegame/head 1.png"));
        head=i3.getImage();

    }
    public void initGame()
    {
        dots=3;
        for(int i=0;i<dots;i++)
        {
            y[i]=50;
            x[i]=50-(i*DOT_SIZE);
        }
        locateApple();
        timer=new Timer(100,this);
        timer.start();
    }
    public void locateApple()
    {
        int r=(int)(Math.random() * RANDOM_POSITION); // returns value in float so typecasted to int
        apple_x=r*DOT_SIZE;
        r=(int)(Math.random()*RANDOM_POSITION);
        apple_y=r*DOT_SIZE;
        
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
        
    }
    public void draw(Graphics g)
    {   if(inGame)
        {
            g.drawImage(apple,apple_x,apple_y,15,15, this);
            for(int i=0;i<dots;i++)
            {
                if(i==0)
                   g.drawImage(head, x[i],y[i],15,15,this);
                else
                {
                   g.drawImage(dot, x[i], y[i],15,15,this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
       else{
           gameOver(g);
       }
        
    }
    public void gameOver(Graphics g)
    {
        String msg="GAMEOVER";
        String msg2="SCORE:";
        Font f=new Font("SAN SERIF",Font.BOLD,14);
        FontMetrics metrices=getFontMetrics(f);
        g.setColor(Color.white);
        g.setFont(f);
        g.drawString(msg,((300-metrices.stringWidth(msg))/2), 300/2);
        g.drawString(msg2+score,((300-metrices.stringWidth(msg))/2),(300/2)+20);
       
    }
    public void move()
    {
        for(int i=dots;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
            
        }
        if(leftdirection)
        {
            x[0]=x[0]-DOT_SIZE;
        }
        if(rightdirection)
        {
            x[0]=x[0]+DOT_SIZE;
        }
        if(updirection)
        {
            y[0]=y[0]-DOT_SIZE;
        }
        if(downdirection)
        {
            y[0]=y[0]+DOT_SIZE;
        }
        
        
    }
    
 
    public void checkApple()
    {
        if(x[0]== apple_x && y[0]==apple_y)
        {
            dots++;
            locateApple();
            score+=2;
            
        }
    }
    public void checkCollision()
    {
        for(int i=dots;i>0;i--)
        {
            if(i>4 && (x[0]==x[i]) && (y[0]==y[i]))
            {
                inGame=false;
            }
        }
        if(y[0]>=300)
            inGame=false;
        if(x[0]>=300)
            inGame=false;
        if(y[0]<0)
            inGame=false;
        if(x[0]<0)
            inGame=false;
        if(!inGame)
        {
            timer.stop();
        }
    
    }
    public void actionPerformed(ActionEvent ae){
       if(inGame)
       {
           checkApple();
           checkCollision();
           move();
       }
        repaint();
    } 
    
    public class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent ke)
        {
            int key=ke.getKeyCode();
            if(key == KeyEvent.VK_LEFT &&(!rightdirection))
            {
                leftdirection=true;
                updirection=false;
                downdirection=false;
                
            }
            if(key == KeyEvent.VK_RIGHT &&(!leftdirection))
            {
                rightdirection=true;
                updirection=false;
                downdirection=false;
                
            }
            if(key == KeyEvent.VK_UP &&(!downdirection))
            {
                updirection=true;
                leftdirection=false;
                rightdirection=false;
                
            }
            if(key == KeyEvent.VK_DOWN &&(!updirection))
            {
                downdirection=true;
                leftdirection=false;
                rightdirection=false;
                
            }
           
        }
       
        
                
    }
    
    
} 
