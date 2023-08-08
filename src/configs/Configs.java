package configs;

public class Configs {
    public static String dbName = "postgres";
    public static String dbHost = "localhost";
    public static String dbPort = "5432";
    public static String dbUser = "postgres";
    public static String dbPass = "postgres";

    //прописываю в виде строки URL моего сервера
    public static String dbURL = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
}
