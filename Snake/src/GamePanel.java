import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener{

    //Constants for frame dimensions and speed
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;

    //Arrays to hold x and y coordinates
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    //Variables for snake body, score, apples, and direction
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R'; //R = right, L = left, U = up, D = down
    boolean running = false; //Indicates if game is currently running
    Timer timer; //To control game speed
    Random random; //For random apple position

    //Constructor to set the game panel
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    //Method to start the game
    public void startGame(){
        newApple(); //Generate initial apple
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    //Draws graphics
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    //Draws the game elements
    public void draw(Graphics g){

        if(running){
            /*
            for(int i = 0; i<SCREEN_HEIGHT/UNIT_SIZE; i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            } 
            */

            //Draws apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //Draw snake body
            for(int i = 0; i < bodyParts; i++){
                if(i == 0){ //Head of snake
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }else{ //Body of snake
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //Display score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
        }else{
            gameOver(g); //Shows game over if not running
        }
    }

    //Method to randomly generate a new apple
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    //Method so that user can move the snake
    public void move(){
        
        for(int i = bodyParts; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    //Method to check if the snake has eaten an apple
    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++; //Increase snake length
            applesEaten++; //Increase score
            newApple(); //Generate new apple
        }
    }

    //Checks for collisions
    public void checkCollisions(){
        //Checks if head of snake collides with body
        for(int i = bodyParts; i > 0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        //Checks if head of snake collides with left border
        if(x[0] < 0){
            running = false;
        }
        //Checks if head of snake collides with right border
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        //Checks if head of snake collides with top border
        if(y[0] < 0){
            running = false;
        }
        //Checks if head of snake collides with bottom border
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }
        //Stops the timer upon collision
        if(running == false){
            timer.stop();
        }

    }

    //Method to display the game over screen
    public void gameOver(Graphics g){
        //Displays the score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
        
        //Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }

    //Updates the game state
    @Override
    public void actionPerformed(ActionEvent e){
        
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint(); //Refresh the screen
    }

    //Handles key events
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
