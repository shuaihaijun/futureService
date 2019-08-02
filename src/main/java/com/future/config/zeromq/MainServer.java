package com.future.config.zeromq;

import java.util.LinkedList;
import java.util.Queue;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Poller;
import org.zeromq.ZMQ.Socket;

public class MainServer {
	 private static final int NBR_CLIENTS = 10;
	 private static final int NBR_WORKERS = 3;
	 
	 
	 public static void main (String[] args) {
	        Context context = ZMQ.context(1);
	        //  Prepare our context and sockets
	        Socket frontend  = context.socket(ZMQ.ROUTER);
	        Socket backend  = context.socket(ZMQ.ROUTER);
	        frontend.bind("ipc://frontend.ipc");
	        backend.bind("ipc://backend.ipc");
            
	        int clientNbr;
	        for (clientNbr = 0; clientNbr < NBR_CLIENTS; clientNbr++)
	            new ClientTask("client"+clientNbr).start();

	        for (int workerNbr = 0; workerNbr < NBR_WORKERS; workerNbr++)
	            new WorkerTask("worker"+workerNbr).start();

	        //  Queue of available workers
	        Queue<String> workerQueue = new LinkedList<String>();

	        while (!Thread.currentThread().isInterrupted()) {

	            /*//  Initialize poll set
	            Poller items = new Poller(context,2);

	            //  Always poll for worker activity on backend
	            items.register(backend, Poller.POLLIN);

	            //  Poll front-end only if we have available workers
	            if(workerQueue.size() > 0)
	                items.register(frontend, Poller.POLLIN);

	            if (items.poll() < 0)
	                break;

	            //  Handle worker activity on backend
	            if (items.pollin(0)) {

	                //  Queue worker address for LRU routing
	                workerQueue.add (backend.recvStr ());

	                //  Second frame is empty
	                String empty = backend.recvStr ();
	                assert (empty.length() == 0);

	                //  Third frame is READY or else a client reply address
	                String clientAddr = backend.recvStr ();

	                //  If client reply, send rest back to frontend
	                if (!clientAddr.equals("READY")) {

	                    empty = backend.recvStr ();
	                    assert (empty.length() == 0);

	                    String reply = backend.recvStr ();
	                    frontend.sendMore(clientAddr);
	                    frontend.sendMore("");
	                    frontend.send(reply);

	                    if (--clientNbr == 0)
	                        break;
	                }

	            }

	            if (items.pollin(1)) {
	                //  Now get next client request, route to LRU worker
	                //  Client request is [address][empty][request]
	                String clientAddr = frontend.recvStr ();

	                String empty = frontend.recvStr ();
	                assert (empty.length() == 0);

	                String request = frontend.recvStr ();

	                String workerAddr = workerQueue.poll();

	                backend.sendMore (workerAddr);
	                backend.sendMore ("");
	                backend.sendMore (clientAddr );
	                backend.sendMore ("");
	                backend.send (request);

	            }*/
	        }

	        frontend.close();
	        backend.close();
	        context.term();

	    }
}
