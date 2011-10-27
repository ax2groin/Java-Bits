package rtb;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * This example assumes that you need to have the TimeConsumingObjects available to proceed.
 */
public final class ParallelInitializer {

    private final String[] objUIDs;

    private final Map<String, TimeConsumingObject> timeConsumingObjects
            = new ConcurrentHashMap<String, TimeConsumingObject>();

    public ParallelInitializer(String... objectNames) {
        // TODO: Ensure that there are enough objects to merit this approach.
        this.objUIDs = objectNames;
        initializeObjects();
        // TODO: Do something with the objects now that they exist.
    }

    private void initializeObjects() {
        // Launch on a thread apiece and then wait to complete.
        int threadPoolSize = Math.min(objUIDs.length,
                Runtime.getRuntime().availableProcessors());
        ExecutorService stateInitializer = Executors .newScheduledThreadPool(threadPoolSize);
        CompletionService<TimeConsumingObject> completionService
                = new ExecutorCompletionService<TimeConsumingObject>(stateInitializer);
        for (final String objName : objUIDs)
            completionService.submit(new Callable<TimeConsumingObject>() {
                public TimeConsumingObject call() {
                    try {
                        return new TimeConsumingObject(objName);
                    } catch (Exception e) { // TODO: Never catch "Exception, but
                                            // catch any relevant exceptions here
                        // Log error:
                        // "Failed to initialize TimeConsumingObject for " +
                        // objName, e
                        return null;
                    }
                }
            });

        for (int i = 0; i < objUIDs.length; i++)
            try {
                Future<TimeConsumingObject> future = completionService.take();
                TimeConsumingObject timeConsumingObject = future.get();
                timeConsumingObjects.put(timeConsumingObject.getUID(),
                        timeConsumingObject);
            } catch (InterruptedException ie) {
                // TODO: Take action if cannot take() from completion service.
            } catch (ExecutionException e) {
                // TODO: Take action if cannot get() from future.
            }
    }

    public static final class TimeConsumingObject {

        private final String uid;

        public TimeConsumingObject(String uid) {
            this.uid = uid;
            // And then some calculations, state initialization, etc. that takes
            // a long time.
        }

        public String getUID() {
            return uid;
        }
    }
}
