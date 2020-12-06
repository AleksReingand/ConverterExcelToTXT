package converter;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ValidateValue
{
  SERVICE_PRODUCT_ID("A3", "int", 5, true),
  AGREEMENT_SERIAL_ID("B3", "String", 36, false),
  EXTERNAL_SYSTEM_REFERENCE_HEADER("C3", "String", 32, false),
  NUM_OF_ROWS("D3", "int", 5, false),
  EXTERNAL_SYSTEM_REFERENCE("A", "String", 32, false),
  BANK_NUMBER("B", "int", 3, false),
  BRANCH_NUMBER("C", "int", 3, false),
  ACCOUNT_NUMBER("D", "int", 7, false),
  PARTY_ID("E", "String", 16, false),
  PHONE_PREFIX("F", "String", 3, true),
  PHONE_NUMBER("G", "int", 7, true),
  RECORD_CONTENT("H", "String", 34, false),
  REQUEST_AMOUNT("I", "long", 3, false),
  CURRENCY_CODE("J", "int", 3, true);

  private String idCell;
  private String type;
  private int size;
  private boolean isBeforeZero;

  public ValidateValue getCellById(String id)
  {
    return Arrays.stream(ValidateValue.values()).filter(field -> field.getIdCell().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Wrong id cell"));
  }
}
