package br.com.heiderlopes.pokemonwstemplatev2.presentation.scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import br.com.heiderlopes.pokemonwstemplatev2.R
import br.com.heiderlopes.pokemonwstemplatev2.databinding.ActivityScanBinding
import br.com.heiderlopes.pokemonwstemplatev2.presentation.pokedex.PokedexActivity
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanActivity : BaseScanActivity(), ZXingScannerView.ResultHandler {

    private val binding: ActivityScanBinding by lazy { ActivityScanBinding.inflate(layoutInflater) }
    override val baseScannerView: ZXingScannerView?
        get() = binding.mScannerView

    override fun onPermissionDenied() {
        isContainerPermissionVisible(true)
    }

    override fun onPermissionGranted() {
        isContainerPermissionVisible(false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.permissions.btPermission.setOnClickListener {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:$packageName")
            )
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            startActivity(intent)
        }
        binding.ivBack.setOnClickListener { finish() }
        super.requestPermission()
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            isContainerPermissionVisible(false)
            binding.mScannerView.setResultHandler(this)
            binding.mScannerView.startCamera()
        } else {
            isContainerPermissionVisible(true)
        }
    }

    override fun handleResult(rawResult: Result?) {
        val pokemonNumber = rawResult?.text
        val intent = Intent(this, PokedexActivity::class.java)
        intent.putExtra("POKEMON", pokemonNumber)
        startActivity(intent)
        finish()
    }

    private fun isContainerPermissionVisible(isVisible: Boolean) {
        binding.permissions.containerPermission.isVisible = isVisible
    }
}