package com.textmiming.test.bayes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

public class XLS {
	
	private Workbook workbook;
	private HashMap<String, Sheet> sheets = null;
	private HashMap<Sheet, ArrayList<Row>> rows = null;
	private HashMap<Row, ArrayList<Cell>> cells = null;
	
	public XLS() throws IOException{
		workbook = new HSSFWorkbook();
		sheets = new HashMap<String, Sheet>();
		rows = new HashMap<Sheet, ArrayList<Row>>();
		cells = new HashMap<Row, ArrayList<Cell>>();
	}
	
	public void generateFile(String path, String name) throws IOException{
		int idxSep = path.lastIndexOf(File.separator);
		String p;
		FileOutputStream fileOut = null;
		
		if(idxSep == path.length()){
			p = path.substring(0, idxSep - 1);
		}else{
			p = path;
		}
		
		fileOut = new FileOutputStream(p + System.getProperty("file.separator") + name + ".xls");
	
		
		if(fileOut != null){
			workbook.write(fileOut);
			fileOut.close();
		}
		
	}
	
	public void generateFile(String name) throws IOException{
		FileOutputStream fileOut = new FileOutputStream(name + ".xls");
	
		if(fileOut != null){
			workbook.write(fileOut);
			fileOut.close();
		}
	}
	
	public void newSheet(String name){
		String safeName = WorkbookUtil.createSafeSheetName(name);
		Sheet sheet = workbook.createSheet(safeName);
		sheets.put(safeName, sheet);
	}
	
	private void writeInCell(Cell c, Object value){
		if(value instanceof Double){
			c.setCellValue((double) value);
		}else if(value instanceof Float){
			c.setCellValue((float) value);
		}else if(value instanceof Integer){
			c.setCellValue((int) value);
		}else if(value instanceof String){
			c.setCellValue((String) value);
		}else{
			System.out.println("Something went wrong with instanceof checks of the value type in writeToCell.");
		}
		
		
	}
	
	/**
	 * 
	 * @param sheetName name of the sheet
	 * @param rowIdx row idx starts with 0
	 * @param cellIdx cell idx starts with 0
	 * @param value Wrapper-Object of Float, Double, Integer or a String
	 */
	public void writeInCell(String sheetName, int rowIdx, int cellIdx, Object value){
		String safeName = WorkbookUtil.createSafeSheetName(sheetName);
		
		if(!sheets.containsKey(safeName)){
			System.out.println("Not in cell written because no Sheet with name " + sheetName + " exists.");
			return;
		}
		
		if(!(value instanceof String)
				&& !(value instanceof Double)
				&& !(value instanceof Float)
				&& !(value instanceof Integer)){
			System.out.println("Not in cell written because value not of type Integer, Float, Double or String.");
				return;
			}
		
		//Noch keine Rows zu dem Sheet?
		if(rows.get(sheets.get(safeName)) == null){
			rows.put(sheets.get(safeName), new ArrayList<Row>());
		}
		
		if(rows.get(sheets.get(safeName)).size() - 1 < rowIdx){
			int rowCount = rows.get(sheets.get(safeName)).size() - 1;

			while(rowCount < rowIdx){
				rows.get(sheets.get(safeName)).add(sheets.get(safeName).createRow(++rowCount));
			}
		}
		
		//Noch keine Cells zu der Row?
		if(cells.get(rows.get(sheets.get(safeName)).get(rowIdx)) == null){
			cells.put(rows.get(sheets.get(safeName)).get(rowIdx), new ArrayList<Cell>());
		}
		  
		if(cells.get(rows.get(sheets.get(safeName)).get(rowIdx)).size() - 1 < cellIdx){
			int cellCount = cells.get(rows.get(sheets.get(safeName)).get(rowIdx)).size() - 1;
		
			while(cellCount < cellIdx){
				cells.get(rows.get(sheets.get(safeName)).get(rowIdx)).add(rows.get(sheets.get(safeName)).get(rowIdx).createCell(++cellCount));
			}
		}
		
		writeInCell(cells.get(rows.get(sheets.get(safeName)).get(rowIdx)).get(cellIdx), value);
	
	}

	public HashMap<String, Sheet> getSheets() {
		return sheets;
	}

	public void setSheets(HashMap<String, Sheet> sheets) {
		this.sheets = sheets;
	}

	public HashMap<Sheet, ArrayList<Row>> getRows() {
		return rows;
	}

	public void setRows(HashMap<Sheet, ArrayList<Row>> rows) {
		this.rows = rows;
	}

	public HashMap<Row, ArrayList<Cell>> getCells() {
		return cells;
	}

	public void setCells(HashMap<Row, ArrayList<Cell>> cells) {
		this.cells = cells;
	}
	
	

	

}
