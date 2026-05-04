package com.example.taskapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskapp.MainActivity
import com.example.taskapp.R
import com.example.taskapp.data.Task
import com.example.taskapp.databinding.FragmentTasksBinding
import com.google.android.material.snackbar.Snackbar

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = (activity as MainActivity).tasksViewModel
        val authViewModel = (activity as MainActivity).authViewModel

        adapter = TaskAdapter(
            onToggle = { viewModel.toggleTask(it) },
            onEdit = { showEditTaskDialog(viewModel, it) },
            onDelete = { viewModel.deleteTask(it) }
        )

        binding.rvTasks.adapter = adapter

        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            adapter.submitList(tasks)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }

        binding.fabAddTask.setOnClickListener {
            showAddTaskDialog(viewModel)
        }

        binding.btnLogout.setOnClickListener {
            authViewModel.logout()
        }

        authViewModel.authSuccess.observe(viewLifecycleOwner) { success ->
            if (!success) {
                findNavController().navigate(R.id.action_tasksFragment_to_loginFragment)
            }
        }

        viewModel.fetchTasks()
    }

    private fun showAddTaskDialog(viewModel: TasksViewModel) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_task, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.et_task_title)

        AlertDialog.Builder(requireContext())
            .setTitle("Add New Task")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val title = etTitle.text.toString()
                if (title.isNotEmpty()) {
                    viewModel.createTask(title)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditTaskDialog(viewModel: TasksViewModel, task: Task) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_task, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.et_task_title)
        etTitle.setText(task.title)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Task")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val title = etTitle.text.toString()
                if (title.isNotEmpty()) {
                    viewModel.updateTaskTitle(task, title)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
