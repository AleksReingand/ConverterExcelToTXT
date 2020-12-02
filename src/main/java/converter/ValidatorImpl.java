package converter;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ValidatorImpl implements Validator
{
  public static final String FILE_EXTENSION = ".xlsx";
  public List<String> errors = new ArrayList<>();

  public boolean validateFile(File file)
  {
    errors.clear();
    if(!validateExtension(file.getName()))
    {
      errors.add("Wrong file. Please choose Excel file with extension " + FILE_EXTENSION);
      return false;
    }

    return validateCells(file);
  }

  @SneakyThrows
  private boolean validateCells(File file)
  {
    //Create Workbook instance holding reference to .xlsx file
    XSSFWorkbook workbook = new XSSFWorkbook(file);

    //Get first/desired sheet from the workbook
    XSSFSheet sheet = workbook.getSheetAt(0);

    Row row = sheet.getRow(2);
    Iterator<Cell> cellIterator = row.cellIterator();

    while(cellIterator.hasNext())
    {
      Cell cell = cellIterator.next();
      CellAddress idCell = cell.getAddress();
      CellType type = cell.getCellType();

      String valueAsString = null;
      switch(type)
      {
        case NUMERIC:
          valueAsString = (int)cell.getNumericCellValue() + "";
          break;
        case STRING:
          valueAsString = cell.getStringCellValue();
          break;
      }

        ValidateValue validateValue = Arrays.stream(ValidateValue.values()).filter(v -> v.getIdCell().equals(idCell.formatAsString())).findFirst().orElse(null);

        if(validateValue != null && !validatorValue(valueAsString, validateValue.getType(), validateValue.getSize(), validateValue.isBeforeZero()))
        {
          errors.add("Wrong value for cell " + idCell + ": change it to " + validateValue.getType() + " size " + validateValue.getSize());
        }
    }


    //        //Iterate through each rows one by one
    //        Iterator<Row> rowIterator = sheet.iterator();
    //
    //
    //
    //        while (rowIterator.hasNext())
    //        {
    //            Row row = rowIterator.next();
    //            if(row.getRowNum() == 1)
    //            {
    //                Iterator<Cell> cellIterator = row.cellIterator();
    //
    //
    //            }
    //
    //
    //            if(row.getRowNum() == 2 || row.getRowNum() >= 4)
    //            {
    //                //For each row, iterate through all the columns
    //                Iterator<Cell> cellIterator = row.cellIterator();
    //
    //                while (cellIterator.hasNext())
    //                {
    //                    Cell cell = cellIterator.next();
    //                    CellType type = cell.getCellType();
    //                    //Check the cell type and format accordingly
    //
    //                    switch (type)
    //                    {
    //                        case NUMERIC:
    //                            cells.add((int)cell.getNumericCellValue() + "");
    //                            break;
    //                        case STRING:
    //                            cells.add(cell.getStringCellValue());
    //                            break;
    //                    }
    //                }
    //                String line = String.join("|", cells);
    //                writer.println(line);
    //                //System.out.println(line);
    //            }
    return errors.size() == 0;
  }

  private boolean validateExtension(String name)
  {
    String extension = "";

    if(name.contains("."))
      extension = name.substring(name.lastIndexOf("."));

    return extension.equals(FILE_EXTENSION);
  }

  public List<String> getErrors()
  {
    return errors;
  }

  @Override
  public boolean validatorValue(String value, String type, int size, boolean isBeforeZero)
  {
    if(type.equals("String"))
    {
      return !value.isEmpty() && value.length() <= size;
    }

    if(type.equals("int"))
    {
      if(!isBeforeZero)
      {
        try
        {
          Integer.parseInt(value);
        }
        catch(Exception exception)
        {
          return false;
        }
      }
      return value.length() <= size;
    }
    return true;
  }
}
