package com.future.config.zeromq;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class ClientTask extends Thread
{
	private String identity; 
	public ClientTask(String tag)
	{
		this.identity=tag;
	}
        public void run()
        {
            Context context = ZMQ.context(1);

            //  Prepare our context and sockets
            Socket client  = context.socket(ZMQ.REQ);
            client.setIdentity(identity.getBytes());

            client.connect("ipc://frontend.ipc");

            //  Send request, get reply
            client.send("HELLO");
            String reply = client.recvStr ();
            System.out.println(this.identity+" receive is: " + reply);

            client.close();
            context.term();
        }
}

