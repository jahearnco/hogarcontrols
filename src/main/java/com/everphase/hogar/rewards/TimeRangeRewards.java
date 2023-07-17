package com.everphase.hogar.rewards;

import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.Set;
import java.util.HashMap;
import java.util.List;

public class TimeRangeRewards {

	private Long customerId;
	private Map<String, List<Integer>> timeRangeRewards = new HashMap<String, List<Integer>>();

	public TimeRangeRewards() {
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public Map<String, List<Integer>> getTimeRangeRewards() {
		return timeRangeRewards;
	}

	public void setTimeRangeRewards(Map<String, List<Integer>> timeRangeRewards) {
		this.timeRangeRewards = timeRangeRewards;
	}

	public void put(String intervalName, List<Integer> rewards) {
		this.timeRangeRewards.put(intervalName, rewards);
	}

	public List<Integer> get(String intervalName) {
		return this.timeRangeRewards.get(intervalName);
	}

	public boolean containsKey(String intervalName) {
		return this.timeRangeRewards.containsKey(intervalName);
	}

	public Set<Entry<String, List<Integer>>> entrySet() {
		return this.timeRangeRewards.entrySet();
	}

	public Set<String> keySet() {
		return this.timeRangeRewards.keySet();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof TimeRangeRewards))
			return false;
		TimeRangeRewards timeRangeRewards = (TimeRangeRewards) o;
		return Objects.equals(this.customerId, timeRangeRewards.customerId)
				&& Objects.equals(this.timeRangeRewards, timeRangeRewards.timeRangeRewards);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.customerId, this.timeRangeRewards);
	}

	@Override
	public String toString() {
		return "TimeRangeRewards{customerId=" + this.customerId + ", timeRangeRewards=" + this.timeRangeRewards + "}";
	}
}
