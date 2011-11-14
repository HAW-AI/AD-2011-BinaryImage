package adp2.implementations;

import adp2.interfaces.BoundarySequence;

/**
 * Helper, muss irgendwo in BlobImpl einsortiert werden.
 * 
 * @author aav511
 */
public class BlobPerimeterGruppe3 {

    /**
     * Berechnet den Umfang nach Methode der Gruppe 3
     * 
     * @author aav511
     * 
     * @param boundarySequence
     * @return Umfang als double
     */
    public static double calcPerimeterGruppe3(BoundarySequence boundarySequence) {
        double res = 0;
        for(int i : boundarySequence.getSequence()) {
            if (i % 2 == 0) res += 1;
            else res += Math.sqrt(2);
        }
        return res;
    }
}
