import configs.Configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    /*----------------ПЕРЕД ЗАПУСКОМ ПРОГРАММЫ НЕОБХОДИМО ЗАПУСТИТЬ Open Service Panel локальном компе,
                                а затем (если не срабоает) в Idea подключиться к БД
                                а также подключить в ProjectStructure
                                (com.google.code.maven-play-plugin.postgresql:postgresql:9.0-801.jdbc4-patched-play-1.2.3)*/
    public static void main(String[] args) {
        System.out.println("Тестирование подключения к PostgreSQL JDBC");

        /*Подключение класса для драйвера PostgreSQL,
        Однако, начиная с JDBC 4.0 (JDK 6.0 и более поздних версий), подключение не требуется.
        Диспетчер драйверов JDBC может обнаружить и загрузить соответствующий драйвер при анализе URL-адреса базы данных.

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }*/


        try (Connection connection = DriverManager.getConnection(Configs.dbURL, Configs.dbUser, Configs.dbPass);) {
            //Connection <- в классе DriverManager вызывается статический метод getConnection возвращающий тип Connection
            //на этом этапе и происходит соединение с БД
            //в качестве параметров в метод передаются в виде строки url адрес БД,
            //логин для входа в БД, пароль для входа в БД тоже в виде строк
            String nameTable = "clients";
            String nameClient = "Sidorov Vova";
            String attribute = "name";

            //insertQuery <- строка запрос для SQL написана ниже в двух вариантах
            //вместо %s после строки в ковычках указываются ссылки строк (nameTable, attribute)
            //после VALUES в скобках ставится знак вопрос, вопросов столько, сколько столбцов необходимо заполнить, в данном случае
            //передается только одно значение т.к. будет заполняться только столбец name таблицы clients
            //после создается объект класса PreparedStatement при помощи connection.prepareStatement();
            //метод prepareStatement() в качестве аргумента принимает строку, которая и является SQL-запросом
            //далее после того как мы передали в метод строку-запрос, нужно на ссылке объекта PreparedStatement
            //вызвать соответствующие методы для передачи значений в поле VALUES(?), в которые поместить само значение
            //в данном примере вызывается метод setString() принимающий число  и строку,
            // число- это номер параметра (номер вопросика в VALUES (счет идет с 1))
            //строка- соответственно то, что мы хотим передать, "Sidorov Vova".
            //в завершение вызывается метод executeUpdate() для обновления внесенных данных
            String insertQuery = String.format("INSERT INTO %s (%s) VALUES (?)", nameTable, attribute);
            String insertQuery2 = "DELETE FROM clients WHERE id = 13;";
            PreparedStatement ps = connection.prepareStatement(insertQuery);
            ps.setString(1, nameClient);
            ps.executeUpdate();


            System.out.println("Драйвер PostgreSQL JDBC успешно подключен и SQL-запрос передан");
        } catch (SQLException e) {
            System.out.println("Сбой подключения");
            e.printStackTrace();
            return;
        }

    }
}
