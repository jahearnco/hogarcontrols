package com.everphase.hogar.rewards;

import java.util.Map;
import java.util.Objects;
import java.util.HashMap;

public class LabeledTimeRangeRewardsTotals {

	private Map<String, TimeRangeRewardsTotals> labeledTimeRangeRewardsTotals = new HashMap<String, TimeRangeRewardsTotals>();

	public LabeledTimeRangeRewardsTotals() {
	}

	public Map<String, TimeRangeRewardsTotals> getLabeledTimeRangeRewardsTotals() {
		return labeledTimeRangeRewardsTotals;
	}

	public void setLabeledTimeRangeRewardsTotals(Map<String, TimeRangeRewardsTotals> labeledTimeRangeRewardsTotals) {
		this.labeledTimeRangeRewardsTotals = labeledTimeRangeRewardsTotals;
	}

	public void put(String label, TimeRangeRewardsTotals timeRangeRewardsTotals) {
		labeledTimeRangeRewardsTotals.put(label, timeRangeRewardsTotals);
	}

	public TimeRangeRewardsTotals get(String label) {
		return labeledTimeRangeRewardsTotals.get(label);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof LabeledTimeRangeRewardsTotals))
			return false;
		LabeledTimeRangeRewardsTotals labeledTimeRangeRewardsTotals = (LabeledTimeRangeRewardsTotals) o;
		return Objects.equals(this.labeledTimeRangeRewardsTotals,
				labeledTimeRangeRewardsTotals.labeledTimeRangeRewardsTotals);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.labeledTimeRangeRewardsTotals);
	}

	@Override
	public String toString() {
		return "" + this.labeledTimeRangeRewardsTotals;
	}
}