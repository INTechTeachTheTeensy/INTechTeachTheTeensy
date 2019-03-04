package teachtheteensy.electricalcomponents.simulation;

import teachtheteensy.electricalcomponents.ElectricalComponent;
import teachtheteensy.electricalcomponents.Pin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Classe utilitaire qui permet, à partir d'une liste de composants, de générer une correspondance entre pin et nom de noeud JSpice pour la simulation
 */
public class NodeMap {

    /**
     * Map qui à partir d'un nom de noeud de pin (toNodeName) donne le noeud JSpice correspondant
     */
    private Map<String, String> nameMap;

    public NodeMap() {
        nameMap = new HashMap<>();
    }

    /**
     * Créer les correspondances pin&lt;-&gt;noeud JSpice
     * @param components les composants de la simulation (peuvent ne pas être connectés)
     */
    public void buildFrom(List<ElectricalComponent> components) {
        nameMap.clear();
        // liste de toutes les pins mises en jeu
        Stream<Pin> allPins = components.stream()
                .flatMap(c -> c.getPins().stream());
        allPins.forEach(pin -> { // pour chaque pin
            Optional<String> potentialNode = findNode(pin); // on regarde s'il y a déjà un nom de noeud utilisable
            String node = potentialNode.orElse(pin.toNodeName()); // si oui on le prend, sinon on prend le nom de la pin
            nameMap.put(pin.toNodeName(), node); // on stocke le noeud
            pin.getConnections().stream()
                    .filter(p -> !nameMap.containsKey(p.toNodeName())) // on récupère toutes les connections qui n'ont pas encore de noeud associé
                    .forEach(p -> nameMap.put(p.toNodeName(), node)); // on leur associe le noeud de leur voisin
        });

        // remplacement des noms de pins (permet de forcer le nom de certains noeuds)
        components.stream()
                .flatMap(c -> c.getPins().stream())
                .forEach(p -> { // pour toutes les pins mises en jeu
                    if(p.forcesNodeName()) { // si le nom de noeud est focé
                        String forcedName = p.toNodeName();
                        String givenName = getNode(p);
                        if(!givenName.equals(forcedName)) {
                            nameMap.put(forcedName, forcedName);
                            for(String key : nameMap.keySet()) { // on remplace tous les noeuds utilisant l'ancien nom pour utiliser le nouveau nom
                                String value = nameMap.get(key);
                                if(value.equals(givenName)) {
                                    nameMap.put(key, forcedName);
                                }
                            }
                        }
                    }
                });
    }

    /**
     * Trouve le nom de noeud déjà utilisable pour cette pin, s'il en existe un (soit cette pin directement, soit le noeud d'un voisin)
     * @param pin la pin dont il faut trouver le nom de noeud
     * @return le nom utilisable, s'il existe
     */
    private Optional<String> findNode(Pin pin) {
        if(nameMap.containsKey(pin.toNodeName()))
            return Optional.of(nameMap.get(pin.toNodeName()));
        return pin.getConnections().stream() // pour toutes les connections de cette pin,
                .map(Pin::toNodeName)        // on prend le nom de noeud
                .filter(nameMap::containsKey) // on regarde s'il est déjà dans la liste
                .findFirst();                 // on prend le premier noeud qui correspond
    }

    /**
     * Renvoies le nom de noeud JSpice correspondant à la pin donnée
     * @param pin la pin dont il faut renvoyer le nom
     * @return le nom de noeud
     * @throws IllegalArgumentException si la pin n'est pas dans cette NodeMap
     */
    public String getNode(Pin pin) {
        if(nameMap.containsKey(pin.toNodeName()))
            return nameMap.get(pin.toNodeName());
        throw new IllegalArgumentException("La pin "+pin+" ("+pin.toNodeName()+") n'a pas de noeud JSpice associé!");
    }
}
