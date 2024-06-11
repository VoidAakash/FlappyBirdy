import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBirdy extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;
    
    //Image variables
    Image bg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;
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
    //pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;
        
        Pipe(Image img) {
            this.img = img;
        }
    }
    //game logic
    Bird bird;
    Timer gameLoop; 
    Timer placePipesTimer;
    int velocityX = -4; //to move pipe right
    int velocityY = 0;//to move bird up and down
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    boolean gameOver = false;
    double score = 0;



    FlappyBirdy() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        // setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);

        //loading images on the background image variable
        bg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        
        //bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<>();

        //game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();

        //pipe place timer
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipesTimer.start();
    }

    

    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        
        //background
        g.drawImage(bg, 0, 0, boardWidth, boardHeight, null);
        
        //bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        //pipes
        for(int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
        //score display 
        g.setColor(Color.white);
        g.setFont(new Font("Comic Sans", Font.PLAIN, 32));
        if(gameOver) {
            g.drawString("Game Over" +  " "   + "-" +  " "  + String.valueOf((int) score), 10, 35);
        }
        else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }    
        
    }

    public void move() {
        //bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        //pipes
        for(int i = 0;i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if(!pipe.passed && bird.x > pipe.x + pipe.width) {
                pipe.passed = true;
                score += 0.5; // 0.5 for each pipe above and below
            }

            if(collision(bird, pipe)) {
                gameOver = true;
            }
        }

        if(bird.y > boardHeight) {
            gameOver = true;
        }

    }

    boolean collision(Bird b, Pipe p) {
        return  b.x < p.x + p.width && 
                b.x + b.width > p.x &&  
                b.y < p.y + p.height && 
                b.y + b.height > p.y;  
    }
    
    public void placePipes() {
        int randomPipeY = (int)(pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int flySpace = boardHeight/4;

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + topPipe.height + flySpace;
        pipes.add(bottomPipe);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver) {
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
            if(gameOver) {
                //restart the game 
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}


    @Override
    public void keyReleased(KeyEvent e){}
    
}
