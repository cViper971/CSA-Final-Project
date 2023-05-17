import java.awt.Color;

import javax.swing.JFrame;

public class TetrisDriver {
    public static void main(String[] args){
    	 JFrame frame = new JFrame();
         frame.setSize(Constants.blockSize*Constants.gridWidth+16,Constants.blockSize*Constants.gridLength+9);
         GamePanel panel = new GamePanel();
         panel.setSize(Constants.blockSize*Constants.gridWidth,Constants.blockSize*Constants.gridLength);
         //panel.setBackground(new Color(255,255,255));
         frame.add(panel);
         frame.setVisible(true);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setResizable(false);
    }
}