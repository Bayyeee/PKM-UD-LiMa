package com.project.pkm_ud_lima.ui.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.pkm_ud_lima.R
import com.project.pkm_ud_lima.database.Catatan
import com.project.pkm_ud_lima.databinding.ActivityCatatanAddUpdateBinding
import com.project.pkm_ud_lima.helper.TanggalHelper
import com.project.pkm_ud_lima.helper.ViewModelFactory

class CatatanAddUpdateActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var isEdit = false
    private var catatan: Catatan? = null
    private lateinit var catatanAddUpdateViewModel: CatatanAddUpdateViewModel

    private var _activityCatatanAddUpdateBinding: ActivityCatatanAddUpdateBinding? = null
    private val binding get() = _activityCatatanAddUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityCatatanAddUpdateBinding = ActivityCatatanAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val toolbar: Toolbar = binding?.toolbaraddupdate ?: return
        setSupportActionBar(toolbar)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.white)))

        catatanAddUpdateViewModel = obtainViewModel(this@CatatanAddUpdateActivity)

        catatan = intent.getParcelableExtra(EXTRA_NOTE)
        if (catatan != null) {
            isEdit = true
        } else {
            catatan = Catatan()
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (catatan != null) {
                catatan?.let { catatan ->
                    binding?.edtTitle?.setText(catatan.judul)
                    binding?.edtDescription?.setText(catatan.isi)
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.btnSubmit?.text = btnTitle
        binding?.btnSubmit?.setOnClickListener {
            val title = binding?.edtTitle?.text.toString().trim()
            val description = binding?.edtDescription?.text.toString().trim()
            when {
                title.isEmpty() -> {
                    binding?.edtTitle?.error = getString(R.string.empty)
                }
                description.isEmpty() -> {
                    binding?.edtDescription?.error = getString(R.string.empty)
                }
                else -> {
                    catatan.let { catatan ->
                        catatan?.judul = title
                        catatan?.isi = description
                    }
                    if (isEdit) {
                        catatanAddUpdateViewModel.update(catatan as Catatan)
                        showToast(getString(R.string.changed))
                    } else {
                        catatan.let { catatan ->
                            catatan?.tanggal = TanggalHelper.getTanggalNow()
                        }
                        catatanAddUpdateViewModel.insert(catatan as Catatan)
                        showToast(getString(R.string.added))
                    }
                    finish()
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showAlertDialog(ALERT_DIALOG_CLOSE)
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_catatan, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.hapus_catatan)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    catatanAddUpdateViewModel.delete(catatan as Catatan)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityCatatanAddUpdateBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): CatatanAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(CatatanAddUpdateViewModel::class.java)
    }
}