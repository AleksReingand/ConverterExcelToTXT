package converter;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Converter
{
  @SneakyThrows
  public static void convertExcelToTxt(File excel, File txt, String savePath)
  {
    FileInputStream file = new FileInputStream(excel);
    PrintWriter writer = new PrintWriter(savePath + txt);

    //Create Workbook instance holding reference to .xlsx file
    XSSFWorkbook workbook = new XSSFWorkbook(file);

    //Get first/desired sheet from the workbook
    XSSFSheet sheet = workbook.getSheetAt(0);

    //Iterate through each rows one by one
    Iterator<Row> rowIterator = sheet.iterator();
    List<String> cells = new ArrayList<>();
    while(rowIterator.hasNext())
    {
      Row row = rowIterator.next();
      if(row.getRowNum() == 2 || row.getRowNum() >= 4)
      {
        cells.clear();
        //For each row, iterate through all the columns
        Iterator<Cell> cellIterator = row.cellIterator();

        while(cellIterator.hasNext())
        {
          Cell cell = cellIterator.next();
          CellType type = cell.getCellType();
          //Check the cell type and format accordingly

          switch(type)
          {
            case NUMERIC:
              cells.add((int) cell.getNumericCellValue() + "");
              break;
            case STRING:
              cells.add(cell.getStringCellValue());
              break;
          }
        }
        String line = String.join("|", cells);
        writer.println(line);
        //System.out.println(line);
      }

    }

    file.close();
    writer.close();
  }
}
