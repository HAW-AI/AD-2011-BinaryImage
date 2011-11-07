package adp2.application;

import adp2.implementations.BinaryImages;
import adp2.interfaces.*;
import adp2.interfaces.Point;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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

	private boolean useRandomColor=false;
	private boolean useBoundary=false;
	private Controller controller;
	private Frame frame;
	private int pointSizeXY = 10;
	private Graphics graphic;
	private int buttonLength = 40;
	private int buttonHeight = 25;
	private int gridPositionX = 30;
	private int gridPositionY = buttonHeight + gridPositionX;

	private Button buttonDrawImage = new Button("Bild malen");
	private Button buttonDrawFourNeighbor = new Button("4er Blobs");
	private Button buttonDrawEightNeighbor = new Button("8er Blobs");
	private Button buttonDrawBoundary = new Button("Ränder");
	private Button buttonInverse = new Button("invertieren");
	private Button buttonChooseFile = new Button("Datei laden...");

	private JFileChooser fileChooser = new JFileChooser(".");

	public View(Controller controller){
		this.setController(controller);
		this.setImage(controller.binaryImage());
		frame = new Frame();
		frame.setResizable(true);
		frame.add(this);
		frame.pack();
		frame.setSize(getImage().width()+600, getImage().height()+350);
		init();
		frame.setVisible(true);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
	    }

	public void init() {	
		final Panel panel = new Panel();
		Rectangle minBounds = new Rectangle(200, 0, gridPositionY + buttonLength * 2, 500);
		
		panel.setBounds(minBounds);
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
		panel.add(buttonDrawBoundary);
		buttonDrawBoundary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				buttonDrawBoundary(event);
			}
		});
		panel.add(buttonInverse);
		buttonInverse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				buttonInverse(event);
			}
		});
		panel.add(buttonChooseFile);
		buttonChooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonChooseFile(panel, e);
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
	private void buttonDrawImage(ActionEvent event) {
		useRandomColor=false;
		repaint();
	}

    /**
     * Button method to draw all blobs in an four neighborhood in different color
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     * 
     * @param event ActionEvent of pressed button
     */
	private void buttonDrawFourNeighbor(ActionEvent event) {
		useRandomColor=true;
		setImage(getImage().toFourNeighborBinaryImage());
		
	}
	
	

    /**
     * Button method to draw all blobs in an eight neighborhood in different color
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     * 
     * @param event ActionEvent of pressed button
     */
	private void buttonDrawEightNeighbor(ActionEvent evt) {
		useRandomColor=true;
		setImage(getImage().toEigthNeighborBinaryImage());
		
	}
	
	/**
	 * Zeichnet den Rand der Blobs farbig.
	 * 
	 * @author Kai Bielenberg
	 * @param evt
	 * 
	 */
	
	private void buttonDrawBoundary(ActionEvent evt) {
		repaint();
		if (useBoundary) {
			useBoundary = false;
		} else {
			useBoundary = true;
		}
	}

	/**
	* Button method to inverse image and draw inverted image
	* 
	* @author Daniel Liesener
     	* @author Fenja Harbke
	* 
	* @param event ActionEvent of pressed button
	*/ 
	private void buttonInverse(ActionEvent event) {
		setImage(getImage().inverse());
		repaint();
	}

	
	private void buttonChooseFile(Panel panel, ActionEvent event) {
        int ret = fileChooser.showOpenDialog(panel);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String path = file.getAbsolutePath();
            controller.setBinaryImage(BinaryImages.binaryImage(controller.openImage(path)));
            
            // Resize frame for new image
            sizeToFit();
        }
	}
	
	/**
	 * Resize window to properly fit its contents.
	 */
	private void sizeToFit() {
        int minWidth = buttonDrawImage.getWidth()
                     + buttonDrawFourNeighbor.getWidth()
                     + buttonDrawEightNeighbor.getWidth()
                     + buttonInverse.getWidth()
                     + buttonChooseFile.getWidth()
                     + 50; // some spacing
        int minHeight = buttonDrawImage.getHeight() + 50; // +50px spacing
        
        Dimension minDimension = new Dimension(minWidth, minHeight);

        Dimension fittingDimension =
            new Dimension(getImage().width() * pointSizeXY + getImage().width() + 70,
                          getImage().height() * pointSizeXY + getImage().height() + 150);
        Dimension newDimension =
            new Dimension(Math.max(minDimension.width, fittingDimension.width),
                          Math.max(minDimension.height, fittingDimension.height));
        
        getFrame().setPreferredSize(newDimension);
        getFrame().pack();
	}
	
	
    /**
     * Paints grid using width and height of binary image
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     */
	public void paint(Graphics graphic) {
	    // save graphic internally to not have to pass it through drawBlobs() etc.
	    this.graphic = graphic;
	    
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
		drawBlobs(useRandomColor);
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
	private void drawBlob(Blob blob, boolean randomColor){
		Color color = (randomColor ? choseColor() : Color.BLACK);
		Set<Point> boundary = new HashSet<Point>(blob.boundary());
		for(Point point : blob){
			if((useBoundary == true) && (boundary.contains(point)))
				drawPoint(point,Color.CYAN);
			else
				drawPoint(point,color);
		}
	}
	
	

    /**
     * Draws blobs in black or different random colors
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     * 
     * @param randomColor If different random colors should be used for each blob
     */	
	private void drawBlobs(boolean randomColor){
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

	private Frame getFrame(){
		return frame;
	}

	private Controller getController() {
		return controller;
	}

	private void setController(Controller controller) {
		this.controller = controller;
	}

	public BinaryImage getImage() {
		return getController().binaryImage();
	}

	public void setImage(BinaryImage image) {
		getController().setBinaryImage(image);
	}
}