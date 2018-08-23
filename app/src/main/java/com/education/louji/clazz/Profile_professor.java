package com.education.louji.clazz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.LeadingMarginSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class Profile_professor extends Fragment  {
   public Profile_professor(){}
    ListView li;
    String[] listcontent={"Friends","All Users","Settings","Support us","Feedback"};
    int[] image={R.drawable.firends,R.drawable.allusers,R.drawable.settings,R.drawable.supportus,R.drawable.feedbac};
   View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_profile_professor, container, false);

        li=v.findViewById(R.id.list);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,listcontent);
        li.setAdapter(adapter);
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent intent=new Intent(getContext(),ProfessorFriends.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1=new Intent(getContext(),ProfessorAllUsers.class);
                        startActivity(intent1);break;
                    case 2:
                        Intent intent2=new Intent(getContext(),ProfessorSettings.class);
                        startActivity(intent2);break;
                    case 3:
                        Intent intent3=new Intent(getContext(),ProfessorContactus.class);
                        startActivity(intent3);break;
                    case 4:
                        Intent intent4=new Intent(getContext(),ProfessorFeedback.class);
                        startActivity(intent4);break;


                }
            }
        });
        return v;
    }


}
