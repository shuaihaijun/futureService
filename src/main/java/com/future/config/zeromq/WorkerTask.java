package com.future.config.zeromq;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class WorkerTask extends Thread
{
	
	private String identity; 
	public WorkerTask(String tag)
	{
		this.identity=tag;
	}
	 public void run()
     {
         Context context = ZMQ.context(1);
         //  Prepare our context and sockets
         Socket worker  = context.socket(ZMQ.REQ);
       worker.setIdentity(identity.getBytes());

         worker.connect("ipc://backend.ipc");
         
         //  Tell backend we're ready for work
         worker.send("READY");

         while(!Thread.currentThread ().isInterrupted ())
         {
             String address = worker.recvStr ();
             String empty = worker.recvStr ();
             assert (empty.length() == 0);

             //  Get request, send reply
             String request = worker.recvStr ();
             System.out.println(this.identity+" receive is: " + request);

             worker.sendMore (address);
             worker.sendMore ("");
             worker.send("World");
         }
         worker.close ();
         context.term ();
     }
}

