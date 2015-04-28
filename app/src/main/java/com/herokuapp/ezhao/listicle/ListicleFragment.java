package com.herokuapp.ezhao.listicle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class ListicleFragment extends Fragment {
    Activity activity;
    @InjectView(R.id.lvListicle) ListView lvListicle;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listicle, container, false);
        ButterKnife.inject(this, view);

        ArrayList<String> objects = new ArrayList<>();
        objects.add("test");
        objects.add("test again");
        objects.add("test");
        objects.add("test again");
        objects.add("test");
        objects.add("test again");

        ArrayAdapter<String> listicleAdapter = new ArrayAdapter<String>(activity, R.layout.item_listicle, objects);
        lvListicle.setAdapter(listicleAdapter);

        return view;
    }
}
