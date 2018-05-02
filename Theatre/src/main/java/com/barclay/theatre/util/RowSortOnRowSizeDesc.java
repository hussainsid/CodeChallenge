package com.barclay.theatre.util;

import java.util.Comparator;

import com.barclay.theatre.model.Row;

public class RowSortOnRowSizeDesc implements Comparator<Row>{

	@Override
	public int compare(Row row1, Row row2) {
		return row2.getTotalNoSeatsInRow() - row1.getTotalNoSeatsInRow();
	}

}
