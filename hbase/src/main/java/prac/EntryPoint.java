package prac;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

public class EntryPoint {
    public static void main(String[] args) throws IOException {
        Physician physician = new Physician();
        physician.setSpecialization("Therapist");
        physician.setFullName("Therapist Therapistov");
        physician.setClinicName("Clinic1");


        UUID id = UUID.randomUUID();
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(id.getMostSignificantBits());
        buffer.putLong(id.getLeastSignificantBits());
        physician.setId(buffer.array());
        System.out.println(buffer.array());


        List<Patient> patients = new ArrayList<>();
        List<String> patientNames = new ArrayList<>(Arrays.asList(
                "Patient1", "Patient2", "Patient3", "Patient4", "Patient5", "Patient5",
                "Patient6", "Patient7", "Patient8", "Patient9", "Patient10"
        ));
        List<String> patientLastNames = new ArrayList<>(Arrays.asList(
                "PatientLastName1", "PatientLastName2", "PatientLastName3", "PatientLastName4", "PatientLastName5",
                "PatientLastName5", "PatientLastName6", "PatientLastName7", "PatientLastName8", "PatientLastName9",
                "PatientLastName10"
        ));
        List<Date> patientDOB = new ArrayList<>(Arrays.asList(
                new Date(1985, 04, 25), new Date(1976, 04, 21), new Date(1999, 12, 12), new Date(1993, 04, 03),
                new Date(1967, 01, 21), new Date(2000, 01, 19), new Date(1959, 12, 22), new Date(1997, 05, 33),
                new Date(1998, 11, 13), new Date(1959, 06, 25)
        ));

        for (int i = 0; i < 10; i++) {
            Patient patientX = new Patient();
            patientX.setDateOfBirth(patientDOB.get(i));
            patientX.setFirstName(patientNames.get(i));
            patientX.setLastName(patientLastNames.get(i));

            UUID uuid = UUID.randomUUID();
            ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
            bb.putLong(uuid.getMostSignificantBits());
            bb.putLong(uuid.getLeastSignificantBits());

            patientX.setId(bb.array());
            patients.add(patientX);
        }

        List<MedicalRecord> medicalRecords = new ArrayList<>();
;
        for(int i = 0; i < 10; i++) {
            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setDescription("any description" + String.valueOf(i));
            medicalRecord.setDatePerformed(new Date(2008, 05, 19));
            medicalRecord.setType("type" + String.valueOf((6 + i)/3));

            UUID uuid = UUID.randomUUID();
            ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
            bb.putLong(uuid.getMostSignificantBits());
            bb.putLong(uuid.getLeastSignificantBits());

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            stream.write(bb.array());
            stream.write(patients.get(i).getId());

            medicalRecord.setId(stream.toByteArray());
            medicalRecords.add(medicalRecord);
        }


        Map<String, String> properties = new HashMap<>();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hbase_pu", properties);
        EntityManager manager = factory.createEntityManager();

        manager.persist(physician);
        for(int i = 0; i < 10; i++) {
            manager.persist(medicalRecords.get(i));
            manager.persist(patients.get(i));
        }

        manager.close();
        factory.close();

    }
}
