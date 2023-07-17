package com.everphase.hogar.transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
public class Transaction {
	private static Logger log = LoggerFactory.getLogger(Transaction.class);

	@Id
	@GeneratedValue
	private Long id;

	private String userId;
	private BigDecimal amount;
	private Long epochSecs; // assume transactions exist only after 1970
	private Integer rewardsPoints;

	public Transaction() {
		log.info("Transaction() initialized");
	}

	public Transaction(BigDecimal amount, Long epochSecs, Long customerId, String userId) {
		this.amount = amount;
		this.epochSecs = epochSecs;
		this.userId = userId;
		log.info("Transaction(amount:" + amount + ", epochSecs:" + epochSecs + ", transactionId:" + id + ", customerId:"
				+ customerId + ", userId:" + userId + ") called");
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getEpochSecs() {
		return this.epochSecs;
	}

	public void setEpochSecs(Long epochSecs) {
		this.epochSecs = epochSecs;
	}

	public Integer getRewardsPoints() {
		return this.rewardsPoints;
	}

	public void setRewardsPoints(Integer rewardsPoints) {
		this.rewardsPoints = rewardsPoints;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	// calc rewards points and return to be persisted along with transaction
	public Integer calcRewardsPoints(BigDecimal b) {
		int rwds = 0;

		// rounding down to accurately calc int number of dollars offset from 100 and 50
		int bRoundedDown = (b.setScale(0, RoundingMode.DOWN)).intValue();

		if (bRoundedDown > 100) {
			rwds = 50 + (bRoundedDown - 100) * 2;
		} else if (bRoundedDown > 50) {
			rwds = bRoundedDown - 50;
		}

		return (Integer) rwds;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof Transaction))
			return false;
		Transaction transaction = (Transaction) o;
		return Objects.equals(this.id, transaction.id) && Objects.equals(this.amount, transaction.amount)
				&& Objects.equals(this.epochSecs, transaction.epochSecs)
				&& Objects.equals(this.rewardsPoints, transaction.rewardsPoints)
				&& Objects.equals(this.userId, transaction.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.amount, this.epochSecs, this.rewardsPoints, this.userId);
	}

	@Override
	public String toString() {
		return "Transaction{" + "id=" + this.id + ", amount='" + this.amount + "', epochSecs='" + this.epochSecs
				+ "'', rewardsPoints='" + this.rewardsPoints + "', userId='" + this.userId + "'}";
	}
}