
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel{
	private infoPanel infopanel;
	private JTextField startField;
	private JTextField endField;
	public AmusementPark start;
	public AmusementPark end;
	private boolean startChanging;
	public mapPanel map;

	public ControlPanel(infoPanel info){
		this.startChanging = true;
		//this.setPreferredSize(new Dimension(250, 300));
		this.setLayout(new MigLayout());
		infopanel = info;
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JTextField searchBox = new JTextField("search", 10);
		this.add(searchBox, "cell 0 0, gapy 30::30");
		
		JButton searchButton = new JButton("search");
		this.add(searchButton, "cell 1 0, span");
		ActionListener search = new ActionListener(){
			public void actionPerformed(ActionEvent f){
				infopanel.removeAll();
				JLabel searching = new JLabel("searching");
				infopanel.add(searching, "cell 0 1");
				infopanel.displayingParkInfo = false;
				infopanel.revalidate();
				infopanel.repaint();
			}
		};
		searchButton.addActionListener(search);
		
		
		JLabel startlabel = new JLabel("start: ");
		this.add(startlabel, "cell 0 1, span");
		
		final JTextField startBox = new JTextField(18);
		this.add(startBox, "cell 0 1");
		this.startField = startBox;
		
		JLabel endLabel = new JLabel("end: ");
		this.add(endLabel, "cell 0 2, span");
		
		final JTextField endBox = new JTextField(18);
		this.add(endBox, "cell 0 2");
		this.endField = endBox;
		
		final JRadioButton byDistance = new JRadioButton("shortest distance");
		byDistance.setSelected(true);
		JRadioButton byTime = new JRadioButton("least time");
		ButtonGroup group =  new ButtonGroup();
		group.add(byDistance);
		group.add(byTime);
		
		this.add(byDistance, "cell 1 3 1 2");
		this.add(byTime, "cell 0 3 1 2");
	
		JButton goButton = new JButton("GO!");
		this.add(goButton, "cell 0 5");
		ActionListener go = new ActionListener(){
			public void actionPerformed(ActionEvent f){
				ParkGraph graph = new ParkGraph(start, end);
				while (graph.paths.peek().parkConnections.contains(end)){
					graph.travel();
				}
				Paths bestPath = graph.paths.poll();
				if (map != null){
					map.drawLines = true;
					map.lines.clear();
				}
				
				for (int i =1; i < bestPath.parkConnections.size(); i++){
					if (map != null){
						System.out.println(bestPath.parkConnections.get(i-1).name + " " + bestPath.parkConnections.get(i).name);
						map.addLine(bestPath.parkConnections.get(i-1), bestPath.parkConnections.get(i));
					}
				}
				if (map != null){
					map.revalidate();
					map.repaint();
				}
				infopanel.removeAll();
				JLabel goLabel = new JLabel("GO by time");
				if(byDistance.isSelected()) {
					goLabel.setText("GO by distance");
				}
				infopanel.add(goLabel, "cell 0 1");
				infopanel.displayingParkInfo = false;
				infopanel.revalidate();
				infopanel.repaint();
			}
		};
		goButton.addActionListener(go);
	}
	
	public void setMap(mapPanel inmap){
		this.map = inmap;
	}
	
	public void setBox(AmusementPark park){
		if(this.startChanging){
			this.start = park;
			this.startField.setText(park.name);
			this.startChanging = !this.startChanging;
		}
		else{
			this.end = park;
			this.endField.setText(park.name);
			this.startChanging = !this.startChanging;
		}
	}
}