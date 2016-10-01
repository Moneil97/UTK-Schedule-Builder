import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

class FastIO {

	BufferedInputStream in;
	
	public FastIO(String s) {
		in = new BufferedInputStream(new ByteArrayInputStream(s.getBytes()));
	}
	
	public void close(){
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int nextInt(){
		try {
			boolean start = false;
			int val = 0;
			while (true){
				int b = in.read();
				if (b > 47 && b < 58){
					if (!start)
						start = true;
					
					val = val*10+b-'0';
				}
				else if (start){
					return val;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public float nextFloat(){
		try {
			boolean decimalFound = false;
			boolean start = false;
			float val = 0;
			float multiplier = .1f;
			while (true){
				int b = in.read();
				if (b > 47 && b < 58){
					if (!start)
						start = true;
					
					if (!decimalFound)
						val = val*10+b-'0';
					else{
						val += (b-'0') * multiplier;
						multiplier/=10.0f;
					}
					
					//System.out.println(val);
				}
				else if (b==46){
					decimalFound = true;
					//System.out.println("found");
				}
				else if (start){
					return val;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

//	public static void main(String[] args) {
//		FastIO in = new FastIO();
//		System.out.println(in.nextInt());
//	}

}
