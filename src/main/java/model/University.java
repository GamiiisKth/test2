package model;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by joshuapro on 09/03/16.
 */
public class University {

    private static final AtomicLong ID_GEN= new AtomicLong();

    @QuerySqlField(index = true)
    private Long uniId;

    @QuerySqlField(index = true)
    private String uniName;


    private Address address;

    private UniversityType type;

    private Timestamp lastUpdate;

    public University() {
    }

    public University(String uniName) {
        uniId = ID_GEN.incrementAndGet();
        this.uniName = uniName;
    }

    /**
     * @param uniName
     * @param address
     * @param type
     * @param lastUpdate last update time
     */

    public University( String uniName, Address address, UniversityType type, Timestamp lastUpdate) {
        // Generate a unique id for this university
        this.uniId = ID_GEN.incrementAndGet();
        this.uniName = uniName;
        this.address = address;
        this.type = type;
        this.lastUpdate = lastUpdate;
    }

    public Address getAddress() {
        return address;
    }

    public Long getUniId() {
        return uniId;
    }

    public String getUniName() {
        return uniName;
    }

    public UniversityType getType() {
        return type;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

}
