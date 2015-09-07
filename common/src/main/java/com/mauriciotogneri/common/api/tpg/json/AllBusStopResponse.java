package com.mauriciotogneri.common.api.tpg.json;

import java.util.ArrayList;
import java.util.List;

public class AllBusStopResponse
{
    public Couleurs couleurs;

    public static class Couleurs
    {
        public String timestamp = "";
        public int maxage = 0;
        public String expire = "";
        public List<Couleur> couleur = new ArrayList<>();
    }

    public static class Couleur
    {
        public String backgroundColor = "";
        public String hexa = "";
        public String ligne = "";
        public String textColor = "";
    }
}