import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TetrisDriver {
    public static void main(String[] args){
    	 JFrame frame = new JFrame();
         //frame.setSize(Constants.blockSize*Constants.gridWidth+16,Constants.blockSize*Constants.gridLength+9);
    	 frame.setSize(Constants.mainWindowWidth, Constants.blockSize*Constants.gridLength+9);
    	 frame.getContentPane().setBackground(Color.WHITE);
    	 
         GamePanel panel = new GamePanel();
         //panel.setSize(Constants.blockSize*Constants.gridWidth,Constants.blockSize*Constants.gridLength);
         panel.setPreferredSize(new Dimension(Constants.blockSize*Constants.gridWidth,Constants.blockSize*Constants.gridLength));
         panel.setBounds(0, 0, Constants.blockSize*Constants.gridWidth, Constants.blockSize*Constants.gridLength);
         
         int width = Constants.mainWindowWidth - Constants.blockSize*Constants.gridWidth - 16;
         int height = Constants.mainWindowHeight - Constants.blockSize*Constants.gridLength - 9;
         
         GameSideBar sideBar = new GameSideBar(width, height, panel);
         sideBar.setPreferredSize(new Dimension(width, height));
         sideBar.setBounds(Constants.blockSize*Constants.gridWidth, 0, Constants.mainWindowWidth, Constants.mainWindowHeight);
 		
         JPanel container = new JPanel();
         container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
         
         
         container.add(panel);
         container.add(sideBar);
         
         frame.add(container);
        
         frame.setVisible(true);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setResizable(false);
    }
}