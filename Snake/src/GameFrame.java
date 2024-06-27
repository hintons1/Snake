import javax.swing.JFrame;

public class GameFrame extends JFrame {

    GameFrame(){
        //Add a new GamePanel object to the frame
        this.add(new GamePanel());
        //Set the title of the frame
        this.setTitle("Snake");
        //Exit application when frame is closed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Prevents the frame from being resized by the user
        this.setResizable(false);
        //Allows the JFrame to fit comfortably around any components that are added
        this.pack();
        //Make the frame visible on screen
        this.setVisible(true);
        //Center on screen
        this.setLocationRelativeTo(null);

    }

    
}
