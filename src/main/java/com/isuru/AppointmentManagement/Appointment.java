package com.isuru.AppointmentManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Appointment {
	private Connection connect()
	 {
	 Connection con = null;
	 try
	 {
		 Class.forName("com.mysql.jdbc.Driver");
		   con = DriverManager.getConnection("jdbc:mysql://localhost/appointments","root","");
	 }
	 catch (Exception e)
	 {e.printStackTrace();}
	 return con;
	 } 
	
	public String insertApp(String pid,String pname, String address, String dname,String hname,String bdate,String cno)
	{
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {
		 return "Error while connecting to the database for inserting.";
		 }
		 // create a prepared statement
		 String query = " insert into appointment(`appointmentid`,`patientid`,`patientname`,`address`,`doctorname`,`hospitalname`,`bookdate`,`contactno`)"
		 + " values (?, ?, ?, ?, ?, ?, ?, ?)";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setInt(1, 0);
		 preparedStmt.setString(2, pid);
		 preparedStmt.setString(3, pname);
		 preparedStmt.setString(4, address);
		 preparedStmt.setString(5, dname);
		 preparedStmt.setString(6, hname);
		 preparedStmt.setString(7, bdate);
		 preparedStmt.setString(8, cno);

		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 String newapp = readApp();
		 output = "{\"status\":\"success\", \"data\": \"" +
		 newapp + "\"}";
		 }
		 catch (Exception e)
		 {
		 output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 } 
	
	
	public String readApp()
	{
		 String output = "";
		try
		 {
		 Connection con = connect();
		 if (con == null)
		 {
		 return "Error while connecting to the database for reading.";
		 }
		 // Prepare the html table to be displayed
		 output = "<table border='1'><tr><th>Patient ID</th>"
		 		+ "<th>Patient Name</th><th>Address</th>"
				 + "<th>Doctor Name</th><th>Hospital Name</th><th>Book Date</th><th>Contact NO</th>"
				 + "<th>Update</th><th>Remove</th></tr>";
		 String query = "select * from appointment";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 // iterate through the rows in the result set
		 while (rs.next())
		 {
		 String appointmentID = Integer.toString(rs.getInt("appointmentid"));
		 String patientID = rs.getString("patientid");
		 String patientName = rs.getString("patientname");
		 String patientAddr = rs.getString("address");
		 String doctorName = rs.getString("doctorname");
		 String hospitalName = rs.getString("hospitalname");
		 String bookDate = rs.getString("bookdate");
		 String contactNo = rs.getString("contactno");
	 
		 
		// Add into the html table
		 output += "<tr><td><input id='hidItemIDUpdate'"
		 		+ "name='hidItemIDUpdate' type='hidden'"
		 		+ "value='" + appointmentID + "'>" + patientID + "</td>";
		 output += "<td>" + patientName + "</td>";
		 output += "<td>" + patientAddr + "</td>";
		 output += "<td>" + doctorName + "</td>";
		 output += "<td>" + hospitalName + "</td>";
		 output += "<td>" + bookDate + "</td>";
		 output += "<td>" + contactNo + "</td>";



		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update'  class='btnUpdate btn btn-secondary'></td>"
		 		+ "<td><input name='btnRemove' type='button'  value='Remove'  class='btnRemove btn btn-danger' data-itemid='"
				 + appointmentID + "'>" + "</td></tr>";
		 }
		 con.close();
		 // Complete the html table
		 output += "</table>";
		 }
		catch (Exception e)
		 {
		 output = "Error while reading the Appointment.";
		 System.err.println(e.getMessage());
		 }
		return output;
		}

	
	
	public String updateApp(String appid,String pid,String pname, String address, String dname,String hname,String bdate,String cno)
	{
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {
		 return "Error while connecting to the database for updating.";
		 }
		 // create a prepared statement
		 String query = "UPDATE appointment SET patientid=?,patientname=?,address=?,doctorname=?,hospitalname=?,bookdate=?,contactno=? WHERE appointmentid=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setString(1, pid);
		 preparedStmt.setString(2, pname);
		 preparedStmt.setString(3, address);
		 preparedStmt.setString(4, dname);
		 preparedStmt.setString(5, hname);
		 preparedStmt.setString(6, bdate);
		 preparedStmt.setString(7, cno);
		 preparedStmt.setInt(8, Integer.parseInt(appid));
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 String newapp = readApp();
		 output = "{\"status\":\"success\", \"data\": \"" +
		 newapp + "\"}";
		 }
		 catch (Exception e)
		 {
		 output = "{\"status\":\"error\", \"data\":\"Error while updating the Appointment.\"}";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 } 
	
	public String deleteApp(String appID)
	{
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {
		 return "Error while connecting to the database for deleting.";
		 } 
		// create a prepared statement
		 String query = "delete from appointment where appointmentid=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(appID));
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 String newapp = readApp();
		 output = "{\"status\":\"success\", \"data\": \"" +
		 newapp + "\"}";
		 }
		 catch (Exception e)
		 {
		 output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 }
		
}
