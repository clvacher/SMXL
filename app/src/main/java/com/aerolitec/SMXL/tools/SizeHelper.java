package com.aerolitec.SMXL.tools;

import android.widget.TextView;

import com.aerolitec.SMXL.model.BrandsSizeGuide;
import com.aerolitec.SMXL.model.SizeConvert;
import com.aerolitec.SMXL.model.TabSizes;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.ui.SMXL;

import java.util.ArrayList;

/**
 * Created by Kevin on 19/06/2014.
 */
public class SizeHelper {

    private ArrayList<TabSizes> sizes;
    private User user;
    private String selectedBrand;
    private String selectedGarment;
    private String mComputeSize;
    private TextView textSizes;

    public SizeHelper(User user, String brand, String garment, String computeSize, TextView textSizes, ArrayList<TabSizes> sizes) {
        super();
        this.user = user;
        this.selectedBrand = brand;
        this.selectedGarment = garment;
        this.mComputeSize = computeSize;
        this.textSizes = textSizes;
        this.sizes = sizes;
    }

    public void computeSize() {
        if (selectedGarment.equalsIgnoreCase("") || selectedBrand.equalsIgnoreCase("")) {
            return;
        }

        // Calcul de la taille en fonction de : Données utilisateurs, type de vetements, marque, et pays
        // Donnees User + Table garment_size_guide.
        mComputeSize = "";
        String recherche = selectedGarment.toUpperCase();
        // Selon le type de vêtements, il faut faire une recherche à partir de certaines dimensions.

        // ************************ 3 Mesures : ROBE *********************************
        // Premier cas : Trois mesures à prendre en compte : ROBE, VESTE-F, MANTEAU-F, BLOUSON-F
        if (selectedGarment.equalsIgnoreCase("ROBE") ||
                selectedGarment.equalsIgnoreCase("VESTE") ||
                selectedGarment.equalsIgnoreCase("MANTEAU") ||
                selectedGarment.equalsIgnoreCase("BLOUSON")) {

            if (recherche.equalsIgnoreCase("BLOUSON") || recherche.equalsIgnoreCase("VESTE") || recherche.equalsIgnoreCase("MANTEAU")){
                recherche = recherche.toUpperCase() + "-" + user.getSexe().substring(0,1);
            }
            // Les dimensions à connaitre sont :Bust / Waist / Hips
            // Pour la marque sélectionnée

            ArrayList<BrandsSizeGuide> brandsSizes = SMXL.get().getDataBase().getBrandsSizes(recherche, selectedBrand);
            if (brandsSizes == null){
                mComputeSize = "N/A";
            } else {
                double computeSizeBust = 0D;
                double computeSizeWaist = 0D;
                double computeSizeHips = 0D;

                double userBust = user.getBust();
                double userWaist = user.getWaist();
                double userHips = user.getHips();
                if (userBust == 0 && userWaist == 0 && userHips == 0) {
                    textSizes.setText("MESURES UTILISATEUR NON DEFINIES");
                    mComputeSize = "N/A";
                } else {
                    if (userBust > 0) {
                        for (BrandsSizeGuide b : brandsSizes) {
                            if (b.getDim1() >= userBust) {
                                computeSizeBust = Double.valueOf(b.getSize());
                                break;
                            }
                        }
                    }

                    if (userWaist > 0) {
                        for (BrandsSizeGuide b : brandsSizes) {
                            if (b.getDim2() >= userWaist) {
                                computeSizeWaist = Double.valueOf(b.getSize());
                                break;
                            }
                        }
                    }

                    if (userHips > 0) {
                        for (BrandsSizeGuide b : brandsSizes) {
                            if (b.getDim3() >= userHips) {
                                computeSizeHips = Double.valueOf(b.getSize());
                                break;
                            }
                        }
                    }

                    if (computeSizeBust == 0 && computeSizeWaist == 0 && computeSizeHips == 0) {
                        textSizes.setText("MESURES MANQUANTES OU ERRONNEES");
                        mComputeSize = "N/A";
                    } else {
                        double sizeMax = convertSize(Math.max(Math.max(computeSizeBust, computeSizeWaist), computeSizeHips));
                        textSizes.setText(formatedStingSizes(convertAllSize(sizeMax)));
                        mComputeSize = " " + (int) Math.round(sizeMax);

                    }
                }
            }
        }

        // ************************ MESURE BUST (ET EVENTUELLEMENT WAIST)  *********************************
        // Cas : Mesure Bust => TSHIRT, SWEATSHIRT, PULL
        else if (selectedGarment.equalsIgnoreCase("TSHIRT") ||
                selectedGarment.equalsIgnoreCase("SWEAT") ||
                selectedGarment.equalsIgnoreCase("PULL")
                ){

            if(recherche.equalsIgnoreCase("sweat")) {
                recherche = "SWEATER";
            }

            recherche = recherche + "-" + user.getSexe().substring(0,1);
            // Les dimensions à connaitre sont :Bust et/ou waist
            // Pour la marque sélectionnée
            ArrayList<BrandsSizeGuide> brandsSizes = SMXL.get().getDataBase().getBrandsSizes(recherche, selectedBrand);
            if (brandsSizes == null){
                textSizes.setText("TAILLE : PAS DE DONNEES POUR CETTE MARQUE");
                mComputeSize = "N/A";
            }
            else {
                double computeSizeBust = 0D;
                double computeSizeWaist = 0D;

                double userBust = user.getBust();
                double userWaist = user.getWaist();
                if (userBust == 0 && userWaist == 0) {
                    textSizes.setText("MESURES UTILISATEUR NON DEFINIES");
                    mComputeSize = "N/A";
                } else {
                    if (userBust > 0) {
                        for (BrandsSizeGuide b : brandsSizes) {
                            if (b.getDim1() >= userBust) {
                                computeSizeBust = Double.valueOf(b.getSize());
                                break;
                            }
                        }
                    }
                    if (userWaist > 0) {
                        for (BrandsSizeGuide b : brandsSizes) {
                            if (b.getDim2() >= userWaist) {
                                computeSizeWaist = Double.valueOf(b.getSize());
                                break;
                            }
                        }
                    }

                    if (computeSizeBust == 0 && computeSizeWaist == 0) {
                        textSizes.setText("MESURES MANQUANTES OU ERRONNEES");
                        mComputeSize = "N/A";
                    } else {
                        double sizeMax = convertSize(computeSizeBust);
                        double sizeMin = convertSize(computeSizeWaist);
                        sizeMax = Math.max(sizeMax,sizeMin);
                        textSizes.setText(formatedStingSizes(convertAllSize(sizeMax)));
                        mComputeSize = " " + (int) Math.round(sizeMax);
                    }
                }
            }
        }


        // ************************ Mesure COU ET BUST : CHEMISE, CHEMISISERS *********************************
        //
        // Cas suivant
        else if (selectedGarment.equalsIgnoreCase("CHEMISE") || selectedGarment.equalsIgnoreCase("CHEMISIER")){
            // Les dimensions à connaitre sont :Bust / Collar
            // Pour la marque sélectionnée
            ArrayList<BrandsSizeGuide> brandsSizes = SMXL.get().getDataBase().getBrandsSizes(selectedGarment.toUpperCase(), selectedBrand);
            if (brandsSizes == null){
                textSizes.setText("TAILLE : PAS DE DONNEES POUR CETTE MARQUE");
                mComputeSize = "N/A";
            }
            else {
                double computeSizeBust = 0D;
                double computeSizeCollar = 0D;

                double userBust = user.getBust();
                double userCollar = user.getCollar();
                if (userBust == 0 && userCollar == 0 ) {
                    textSizes.setText("MESURES UTILISATEUR NON DEFINIES");
                    mComputeSize = "N/A";
                } else {
                    if (userCollar > 0) {
                        for (BrandsSizeGuide b : brandsSizes) {
                            if (b.getDim1() >= userCollar) {
                                computeSizeBust = Double.valueOf(b.getSize());
                                break;
                            }
                        }
                    }

                    if (userBust > 0) {
                        for (BrandsSizeGuide b : brandsSizes) {
                            if (b.getDim2() >= userBust) {
                                computeSizeBust = Double.valueOf(b.getSize());
                                break;
                            }
                        }
                    }

                    if (computeSizeBust == 0 && computeSizeCollar == 0 ) {
                        textSizes.setText("MESURES MANQUANTES OU ERRONNEES");
                        mComputeSize = "N/A";
                    } else {
                        double sizeMin;
                        double sizeMax = convertSize(Math.max(computeSizeBust, computeSizeCollar));
                        sizeMin = sizeMax;
                        textSizes.setText(formatedStingSizes(convertAllSize(sizeMin)));
                        mComputeSize = " " + (int) Math.round(sizeMin);
                    }
                }
            }
        }


        // ************************ Mesure WAIST & HIPS : JUPE, SLIP, PANTALONS,  *********************************
        //
        // Cas suivant
        else if (selectedGarment.equalsIgnoreCase("JUPE") || selectedGarment.equalsIgnoreCase("SLIP") ||
                selectedGarment.equalsIgnoreCase("PANTALON")){
            // Les dimensions à connaitre sont :Waist / Hips
            // Pour la marque sélectionnée
            if (recherche.equalsIgnoreCase("SLIP") || recherche.equalsIgnoreCase("PANTALON")){
                recherche += "-" + user.getSexe().substring(0,1);
            }
            ArrayList<BrandsSizeGuide> brandsSizes = SMXL.get().getDataBase().getBrandsSizes(recherche, selectedBrand);
            if (brandsSizes == null){
                textSizes.setText("TAILLE : PAS DE DONNEES POUR CETTE MARQUE");
                mComputeSize = "N/A";
            }
            else {
                double computeSizeWaist = 0D;
                double computeSizeHips = 0D;

                double userWaist = user.getWaist();
                double userHips = user.getHips();
                if (userWaist == 0 && userHips == 0 ) {
                    textSizes.setText("MESURES UTILISATEUR NON DEFINIES");
                    mComputeSize = "N/A";
                } else {
                    if (userWaist > 0) {
                        for (BrandsSizeGuide b : brandsSizes) {
                            if (computeSizeWaist == 0 && Double.valueOf(b.getDim1()) >= userWaist) {
                                computeSizeWaist = Double.valueOf(b.getSize());
                                break;
                            }
                        }
                    }

                    if (userHips > 0) {
                        for (BrandsSizeGuide b : brandsSizes) {
                            if (computeSizeHips == 0 && Double.valueOf(b.getDim2()) >= userHips) {
                                computeSizeHips = Double.valueOf(b.getSize());
                                break;
                            }
                        }
                    }

                    if (computeSizeHips == 0 && computeSizeWaist == 0 ) {
                        textSizes.setText("MESURES MANQUANTES OU ERRONNEES");
                        mComputeSize = "N/A";
                    } else {
                        double sizeMin;
                        double sizeMax = convertSize(Math.max(computeSizeHips, computeSizeWaist));
                        sizeMin = sizeMax;
                        textSizes.setText(formatedStingSizes(convertAllSize(sizeMin)));
                        mComputeSize = " " + (int) Math.round(sizeMin);
                    }
                }
            }
        }


        // ************************ CHAUSSURE HOMME & FEMME *********************************
        // Cas suivant
        else if (selectedGarment.equalsIgnoreCase("CHAUSSURE")){
            // Les dimensions à connaitre sont :Longueur de pied
            // Pour la marque sélectionnée
            recherche = selectedGarment.toUpperCase() + '-' + user.getSexe().substring(0,1);
            ArrayList<BrandsSizeGuide> brandsSizes = SMXL.get().getDataBase().getBrandsSizes(recherche, selectedBrand);
            if (brandsSizes.size() == 0){
                textSizes.setText("TAILLE : PAS DE DONNEES POUR CETTE MARQUE");
                mComputeSize = "N/A";
            }
            else {
                double computeSizeFoot = 0D;

                double userFoot = user.getFeet();
                if (userFoot == 0  ) {
                    textSizes.setText("MESURES UTILISATEUR NON DEFINIES");
                    mComputeSize = "N/A";
                } else {
                    if (userFoot > 0) {
                        for (BrandsSizeGuide b : brandsSizes) {
                            if (computeSizeFoot == 0 && Double.valueOf(b.getDim3()) >= userFoot) {
                                computeSizeFoot = Double.valueOf(b.getSize());
                                break;
                            }
                        }
                    }

                    if (computeSizeFoot == 0  ) {
                        textSizes.setText("MESURES MANQUANTES OU ERRONNEES");
                        mComputeSize = "N/A";
                    } else {
                        double sizeMax = convertSize(computeSizeFoot);
                        textSizes.setText(formatedStingSizes(convertAllSize(computeSizeFoot)));
                        mComputeSize = " " + (int) Math.round(sizeMax);
                    }
                }
            }
        }


        else if (selectedGarment.equalsIgnoreCase("PANTALON")) {
            // ... Autres types de vetements //
            textSizes.setText("Pantalon : Indéfini");
            mComputeSize = "N/A";
/**
 else if ... Autres types de vêtements
 /**/
        }
        else { // Type of Garments unknown
            textSizes.setText("VET. INCONNU");
            mComputeSize = "N/A";
        }
    }

