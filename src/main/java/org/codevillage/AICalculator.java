package org.codevillage;

import java.util.ArrayList;
import java.util.List;

public class AICalculator {

    private List<Double> abstractnessList = new ArrayList<>();
    private List<Double> instabilityList = new ArrayList<>();

    public void calculateConnections(ArrayList<JavaClass> javaClasses) {
        for (JavaClass javaClass : javaClasses) {
            int inConnections = calculateInConnections(javaClass);
            int outConnections = calculateOutConnections(javaClass, javaClasses);

            double abstractness = calculateAbstractness(javaClass);
            double instability = calculateInstability(outConnections, inConnections);

            abstractnessList.add(abstractness);
            instabilityList.add(instability);

            System.out.println("JavaClass: " + javaClass.getName());
            System.out.println("In Connections: " + inConnections);
            System.out.println("Out Connections: " + outConnections);
            System.out.println("Abstractness: " + abstractness);
            System.out.println("Instability: " + instability);
            System.out.println("========================");
        }
    }

    private int calculateInConnections(JavaClass javaClass) {
        int inConnections = javaClass.getDependencies().size()
                + javaClass.getRealizations().size()
                + javaClass.getCompositions().size()
                + javaClass.getAssociations().size();

        if (javaClass.getParent() != null) {
            inConnections++; // Increment for parent connection
        }

        return inConnections;
    }

    private int calculateOutConnections(JavaClass javaClass, ArrayList<JavaClass> javaClasses) {
        int outConnections = 0;

        for (JavaClass otherClass : javaClasses) {
            if (!javaClass.equals(otherClass)) {
                if (javaClass.getDependencies().contains(otherClass.getName())
                        || javaClass.getRealizations().contains(otherClass.getName())
                        || javaClass.getCompositions().contains(otherClass.getName())
                        || javaClass.getAssociations().contains(otherClass.getName())
                        || javaClass.getName().equals(otherClass.getParent())) {
                    outConnections++;
                }
            }
        }

        return outConnections;
    }

    public double calculateAbstractness(JavaEntity javaClass) {
        return (javaClass.getType() == JavaEntityType.JAVA_ABSTRACT_CLASS || javaClass.getType() == JavaEntityType.JAVA_INTERFACE) ? 1.0 : 0.0;
    }

    public double calculateInstability(int outConnections, int inConnections) {
        return outConnections / (double) (inConnections + outConnections);
    }

    // Getter methods for accessing the lists
    public List<Double> getAbstractnessList() {
        return abstractnessList;
    }

    public List<Double> getInstabilityList() {
        return instabilityList;
    }
}
