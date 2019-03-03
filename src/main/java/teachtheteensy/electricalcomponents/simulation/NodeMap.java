package teachtheteensy.electricalcomponents.simulation;

import teachtheteensy.electricalcomponents.ElectricalComponent;
import teachtheteensy.electricalcomponents.Pin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class NodeMap {

    /**
     * Map qui à partir d'un nom de noeud de pin (toNodeName) donne le noeud JSpice correspondant
     */
    private Map<String, String> nameMap;

    public NodeMap() {
        nameMap = new HashMap<>();
    }

    public void buildFrom(List<ElectricalComponent> components) {
        nameMap.clear();
        Stream<Pin> allPins = components.stream()
                .flatMap(c -> c.getPins().stream());
        allPins.forEach(pin -> {
            Optional<String> potentialNode = findNode(pin);
            String node = potentialNode.orElse(pin.toNodeName());
            nameMap.put(pin.toNodeName(), node);
            pin.getConnections().stream()
                    .filter(p -> !nameMap.containsKey(p.toNodeName())) // on récupère toutes les connections qui n'ont pas encore de noeud associé
                    .forEach(p -> nameMap.put(p.toNodeName(), node)); // on leur associe le noeud de leur voisin
        });

        // remplacement des noms de pins (permet de forcer le nom de certains noeuds)
        components.stream()
                .flatMap(c -> c.getPins().stream())
                .forEach(p -> {
                    if(p.forcesNodeName()) {
                        String forcedName = p.toNodeName();
                        String givenName = getNode(p);
                        if(!givenName.equals(forcedName)) {
                            nameMap.put(forcedName, forcedName);
                            for(String key : nameMap.keySet()) {
                                String value = nameMap.get(key);
                                if(value.equals(givenName)) {
                                    nameMap.put(key, forcedName);
                                }
                            }
                        }
                    }
                });

        for(Map.Entry<String, String> entry : nameMap.entrySet()) {
            System.out.println(entry.getKey()+" => "+entry.getValue());
        }
    }

    private Optional<String> findNode(Pin pin) {
        if(nameMap.containsKey(pin.toNodeName()))
            return Optional.of(nameMap.get(pin.toNodeName()));
        return pin.getConnections().stream() // pour toutes les connections de cette pin,
                .map(Pin::toNodeName)        // on prend le nom de noeud
                .filter(nameMap::containsKey) // on regarde s'il est déjà dans la liste
                .findFirst();                 // on prend le premier noeud qui correspond
    }

    public String getNode(Pin pin) {
        if(nameMap.containsKey(pin.toNodeName()))
            return nameMap.get(pin.toNodeName());
        throw new IllegalArgumentException("La pin "+pin+" ("+pin.toNodeName()+") n'a pas de noeud JSpice associé!");
    }
}
