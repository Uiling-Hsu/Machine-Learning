package dataBean;

public class Function {
	private double s;
	private double threshold;
	private int feature_index;
	private double updateCoefficient;
	private double alpha;
	private double errRate;
	private int minArgIndex;
	private boolean isLeaves = false;
	private double value;
	
	public double getS() {
		return s;
	}
	public void setS(double s) {
		this.s = s;
	}
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	public int getFeature_index() {
		return feature_index;
	}
	public void setFeature_index(int feature_index) {
		this.feature_index = feature_index;
	}
	public double getUpdateCoefficient() {
		return updateCoefficient;
	}
	public void setUpdateCoefficient(double updateCoefficient) {
		this.updateCoefficient = updateCoefficient;
	}
	public double getAlpha() {
		return alpha;
	}
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	public double getErrRate() {
		return errRate;
	}
	public void setErrRate(double errRate) {
		this.errRate = errRate;
	}
	public int getMinArgIndex() {
		return minArgIndex;
	}
	public void setMinArgIndex(int minArgIndex) {
		this.minArgIndex = minArgIndex;
	}
	public boolean isLeaves() {
		return isLeaves;
	}
	public void setLeaves(boolean isLeaves) {
		this.isLeaves = isLeaves;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	
}
