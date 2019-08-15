package app.pwdgen.ui.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import app.pwdgen.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.bottomsheet.*
import kotlinx.android.synthetic.main.main_fragment.*
import com.google.android.material.bottomsheet.BottomSheetBehavior


const val SEEKBAR_MIN = 4
const val DIGITS = "DIGITS"
const val LOWER = "LOWER"
const val UPPER = "UPPER"
const val SPECIAL = "SPECIAL"
const val LENGTH = "LENGTH"

fun getAdjustedSeekbarProgress(progress: Int) = progress + SEEKBAR_MIN

class MainFragment : Fragment(), SeekBar.OnSeekBarChangeListener {
    override fun onStartTrackingTouch(p0: SeekBar?) {}
    override fun onStopTrackingTouch(p0: SeekBar?) {}
    override fun onProgressChanged(seekBar: SeekBar, p1: Int, p2: Boolean) {
        prefs.edit {
            putInt(LENGTH, seekBar.progress)
        }
        viewModel.length = getAdjustedSeekbarProgress(seekBar.progress)
    }

    private lateinit var disposables: CompositeDisposable
    private lateinit var viewModel: MainViewModel
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prefs = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        setViewModel()

        pwd.text = viewModel.pwd
        lower.isChecked = viewModel.lower
        upper.isChecked = viewModel.upper
        digit.isChecked = viewModel.digits
        special.isChecked = viewModel.special
        length.progress = viewModel.length

        length.setOnSeekBarChangeListener(this)

        regenerate.setOnClickListener { viewModel.generateNewPassword() }


        copy.setOnClickListener {
            val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Generated password", viewModel.pwd)
            clipboard.primaryClip = clip
            Toast.makeText(context, "Password copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        lower.setOnCheckedChangeListener { _, checked ->
            updatePref(LOWER, checked)
            viewModel.lower = checked
        }
        upper.setOnCheckedChangeListener { _, checked ->
            updatePref(UPPER, checked)
            viewModel.upper = checked
        }
        digit.setOnCheckedChangeListener { _, checked ->
            updatePref(DIGITS, checked)
            viewModel.digits = checked
        }
        special.setOnCheckedChangeListener { _, checked ->
            updatePref(SPECIAL, checked)
            viewModel.special = checked }

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet)

        settings.setOnClickListener {
            when (bottomSheetBehavior.state) {
                BottomSheetBehavior.STATE_EXPANDED -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                else -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.lower = prefs.getBoolean(LOWER, true)
        viewModel.upper = prefs.getBoolean(UPPER, true)
        viewModel.digits = prefs.getBoolean(DIGITS, true)
        viewModel.special = prefs.getBoolean(SPECIAL, true)
        viewModel.length = prefs.getInt(LENGTH, 8)
    }

    private fun updatePref(pref: String, checked: Boolean) {
        prefs.edit {
            putBoolean(DIGITS, checked)
        }
    }

    override fun onStart() {
        super.onStart()
        disposables = CompositeDisposable()
        disposables.add(viewModel.pwdObservable.subscribe {
            pwd.text = it
        })
    }

    override fun onStop() {
        super.onStop()
        disposables.dispose()
    }

}
