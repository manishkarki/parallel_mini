package edu.coursera.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * @author mkarki
 */
public class CustomRecursiveAction extends RecursiveAction {
	private String workload;
	private static final int THRESHOLD = 4;

	public CustomRecursiveAction(String workload) {
		this.workload = workload;
	}

	@Override
	protected void compute() {
		if(workload.length() > THRESHOLD) {
			ForkJoinTask.invokeAll(createSubtasks());
		} else {
			processing(workload);
		}
	}

	private void processing(String workload) {
		System.out.println("This result:"+workload.toUpperCase()+" was processed by " +
				Thread.currentThread().getName());
	}

	private List<CustomRecursiveAction> createSubtasks() {
		List<CustomRecursiveAction> subtasks = new ArrayList<>();

		String part1 = workload.substring(0, workload.length()/2);
		String part2 = workload.substring(workload.length()/2);

		subtasks.add(new CustomRecursiveAction(part1));
		subtasks.add(new CustomRecursiveAction(part2));

		return subtasks;
	}

	public static void main(String[] args) {
		CustomRecursiveAction customRecursiveAction = new CustomRecursiveAction("manisha");
		customRecursiveAction.compute();
	}


}
