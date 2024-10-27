package com.automation.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtility {
	public void readSingleData(String filePath, String sheetname, int rownum, int colnum) throws IOException {
		String Dirpath = System.getProperty("user.dir");
		FileInputStream fi = new FileInputStream(new File(Dirpath + filePath));
		XSSFWorkbook wb = new XSSFWorkbook(fi);
		XSSFSheet sheet = wb.getSheet(sheetname);
		XSSFRow row = sheet.getRow(rownum);

		XSSFCell cell = row.getCell(colnum);

		if (cell.getCellType() == CellType.STRING)
			System.out.println(cell.getStringCellValue());
		else if (cell.getCellType() == CellType.NUMERIC)
			System.out.println(cell.getNumericCellValue());

		wb.close();
		fi.close();

		// ""
	}

	public static Object[][] readAllDataFromXLToArray(String filePath, String sheetname) {
		Object[][] data = null;
		XSSFWorkbook workbook = null;
		FileInputStream file = null;

		try {
			file = new FileInputStream(new File(filePath));
			workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(sheetname);
			int rows = sheet.getPhysicalNumberOfRows();
			int cols = sheet.getRow(0).getLastCellNum();
			data = new Object[rows][cols];
			for (int i = 0; i < rows; i++) {
				Row row = sheet.getRow(i);
				for (int j = 0; j < cols; j++) {
					Cell cell = row.getCell(j);
					data[i][j] = cell.toString();
				}
			}
		} 
		catch (Exception e) {
			System.out.println("exception caught while reading data from xlsx sheet");
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		return data;
	}

}
