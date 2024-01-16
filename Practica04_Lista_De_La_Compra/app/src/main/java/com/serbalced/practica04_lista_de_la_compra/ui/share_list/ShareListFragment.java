package com.serbalced.practica04_lista_de_la_compra.ui.share_list;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.serbalced.practica04_lista_de_la_compra.R;
import com.serbalced.practica04_lista_de_la_compra.databinding.FragmentShareListBinding;
import com.serbalced.practica04_lista_de_la_compra.managers.ContactsManager;
import com.serbalced.practica04_lista_de_la_compra.ui.new_product.NewProductViewModel;
import com.serbalced.practica04_lista_de_la_compra.ui.show_lists.ShoppingListsDialog;

public class ShareListFragment extends Fragment {
    private FragmentShareListBinding binding;
    private final int REQUEST_CODE = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        NewProductViewModel newProductViewModel =
                new ViewModelProvider(this).get(NewProductViewModel.class);

        binding = FragmentShareListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        checkPermissions();

        ShoppingListsDialog dialog = new ShoppingListsDialog();
        dialog.show(getParentFragmentManager(), "show_lists");

        binding.txtContactName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (getContext().checkSelfPermission("android.permission.READ_CONTACTS") != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                String contactName = s.toString();
                ContactsManager.contacts.clear();
                ContactsManager.loadContacts(contactName);
            }
        });

        binding.chkAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ContactsManager.setAlarm = true;
                else
                    ContactsManager.setAlarm = false;
            }
        });

        binding.txtHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")){
                    return;
                }
                int hour = Integer.parseInt(s.toString());

                if (hour > 24) {
                    hour = 24;
                    binding.txtHour.setText(hour + "");
                }

                if (hour < 0) {
                    hour = 0;
                    binding.txtHour.setText(hour + "");
                }

                ContactsManager.hour = hour;
            }
        });

        binding.txtMinute.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")){
                    return;
                }
                int minute = Integer.parseInt(s.toString());

                if (minute > 59) {
                    minute = 59;
                    binding.txtMinute.setText(minute + "");
                }

                if (minute < 0) {
                    minute = 0;
                    binding.txtMinute.setText(minute + "");
                }

                ContactsManager.minute = minute;
            }
        });

        return root;
    }

    public void checkPermissions() {
        String permisisons[] = {
                "android.permission.READ_CONTACTS",
                "android.permission.POST_NOTIFICATIONS",
                "android.permission.SEND_SMS"
        };

        requestPermissions(permisisons, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ContactsManager.loadContacts("");
            } else {
                Toast.makeText(
                        getContext(),
                        R.string.contacts_permission_denied,
                        Toast.LENGTH_LONG
                ).show();
            }

            if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                        getContext(),
                        R.string.notifications_permission_denied,
                        Toast.LENGTH_LONG
                ).show();
            }

            if (grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                        getContext(),
                        R.string.sms_permission_denied,
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}