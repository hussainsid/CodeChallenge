package com.barclay.theatre.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author HussainPeera
 *
 */
public class Section implements Comparable<Section>{
	
	private int seatCount ;
	private boolean isVacant = true;
	private Map<String, Integer> reservationList = new HashMap<>();
	private int vacantSeats ;
	private int sectionCode;

	public Section(int sectionCode, int seatCount) {
		super();
		this.sectionCode = sectionCode;
		this.seatCount = seatCount;
		this.vacantSeats = seatCount;
	}

	public int getSeatCount() {
		return seatCount;
	}
	
	public int getVacantSeatsCount(){
		return vacantSeats;
	}
	
	
	
	public int getSectionCode() {
		return sectionCode;
	}

	public boolean isVacant() {
		return isVacant;
	}

	public boolean bookSeats(String name,int noOFSeatsRequested){
		boolean isBookingSuccess = false;
		if(noOFSeatsRequested <= seatCount){
			if(reservationList.isEmpty()){
				if(noOFSeatsRequested == seatCount){
					isVacant = false;
					reservationList.put(name, noOFSeatsRequested);
					isBookingSuccess = true;
				}
			} else {
				int filledSeats = 0;
				for(Map.Entry<String, Integer> entry : reservationList.entrySet()){
					filledSeats += entry.getValue();
				}
				int vacantSeats = seatCount - filledSeats ;
				if(vacantSeats <= noOFSeatsRequested){
					reservationList.put(name, noOFSeatsRequested);
					isBookingSuccess = true;
				}
			}
		}else {
			isBookingSuccess = false;
		}
		updateIsVacant();
		return isBookingSuccess;
		
	}

	private void updateIsVacant(){
		
		int filledSeats = 0;
		for(Map.Entry<String, Integer> entry : reservationList.entrySet()){
			filledSeats += entry.getValue();
		}
		vacantSeats = seatCount - filledSeats;
		if(filledSeats == seatCount){
			isVacant = false;
		} else {
			isVacant = true;
		}
		
	}

	@Override
	public int compareTo(Section section) {
		return this.seatCount - section.seatCount;
	}
}
