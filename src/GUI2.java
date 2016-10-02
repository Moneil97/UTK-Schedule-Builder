import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class GUI2 extends JFrame {

	JCheckBox chckbxMonday = null, chckbxTuesday = null, chckbxWednesday = null, chckbxThursday = null, chckbxFriday = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/* GUI2 window = */new GUI2();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */

	//List<Course> x;
	Calendar cal;

	public GUI2() throws IOException {

		cal = new Calendar();
		cal.compute();
		initialize();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				
				
			}
			
		}).start();
	}
	
	

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1300, 700);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		JPanel FullOrNot = new JPanel();
		panel.add(FullOrNot);

		JCheckBox chckbxShowFullClasses = new JCheckBox("Full Classes");
		chckbxShowFullClasses.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cal.setFullFilter(!chckbxShowFullClasses.isSelected());
				cal.compute();
				repaint();
			}
		});
		FullOrNot.add(chckbxShowFullClasses);

		JPanel daysToShow = new JPanel();
		panel.add(daysToShow);
		
		ActionListener dayCheckBoxActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cal.setDayFilter(new boolean[] {!chckbxMonday.isSelected(), !chckbxTuesday.isSelected(),!chckbxWednesday.isSelected(),!chckbxThursday.isSelected(), !chckbxFriday.isSelected()});
				cal.compute();
				repaint();
			}
		};
		
		chckbxMonday = new JCheckBox("Monday");
		chckbxMonday.setSelected(true);
		chckbxMonday.addActionListener(dayCheckBoxActionListener);
		daysToShow.add(chckbxMonday);

		chckbxTuesday = new JCheckBox("Tuesday");
		chckbxTuesday.setSelected(true);
		chckbxTuesday.addActionListener(dayCheckBoxActionListener);
		daysToShow.add(chckbxTuesday);

		chckbxWednesday = new JCheckBox("Wednesday");
		chckbxWednesday.setSelected(true);
		chckbxWednesday.addActionListener(dayCheckBoxActionListener);
		daysToShow.add(chckbxWednesday);

		chckbxThursday = new JCheckBox("Thursday");
		chckbxThursday.setSelected(true);
		chckbxThursday.addActionListener(dayCheckBoxActionListener);
		daysToShow.add(chckbxThursday);

		chckbxFriday = new JCheckBox("Friday");
		chckbxFriday.setSelected(true);
		chckbxFriday.addActionListener(dayCheckBoxActionListener);
		daysToShow.add(chckbxFriday);

		JScrollPane timesToShow = new JScrollPane(new JLabel("times to show"));
		timesToShow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		timesToShow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.add(timesToShow);

		JScrollPane teachersToShow = new JScrollPane(new JLabel("teachers to show"));
		teachersToShow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		teachersToShow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.add(teachersToShow);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.WEST);

		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.NORTH);

		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3, BorderLayout.EAST);

		JPanel center = new JPanel() {

			int minTime = 8 * 60;
			int maxTime = (5 + 12) * 60;
			float scale;
			int boxWidth;

			@Override
			protected void paintComponent(Graphics g1) {
				super.paintComponents(g1);
				Graphics2D g = (Graphics2D) g1;
				g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g.setFont(new Font("Arial", Font.PLAIN, 12));
				
				scale = (float) this.getHeight() / (maxTime - minTime);
				boxWidth = this.getWidth() / 5;
				
				//white background
				g.setColor(Color.white);
				g.fillRect(0, 0, getWidth(), getHeight());

				//draw boxes for each class
				
				if (!cal.possibleCombinations.isEmpty()){
				
					for (Course c : cal.possibleCombinations.get(0)) {
						for (ClassGroup r : c.classGroups) {
							for (Days d : r.week.days) {
								g.setColor(Color.green);
								int boxHeight = convertHeight(r.week.time);
								g.fillRect(d.dayNumber * boxWidth, convertY(r.week.time), boxWidth, boxHeight);
								g.setColor(Color.black);
								if (boxHeight > 12){
									g.drawString(trimIfNeeded(g, c.getTitle() + " (" + c.subject+ " " + c.course + ")"), d.dayNumber * boxWidth+2, convertY(r.week.time)+12);
									if (boxHeight > 26){
										g.drawString(trimIfNeeded(g, r.teacher), d.dayNumber * boxWidth+2, convertY(r.week.time)+26);
										if (boxHeight > 40){
											g.drawString(trimIfNeeded(g,r.location), d.dayNumber * boxWidth+2, convertY(r.week.time)+40);
											if (boxHeight > 54){
												g.drawString(trimIfNeeded(g,r.week.time.toString()), d.dayNumber * boxWidth+2, convertY(r.week.time)+54);
											}
										}
										
									}
								}
							}
						}
					}
				}
				else{
					//display error
					g.setColor(Color.orange);
					g.fillRect(this.getWidth()/2-100, this.getHeight()/2-50, 200, 100);
					g.setColor(Color.red);
					g.setFont(new Font("Arial", Font.PLAIN, 16));
					g.drawString("No Available Classes", this.getWidth()/2-80, this.getHeight()/2);
				}
				
				//draw day dividing lines
				g.setColor(Color.red);
				for (int i = 1; i < 7; i++)
					g.drawLine(boxWidth * i, 0, boxWidth * i, getHeight());

			}

			int convertY(Time t) {
				return (int) ((t.start - minTime) * scale);
			}

			int convertHeight(Time t) {
				return (int) ((t.end - t.start) * scale);
			}
			
			String trimIfNeeded(Graphics2D g, String text){
				int width = g.getFontMetrics().stringWidth(text);
				
				if (width > boxWidth){
					text = text.substring(0,text.length()-1);
					return trimIfNeeded(g, text);
				}
				else
					return text;
				
			}

		};
		getContentPane().add(center, BorderLayout.CENTER);
		this.setVisible(true);
	}
}
