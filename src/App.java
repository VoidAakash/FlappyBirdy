
import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception{
        //dimensions of bg image 
        int boardWidth = 360;
        int boardHeight = 640;
                                    
        JFrame frame = new JFrame("Flappy Birdy");
        
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);//place the window at the centre of the screen
        frame.setResizable(false); //user cannot resize the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//x button terminates

        FlappyBirdy flappyBirdy = new FlappyBirdy();
        frame.add(flappyBirdy);
        frame.pack();
        flappyBirdy.requestFocus();
        frame.setVisible(true); //make the frame visible
    }

}