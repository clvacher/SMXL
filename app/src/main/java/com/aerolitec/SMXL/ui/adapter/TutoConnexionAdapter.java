package com.aerolitec.SMXL.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.PageTuto;
import com.aerolitec.SMXL.ui.fragment.PageTutoConnexionFragment;

import java.util.ArrayList;

/**
 * Created by Jerome on 11/06/2015.
 */
public class TutoConnexionAdapter extends FragmentPagerAdapter {

    private static ArrayList<PageTuto> al_pagetuto = new ArrayList<PageTuto>(){{
        //add(new PageTuto(R.drawable.icone_hd, "Avec SMXL, vive le shopping !", "3 petits tours ... Et tout est à ma taille !"));
        /*add(new PageTuto(R.drawable.desktop, "Fini les retours !", "Je commande sur le net vêtements et chaussures toujours à ma taille !"));
        add(new PageTuto(R.drawable.watch, "Gagnez du temps à l'essayage !", "Je n'hésite plus entre deux tailles, je n'en essaye plus qu'une et tout me va !"));
        add(new PageTuto(R.drawable.family, "Une tribu bien habillée !", "Non seulement j'ai toujours les mesures de ma famille, mais aussi leurs marques préférées !"));
        add(new PageTuto(R.drawable.present_pic, "Des cadeaux bien trouvés !", "Mes amis partagent leur profil avec moi, et SMXL me prévient le jour de leur anniversaire, facile de faire plaisir !"));
    */
        //R.string.tuto_mobile_title
        add(new PageTuto(R.drawable.mobile_pic,     "Fini les surprises de tailles !", "Je commande sur le net vêtements et chaussures pour moi et mes proches toujours à la bonne taille !"));
        add(new PageTuto(R.drawable.present_pic,       "Gagnez du temps à l'essayage!" , "Grâce à la Quicksize , je n'hésite plus entre 2 tailles. Je n'en essaye plus qu'une et tout me va !"));
        add(new PageTuto(R.drawable.family,      "Une famille bien habillée !"    , "Non seulement j'ai toujours les mesures de ma famille, mais aussi leurs marques préférées !"));
        add(new PageTuto(R.drawable.friends_pic, "Envie de faire plaisir !"   , "Partagez les profils avec vos amis et offrez leur des cadeaux à leurs envies !"));

    }};

    private static final int NUM_PAGES = al_pagetuto.size();

    public TutoConnexionAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        PageTuto pageTuto = al_pagetuto.get(position);

        return new PageTutoConnexionFragment().newInstance(pageTuto);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }


}




