package com.geektech.todoapp4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.geektech.todoapp4.ui.models.Task;

import org.jetbrains.annotations.NotNull;

public class FormFragment extends Fragment {

    private EditText editText;
    private boolean isRedact = false;
    private Task task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Task task = (Task) requireArguments().getSerializable("task");
        editText = view.findViewById(R.id.editText);
        if (task != null){
            this.task = task;
            isRedact = true;
            editText.setText(task.getTite());
        }

        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {
        String text = editText.getText().toString();
        Bundle bundle = new Bundle();
        if (isRedact) task.setTite(text);
        else task = new Task(text);
        task.setCreatedAt(System.currentTimeMillis());
        bundle.putSerializable("task", task);

        if (isRedact)getParentFragmentManager().setFragmentResult("rk_form2", bundle);
        else getParentFragmentManager().setFragmentResult("rk_form", bundle);

        App.getAppDatabase().taskDao().insert(task);

        close();
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}