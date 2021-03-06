package com.example.creditreminder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity implements AddEditCreditFragment.AddEditCreditListener {

    public static final int ADD_CREDIT_REQUEST = 1;
    public static final int EDIT_CREDIT_REQUEST = 2;
    private CreditViewModel creditViewModel;
    private CreditAdapter adapter = new CreditAdapter();
    boolean bound = false;
    ServiceConnection serviceConnection;
    Intent intent;
    ReminderService reminderService;
    long interval;
    final String TAG = "Reminder";
    NotificationManager nm;
    private List<Credit> creditsForService;
    //private  Credit openCredit;
    AddEditCreditFragment addEditCreditFragment = new AddEditCreditFragment();
    //private  mainCreditListener;
    //private final int NOTIFICATION_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //--------------------------------------------------------------------------
        FloatingActionButton buttonAddCredit = findViewById(R.id.button_add_credit);
        buttonAddCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AddEditCreditActivity addCreditActivity = new AddEditCreditActivity();
                addEditCreditFragment.show(getSupportFragmentManager(), "AddEditCredit");
                //Intent intent = new Intent(MainActivity.this, AddEditCreditActivity.class);
                //startActivityForResult(intent, ADD_CREDIT_REQUEST);
            }
        });
        //--------------------------------------------------------------------------
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        intent = new Intent(this, ReminderService.class);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                reminderService = ((ReminderService.ReminderBinder) service).getService();
                bound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                bound = false;
            }
        };

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        creditViewModel = ViewModelProviders.of(this).get(CreditViewModel.class);
        //creditViewModel = ViewModelProviders.of(this).get(CreditViewModel.class);
        creditViewModel.getAllCredits().observe(this, new Observer<List<Credit>>() {
            @Override
            public void onChanged(List<Credit> credits) {
                adapter.setCredit(credits);
                creditsForService = credits;
                intent.putExtra(Credit.class.getSimpleName(), (Serializable) creditsForService);
                //----------------------------
                if (sharedPreferences.getBoolean("show_notification", false) == true) {
                    //stopService(intent);
                    startService(intent);
                    //reminderService.setCredit(credits);
                    Toast.makeText(MainActivity.this, "Данные получены и отправлены в сервис", Toast.LENGTH_SHORT).show();
                }
                if (sharedPreferences.getBoolean("show_notification", false) == false) {
                    //stopService(intent);
                    stopService(intent);
                    //reminderService.setCredit(credits);
                    Toast.makeText(MainActivity.this, "Сервис остановлен", Toast.LENGTH_SHORT).show();
                }

            }
        });

        adapter.setOnItemClickListener(new CreditAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Credit credit) {
                //AddEditCreditActivity editCreditActivity = new AddEditCreditActivity();
                addEditCreditFragment.show(getSupportFragmentManager(), "AddEditCredit");
                //openCredit = credit;
                addEditCreditFragment.openCredit(credit);
                //Intent intent = new Intent(MainActivity.this, AddEditCreditActivity.class);
                //intent.putExtra(Credit.class.getSimpleName(), credit);
                //intent.putExtra("AddEditId", EDIT_CREDIT_REQUEST);
                //startActivityForResult(intent, EDIT_CREDIT_REQUEST);
            }

            @Override
            public void onItemLongClick(int position) {
                creditViewModel.deleteAllCredits();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(intent, serviceConnection, 0);
        if (bound) reminderService.setCredit(creditsForService);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!bound) return;
        unbindService(serviceConnection);
        stopService(intent);
        bound = false;
    }

    void OpenAddEditCreditDialog() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_CREDIT_REQUEST && resultCode == RESULT_OK) {
            Credit newCredit = (Credit) data.getSerializableExtra(Credit.class.getSimpleName());
            creditViewModel.insert(newCredit);
            Toast.makeText(MainActivity.this, "Запись добавлена", Toast.LENGTH_SHORT).show();
        } else if(requestCode == EDIT_CREDIT_REQUEST && resultCode == RESULT_OK) {
            Credit newCredit = (Credit) data.getSerializableExtra(Credit.class.getSimpleName());
            creditViewModel.update(newCredit);
            Toast.makeText(MainActivity.this, "Запись обновлена", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Запись не добавлена", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_credits:
                creditViewModel.deleteAllCredits();
                Toast.makeText(MainActivity.this, "Все записи удалены", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.preferences:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                //creditViewModel.deleteAllCredits();
                //Toast.makeText(MainActivity.this, "Все записи удалены", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 121:
                //creditViewModel.delete(item.getGroupId());
                adapter.removeCredit(item.getGroupId());
                Toast.makeText(MainActivity.this, "Запись удалена", Toast.LENGTH_SHORT).show();
                return true;
            /*case 122:
                Intent intent = new Intent(MainActivity.this, AddEditCreditActivity.class);
                intent.putExtra(Credit.class.getSimpleName(), credit);
                intent.putExtra("AddEditId", EDIT_CREDIT_REQUEST);
                startActivityForResult(intent, EDIT_CREDIT_REQUEST);
                return true;*/
            default:
                return super.onContextItemSelected(item);

        }


    }

    @Override
    public void saveCredit(Credit newCredit) {
        if (newCredit.getId() >=1) {
            creditViewModel.update(newCredit);
        } else {
            creditViewModel.insert(newCredit);
        }
    }

    private Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            if (preference instanceof SwitchPreference) {
                if (stringValue == "false") {
                    ((SwitchPreference) preference).setSummaryOff("Напоминания отключены");
                    StopNotification();
                }
                if (stringValue == "true") {
                    ((SwitchPreference) preference).setSummaryOff("Напоминания включены");
                    StartNotification();
                }
            }
            return false;
        }
    };

    void StopNotification() {
        stopService(intent);
        Toast.makeText(MainActivity.this, "Сервис остановлен", Toast.LENGTH_SHORT).show();
    }

    void StartNotification() {
        stopService(intent);
        Toast.makeText(MainActivity.this, "Сервис запущен", Toast.LENGTH_SHORT).show();
    }

    //@Override
    //public void openCredit(Credit newCredit) {

    //}

    /*private void remove(int position) {
        Product product = productsAdapter.getProducts().remove(position);
        viewModel.deleteProduct(product);
        //database.notesDao().deleteNote(note);
    }*/

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }*/
}
