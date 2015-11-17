/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.buba.robocarlauncher;

import java.io.*;
import java.net.*;

/**
 *
 * @author szanicsl
 */
public class Network extends MainApp {

    private final String hostname;
    private final int port;
    public String command;
    public String s;
    public boolean connected = true;
    public Socket soc;

    Network(String hostname, int port) {
        super();
        this.hostname = hostname;
        this.port = port;
    }

    public void connectToServer(String cmd) throws Exception {
        if (connected) {
            soc = new Socket(hostname, port);
        }
        PrintStream PS = new PrintStream(soc.getOutputStream());
        command = cmd;
        PS.println(command);
    }
}
