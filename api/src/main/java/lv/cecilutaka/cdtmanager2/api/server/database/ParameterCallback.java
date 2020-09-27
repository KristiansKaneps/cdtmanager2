package lv.cecilutaka.cdtmanager2.api.server.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ParameterCallback
{
	void mapParameter(PreparedStatement statement, int index) throws SQLException;
}
