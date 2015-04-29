package com.herokuapp.ezhao.listicle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.activeandroid.query.Select;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ListicleFragment extends Fragment {
    public static final String LISTICLE_NAME_KEY = "listicleName";
    Activity activity;
    String listicleName;
    ArrayAdapter<Listicle> listicleAdapter;
    @InjectView(R.id.lvListicle) ListView lvListicle;
    @InjectView(R.id.etNewItem) EditText etNewItem;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listicleName = getArguments().getString(LISTICLE_NAME_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listicle, container, false);
        ButterKnife.inject(this, view);

        List<Listicle> objects = new ArrayList<>();
        if (listicleName != null) {
            objects = new Select().from(Listicle.class).where("listName = ?", listicleName).execute();
        }

        listicleAdapter = new ArrayAdapter<>(activity, R.layout.item_listicle, objects);
        lvListicle.setAdapter(listicleAdapter);

        return view;
    }

    @OnClick(R.id.btnSubmit)
    public void onSubmit() {
        if (etNewItem != null && listicleName != null) {
            String newObject = etNewItem.getText().toString();
            Listicle listicle = new Listicle(listicleName, newObject);
            listicle.save();
            listicleAdapter.add(listicle);
            etNewItem.setText("");
        }
    }
}
