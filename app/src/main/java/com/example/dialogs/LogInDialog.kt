package com.example.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment

class LogInDialog : DialogFragment() {
    private lateinit var username: AppCompatEditText
    private lateinit var email: AppCompatEditText
    private lateinit var submit: AppCompatTextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in_dialog, container, false)
        initView(view)
        initListeners()
        return view
    }

    private fun initListeners() {
        submit.setOnClickListener {
            DialogButtons().newData(username.text.toString(), email.text.toString())
            dismiss()
            clearFields()
        }
    }

    private fun initView(view: View) {
        username = view.findViewById(R.id.ed_username)
        email = view.findViewById(R.id.ed_e_mail)
        submit = view.findViewById(R.id.btn_submit)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun clearFields() {
        username.setText("")
        email.setText("")
    }
}