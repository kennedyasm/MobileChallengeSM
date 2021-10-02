package com.challenges.mobilechallengesm.ui.dialogs

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.challenges.mobilechallengesm.R
import com.challenges.mobilechallengesm.databinding.BeerItemDialogBinding
import com.challenges.mobilechallengesm.dto.BeerDto

class DetailsBeerDialog(private val activity: FragmentActivity) {


    private val inflater by lazy { activity.layoutInflater }
    private val builder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(
            activity,
            R.style.TransparentDialog
        )
    }
    private val binding: BeerItemDialogBinding by lazy {
        BeerItemDialogBinding.inflate(inflater, null, false)
    }

    fun show(beerDto: BeerDto) {
        binding.item = beerDto
        builder.setView(binding.root)
        builder.setCancelable(true)
        val dialog = builder.create()
        dialog.show()
    }

}