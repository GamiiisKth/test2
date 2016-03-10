package datagrid;

import org.apache.ignite.*;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.lang.IgniteRunnable;

/**
 * Created by joshuapro on 01/03/16.
 */
public class GettingStart {
    private static final String CACHE_NAME = GettingStart.class.getSimpleName();

    public static void main(String[] args) throws IgniteException {

        try (Ignite ignite = Ignition.start("test2/config/example-cache.xml");) {

            populateCache(ignite);

            getvalueFromCache(ignite);
        }
        //   asyncAndSync(ignite);
    }

    public static void asyncAndSync(Ignite ignite) {
        IgniteCompute asyncCompute = ignite.compute().withAsync();

// Asynchronously execute a job.
        asyncCompute.call(() -> {
            // Print hello world on some cluster node and wait for completion.
            System.out.println("Asynchronous Hello World");

            return "Asynchronous Hello World";
        });

// Get the future for the above invocation.
        IgniteFuture<String> fut = asyncCompute.future();

// Asynchronously listen for completion and print out the result.
        fut.listen(f -> System.out.println("Job result: " + f.get()));

        /************* SYNC***************/

        IgniteCompute compute = ignite.compute();

// Execute a job and wait for the result.
        String res = compute.call(() -> {
            // Print hello world on some cluster node.
            System.out.println("Synchronous  Hello World");
            return "Synchronous  Hello World";
        });
    }

    public static void populateCache(Ignite ignite) {

        IgniteCache<Integer, String> cache = ignite.getOrCreateCache(CACHE_NAME);

        for (int i = 0; i < 20; i++) {
            cache.put(i, Integer.toString(i));
        }

    }

    public static void getvalueFromCache(Ignite ignite) {

        // get a cache with CACHE Name
     final IgniteCache<Integer ,String> cache = ignite.cache(CACHE_NAME);

     for (int i =0; i < 20; i++){

         final int key=i;

         ignite.compute().affinityRun(CACHE_NAME, key, new IgniteRunnable() {
             @Override
             public void run() {
                 System.out.println("key is "+ key +" localPeek "+ cache.localPeek(key)  );
             }
         });
     }



    }

}
