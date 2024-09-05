package com.example.kritsanaruksssss

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kritsanaruksssss.Car
import com.example.kritsanaruksssss.databinding.ItemCarBinding
import com.example.kritsanaruksssss.R

// Extension function to generate image URL
fun String?.toImageUrl(context: Context): String? {
    return this?.takeIf { !it.contains("*") }?.let {
        val rootUrl = context.getString(R.string.root_url)
        val imgPath = context.getString(R.string.img)
        "$rootUrl$it"
    }
}

// Adapter for displaying cars
class CarAdapter(private var cars: List<Car>) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    inner class CarViewHolder(private val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(car: Car) {
            binding.textBrand.text = "Brand: ${car.brand}"
            binding.textModel.text = "Model: ${car.model}"
            binding.textYear.text = "Year: ${car.year}"
            binding.textColor.text = "Color: ${car.color}"
            binding.textPrice.text = "Price: ${car.price}"
            binding.textFuelType.text = "Fuel Type: ${car.fuel_type}"
            binding.textNumberOfDoors.text = "Number of Doors: ${car.doors}"
            binding.textNumberOfSeats.text = "Number of Seats: ${car.seats}"

            val imageUrl = car.image_url.toImageUrl(binding.root.context)
            Log.d("CarAdapter", "Loading image from URL: $imageUrl")

            Glide.with(binding.carImage.context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background) // Ensure this image exists
                .error(R.drawable.ic_launcher_background) // Ensure this image exists
                .into(binding.carImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(cars[position])
    }

    override fun getItemCount() = cars.size

    fun updateCars(newCars: List<Car>) {
        val diffCallback = CarDiffCallback(cars, newCars)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        cars = newCars
        diffResult.dispatchUpdatesTo(this)
    }
}

// DiffUtil callback for calculating differences
class CarDiffCallback(
    private val oldList: List<Car>,
    private val newList: List<Car>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}

// Ensure all classes and functions are properly closed and no stray code is outside
