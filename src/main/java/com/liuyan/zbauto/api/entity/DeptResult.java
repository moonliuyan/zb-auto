package com.liuyan.zbauto.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public class DeptResult {
	private LinkedHashMap<Double, Double> asks; // 卖方深度,为了方便取数,1.需要取反 2.map很麻烦取数,需要转list
	private LinkedHashMap<Double, Double> bids; // 买方深度
	private long timestamp;
	
	
	/**
	 * 获取卖方深度(有经过取反,list.get(0)为卖1)
	 */
	public List<Dept> getAsks() {
		List<Dept> asksList = new LinkedList<Dept>();
		ListIterator<Map.Entry<Double, Double>> i = new ArrayList<Map.Entry<Double, Double>>(asks.entrySet())
				.listIterator(asks.size());
		Dept dept = null;
		while (i.hasPrevious()) {
			Map.Entry<Double, Double> entry = i.previous();
			dept = new Dept(entry.getKey(), entry.getValue());
			asksList.add(dept);
		}
		return asksList;
	}
	
	/**
	 * 获取不需要反转
	 * @return
	 */
	public List<Dept> getAsksNoReverse() {
		List<Dept> asksList = new LinkedList<Dept>();
		Iterator<Double> iterator = asks.keySet().iterator();
		Dept dept = null;
		while(iterator.hasNext()){
			double price = iterator.next();
			dept = new Dept(price, asks.get(price));
			asksList.add(dept);
		}
		return asksList;
	}
	
	/**
	 * 获取买方深度
	 */
	public List<Dept> getBids() {
		List<Dept> bidsList = new LinkedList<Dept>();
		Iterator<Double> iterator = bids.keySet().iterator();
		Dept dept = null;
		while(iterator.hasNext()){
			Double price = iterator.next();
			dept = new Dept(price, bids.get(price));
			bidsList.add(dept);
		}
		return bidsList;
	}

	/**
	 * 深度
	 */
	@Data
	@AllArgsConstructor
	public static class Dept implements Serializable{
		private static final long serialVersionUID = -2255650367031625151L;
		private double price;
		private double amount;
	}
}
