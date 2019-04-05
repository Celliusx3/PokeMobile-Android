package com.app.cellstudio.pokemobile.presentation.service

import android.annotation.TargetApi
import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.app.cellstudio.pokemobile.BaseApplication
import com.app.cellstudio.pokemobile.GlideApp
import com.app.cellstudio.pokemobile.R
import com.app.cellstudio.pokemobile.di.modules.DownloadModule
import com.app.cellstudio.pokemobile.domain.entity.CacheStatus
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
import com.app.cellstudio.pokemobile.presentation.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.DownloadViewModel
import com.app.cellstudio.pokemobile.presentation.view.activity.MainActivity
import java.util.*
import javax.inject.Inject

class DownloadService: IntentService("Download-Service") {

    @Inject
    lateinit var downloadViewModel: DownloadViewModel

    @Inject
    lateinit var scheduler: BaseSchedulerProvider

    private val notificationManager by lazy { NotificationManagerCompat.from(this) }
    override fun onCreate() {
        super.onCreate()
        BaseApplication.getInstance().getApplicationComponent().plus(DownloadModule()).inject(this)
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val pokemonTCGId = intent.getStringExtra(EXTRA_POKEMON_TCG_ID)
            val pokemonTCGTitle = intent.getStringExtra(EXTRA_POKEMON_TCG_TITLE)
            this.cacheCardData(pokemonTCGId, pokemonTCGTitle)
        }
    }

    private fun cacheCardImages(pokemonTCGTitle: String, cards: List<PokemonTCGCard>) {
        val combinedDownloadImage = ArrayList<String>()

        cards.forEach {
            combinedDownloadImage.add(it.imageUrl)
            combinedDownloadImage.add(it.imageUrlHiRes)
        }

        val targets = combinedDownloadImage.map {
            GlideApp.with(this)
                    .downloadOnly()
                    .load(it)
                    .submit()
        }


        targets.forEachIndexed { i, target ->

            try {
                val result = target.get()
                Log.d(TAG, "Image preloaded (${result.name})")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val progress = (i + 1).toFloat() / combinedDownloadImage.size.toFloat()
            Log.d(TAG, "Progress: $progress" )
            if (progress < 1) {
                showExpansionNotification(pokemonTCGTitle, CacheStatus.Downloading(progress))
            } else {
                showExpansionNotification(pokemonTCGTitle, CacheStatus.Cached)
            }
        }
    }

    private fun cacheCardData(pokemonTCGId: String, pokemonTCGTitle: String) {
        // Update initial state of all expansions
        showExpansionNotification(pokemonTCGTitle, CacheStatus.Downloading())
        downloadViewModel.getAllPokemonTCGCardsInASet(pokemonTCGId)
                .subscribeOn(scheduler.io())
                .subscribe ({
                    cacheCardImages(pokemonTCGTitle, it)
                }, {
                    it.printStackTrace()
                    showExpansionNotification(pokemonTCGTitle, null)
                })
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = getString(R.string.notification_channel_description)
            channel.enableVibration(false)
            channel.setSound(null, null)

            val notifMan = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notifMan.createNotificationChannel(channel)
        }
    }

    private fun showExpansionNotification(expansion: String, status: CacheStatus?) {
        createNotificationChannel()

        val title = when(status) {
            CacheStatus.Empty -> getString(R.string.notification_caching_title_start)
            CacheStatus.Queued -> getString(R.string.notification_caching_queued_title)
            is CacheStatus.Downloading -> getString(R.string.notification_caching_title)
            CacheStatus.Cached -> getString(R.string.notification_caching_title_finished)
            else -> getString(R.string.notification_caching_title_error)
        }

        val text = when (status) {
            null -> getString(R.string.notification_caching_text_error)
            CacheStatus.Empty -> getString(R.string.notification_caching_text)
            CacheStatus.Cached -> getString(R.string.notification_expansion_cached_text_format, expansion)
            else -> expansion
        }

        val intent = Intent(this, MainActivity::class.java)
        val pending = PendingIntent.getActivity(this, 0, intent, 0)

        val isOngoing = status != null && (status != CacheStatus.Cached)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pending)
                .setColor(ContextCompat.getColor(this, R.color.blue_900))
                .setOngoing(isOngoing)
                .setOnlyAlertOnce(true)
                .setSound(null)
                .setSmallIcon(when(status is CacheStatus.Downloading){
                    true -> android.R.drawable.stat_sys_download
                    else -> android.R.drawable.stat_sys_download_done
                })
                .setCategory(NotificationCompat.CATEGORY_PROGRESS)
                .setPriority(NotificationCompat.PRIORITY_LOW)

        if (status != null && status is CacheStatus.Downloading) {
            val progress = status.progress?.times(100f)?.toInt() ?: 0
            builder.setProgress(100, progress, progress == 0)
        } else {
            builder.setProgress(0, 0, false)
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }


    companion object {
        private val TAG = DownloadService::class.java.simpleName
        private const val EXTRA_POKEMON_TCG_ID = "EXTRA_POKEMON_TCG_ID"
        private const val EXTRA_POKEMON_TCG_TITLE = "EXTRA_POKEMON_TCG_TITLE"
        private const val CHANNEL_ID = "pokemobile_service"
        private const val NOTIFICATION_ID = 100

        fun startService(context: Context, pokemonTCGId: String, pokemonTCGTitle: String) {
            val intent = Intent(context, DownloadService::class.java)
            intent.putExtra(EXTRA_POKEMON_TCG_ID, pokemonTCGId)
            intent.putExtra(EXTRA_POKEMON_TCG_TITLE, pokemonTCGTitle)
            context.startService(intent)
        }
    }
}