package Handle;

import Database.DatabaseConnection;
import Values.Value;
import raven.toast.Notifications;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class RegisterRequest {
    private String fullName;
    private Short gender;
    private LocalDate dateOfBirth;
    private String email;
    private boolean isVerified;
    private String phoneNumber;
    private String password;
    private String authCode;

    public String getFullName() {
        return fullName;
    }

    public Short getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(Short gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public RegisterRequest(String fullName, Short gender, LocalDate dateOfBirth, String email, boolean isVerified, String phoneNumber, String password, String authCode) {
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.isVerified = isVerified;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.authCode = authCode;
    }
    
    public void addUser () {
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(Value.REGISTER)) {
            statement.setString(1, "fName" + ' ' + "lName");
            statement.setShort(2, gender);
//            statement.setDate(3, Date.valueOf(dob));
            statement.setString(4, email);
            statement.setBoolean(5, false);
//            statement.setString(6, phone);
            statement.setString(7, password);
            statement.setString(8, null);
            statement.executeUpdate();
            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Successful registration");
//            FormsManager.getInstance().showForm(new Login());
        } catch (SQLException ex) {Notifications.getInstance().show(Notifications.Type.ERROR, "Data update error in SQL Server");}
    }
    private boolean isExists (String email) {
        try {
            try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(Value.EMAIL_EXIST)) {
                statement.setString(1, email);
                if (statement.executeQuery().next()) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Error Checking");
        }
        return false;
    }
}

