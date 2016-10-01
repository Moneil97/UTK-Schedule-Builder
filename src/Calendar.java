import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Calendar {

	public Calendar() throws IOException {

		List<List<Course>> possibleCombinations = new ArrayList<>();
		List<List<Course>> courses = new Parser().courses;
		int maxes[] = new int[courses.size()];
		for (int i = 0; i < courses.size(); i++) {
			maxes[i] = courses.get(i).size() - 1;
		}
		Incrementor inc = new Incrementor(courses.size(), maxes);

		// System.out.println(Arrays.toString(maxes));

		int total = 1;
		for (;; total++) {
			try {
				List<Course> classesToTest = new ArrayList<>();
				for (int i = 0; i < courses.size(); i++) {
					classesToTest.add(courses.get(i).get(inc.x[i]));
				}
				if (!checkForOverlap(classesToTest)) {
					possibleCombinations.add(classesToTest);
				}
				inc.increment();
			} catch (ArrayIndexOutOfBoundsException e) {
				break;
			}
		}

		System.out.println(possibleCombinations);
		System.out.println(possibleCombinations.size() + " / " + total);
	}

	private boolean checkForOverlap(List<Course> classesToTest) {

		for (Days day : Days.values()){

			List<Time> times = new ArrayList<>();
			for (Course c : classesToTest) {
				for (ClassGroup g : c.classGroups) {
					if (g.week.days.contains(day)) {
						times.add(g.week.time);
					}
				}
			}

			for (int i = 0; i < times.size(); i++) {
				for (int j = i + 1; j < times.size(); j++) {
					if (overLap(times.get(i), times.get(j)))
						return true;
				}
			}
		}
		
		return false;
	}

	private boolean overLap(Time time, Time time2) {

		if (time.start < time2.start) {
			return (time.end > time2.start);
		} else {
			return (time2.end > time.start);
		}

	}

	public static void main(String[] args) {
		try {
			new Calendar();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
