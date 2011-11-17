package adp2.application;

import adp2.implementations.BoundarySequenceImpl;
import adp2.implementations.PointImpl;
import adp2.interfaces.*;
import adp2.interfaces.Point;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Daniel Liesener
 * @author Fenja Harbke
 */
@SuppressWarnings("serial")
public final class View extends Applet {

    private boolean useRandomColor = false;
    private boolean useBoundary = false;
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
    private Button buttonDrawBoundary = new Button("Kontur");
    private Button buttonInverse = new Button("invertieren");
    private Button buttonChooseFile = new Button("Datei laden...");
    private Button buttonSaveBlobs = new Button("Blobs speichern...");
    private Button buttonLoadBlobs = new Button("Blobs laden...");
    // panel hält die Buttons
    private final Panel panel = new Panel();
    // panel2 hält die TextArea zur circularity ausgabe
    private final Panel panel2 = new Panel();
    // distance hält die höhe von panel + panel2
    private final int distance;
    // textAreaCircularity zur ausgabe der Circularitäten
    private TextArea textAreaCircularity = new TextArea();
    private JFileChooser fileChooser = new JFileChooser(".");
    private EsserFilter esser = new EsserFilter();
    private BlobFilter blobs = new BlobFilter();

    public View(Controller controller) {
        this.setController(controller);
        this.setImage(controller.binaryImage());
        frame = new Frame();
        frame.setResizable(true);
        frame.add(this);
        frame.pack();
        frame.setSize(getImage().width() + 600, getImage().height() + 350);
        init();

        // distance berechnen
        distance = panel2.getHeight() + gridPositionY + buttonLength * 2;
        // gridpositionY hochrechnen sodass BinaryImagesan der korrekten
        // position gezeichnet werden
        gridPositionY += distance;

        frame.setVisible(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        ;
    }

    );
	}

    @Override
	public void init() {

        Rectangle minBounds = new Rectangle(200, 0, gridPositionY
                + buttonLength * 2, 500);

        panel.setBounds(minBounds);
        // panel.getAlignmentX();
        add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        graphic = getGraphics();

        panel.add(buttonDrawImage);
        buttonDrawImage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                buttonDrawImage(event);
            }
        });
        panel.add(buttonDrawFourNeighbor);
        buttonDrawFourNeighbor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                buttonDrawFourNeighbor(event);
            }
        });
        panel.add(buttonDrawEightNeighbor);
        buttonDrawEightNeighbor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                buttonDrawEightNeighbor(event);
            }
        });
        panel.add(buttonDrawBoundary);
        buttonDrawBoundary.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                buttonDrawBoundary(event);
            }
        });
        panel.add(buttonInverse);
        buttonInverse.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                buttonInverse(event);
            }
        });
        panel.add(buttonChooseFile);
        buttonChooseFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buttonChooseFileLoad(panel, e);
            }
        });
        panel.add(buttonSaveBlobs);
        buttonSaveBlobs.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSaveBlob(panel, e);
            }
        });
        panel.add(buttonLoadBlobs);
        buttonLoadBlobs.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buttonLoadBlob(panel, e);
            }
        });

        // ---------------------
        // @author Stephan Berngruber
        // @author Tobias Meurer
        //
        // add circularity value
        setCircularityText();
        // hier wird in mehreren schritten die gr��e von panel2 gesetzt,
        panel2.setBounds(0, 30, 50, 50);
        // es zu view geadded
        add(panel2);
        // das Layout gesetzt
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        // und die textAreaCircularity panel2 geadded
        panel2.add(textAreaCircularity);

        addMouseListener(new MouseAdapter() { //mouseListner for BlobSelect
	        public void mouseReleased(MouseEvent evt) {
	        	int x = evt.getX();
	        	int y = evt.getY();
	        	BinaryImage b = getImage();
	        	int w = gridPositionX+b.width()*(pointSizeXY+1);
	        	int h = gridPositionY+b.height()*(pointSizeXY+1);
	        	
	            if (evt.isPopupTrigger() &&
	            		gridPositionX < x && x < w &&
	            		gridPositionY < y && y < h) { //check if mouse is over our Bitmap
	            	
	            	int pX = (x-gridPositionX)/(pointSizeXY+1); //calculation of X-BitmapPixel
	            	int pY = (y-gridPositionY)/(pointSizeXY+1); //calculation of Y-BitmapPixel
	            	
	            	
	            	System.out.println("Pos: "+pX+", "+pY);
	            	
            		int blobId = -1;
            		
            		java.util.List<Blob> bs = b.blobs();
            		
            		for(int i=0; i<bs.size(); ++i){ //check which blob is clicked (id)
            			if(bs.get(i).contains(PointImpl.valueOf(pX, pY))){
            				blobId = i;
            				break;
            			}
            		}
            		System.out.println("BlobId: "+blobId);
            		
            		showMenu(evt, blobId, PointImpl.valueOf(pX, pY)); //show our menu
	            }
	        }
	    });
    }
    
    /**
	 * open our Menu for Bitmap/Blob interaction
	 * @param evt
	 * @param blobId
     * @param point 
	 */
	private void showMenu(final MouseEvent evt, final int blobId, final Point point){
		JPopupMenu menu = new JPopupMenu();
		JMenuItem item1 = new JMenuItem("Blob killen");
		JMenuItem item2 = new JMenuItem("Blob speichern");
		JMenuItem item3 = new JMenuItem("Blob laden");
		
		item1.addActionListener(new ActionListener() { //delete the blob
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonChooseDeleteBlob(blobId);
			}
		});
		
		item2.addActionListener(new ActionListener() { //save the blob
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonChooseBlobFileSave(panel, e, blobId);
			}
		});
		
		item3.addActionListener(new ActionListener() { //load/fill the blob
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonChooseBlobFileLoad(point);
			}
		});
		
		if(blobId != -1) menu.add(item1); //kill
		if(blobId != -1 && !getImage().isEightNbr()) menu.add(item2); //save
		menu.add(item3); //load
		
		menu.show(this, evt.getX(), evt.getY());
	}
	
	protected void buttonChooseBlobFileLoad(Point point) {
            fileChooser.setFileFilter(blobs);
            int ret = fileChooser.showOpenDialog(panel);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String path = file.getAbsolutePath();
//                List<Blob> blobs = controller.openBlob(path);
            
                List<BoundarySequence> boundaries = BlobParser.parse(path);
                List<Blob> result = new ArrayList<Blob>();
                for (BoundarySequence b : boundaries) {
                	List<Integer> bList = b.getSequence();
                	int pos=0;
                	int xNeu = 0;
                	int yNeu = 0;
                	
                	for(int i : bList){//getHeight
                		if(i>=1 && i<=3) yNeu = Math.max(yNeu, ++pos);
                		if(i>=5 && i<=7) --pos;
                	}
                	
                	System.out.println("MySyso: "+xNeu+" "+yNeu);
                	
                	Point p2 = PointImpl.valueOf(point.x()+xNeu, point.y()+yNeu);
                	BoundarySequence b2 = BoundarySequenceImpl.valueOf(p2, bList);
                    result.add(b2.createBlob());
                }
                List<Blob> blobs = result;
                
                if(blobs.size() != 1) return;
            
                BinaryImage bi = getImage();
    		
	    		controller.setBinaryImage(bi.addBlob(blobs.get(0)));
	    		
	    		setCircularityText();
	    		sizeToFit();
            }
	}

        protected void buttonChooseBlobFileSave(Panel panel3, ActionEvent e, int blobId) {
            fileChooser.setFileFilter(blobs);
            int ret = fileChooser.showSaveDialog(panel);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String path = file.getAbsolutePath();
                
                //fixPath
                if(!path.endsWith(".blob")) path+=".blob";
                
                // text in datei speichern
                controller.writeOneBlob(path, blobId);
            }
        }

	/**
	 * called from the GUI-PopUp to delete one blob
	 * @param blobId
	 */
	protected void buttonChooseDeleteBlob(int blobId) {
		BinaryImage bi = getImage();
		
		controller.setBinaryImage(bi.deleteBlob(blobId));
		
		setCircularityText();
		sizeToFit();
	}

    /**
     * tr�gt Circularity aller blobs in die Textarea ein
     * 
     * @author Stephan Berngruber
     * @author Tobias Meurer
     * 
     * 
     */
    private void setCircularityText() {
        textAreaCircularity.setText("Circularity:\n"
                + getImage().circularities());
    }

    /**
     * Button method to draw all points on an image black
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     * 
     * @param event
     *            ActionEvent of pressed button
     */
    private void buttonDrawImage(ActionEvent event) {
        useRandomColor = false;
        repaint();
    }

    /**
     * Button method to draw all blobs in an four neighborhood in different
     * color
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     * 
     * @param event
     *            ActionEvent of pressed button
     */
    private void buttonDrawFourNeighbor(ActionEvent event) {
        useRandomColor = true;
        setImage(getImage().toFourNeighborBinaryImage());
        setCircularityText();
    }

    /**
     * Button method to draw all blobs in an eight neighborhood in different
     * color
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     * 
     * @param event
     *            ActionEvent of pressed button
     */
    private void buttonDrawEightNeighbor(ActionEvent evt) {
        useRandomColor = true;
        setImage(getImage().toEigthNeighborBinaryImage());
        setCircularityText();

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
     * @param event
     *            ActionEvent of pressed button
     */
    private void buttonInverse(ActionEvent event) {
        setImage(getImage().inverse());
        setCircularityText();
        repaint();
    }

    private void buttonChooseFileLoad(Panel panel, ActionEvent event) {
        fileChooser.setFileFilter(esser);
        int ret = fileChooser.showOpenDialog(panel);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String path = file.getAbsolutePath();
            controller.setBinaryImage(controller.openImage(path));
            setCircularityText();
            // Resize frame for new image
            sizeToFit();
        }
    }

    /**
     * Button method to save the displayed image with its blobs as file on the file system
     * 
     * @author Harald Kirschenmann
     * @author Philipp Gille
     */
    private void buttonSaveBlob(Panel panel, ActionEvent event) {
    	// sequence only works for 4-blob, so saving blobs does so too
    	if (!controller.binaryImage().isEightNbr()) {
	        fileChooser.setFileFilter(blobs);
	        int ret = fileChooser.showSaveDialog(panel);
	        if (ret == JFileChooser.APPROVE_OPTION) {
	            File file = fileChooser.getSelectedFile();
	            String path = file.getAbsolutePath();
	            // text in datei speichern
	            controller.writeAllBlobs(path);
	        }
    	} else {
    		JOptionPane.showMessageDialog(null, "Saving blobs only works in 4-blob mode", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    /*
     * Button method to select and load blobs from a file
     * 
     * @author Sebastian Bartels
     */
    private void buttonLoadBlob(Panel panel, ActionEvent e) {
        fileChooser.setFileFilter(blobs);
        int ret = fileChooser.showOpenDialog(panel);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String path = file.getAbsolutePath();
            List<Blob> blobs = new ArrayList<Blob>();
            blobs.addAll(controller.openBlob(path));
//          Anzeige einer Liste der Blobs zur Auswahl? 
//          oder alle importieren 
            for(Blob b : blobs)
                this.controller.addBlob(b);
        }
    }

    /**
     * Resize window to properly fit its contents.
     */
    private void sizeToFit() {
        int minWidth = buttonDrawImage.getWidth()
                + buttonDrawFourNeighbor.getWidth()
                + buttonDrawEightNeighbor.getWidth() + buttonInverse.getWidth()
                + buttonChooseFile.getWidth() + buttonSaveBlobs.getWidth() 
                + buttonLoadBlobs.getWidth() + panel2.getWidth() + 50; // some
        // spacing
        int minHeight = buttonDrawImage.getHeight() + 50; // +50px spacing

        Dimension minDimension = new Dimension(minWidth, minHeight);

        Dimension fittingDimension = new Dimension(getImage().width()
                * pointSizeXY + getImage().width() + 70, getImage().height()
                * pointSizeXY + getImage().height() + panel2.getHeight() + 150);
        Dimension newDimension = new Dimension(Math.max(minDimension.width,
                fittingDimension.width), Math.max(minDimension.height,
                fittingDimension.height));

        getFrame().setPreferredSize(newDimension);
        getFrame().pack();
    }

    /**
     * Paints grid using width and height of binary image
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     */
    @Override
    public void paint(Graphics graphic) {
        // save graphic internally to not have to pass it through drawBlobs()
        // etc.
        this.graphic = graphic;

        graphic.drawLine(gridPositionX, gridPositionY, gridPositionX
                + getImage().width() * (pointSizeXY + 1), gridPositionY);
        graphic.drawLine(gridPositionX, gridPositionY, gridPositionX,
                gridPositionY + getImage().height() * (pointSizeXY + 1));

        for (int i = 0; i < getImage().height(); i++) {
            graphic.drawLine(gridPositionX, gridPositionY + 1 + (i + 1)
                    * pointSizeXY + i, gridPositionX + getImage().width()
                    * (pointSizeXY + 1), gridPositionY + 1 + (i + 1)
                    * pointSizeXY + i);
        }
        for (int j = 0; j < getImage().width(); j++) {
            graphic.drawLine(gridPositionX + 1 + (j + 1) * pointSizeXY + j,
                    gridPositionY, gridPositionX + 1 + (j + 1) * pointSizeXY
                    + j, gridPositionY + getImage().height()
                    * (pointSizeXY + 1));
        }
        drawBlobs(useRandomColor);
    }

    /**
     * Draws point in grid
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     * 
     * @param point
     *            The point which should be drawn
     * @param color
     *            The color the point should be drawn
     */
    private void drawPoint(Point point, Color color) {
        graphic.setColor(color);
        graphic.fillRect(gridPositionX + 1 + point.x() * (pointSizeXY + 1),
                gridPositionY + 1 + point.y() * (pointSizeXY + 1), pointSizeXY,
                pointSizeXY);
    }

    /**
     * Draws point in grid
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     * 
     * @param point
     *            The point which should be drawn
     * @param color
     *            The color the point should be drawn
     */
    private void drawBlob(Blob blob, boolean randomColor) {
        Color color = (randomColor ? choseColor() : Color.BLACK);
        Set<Point> boundary = new HashSet<Point>(blob.boundary());
        for (Point point : blob) {
            if ((useBoundary == true) && (boundary.contains(point))) {
                drawPoint(point, Color.CYAN);
            } else {
                drawPoint(point, color);
            }
        }
    }

    /**
     * Draws blobs in black or different random colors
     * 
     * @author Daniel Liesener
     * @author Fenja Harbke
     * 
     * @param randomColor
     *            If different random colors should be used for each blob
     */
    private void drawBlobs(boolean randomColor) {
        for (Blob blob : getImage().blobs()) {
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
    private Color choseColor() {
        Random rand = new Random();
        return (new Color(rand.nextInt(255), rand.nextInt(255),
                rand.nextInt(255)));
    }

    private Frame getFrame() {
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
