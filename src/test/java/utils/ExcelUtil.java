package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// ExcelFile -> Workbook -> Sheets -> Rows
public class ExcelUtil {
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static FileInputStream fi;
	public static FileOutputStream fo;
	
	public static int getRows(String path, String sheetName) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		int row = sheet.getLastRowNum();
		return row;
	}
	
	public static int getCols(String path, String sheetName) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		int totalCols = sheet.getRow(1).getLastCellNum();
		return totalCols;
	}
	
	public static int getCell(String path, String sheetName) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		int row = sheet.getLastRowNum();
		int cell = sheet.getRow(1).getLastCellNum();
		return cell;
	}
	
	public static String getCellData(String path, String sheetName, int row, int col) throws IOException{

		try {
			fi = new FileInputStream(path);
			workbook = new XSSFWorkbook(fi);
			sheet = workbook.getSheet(sheetName);
			
			XSSFRow r = sheet.getRow(row);
			XSSFCell c = r.getCell(col);
			
			if(c==null) {
				return "";
			}
			return c.toString();
		}catch(Exception e) {
			throw new RuntimeException("Error reading Excel file: " + e.getMessage());
		}
	}
	
	public static void updateResult(String path, String sheetName, int r, int c, String result) {

	    try {

	        FileInputStream fi = new FileInputStream(path);
	        XSSFWorkbook workbook = new XSSFWorkbook(fi);
	        XSSFSheet sheet = workbook.getSheet(sheetName);

	        // Get or create row
	        XSSFRow row = sheet.getRow(r);
	        if (row == null) {
	            row = sheet.createRow(r);
	        }

	        // Get or create cell
	        XSSFCell cell = row.getCell(c);
	        if (cell == null) {
	            cell = row.createCell(c);
	        }

	        // Create style
	        XSSFCellStyle style = workbook.createCellStyle();

	        if (result.equalsIgnoreCase("Pass")) {

	            style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
	            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	            cell.setCellValue("Pass");

	        } else {

	            style.setFillForegroundColor(IndexedColors.RED.getIndex());
	            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	            cell.setCellValue("Fail");
	        }

	        cell.setCellStyle(style);

	        fi.close();

	        fo = new FileOutputStream(path);
	        workbook.write(fo);

	        fo.close();
	        workbook.close();

	        System.out.println("Excel updated → Row: " + r + " Result: " + result);

	    } catch (Exception e) {

	        e.printStackTrace();
	    }
	}
}
