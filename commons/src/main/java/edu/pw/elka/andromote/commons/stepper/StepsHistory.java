package edu.pw.elka.andromote.commons.stepper;

import java.util.LinkedList;

/**
 * Historia kroków samochodu. Klasa umożliwia powrót do miejsca, w którym zasięg
 * został znaleziony lub powrót do miejsca startu.
 * 
 * @author Maciej Gzik
 * 
 */
public class StepsHistory {

	private LinkedList<Step> steps;

	public StepsHistory() {
		this.steps = new LinkedList<Step>();
	}

	public Step getLastStep() {
		if (this.steps != null && this.steps.size() > 0) {
			Step lastStep = this.steps.removeLast();
			return lastStep;
		} else {
			return null;
		}
	}

	public void addStep(Step step) {
		if (this.steps != null) {
			this.steps.addLast(step);
		}
	}

	public int getLength() {
		if (this.steps != null) {
			return this.steps.size();
		} else {
			return 0;
		}
	}

	public Step getStep(int index) {
		return this.steps.get(index);
	}

}
