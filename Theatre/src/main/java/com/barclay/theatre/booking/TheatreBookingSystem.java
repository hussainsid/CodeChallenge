package com.barclay.theatre.booking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.barclay.theatre.model.Row;
import com.barclay.theatre.model.Section;
import com.barclay.theatre.util.RowSortOnNoOfAvailableSeats;
import com.barclay.theatre.util.RowSortOnRowSizeDesc;

/**
 * 
 * @author Hussain Peera Kanekal
 *
 */
public class TheatreBookingSystem {
	

	public static void main(String[] args) {
		String fileName = "";
		int layoutArray[][];
		List<String> listOfLinesForRows = new ArrayList<>();
		List<String> listOfLinesForBooking = new ArrayList<>();
		String regexNumber = "\\d+";
		String stringRegEx = "^[A-Za-z, ]++$";
		
		if(args.length == 0){
			
			System.out.println("provide input file full path as first argument ");
			System.exit(0);
		}
		fileName = args[0];
		
		//validate file and parse the input file 
		readInputFile(fileName, listOfLinesForRows, listOfLinesForBooking, regexNumber, stringRegEx);
		
		layoutArray = new int [listOfLinesForRows.size()][];
		int i = 0;
		for(String line : listOfLinesForRows){
			String[] sections = line.split(" ");
			layoutArray[i] = new int [sections.length];
			int j = 0;
			for(String section : sections){
				int sectionValue = Integer.parseInt(section);
				layoutArray[i][j] = sectionValue;
				j++;
			}
			i++;
		}
		
		int max = 0;
		
		for (int k = 0; k < layoutArray.length; k++) {
			if (max < layoutArray[k].length) {
		        max = layoutArray[k].length;
		    }
		}
		List<Row> rowList = new ArrayList<>();//use good collections
		int decrementCounter = max-1;
		int rowSeqNo = 1;
		while(decrementCounter >= 0){
			Row row = new Row(rowSeqNo);
			List<Section> sectionList = new ArrayList<>();
			int sectionCounter = 1;
			for(int rowCount = 0; rowCount <= layoutArray.length-1; rowCount++ ){
				
				
				if(decrementCounter <= layoutArray[rowCount].length - 1 ){
					sectionList.add(new Section(sectionCounter,layoutArray[rowCount][decrementCounter]));
					sectionCounter++;
				}
				
			}
			Collections.sort(sectionList);
			row.setSections(sectionList);
			rowList.add(row);
			rowSeqNo++;
			decrementCounter--;
		}
		
		Collections.sort(rowList);
		List<Row> rowListDescNoOfSeats = new ArrayList<>();
		rowListDescNoOfSeats.addAll(rowList);
		Collections.sort(rowListDescNoOfSeats, new RowSortOnRowSizeDesc());
		int maxNoOfSeatsinArow = rowListDescNoOfSeats.get(0) == null ? 0 :rowListDescNoOfSeats.get(0).getTotalNoSeatsInRow();
		
		for(String bookingRequestLine : listOfLinesForBooking){
			if(bookingRequestLine.split(" ").length > 1){
				String patronName = bookingRequestLine.split(" ")[0] ;
				String noOfSeatsToBook = bookingRequestLine.split(" ")[1];
				int noOfSeatsToBookInt =  Integer.parseInt(noOfSeatsToBook);
				if(noOfSeatsToBookInt > maxNoOfSeatsinArow){
					System.out.println(patronName+" Sorry, we can't handle your party.");
				}
				
				boolean isBookingSuccessful = false;
				for(Row row : rowList){
					
					if(row.getCurrentRemaingSeatsInaRow() > 0){
						if(row.bookSeats(patronName, noOfSeatsToBookInt)){
							isBookingSuccessful =true;
							break;
							
						} 
					}
				}
				if(!isBookingSuccessful ){
					List<Row> rowListDescNoOfSeatsRemaining = new ArrayList<>();
					rowListDescNoOfSeatsRemaining.addAll(rowList);
					Collections.sort(rowListDescNoOfSeatsRemaining,new RowSortOnNoOfAvailableSeats());
					int maxNoSeatAvailabeInSingRow = rowListDescNoOfSeatsRemaining.get(0).getCurrentRemaingSeatsInaRow();
					if(maxNoSeatAvailabeInSingRow >= noOfSeatsToBookInt){
						System.out.println(patronName+" Call to split party.");
					}
					
				}
				
			}
			
			
			
		}
		
		
		
		

	}

	private static void readInputFile(String fileName, List<String> listOfLinesForRows,
			List<String> listOfLinesForBooking, String regexNumber, String stringRegEx) {
		File file = new File(fileName);
		
		if(!file.isFile() && !file.exists()){
			
			System.out.println("Provide File name ");
			System.exit(0);
		}
		BufferedReader br = null;
		try  {
			br = new BufferedReader(new FileReader(file));
		    String line;
		    while ((line = br.readLine()) != null) {
		    	if(line.split(" ")[0].matches(regexNumber)){
		    		listOfLinesForRows.add(line);
		    	}else if(line.isEmpty()){
		    		//ignore empty line
		    	} else if (line.split(" ")[0].matches(stringRegEx)){
		    		listOfLinesForBooking.add(line);
		    	}
		    }
		} catch (FileNotFoundException e) {
			System.out.println("File not found:"+fileName +" , Exception :"+e.getMessage());
		} catch (IOException e) {
			System.out.println("IO Exception , while reading file :"+fileName+" , Exception:"+e.getMessage());
		} finally{
			try {
				if(br!=null){
					br.close();
				}
			} catch (IOException e) {
				System.out.println("error while closing the file"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	

}
