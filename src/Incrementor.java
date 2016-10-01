import java.util.Arrays;

public class Incrementor {

	int x[];
	int max[];
	
	public Incrementor(int size, int...maxes) {
		
		if (maxes.length != size){
			System.err.println("not same size");
			return;
		}
		
		x = new int[size];
		max = maxes; 
//		
//		for (int i = 0; i < size; i++)
//			max[i] = maxes[i];
		
	}
	
	public void increment() throws ArrayIndexOutOfBoundsException{
		
		x[x.length-1]++;
		
		for (int i=x.length-1; i >= 0; i--){
			if (x[i] > max[i] ){
				x[i-1] += 1; //will throw error if MSB goes over
				x[i] = 0;
			}
		}	
	}
	
	
	
	public static void main(String[] args) {
		Incrementor i = new Incrementor(3, 4,3,2);	
		
		while (true){
			i.increment();
			System.out.println(Arrays.toString(i.x));
		}
	}

}
