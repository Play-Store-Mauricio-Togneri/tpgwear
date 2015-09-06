package com.mauriciotogneri.common.api.tpg.json;

import java.util.ArrayList;
import java.util.List;

public class DeparturesResponse
{
    public ProchainsDeparts prochainsDeparts;

    public static class ProchainsDeparts
    {
        public String nomArret = "";
        public String heure = "";
        public String timestamp = "";
        public String codeArret = "";
        public String arretFinal = "";
        public List<ProchainDepart> prochainDepart = new ArrayList<>();
    }

    public static class ProchainDepart
    {
        public String attente = "";
        public String attenteMilli = "";
        public String destination = "";
        public String fiabilite = "";
        public String ligne = "";
        public String caracteristique = "";
        public String courseRef = "";
        public String destinationMajuscule = "";
        public String deviation = "";
        public String horaireRef = "";
    }
}