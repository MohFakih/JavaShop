package server;

import java.sql.SQLException;
import java.sql.Statement;
import common.Request;
import common.Response;

public interface RequestHandler {
	public Response resolve() throws Exception;
}
