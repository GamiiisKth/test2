package model;

import org.apache.ignite.cache.affinity.AffinityKey;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by joshuapro on 09/03/16.
 */
public class Person implements Serializable {

    private static final AtomicLong ID_GEN = new AtomicLong();
    @QuerySqlField(index = true)
    public Long uId;

    @QuerySqlField(index = true)
    public Long orgId;

    @QuerySqlField
    public String firstName;
    @QuerySqlField
    public String lastName;
    @QuerySqlField
    public String resume;

    @QuerySqlField(index = true)
    public double rate;

    // anpassade cache nyckel för att garantera att person kommmer alltid samlokaliseras med sitt university (organisation)
    // The transient keyword in Java is used to indicate that a field should not be serialized.
    private transient AffinityKey<Long> key;

    public  Person(){

    }

    /**
     *
     * @param uni
     * @param firstName
     * @param lastName
     * @param resume
     * @param rate
     */

    public Person(University uni,String firstName, String lastName, String resume, double rate) {
        // Generate unique id for this person
        uId=ID_GEN.decrementAndGet();
        orgId= uni.getUniId();

        this.firstName = firstName;
        this.lastName = lastName;
        this.resume = resume;
        this.rate = rate;
    }

    /**
     *
     * @param uId ID
     * @param orgId UniversityId
     * @param firstName Student first name
     * @param lastName Student last name
     * @param resume
     * @param rate
     */
    public Person(Long uId, Long orgId, String firstName, String lastName, String resume, double rate) {
        this.uId = uId;
        this.orgId = orgId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.resume = resume;
        this.rate = rate;
    }

    /**
     * * Gets cache affinity key. Since in some examples person needs to be collocated with organization, we create
     * custom affinity key to guarantee this collocation.
     * @return Custom affinity key to guarantee that person is always collocated with organization.
     */
    public AffinityKey<Long> key(){
        if (key==null)
            key= new AffinityKey<>(uId,orgId);
        return key;
    }

    @Override
    public String toString() {
        return "Person{" +
                "rate=" + rate +
                ", resume='" + resume + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", orgId=" + orgId +
                ", uId=" + uId +
                '}';
    }
}
