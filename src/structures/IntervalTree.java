package structures;

import java.util.ArrayList;


/**
 * Encapsulates an interval tree.
 * 
 * @author runb-cs112
 */
public class IntervalTree {

	/**
	 * The root of the interval tree
	 */
	IntervalTreeNode root;

	/**
	 * Constructs entire interval tree from set of input intervals. Constructing the tree
	 * means building the interval tree structure and mapping the intervals to the nodes.
	 * 
	 * @param intervals Array list of intervals for which the tree is constructed
	 */
	public IntervalTree(ArrayList<Interval> intervals) {


		// make a copy of intervals to use for right sorting
		ArrayList<Interval> intervalsRight = new ArrayList<Interval>(intervals.size());
		for (Interval iv : intervals) {
			intervalsRight.add(iv);
		}

		// rename input intervals for left sorting
		ArrayList<Interval> intervalsLeft = intervals;

		// sort intervals on left and right end points
		sortIntervals(intervalsLeft, 'l');
		sortIntervals(intervalsRight,'r');

		// get sorted list of end points without duplicates
		ArrayList<Integer> sortedEndPoints = 
				getSortedEndPoints(intervalsLeft, intervalsRight);

		// build the tree nodes
		root = buildTreeNodes(sortedEndPoints);

		// map intervals to the tree nodes
		mapIntervalsToTree(intervalsLeft, intervalsRight);
	}

	/**
	 * Returns the root of this interval tree.
	 * 
	 * @return Root of interval tree.
	 */
	public IntervalTreeNode getRoot() {
		return root;
	}

	/**
	 * Sorts a set of intervals in place, according to left or right endpoints.  
	 * At the end of the method, the parameter array list is a sorted list. 
	 * 
	 * @param intervals Array list of intervals to be sorted.
	 * @param lr If 'l', then sort is on left endpoints; if 'r', sort is on right endpoints
	 */
	public static void sortIntervals(ArrayList<Interval> intervals, char lr) {

			
		if(lr== 'l'){
			for(int i=1; i< intervals.size(); i++){ //insertion sort for unsorted array
				int ptr = intervals.get(i).leftEndPoint; //start of unsorted region
				int prev = i-1; //end of sorted region
				int y = i;
				while(prev>=0){
					if(intervals.get(prev).leftEndPoint > ptr){
						Interval temp = new Interval (intervals.get(y).leftEndPoint, intervals.get(y).rightEndPoint, intervals.get(y).desc);
						intervals.set(y, intervals.get(prev));
						intervals.set(prev, temp);
						prev--;
						y--;
					}else{
						break;
					}
				}
			}
		}
		if(lr == 'r'){
			for(int i=1; i< intervals.size(); i++){ //insertion sort for unsorted array
				int ptr = intervals.get(i).rightEndPoint; //start of unsorted region
				int prev = i-1; //end of sorted region
				int y = i;
				while(prev>=0){
					if(intervals.get(prev).rightEndPoint > ptr){
						Interval temp = new Interval (intervals.get(y).leftEndPoint, intervals.get(y).rightEndPoint, intervals.get(y).desc);
						intervals.set(y, intervals.get(prev));
						intervals.set(prev, temp);
						prev--;
						y--;
					}else{
						break;
					}
				}
			}
		}
		//		print(intervals);
		//		System.out.println(" ");
		//COMPLETE THIS METHOD
	}

	//tells me if it is already there or not
	private static boolean search(ArrayList <Integer> points, int point){
		if(points.size()==0){ //first item being added
			return false;
		}
		if(points.contains(point) == true){
			return true;
		}
		return false;
	}

	private static int position(ArrayList <Integer> points, int val){
		for(int i=0; i< points.size(); i++){
			if(points.size()==0){ //empty list
				return 0;
			}
			if(points.get(i).intValue() > val){
				return i;
			}
		}
		return -1;
	}

