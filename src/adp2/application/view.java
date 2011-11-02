package adp2.application;

import adp2.implementations.BinaryImages;
import adp2.interfaces.*;
import adp2.interfaces.Point;
import adp2.interfaces.BinaryImage;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BoxLayout;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;


/* Daniel, Fenja
 * To-Do:
 * Implementierung für 4 und 8 Blob Anzeige
 * Anpassung des Designs
 * Code dokumentieren und sturkturieren
 * Testfälle entfernen
 */

public class View extends Applet {

  private BinaryImage bi;
	
	int ps = 30; // Pixelsize
	Graphics g;
	int bl = 40; // buttonlength
	int bh = 25; // buttonheight
	int xbegin = 20;
	int ybegin = bh + xbegin;

	private Button buttonDrawAry = new Button("drawAry");

//--------------------------------------------------------------------
	
	
	public View(){
		List<Boolean> array= new ArrayList<Boolean>();
		array.add(new Boolean(true));
		array.add(new Boolean(false));
		array.add(new Boolean(true));

		List<Boolean> array2= new ArrayList<Boolean>();
		array2.add(new Boolean(false));
		array2.add(new Boolean(true));
		array2.add(new Boolean(false));
		
		List<List<Boolean>> pic = new ArrayList<List<Boolean>>();
		
		pic.add(array);
		pic.add(array2);
		
		bi =  BinaryImages.fourNeighborBinaryImage(pic);
		}
	
	public void init() {
				
		Panel cp = new Panel();
		cp.setBounds(0, 0, ybegin + bl * 2, 500);
		cp.getAlignmentX();
		add(cp);
		cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
		g = getGraphics();

		cp.add(buttonDrawAry);
		buttonDrawAry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonDrawAryActionPerformed(evt);
			}
		});
	}

//--------------------------------------------------------------------
	//Zeichnet Gitter mit width und height des BinaryImage
	public void paint(Graphics g) {
		g.drawLine(xbegin, ybegin, xbegin + bi.width() * (ps + 1), ybegin);
		g.drawLine(xbegin, ybegin, xbegin, ybegin + bi.height() * (ps + 1));
		for (int i = 0; i < bi.height(); i++) {//hier (richtig herum?)
			g.drawLine(xbegin, ybegin + 1 + (i + 1) * ps + i, xbegin + bi.width()
					* (ps + 1), ybegin + 1 + (i + 1) * ps + i);
		}
		for (int j = 0; j < bi.width(); j++) {//und hier
			g.drawLine(xbegin + 1 + (j + 1) * ps + j, ybegin, xbegin + 1
					+ (j + 1) * ps + j, ybegin + bi.height() * (ps + 1));
		}
	}

//---------------------------------------------------------------------
	
	private void drawP(Point p, Color color){
		g.setColor(color);
		g.fillRect(xbegin+1+p.x()*(ps+1), ybegin+1+p.y()*(ps+1), ps, ps);  //x, y richtig herum?
	}
	
	public void drawBlobs(List<Blob> b){
		for(Blob blob : b){
			drawSingleBlob(blob);
		}
	}
	
	public void drawSingleBlob(Blob blob){
		  Color color = choseColor();
		  for(Point p : blob){
		   drawP(p,color);
		  }
		 }
	
	
	private Color choseColor(){
		Random rand = new Random();
        return(new Color(rand.nextInt(256), 
                         rand.nextInt(256),
                         rand.nextInt(256)));
     
		//		switch(color){
		//		case 0:
		//			color++;
		//			return Color.gray;
		//			break;
		//		case 1:
		//			color++;
		//			return Color.blue;
		//			break;
		//		case 2:
		//			color++;
		//			return Color.yellow;
		//			break;
		//		case 3:
		//			color++;
		//			return Color.green;
		//			break;
		//		case 4:
		//			color++;
		//			return Color.magenta;
		//			break;
		//		case 5:
		//			color++;
		//			return Color.red;
		//			break;
		//		case 6:
		//			color++;
		//			return Color.cyan;
		//			break;
		//		case 7:
		//			color++;
		//			return Color.pink;
		//			break;
		//		case 8:
		//			color++;
		//			return Color.lightGray;
		//			break;
		//		case 9:
		//			color=0;
		//			return Color.orange;
		//			break;
		//		default:
		//			System.out.println("Too many Blobs!");
		//		}
		//	}
	}
	
	public void drawAry(){
		for(Blob b : bi.blobs()){
			for(Point p : b){
				drawP(p,Color.black);
			}
		}
	}
	
	// drawAry
	public void buttonDrawAryActionPerformed(ActionEvent evt) {
		drawAry();
	}
}