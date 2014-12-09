package pippin;

public class Processor {

	private int accumulator;
	private int programCounter;
	//ADD GETTERS AND SETTERS FOR BOTH FIELDS
	public void incrementCounter() {
		setProgramCounter(getProgramCounter() + 1);

	}
	public int getAccumulator() {
		return accumulator;
	}
	public void setAccumulator(int accumulator) {
		this.accumulator = accumulator;
		
	}
	public int getProgramCounter() {
		return programCounter;
	}
	public void setProgramCounter(int programCounter) {
		this.programCounter = programCounter;
	}
}