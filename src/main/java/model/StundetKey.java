package model;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;

/**
 * Created by joshuapro on 09/03/16.
 * this class represents key for stundetObject
 *
 * used in query example to collocate student with their university
 */
public class StundetKey {

    private  int id;

    @AffinityKeyMapped
    private int universityId;


    public  StundetKey(){

    }
    /**
     *
     * @param id
     * @param universityId
     */
    public StundetKey(int id, int universityId) {
        this.id = id;
        this.universityId = universityId;
    }

    /**
     *
     * @return ID
     */
    public int getId() {
        return id;
    }

    public int getUniversityId() {
        return universityId;
    }


    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + universityId;
        return result;
    }

    @Override
    public String toString() {
        return "StundetKey{" +
                "id=" + id +
                ", universityId=" + universityId +
                '}';
    }
}
