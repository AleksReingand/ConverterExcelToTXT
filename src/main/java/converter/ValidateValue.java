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
  NUM_OF_ROWS("D3", "int", 5, false);
  //    EXTERNAL_SYSTEM_REFERENCE("externalSystemReference", "String", 32),
  //    BANK_NUMBER("bankNumber", "int", 3),
  //    BRANCH_NUMBER("branchNumber", "int", 3),
  //    ACCOUNT_NUMBER("accountNumber", "int", 7),
  //    PARTY_ID("partyId", "String", 16),
  //    PHONE_PREFIX("phonePrefix", "String", 3),
  //    PHONE_NUMBER("phoneNumber", "int", 7),
  //    RECORD_CONTENT("recordContent", "String", 34),
  //    REQUEST_AMOUNT("requestAmount", "long", 3),
  //    CURRENCY_CODE("currencyCode", "int", 3);

  private String idCell;
  private String type;
  private int size;
  private boolean isBeforeZero;

  public ValidateValue getCellById(String id)
  {
    return Arrays.stream(ValidateValue.values()).filter(field -> field.getIdCell().equals(id)).findFirst().orElseThrow(() -> new RuntimeException("Wrong id cell"));
  }
}