    public double convertSize(double size){
        //sizes = new ArrayList<>();
        String recherche = selectedGarment.toUpperCase();
        if(recherche.equals("SWEAT")) {
            recherche = "SWEATER";
        }
        if (recherche.equalsIgnoreCase("CHAUSSURE")){
            recherche = recherche + "-" + user.getSexe().substring(0,1);
        }
        ArrayList<SizeConvert> listsizes = SMXL.get().getDataBase().getConvertSizesByGarment(recherche);
        if (listsizes == null)
            return size;
        double convertSize = 0;
        for(SizeConvert s : listsizes) {
            double toSizeConvert = 0D;
            toSizeConvert = Double.valueOf(s.getValueUE());

            double sizeRef = Double.valueOf(s.getValueUK());
            if (recherche.equalsIgnoreCase("CHAUSSURE")){ //TODO look into it
                sizeRef = Double.valueOf(s.getValueUS());
            }
            if (convertSize == 0 && sizeRef >= size) {
                convertSize = toSizeConvert;
                break;
            }
        }

        return convertSize;
    }

    public ArrayList<TabSizes> convertAllSize(double size){

        String recherche = selectedGarment.toUpperCase();
        if(recherche.equals("SWEAT")) {
            recherche = "SWEATER";
        }
        if (recherche.equalsIgnoreCase("CHAUSSURE")){
            recherche = recherche + "-" + user.getSexe().substring(0,1);
        }

        ArrayList<SizeConvert> listSizes = SMXL.get().getDataBase().getConvertSizesByGarment(recherche);
        if (sizes == null)
            return null;

        for(SizeConvert s : listSizes) {
            if (Double.valueOf(s.getValueUE()) >= size) {
                sizes.add(new TabSizes("US", String.valueOf(s.getValueUS())));
                sizes.add(new TabSizes("UK", String.valueOf(s.getValueUK())));
                sizes.add(new TabSizes("UE", String.valueOf(s.getValueUE())));
                sizes.add(new TabSizes("SMXL", String.valueOf(s.getValueAUS())));
                break;
            }
        }

        return sizes;
    }

    private StringBuilder formatedStingSizes(ArrayList<TabSizes> tabsizes){
        StringBuilder sb = new StringBuilder("");
        if (tabsizes.size()==0)
            return sb;
        for (TabSizes ts : tabsizes){
            sb.append(ts.getPays()).append(" : ").append(String.valueOf(ts.getValeur())).append("\n");
        }
        return sb;
    }

}
