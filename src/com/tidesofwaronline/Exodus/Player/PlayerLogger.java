package com.tidesofwaronline.Exodus.Player;

import java.sql.Connection;

import com.tidesofwaronline.Exodus.Exodus;
import com.tidesofwaronline.Exodus.Database.mysql.MySQL;


public class PlayerLogger {
	
	Exodus exodus;
	MySQL mysql;
	Connection c;
	
	public PlayerLogger(Exodus exodus) {
		this.exodus = exodus;
		mysql = new MySQL(exodus, "host.name", "port", "database", "user", "pass");
	}
	
	void openConnection() {
	    c = mysql.openConnection();
	}
}
