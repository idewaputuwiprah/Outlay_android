package com.example.outlay;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Calendar;

public class DBHandler {

    private Connection myConnection;

    public DBHandler() {

    }

    public Connection init() {
        try{
            String url,name,pass;
            Class.forName("com.mysql.cj.jdbc.Driver");
            url="jdbc:mysql://localhost/outlay?useLegacyDatetimeCode=false&serverTimezone=UTC";
            name="root";
            pass="";
            myConnection = DriverManager.getConnection(url,name,pass);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return myConnection;
    }

    public void getKategori(){
        try{
            Statement st = myConnection.createStatement();
            String q = "Select nama from kategori;";
            ResultSet rs = st.executeQuery(q);
        }
        catch(Exception e){
            System.out.println("Failed to get kategori");
        }
    }

    public ResultSet getKategoriT(Connection conn, String value){
        ResultSet rs  = null;
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement("select * from kategori where nama = ?");
            ps.setString(1, value);
            rs = ps.executeQuery();
            rs.next();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public String getIdKategori(ResultSet rs){
        String temp = "";
        try{
//            rs.next();
            temp = rs.getString("idKategori");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return temp;
    }

    public String getNamaKategori(ResultSet rs){
        String temp = "";
        try{
//            rs.next();
            temp = rs.getString("nama");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return temp;
    }

    public Integer getIcon(Connection conn, String value){
        Integer id = 0;
        try{
            Statement st = conn.createStatement();
            String q = "select idIcon from icon where nama = '"+value+"'";
            ResultSet rs = st.executeQuery(q);
            String temp = rs.getNString(1);
            id = Integer.parseInt(temp);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return id;
    }

    public void submitPengeluaran(String nama, Integer nominal, String value, String des, Connection conn){
        try{
            Statement st = conn.createStatement();
            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
            ResultSet rs = getKategoriT(conn, value);
            String id = getIdKategori(rs);
            st.executeUpdate("INSERT INTO pengeluaran (nama,tanggal,nominal,deskripsi,idKategori) "
                            + "values ('"+nama+"','"+startDate+"','"+nominal+"','"+des+"','"+id+"')",
                    Statement.RETURN_GENERATED_KEYS);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void submitKategori(String nama, Connection conn){
        try{
            Statement st = conn.createStatement();
//            Integer id = getIcon(conn, value);
            st.executeUpdate("INSERT INTO kategori (nama) "
                            + "values ('"+nama+"')",
                    Statement.RETURN_GENERATED_KEYS);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void updateKategori(String nama, Integer id, Connection conn){
        try{
            Statement st = conn.createStatement();
//            Integer id = getIcon(conn, value);
            st.executeUpdate("update kategori SET nama =  "
                            + "'"+nama+"'" + "where idKategori = " + "'"+id+"'",
                    Statement.RETURN_GENERATED_KEYS);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteKategori(int id, Connection conn){
        try{
            Statement st = conn.createStatement();
            st.executeUpdate("delete from kategori where idKategori = "
                            + "'"+id+"'",
                    Statement.RETURN_GENERATED_KEYS);
    }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void close(ResultSet rs){

        if(rs !=null){
            try{
                rs.close();
            }
            catch(Exception e){}

        }
    }

    public void close(java.sql.Statement stmt){

        if(stmt !=null){
            try{
                stmt.close();
            }
            catch(Exception e){}

        }
    }

    public void destroy(){

        if(myConnection !=null){

            try{
                myConnection.close();
            }
            catch(Exception e){

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ResultSet getTotal(String waktu, Connection conn){

        ResultSet hasil = null;
        try{
            Statement st = conn.createStatement();
            String q = null;
            if(waktu == "hari")
            {
                q = "select * from pengeluaran where tanggal = '"+ LocalDate.now()+"';";
            }
            else if(waktu == "minggu")
            {
                LocalDate tanggal = LocalDate.now();
                q = "select idPengeluaran,tanggal,SUM(nominal) as nominal from pengeluaran where tanggal > '"+tanggal.minusDays(7)+"' AND tanggal <= '"+tanggal+"' GROUP BY tanggal;";
            }
            else if(waktu == "bulan")
            {
                LocalDate tanggal = LocalDate.now();
                q = "select idPengeluaran,tanggal,SUM(nominal) as nominal from pengeluaran where tanggal > '"+tanggal.minusDays(30)+"' AND tanggal <= '"+tanggal+"' GROUP BY tanggal;";
            }
            if(waktu == "seluruh")
            {
                q = "select * from pengeluaran order by tanggal desc;";
            }
            hasil = st.executeQuery(q);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return hasil;
    }


}
