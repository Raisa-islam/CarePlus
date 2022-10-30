package com.raisa.update1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.raisa.update1.Card.FindMember;
import com.raisa.update1.Card.Member_card;

public class AddMemberFragment extends Fragment {
    TextView Find;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_member,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
           super.onViewCreated(view, savedInstanceState);


           Find = getView().findViewById(R.id.FindMemberPage);

           Find.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent i = new Intent( getContext(), FindMember.class);
                   startActivity(i);
               }
           });
    }
}
