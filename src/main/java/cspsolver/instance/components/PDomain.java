package cspsolver.instance.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import cspsolver.instance.InstanceTokens;

public class PDomain implements Serializable {
	
	private String name;
	private int[] values;
	
	protected PVariable clone() throws CloneNotSupportedException {
		return (PVariable)super.clone();
	}

	public String getName() {
		return name;
	}
	
	public int[] getValues() {
		return values;
	}

	public int getMaxAbsoluteValue() {
		return Math.max(Math.abs(values[0]),
				Math.abs(values[values.length - 1]));
	}

	public PDomain(String name, int[] values) {
		this.name = name;
		this.values = values;
	}

	public boolean contains(int value) {
		return Arrays.binarySearch(values, value) >= 0;
	}

	public String toString() {
		int displayLimit = 5;
		String s = "  domain " + name + " with " + values.length + " values : ";
		for (int i = 0; i < Math.min(values.length, displayLimit); i++)
			s += values[i] + " ";
		return s + (values.length > displayLimit ? "..." : "");
	}

	public String getStringListOfValues() {
		int previousValue = values[0];
		boolean startedInterval = false;
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < values.length; i++) {
			int currentValue = values[i];
			if (currentValue != previousValue + 1) {
				if (startedInterval) {
					sb.append(previousValue
							+ InstanceTokens.DISCRETE_INTERVAL_END);
					startedInterval = false;
				} else
					sb.append(previousValue);
				sb.append(InstanceTokens.VALUE_SEPARATOR);
			} else {
				if (!startedInterval) {
					sb.append(InstanceTokens.DISCRETE_INTERVAL_START
							+ previousValue
							+ InstanceTokens.DISCRETE_INTERVAL_SEPARATOR);
					startedInterval = true;
				}
			}
			previousValue = currentValue;
		}
		if (startedInterval)
			sb.append(previousValue + InstanceTokens.DISCRETE_INTERVAL_END);
		else
			sb.append(previousValue);
		return sb.toString();
	}

	public void remove(int c)	{
		
		int[] result = new int[values.length-1];
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i:values){
			if(i!=c){
				temp.add(i);
			}
		}
		
		for(int i=0;i<temp.size();i++){
			result[i] = temp.get(i);
		}
		
		this.values = result;
	}
	public void setValues(int[] values) {
		this.values = values;
	}

	public boolean controlValueRanging(int min, int max) {
		for (int v : values)
			if (v < min || v > max)
				return false;
		return true;
	}

}
