import javax.swing.JFrame;

public class TetrisDriver {
    public static void main(String[] args){
    	 JFrame frame = new JFrame();
         frame.setSize(Constants.blockSize*Constants.gridWidth,Constants.blockSize*Constants.gridLength);
         GamePanel panel = new GamePanel();
         frame.add(panel);
         frame.setVisible(true);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}