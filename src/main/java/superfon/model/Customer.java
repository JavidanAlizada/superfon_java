package superfon.model;

import lombok.*;

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
    private String serialNumber;
    private String qrCodeContent;
    private String password;
    private Integer status;
}
