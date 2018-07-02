package models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(fluent = true)
public class ConvertCurrency {

    Double count;
    String currency;
    String currencyName;
}
