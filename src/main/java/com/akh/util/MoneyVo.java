package com.akh.util;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "money", schema = "cods_venom")
public class MoneyVo {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer Id;
	private String reason;
	private float amount;
	@CreationTimestamp
	private Date  date;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "MoneyVo [Id=" + Id + ", reason=" + reason + ", amount=" + amount + ", date=" + date + "]";
	}
	
	
	
	
	

}
