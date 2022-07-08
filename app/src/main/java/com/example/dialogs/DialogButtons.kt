package com.example.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.dialogs.Constants.Companion.EMAIL_KEY
import com.example.dialogs.Constants.Companion.USERNAME_KEY


class DialogButtons : Fragment() {
    private lateinit var setRingtone: AppCompatButton
    private lateinit var label: AppCompatButton
    private lateinit var ringtoneDialog: Dialog
    private lateinit var labelDialog: Dialog
    private lateinit var ringtoneList: Array<String>
    private lateinit var labelList: Array<String>
    private lateinit var labelBoolList: BooleanArray
    private lateinit var ringtoneSet: AppCompatTextView
    private lateinit var labelSet: AppCompatTextView
    private lateinit var userInputData: AppCompatTextView
    private lateinit var login: AppCompatButton
    private lateinit var dialogLogIn: DialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_dialog_buttons, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListeners()
    }


    override fun onPause() {
        super.onPause()
        setInputData()
        Log.d("TAG","PAUSE")
    }

    private fun setInputData() {
        arguments?.let {
            userInputData.text =
                it.getString(USERNAME_KEY).toString() + "\n" + it.getString(EMAIL_KEY).toString()
        }
    }


    private fun initView() {
        ringtoneList = resources.getStringArray(R.array.ringtone_list)
        setRingtone = requireView().findViewById(R.id.btn_ringtone)
        label = requireView().findViewById(R.id.btn_label)
        ringtoneSet = requireView().findViewById(R.id.tv_ringtone)
        labelSet = requireView().findViewById(R.id.tv_labels)
        userInputData = requireView().findViewById(R.id.tv_login_user)
        login = requireView().findViewById(R.id.btn_login)
        labelList = resources.getStringArray(R.array.label_list)
        labelBoolList = BooleanArray(labelList.size)
        dialogLogIn = LogInDialog()
        singleChoiceDialogBuilder()
        multipleChoiceDialogBuilder()
    }

    private fun initListeners() {
        setRingtone.setOnClickListener {
            ringtoneDialog.show()
        }
        label.setOnClickListener {
            labelDialog.show()
        }
        login.setOnClickListener {
            customDialogLogIn()
        }
    }

    private fun customDialogLogIn() {
        dialogLogIn.show(requireActivity().supportFragmentManager, "LogIn)))")
    }

    private fun singleChoiceDialogBuilder() {
        var setItem: String = ""
        ringtoneDialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.ringtone_dialog_title))
            .setSingleChoiceItems(ringtoneList, 0) { _, i ->
                setItem = ringtoneList[i]
                initToast(String.format(getString(R.string.toast_choice_message), setItem))
            }
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok_text)) { _, _ ->
                setRingtone(setItem)
                initToast(getString(R.string.accept_ringtone))

            }
            .setNegativeButton(getString(R.string.cancel_text)) { _, _ ->
                initToast(getString(R.string.cancel_text))

            }
            .create()
    }

    private fun initToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun multipleChoiceDialogBuilder() {
        var labelSetTemp = mutableSetOf<String>()
        labelDialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.label_dialog_title))
            .setMultiChoiceItems(labelList, labelBoolList) { _, i, check ->
                if (labelBoolList[i]) {
                    labelSetTemp.add(labelList[i])
                } else if (!labelBoolList[i] && labelSetTemp.contains(labelList[i])) {
                    labelSetTemp.remove(labelList[i])
                }
                if (labelBoolList[i]) {
                    initToast(String.format(getString(R.string.multi_choice_text), labelList[i]))
                } else {
                    initToast(String.format(getString(R.string.label_uncheck), labelList[i]))
                }
            }

            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok_text))
            { _, _ ->
                setLabel(labelSetTemp)
                initToast(getString(R.string.label_choose))

            }
            .setNegativeButton(getString(R.string.cancel_text))
            { _, _ ->
                initToast(getString(R.string.label_cancel))
            }
            .create()
    }

    private fun setRingtone(ringtoneId: String) {
        ringtoneSet.text = ringtoneId
    }

    private fun setLabel(label: MutableSet<String>) {
        labelSet.text = ""
        label.forEach {
            if (it != label.last()) {
                labelSet.text = labelSet.text.toString() + "$it, "
            } else {
                labelSet.text = labelSet.text.toString() + it
            }
        }
    }

    fun newData(username: String, email: String) {
            arguments = Bundle().apply {
                putString(USERNAME_KEY, username)
                putString(EMAIL_KEY, email)

            }
    }
}