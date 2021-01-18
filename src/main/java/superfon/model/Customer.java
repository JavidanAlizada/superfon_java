package superfon.model;

import lombok.*;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {
    private int id;
    private String fullName;
    private String phoneNumber;
    private String gender;
    private String birthDate;
    private String maritalStatus;
    private String timeStamp;
}
