package org.sebasi.mep.tool.datastructure.v1;

// Neurons with axons get an instance of this class upon construction
public class AxonFiringState {

    boolean isActive;
    AxonFiringProfile axonFiringProfile;

    int numTicksUntilNextFiring;
    int numTicksBetweenFirings;
    int numFiringsRemaining;
    int numTicksAfterFinalFiringUntilReset;

    public AxonFiringState(AxonFiringProfile axonFiringProfile) {
        this.axonFiringProfile = axonFiringProfile;
        isActive = false;
    }

    public void prepareToStartFiringSequence(AxonInputSignalStrength signalStrength) {
        if (isActive) {
            return;
        }

        this.numTicksBetweenFirings = axonFiringProfile.getNumTicksBetweenFirings(signalStrength);
        this.numFiringsRemaining = axonFiringProfile.getNumFirings();
        this.numTicksAfterFinalFiringUntilReset = axonFiringProfile.getNumTicksAfterFinalFireUntilReset();
        this.numTicksUntilNextFiring = 0;
    }

    // returns true if the axon should now fire.
    public boolean receiveTickAndMaybeFire() {
        if (!isActive) {
            return false;
        }

        return false;
        // todo
//        if (numFiringsRemaining > 0) {
//            numFiringsRemaining--;
//            if (numTicksUntilNextFiring <= 0) {
//                numTicksUntilNextFiring = numTicksBetweenFirings;
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//
//        }
    }

}
