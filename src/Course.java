import java.util.ArrayList;
import java.util.List;

public class Course {

	int crn, course, size, space;
	float credit;
	String subject, title, teacher, location, days, time;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Course(int crn) {
		this.crn = crn;
	}

	public int getCrn() {
		return crn;
	}

	public void setCrn(int crn) {
		this.crn = crn;
	}

	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSpace() {
		return space;
	}

	public void setSpace(int space) {
		this.space = space;
	}

	public float getCredit() {
		return credit;
	}

	public void setCredit(float credit) {
		this.credit = credit;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// public String getTeacher() {
	// return teacher;
	// }

	public void setTeacher(String teacher) {
		this.teacher = teacher;

		getLatestClassGroup().setTeacher(teacher);

	}

	// public String getLocation() {
	// return location;
	// }

	public void setLocation(String location) {
		this.location = location;

		getLatestClassGroup().setLocation(location);
	}

	public void setDaysAndTime(String days, String time) {
		// These are only used for being printed, they will only print out the
		// last set
		this.days = days;
		this.time = time;

		for (char c : days.toCharArray())
			// week.addTime(Days.getDay(c), time);
			getLatestClassGroup().addATime(Days.getDay(c), time);

	}

	public String toString() {
		String s = "CRN: " + crn + " | Title: " + title + " | Course: " + subject + " " + course + " | Credit: "
				+ credit;
		for (ClassGroup c : classGroups)
			s += "\n" + c;
		return s + "\n";
	}

	private ClassGroup getLatestClassGroup() {
		return classGroups.get(classGroups.size() - 1);
	}

	List<ClassGroup> classGroups = new ArrayList<>();

	public void addClassGroup() {
		classGroups.add(new ClassGroup());
	}

}

class ClassGroup {

	// Each class section. Example: Lab, recitation, lecture, etc.
	Week week = new Week();
	String teacher;
	String location;

	public ClassGroup() {}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public void addATime(Days day, String time) {
		week.addTime(day, time);
	}

	public String toString() {
		return "\tDay&Time: " + week + " | " + "Professor: " + teacher + " | Location: " + location;
	}
}

enum Days {

	MONDAY("M", 0), TUESDAY("T", 1), WEDNESDAY("W", 2), THURSDAY("R", 3), FRIDAY("F", 4), ERROR("E", 5);

	String abr;
	int dayNumber;

	Days(String abbreviation, int dayNum) {
		abr = abbreviation;
		dayNumber = dayNum;
	}

	public static Days getDay(char abr) {
		switch (abr) {
		case 'M':
			return MONDAY;
		case 'T':
			return TUESDAY;
		case 'W':
			return WEDNESDAY;
		case 'R':
			return THURSDAY;
		case 'F':
			return FRIDAY;
		default:
			return ERROR;
		}
	}
}

class Time {

	int start = 0, end = 0;

	public Time(String startString, String endString) {

		// Start Time
		FastIO t = new FastIO(startString);
		int hour = t.nextInt();
		start += hour * 60 + t.nextInt();
		if (startString.contains("pm") && hour != 12)
			start += 12 * 60;

		// End Time
		t = new FastIO(endString);
		hour = t.nextInt();
		end += hour * 60 + t.nextInt();
		if (endString.contains("pm") && hour != 12)
			end += 12 * 60;
		t.close();

		// System.out.println(startString + " - " + endString);
		// System.out.println(start + " - " + end);
	}
	
	public String toString() {
		return start + " - " + end;
	}

}

class Week {
	
	List<Days> days = new ArrayList<>();
	Time time = null;

	public Week() {}

	public void addTime(Days day, String time) {
		days.add(day);
		if (this.time == null)
			this.time = new Time(time.substring(0, time.indexOf("-")), time.substring(time.indexOf("-") + 1));
	}

	public String toString() {
		
		String s = "";
		for (Days d : days)
			s += d.abr;
		return s + " " + time;
	}

}
