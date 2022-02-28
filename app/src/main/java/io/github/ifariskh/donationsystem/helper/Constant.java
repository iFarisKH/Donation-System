package io.github.ifariskh.donationsystem.helper;

public interface Constant {

    String REGISTER = "https://ds-dbms.000webhostapp.com/api/Register.php";
    String LOGIN = "https://ds-dbms.000webhostapp.com/api/Login.php";
    String OTP = "https://ds-dbms.000webhostapp.com/api/OTP.php";
    String FIND_EMAIL = "https://ds-dbms.000webhostapp.com/api/FindEmail.php";
    String UPDATE_PASSWORD = "https://ds-dbms.000webhostapp.com/api/UpdatePassword.php";
    String VISA = "^4[0-9]{2,12}(?:[0-9]{3})?$";
    String MASTERCARD = "^5[1-5][0-9]{1,14}$";

}
