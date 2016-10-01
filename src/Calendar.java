import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calendar {

	public Calendar() throws IOException {

		List<List<Course>> courses = new Parser().courses;
		//System.out.println(courses);
		int maxes[] = new int[courses.size()];
		for (int i=0; i < courses.size(); i++){
			maxes[i] = courses.get(i).size()-1;
		}
		Incrementor inc = new Incrementor(courses.size(), maxes);
		
		//System.out.println(Arrays.toString(maxes));
		
		while (true){
			try{
				List<Course> classesToTest = new ArrayList<>();
				
				//System.out.println(Arrays.toString(inc.x));
				
				for (int i=0; i < courses.size(); i++){
					classesToTest.add(courses.get(i).get(inc.x[i]));
				}
				
				//System.out.println(classesToTest);
				checkForOverlap(classesToTest);
				
				inc.increment();
			}catch(ArrayIndexOutOfBoundsException e){}
			
		}
	}
	
	
	
	
	private void checkForOverlap(List<Course> classesToTest) {
		// TODO Auto-generated method stub
		
	}




	public static void main(String[] args) {
		try {
			new Calendar();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
