package com.example.estabasura


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.weather.databinding.FragmentWeatherBinding
import java.text.SimpleDateFormat
import java.util.*

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)


        weatherAdapter = WeatherAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = weatherAdapter
        }

        viewModel.weatherLiveData.observe(viewLifecycleOwner, { weather ->
            binding.progressBar.visibility = View.GONE
            binding.content.visibility = View.VISIBLE

            val currentWeather = weather.current
            val temperature = currentWeather.temp
            val humidity = currentWeather.humidity
            val icon = weather.daily[0].weather[0].icon

            binding.temperature.text = getString(R.string.temperature, temperature.toInt())
            binding.humidity.text = getString(R.string.humidity, humidity)

            val requestOptions = RequestOptions().centerCrop()
            Glide.with(this)
                .load("https://openweathermap.org/img/w/$icon.png")
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.icon)

            val dateFormatter = SimpleDateFormat("dd 'de' MMMM, HH:mm", Locale.getDefault())
            val currentDate = dateFormatter.format(Date())
            binding.currentDate.text = currentDate

            val dailyWeather = weather.daily.subList(1, weather.daily.size)
            weatherAdapter.submitList(dailyWeather)
        })

        binding.progressBar.visibility = View.VISIBLE
        binding.content.visibility = View.GONE

        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        location?.let {
            viewModel.getWeather(it.latitude, it.longitude, "YOUR_API_KEY_HERE")
        }
    }
}
