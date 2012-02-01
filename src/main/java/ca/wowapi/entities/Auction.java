package ca.wowapi.entities;

public class Auction {

	private long bid;

	private long buyout;

	private long id;

	private int item;

	private String owner;

	private int quantity;

	private String timeLeft;

	public Auction() {

	}

	public long getBid() {
		return bid;
	}

	public long getBuyout() {
		return buyout;
	}

	public long getId() {
		return id;
	}

	public int getItem() {
		return item;
	}

	public String getOwner() {
		return owner;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getTimeLeft() {
		return timeLeft;
	}

	public void setBid(long bid) {
		this.bid = bid;
	}

	public void setBuyout(long buyout) {
		this.buyout = buyout;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setTimeLeft(String timeLeft) {
		this.timeLeft = timeLeft;
	}

	public String toString() {
		return id + ", " + item + ", \t" + owner + ", \t" + bid / 10000 + ", \t" + buyout / 10000 + ", \t" + quantity + ", \t" + timeLeft;
	}
}
