import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.io.IOException;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class GUI2 extends JFrame {

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

	List<Course> x;

	public GUI2() throws IOException {

		x = new Calendar().possibleCombinations.get(0);

		initialize();
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

		JCheckBox chckbxShowFullClasses = new JCheckBox("Show Full Classes");
		FullOrNot.add(chckbxShowFullClasses);

		JPanel daysToShow = new JPanel();
		panel.add(daysToShow);

		JCheckBox chckbxMonday = new JCheckBox("Monday");
		chckbxMonday.setSelected(true);
		daysToShow.add(chckbxMonday);

		JCheckBox chckbxTuesday = new JCheckBox("Tuesday");
		chckbxTuesday.setSelected(true);
		daysToShow.add(chckbxTuesday);

		JCheckBox chckbxWednesday = new JCheckBox("Wednesday");
		chckbxWednesday.setSelected(true);
		daysToShow.add(chckbxWednesday);

		JCheckBox chckbxThursday = new JCheckBox("Thursday");
		chckbxThursday.setSelected(true);
		daysToShow.add(chckbxThursday);

		JCheckBox chckbxFriday = new JCheckBox("Friday");
		chckbxFriday.setSelected(true);
		daysToShow.add(chckbxFriday);

		JCheckBox chckbxSaturday = new JCheckBox("Saturday");
		chckbxSaturday.setSelected(true);
		daysToShow.add(chckbxSaturday);

		JCheckBox chckbxSunday = new JCheckBox("Sunday");
		chckbxSunday.setSelected(true);
		daysToShow.add(chckbxSunday);

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

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponents(g);

				scale = (float) this.getHeight() / (maxTime - minTime);

				g.setColor(Color.white);
				g.fillRect(0, 0, getWidth(), getHeight());

				int a = this.getWidth() / 7;

				g.setColor(Color.red);
				for (int i = 1; i < 7; i++)
					g.drawLine(a * i, 0, a * i, getHeight());

				// assume we have class from 8 (480) to 8:50 (530)

				int width = this.getWidth() / 7;

				g.setColor(Color.green);
				/*
				 * //Monday 8:00-8:50 Time test1 = new Time("8:00am","8:50am");
				 * g.fillRect(width*0, convertX(test1), width,
				 * convertHeight(test1));
				 * 
				 * //Wednesday 10:10-11:00 (610-660) Time test2 = new
				 * Time("10:10am", "11:00am"); g.fillRect(width*2,
				 * convertX(test2), width, convertHeight(test2));
				 * 
				 * //Friday (9-4) Time test3 = new Time("9:00am","4:00pm");
				 * g.fillRect(width*4, convertX(test3), width,
				 * convertHeight(test3));
				 */

				for (Course c : x) {
					for (ClassGroup r : c.classGroups) {
						for (Days d : r.week.days) {
							g.fillRect(d.dayNumber * width, convertX(r.week.time), width, convertHeight(r.week.time));
						}
					}
				}

			}

			int convertX(Time t) {
				return (int) ((t.start - minTime) * scale);
			}

			int convertHeight(Time t) {
				return (int) ((t.end - t.start) * scale);
			}

		};
		// center.setMinimumSize(new Dimension(400, 400));
		getContentPane().add(center, BorderLayout.CENTER);
		this.setVisible(true);
	}

}
