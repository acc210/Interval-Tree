package apps;
import java.util.ArrayList;
import structures.*;

public class test {

	public static void main(String[] args){
		ArrayList<Interval> t = new ArrayList<Interval>();
	t.add(new Interval(1,2,""));
		t.add(new Interval(1,8,"one"));
		t.add(new Interval(2,5,"two"));
		t.add(new Interval(3,7,"three"));
		t.add(new Interval(1,2,"four"));
		t.add(new Interval(2,4,"five"));
		t.add(new Interval(6,8,"six"));
		t.add(new Interval(1,3,"seven"));
		
	IntervalTree h = new IntervalTree(t);

		
	
	 

	
	
	
	}
	
}
