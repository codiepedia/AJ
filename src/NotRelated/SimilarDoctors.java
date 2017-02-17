package NotRelated;
import java.util.*;

/*
SimilarDoctors is a class which holds the in-memory doctor data, and supports:
      ranking all doctors given a specific doctor
 */
public class SimilarDoctors {

    private List<Doctor> doctors;

    public SimilarDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Doctor> getSimilarDoctors(Doctor doctor) {
        List<Doctor> doctors_copy = new ArrayList<Doctor>(doctors);
        Collections.sort(doctors_copy, doctor.similarityComparator());
        return doctors_copy;
    }

    public static void main(String[] args) {
        Doctor d1 = new Doctor("Anne", 9.0, "East", "Dentist");
        Doctor d2 = new Doctor("Bob", 8.2, "West", "Surgeon");
        Doctor d3 = new Doctor("Cindy", 3.0, "West", "Surgeon");
        Doctor d4 = new Doctor("David", 3.0, "Central", "Surgeon");
        List<Doctor> doctors = new ArrayList<Doctor>(Arrays.asList(d1, d2, d3));

        SimilarDoctors sd = new SimilarDoctors(doctors);
        List<Doctor> rankedDoctors = sd.getSimilarDoctors(d4);
        for (Doctor d : rankedDoctors) {
            System.out.println(d);
        }
    }
}

/*
Doctor is a class which contains necessary fields to describe a doctor
 */
class Doctor {
    private String name;
    private double score;
    private String area;
    private String specialty;
    private static final Set<String> AREAS = new HashSet<String>(Arrays.asList("east", "central", "west"));
    private static final Set<String> SPECIALTIES = new HashSet<String>(Arrays.asList("dentist", "surgeon", "neurologist"));
    private Comparator<Doctor> similarityComparator;

    private static final double STRING_FIELD_WEIGHT = 5.0;

    Doctor(String name, double score, String area, String specialty) {
        /*
        param(String): name, the name of the doctor
        param(double): score, the review score of the doctor
        param(String): area, where does the doctor live
        param(String): specialty: what field does the doctor specialize

        throws(IllegalArgumentException)
         */
        this.name = name;

        if (score < 0.0 || score > 10.0) {
            throw new IllegalArgumentException();
        }
        this.score = score;

        if (! AREAS.contains(area.toLowerCase())) {
            throw new IllegalArgumentException();
        }
        this.area = area;

        if (! SPECIALTIES.contains(specialty.toLowerCase())) {
            throw new IllegalArgumentException();
        }
        this.specialty = specialty;

        this.similarityComparator = new DoctorSimilarityComparator(this);
    }

    private double calculate_difference(Doctor d) {
        /*
        param(Doctor) : d, the doctor object you are comparing against

        return(double) : the difference between current doctor and doctor d. Lower value indicates more similar.
         */
        double specialty = STRING_FIELD_WEIGHT * (this.specialty.equals(d.specialty) ? 0: 1);
        double area = STRING_FIELD_WEIGHT * (this.area.equals(d.area) ? 0: 1);
        double score = Math.abs(this.score - d.score);
        return specialty + area + score;
    }

    public Comparator<Doctor> similarityComparator() {
        return this.similarityComparator;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", area='" + area + '\'' +
                ", specialty='" + specialty + '\'' +
                ", similarityComparator=" + similarityComparator +
                '}';
    }

    /*
    DoctorSimilarityComparator is a comparator that compares two doctors based on their difference against another doctor
     */
    private class DoctorSimilarityComparator implements Comparator<Doctor> {

        private Doctor selectedDoctor;

        DoctorSimilarityComparator(Doctor selectedDoctor) {
            this.selectedDoctor = selectedDoctor;
        }

        @Override
        public int compare(Doctor doctor, Doctor t1) {
            double d1_score = this.selectedDoctor.calculate_difference(doctor);
            double d2_score = this.selectedDoctor.calculate_difference(t1);
            if (d1_score < d2_score) {
                return -1;
            } else if (d1_score > d2_score) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}