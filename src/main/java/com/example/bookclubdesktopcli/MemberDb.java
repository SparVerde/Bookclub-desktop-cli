package com.example.bookclubdesktopcli;

import java.sql.*;
import java.util.ArrayList;

public class MemberDb {
    //1. Connection osztály példányosítása
    Connection conn;
    //2. SQLException and establish connector: getConnection
    public MemberDb() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vizsga-konyvklub", "root", "");
    }
    //3. ListaFeltolt metódus, SQLExceptionnal
    public ArrayList<Member> ListaFeltolt() throws SQLException {
        //új Cegadatlista létrehozása
        ArrayList<Member> lista = new ArrayList<>();
        //statement változó definiálás
        Statement stmt = conn.createStatement();
        //sql változó definiálás
        String sql = "SELECT * FROM members;";
        //result definiálás
        ResultSet result = stmt.executeQuery(sql);
        // lista feltöltése amíg van sql-ben adat
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            String gender = result.getString("gender");
            Date birth_date = result.getDate("birth_date");
            Boolean banned = result.getBoolean("banned");

            Member elem = new Member(id, name, gender, birth_date, banned);
            lista.add(elem);
        }
        return lista;
    }
    public int bannedModosit(String Name) throws SQLException {
        String sql = "UPDATE members SET banned = ABS(banned-1) WHERE Name = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        return stmt.executeUpdate();
    }
}
