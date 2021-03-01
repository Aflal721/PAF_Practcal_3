/**
 *
 */
package com;

/**
 * @author user
 *
 */

import java.sql.*;


public class Item {



public Connection connect()
{
Connection con = null;

try
{
Class.forName("com.mysql.jdbc.Driver");
con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/labsheet3.2",
"root", "");
//For testing

System.out.print("Successfully connected");
}
catch(Exception e)
{
e.printStackTrace();
}

return con;
}

public String insertItem(String code, String name, String price, String desc)
{
String output = "";
try
{
Connection con = connect();
if (con == null)
{
return "Error while connecting to the database";
}
// create a prepared statement
String query = " insert into items(`itemID`,`itemCode`,`itemName`,`itemPrice`,`itemDesc`)"
+ " values (?, ?, ?, ?, ?)";
PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
preparedStmt.setInt(1, 0);
preparedStmt.setString(2, code);
preparedStmt.setString(3, name);
preparedStmt.setDouble(4, Double.parseDouble(price));
preparedStmt.setString(5, desc);


//execute the statement
preparedStmt.execute();
con.close();
output = "Inserted successfully";
}
catch (Exception e)
{
output = "Error while inserting";
System.err.println(e.getMessage());
}
return output;
}


public String readItems()
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
output = "<table border='1'><tr><th>Item Code</th>"
+"<th>Item Name</th><th>Item Price</th>"
+ "<th>Item Description</th>"
+ "<th>Update</th><th>Remove</th></tr>";
String query = "select * from items";
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
// iterate through the rows in the result set
while (rs.next())
{
String itemID = Integer.toString(rs.getInt("itemID"));
String itemCode = rs.getString("itemCode");
String itemName = rs.getString("itemName");
String itemPrice = Double.toString(rs.getDouble("itemPrice"));
String itemDesc = rs.getString("itemDesc");
// Add a row into the html table
output += "<tr><td>" + itemCode + "</td>";
output += "<td>" + itemName + "</td>";
output += "<td>" + itemPrice + "</td>";

output += "<td>" + itemDesc + "</td>";
// buttons
output += "<td><form method='post' action='items.jsp'>"
+ "<input name='btnUpdate' "
     + " type='button' value='Update'></td>"
+ "<input name='itemID' type='hidden' "
+ " value='" + itemID + "'>" + "</form></td>"
+ "<td><form method='post' action='items.jsp'>"
+ "<input name='btnRemove' "
+ " type='submit' value='Remove'>"
+ "<input name='itemID' type='hidden' "
+ " value='" + itemID + "'>" + "</form></td></tr>";
}
con.close();
// Complete the html table
output += "</table>";
}
catch (Exception e)
{
output = "Error while reading the items.";
System.err.println(e.getMessage());
}
return output;
}


public String removeItem(String ID)
{
String output = "";
try
{
Connection con = connect();
if (con == null)
{
return "Error while connecting to the database";
}

Statement st=con.createStatement();
int i=st.executeUpdate("DELETE FROM items WHERE itemID="+ID);
con.close();
output = "deleted successfully";


}
catch (Exception e)
{
output = "Error while deleting";
System.err.println(e.getMessage());
}
return output;
}

public String updateItem(String code, String name, String price, String desc,String id)
{
String output = "";
try
{
Connection con = connect();
if (con == null)
{
return "Error while connecting to the database";
}
// create a prepared statement
String query = "update items set itemID=? , itemCode=?,  itemName=?, itemPrice=?, itemDesc=? where itemID=id";
PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values

preparedStmt.setString(1, code);
preparedStmt.setString(2, name);
preparedStmt.setDouble(3, Double.parseDouble(price));
preparedStmt.setString(4, desc);


//execute the statement
preparedStmt.execute();
con.close();
output = "Updated successfully";
}
catch (Exception e)
{
output = "Error while updating";
System.err.println(e.getMessage());
}
return output;
}


}
