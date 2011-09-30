package job.objorient;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/*
 * 7.2 Imagine you have a call center with three levels of employees: fresher,
 *     technical lead (TL), product manager (PM). There can be multiple employees,
 *     but only one TL or PM. An incoming telephone call must be allocated to a
 *     fresher who is free. If a fresher can’t handle the call, he or she must
 *     escalate the call to technical lead. If the TL is not free or not able
 *     to handle it, then the call should be escalated to PM. Design the classes
 *     and data structures for this problem. Implement a method getCallHandler().
 */
public final class CallCenter {

    public static interface Call {
        public long getInitTime();
        // put any relevant methods here.
    }

    public static interface CallHandler {
        public boolean handle(Call call);
        public boolean isOpen();
        public String getUID();
    }

    public enum EmployeeType {
        FRESHER, TL, PM
    }

    public enum CallStatus {
        FINISHED, ESCALATE,
    }

    public static final class CallCenterSystem {

        private CallHandler productManager;
        private CallHandler technicalLead;

        private Thread runThread;

        private final ArrayBlockingQueue<Call> callQueue = new ArrayBlockingQueue<Call>(
                25);

        // This isn't used for anything at the moment. Could be used for tracking, such as
        // average call time to estimate wait time.
        // private final ConcurrentHashMap<String, CallHandler> allAgents = new ConcurrentHashMap<String, CallHandler>();

        private final ConcurrentLinkedQueue<CallHandler> openAgents = new ConcurrentLinkedQueue<CallHandler>();

        public void start() {
            // Brute force assumption of only one start.
            if (runThread != null)
                return;

            runThread = new Thread(new Runnable() {
                public void run() {
                    while (!Thread.currentThread().isInterrupted()) {
                        Call incoming;
                        try {
                            incoming = callQueue.take();
                        } catch (InterruptedException e) {
                            // Just return for now, for testing purposes.
                            return;
                        }
                        // This needs to be transactional to be safe
                        // Also should log which agents take which calls
                        CallHandler agent = getCallHandler(100);
                        if (agent == null)
                            escalate(incoming);
                        else
                            // This would have to be handled in a non-blocking
                            // way
                            agent.handle(incoming);
                    }
                }
            });
            runThread.start();
        }

        public void stop() {
            // No concern, yet, for whether calls are in queue.
            // Would be appropriate to disallow new calls, but existing finish queue.
            if (runThread == null || runThread.isInterrupted())
                return;
            runThread.interrupt();
        }

        private CallHandler getCallHandler(int timeout) {
            CallHandler agent = openAgents.poll();
            // while (agent == null)
            // keep trying until timeout
            return agent;
        }

        public boolean take(Call call) {
            try {
                return callQueue.offer(call, 100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                return false;
            }
        }

        public boolean escalate(Call call) {
            // This doesn't guarantee that a call is handled
            boolean handled = technicalLead.handle(call);
            if (!handled)
                handled = productManager.handle(call);
            return handled;
        }

        public void login(CallHandler agent, EmployeeType role) {
            switch (role) {
            case PM:
                if (productManager == null)
                    productManager = agent;
                else
                    throw new RuntimeException(
                            "Only one Product Manager can be logged in at one time.");
                break;

            case TL:
                if (technicalLead == null)
                    technicalLead = agent;
                else
                    throw new RuntimeException(
                            "Only one Technical Lead can be logged in at one time.");
                break;

            case FRESHER:
                openAgents.offer(agent);
                break;

            default:
                throw new RuntimeException("Unrecognized Employee Type!");
            }
            // allAgents.put(agent.getUID(), agent);
        }

        public void log(CallHandler agent, Call call, CallStatus status) {
            // Do some sort of logging here
            // long callTime = System.nanoTime() - call.getInitTime();

            // A switch statement makes more sense when more options are engaged.
            if (status == CallStatus.ESCALATE)
                escalate(call);
            openAgents.offer(agent);
        }
    }
}
