import co.flock.www.FlockApiClient;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;

import static spark.Spark.*;

public class PomodoroServer
{
    private static final Logger _logger = Logger.getLogger(PomodoroServer.class);

    public static void main(String[] args) throws IOException
    {
        _logger.debug("Starting..");

        get("/", (req, res) -> {
            res.status(200);
            return "Hello World";
        });

        post("/", (req, res) -> {
            _logger.debug("Req received : " + req);
            JSONObject jsonObject = new JSONObject(req.body());
            String type = (String) jsonObject.get("name");
            if ("app.install".equals(type))
            {
                handleAppInstall(jsonObject);
            }
            else
            {
                _logger.debug("Got event: " + type);
                if (type.equals("client.slashCommand")){
                    handleSlashCommand(jsonObject);
                }
            }
            res.status(200);
            return "";
        });
    }

    private static void handleAppInstall(JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        String userToken = jsonObject.getString("userToken");
        _logger.debug("Install event received " + userId);
        System.out.println("Userid : " + userId);
        System.out.println("userToken: " + userToken);
        FlockApiClient flockApiClient = new FlockApiClient(userToken,false);
    }

    private static void handleSlashCommand(JSONObject jsonObject) {
        String text = jsonObject.getString("text");
        if (text.startsWith("distraction")) {

        } else if (text.startsWith("startWork")) {

        } else if (text.startsWith("end")) {

        }
    }
}