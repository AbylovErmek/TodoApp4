package com.geektech.todoapp4.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geektech.todoapp4.App;
import com.geektech.todoapp4.OnItemClickListener;
import com.geektech.todoapp4.R;
import com.geektech.todoapp4.databinding.FragmentHomeBinding;
import com.geektech.todoapp4.ui.models.Task;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TaskAdapter adapter;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        adapter = new TaskAdapter();
        List<Task> list = App.getAppDatabase().taskDao().getAll();
        adapter.addItems(list);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Task task = adapter.getItem(position);
                openFragment(task);
                Toast.makeText(requireContext(),"pos =  " + task.getTite(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemLongClick(int position) {
                Task task = adapter.getItem(position);
                new AlertDialog.Builder(requireContext()).setMessage("Удалить запись\"" + task.getTite() + "\"?").
                        setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                App.getAppDatabase().taskDao().delete(adapter.list.get(position));
                                adapter.removeItem(position);
                                adapter.list.remove(position);
                            }
                        }).setNegativeButton("Нет", null).show();
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(null);
            }
        });
        setFragmentListener();
    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener("rk_form", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
                Task task = (Task) result.getSerializable("task");
                Log.e("Home", "text: " + task.getTite());
                adapter.addItem(task);
            }
        });

        getParentFragmentManager().setFragmentResultListener("rk_form2", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
                Task task = (Task) result.getSerializable("task");
                int pos = adapter.list.lastIndexOf(task);
                adapter.list.remove(pos);
                adapter.list.add(pos, task);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void openFragment(Task task) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putSerializable("task", task);
        navController.navigate(R.id.formFragment, bundle);
    }
}