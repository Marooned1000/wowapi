package ca.wowapi.entities;

public class Achievement {

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getCriteriaQuantity() {
		return criteriaQuantity;
	}

	public void setCriteriaQuantity(long criteriaQuantity) {
		this.criteriaQuantity = criteriaQuantity;
	}

	int aid;
	boolean completed;
	long timestamp;
	long criteriaQuantity;
}
