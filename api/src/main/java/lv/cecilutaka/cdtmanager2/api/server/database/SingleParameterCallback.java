package lv.cecilutaka.cdtmanager2.api.server.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SingleParameterCallback
{
	void mapParameter(PreparedStatement statement) throws SQLException;
}
