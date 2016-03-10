package datagrid;

import model.Person;
import model.University;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.affinity.AffinityKey;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.springframework.cache.Cache;

/**
 * Created by joshuapro on 10/03/16.
 */
public class CacheQueryExample {

    private static final String UNI_CACHE = CacheQueryExample.class.getSimpleName() + "Universities";
    private static final String PERSON_CACHE = CacheQueryExample.class.getSimpleName() + "Persons";


    public static void main(String[] args) {
        try (Ignite ignite = Ignition.start("test2/config/example-ignite.xml")) {
            System.out.println();
            System.out.println("cash query started");

            CacheConfiguration<Long, University> uniCachCfg = new CacheConfiguration<>(UNI_CACHE);
            uniCachCfg.setCacheMode(CacheMode.PARTITIONED);
            uniCachCfg.setIndexedTypes(Long.class, University.class);

            CacheConfiguration<AffinityKey<Long>, Person> personCachCfg = new CacheConfiguration<>(PERSON_CACHE);
            personCachCfg.setCacheMode(CacheMode.PARTITIONED);
            personCachCfg.setIndexedTypes(AffinityKey.class, Person.class);

            try (
                    IgniteCache<Long, University> uniCache = ignite.getOrCreateCache(UNI_CACHE);
                    IgniteCache<AffinityKey<Long>, Person> personCache = ignite.getOrCreateCache(PERSON_CACHE);) {

                initialize();
                scanQuery();
                    sqlQuery();
            }


        }
    }

    /**
     * populate cash with data
     */
    private static void initialize() {
        IgniteCache<Long, University> uniCache = Ignition.ignite().cache(UNI_CACHE);

        uniCache.clear();

        // university
        University u1 = new University("KTH");
        University u2 = new University("DSV");

        uniCache.put(u1.getUniId(),u1);
        uniCache.put(u2.getUniId(),u2);


        IgniteCache<AffinityKey<Long>, Person> personCache = Ignition.ignite().cache(PERSON_CACHE);

        personCache.clear();

        // Persons

        Person p1 = new Person(u1, "ali", "yusha", "ali has bachlor degree", 4.5);
        Person p2 = new Person(u1, "keng", "fu", "keng has bachlor degree", 4.0);
        Person p3 = new Person(u2, "tomas", "anderson", "tomas has bachlor degree", 4.2);
        Person p4 = new Person(u2, "nicklas", "anderson", "nicklas has bachlor degree", 3.0);


        // Note that in this example we use custom affinity key for Person objects
        // to ensure that all persons are collocated with their university
        personCache.put(p1.key(), p1);
        personCache.put(p2.key(), p2);
        personCache.put(p3.key(), p3);
        personCache.put(p4.key(), p4);


    }

    /**
     * Example for scan query based on a predicate.
     */

    private static void scanQuery() {
        IgniteCache<BinaryObject, BinaryObject> cache = Ignition.ignite().cache(PERSON_CACHE).withKeepBinary();

        ScanQuery<BinaryObject, BinaryObject> scan = new ScanQuery<>(new IgniteBiPredicate<BinaryObject, BinaryObject>() {
            @Override
            public boolean apply(BinaryObject key, BinaryObject person) {
                return person.<Double>field("rate") <= 4;
            }
        });

           print("People with rate between 0 and 4 (queried with SCAN query): ", cache.query(scan).getAll());


    }

    private static void sqlQuery(){
        IgniteCache<AffinityKey<Long>,Person > cache = Ignition.ignite().cache(PERSON_CACHE);

        // SQL clause
        String sql= "rate < ?  and rate <=?";

            print("People with rates between 0 and 1000 (queried with SQL query): ",
    cache.query(new SqlQuery<AffinityKey<Long>, Person>(Person.class, sql).
        setArgs(2,5)).getAll());

    }







    private static void print(String msg, Iterable<?> col) {
        print(msg);
        print(col);
    }

    private static void print(String msg) {
        System.out.println();
        System.out.println(">>> " + msg);
    }


    private static void print(Iterable<?> col) {
        for (Object next : col)
            System.out.println(">>>     " + next);


    }
}

