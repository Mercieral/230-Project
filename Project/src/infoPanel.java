
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.io.InputStream;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class infoPanel extends JPanel{
	public boolean displayingParkInfo;
	private AmusementPark currentPark;

	public infoPanel(){
		//this.setPreferredSize(new Dimension(250, 300));
		this.displayingParkInfo = false;
		this.setLayout(new MigLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JLabel label = new JLabel(" This area will contain useful info");
		this.add(label, "cell 0 0, gapleft push");
		
		
	}
	
	public void displayRoute(LinkedList<AmusementPark> list){
		this.removeAll();
		for (int i=0; i < list.size() -1; i++){
			for (int j=0; j< list.get(i).getLinks().size(); j++){
				if (list.get(i).getLinks().get(j).travelToLocation == list.get(i +1)){
					String labelText = String.format("<html><div WIDTH=%d>%s</div><html>", 200, list.get(i).getLinks().get(j).directions);
					JLabel direction = new JLabel();
					direction.setText(labelText);
					this.add(direction, "cell 0 " + i);
				}
			}
		}
		this.revalidate();
		this.repaint();
	}
	
	public void displayInfo(AmusementPark park){
		this.currentPark = park;
        this.removeAll();
        JLabel parkLabel = new JLabel("<HTML><U><B>" + park.name + "</B></U></HTML>");
        parkLabel.setFont(parkLabel.getFont().deriveFont(22f));
        this.add(parkLabel, "pushx, align center");
		this.displayingParkInfo = true;
		InputStream inputFile = this.getClass().getResourceAsStream("" + "ParkInfo/" + park.name);
		Scanner inputScanner = null;
		inputScanner = new Scanner(inputFile);
		int row = 2; 
		this.add(new JLabel(""), "cell 0 1, height 150");
		while(inputScanner.hasNextLine()){
			
			String line = inputScanner.nextLine();
			this.add(new JLabel(line), "cell 0 " + row + "");
			row++;
			
		}
		inputScanner.close();
		this.revalidate();
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if (this.displayingParkInfo){
			this.currentPark.drawOn(g, this);
		}
	}
}