package Utils;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSXUtils {
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;

	public static XSSFWorkbook createWorkbook() {
		workbook = new XSSFWorkbook();
		return workbook;
	}

	public static XSSFSheet createSheet(String sheetname) {
		sheet = workbook.createSheet(sheetname);
		return sheet;
	}

	public static Row createRow(int rownum) {
		Row row = sheet.getRow(rownum);

		if (row == null) {
			row = sheet.createRow(rownum);
		}

		return row;
	}

	public static Cell createCellOfRow(int rownum, int colnum) {
		Row row = createRow(rownum);
		Cell cell = row.createCell(colnum);
		return cell;
	}

	public static void insertIntoCell(int rownum, int cellnum, String value) {

		Cell cell = createCellOfRow(rownum, cellnum);

		cell.setCellValue(value);
	}

	public static void saveWorkbookToFile(String filename) throws Exception {
		FileOutputStream fos = new FileOutputStream(new File(".//ExcelFiles//" + filename + ".xlsx"));

		workbook.write(fos);

		fos.close();
		workbook.close();
	}

	public static void insertArrayToRow(int rownum, String[] data) {

		int cellnum = 0;
		for (String d : data) {
			
			insertIntoCell(rownum, cellnum, d);
			++cellnum;
		}

	}

}
