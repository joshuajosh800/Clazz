package com.education.louji.clazz;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Student extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
TextView tx;
FirebaseUser firebaseUser;
FirebaseAuth mauth;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
String uid;
String utype;
ProgressDialog progressBar;
TextView username;
ImageView ima;
    StorageReference storageRef;
    FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        mauth=FirebaseAuth.getInstance();
        firebaseUser=mauth.getCurrentUser();
        ima=findViewById(R.id.imageView);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        uid=firebaseUser.getUid();
        progressBar=new ProgressDialog(this);
        progressBar.setMessage("Initializing");
        progressBar.show();
        progressBar.setCancelable(false);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users").child(uid);
        databaseReference.child("Personal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                utype=dataSnapshot.child("U_type").getValue().toString();
                    if(utype.equals("Student")){
                        loadFragment(new Home_student());

                    }
                    else{
                        loadFragment(new Home_professor());

                    }
                Toast.makeText(Student.this,utype,Toast.LENGTH_LONG).show();
                progressBar.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView bottomNavigationView=findViewById(R.id.btnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                int id=item.getItemId();
                if (id == R.id.navigation_home) {
                       if(utype.equals("Student")){
                           fragment=new Home_student();
                       }
                       else{
                           fragment=new Home_professor();

                       }
                } else if (id == R.id.navigation_dashboard) {
                    if(utype.equals("Student")){
                        fragment=new Class_student();

                    }
                    else{
                        fragment=new Class_professor();

                    }
                } else if (id == R.id.navigation_notifications) {
                    if(utype.equals("Student")){
                        fragment=new Profile_student();

                    }
                    else{
                        fragment=new Profile_professor();

                    }
                }
                return loadFragment(fragment);

            }
        });
        tx=findViewById(R.id.text1);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id==R.id.action_logout){
            progressBar.setMessage("loging Out");
            progressBar.show();
            mauth.signOut();

                    FirebaseUser muse=mauth.getCurrentUser();
                    if(muse==null){
                        progressBar.dismiss();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment=null;
        if (id == R.id.nav_about) {
                fragment=new About();
        } else if (id == R.id.nav_gallery) {
            fragment=new Gallery();

        } else if (id == R.id.nav_faculty) {
            fragment=new Faculty();

        } else if (id == R.id.nav_contact) {
            fragment=new ContactUs();

        } else if (id == R.id.nav_fdirection) {
            fragment=new FutureDirection();

        } else if (id == R.id.nav_stuent) {
            fragment=new StudentAchievements();
        }else if (id == R.id.nav_events) {
            fragment=new Events();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fcontainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
