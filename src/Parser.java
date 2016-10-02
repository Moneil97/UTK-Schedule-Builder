import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Parser {
	
	List<List<Course>> courses = new ArrayList<>();
	
	public Parser() throws IOException {
		for (File f : getAllFiles()){
			parse(f);
			//System.out.println("\n\n");
		}
	}

	public void parse(File f) throws IOException {

		List<Course> classes = new ArrayList<>();

		Scanner scan = new Scanner(f);
		Course newest = null;
		while (scan.hasNextLine()) {
			String line = scan.nextLine();

			// Look for a Class
			if (line.startsWith("<tr>")) {
				line = scan.nextLine();
				//New Class
				if (line.startsWith("<td class=\"dddefault\"><abbr title=")) {

					line = scan.nextLine();
					line = line.substring(line.indexOf("crn_in="));
					newest = new Course(new FastIO(line).nextInt());
					newest.addClassGroup();
					classes.add(newest);
					
					// Get Subject
					line = scan.nextLine();
					newest.setSubject(trim(line));

					// Get Course #
					line = scan.nextLine();
					newest.setCourse((new FastIO(line).nextInt()));

					// Get Credit
					skipLines(scan, 2);
					line = scan.nextLine();
					newest.setCredit((new FastIO(line).nextFloat()));

					// Get Title
					line = scan.nextLine();
					newest.setTitle(trim(line));

					// Get days and times
					newest.setDaysAndTime(trim(scan.nextLine()), trim(scan.nextLine()));
					
					// Get class cap, actual, remaining
					newest.setSeats(new FastIO(scan.nextLine()).nextInt(),new FastIO(scan.nextLine()).nextInt(),new FastIO(scan.nextLine()).nextInt());
					
					// Get wl stuff
					line = scan.nextLine();
					line = scan.nextLine();
					line = scan.nextLine();

					// Get crosslist(?) stuff
					line = scan.nextLine();
					line = scan.nextLine();
					line = scan.nextLine();

					// Get teacher
					line = trimProf(scan.nextLine());
					newest.setTeacher(line);

					// Get class term
					line = scan.nextLine();

					// Get Location
					line = scan.nextLine();
					newest.setLocation(trimLocation(line));

					// Get attribute
					line = scan.nextLine();

					//System.out.println(newest);

				}
				//Additional class group
				else if (line.startsWith("<td class=\"dddefault\">&nbsp;</td>")){
					
					//Lab, recitation, lecture, etc.
					newest.addClassGroup();
					
					
					skipLines(scan, 7);
					try{
						newest.setDaysAndTime(trim(scan.nextLine()), trim(scan.nextLine()));
					}catch(Exception e){
						//System.out.println("Class TBA");
					}
					
					skipLines(scan, 9);
					newest.setTeacher(trimProf(scan.nextLine()));
					
					skipLines(scan, 1);
					line = scan.nextLine();
					newest.setLocation(trimLocation(line));
				}
			}
		}

		new Date();
		
		courses.add(classes);
		
//		for (Course c : classes)
//			System.out.println(c);

		scan.close();
	}

	private int start = "<td class=\"dddefault\">".length();
	private int end = "</td>".length();

	private String trim(String s) {
		return s.substring(start, s.length() - end);
	}

	private String trimProf(String line) {

		line = trim(line);

		if (line.startsWith("<"))
			return line.substring("<abbr title=\"To Be Announced\">".length(), line.length() - "</abbr>".length());
		else if (line.contains("("))
			return line.substring(0, line.indexOf("(") - 1);
		else
			return line.substring(0, line.length() - 1);
	}
	
	private String trimLocation(String line) {

		line = trim(line);

		if (line.startsWith("<abbr"))
			return line.substring("<abbr title=\"To Be Announced\">".length(), line.length() - "</abbr>".length());
		else
			return line.substring(0, line.length());
	}

	public void skipLines(Scanner scan, int i) {
		while (i-- > 0)
			scan.nextLine();
	}
	
	public File[] getAllFiles(){
		return new File("src/HTML_FILES/").listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".html");
			}
		});
	}
	
	public static void main(String[] args) {
		try {
			new Parser();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
