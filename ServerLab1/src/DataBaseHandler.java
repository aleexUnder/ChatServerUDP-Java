

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseHandler extends Configs {//здесь подключаемся к базе данных и делаем всякие штуки с ней
    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassNotFoundException,SQLException{
        String connectionString="jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName+"?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";;

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection= DriverManager.getConnection(connectionString, dbUser,dbPass);
        return dbConnection;
    }

    public  void signUpUser(String nickname,String firstName,          //регистрация пользователя
                            String lastName,String password){
        String insert="INSERT INTO "+Const.USER_TABLE+"("+Const.USER_NAME +","+Const.USER_SECOND_NAME+","+Const.USER_NICKNAME+","+Const.USER_PASSWORD+")"+"VALUES(?,?,?,?)";
        PreparedStatement prSt= null;
        try {
            prSt = getDbConnection().prepareStatement(insert);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            prSt.setString(1,firstName);
            prSt.setString(2,lastName);
            prSt.setString(3,nickname);
            prSt.setString(4,password);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
