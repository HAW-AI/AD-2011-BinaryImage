package adp2.application;

import adp2.interfaces.*;
import adp2.interfaces.Point;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.File;

/**
 * @author Daniel Liesener
 * @author Fenja Harbke
 */

@SuppressWarnings("serial")
public class View extends Applet {

	private Controller controller;
	
	int pointSizeXY = 30;
	Graphics graphic;
	int buttonLengeth = 40;
	int buttonHeight = 25;
	int gridPositionX = 30;
	int gridPositionY = buttonHeight + gridPositionX;

	private Button buttonDrawImage = new Button("Bild malen");
	private Button buttonDrawFourNeighbor = new Button("4er Blobs");
	private Button buttonDrawEightNeighbor = new Button("8er Blobs");
	private Button buttonChooseFile = new Button("Datei laden...");
	
	private JFileChooser fileChooser = new JFileChooser();
	
	public View(Controller controller){
		this.setController(controller);
		this.setImage(controller.binaryImage());
		Frame f = new Frame();
	    f.setResizable(false);
	    f.add(this);
	    f.pack();
	    f.setSize(getImage().width()+350, getImage().height()+350);
	    init(); //initialisiere Oberfläche
	    f.setVisible(true);
		f.addWindowListener(new java.awt.event.WindowAdapter() {
	         public void windowClosing(java.awt.event.WindowEvent e) {
	         System.exit(0);
	         };
	    });
		}
	
	public void init() {	
		final Panel panel = new Panel();
		panel.setBounds(200, 0, gridPositionY + buttonLengeth * 2, 500);
		panel.getAlignmentX();
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		graphic = getGraphics();

		panel.add(buttonDrawImage);
		buttonDrawImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				buttonDrawImage(event);
			}
		});
		panel.add(buttonDrawFourNeighbor);
		buttonDrawFourNeighbor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				buttonDrawFourNeighbor(event);
			}
		});
		panel.add(buttonDrawEightNeighbor);
		buttonDrawEightNeighbor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				buttonDrawEightNeighbor(event);
			}
		});
		panel.add(buttonChooseFile);
		buttonChooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int ret = fileChooser.showOpenDialog(panel);
               if (ret == JFileChooser.APPROVE_OPTION) {
                   File file = fileChooser.getSelectedFile();
                   String path = file.getAbsolutePath();
                   controller.openImage(path);
                   
               }
            }
        });
	}
	
    /**
     * Button method to draw all points on an image black
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     * 
     * @param event ActionEvent of pressed button
     */
	public void buttonDrawImage(ActionEvent event) {
		drawBlobs(false);
	}
	
    /**
     * Button method to draw all blobs in an four neighborhood in different color
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     * 
     * @param event ActionEvent of pressed button
     */
	public void buttonDrawFourNeighbor(ActionEvent event) {
		setImage(getImage().toFourNeighborBinaryImage());
		drawBlobs(true);
	}
	
    /**
     * Button method to draw all blobs in an eight neighborhood in different color
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     * 
     * @param event ActionEvent of pressed button
     */
	public void buttonDrawEightNeighbor(ActionEvent evt) {
		setImage(getImage().toEigthNeighborBinaryImage());
		drawBlobs(true);
	}
	
    /**
     * Paints grid using width and height of binary image
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     */
	public void paint(Graphics graphic) {
		graphic.drawLine(gridPositionX, gridPositionY, gridPositionX + getImage().width() * (pointSizeXY + 1), gridPositionY);
		graphic.drawLine(gridPositionX, gridPositionY, gridPositionX, gridPositionY + getImage().height() * (pointSizeXY + 1));
		
		for (int i = 0; i < getImage().height(); i++) {
			graphic.drawLine(gridPositionX, gridPositionY + 1 + (i + 1) * pointSizeXY + i, gridPositionX + getImage().width()
					* (pointSizeXY + 1), gridPositionY + 1 + (i + 1) * pointSizeXY + i);
		}
		for (int j = 0; j < getImage().width(); j++) {
			graphic.drawLine(gridPositionX + 1 + (j + 1) * pointSizeXY + j, gridPositionY, gridPositionX + 1
					+ (j + 1) * pointSizeXY + j, gridPositionY + getImage().height() * (pointSizeXY + 1));
		}
	}

    /**
     * Draws point in grid
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     * 
     * @param point The point which should be drawn
     * @param color The color the point should be drawn
     */
	private void drawPoint(Point point, Color color){
		graphic.setColor(color);
		graphic.fillRect(gridPositionX+1+point.x()*(pointSizeXY+1), gridPositionY+1+point.y()*(pointSizeXY+1), pointSizeXY, pointSizeXY);
	}
	
    /**
     * Draws point in grid
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     * 
     * @param point The point which should be drawn
     * @param color The color the point should be drawn
     */	
	public void drawBlob(Blob blob, boolean randomColor){
		Color color = (randomColor ? choseColor() : Color.BLACK);
		for(Point point : blob){
			drawPoint(point,color);
		}
	}
	
    /**
     * Draws blobs in block in black or different random color
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     * 
     * @param randomColor If difference random color should be used
     */	
	public void drawBlobs(boolean randomColor){
		for(Blob blob : getImage().blobs()){
			drawBlob(blob, randomColor);
		}
	}
	
    /**
     * Generates random color
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     * 
     * @return Random color object
     */	
	private Color choseColor(){
		Random rand = new Random();
        return(new Color(rand.nextInt(255), 
                         rand.nextInt(255),
                         rand.nextInt(255)));
     }

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public BinaryImage getImage() {
		return getController().binaryImage();
	}

	public void setImage(BinaryImage image) {
		getController().setBinaryImage(image);
	}
}