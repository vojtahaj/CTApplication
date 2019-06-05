package umte.fim.uhk.cz.ctapplication.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import umte.fim.uhk.cz.ctapplication.R;

public class SettingFragment extends Fragment {

    private Spinner spinnerSequence;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting,container,false);

        spinnerSequence = view.findViewById(R.id.spinner_sequence);
        String[] sequenceValues = getResources().getStringArray(R.array.CT_set_sequence);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, sequenceValues);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSequence.setAdapter(spinnerAdapter);

        return view;
    }

}
