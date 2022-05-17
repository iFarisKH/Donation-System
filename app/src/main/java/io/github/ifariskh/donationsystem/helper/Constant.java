package io.github.ifariskh.donationsystem.helper;

public interface Constant {

    String BASE_URL = "https://ds-dbms.000webhostapp.com";
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
    String GET_BALANCE = "https://ds-dbms.000webhostapp.com/api/GetBalance.php";
    String GET_TRANSACTION = "https://ds-dbms.000webhostapp.com/api/GetTransaction.php";
    String PAY = "https://ds-dbms.000webhostapp.com/api/Pay.php";
    String UPLOAD = "https://ds-dbms.000webhostapp.com/api/Upload.php";
    String GET_KYC = "https://ds-dbms.000webhostapp.com/api/GetKYC.php";
    String UPDATE_STATUS = "https://ds-dbms.000webhostapp.com/api/UpdateStatus.php";
    String ADD_BALANCE = "https://ds-dbms.000webhostapp.com/api/AddBalance.php";
    String GET_COUNT = "https://ds-dbms.000webhostapp.com/api/GetCount.php";
    String VISA = "^4[0-9]{2,12}(?:[0-9]{3})?$";
    String MASTERCARD = "^5[1-5][0-9]{1,14}$";

}
