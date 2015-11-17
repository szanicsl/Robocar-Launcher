package justine.robocar;

import java.io.*;
import java.net.*;

/**
 *
 * @author szanicsl
 */
public class ServerForLauncher {

    public String command;
    public boolean showtraffic = true;
    public ServerSocket servsoc;
    public Socket soc;
    public boolean made = true;

    public void makeServer() throws Exception {
        if(made)
        {
            servsoc = new ServerSocket(11100);
            soc = servsoc.accept();
        }
        InputStreamReader ir = new InputStreamReader(soc.getInputStream());
        BufferedReader br = new BufferedReader(ir);
        System.out.println("connection made");
        String cmd = br.readLine();

        if (cmd != null) {
            PrintStream ps = new PrintStream(soc.getOutputStream());
            ps.println("Connected!");
            if ("donotshowtraffic".equals(cmd)) {
                showtraffic = false;
                System.out.println("show");
            } else if ("showtraffic".equals(cmd)) {
                showtraffic = true;
                System.out.println("not show");
            }
        }
    }
}
