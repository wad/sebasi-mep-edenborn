package org.sebasi.mep.edenborn.mind;

import org.sebasi.mep.edenborn.mind.internal.Brain;
import org.sebasi.mep.edenborn.mind.internal.InitialSettings;
import org.sebasi.mep.edenborn.mind.internal.NeuronCluster;
import org.sebasi.mep.edenborn.mind.internal.NeuronType;

public class Sim {

    public static void main(String... args) {
        Sim sim = new Sim();
        Brain brain = sim.makeBrain();
        System.out.println("Num neurons: " + brain.countAllNeurons());
    }

    public Brain makeBrain() {
        Brain brain = new Brain();
        NeuronCluster cluster = brain.getRegionByLabel(InitialSettings.INITIAL_REGION_LABEL)
                .getGroupByLabel(InitialSettings.INITIAL_GROUP_LABEL)
                .getClusterByLabel(InitialSettings.INITIAL_CLUSTER_LABEL);
        cluster.createNeuron(NeuronType.Standard);
        cluster.createNeuron(NeuronType.Standard);
        cluster.createNeuron(NeuronType.Standard);
        return brain;
    }
}
