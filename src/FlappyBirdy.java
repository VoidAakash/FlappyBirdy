import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FlappyBirdy extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;
    
    //Image variables
    Image bg;
    Image birdImg;
    Image topPipe;
    Image bottomPipe;
    //Bird
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdHeight = 24;
    int birdWidth = 34;

    class Bird {
        int x = birdX;
        int y = birdY;
        int height = birdHeight;
        int width = birdWidth;
        Image img;
        Bird(Image img) {
            this.img = img;
        }
    }
    //game logic
    Bird bird;
    Timer gameLoop; 
    int velocityY = 0;
    int gravity = 1;



    FlappyBirdy() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        // setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);

        //loading images on the background image variable
        bg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipe = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipe = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        
        //bird
        bird = new Bird(birdImg);

        //game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        g.drawImage(bg, 0, 0, boardWidth, boardHeight, null);
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
    }

    public void move() {
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}


    @Override
    public void keyReleased(KeyEvent e){}
    
}
