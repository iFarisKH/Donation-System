package io.github.ifariskh.donationsystem.helper;

public interface Constant {

    String REGISTER = "https://ds-dbms.000webhostapp.com/api/Register.php";
    String LOGIN = "https://ds-dbms.000webhostapp.com/api/Login.php";
    String LOGOUT = "https://ds-dbms.000webhostapp.com/api/Logout.php";
    String OTP = "https://ds-dbms.000webhostapp.com/api/OTP.php";
    String FIND_EMAIL = "https://ds-dbms.000webhostapp.com/api/FindEmail.php";
    String UPDATE_PASSWORD = "https://ds-dbms.000webhostapp.com/api/UpdatePassword.php";
    String ADD_CREDIT_CARD = "https://ds-dbms.000webhostapp.com/api/AddCreditCard.php";
    String GET_CREDIT_CARD = "https://ds-dbms.000webhostapp.com/api/GetCreditCard.php";
    String GET_NEEDERS = "https://ds-dbms.000webhostapp.com/api/GetNeeder.php";
    String KYC = "https://ds-dbms.000webhostapp.com/api/ValidateKYC.php";
    String VISA = "^4[0-9]{2,12}(?:[0-9]{3})?$";
    String MASTERCARD = "^5[1-5][0-9]{1,14}$";

}
