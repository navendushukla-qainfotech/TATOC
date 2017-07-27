package com.sockets.db;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBScoket extends Thread {
	private ServerSocket serverSocket;

	public DBScoket(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	public void run() {
		while (true) {
			try {
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();

				System.out.println("Just connected to " + server.getRemoteSocketAddress());
				Class.forName("com.mysql.jdbc.Driver");
				String dbUrl = "jdbc:mysql://10.0.1.86:3306/tatoc";
				String username = "tatocuser";
				String password = "tatoc01";

				String query1 = "select *  from credentials;";
				Connection con = DriverManager.getConnection(dbUrl, username, password);
				Statement stmt = con.createStatement();
				ResultSet r1 = stmt.executeQuery(query1);
				String data = "";
				while (r1.next()) {
					String Name = r1.getString(2);
					String pass = r1.getString(3);
					data += Name + "-" + pass + "\n";
				}

				System.out.println("data " + data);
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.writeUTF(data);
				out.flush();
				server.close();
				r1.close();
				con.close();

			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		try {
			Thread t = new DBScoket(2000);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
