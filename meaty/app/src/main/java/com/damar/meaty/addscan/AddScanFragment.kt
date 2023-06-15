package com.damar.meaty.addscan

import android.Manifest
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.*
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.damar.meaty.R
import com.damar.meaty.customview.AnimationUtil
import com.damar.meaty.databinding.FragmentAddScanBinding
import com.damar.meaty.etc.createCustomTempFile
import com.damar.meaty.hasil.HasilActivity
import com.damar.meaty.home.HomeFragment
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class AddScanFragment : Fragment() {

    private lateinit var binding: FragmentAddScanBinding
    private var getImgFile: File? = null
    private lateinit var sharedPreferences: SharedPreferences
    private var isUploaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddScanBinding.inflate(inflater, container, false)
        AnimationUtil.playAddScanAnimation(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setHasOptionsMenu(true)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // Inisialisasi view binding
        val binding = FragmentAddScanBinding.bind(view)

        sharedPreferences =
            requireContext().getSharedPreferences(getString(R.string.pref_name), Context.MODE_PRIVATE)

        binding.buttonCamera.setOnClickListener {
            startTakePhoto()
        }

        binding.buttonGallery.setOnClickListener {
            startGallery()
        }

        binding.buttonCekHasil.setOnClickListener {
            cekHasil()
        }

        // Mengatur listener pada tombol "Cek Hasil"
        binding.buttonCekHasil.isEnabled = isUploaded
        binding.buttonCekHasil.setOnClickListener {
            cekHasil()
        }

        binding.refresh.setOnClickListener {
            val message = getString(R.string.data_cleaned)
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            isUploaded = false
            binding.buttonCekHasil.isEnabled = false
            val fragmentTransaction = requireFragmentManager().beginTransaction()
            fragmentTransaction.detach(this).attach(this).commit()
        }

        // Mengatur listener pada tombol "Tambah"
        binding.buttonAdd.setOnClickListener {
            val note = binding.edAddNotes.text.toString()

            when {
                getImgFile != null -> {
                    if (note.isNotEmpty()) {
                        val addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)

                        addViewModel.postImage(
                            HomeFragment.USER_TOKEN!!,
                            note,
                            getImgFile!!
                        )

                        showLoading(true)

                        addViewModel.isInfoError.observe(viewLifecycleOwner) { isError ->
                            showLoading(false)
                            if (isError) {
                                Toast.makeText(
                                    requireContext(),
                                    addViewModel.errorMessage.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.upload_success),
                                    Toast.LENGTH_SHORT
                                ).show()
                                navigateToHasilActivity()
                                getImgFile = null

                                // Setelah berhasil mengunggah, atur status isUploaded ke true
                                isUploaded = true
                                binding.buttonCekHasil.isEnabled = true
                            }
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.description_warning),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                else -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.photo_warning),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Memeriksa dan meminta izin jika diperlukan
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun navigateToHasilActivity() {
        val intent = Intent(requireContext(), HasilActivity::class.java)
        startActivity(intent)
    }

    private fun cekHasil() {
        val intentCekHasil = Intent(requireContext(), HasilActivity::class.java)
        startActivity(intentCekHasil)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)
        createCustomTempFile(requireContext().applicationContext).also { file ->
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                requireContext().getString(R.string.authority),
                file
            )
            getImgFile = file
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.choose_pic))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val bitmap = BitmapFactory.decodeFile(getImgFile?.path)
                    val exif = ExifInterface(getImgFile?.path!!)
                    val orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED
                    )
                    val rotatedBitmap = rotateBitmap(bitmap, orientation)
                    binding.ivCreatePhoto.setImageBitmap(rotatedBitmap)
                }
            }
        }

    private val launcherIntentGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val selectedImg = result.data?.data as Uri
                getImgFile = uriToFile(selectedImg)
                binding.ivCreatePhoto.setImageURI(selectedImg)
            }
        }
    }

    private fun uriToFile(uri: Uri): File {
        val context = requireContext().applicationContext
        val contentResolver: ContentResolver = context.contentResolver

        val inputStream = contentResolver.openInputStream(uri)
        val fileExtension = getFileExtension(uri)
        val fileName = "IMG_${System.currentTimeMillis()}.$fileExtension"
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)
        val outputStream: OutputStream = FileOutputStream(file)

        inputStream?.copyTo(outputStream, bufferSize = DEFAULT_BUFFER_SIZE)

        outputStream.close()
        inputStream?.close()

        return file
    }

    private fun getFileExtension(uri: Uri): String? {
        val contentResolver = requireContext().contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    private fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_NORMAL -> return bitmap
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                matrix.setRotate(180f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
            else -> return bitmap
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarCreate.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(),
            it
        ) == PackageManager.PERMISSION_GRANTED
    }
}