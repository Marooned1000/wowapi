package ca.wowapi.entities;

public class ItemSource {

	private int sourceId;
	
	private String sourceType;

	public ItemSource() {

	}

	public int getSourceId() {
		return sourceId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("\t sourceId: " + sourceId + "\n");
		output.append("\t sourceType: " + sourceType + "\n");
		return output.toString();
	}
}