	/**
	 * Given a set of intervals (left sorted and right sorted), extracts the left and right end points,
	 * and returns a sorted list of the combined end points without duplicates.
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 * @return Sorted array list of all endpoints without duplicates
	 */
	public static ArrayList<Integer> getSortedEndPoints(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {

		ArrayList <Integer> points = new ArrayList<Integer>();
		for(int i=0; i< leftSortedIntervals.size(); i++){ //first create list with left points
			if(search(points,leftSortedIntervals.get(i).leftEndPoint)== false){ //no duplicates
				int pos = position(points, leftSortedIntervals.get(i).leftEndPoint);//where to add
				if(pos== -1 || pos == 0){ //add onto the end
					points.add(leftSortedIntervals.get(i).leftEndPoint);
				}else{ //add to specific spot
					points.add(pos, leftSortedIntervals.get(i).leftEndPoint);
				}
			}
		}

		for(int i=0; i< rightSortedIntervals.size(); i++){ //now create with right end points
			if(search(points,rightSortedIntervals.get(i).rightEndPoint)== false){ //no duplicates
				int pos = position(points, rightSortedIntervals.get(i).rightEndPoint);
				if(pos== -1 || pos == 0){ //add onto the end
					points.add(rightSortedIntervals.get(i).rightEndPoint);
				}else{ //add to specific spot
					points.add(pos, rightSortedIntervals.get(i).rightEndPoint);
				}
			}
		}

		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE PROGRAM COMPILE
		//		System.out.println("points");
		//		printP(points);

		return points;
	}

	/**
	 * Builds the interval tree structure given a sorted array list of end points
	 * without duplicates.
	 * 
	 * @param endPoints Sorted array list of end points
	 * @return Root of the tree structure
	 */
	public static IntervalTreeNode buildTreeNodes(ArrayList<Integer> endPoints) {

		if(endPoints.isEmpty()){
			return null;
		}

		Queue<IntervalTreeNode> Q = new Queue<IntervalTreeNode>();

		//create a node for every point
		IntervalTreeNode T= null;
		for(int i=0; i< endPoints.size(); i++){
			int p= endPoints.get(i).intValue();
			T = new IntervalTreeNode(p,p,p); //what to set max and min val
			T.leftIntervals = new ArrayList<Interval>();
			T.rightIntervals = new ArrayList<Interval>();
			Q.enqueue(T);
			//print each node made
//			System.out.println("First Node: " + T);
		}


		boolean flag = true;
		while(flag==true){
			int s = Q.size;
			if(s==1){ //meaning there is one thing left, go to step seven
				T=Q.dequeue();
				flag= false;
			}
			stepSix(Q);
		}

		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE PROGRAM COMPILE


		return T;
	}

	private static Queue<IntervalTreeNode> stepSix(Queue<IntervalTreeNode> Q){

		int temps=Q.size;
		while(temps>1){ 
			IntervalTreeNode T1= Q.dequeue();
			IntervalTreeNode T2 = Q.dequeue();
			float v1 = T1.maxSplitValue;
			float v2 = T2.minSplitValue;
			float x = (v1+v2)/2; //split value
			IntervalTreeNode N = new IntervalTreeNode(x,T1.minSplitValue,T2.maxSplitValue); //create node with this split value
			N.leftChild = T1;
			N.rightChild= T2;
			N.leftIntervals = new ArrayList<Interval>();
			N.rightIntervals = new ArrayList<Interval>();
//			System.out.println("the Node: " + N);
			Q.enqueue(N);
			temps= temps-2;
		}

		if(temps==1){ //is there is one left, take out, put back in
			IntervalTreeNode temp = Q.dequeue();
			Q.enqueue(temp);
		}
		
		//return the queue
		return Q;
	}

	/**
	 * Maps a set of intervals to the nodes of this interval tree. 
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 */
	public void mapIntervalsToTree(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {

		if(leftSortedIntervals.isEmpty() || rightSortedIntervals.isEmpty()){
			return;
		}

		for(int i=0; i<leftSortedIntervals.size(); i++){
			Interval x = leftSortedIntervals.get(i); //get the first interval
			float left = x.leftEndPoint; //left val
			float right = x.rightEndPoint; //right val
			boolean flag=true;
			IntervalTreeNode current = this.root;

			while(flag==true){
				float splitVal= current.splitValue;
				if(splitVal >= left && splitVal<=right){ //if its in the interval
					current.leftIntervals.add(x); //add it
					flag=false; //exit out
				}else if(splitVal>right){ //condition might be wrong
					current= current.leftChild;
				}else{
					current=current.rightChild;
				}
			}
		}

		for(int i=0; i<rightSortedIntervals.size(); i++){
			Interval x = rightSortedIntervals.get(i); //get the first interval
			float left = x.leftEndPoint; //left val
			float right = x.rightEndPoint; //right val
			boolean flag=true;
			IntervalTreeNode current = this.root;


			while(flag==true){
				float splitVal= current.splitValue;
				if(splitVal >= left && splitVal<=right){ //if its in the interval
					current.rightIntervals.add(x); //add it
					flag=false; //exit out
				}else if(splitVal>right){ //condition might be wrong
					current= current.leftChild;
				}else{
					current=current.rightChild;
				}
			}
		}

		//	printTree(this.root);

		// COMPLETE THIS METHOD
	}

	//adds intervals to the tree
	private void setInterval(ArrayList<Interval> sorted){

		if(sorted.isEmpty()){
			return;
		}

		for(int i=0; i<sorted.size(); i++){
			Interval x = sorted.get(i); //get the first interval
			float left = x.leftEndPoint; //left val
			float right = x.rightEndPoint; //right val
			boolean flag=true;
			IntervalTreeNode current = this.root;


			while(flag==true){
				float splitVal= current.splitValue;
				if(splitVal >= left && splitVal<=right){ //if its in the interval
					current.leftIntervals.add(x); //add it
					flag=false; //exit out
				}else if(splitVal>right){ //condition might be wrong
					current= current.leftChild;
				}else{
					current=current.rightChild;
				}
			}
		}
	}

	/*
	private void printTree(IntervalTreeNode tree){

		IntervalTreeNode current = root;
		IntervalTreeNode left = current.leftChild;
		IntervalTreeNode right = current.rightChild;
		boolean flag=true;


		while(right!= null){
			System.out.println("intervals "+ current.leftIntervals);
			current= current.rightChild;
		}


//			System.out.println("intervals "+ current.leftIntervals);
//			current= current.rightChild;
//		
//			System.out.println("intervals "+ current.leftIntervals);
//			current= current.leftChild;

	}
	 */ 

	/**
	 * Gets all intervals in this interval tree that intersect with a given interval.
	 * 
	 * @param q The query interval for which intersections are to be found
	 * @return Array list of all intersecting intervals; size is 0 if there are no intersections
	 */
	public ArrayList<Interval> findIntersectingIntervals(Interval q) {

		IntervalTreeNode top = this.root;
		ArrayList<Interval> ResultList = new ArrayList<Interval>();
		ArrayList<Interval> Result = new ArrayList<Interval>(recurs(top, q, ResultList));
		return Result;

		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE PROGRAM COMPILE

	}

	private ArrayList<Interval> recurs(IntervalTreeNode top, Interval inter, ArrayList<Interval> ResultList){

		
		IntervalTreeNode R = top;
		float splitVal = top.splitValue;
		ArrayList<Interval> Llist = top.leftIntervals;
		ArrayList<Interval> Rlist = top.rightIntervals;
		IntervalTreeNode Lsub = R.leftChild;
		IntervalTreeNode Rsub = R.rightChild;

		if(Lsub == null && Rsub==null){ //no children, is a leaf
			//should this be and or or
			return ResultList;
		}

		if(splitVal>= inter.leftEndPoint && splitVal<= inter.rightEndPoint){ //is in the interval
			ResultList.addAll(Llist);
			recurs(Lsub,inter, ResultList);
			recurs(Rsub,inter, ResultList);
		}else if(splitVal>inter.rightEndPoint){ //goes to the left
			int i = Rlist.size();
			for(int x=0; x<i; x++){
				Interval test = Rlist.get(x);
				int testLeft = test.leftEndPoint;
				int testRight = test.rightEndPoint;
				int left = inter.leftEndPoint;
				int right = inter.rightEndPoint; 
				if(left== testLeft || left== testRight || right==testLeft || right==testRight){
					ResultList.add(test);
				}
				else if((left > testLeft && left < testRight) || (right > testLeft && right < testRight)){ //contains it
					ResultList.add(test);
				}
			}
			recurs(Lsub,inter, ResultList);
			//*******call recursion on lefht sub tree
		}else if(splitVal< inter.rightEndPoint){ //goes to the right
			int i=0;
			while(i< Llist.size()){
				Interval test = Llist.get(i);
				int testLeft = test.leftEndPoint;
				int testRight = test.rightEndPoint;
				int left = inter.leftEndPoint;
				int right = inter.rightEndPoint; 
				if(left== testLeft || left== testRight || right==testLeft || right==testRight){
					ResultList.add(test);
				}
				else if((left >testLeft && left < testRight) || (right > testLeft && right < testRight)){ //contains it
					ResultList.add(test);
				}
				i=i+1;
			}
			recurs(Rsub,inter, ResultList);
			//*******call recursion on right subtree
		}

		return ResultList;

	}


	//	private static void print(ArrayList <Interval>intervals) {
	//		for (Interval ls: intervals) {
	//			System.out.println(ls);
	//		}
	//	}
	//
	//	private static void printP(ArrayList <Integer>points) {
	//		for (Integer in: points) {
	//			System.out.println(in);
	//		}
	//	}

}
