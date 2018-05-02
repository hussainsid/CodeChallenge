package com.barclay.theatre.model;

import java.util.ArrayList;
import java.util.List;

public class Row implements Comparable<Row> {
	
	private int rowSequenceNo;
	List<Section> sections = new ArrayList<Section>();
	private int totalNoSeatsInRow = 0 ;
	private int totalReamingSeatsInaRow = 0 ;
	boolean isRowFull = false;
	
	public Row(int rowSequenceNo) {
		super();
		this.rowSequenceNo = rowSequenceNo;
	}

	public int getRowSequenceNo() {
		return rowSequenceNo;
	}

	public boolean isRowFull() {
		return isRowFull;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
		for(Section section: sections){
			totalNoSeatsInRow += section.getSeatCount();
			totalReamingSeatsInaRow += section.getSeatCount();
		}
	}

	public boolean bookSeats(String name, int noOFSeatsRequested) {
		
		for(Section section : sections){
			if( section.isVacant() && section.getVacantSeatsCount() <= noOFSeatsRequested) {
				if(section.bookSeats(name, noOFSeatsRequested)){
					System.out.println(name+" Row "+rowSequenceNo +" Section "+section.getSectionCode());
					updateIsRowFull();
					return true;
				}
			}
		}
		return false;
	}

	public int getTotalNoSeatsInRow() {
		return totalNoSeatsInRow;
	}
	public int getCurrentRemaingSeatsInaRow(){
		return totalReamingSeatsInaRow;
	}

	@Override
	public int compareTo(Row row) {
		return rowSequenceNo - row.getRowSequenceNo();
	}
	
	private void updateIsRowFull(){
		for(Section section : sections){
			totalReamingSeatsInaRow += section.getVacantSeatsCount();
			if(!section.isVacant()){
				isRowFull = true;
			}
		}
	}

}
