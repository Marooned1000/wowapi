package ca.wowapi.entities;

/**
 *  Test comment for commit
 */
public class Achievement {

	int aid;

	boolean completed;

	long criteriaQuantity;

	long timestamp;

	public int getAid() {
		return aid;
	}

	public long getCriteriaQuantity() {
		return criteriaQuantity;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public void setCriteriaQuantity(long criteriaQuantity) {
		this.criteriaQuantity = criteriaQuantity;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("\n");
		output.append("\t aid: " + aid + "\n");
		output.append("\t completed: " + completed + "\n");
		output.append("\t criteriaQuantity: " + criteriaQuantity + "\n");
		output.append("\t timestamp: " + timestamp + "\n");
		return output.toString();
	}
}
