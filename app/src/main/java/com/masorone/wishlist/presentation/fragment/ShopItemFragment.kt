package com.masorone.wishlist.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.masorone.wishlist.R
import com.masorone.wishlist.databinding.FragmentShopItemBinding
import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.presentation.ShopItemViewModel
import com.masorone.wishlist.presentation.wrapper.TextWatcherWrapper

class ShopItemFragment : Fragment() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINE_ID

    private var _binding: FragmentShopItemBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement listener OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        startMode(screenMode)
        setupTextChangeListeners()
        endWork()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnEditingFinishedListener {
        fun onFinished()
    }

    private fun startMode(screenMode: String) {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun launchEditMode() {
        viewModel.fetch(shopItemId)

        viewModel.shopItem.observe(viewLifecycleOwner) { shopItem ->
            binding.etName.setText(shopItem.name)
            binding.etCount.setText(shopItem.count.toString())
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val count = binding.etCount.text.toString()
            viewModel.edit(name, count)
        }
    }

    private fun launchAddMode() {
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val count = binding.etCount.text.toString()
            viewModel.add(name, count)
        }
    }

    private fun endWork() {
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onFinished()
        }
    }

    private fun setupTextChangeListeners() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            binding.tilName.error = if (it) getString(R.string.error_input_name) else null
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            binding.tilCount.error = if (it) getString(R.string.error_input_count) else null
        }

        val textWatcherName = object : TextWatcherWrapper() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }
        }

        val textWatcherCount = object : TextWatcherWrapper() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }
        }

        binding.etName.addTextChangedListener(textWatcherName)
        binding.etCount.addTextChangedListener(textWatcherCount)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(MODE_KEY)) throw RuntimeException("Param screen mode is absent")
        val mode = args.getString(MODE_KEY)
        if (mode != MODE_ADD && mode != MODE_EDIT) throw RuntimeException("Unknown screen mode $mode")
        screenMode = mode
        if (screenMode == MODE_EDIT && !args.containsKey(SHOP_ITEM_ID))
            throw RuntimeException("Param shop item id is absent")
        shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINE_ID)
    }

    companion object {

        private const val SHOP_ITEM_ID = "shop_item_id"

        private const val MODE_KEY = "mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddMode() = ShopItemFragment().apply {
            arguments = Bundle().apply {
                putString(MODE_KEY, MODE_ADD)
            }
        }

        fun newInstanceEditMode(shopItemId: Int) = ShopItemFragment().apply {
            arguments = Bundle().apply {
                putString(MODE_KEY, MODE_EDIT)
                putInt(SHOP_ITEM_ID, shopItemId)
            }
        }
    }
}