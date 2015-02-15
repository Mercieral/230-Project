import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class mapPanel extends JPanel{
	private BufferedImage usa;
	private ArrayList<AmusementPark> parkList;
	infoPanel infopanel;
	
	
	public mapPanel(ArrayList<AmusementPark> parks, infoPanel info) {
		this.infopanel = info;
		this.parkList = parks;
		this.setPreferredSize(new Dimension(900, 600));
		try {
			usa = ImageIO.read(new File("Images/usa.png"));
			
		} catch (IOException ex){
			
		}
		 addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent me) {
	                super.mouseClicked(me);
	                for (AmusementPark park : parkList) {
	                	Point2D point = me.getPoint();
	                    if (isContained(point, park.location)) {
	                        //System.out.println("Clicked "+park.name);
	                        JLabel parkLabel = new JLabel(park.name);
	                        infopanel.removeAll();
	                        infopanel.add(parkLabel);
	        				infopanel.revalidate();
	        				infopanel.repaint();
	                    }
	                }
	            }
	        });
	}
	
	public boolean isContained(Point2D click, Point2D park){
		if (click.getX() > park.getX() && click.getX() < park.getX() + 15){
			if (click.getY() > park.getY() && click.getY() < park.getY() + 15){
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D graphics2 = (Graphics2D) g;
		
		double scale = 1.3;
		g.drawImage(usa,50,50,(int)(usa.getWidth() * scale), (int)(usa.getHeight() * scale), this);
		
		for(int i=0; i < parkList.size(); i++){
			Point2D point = parkList.get(i).location;
			Ellipse2D.Double circ = new Ellipse2D.Double(point.getX(), point.getY(), 15, 15);
			graphics2.setColor(Color.RED);
			graphics2.fill(circ);
		}
	}
}