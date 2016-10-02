import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Calendar {

	List<List<Course>> possibleCombinations = new ArrayList<>();
	
	public List<List<Course>> getPossibleCombinations() {
		return possibleCombinations;
	}

	List<List<Course>> courses;
	
	boolean[] daysFilter = {false,false,false,false,false};
	
	public void setDayFilter(boolean[] days){
		daysFilter = days;
	}
	
	List<Time> timesFilter = new ArrayList<>();
	
	public void setTimesFilter(List<Time> times){
		timesFilter = times;
	}
	
	List<String> teachersFilter = new ArrayList<>();
	
	public void setTeacherFilter(List<String> teachers){
		teachersFilter = teachers;
	}
	
	List<String> locationsFilter = new ArrayList<>();
	
	public void setLocationsFilter(List<String> locations){
		locationsFilter = locations;
	}
	
	boolean fullFilter = true;
	
	public void setFullFilter(boolean full){
		fullFilter = full;
	}
	
	private boolean filtered(List<Course> classesToTest) {
		
		if (fullFilter){
			for (Course c: classesToTest){
				if (c.full)
					return true;
			}
		}
		
		return false;
	}

	
	Incrementor inc;
	
	public List<List<Course>> compute(){

		inc.reset();
		
		int total = 1;
		for (;; total++) {
			try {
				List<Course> classesToTest = new ArrayList<>();
				for (int i = 0; i < courses.size(); i++) {
					classesToTest.add(courses.get(i).get(inc.x[i]));
				}
				if (!checkForOverlap(classesToTest) && !filtered(classesToTest)) {
					possibleCombinations.add(classesToTest);
				}
				inc.increment();
			} catch (ArrayIndexOutOfBoundsException e) {
				break;
			}
		}

		System.out.println(possibleCombinations.size() + " / " + total);
		return possibleCombinations;
	}
	
	

	public Calendar() throws IOException {

		//takes ~.4 seconds
		courses = new Parser().courses;
		
		int maxes[] = new int[courses.size()];
		for (int i = 0; i < courses.size(); i++) {
			maxes[i] = courses.get(i).size() - 1;
		}
		
		inc = new Incrementor(courses.size(), maxes);
		
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
