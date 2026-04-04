package dataprovider;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.ExcelUtil;

public class TestDataProvider {
	@DataProvider
	public Object[][] getLoginData() throws IOException{
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/testData/TestData.xlsx";
		int row = ExcelUtil.getRows(excelPath, "loginData");
		int cols = ExcelUtil.getCols(excelPath, "loginData");
		
		Object[][] data = new Object[row][cols];
		for(int i=1; i<=row; i++) {
			data[i-1][0] = ExcelUtil.getCellData(excelPath, "loginData", i, 0);
			data[i-1][1] = ExcelUtil.getCellData(excelPath, "loginData", i, 1);
			data[i-1][2] = ExcelUtil.getCellData(excelPath, "loginData", i, 2);
			data[i-1][3] = i;
		}
		return data;
	}
	
//	@DataProvider
//	public Object[][] getCheckoutInfoData() throws IOException{
//		String excelPath = System.getProperty("user.dir") + "/src/test/resources/testData/TestData.xlsx";
//		int row = ExcelUtil.getRows(excelPath, "checkoutInfoData");
//		int cols = ExcelUtil.getCols(excelPath, "checkoutInfoData");
//		
//		Object[][] data = new Object[row][cols];
//		for(int i=1; i<=row; i++) {
//			data[i-1][0] = ExcelUtil.getCellData(excelPath, "testdata", i, 0);
//			data[i-1][1] = ExcelUtil.getCellData(excelPath, "testdata", i, 1);
//			data[i-1][2] = ExcelUtil.getCellData(excelPath, "testdata", i, 2);
//			data[i-1][3] = i;
//		}
//		return data;
//	}
}
