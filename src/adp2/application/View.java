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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Daniel Liesener
 * @author Fenja Harbke
 */

@SuppressWarnings("serial")
public class View extends Applet {

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

	// panel hï¿½lt die Buttons
	private final Panel panel = new Panel();
	// panel2 hï¿½lt die TextArea zur circularity ausgabe
	private final Panel panel2 = new Panel();
	// distance hï¿½lt die hï¿½he von panel + panel2
	private final int distance;
	// textAreaCircularity zur ausgabe der Circularitï¿½ten
	private TextArea textAreaCircularity = new TextArea();

	private JFileChooser fileChooser = new JFileChooser(".");

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
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
	}

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

		// ---------------------
		// @author Stephan Berngruber
		// @author Tobias Meurer
		//
		// add circularity value
		setCircularityText();
		// hier wird in mehreren schritten die grï¿½ï¿½e von panel2 gesetzt,
		panel2.setBounds(0, 30, 50, 50);
		// es zu view geadded
		add(panel2);
		// das Layout gesetzt
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		// und die textAreaCircularity panel2 geadded
		panel2.add(textAreaCircularity);

	}

	/**
	 * trï¿½gt Circularity aller blobs in die Textarea ein
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
		int ret = fileChooser.showOpenDialog(panel);
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String path = file.getAbsolutePath();
			controller.setBinaryImage(controller
					.openImage(path));
			setCircularityText();
			// Resize frame for new image
			sizeToFit();
		}
	}
	
	/**
	 * Button method to save the displayed image with its blobs as file on the file system
	 * 
	 * @author Harald Kirschenmann
	 * @author Philipp Gillé
	 */
	private void buttonSaveBlob(Panel panel, ActionEvent event) {
		int ret = fileChooser.showSaveDialog(panel);
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String path = file.getAbsolutePath();
			// text in datei speichern
			String binaryImageAsSequenceString = controller.getBlobAsSequenceString();
			System.out.println(binaryImageAsSequenceString);//test
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(path));
				out.write(binaryImageAsSequenceString);
				out.close();
			} catch (IOException e) {
				System.out.println("Error while trying to write the file");
			}
		}
	}

	/**
	 * Resize window to properly fit its contents.
	 */
	private void sizeToFit() {
		int minWidth = buttonDrawImage.getWidth()
				+ buttonDrawFourNeighbor.getWidth()
				+ buttonDrawEightNeighbor.getWidth() + buttonInverse.getWidth()
				+ buttonChooseFile.getWidth() + panel2.getWidth() + 50; // some
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
			if ((useBoundary == true) && (boundary.contains(point)))
				drawPoint(point, Color.CYAN);
			else
				drawPoint(point, color);
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
