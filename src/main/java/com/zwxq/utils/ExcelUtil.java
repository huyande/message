package com.zwxq.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;



public class ExcelUtil {
	
	private final static String XLS = "xls";  
    private final static String XLSX = "xlsx";  
	  

	/**
	 * 读取excel数据
	 */
	public static List<Map<String, Object>> exportListFromExcel(File file, int sheetNum) throws IOException {  
        return exportListFromExcel(new FileInputStream(file), FilenameUtils.getExtension(file.getName()), sheetNum);  
    }  
  
    public static List<Map<String, Object>> exportListFromExcel(InputStream is, String extensionName, int sheetNum) throws IOException {  
  
        Workbook workbook = null;  
  
        if (extensionName.toLowerCase().equals(XLS)) {  
            workbook = new HSSFWorkbook(is);  
        } else if (extensionName.toLowerCase().equals(XLSX)) {  
            workbook = new XSSFWorkbook(is);  
        }  
  
        return readCell(workbook, sheetNum);  
    }  
  
    public static List<Map<String, Object>> readCell(Workbook workbook, int sheetNum) {
        Sheet sheet = workbook.getSheetAt(sheetNum);  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i=1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);  
            Map<String, Object> map = new HashMap<String, Object>();  
            for (Cell cell : row) {  
  
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());  
                String key = cellRef.formatAsString();  
  
                switch (cell.getCellType()) {  
                case Cell.CELL_TYPE_STRING:  
                    map.put(key, cell.getRichStringCellValue().getString());  
                    break;  
                case Cell.CELL_TYPE_NUMERIC:  
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {  
                        map.put(key, cell.getDateCellValue());  
                    } else {
                    	DecimalFormat dfs = new DecimalFormat("0");
                        map.put(key, dfs.format(cell.getNumericCellValue()));  
                    }  
                    break;  
                case Cell.CELL_TYPE_BOOLEAN:  
                    map.put(key, cell.getBooleanCellValue());  
                    break;  
                case Cell.CELL_TYPE_FORMULA:  
                    map.put(key, cell.getCellFormula());  
                    break;  
                case Cell.CELL_TYPE_BLANK:  
                    break;  
                case Cell.CELL_TYPE_ERROR:  
                    break;  
                default:  
                    map.put(key, "");  
                }  
            }  
            list.add(map);  
        }  
        return list;  
    }
}
