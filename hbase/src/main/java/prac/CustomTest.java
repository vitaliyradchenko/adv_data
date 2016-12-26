package prac;

/**
 * Created by vitaliyradchenko on 12/26/16.
 */
import com.lits.kundera.test.BaseTest;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.Assert;

public class CustomTest extends BaseTest{

    @Override
    public void customTest() throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hbase_pu");
        EntityManager em = emf.createEntityManager();

        Query findQuery = em.createQuery("select p from Physician p");
        List<prac.Physician> allPhysicians = findQuery.getResultList();
        Assert.assertNotEquals(0, allPhysicians.size());

        Query findQuery2 = em.createQuery("select p from Patient p where p.firstName = \'Patient1\'");
        List<prac.Patient> Patients = findQuery2.getResultList();
        Assert.assertEquals(1, Patients.size());

        Query findQuery3 = em.createQuery("select mr from MedicalRecord mr where mr.type = \'type2\'");
        List<prac.MedicalRecord> allRecords = findQuery3.getResultList();
        Assert.assertEquals(3, allRecords.size());
    }

    public static void main(String[] args) {
        CustomTest test = new CustomTest();
        try {
            test.runSuite();
        } catch (IOException ex) {

        }
    }
}
