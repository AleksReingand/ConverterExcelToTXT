package converter;

public interface Validator
{
  boolean validatorValue(String value, String type, int size, boolean isBeforeZero);
}
